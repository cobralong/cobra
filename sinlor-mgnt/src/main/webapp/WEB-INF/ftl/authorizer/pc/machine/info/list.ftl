<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-苹果授权机器虚拟信息列表</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>苹果授权机器虚拟信息列表</li>
		        </ul>
		    </div>
	    	<div class="container-fluid">
	        	<div class="row-fluid">
	            	<div class="container span12">
	            		<#include "lib/alert.ftl">
		                <div class='main-content'>
		                    <div class="well form-inline">
		                        <div class='main-action'>
		                            <form id="searchForm" name="searchForm" action="" method="post">
				                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
				                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
				                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		                               <div class="input-prepend">
			                               	<span class="add-on">状态</span>
			                               	<@spring.formSingleSelect "para.status", status, " " />
		                               </div>
		                                <button class="btn btn-info query" onClick="modifyPage()">
		                                    <i class="icon-search icon-white"></i>过滤
		                                </button>
		                            </form>
		                        </div>
		                    </div>
	                        <#if addMsg>
	                        	<font color="red">添加失败，原因：${addMsg}</font> 
	                        </#if>
		                    <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/authorizer/pc/machine/info/add"  method="post">
		                                <div class="input-prepend">
		                                	<span class="add-on">机器名称：</span>
		                                	<input class="span4" name="osName" type="text" placeholder="os_name" value="${para.osName}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器Guid：</span>
		                                	<input class="span4" name="osGuid" type="text" placeholder="os_guid" value="${para.osGuid}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器kMachineA：</span>
		                                	<input class="span4" name="kmachineIda" type="text" placeholder="kMachineA" value="${para.kmachineIda}">
		                                </div>
		                                <br/>
		                                <br/>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器kMachineB：</span>
		                                	<input class="span4" name="kmachineIdb" type="text" placeholder="kMachineB" value="${para.kmachineIdb}">
		                                </div>
		                                <button class="btn btn-info query" >
		                                    <i class="icon-search icon-white"></i>添加
		                                </button>
		                            </form>
		                        </div>                        
		                    </div>
	            			<br/><br/>
		                    <div class="table-content">
		                        <form id="itemForm" name="itemForm" action="" method="post">
		                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
		                                <thead>
		                                <tr>
		                                    <th>Id</th>
		                                    <th>机器名称</th>
		                                    <th>机器Guid</th>
		                                    <th>机器kMachineA</th>
		                                    <th>机器kMachineB</th>
		                                    <th>已授权账号</th>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
		                                    <tr>
		                                        <td>${value.id?c}</td>
		                                        <td>${value.osName}</td>
		                                        <td><@shortdesc value.osGuid!/></td>
		                                        <td><@shortdesc value.kmachineIda!/></td>
		                                        <td><@shortdesc value.kmachineIdb!/></td>
		                                        <td>
		                                        	<a class="btn btn-info query" href="${rc.contextPath}/auth/appleaccount/authorizer/auth/pc/machine/info/list?pcServerId=${value.id?c}">已信任授权账号列表</a>
		                                        </td>
		                                    </tr>
		                                    </#list>
		                                </#if>
		                                </tbody>
		                            </table>
		                        </form>
	                    		<div class="pagination" id="itemPage">
	                    	</div>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	    <script>
		    $(document).ready(function(){
		        var currPageDiv = "#pager_page";
		        var totalDiv = "#pager_total";
		        var sizeDiv = "#pager_size";
		        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
		    });
		    
		    function modifyPage(){
		    	$("#pager_page").val(1);
		    }
		</script>
	</body>
</html>