<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
	<body>
		<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>Pc助手用户连绑苹果账号信息</li>
		        </ul>
		    </div>
		    <div class="container-fluid">
		        <div class="row-fluid">
		            <div class="container span12">
		            <#include "lib/alert.ftl">
		                <div class='main-content'>
		                    <button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
		                    <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="searchForm" name="searchForm" action="" method="post">
		                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
				                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
				                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		                            	<div class="input-prepend">
		                                    <span class="add-on">状态</span>
		                                     <@spring.formSingleSelect "para.status", status, " " />
		                            	</div>
		                                <button class="btn btn-info search" >
		                                    <i class="icon-search icon-white"></i>查询
		                                </button>
		                            </form>
		                        </div>
		                    </div>
		                    <div class="table-content">                    
		                    <#-- table -->
		                        <form id="itemForm" name="itemForm" method="post">
		                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
		                                <thead>
		                                <tr>
		                                    <th>PcSuite用户ID</th>
		                                    <th>绑定AppleID</th>
		                                    <th>状态</th>
		                                    <th>成功登录</th>
		                                    <th>修改时间</th>
		                                    <th>入库时间</th>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
		                                    <tr>
		                                        <td>
		                                        	${value.pcSuiteUserId!''}
		                                        </td>
		                                        <td>
		                                        	${value.appleId!''}
		                                        </td>
		                                        <td>
		                                        	<#if value.status == 0>
		                                    			<span class="label label-success">√</span>
		                                    		<#else>
		                                    			<span class="label label-danger" disabled>X</span>
		                                    		</#if>
		                                        </td>
		                                        <td>
		                                        	<#if value.loginStatus == 0>
		                                    			<span class="label label-success">登陆成功</span>
		                                    		<#else>
		                                    			<span class="label label-danger">登陆失败</span>
		                                    		</#if>
		                                        </td>
		                                        <td>
		                                            <#if value.updateTime ??>
		                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
		                                            <#else>
		                                                -
		                                            </#if>
		                                        </td>
		                                        <td>
		                                            <#if value.createTime ??>
		                                            ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
		                                            <#else>
		                                                -
		                                            </#if>
		                                        </td>
		                                    </tr>
		                                    </#list>
		                                </#if>
		                                </tbody>
		                            </table>
		                        </form>
		                        <div class="pagination" id="itemPage"></div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	</body>
	<script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
    </script>
</html>