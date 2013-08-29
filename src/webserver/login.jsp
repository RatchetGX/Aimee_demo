<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Aimee服务系统客服端登录</title>

        <link href="${ctx}/resources/css/newlogin.css" type="text/css" rel="stylesheet" />

        <script type="text/javascript" src="${ctx}/resources/js/jquery/jquery-1.8.2.min.js"></script>
        <!-- js框架 -->
        <script src="${ctx}/resources/js/jquery-plugins/validate/jquery.metadata.js" type="text/javascript"></script>
        <script src="${ctx}/resources/js/jquery-plugins/validate/jquery.validate.js" type="text/javascript"></script>
        <script src="${ctx}/resources/js/jquery-plugins/validate/messages_cn.js" type="text/javascript"></script>
        <script src="${ctx}/resources/js/jquery-plugins/validate/validate_ex.js" type="text/javascript"></script>
        
        <script type="text/javascript">
            $(document).ready(function() {
            	
                $("#loginForm").validate({
                    rules : {
                        j_username : {
                            required : true
                        },
                        j_password : {
                            required : true
                        }
                    },
                    messages : {
                        j_username : '<font color=red>&nbsp;必填项！</font>',
                        j_password : '<font color=red>&nbsp;必填项！</font>'
                    }
                });
		
                $("#captcha").click(function(){  
                    $('#captcha').hide().attr('src','${ctx}/captcha.do'+ '?'+ Math.floor(Math.random() * 100)).fadeIn();  
                });  
            });
        </script>

    </head>

    <body>
        <form action="${ctx}/login.do" method="post" id="loginForm">
        <input type="hidden" name="mode" value="db"/>
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="height:150px; background-image:url(resources/image/login/logo.png); background-repeat:no-repeat; background-position:20px 60px;"><table width="40%" border="0" align="right" cellpadding="0" cellspacing="0">
                            <tr>
                                <td align="right" valign="bottom" style="height:90px; padding-right:20px;"><a href="#">设为首页</a>  |  <a href="#">登录帮助</a>  |  <a href="#">联系我们</a></td>
                            </tr>
                        </table></td>
                </tr>
            </table>
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td valign="top" style="width:980px; height:330px; background-image:url(resources/image/login/main_bg.jpg); background-repeat:no-repeat;"><table width="40%" border="0" align="right" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="81%" height="30"></td>
                                <td width="19%">&nbsp;</td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td style="height:50px; background-image:url(resources/image/login/formtitle.png); background-repeat:no-repeat; background-position:20px 5px;">&nbsp;</td>
                                        </tr>
                                        <tr>
	                                        <td align="left"  style="padding-left: 15px">
	                                      		<b>可使用Email或手机号登录</b>                                      
	                                        </td>
                                        </tr>
                                       
                                        <c:if test="${param.error != null || param.validateCodeError != null || param.errorMsg != null}">
                                            <tr>
                                                <td align="center"><span><font color="red">
                                                            <c:if test="${param.error == 'validateCode'}">
															                        验证码错误
															</c:if>
                                                            <c:if test="${param.error == 'usernameEmpty'}">
                                                                                                                                                                                        用户名不可为空
                                                            </c:if>
                                                            <c:if test="${param.error == 'username'}">
                                                                                                                                                                                        用户名不存在
                                                            </c:if>                                                                                                                            
                                                            <c:if test="${param.error == 'password'}">
                                                                                                                                                                                        密码错误
                                                            </c:if>
                                                            <c:if test="${param.errorMsg != null}">
                                                        ${param.errorMsg}
                                                            </c:if>
                                                    </font></span></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td><table width="300" border="0" align="right" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td width="61" height="40" style="font-size:12px">用户名：</td>
                                                        <td colspan="2">
                                                            <input type="text" id='j_username' name='j_username' style="width:180px; height:20px; border:1px solid #7ab8d8"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td height="40" style="font-size:12px">密&nbsp;&nbsp; 码：</td>
                                                        <td colspan="2"><input type="password" id='j_password' name='j_password' style="width:180px; height:20px; border:1px solid #7ab8d8" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td height="40" style="font-size:12px">验证码：</td>
                                                        <td width="108"><input type="text" name="validateCode" id="validateCode" style="width:90px; height:20px; border:1px solid #7ab8d8" /></td>
                                                        <td width="131"><img src="${ctx}/captcha.do" width="66" height="21" id="captcha"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td height="50">&nbsp;</td>
                                                        <td colspan="2">
                                                            <input type="submit" value="" style="border:0px;width:84px;height:30px;background:url(${ctx}/resources/image/login/dl.png);cursor:hand"/>
                                                            <input type="reset" value="" style="border:0px;width:84px;height:30px;background:url(${ctx}/resources/image/login/cz.png);cursor:hand"/>
                                                        </td>
                                                    </tr>

                                                </table></td>
                                        </tr>                                         
                                    </table></td>
                                <td> </td>
                            </tr>
                        </table></td>
                </tr>
            </table>
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="height:10px;"></td>
                </tr>
            </table>
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center"> Copyright <span style="font-family:Arial, Helvetica, sans-serif; font-size:12px">&copy;</span> 2012 上海交通大学  All Rights Reserved.</td>
                </tr>
            </table>
            <table width="980" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="height:20px;"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
