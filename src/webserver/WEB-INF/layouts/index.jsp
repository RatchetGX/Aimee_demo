<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>上海交通大学研究生教育管理系统</title>
        <%@ include file="/common/widgets.jsp"%>

        <decorator:head></decorator:head>

        <script type="text/javascript">
            $(function() {	
                
                /*************************************/
                $(".myList").ssfwAccordion ({
                    active : 0
                });
		
                /*************************************/
                $(".mRTable").ssfwAccordion({
                    header : ">form> div > h4",
                    heightStyle : "content",
                    collapsible : true,
                    active : false,
                    beforeActivate : function(event, ui) {
                        ui.newPanel.slideToggle();
                        return false;
                    }
                }).sortable({
                    axis : "y",
                    handle : "h4",
                    stop : function(event, ui) {
                        ui.item.children("h4").triggerHandler("focusout");
                    }
                });
                
                /*************************************/
                var instance = $(".mRTable").data('ssfwAccordion');
                if (instance && instance.headers.length) {
                    $(instance.headers[0]).next().show();
                    if(instance.headers.length > 1){
                        $(instance.headers[1]).next().show();
                    }
                }
		
                /*************************************/
                $(".listCon > li >a").click(function(){
                    $('#menuitemwid').val($(this).attr('menuitemwid'));
                    $('#menuForm').submit();
                });
               var switchRoleSelect = $('#roleSelect');
               if(switchRoleSelect.length){
            	   switchRoleSelect.change(function (){
            		    var selectedRole = $(this).find('option:selected');
            		    var form = $('<form>',{
            		    	method : 'post',
            		    	action : '${ctx}' + '/index.do'
            		    }).append($('<input>',{
            		        type : 'hidden',
            		        name : 'role'
            		    }).val(selectedRole.val())).appendTo('body');
            		    form.submit()
            	   });
               }
            });

        </script>
        <!--[if IE 6]>
        <script type="text/javascript" language="javascript" src="${ctx}/resources/js/ie6png.js"></script>
        <![endif]-->
    </head>

    <body>
        <form id="menuForm" method="post" action="${ctx}/navmenu.do">
            <input type="hidden" id="menuitemwid" name="menuItemWid" />
        </form>
        <div class="header w1000">
            <div class="logo">
                <a href="#" title="上海交通大学研究生信息管理系统"></a>
            </div>
            <div class="welMessage">
                <p>   <c:if test="${fn:length(roleList) > 0}">
                          <select id="roleSelect">
		                      <c:forEach var="role" items="${sessionScope.roleList}">
		                         <option  
		                           <c:if test="${sessionScope.currentRole eq role.authority}"> selected = "true"</c:if>
		                          >${role.authority}</option>
		                      </c:forEach>
	                      </select>
                       </c:if>
                    <a href="#" class="changePassword"><i></i>修改密码</a>
                    <a href="${ctx}/j_spring_security_logout" class="exit"><i></i>退出系统</a>
                </p>
                <p>
                    <span>2013年1月1日，欢迎访问本系统</span>
                </p>
            </div>
        </div>
        <div class="container w1000">
            <div class="mainLeft">
                <h1 class="areaTitle">我的菜单</h1>
                <div class="myList">
                    <c:forEach var="first" items="${sessionScope.menuItems}"
                               varStatus="status">
                        <h3 class="myListTitle">
                            <i class="arrow"></i><i class="smallIcon"></i>${first.name}
                        </h3>
                        <div class="myListCon">
                            <ul class="listCon">
                                <c:forEach var="second" items="${first.children}">
                                    <li><a href="javascript:" path="${second.path}"
                                           menuitemwid="${second.wid}"><i></i>${second.name}</a></li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <decorator:body></decorator:body>

        </div>
    </body>
</html>
