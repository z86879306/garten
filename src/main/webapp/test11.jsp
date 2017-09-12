<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />  
</head>  
<body>
修改老师头像 <form action="/worker/updateTeacherHead.do?" method="post" enctype="multipart/form-data">

            选择文件:<input type="file" name="file">
	token:		<input type="text" id ="token" name="token"/>
	<!-- workerName:		<input type="text" id ="workerName" name="workerName"/>
		sex:	<input type="text" id ="sex" name="sex"/>
		age:	<input type="text" id ="age" name="age"/> -->

            <input type="submit" value="上传">

        </form>
          <a href="wode/download.do?fileName=20170306132459001.jpg">下载 </a><br />
</body>


创建代接信息<form action="/parent/createDaijie.do?" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="file">
	token:		<input type="text" id ="token" name="token"/>
	daijieName:		<input type="text" id ="daijieName" name="daijieName"/>
		relation:	<input type="text" id ="relation" name="relation"/>
		realPhoneNumber:	<input type="text" id ="realPhoneNumber" name="realPhoneNumber"/>
	arriveTime	<input type="text" id ="arriveTime" name="arriveTime"/>
		babyId	<input type="text" id ="babyId" name="babyId"/>
            <input type="submit" value="上传">

        </form>
        
        
   家长修改宝宝头像     <form action="/parent/updateBabyHead.do?" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="file">
	token:		<input type="text" id ="token" name="token"/>
		babyId	<input type="text" id ="babyId" name="babyId"/>
            <input type="submit" value="上传">

        </form>
        
        
        
         发表朋友圈时传图片<form action="/worker/publishPhotoImg.do?" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="file1">
	   选择文件:<input type="file" name="file2">
	    选择文件:<input type="file" name="file3">
	     选择文件:<input type="file" name="file4">
	      选择文件:<input type="file" name="file5">
	       选择文件:<input type="file" name="file6">
	        选择文件:<input type="file" name="file7">
		token:	<input type="text" id ="token" name="token"/>
            <input type="submit" value="上传">

        </form>
        <br>  <br>
        
        家长发表了朋友圈[全部]  
        <form action="/parent/publishPhotoImg.do?" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="file1">
	   选择文件:<input type="file" name="file2">
	    选择文件:<input type="file" name="file3">
	     选择文件:<input type="file" name="file4">
	      选择文件:<input type="file" name="file5">
	       选择文件:<input type="file" name="file6">
	        选择文件:<input type="file" name="file7">
		token:	<input type="text" id ="token" name="token"/>
		babyId:	<input type="text" id ="babyId" name="babyId"/>
		content:	<input type="text" id ="content" name="content"/>
            <input type="submit" value="上传">
              </form>
            <br />
            ---------------------------------------------<br />
               园长发表了朋友圈[全部]  
        <form action="/principal/publishPhotoImg.do?" method="post" enctype="multipart/form-data">
            选择文件:<input type="file" name="file1">
	   选择文件:<input type="file" name="file2">
	    选择文件:<input type="file" name="file3">
	     选择文件:<input type="file" name="file4">
	      选择文件:<input type="file" name="file5">
	       选择文件:<input type="file" name="file6">
	        选择文件:<input type="file" name="file7">
		token:	<input type="text" id ="token" name="token"/>
		content:	<input type="text" id ="content" name="content"/>
            <input type="submit" value="上传">  </form>
            
            
          发起支付
        <form action="/bigcontrol/alipay.do?" method="post" enctype="multipart/form-data">
         token:		<input type="text" id ="token" name="token"/>,,,
		type:	<input type="text" id ="type" name="type"/>
		monthCount:	<input type="text" id ="monthCount" name="monthCount"/>
		gartenId:<input type="text" id ="gartenId" name="gartenId"/>
            <input type="submit" value="上传">  </form>
            
            
            
             添加食谱
            
        <form action="/smallcontrol/addrecipe.do?" method="post" enctype="multipart/form-data">
         token:		<input type="text" id ="token" name="token"/>
		time:	<input type="text" id ="time" name="time"/>
		foodName:	<input type="text" id ="foodName" name="foodName"/>
		foodContent:<input type="text" id ="foodContent" name="foodContent"/>
		    选择食谱图片:<input type="file" name="file1">
            <input type="submit" value="上传">  </form>
            
            
            
          <a href="wode/download.do?fileName=20170306132459001.jpg">下载 </a><br />
</body>
</html>