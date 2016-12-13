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
            <li>轮播图列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <span class="">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="search_startDateString" name="st" size="16" type="text" value="${para.st}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                                <button id="btn_now_promote" class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>正在推广
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                    	<#if para.status == 0>	
                    		<p>在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表:</p>
                    	</#if>
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>RootID</th>
                                    <th>Icon</th>
                                    <th>图片</th>
                                    <th>下载地址</th>
                                    <th>操作</th>                                    
                                    <th>Rank</th>
                                    <th>渠道</th>
                                    <th>状态</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>授权下载状态</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.rootId}" class="gr_${value.rootId?c}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<input id="hidden_${value.rootId}" type="hidden" root_id = "${value.rootId!}" root_title="${rootSimple.name}"/>
                                    	<#assign rowspan=1>
                                    	<#if value.banners??>
                                    		<#if value.banners?size gt 1>
                                    			<#assign rowspan = value.banners?size>
                                    		</#if>
                                    	</#if>
                                        <td rowspan="${rowspan?c}">
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td rowspan="${rowspan?c}">
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                        <td rowspan="${rowspan?c}">
                                        	<a href="${value.bannerUrl}" target="_blank"><img style="width:150px; height:70px" src="${value.bannerUrl}" alt="${value.bannerUrl}"/></a>
                                        </td>
                                        <td rowspan="${rowspan?c}">
                                        	<@hrefipaplist rootSimple.downloadUrl!''/>
                                        </td>                                        
                                        <td rowspan="${rowspan?c}">
                                        	<button class="btn btn-danger groupdown" title = "${rootSimple.name!}" root_id = "${value.rootId?c}" <#if para.status == -1>disable</#if>>                                        	
                                        		所有渠道停止
                                			</button>
                                        </td>
                                        <#if value.banners??>
                                        	<#list value.banners as banner>
                                        		<#if banner_index == 0>
	                                        		<td>${ banner.rank! }</td>
			                                        <td>${ banner.channel! }</td>
			                                        <td>
			                                        	<#if banner.status == 0>
			                                    			<span id="status_span_${banner.id}" class="btn btn-success" disabled>正常</span>
			                                    		<#else>
			                                    			<span id="status_span_${banner.id}" class="btn btn-danger" disabled>删除</span>
			                                    		</#if>
			                                        </td>
			                                        <td>
			                                            <#if banner.startTime ??>
			                                            ${ banner.startTime?string("yyyy-MM-dd HH:mm:ss") }
			                                            <#else>
			                                                -
			                                            </#if>
			                                        </td>  
			                                        <td>
			                                            <#if banner.endTime ??>
			                                            ${ banner.endTime?string("yyyy-MM-dd HH:mm:ss") }
			                                            <#else>
			                                                -
			                                            </#if>
			                                        </td>
			                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
			                                    </#if>
                                        	</#list>
                                        </#if>                                        
                                    </tr>
                                    <#if value.banners??>
                                    	<#if value.banners?size gt 1>
                                    		<#list value.banners as banner>
                                    			<#if banner_index != 0>
                                    				<tr class="gr_${value.rootId?c}">
		                                        		<td>${ banner.rank! }</td>
				                                        <td>${ banner.channel! }</td>
				                                        <td>
				                                        	<#if banner.status == 0>
				                                    			<span id="status_span_${banner.id}" class="btn btn-success" disabled>正常</span>
				                                    		<#else>
				                                    			<span id="status_span_${banner.id}" class="btn btn-danger" disabled>删除</span>
				                                    		</#if>
				                                        </td>
				                                        <td>
				                                            <#if banner.startTime ??>
				                                            ${ banner.startTime?string("yyyy-MM-dd HH:mm:ss") }
				                                            <#else>
				                                                -
				                                            </#if>
				                                        </td>
				                                        <td>
				                                            <#if banner.endTime ??>
				                                            ${ banner.endTime?string("yyyy-MM-dd HH:mm:ss") }
				                                            <#else>
				                                                -
				                                            </#if>
				                                        </td>
				                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
				                                	</tr>
                                    			</#if>
                                    		</#list>
                                    	</#if>
                                    </#if>
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
	    
 		$("#btn_now_promote").bind("click",function(){
 			$("#search_startDateString").val("");
 		});
 		
 		$("button.groupdown").bind("click", function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var rootId = $(this).attr("root_id");
 			var title = $(this).attr("title");
 			var postUrl = "${rc.contextPath}/auth/banner/offlinefrombanner";
 			
	 		bootbox.confirm("是否将应用" + title + "在所有渠道所有时间段的banner停止[不可逆]？", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'rootId': rootId});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("已成功将应用" + title + "在所有渠道所有时间段的banner停止！");
	 					$("tr.gr_"+rootId).remove();
	 				}
	 			});
			});
 			
 		});
		
        $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
            timeFormat: 'yyyy-mm-dd',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1
        });

 
    </script>
</body>
</html>