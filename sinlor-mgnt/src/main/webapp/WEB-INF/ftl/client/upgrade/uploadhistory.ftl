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
            <li>已上传客户端更新列表</li>
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
                                	<span class="">包名</span>
                                	<input class="span2 search" name="bundleId" type="text" placeholder="bundleId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">类型</span>
                                     <@spring.formSingleSelect "para.test", tests, " " />
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
                                    <th>应用包名</th>
                                    <th>ipa渠道</th>
                                    <th>版本(应用版本)</th>
                                    <th>企业签名/plist</th>
                                    <th>更新日期</th>
                                    <th>测试版</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <td>${ value.bundleId! }</td>
                                        <td>${ value.ipaChannel! }</td>
                                        <td>
                                        	${value.shortVersion}(${value.version!''})
                                        </td>
                                        <td>
                                        	<@hrefipaplist value.plist!''/>
	                                      	</br>
                                        	<@hrefsignatures value.sign!/>
                                        </td>
                                        <td>                                        	
                                            <#if value.releaseDate ??>
                                            	${ value.releaseDate?string("MM-dd") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                        	<#if value.test == 0>
                                        		<font color="green">线上版</font>
                                        	<#elseif value.test == 1>
                                        		<font color="red">测试版</font>
                            				<#else>
                                        		<font color="red">X</font>
                                        	</#if>
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                        		<font color="green">√</font>
                            				<#else>
                                        		<font color="red">X</font>
                                        	</#if>
                                        </td>
                                        <td>
                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" defaultId="${value.id?c}" status="${value.status?c}">
                                    			删除
                            				</button>
                                        </td>
                                        <td>
                                        	<a class="btn btn-info query" href="${rc.contextPath}/auth/client/upgrade/detail?id=${value.id}">详情</a>
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
 			var id = $(this).attr("defaultId");
 			var trCtl = $("#tr_"+id);
 			var status = $(this).attr("status");
 			var postUrl = "${rc.contextPath}/auth/client/upgrade/delclientuploadinfo";
 			var statusInfo = status == 0 ? "可用":"不可用";
 			var confirmInfo="是否删除当前记录？此记录当前" + statusInfo;
	 		bootbox.confirm(confirmInfo, function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("操作成功，已删除");
	 					$(trCtl).remove();
	 				}
	 			});
			});
		});
    </script>
    </body>
</html>