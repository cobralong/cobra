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
            <li>搜索默认结果列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<button class="btn queryToggle  btn-info">
                		<span class="glyphicon glyphicon-search">查询</span>
                	</button>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="">渠道</span>
                                     <@spring.formSingleSelect "para.channel", channels, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>查询
                                </button>
                            </form>
                        </div>
                    </div>
                    <br/>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/search/defaultresult/add" method="post">		                      
                                <div class="input-prepend">
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                            	</div>
                            	<div class="input-prepend">
                                    <span class="add-on">授权应用</span>
                                    <input id="input_app_auth_status" class="span2" style="width:200px" name="authStatus" type="text" placeholder="应用授权状态" disabled value="">
                                </div>
                            	<br/>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="name" type="text" placeholder="应用名称" disabled value="">
                                </div>
                                <br/>
                            	<div class="input-prepend">
                                	<span class="">渠道</span>
                                     <@spring.formSingleSelect "para.channel", allChannels, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">位置</span>
                                    <input class="span1" size="10" name="rank" type="text" placeholder="位置" value="">                                   
                                </div>			
                                <button class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                	<#if para.errMsg??>
                                		<font>${para.errMsg}</font>
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
                                    <th>名称</th>
                                    <th>图标</th>
                                    <th>渠道</th>
                                    <th>rank</th>
                                    <th>授权应用</th>
                                    <th>状态</th>
                                    <th>更新时间</th>
                                    <th>新增时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<#assign statusRespMap = statusRespMaps[value.rootId?c]>
                                    	<input id="hidden_${value.id}" type="hidden" app_title="${rootSimple.name}" app_channel="${value.channel}" cur_status="${value.status}"/>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                    	<td>${value.channel}</td>
                                    	<td>${value.rank}</td>
                                    	<td>
	                                        <#if statusRespMap.download==true>
	                                    		<span id="auth_status_span_${value.id}" class="btn btn-success" disabled>可下载</span>
	                                    	<#elseif statusRespMap.bought==true>
	                                    		<span id="auth_status_span_${value.id}" class="btn btn-warning" disabled>已购买/下载异常</span>
	                                    	<#else>
	                                    		<span id="auth_status_span_${value.id}" class="btn btn-danger" disabled>未购买</span>
	                                        </#if>
	                                    </td>
                                    	 <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success">正常</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>删除</span>
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
                                        <td>
                                        	<button id="promote_btn_${value.id}" value_id = "${value.id}" value_desc="${rootSimple.name}" channel="${value.channel}" status="${value.status}" class="btn btn-success modify glyphicon glyphicon-trash ">
	                                        	<#if value.status == 0>
	                                        		删除
	                                        	<#else>
	                                        		使用
	                                        	</#if>                                        		                                      	
                                			</button>
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
 		$("button.queryToggle").bind("click", function(){
 			$("#div_query").toggle();
 		});
 		
 		$("button.addToggle").bind("click", function(){
 			$("#div_add").toggle();
 		});
 		
 		$("input.search").bind("blur", function(){
 			var ctl = $(this);
 			var rid = $(ctl).val();
 			var txtCtl = $("#input_app_name");
 			var txtStatus = $("#input_app_auth_status");
 			var btn = $("button.add");
 			var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
 			posting.done(function(result){
 				if(result.success){
 					$(txtCtl).val(result.data.name);
 					if(result.data.authDownload){
 						$(txtStatus).val("可下载");
 					}else if(result.data.bought){
 						$(txtStatus).val("已购买/下载异常");
 					}else{
 						$(txtStatus).val("未购买");
 					}
 					$(btn).removeAttr("disabled");
 				}else{
 					$(txtCtl).val(result.message);
 					$(txtStatus).val("id无效,状态未知");
					$(btn).attr("disabled", "disabled");
 				} 				
 			});
 		});
 		
 		$("button.modify").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = eventCtl.attr("value_id");
 			var value = eventCtl.attr("value_desc");
 			var channel = eventCtl.attr("channel");
 			var preStatus = eventCtl.attr("status");
 			var postUrl = "${rc.contextPath}/auth/search/defaultresult/modify";
 			var action = preStatus == 0 ? "不再":"";
 			var setStatus = preStatus == 0 ? -1 : 0; 			
 			var trCtl = $("#tr_"+id);
 			
	 		bootbox.confirm("是否在"+channel+"渠道上将【"+value+"】"+action+"设置为默认搜索结果?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("操作成功，已"+action+"推荐");
	 					trCtl.remove();
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