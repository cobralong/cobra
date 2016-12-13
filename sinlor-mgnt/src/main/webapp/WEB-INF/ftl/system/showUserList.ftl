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
            <li>用户列表</li>
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
                           <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>用户Id</th>
                                    <th>用户名</th>
                                    <th>权限</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if userList??>
                                    <#list userList as user>
                                    	<tr>
                                    	<td>${user.userId}</td>
                                    	<td>${user.userName}</td>
                                    	<td>
                                    		<#if (user.userRole="ROLE_USER")>用户
                                    		<#elseif (user.userRole="ADMIN_USER")>管理员
                                    		</#if>
                                    	</td>
                                    	<td >
                                    		<#if user.userId!=adminUserId>
                                    			<button class="btn btn-danger delete" userId=${user.userId} <#if !isAdminUser>disabled</#if>>
                                        			删除
                                				</button>
                                			</#if>
                                    	</td>
                                    	</tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div> 
</body>
<script>
	$("button.delete").bind("click",function(){
    var id = $(this).attr('userId');
    var post_data = {'id':id};
     $.post('${rc.contextPath}/auth/system/deleteUser', post_data, function(data){
        if(data == 'true'){
        	location.reload();
        	alert('删除成功！');
        }else{
            alert(data);
        }
    });
});    	
</script>
</html>