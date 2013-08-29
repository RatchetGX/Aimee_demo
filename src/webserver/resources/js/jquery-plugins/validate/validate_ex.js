/**
 * 扩展jQuery validate规则
 */
$(document).ready(function() {
    // 年月yyyy-MM
    jQuery.validator.addMethod("isNy", function(value, element) {
        var tel =  /\d{4}-(?:0[1-9]|1[0-2])/;
        return this.optional(element) || (tel.test(value));
    }, "<font color=red>&nbsp;请正确填写年月（yyyy-MM）&nbsp;</font> ");
    // 电话号码格式010-12345678
    jQuery.validator.addMethod("isTel", function(value, element) {
        var tel = /^\d{3,4}-?\d{7,9}$/;
        return this.optional(element) || (tel.test(value));
    }, "<font color=red>&nbsp;请正确填写您的电话号码&nbsp;</font> ");
    //手机
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
        return this.optional(element)
        || (length == 11 && mobile.test(value));
    }, "<font color=red>&nbsp;请正确填写您的手机号码 &nbsp;</font>");
    //身份证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) {
        return this.optional(element) || isIdCardNo(value);
    }, "<font color=red>&nbsp;请正确输入您的身份证号码&nbsp;</font> ");
    //邮编
    jQuery.validator.addMethod("isZipCode", function(value, element) {
        var tel = /[1-9]\d{5}(?!\d)$/;
        return this.optional(element) || (tel.test(value));
    }, "<font color=red>&nbsp;请正确填写您的邮政编码 &nbsp;</font>");
    //字符
    jQuery.validator.addMethod("stringCheck ", function(value, element) {
        return this.optional(element)
        || /^[\u0391-\uFFE5\w]+$/.test(value);
    }, "<font color=red>&nbsp;只能包括中文字、英文字母、数字和下划线&nbsp; </font>");
    //QQ
    jQuery.validator.addMethod("isQQ", function(value, element) {
        //alert("value="+ value + "，element="+ element.id);
        return this.optional(element)
        || /[1-9][0-9]{4,}$/.test(value);
    }, "<font color=red>&nbsp;QQ号码输入有误&nbsp;</font>");
    //日期比较
    jQuery.validator.addMethod("afterStartime", function(value, element,param) {
        var v = $("#" + param).val();
        if(value && v){
            var s_st = new Date(Date.parse(v.replace(/-/g, "/"))); 
            var s_et = new Date(Date.parse(value.replace(/-/g, "/"))); 
//            alert(s_st + "------" + s_et);
            return s_st < s_et;
        }
        return true;
    }, "<font color=red>&nbsp;结束日期必须大于起始日期&nbsp;</font> ");
	
	
	
});
