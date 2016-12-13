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
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>软件列表</li>
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
                                	<span class="add-on">ROOT_ID</span>
                                    <input class="span2" name="rid" type="text" placeholder="ROOT_ID" value="${para.rid}">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">名称</span>
                                    <input class="span2" name="name" type="text" placeholder="应用名称" value="${para.name}">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">最低价格</span>
                                    <input class="span2" name="minPrice" type="text" placeholder="0.0" value="${para.minPrice}">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">最高价格</span>
                                    <input class="span2" name="maxPrice" type="text" placeholder="100.0" value="${para.maxPrice}">
                                </div><br/>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <span class="add-on">开始时间</span>
							      <input class="span2" id="startDateString" name="startTime" size="16" type="text" value="${para.startTime}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							    	<span class="add-on">结束时间</span>
							      <input class="span2" id="endDateString" name="endTime" size="16" type="text" value="${para.endTime}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <div class="input-prepend">
                                	<span class="add-on">排序类别</span>
                                     <@spring.formSingleSelect "para.sortType", sortTypes, " " />
                                     <@spring.formSingleSelect "para.order", orders, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" value="" id="selectAll" /></th>
                                    <th>名称(编辑名称)</th>
                                    <th>大小</th>
                                    <th>短描述</th>
                                    <th>分类</th>
                                    <th>价格</th>
                                    <th>最新更新时间</th>
                                    <th>授权下载状态</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td><input type="checkbox" name="ids" class='selectOne' value="${ value.id! }"/></td>
                                        <td>
                                            <a class="mark-visited" href="http://ios.appchina.com/app/${ value.rootId! }" target="_blank" title="${value.title}">
                                               <#if value.title?length gt 8>
                                               	${value.title?substring(0,8)}...
                                               <#else>
                                               	${value.title}
                                               </#if>
                                               <#if value.editorTitle?length gt 0>
                                               	(${value.editorTitle})
                                               </#if>                                               
                                            </a>
                                        </td>
                                        <td>${ value.size! }</td>
                                        <td>${ value.shortDesc! }</td>
                                        <td>
                                       		<#assign cname = categoryNameMap[value.categoryId?c]>
                                        	${ cname! }
                                        </td>
                                        <td>
                                            <#if value.price == 0>
                                            	免费
                                            <#else>
                                                ${value.priceSymbol} ${value.price}
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.updateTime ??>
                                            	${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success" disabled>正常</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>删除</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/detail?id=${value.id}">详情</a>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
                    	<div class="pagination" id="itemPage"></div>
                    </div>

                    <div class="modal hide" id="batch-modal">
                        <div class="modal-header">
                            <button type="button" class="close close-modal" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h3 id="batch-operate-title"></h3>
                        </div>
                        <div class="modal-body">
                            <textarea class="input-block-level" id="reason"></textarea>
                        </div>
                        <div class="modal-footer">
                            <a href="#" class="btn close-modal">关闭</a>
                            <a class="btn btn-primary batch-submit" id="">确定</a>
                        </div>
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
            timeFormat: 'yyyy-mm-dd hh:ii:00',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1
        });
    </script>
</body>
</html>