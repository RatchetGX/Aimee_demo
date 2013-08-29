package org.ratchetgx.aimee.common.webbase;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


/**
 * @author Anthony
 *
 */
public class BaseDao {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected DataSource dataSource;
	
	@Autowired
	protected SimpleJdbcTemplate jt;
	
	/** 
	 * 获得一个新的UUID 
	 */
	public String getNewUuid() {
		String sql = "SELECT uuid()";
		return this.jt.queryForObject(sql, String.class);
	}
	
	

}
