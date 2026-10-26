<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>自定义函数 divide(a, b)</title>
</head>
<body>
<h1>自定义函数 #2：divide(a, b)</h1>
<p>
    ${a} ÷ ${b} = <strong>${divide(a, b)}</strong>
</p>
<hr/>
<p>
    <a href="/fn/divide?a=10&b=2">正常: 10/2</a> |
    <a href="/fn/divide?a=10&b=0">异常: 10/0（视图渲染阶段抛异常）</a> |
    <a href="/fn/divide-caught?a=10&b=0">Controller 内 try/catch 捕获</a> |
    <a href="/fn/divide-advice?a=10&b=0">交给 @ControllerAdvice 全局处理</a>
</p>
</body>
</html>
