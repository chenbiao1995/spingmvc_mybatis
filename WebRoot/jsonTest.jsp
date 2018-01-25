<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json交互测试</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
//请求的是json，输出的是json
function requestJson(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/requestJson.action',
		contenType:'application/json;charset=utf-8',
		//数据格式是json串
		data:'{"name":"手机","price","999"}',
		success:function(data){
			//返回json结果
			alert(data);
		}
	})
}
//请求的是key/value，输出的是json

function responseJson(){
	
}
</script>
</head>
<body>
<input type="button" onclik="requestJson()" value="请求的是json，输出的是json"/>
<input type="button" onclik="responseJson()"value="请求的是key/value，输出的是json"/>
</body>
</html>