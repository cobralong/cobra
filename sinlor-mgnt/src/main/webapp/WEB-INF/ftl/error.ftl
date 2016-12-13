<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
		<link href='http://fonts.googleapis.com/css?family=Creepster|Audiowide' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/bootstrap.css">
		<style>
			*{
				margin:0;
				padding:0;
			}
			body{
				font-family: 'Audiowide', cursive, arial, helvetica, sans-serif;
				background-color:#212121;
				color:white;
				font-size: 18px;
				padding-bottom:20px;
			}
			.error-code{
				font-family: 'Creepster', cursive, arial, helvetica, sans-serif;
				font-size: 200px;
				color: white;
				color: rgba(255, 255, 255, 0.98);
				width: 50%;
				text-align: right;
				margin-top: 5%;
				text-shadow: 5px 5px hsl(0, 0%, 25%);
				float: left;
			}
			.not-found{
				width: 47%;
				float: right;
				margin-top: 5%;
				font-size: 50px;
				color: white;
				text-shadow: 2px 2px 5px hsl(0, 0%, 61%);
				padding-top: 70px;
			}
			.clear{
				float:none;
				clear:both;
			}
			.content{
				text-align:center;
				line-height: 30px;
                margin-top: 100px;
			}
			input[type=text]{
				border: hsl(247, 89%, 72%) solid 1px;
				outline: none;
				padding: 5px 3px;
				font-size: 16px;
				border-radius: 8px;
			}
			a{
				text-decoration: none;
				color: #9ECDFF;
				text-shadow: 0px 0px 2px white;
			}
			a:hover{
				color:white;
			}

		</style>
		<title>服务器错误</title>
	</head>
	<body>

		<p class="error-code">
			500
		</p>
		<p class="not-found">服务器出错<br/></p>
		<div class="clear"></div>
        <div class="hide">
            ${exceptionStack}
        </div>
		<div class="content">
			好吧。。。貌似有BUG了，报给管理员吧！
            <br />
            你刚刚做了什么？
            <br />
            <form action="${rc.contextPath}/admin/bug/send" method="post">
                <textarea class="span5" name="desc"></textarea>
                <br />
                <textarea name="exception" class="span5 hide">${exceptionStack}</textarea>
                <br />
                <input type="submit" class="btn btn-danger" value="报BUG">
            </form>
		</div>
	</body>
</html>