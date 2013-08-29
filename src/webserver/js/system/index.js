var gKf_kfbh;  /**  */
var gKf_kfhm;  /**  */

var gKhTab_hiddenMsg = {};

var selectedKh_khbh; /** 当前选择客户_客户编号 */
var selectedKh_openid;/** 当前选择客户_客户OPENID */

/** 处理来自服务器端的消息 */
function processServerMsg(sMsg) {
	
	var jsonData = JSON.parse(sMsg);
	
	/** 元数据相关 */
	var sMeta_source = jsonData.meta_source;  		/** 元数据_消息来源：customer/admin/system */ 
	
	/** 客户相关 */
	var sKh_khbh = jsonData.kh_khbh;  				/** 客户编号 */
	var sKh_khzh = jsonData.kh_khzh;  				/** 客户帐号 */
	var sKh_openid = jsonData.kh_openid;			/** 客户openid */
	var sKh_nc = jsonData.kh_nc;  					/** 客户昵称 */
	var sKh_xb = jsonData.kh_xb;  					/** 客户性别 */
	var sKh_cs = jsonData.kh_cs;  					/** 客户城市 */
	var sKh_yy = jsonData.kh_yy;  					/** 客户语言 */
	
	/** 消息体相关 */
	var sMsg_msgid = jsonData.msg_id;               /** 消息id，64位整型 */
    var sMsg_msgtype = jsonData.msg_type;  			/** 消息类型： text/image/voice/video/music/news */
    var sMsg_content = ""; 							/** 消息内容 */
    var sMsg_picurl = ""; 							/** 消息图片链接 */
    var sMsg_mediaid = "";                          /** 消息媒体ID */
    if (sMsg_msgtype == "text") {
    	sMsg_content = jsonData.msg_content;
    } else if (sMsg_msgtype == "image") {
    	sMsg_picurl = jsonData.msg_picurl;
    } else if (sMsg_msgtype == "voice") {
    	sMsg_mediaid = jsonData.msg_mediaid;
    }
    var sMsg_sendtime = jsonData.msg_sendtime;  	/** 消息发送时间 */ 
    
    var sKh_xbmc = "";
	if (sKh_xb == "1") {
		sKh_xbmc = "男";
	} else if (sKh_xb == "2") {
		sKh_xbmc = "女";
	}
    
    /** 客户消息处理 */
    if (sMeta_source == "customer") { /** 客户消息 */
	    /** 增加客户列表accordion Header */
	    var sHeaderId = "accHeader_" + sKh_khbh;
	    if ($("#" + sHeaderId).length == 0) {
	    	var sHeader = "客户：" + sKh_nc;
	    	var sContent = sKh_nc + "[" + sKh_xbmc + "/" + sKh_cs + "/" + sKh_yy + "]"
	    	             + "<br><span style='cursor:pointer' onclick='javascript:showCustomerTab(this)'"
	    	             + " kh_khbh='" + sKh_khbh + "'"
	    	             + " kh_khzh='" + sKh_khzh + "'"
	    	             + " kh_openid='" + sKh_openid + "'"
	    	             + " kh_nc='" + sKh_nc + "'"
	    	             + " kh_xb='" + sKh_xb + "'"
	    	             + " kh_cs='" + sKh_cs + "'"
	    	             + " kh_yy='" + sKh_yy + "'"
	    		         + ">" + "[进入对话]" + "</span>";
	    	addAccordionHeader(sHeaderId, sHeader, sContent);
	    }
	    
	    /** 将消息内容添加到对话消息区(TAB已打开)或隐藏消息区(TAB未打开)*/
		if ($("#tabs div[id='tab_" + sKh_khbh + "']").length > 0) {  /** TAB已打开 */
			/** 将消息内容添加到对话消息区 */
			if (sMsg_msgtype == "text") {
				addTextMsgToDialogueWordsArea(sKh_nc + "[" + sKh_xbmc + "/" + sKh_cs + "]", sMsg_content, sKh_khbh, sMsg_sendtime);
			} else if (sMsg_msgtype == "image") {
				addImgMsgToDialogueWordsArea(sKh_nc + "[" + sKh_xbmc + "/" + sKh_cs + "]", sMsg_picurl, sKh_khbh, sMsg_sendtime);
			} else if (sMsg_msgtype == "voice") {
				addVoiceMsgToDialogueWordsArea(sKh_nc + "[" + sKh_xbmc + "/" + sKh_cs + "]", sMsg_mediaid, sKh_khbh, sMsg_sendtime);
			}
			
			
		} else {  /** TAB未打开 */
			/** 将消息内容添加到隐藏消息区 */
			if (gKhTab_hiddenMsg[sKh_khbh] == undefined) {
				gKhTab_hiddenMsg[sKh_khbh] = new Array();
			}
			var sMsg = {};
			sMsg.kh_khbh = sKh_khbh;
			sMsg.kh_khzh = sKh_khzh;
			sMsg.kh_openid = sKh_openid;
			sMsg.kh_nc = sKh_nc;
			sMsg.kh_xb = sKh_xb;
			sMsg.kh_cs = sKh_cs;
			sMsg.kh_yy = sKh_yy;  
			sMsg.msgtype = sMsg_msgtype;
			sMsg.content = sMsg_content;
			sMsg.picurl = sMsg_picurl;
			sMsg.mediaid = sMsg_mediaid;
			sMsg.time = sMsg_sendtime;
			gKhTab_hiddenMsg[sKh_khbh].push(sMsg);
		}
    }
}

