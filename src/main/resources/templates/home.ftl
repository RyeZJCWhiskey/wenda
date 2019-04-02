<#--<!DOCTYPE> 声明必须是 HTML 文档的第一行，位于 <html> 标签之前。-->
<#--<!DOCTYPE> 不是 HTML 标签。它为浏览器提供一项信息(声明),即 HTML 是用什么版本编写的。
HTML 也有多个不同的版本，只有完全明白页面中使用的确切 HTML 版本，浏览器才能完全正确地显示出 HTML 页面。
这就是 <!DOCTYPE> 的用处。-->
<#--请始终向 HTML 文档添加 <!DOCTYPE> 声明，这样浏览器才能获知文档类型-->
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<body>
<div align="centrt">
    <span>${currentDate?string("yyyy-MM-dd HH:mm:ss")}</span>
    <span>${name}</span>
</div>
</body>
</html>
