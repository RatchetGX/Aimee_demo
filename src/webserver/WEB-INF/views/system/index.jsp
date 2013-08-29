<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Aimee服务系统客服端</title>
    
    <script type="text/javascript" src="${ctx}/resources/js/jquery-ui-1.10.3.custom/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/jplayer/jquery.jplayer.min.js"></script>
    <link rel="stylesheet" href="${ctx}/resources/js/jquery-ui-1.10.3.custom/css/smoothness/jquery-ui-1.10.3.custom.min.css" />
    <link type="text/css" href="${ctx}/resources/js/jplayer/skin/blue.monday/jplayer.blue.monday.css" rel="stylesheet" />

    
    <script type="text/javascript" src="${ctx}/js/system/index.js"></script>
    <script type="text/javascript" src="${ctx}/js/system/index_websocket.js"></script>
    <script type="text/javascript" src="${ctx}/js/system/index_tab.js"></script>
    
    <style type="text/css">
        #table_kcxx td {
            text-align: center;
        }

        #table_kcxx th {
            text-align: center;
        }
        
        body {
        	font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
        	font-size: 62.5%;
        }
    </style>
    
    <style>
    #dialog label, #dialog input { display:block; }
    #dialog label { margin-top: 0.5em; }
    #dialog input, #dialog textarea { width: 95%; }
    #tabs { margin-top: 1em; }
    #tabs li .ui-icon-close { float: left; margin: 0.4em 0.2em 0 0; cursor: pointer; }
    #add_tab { cursor: pointer; }
    </style>
    
</head>
<body>
	<input type="hidden" id="ctx" name="ctx" value="${ctx}">
	<input type="hidden" id="username" name="username" value="${sessionScope.kfInfo.username}">
	<input type="hidden" id="kf_kfbh" name="kf_kfbh" value="${sessionScope.kfInfo.kfbh}">
	<input type="hidden" id="kf_kfhm" name="kf_kfhm" value="${sessionScope.kfInfo.kfhm}">
	
	<table border="0" width="98%">
	   <tr>
	      <td align="right">
	         <input type="button" id="logoutButton" value="退出登录">
	      </td>
	   </tr>
	</table>
	
	<form id="inputWordsFormTamplate" style="display:none">
	    <table border="1" width="600">
	       <tr>
	          <td align="right">消息内容：</td>
	          <td>
	              <textarea rows="10" cols="60" style="overflow-x:hidden;overflow-y:auto"></textarea>
	          </td>
	       </tr>
	       <tr>
	          <td align="center" colspan="2">
	             <input type="button" value=" 发 送 " onclick="javascript:sendMsgToCustomer2()">
	             <input type="reset" value=" 清 空 ">
	          </td>
	       </tr>
	    </table>
	</form>
	
	  <div id="jquery_jplayer_1" class="jp-jplayer" style="display:none"></div>
	  <div id="jp_container_1" class="jp-audio" style="display:none">
	    <div class="jp-type-single">
	      <div class="jp-gui jp-interface">
	        <ul class="jp-controls">
	          <li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
	          <li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
	          <li><a href="javascript:;" class="jp-stop" tabindex="1">stop</a></li>
	          <li><a href="javascript:;" class="jp-mute" tabindex="1" title="mute">mute</a></li>
	          <li><a href="javascript:;" class="jp-unmute" tabindex="1" title="unmute">unmute</a></li>
	          <li><a href="javascript:;" class="jp-volume-max" tabindex="1" title="max volume">max volume</a></li>
	        </ul>
	        <div class="jp-progress">
	          <div class="jp-seek-bar">
	            <div class="jp-play-bar"></div>
	          </div>
	        </div>
	        <div class="jp-volume-bar">
	          <div class="jp-volume-bar-value"></div>
	        </div>
	        <div class="jp-time-holder">
	          <div class="jp-current-time"></div>
	          <div class="jp-duration"></div>
	          <ul class="jp-toggles">
	            <li><a href="javascript:;" class="jp-repeat" tabindex="1" title="repeat">repeat</a></li>
	            <li><a href="javascript:;" class="jp-repeat-off" tabindex="1" title="repeat off">repeat off</a></li>
	          </ul>
	        </div>
	      </div>
	      <div class="jp-title">
	        <ul>
	          <li>Bubble</li>
	        </ul>
	      </div>
	      <div class="jp-no-solution">
	        <span>Update Required</span>
	        To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
	      </div>
	    </div>
	  </div>

    <table border='0' width="98%">
       <tr>
          <td>
             <span id="connStatus" style="text-align:left;width:100%"></span>
          </td>
       </tr>
    </table>
    <table id="mainTable" border='1' width="98%">
       <tr>
          <td width="200" valign="top">
	         <div id="accordion">
	         </div>
          </td>
          <td valign="top">
	         <div id="tabs" valign="top">
	            <ul>
	               <li><a href="#tabs-1">主页</a></li>
	            </ul>
	            <div id="tabs-1">
	               <p>主页内容介绍</p>
	            </div>
	         </div>
          </td>
       </tr>
    </table>
    
</body>
</html>