/** 显示客户对话TAB */
function showCustomerTab(sCustomerInfo) {
	
	/** 客户相关 */
	var sKh_khbh = $(sCustomerInfo).attr("kh_khbh");  				/** 客户编号 */
	var sKh_khzh = $(sCustomerInfo).attr("kh_khzh");  				/** 客户帐号 */
	var sKh_openid = $(sCustomerInfo).attr("kh_openid");  			/** 客户OPENID */
	var sKh_nc = $(sCustomerInfo).attr("kh_nc");  					/** 客户昵称 */
	var sKh_xb = $(sCustomerInfo).attr("kh_xb");  					/** 客户性别 */
	var sKh_cs = $(sCustomerInfo).attr("kh_cs");  					/** 客户城市 */
	var sKh_yy = $(sCustomerInfo).attr("kh_yy");  					/** 客户语言 */
	
	var cnt = $("#tabs div[id='tab_" + sKh_khbh + "']").length;
	if (cnt == 0) {  /** TAB不存在 */
	
		/** 新建客户对话TAB页 */
		var sTabId = "tab_" + sKh_khbh;
		var sTitle = sKh_nc;
		//var sDialogueWordsArea = '<textarea id="dialogueWordsArea_' + sKh_khbh + '" readonly="readonly" rows="8" cols="20" style="width:100%;height:260px;BORDER-BOTTOM:0px solid;BORDER-LEFT:0px solid;   BORDER-RIGHT:0px solid;BORDER-TOP:0px solid;overflow-y:auto;overflow-x:hidden;border:0;"></textarea>';
		var sDialogueWordsArea = '<div id="dialogueWordsArea_' + sKh_khbh + '" readonly="readonly" rows="8" cols="20" style="width:100%;height:260px;BORDER-BOTTOM:0px solid;BORDER-LEFT:0px solid;   BORDER-RIGHT:0px solid;BORDER-TOP:0px solid;overflow-y:auto;overflow-x:hidden;border:0;"></div>';
		var sInputWordsArea = "<form id='inputWordsForm_" + sKh_khbh + "'>"
		                    + $("#inputWordsFormTamplate").html()
		                    + "</form>";
		var sContent = "<table border='1' width='100%'><tr><td>"
			        + sDialogueWordsArea
			        + "</td><td valign='top' rowspan='2' width='200'><b>使用技巧或其它动态消息</b>" 
			        + "<br>具体内容等...</td>"
			        + "</tr><tr><td>"
			        + sInputWordsArea
			        +"</td></tr></table>";
		addCustomerTab(sTabId, sTitle, sContent, sKh_khbh, sKh_openid);
	}

	/** 显示对应的客户TAB页 */
	$("#tabs div[id^='tab_']").each(function(i, obj) {
	    var sKhbh = $(obj).attr("khbh");
	    if (sKh_khbh == sKhbh) {
	    	tabs.tabs( "option", "active", i + 1);
	    }
	});
	
	/** 将隐藏的消息内容添加到对话消息区 */
	for (var i = 0; i < gKhTab_hiddenMsg[sKh_khbh].length; i++) {
		var sMsg = gKhTab_hiddenMsg[sKh_khbh][i];
		var sKh_khbh = sMsg.kh_khbh;
		var sKh_khzh = sMsg.kh_khzh;
		var sKh_openid = sMsg.kh_openid;
		var sKh_nc = sMsg.kh_nc;
		var sMsg_msgtype = sMsg.msgtype;
		var sMsg_content = sMsg.content;
		var sMsg_picurl = sMsg.picurl;
		var sMsg_mediaid = sMsg.mediaid;
		var sMsg_time = sMsg.time;
		
		if (sMsg_msgtype == "text") {
			addTextMsgToDialogueWordsArea(sKh_nc, sMsg_content, sKh_khbh, sMsg_time);
		} else if (sMsg_msgtype == "image") {
			addImgMsgToDialogueWordsArea(sKh_nc, sMsg_picurl, sKh_khbh, sMsg_time);
		} else if (sMsg_msgtype == "voice") {
			addVoiceMsgToDialogueWordsArea(sKh_nc, sMsg_mediaid, sKh_khbh, sMsg_time);
		}
	}
	gKhTab_hiddenMsg[sKh_khbh] = undefined;
}

