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
            <li>可下载应用列表</li>
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
	                                <div class="input-prepend">
	                                	<span class="">名称</span>
	                                	<input class="span2 search" name="name" type="text" placeholder="名称" value="">
	                                </div>
                                	<span class="">苹果Id</span>
                                	<input class="span2 search" name="itemId" type="text" placeholder="itemId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">包名</span>
                                	<input class="span2 search" name="bundleId" type="text" placeholder="bundleId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">RootId</span>
                                	<input class="span2 search" name="rootId" type="text" placeholder="rootId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">分类</span>
                                     <@spring.formSingleSelect "para.categoryId", cates, " " />
                                </div>
                                <br/>
                                <div class="input-prepend">
                                	<span class="">类型</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">干预</span>
                                     <@spring.formSingleSelect "para.intervene", intervenes, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">价格 &gt;</span>
	                                <input class="span1" name="price" type="text" placeholder="价格" value="${para.price}">
                                </div>
                                <div class="input-prepend">
                                     <@spring.formSingleSelect "para.orderBy", orderBys, " " />
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
                                    <th>苹果Id</th>
                                    <th>应用名</th>
                                    <th>应用包名</th>
                                    <th>分类</th>
                                    <th>版本号(当前库版本)</th>
                                    <th>价格</th>
                                    <th>下载地址/企业签名</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                    <th>入库时间</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	 <#if value.categoryId??>
                                    	 	<#assign cateName = cates[value.categoryId?c]>
                                    	 </#if>
                                    	<#if value.itemId??>
                                    		<#assign simple = itemIdRootSimpleMap[value.itemId?c]>
                                    	</#if>
                                    	<#assign pnsimple = bundleIdRootSimpleMap[value.bundleId!'']>
                                    	<input id = "hidden_${value.id}" type="hidden" intervene="${value.intervene}" plist_version="${value.shortVersion}" app_version="${simple.version}" plist_id="${value.id}" root_id = "${value.rootId}" app_title = "${value.appName!''}"></input>    
                                    	<td>
	                                    	<#if value.itemId??>
	                                    		<@machrefapp value.itemId, value.appName/>	
	                                    	</#if>                                        	
                                        </td> 
                                		<#if value.rootId??>
                                			 <td>
	                                        	<@hrefapp value.rootId, value.appName/>
	                                        	<a class="btn btn-info" target="_blank" href="${rc.contextPath}/auth/app/detail?rootId=${value.rootId}">详情</a>
	                                        </td>
                                		<#else>
                                			<td></td>
                                		</#if>
                                        <td>${ value.bundleId! }</td>
                                        <td>
                                        	<#if value.categoryId??>
                                        		${cateName}                                        		
                                        	</#if>
                                        </td>
                                        <td>${ value.shortVersion! }
	                                        <#if simple?? && simple!='' &&  simple.version??>
	                                        	<#if simple.version == value.shortVersion>
		                                       		(<font color='green'>${simple.version}</font>)
		                                       	<#else>
		                                       		(<font color='red'>${simple.version}</font>)
		                                       	</#if>
	                                    	</#if>
                                        </td>
                                        <td>
                                        	<#if value.price != 0>
                                        		<font color="red">${ value.price! }</font>
                                        	<#else>
                                        		<font color="green">${ value.price! }</font>
                                        	</#if>
                                        </td>
                                        <td>
                                        	<@hrefipaplist value.enterprisePlistUrl!''/>
	                                      	</br>
                                        	<@hrefsignatures value.certSerial!/>
                                        </td>             
                                        <td>
                                        	<button id="btn_diable_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " status = "${value.status}"  plist_id = "${value.id}" <#if value.status == -1>disabled</#if>>
                                        		不可用                                        	
                                			</button>
                                        	<button id="btn_able_${value.id}" class="btn btn-danger delete" status = "${value.status}"  plist_id = "${value.id}" <#if value.status == 0>disabled</#if>>
                                        		可用
                                			</button>
                                        </td>
                                        <td>
                                        	<button id="btn_intervene_${value.id}" class="btn btn-success intervene glyphicon glyphicon-trash " plist_id = "${value.id}" <#if value.intervene == 1>disabled</#if>>
                                        		干预                                       	
                                			</button>
                                        	<button id="btn_intervene_${value.id}" class="btn btn-danger intervene"  plist_id = "${value.id}" <#if value.intervene == 0>disabled</#if>>
                                        		不干预
                                			</button>
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
	    
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var plistId = $(this).attr("plist_id");
 			var hiddenCtl = $("#hidden_" + plistId);
 			var trCtl = $("#tr_"+plistId);
 			var appTitle = $(hiddenCtl).attr("app_title");
 			var preStatus = $(this).attr("status");
 			var action = preStatus == 0 ? "不可用":"可用";
 			var able = preStatus == 0 ?"false":"true";
 			var postUrl = "${rc.contextPath}/auth/enterprise/able.json";
 			
	 		bootbox.confirm(appTitle+"的企业下载地址"+action+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'plistId': plistId, "able": able});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("操作成功，已将应用"+appTitle+"id为"+plistId+"企业下载地址置为"+action);
	 					$(trCtl).remove();
	 				}
	 			});
			});
		});
		
 		$("button.intervene").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var plistId = $(this).attr("plist_id");
 			var hiddenCtl = $("#hidden_" + plistId);
 			var trCtl = $("#tr_"+plistId);
 			var version = $(hiddenCtl).attr("plist_version");
 			var appVersion = $(hiddenCtl).attr("app_version");
 			var appTitle = $(hiddenCtl).attr("app_title");
 			var preIntervene = $(hiddenCtl).attr("intervene");
 			var action = preIntervene == 0 ? "干预":"不干预";
 			var intervene = preIntervene == 0 ?"true":"false";
 			var postUrl = "${rc.contextPath}/auth/enterprise/intervene.json";
 			
	 		bootbox.confirm(action + "版本为" + version + "的下载地址干预" + appTitle + "(" + appVersion + ")的企业下载地址?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'plistId': plistId, "intervene": intervene});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("操作成功，已" + action + "版本为" + version + "的下载地址干预" + appTitle + "(" + appVersion + ")的企业下载地址");
	 					$(trCtl).remove();
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