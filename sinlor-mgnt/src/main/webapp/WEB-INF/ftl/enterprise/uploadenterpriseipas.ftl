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
            <li>已上传应用列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<button class="btn queryToggle  btn-info">
                		<span class="glyphicon glyphicon-search"></span>查询框
                	</button>                	
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		                        <div class="input-prepend">       
                                	<span class="">应用文件名</span>
                                	<input class="span2 search" name="filePath" type="text" placeholder="filePath" value="">
                                </div>
    							<div class="input-prepend">       
                                	<span class="">应用MD5</span>
                                	<input class="span2 search" name="md5" type="text" placeholder="md5" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">类型</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
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
                                    <th>上传Id</th>
                                    <th>文件路径</th>
                                    <th>类型</th>
                                    <th>解析Id</th>
                                    <th>文件Md5</th>
                                    <th>描述</th>
                                    <th>状态</th>
                                    <th>入库时间</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <td>${ value.id?c}</td>
                            			<td>${value.filePath!''}</td>
                            			 <td>
                                        	<#if value.type == 1>
                                        		编辑上传
                                        	<#else>
                                        		爬虫上传
                                        	</#if>
                                        </td>
                                        <td>
                                        	${ value.ipaId?c}
                                        </td>
                                        <td>
                                        	${value.fileMd5!''}
                                        </td>
                                        <td>${value.info!''}</td>
                                        <td>
                                        	<#if value.status == 0>
                                        		<font color="green">待解析</font>
                                        	<#elseif value.status == 1>
                                        		<font color="green">解析成功</font>
                                        	<#else>
                                        		<font color="red">解析失败</font>
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
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
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