/** 当切换到指定的用户对话TAB */
function doOnSwitchCustomerTab(sNewPanel) {
	var sKh_khbh = sNewPanel.attr("khbh");
	var sKh_openid = sNewPanel.attr("openid");
	selectedKh_khbh = sKh_khbh;
	selectedKh_openid = sKh_openid;
}

/** 向客户发送消息 */ 
function sendMsgToCustomer2() {
	var sKh_khbh = selectedKh_khbh;
	var sKh_openid = selectedKh_openid;
	var sMsg_msgtype = "text";
	var sMsg_content = $('#inputWordsForm_' + sKh_khbh + " textarea").val();
	if (sMsg_content == "") {
		alert("请输入消息内容！");
		return;
	}
	var sMsg_picurl = "";
	
	/** 向客户发消息 */
	var bSuccess = sendMsgToCustomer(sKh_khbh, sKh_openid, sMsg_content);
	if (bSuccess) {
		
		/** 将消息内容添加到对话消息区 */
		addTextMsgToDialogueWordsArea(gKf_kfhm, sMsg_content, sKh_khbh, getNowStr());
		
		/** 清空消息输入框 */
		$('#inputWordsForm_' + sKh_khbh + " textarea").val("");
	}
}

/** 将文本消息内容添加到对话消息区 */
function addTextMsgToDialogueWordsArea(sMsgSender, sMsg_content, sKhbh, sNowStr) {
	
	if ($('#dialogueWordsArea_' + sKhbh).html() != "") {
		$('#dialogueWordsArea_' + sKhbh).append("<br>");
	}
	$('#dialogueWordsArea_' + sKhbh).append("# " + sMsgSender + "(" + sNowStr + ")" + "<br>");
	
	$('#dialogueWordsArea_' + sKhbh).append(sMsg_content);
}

