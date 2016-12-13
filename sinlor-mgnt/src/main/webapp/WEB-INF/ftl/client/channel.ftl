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
	            <li>客户端渠道列表</li>
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
	                                    <span class="add-on">父渠道名称</span>
	                                    <@spring.formSingleSelect "para.parentId", parentChannelIdNames, " " />
	                            	</div>
	                                <button class="btn btn-info add" >
	                                    <i class="icon-search icon-white"></i>过滤
	                                </button>
	                            </form>
	                        </div>
	                    </div>
	                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form id="addForm" name="searchForm" action="${rc.contextPath}/auth/client/addchannel" method="post">
	                                <div class="input-prepend">
	                                    <span class="add-on">渠道名称</span>
	                                    <input class="span2" name="channel" type="text" placeholder="渠道名称" value="">
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">渠道描述</span>
	                                    <input class="span2" name="desc" type="text" placeholder="渠道描述" value="">
	                            	</div>
	                            	<div class="input-prepend">
	                                	<span class="">父渠道</span>
	                                     <@spring.formSingleSelect "para.parentId", parentChannelIdNames, " " />
	                                </div>
	                                <div class="input-prepend">
	                                    <span class="add-on">直接子渠道</span>
	                                    <input id="input_app_name" class="checkbox" name="leaf" type="checkbox" value="1" checked>
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
	                                    <th>渠道名称</th>
	                                    <th>渠道描述</th>
	                                    <th>父渠道</th>
	                                    <th>直接子渠道</th>
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
	                                        	${value.channel}
	                                        </td>
	                                        <td>
	                                        	${value.desc}
	                                        </td>
	                                        <td>
	                                        	<#if value.parentId??>
	                                        		<#assign channelName = parentChannelIdNames[value.parentId?c]>    
	                                        		${channelName}
	                                        	<#else>	                                        		
	                                    			<span class="label label-success">√</span>
	                                        	</#if>
	                                        </td>
	                                        <td>
	                                        	<#if value.leaf??>
	                                        		<#if value.leaf == 1>
	                                        			<span class="label label-success">√</span>
	                                        		<#else>
	                                        			<span class="label label-danger">X</span>
	                                        		</#if>
	                                        	<#else>	                                        		
	                                    			<span class="label label-danger">X</span>
	                                        	</#if>
	                                        </td>
	                                        <td>
	                                        	<#if value.status == 0>
	                                    			<span class="label label-success">√</span>
	                                    		<#else>
	                                    			<span class="label label-danger">X</span>
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