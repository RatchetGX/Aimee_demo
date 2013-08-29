/** ===========开始定义客服端WebSocket通信===========*/
/** 客服端socket */
var socket;

/** 初始化WebSocket */
function initWebSocket(sUrl) {

   if (window.WebSocket) { /** 如浏览器支持WebSocket */
	   socket = new WebSocket(sUrl);
	   
       socket.onmessage = function(event) {
           processServerMsg(event.data);
       };
       
       socket.onopen = function(event) {
           //alert("Web Socket opened!");
    	   button_connStatus.button( "option", "label", "服务器连接状态：正常!" ); 
       };
       
       socket.onclose = function(event) {
           //alert("WebSockt与服务器端的连接已关闭！");
           button_connStatus.button( "option", "label", "服务器连接已断开!" ); 
       };
       
       socket.onerror = function(event) {
           //alert("WebSockt与服务器端的连接出错！");
           button_connStatus.button( "option", "label", "服务器连接出错!" ); 
       };
       
   } else { /** 如浏览器不支持WebSocket */
       //alert("您的浏览器不支持HTML5的WebSocket，请换用Chrome、Firefox等浏览器的较新版本，谢谢合作！");
	   button_connStatus.button( "option", "label", "您的浏览器不支持HTML5的WebSocket，请换用Chrome、Firefox等浏览器的较新版本，谢谢配合！" ); 
   }
}

/** 侦听并且恢复WebSocket链接 */
function listenAndRecoverWebSocket(sUrl) {
	var waitTime = 5;  /** 重试时间 */
    var sInt = window.setInterval(function(){
 	   if (socket.readyState != 1) {
 		  initWebSocket(sUrl);
       } 
    }, (waitTime * 1000));
}

/** 通过WebSocket发送消息 */
function sendMsgViaSocket(content) {
	/** 通过WebSocket发送消息 */
	var bSuccess = false
    if (socket.readyState != 1) {  /** 如socket状态不正常  */
        socket.close();
        if (initWebSocket()) {
           setTimeout(function() {
        	   bSuccess = socket.send(content);
           }, 250);
        }
        
    } else {   /** 如socket状态正常  */
    	bSuccess = socket.send(content);
    };
    return bSuccess;
}

/** 客户浏览器WebSocket向服务器注册 */
$(document).ready(function() {
	var sZxzbh = "zxz001";  //座席组编号
    var sKfbh = $("#kf_kfbh").val();;    //客服编号
    
    var sHost = window.location.hostname;
    var sPort = window.location.port;
    var sCtx = $("#ctx").val();
	var sUrl = "ws://" + sHost;
	if (sPort == "") {
		sPort = "80";
	} 
	sUrl += ":" + sPort;
	sUrl += sCtx + "/serverMsgReceiver?zxzbh="+sZxzbh+"&kfbh="+sKfbh;
	initWebSocket(sUrl);
	
	listenAndRecoverWebSocket(sUrl);
	
})

/** 向客户发送消息 */
function sendMsgToCustomer(sKh_khbh, sKh_openid, sMsg) {
	
	/** 组织回复给客户的消息内容 */
	var content = {};
	content.khbh = sKh_khbh;
	content.openid = sKh_openid;
	content.message = sMsg;
	return sendMsgViaSocket(JSON.stringify(content));

}
/** ===========结束定义客服端WebSocket连接===========*/