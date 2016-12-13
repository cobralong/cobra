<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<title>应用汇-管理后台-共享账号已购应用列表</title>
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
            <li>共享账号已购应用列表</li>
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
                                	<span class="add-on">AppleId</span>
                                	<input name="appleId" value="" type="input"/>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">RootId</span>
                                	<input name="rootId" value="" type="input"/>
                                </div>                                
                               	<div class="input-prepend">
	                               	<span class="add-on">状态</span>
	                               	<@spring.formSingleSelect "para.status", status, " " />
                               	</div>
                                <button class="btn btn-info query" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <#if errMsg>
	                        	<font color="red">添加失败，原因：${errMsg}</font> 
	                        </#if>
		                    <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/appleaccount/shareaccount/buyapp/add" method="post">
		                                <div class="input-prepend">
		                                	<span class="add-on">AppleId：</span>
		                                	<input class="span2" name="appleId" type="text" placeholder="appleId" value="">
		                                </div>
		                                <div class="input-prepend">
                                            <span class="add-on">RootId</span>
                                            <input class="span2 search" name="rootId" type="text" placeholder="RootId" value="">
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">应用名称</span>
                                            <input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="">
                                            <span class="add-on">OTA包</span>
		                                    <input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
		                                    <input id="input_cert" class="span2" style="width:100px" name="name" type="text" placeholder="签名" disabled value="">
                                        </div>
		                                <button class="btn btn-info query" >
		                                    <i class="icon-search icon-white"></i>添加
		                                </button>
		                            </form>
		                        </div>                        
		                    </div>
	            			<br/><br/>
                    <div class="table-content">
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>购买账号Id</th>
                                    <th>应用Id</th>
                                    <th>ItemId</th>
                                    <th>版本</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                        <td>${value.appleId}</td>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td>
                                        <td>${ value.itemId?c }</td>
                                        <td>${ value.version!'' }</td>
	                                    <td>
	                                    	<#if value.status == 0>
	                                    		<font id="status_font_${value.id}" color="green">√</font>
	                                		<#else>
	                                			<font id="status_font_${value.id}" color="red">X</font>
	                                		</#if>
	                                    </td>
	                                    <td>
	                                    	<#if value.status == 0>
	                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-danger">删除</span>
	                                		<#else>
	                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-success">正常</span>
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
    
    
		    
	$("span.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id"); 			
 			var preStatus = $(this).attr("status");
 			var status = preStatus == 0 ? -1 : 0;
 			var postUrl = "${rc.contextPath}/auth/appleaccount/shareaccount/buyapp/modify.json";
 			var confirmMsg = "";
 			if(status == -1){
 				confirmMsg = "删除当前记录";
 			}else{
 				confirmMsg = "恢复当前记录";
 			}
	 		bootbox.confirm(confirmMsg+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{ 					
	 					alert("操作成功，已" + confirmMsg + "!");
	 					var fontCtl = $("#status_font_"+ id);
	 					$(eventCtl).attr("status", status);
	 					if(status == 0){	 					
	 						$(eventCtl).attr("class", "btn delete btn-danger");
	 						$(eventCtl).html("删除");
	 						$(fontCtl).attr("color", "green");
	 						$(fontCtl).html("√");
	 					}else{
	 						$(eventCtl).attr("class", "btn delete btn-success");
	 						$(eventCtl).html("正常");
	 						$(fontCtl).attr("color", "red");
	 						$(fontCtl).html("X");
	 					}
	 				}
	 			});
			});
		});
    
        $("input.search").bind("blur", function () {
            var ctl = $(this);
            var rid = $(ctl).val();
            var txtCtl = $("#input_app_name");
            var btn = $("button.add");
            var posting = $.post("${rc.contextPath}/auth/promote/search", {"id": rid});
            posting.done(function (result) {
                if (result.success) {
                    $(txtCtl).val(result.data.name);                        
					$("#input_plist").val(result.data.downloadUrl);
					$("#input_cert").val(result.data.certSerial);
                    $(btn).removeAttr("disabled");
                } else {
                    $(ctl).focus();
                    $(txtCtl).val(result.message);
                    $(btn).attr("disabled", "disabled");
                }
            });
        });
	</script>
</body>
</html>