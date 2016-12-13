<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-应用信息上架统计列表</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>上架统计列表</li>
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
                                	<span class="add-on">上架方式</span>
                                	<select name="type">
                                		<#list types as type>
                                			<#if type.type == para.type>
                                				<option selected value="${type.type}">${type.desc}</option>
                                			<#else>
                                				<option value="${type.type}">${type.desc}</option>
                                			</#if>
                                		</#list>
                                	</select>
                                </div>
							   	<span class="add-on">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="startDateString" name="st" size="16" type="text" value="${para.st?substring(0,10)}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="add-on">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et?substring(0,10)}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
                                <button class="btn btn-info query" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    	待上架应用${wait}<a href="${rc.contextPath}/auth/app/upload/crawler/application/list?type=${para.type}">前往查看</a>
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>总数</th>
                                    <th>成功数</th>
                                    <th>更新数</th>
                                    <th>新应用数</th>
                                    <th>失败数</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td>${value.date?string("yyyy-MM-dd")}</td>
                                        <td>${ value.total }</td>
                                        <td>${ value.success }</td>
                                        <td>${ value.upgrade }</td>
                                        <td>${ value.newApp }</td>
                                        <td>${ value.failed }</td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/upload/stat/detail?id=${value.id}&type=${para.type}">上架信息列表</a>
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
	    
        $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
            'date-format': 'yyyy-mm-dd',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1,
            startView: 2,
            minView: 2,
            maxView: 2           
        });

	    function modifyPage(){
	    	$("#pager_page").val(1);
	    }
	</script>
</body>
</html>