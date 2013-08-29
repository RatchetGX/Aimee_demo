// JavaScript Document
// This doc creat by chen wei 
// (c) 2012 wisedu
$(document).ready(function(){
	/*左侧菜单样式
	 *实现类似于AO系统的效果
	 *目前兼容所有浏览器
	*/
	var accordion_head = $('.areaCon .list .conTitle'),
		accordion_body = $('.areaCon .list .listCon');
		// 初始化展示第一个菜单
		accordion_head.first().addClass('hover').next('.listCon').slideDown('normal');
		// 鼠标单击后显示层
		accordion_head.on('click', function(event) {
			// 关闭默认展开的第一个菜单
			event.preventDefault();
			// 给a加样式并显示关闭层
			if ($(this).attr('class') != 'hover'){
				accordion_body.slideUp('normal');
				$(this).next().stop(true,true).slideToggle('normal');
				accordion_head.removeClass('hover');
				$(this).addClass('hover');
			}
		});
/*高级选项
*可以调整层展开速度 数字也可以
 */		
   $(".pro_inpuiry a").toggle(function(){
		$(this).addClass("fix_i").parent().siblings(".pro_con").animate({height: 'toggle', opacity: 'toggle'}, "fast");
   },function(){
	$(this).removeClass("fix_i").parent().siblings(".pro_con").animate({height: 'toggle', opacity: 'toggle'}, "fast");
   }); 

/*表格
 *说明
 *在读取数据库中信息是请注意应用在表格中的样式
 *如果发现不便实现请自行调整
 *隔行变色就可以将名字和专业的颜色给去掉了
 *
*/  
	$(".listCon").children("li").each(function(i){
		this.style.backgroundColor=['#f2f8fd','#ffffff'][i%2]
	})
	$(".listCon").children("li").mouseover(function(){
		$(this).css("backgroundColor","#f8f8f8");
	});
	$(".listCon").children("li").mouseout(function(){
	   $(".listCon li:even").css("backgroundColor","#f2f8fd"); //偶数行的颜色
	   $(".listCon li:odd").css("backgroundColor","#ffffff"); //奇数行的颜色
	});
	
	
	$(".messListCon").children("li").each(function(i){
		this.style.backgroundColor=['#f2f8fd','#ffffff'][i%2]
	})
	$(".messListCon").children("li").mouseover(function(){
		$(this).css("backgroundColor","#f8f8f8");
	});
	$(".messListCon").children("li").mouseout(function(){
	   $(".messListCon li:even").css("backgroundColor","#f2f8fd"); //偶数行的颜色
	   $(".messListCon li:odd").css("backgroundColor","#ffffff"); //奇数行的颜色
	});
	
 });
	
	
	
	
	
	
	
	
	
	
	
	
	