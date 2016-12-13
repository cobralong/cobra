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
	            <li>苹果帐号来源列表</li>
	        </ul>
	    </div>
	    <div class="container-fluid">
	        <div class="row-fluid">
	            <div class="container span12">
	            <#include "lib/alert.ftl">
	                <div class='main-content'>
	                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/appleaccount/source/add" method="post">
	                                <div class="input-prepend">
	                                    <span class="add-on">站点描述</span>
	                                    <input class="span2" name="desc" type="text" placeholder="站点描述" value="">
	                            	</div>
	                            	<div class="input-prepend">
	                                    <span class="add-on">站点Url</span>
	                                    <input class="span2" name="url" type="text" placeholder="站点地址" value="">
	                            	</div>
	                                <button class="btn btn-info add" >
	                                    <i class="icon-search icon-white"></i>增加
	                                </button>
	                                <div id="addInfoDiv" errMsg="${errMsg}">
	                                	<#if errMsg??>
	                                		<font>${errMsg}</font>
	                                	</#if>
	                                </div>
	                            </form>
	                        </div>
	                    </div>
	                    <div class="table-content">                    
	                    <#-- table -->
	                        <form id="itemForm" name="itemForm" method="post">
	                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
	                                <thead>
	                                <tr>
	                                    <th>站点Id</th>
	                                    <th>站点描述</th>
	                                    <th>站点地址</th>
	                                    <th>状态</th>
	                                    <th>修改时间</th>
	                                    <th>入库时间</th>
	                                </tr>
	                                </thead>
	                                <tbody class="sortable">
	                                <#if values??>
	                                    <#list values as value>
	                                    <tr>
	                                        <td>
	                                        	${value.id?c}
	                                        </td>
	                                        <td>
	                                        	${value.desc!''}
	                                        </td>
	                                        <td>
	                                        	<a target="_blank" href="${value.url!''}">官网</a>
	                                        </td>
	                                        <td>
	                                        	<#if value.status == 0>
	                                    			<span class="label label-success">√</span>
	                                    		<#else>
	                                    			<span class="label label-danger" disabled>X</span>
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
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>