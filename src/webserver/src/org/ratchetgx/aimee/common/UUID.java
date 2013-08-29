package org.ratchetgx.aimee.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class UUID {
	/**
	 * @serial Integer that helps create a unique UID.
	 */
	private int unique;

	/**
	 * @serial Long used to record the time. The <code>time</code> will be
	 *         used to create a unique UID.
	 */
	private long time;

	/**
	 * InetAddress to make the UID globally unique
	 */
	private static String address;

	/**
	 * a random number
	 */
	private static int hostUnique;

	/**
	 * Used for synchronization
	 */
	private static Object mutex;

	private static long lastTime;

	private static long DELAY;

	private static String generateNoNetworkID() {
		Thread current = Thread.currentThread();
		StringBuffer nid = new StringBuffer();
		nid.append(current.activeCount());
		nid.append(System.getProperty("os.version"));
		nid.append(System.getProperty("user.name"));
		nid.append(System.getProperty("java.version"));

		Md5 md5 = new Md5(nid.toString());
		md5.processString();
		return md5.getStringDigest();
	}

	static {
		hostUnique = (new Object()).hashCode();
		mutex = new Object();
		lastTime = System.currentTimeMillis();
		DELAY = 10; // in milliseconds
		try {
			address = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException ex) {
			address = generateNoNetworkID();
		}
	}

	public UUID() {
		synchronized (mutex) {
			boolean done = false;
			while (!done) {
				time = System.currentTimeMillis();
				if (time < lastTime + DELAY) {
					// pause for a second to wait for time to change
					try {
						Thread.currentThread().sleep(DELAY);
					} catch (java.lang.InterruptedException e) {
					} // ignore exception
					continue;
				} else {
					lastTime = time;
					done = true;
				}
			}
			unique = hostUnique;
		}
	}

	public String toString() {
		StringBuffer seed = new StringBuffer();
		seed.append(Integer.toString(unique, 16));
		seed.append(Long.toString(time, 16));
		seed.append(address);
		Md5 md5 = new Md5(seed.toString());
		md5.processString();
		return md5.getStringDigest().toUpperCase();
	}

	public boolean equals(Object obj) {
		if ((obj != null) && (obj instanceof UUID)) {
			UUID uuid = (UUID) obj;
			return (unique == uuid.unique && time == uuid.time && address
					.equals(uuid.address));
		} else {
			return false;
		}
	}

	public static void main(String args[]) {
		System.out.println(new UUID().toString());
	}
}