/** 将图片消息内容添加到对话消息区 */
function addImgMsgToDialogueWordsArea(sMsgSender, sMsg_picurl, sKhbh, sNowStr) {
	
	if ($('#dialogueWordsArea_' + sKhbh).html() != "") {
		$('#dialogueWordsArea_' + sKhbh).append("<br>");
	}
	$('#dialogueWordsArea_' + sKhbh).append("# " + sMsgSender + "(" + sNowStr + ")" + "<br>");
	
	var sHost = window.location.hostname;
    var sPort = window.location.port;
    var sCtx = $("#ctx").val();
	var picUrl = "http://" + sHost;
	if (sPort == "") {
		sPort = "80";
	} 
	picUrl += sCtx + "/pictureGetter";
	sMsg_picurl = picUrl + "?picurl=" + sMsg_picurl;
	var sImgTag = "<img src='" + sMsg_picurl + "' width='150' height='150'>";
	$('#dialogueWordsArea_' + sKhbh).append(sImgTag);
	
	//var dialogueWordsArea = document.getElementById("dialogueWordsArea_" + sKhbh);
	//dialogueWordsArea.scrollTop = dialogueWordsArea.scrollHeight;
	
	//var imgObj = document.createElement("IMG");
	//imgObj.src = sMsg_picurl;
	//document.getElementById('dialogueWordsArea_' + sKhbh).appendChild(imgObj);
	
}

/** 将语音消息内容添加到对话消息区 */
function addVoiceMsgToDialogueWordsArea(sMsgSender, sMsg_mediaid, sKhbh, sNowStr) {
	
	if ($('#dialogueWordsArea_' + sKhbh).html() != "") {
		$('#dialogueWordsArea_' + sKhbh).append("<br>");
	}
	$('#dialogueWordsArea_' + sKhbh).append("# " + sMsgSender + "(" + sNowStr + ")" + "<br>");
	
	
	var sHost = window.location.hostname;
    var sPort = window.location.port;
    var sCtx = $("#ctx").val();
	var mediaUrl = "http://" + sHost;
	if (sPort == "") {
		sPort = "80";
	} 
	mediaUrl += ":" + sPort;
	mediaUrl += sCtx + "/mediaGetter?mediaId="+sMsg_mediaid;
	var playerStr = getQPlayer(mediaUrl, 300, 80);
	$('#dialogueWordsArea_' + sKhbh).append(playerStr);
	
	/*
	$("#jquery_jplayer_1").jPlayer({
        ready: function () {
          $(this).jPlayer("setMedia", {
            mp3: mediaUrl
          });
        },
        swfPath: sCtx + "/resources/js/jplayer",
        supplied: "mp3"
    });
	*/
	
}



/** 获取当前时间字符串 */ 
function getNowStr() {
	var sNow = new Date()
	var sYear = sNow.getFullYear()
	var sMonth = sNow.getMonth() + 1
	var sDay = sNow.getDate()
	var sHour = sNow.getHours(); 
	var sMinute = sNow.getMinutes(); 
	var sSec = sNow.getSeconds(); 
	var sNowStr = sYear + "-" 
    + (sMonth<10 ? "0" + sMonth : sMonth) + "-"
    + (sDay<10 ? "0"+ sDay : sDay) + " "
    + (sHour<10 ? "0"+ sHour : sHour) + ":"
    + (sMinute<10 ? "0" + sMinute : sMinute) + ":"
    + (sSec<10 ? "0" + sSec : sSec);
	
	return sNowStr;
}

function getQPlayer(u, w, h) { 
	var pv=''; 
	pv += '<object width="'+w+'" height="'+h+'" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">'; 
	pv += '<param name="src" value="'+u+'">'; 
	pv += '<param name="controller" value="true">'; 
	pv += '<param name="type" value="video/quicktime">'; 
	pv += '<param name="autoplay" value="true">'; 
	pv += '<param name="target" value="myself">'; 
	pv += '<param name="bgcolor" value="black">'; 
	pv += '<param name="pluginspage" value="http://www.apple.com/quicktime/download/index.html">'; 
	pv += '<embed src="'+u+'" width="'+w+'" height="'+h+'" controller="true" align="middle" bgcolor="black" target="myself" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/index.html"></embed>'; 
	pv += '</object>'; 
	
	return pv;
} 

/** 组织界面 */
$(document).ready(function() {
	var sCtx = $("#ctx").val();
	
	gKf_kfbh = $("#kf_kfbh").val();
	gKf_kfhm = $("#kf_kfhm").val();
	
	/** 设置SESSION永不过期 */
	setInterval(function() {
		$.ajax({ 
			 url : sCtx + "/heartbeat.jsp",
             async : true
		}) 
	}, 10000); 

})


