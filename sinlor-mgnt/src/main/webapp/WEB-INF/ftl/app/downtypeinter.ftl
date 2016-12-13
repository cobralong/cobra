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
            <li>应用下载方式列表</li>
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
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                            	</div>
                                <div class="input-prepend">
                                	<span class="">下载方式</span>
                                     <@spring.formSingleSelect "para.type", allTypes, " " />
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
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/app/adddowntypeintervention" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span4" name="name" type="text" placeholder="应用名称" disabled value="">
                                    <span class="add-on">售价</span>
                                    <input id="input_app_price" class="span"  name="price" type="text" placeholder="应用售价" disabled value="">                                    
                                </div>
                                <br/>
                                <div class="input-prepend">
                                	<span class="">下载方式</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">下载地址</span>
                                    <input class="span5 input" name="url" type="text" placeholder="下载地址" value="">
                            	</div>
                                <button class="btn btn-info add" disabled >
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
                    <p>在<b><font color="green">${para.startTime}</font></b>时间在进行展示的应用列表(总数<font color="red">${para.pager.total}</font>):</p>
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>应用</th>
                                    <th>图标</th>
                                    <th>下载方式</th>
                                    <th>下载地址</th>
                                    <th>状态</th>
                                    <th>修改时间</th>
                                    <th>入库时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<#assign downType = types[value.type?c]>                                    	
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                        <td class="edit_td" pid="${value.id?c}">
                                        	${downType!}
                                        </td>
                                        <td>${ value.url! }</td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="label label-success">√</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="label label-danger" disabled>X</span>
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
                                        	<button id="btn_recover_${value.rootId?c}" class="btn btn-success delete glyphicon glyphicon-trash " reverId="btn_delete_${value.rootId?c}" downType="${downType!}" rootId="${value.rootId?c}" appName="${rootSimple.name!}" status="0" <#if value.status == 0>disabled</#if>>
                                        		恢复                             	
                                			</button>
                                        	<button id="btn_delete_${value.rootId?c}" class="btn btn-danger delete" reverId="btn_recover_${value.rootId?c}" rootId="${value.rootId?c}" downType="${downType!}" rootId="${value.rootId?c}" appName="${rootSimple.name!}" status="-1" <#if value.status != 0>disabled</#if>>
                                        		删除
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
 		$("input.search").bind("blur", function(){
 			var ctl = $(this);
 			var rid = $(ctl).val();
 			var txtCtl = $("#input_app_name");
 			var priceCtl = $("#input_app_price");
 			var urlCtl = $("#input_app_url");
 			var btn = $("button.add");
 			var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
 			posting.done(function(result){
 				if(result.success){
 					$(txtCtl).val(result.data.name);
 					$(priceCtl).val(result.data.price);
 					$(btn).removeAttr("disabled");
 				}else{
 					$(txtCtl).val(result.message);
 					$(priceCtl).val();
					$(btn).attr("disabled", "disabled");
 				} 				
 			});
 		});
 		$("button.queryToggle").bind("click", function(){
 			$("#div_query").toggle();
 		});
 		
 		$("button.addToggle").bind("click", function(){
 			$("#div_add").toggle();
 		});
 		$("#btn_now_promote").bind("click",function(){
 			$("#search_startDateString").val("");
 		});
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var rootId = $(this).attr("rootId");
 			var reverId = $(this).attr("reverId");
 			var reverIdCtl = $("#" + reverId);
 			var appName = $(this).attr("appName");
 			var status = $(this).attr("status");
 			var downType = $(this).attr("downType");
 			var postUrl = "${rc.contextPath}/auth/app/modifydowntypeintervention.json"; 			
 			var action = status == 0 ? "恢复":"删除";
 			
	 		bootbox.confirm("是否"+action+"应用"+appName+"的"+downType+"下载干预?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'rootId': rootId, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(reverIdCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					alert("操作成功，已"+action+"应用"+appName+"的"+downType+"下载干预!");
	 				}
	 			});
			});
		}); 
    </script>
</body>
</html>