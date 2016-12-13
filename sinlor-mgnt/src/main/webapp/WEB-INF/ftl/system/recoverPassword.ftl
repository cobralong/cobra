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
            <li>重置密码</li>
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
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/system/recoverPassword" method="post">	
			                    <#if !isAdminUser>
			                        <div class="alert alert-error">
									  <span>您不是管理员，不能重置密码！</span>
									</div>
								<#else>
									<#if requestParameters.message == "recoverSucceed">
							    		<div class="alert alert-success">
									  	<span>重置成功！密码是：123456</span>
										</div>
							    	<#elseif requestParameters.message == "notExist">
							    	<div class="alert alert-error">
									  	<span>不存这个用户！</span>
									</div>
									<#elseif requestParameters.message == "emptyInput">
									<div class="alert alert-error">
									  	<span>请输入要重置的用户！</span>
									</div>
							    	</#if> 
							    	
									<div class="input-prepend">
                                    	<span class="add-on">重置用户名：</span>
                                    	<input class="span2 search" style="width:400px" name="userName" type="text" placeholder="用户名(邮箱)" value="">
                            		</div>                  
	                                <br/><br/>  
	                                <button class="btn btn-info submit" >
	                                    <i class="icon-search icon-white"></i>提交
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