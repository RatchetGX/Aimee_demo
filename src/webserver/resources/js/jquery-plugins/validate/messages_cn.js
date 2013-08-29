/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
        required: "<font color=red>&nbsp;必填字段&nbsp;</font>",
		remote: "<font color=red>&nbsp;请修正该字段&nbsp;</font>",
		email: "<font color=red>&nbsp;请输入正确格式的电子邮件&nbsp;</font>",
		url: "<font color=red>&nbsp;请输入合法的网址&nbsp;</font>",
		date: "<font color=red>&nbsp;请输入合法的日期&nbsp;</font>",
		dateISO: "<font color=red>&nbsp;请输入合法的日期 (ISO).&nbsp;</font>",
		number: "<font color=red>&nbsp;请输入合法的数字&nbsp;</font>",
		digits: "<font color=red>&nbsp;只能输入整数&nbsp;</font>",
		creditcard: "<font color=red>&nbsp;请输入合法的信用卡号&nbsp;</font>",
		equalTo: "<font color=red>&nbsp;请再次输入相同的值&nbsp;</font>",
		accept: "<font color=red>&nbsp;请输入拥有合法后缀名的字符串&nbsp;</font>",
		maxlength: jQuery.validator.format("<font color=red>&nbsp;请输入一个长度最多是 {0} 的字符串&nbsp;</font>"),
		minlength: jQuery.validator.format("<font color=red>&nbsp;请输入一个长度最少是 {0} 的字符串&nbsp;</font>"),
		rangelength: jQuery.validator.format("<font color=red>&nbsp;请输入一个长度介于 {0} 和 {1} 之间的字符串&nbsp;</font>"),
		range: jQuery.validator.format("<font color=red>&nbsp;请输入一个介于 {0} 和 {1} 之间的值&nbsp;</font>"),
		max: jQuery.validator.format("<font color=red>&nbsp;请输入一个最大为 {0} 的值&nbsp;</font>"),
		min: jQuery.validator.format("<font color=red>&nbsp;请输入一个最小为 {0} 的值&nbsp;</font>")
});