<!DOCTYPE html>
<html lang="zh-cn" >
	<head>
		<title>修改密码</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="${rc.contextPath}/css/bootstrap.min.css" rel="stylesheet">
		<link href="${rc.contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet">
		
		<script src="${rc.contextPath}/js/jquery-1.7.2.min.js"></script>
		<script src="${rc.contextPath}/js/bootstrap.min.js"></script>
		
		<style type="text/css">
			.colM{width:220px; margin:70px auto;margin-top:226px;}
			.submit-row{margin-left:166px;}
		</style>
		
		<script type="text/javascript">
			(function($) {
			    $(document).ready(function() {
			        $('input[type="submit"]').addClass('btn');
			    });
			}(jQuery));
		</script>
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
				<form action="" method="post" id="login-form">
                  <#include "/lib/alert.ftl">
                  <#if message??>
	                  <div class="form-row">
	                  	<font color="red">${message}</font>
	                  </div>
                  </#if>
				  <div class="form-row">
				    <label for="id_password" class="required">原密码：</label> 
				    <input type="password" name="oldPassword" id="oldPassword" />
				  </div>
				  <div class="form-row">
				    <label for="id_password" class="required">新密码：</label> 
				    <input type="password" name="newPassword" id="newPassword" />
				  </div>
				  <div class="form-row">
				    <label for="id_password" class="required">确认密码：</label> 
				    <input type="password" name="confirmPassword" id="confirmPassword" />
				  </div>
				  <div class="submit-row">
				    <label>&nbsp;</label><input type="submit" value="提交" />
				  </div>
				</form> 
				</div>
		    </div>
		    <!-- END Content -->
		    <footer id="footer"></footer>
		</div>
		<!-- END Container -->
	</body>
</html>
