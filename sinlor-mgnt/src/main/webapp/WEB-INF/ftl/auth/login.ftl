<!DOCTYPE html>
<html lang="zh-cn" >
	<head>
		<title>用户登录</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${rc.contextPath}/css/bootstrap.min.css" rel="stylesheet">
		<link href="${rc.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet">
		
		<script src="${rc.contextPath}/js/jquery-1.7.2.min.js"></script>
		<script src="${rc.contextPath}/js/bootstrap.min.js"></script>
		
		<style type="text/css">
			.colM{width:220px; margin:70px auto;margin-top:226px;}
			.submit-row{margin-left:166px;}
		</style>
	</head>


	<body class="login">
		<!-- Container -->
		<div class="container-fluid">
		    <!-- Header -->
		    <div class="navbar navbar-inverse navbar-fixed-top">
		      <div class="navbar-inner">
		        <div class="container-fluid">
		          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		          </a>
				  <a class="brand" href="/admin/">管理后台</a>
		          <div class="nav-collapse collapse">
		            <ul class="nav pull-right">
		                <li class="divider-vertical"></li>
		            </ul>
		          </div><!--/.nav-collapse -->
		        </div>
		      </div>
		    </div>
		    <!-- END Header -->
		    <div class="row-fluid">
		        <div class="span12">
		        </div>
		    </div>
		    <!-- Content -->
		    <div id="content" class="colM">
				<div id="content-main">
				<form action="${rc.contextPath}/j_spring_security_check" method="post" id="login-form">
                    <#if RequestParameters.error == "user_or_pass_error">
                        <div class="alert alert-error">
                            <span>用户名或密码错误！</span>
                        </div>
					<#elseif RequestParameters.error != "">
						<div class="alert alert-error">
						  <span>${ RequestParameters.error! }</span>
						</div>
				    </#if>
				    <#if RequestParameters.success != "">
						<div class="alert alert-success">
						  <span>${ RequestParameters.success! }</span>
						</div>
				    </#if>
                    <#include "/lib/alert.ftl">
				  <div class="form-row">
				    <label for="id_username" class="required">用户名：</label>
				    <input id="id_username" type="text" name="j_username" maxlength="30" />
				  </div>
				  <div class="form-row">
				    <label for="id_password" class="required">密码：</label> 
				    <input type="password" name="j_password" id="id_password" />
				  </div>
				  <div class="form-remember">
				   <input type='checkbox' name='_spring_security_remember_me' checked/>记住我
				  </div>
				  <div class="submit-row">
				    <label>&nbsp;</label><input type="submit" class="btn" value="登录" />
				  </div>
				</form> 
					<script type="text/javascript">
						document.getElementById('id_username').focus()
					</script>
				</div>
		    </div>
		    <!-- END Content -->
		    <footer id="footer"></footer>
		</div>
		<!-- END Container -->
	</body>
</html>
