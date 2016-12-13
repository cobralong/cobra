<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>用户添加</li>
        </ul>
    </div>
    
    <div id="addInfoDiv">
    	<#if errMsg??>
    		<font color="red">${errMsg}</font>
    	</#if>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/system/addUser" method="post">	
			                    <#if !isAdminUser>
			                      <div class="alert alert-error">
			                            <span>你不是管理员不能添加用户！ </span>
			                        </div>
			                    <#else>
			                    	<#if requestParameters.message == "emptyInfo">
			                        <div class="alert alert-error">
			                            <span>填写信息不完整！</span>
			                        </div>
									<#elseif requestParameters.message == "differPassword">
										<div class="alert alert-error">
										  <span>密码不一致！</span>
										</div>
								    </#if>
								    <#if requestParameters.message == "addSucceed">
										<div class="alert alert-success">
										  <span>添加成功！</span>
										</div>
								    </#if>	                      
	                                <div class="input-prepend">
	                                    <span class="add-on">用户名：</span>
	                                    <input class="span2 search" style="width:400px" name="userName" type="text" placeholder="用户名(邮箱)" value="">
	                            	</div>
	                            	<br/><br/>
	                                <div class="input-prepend">
	                                    <span class="add-on">用户密码</span>
	                                    <input id="input_user_pwd" class="span2" style="width:600px" name="password" type="password" placeholder="用户密码" value="">
	                                </div>
	                                <br/><br/>
	                                 <div class="input-prepend">
	                                    <span class="add-on">确认密码</span>
	                                    <input id="input_user_pwd1" class="span2" style="width:600px" name="confirmPassword" type="password" placeholder="确认密码" value="">
	                                </div>                          
	                                <br/><br/>  
	                                <button class="btn btn-info add" >
	                                    <i class="icon-search icon-white"></i>增加
	                                </button>
			                    </#if>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div> 
    </script>
</body>
</html>