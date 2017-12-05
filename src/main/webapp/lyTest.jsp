<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/bigcontrol/importCard.do" method="post" enctype="multipart/form-data">
	
	选择文件:<input name="str" type="file">
	agentId:<input type="text" name="agentId">
	agentType:<input type="text" name="agentType">
	isReturnable:<input type="text" name="isReturnable">
	文件名：<input type="text" name="fileName">
	<input type="submit" value="提交">
	</form>

</body>

<script>
    var file = document.querySelector('#file'),
        reader = new FileReader();
    reader.addEventListener('load',function (e) {
         var str = e.target.result;
         console.log(str);
    });
    file.addEventListener('change',function (e) {
        var e = event || window.event;
        var file = e.target.files[0];
        reader.readAsDataURL(file);
    });

</script>
</html>