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
            <li>第三方企业签名列表</li>
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
                                    <th>签名</th>
                                    <th>UUID</th>
                                    <th>公司</th>
                                    <th>已有ipa包数</th>
                                    <th>损坏ipa包数</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<input id = "hidden_${value.id}" type="hidden" intervene="${value.intervene}" plist_version="${value.shortVersion}" app_version="${value.version}" plist_id="${value.id}" root_id = "${value.rootId}" app_title = "${value.appName!''}"></input>    
                                    	<td>
	                                    	${value.certSerial!''}
                                        </td>
                                        <td>
                                        ${value.uuid!''}
                                        </td>
                                        <td>
                                        	${value.teamName!''}
                                        </td>
                                        <td>
                                        	<a href="${rc.contextPath}/auth/enterprise/signatureipas?certSerial=${value.certSerial!''}"> ${value.ipas?c}</a>
                                        </td>
                                        <td>
                                        	<a href="${rc.contextPath}/auth/enterprise/signatureipas?status=-1&certSerial=${value.certSerial!''}"> ${value.ipasBanned?c}</a>
                                        </td>
                                        <td>
                                        	<#if value.status == -1>
                                        		<button class="btn btn-success signdel glyphicon glyphicon-trash sigdel" signatures = "${value.certSerial}" disabled>
                                        			已禁用
                                        		</button>
                                        	<#else>
	                                        	<button class="btn btn-success signdel glyphicon glyphicon-trash sigdel" signatures = "${value.certSerial}">
	                                        		禁用                                       	
	                                			</button>
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

        $("button.sigdel").bind("click",function(){
            event.preventDefault();
            bootbox.setDefaults({
                locale: "zh_CN"
            });
            var eventCtl = $(this);
            var signatures = $(this).attr("signatures");
            var postUrl = "${rc.contextPath}/auth/enterprise/sigdisable.json";

            bootbox.confirm("是否禁用签名"+signatures+"?", function(result) {
                if(!result){
                    return;
                }
                var posting = $.post(postUrl, {'certSerial': signatures});
                posting.done(function(errMsg){
                    if(errMsg){//
                        //show msg
                        alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
                    }else{
                        alert("操作成功，已将签名"+signatures+"禁用");
                        $(eventCtl).html("已禁用")
                        $(eventCtl).attr("disabled","disabled");
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