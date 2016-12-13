<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-苹果设备列表</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>苹果设备列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/client/apple/device/add" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">平台型号</span>
                             		<select name="platform" class="search_platform">		                            	
										<#list platforms?keys as key>
											<#if key == para.platform>
	                                			<option value="${key}" selected>${platforms[key]}----${key}</option>
	                                		<#else>
	                                			<option value="${key}">${platforms[key]}-----${key}</option>
	                                		</#if>
	                            		</#list>
                                	</select>
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">设备颜色</span>
                                    <input id="input_app_name" class="span2" name="color" type="text" placeholder="设备颜色" value="">
                                </div>
                                <button class="btn btn-info add" >
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
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
	                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
	                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
	                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>平台型号</th>
                                    <th>颜色</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>                      	
                                        <td>
                                        	${platforms[value.platform]}----${value.platform}
                                        </td> 
                                        <td>
                                        	${value.color}
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                        		<font id="status_font_${data.id}" color="green">√</font>
                                    		<#else>
                                    			<font id="status_font_${data.id}" color="red">X</font>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span default_id = ${data.id} status = "${value.status}" class="btn delete btn-danger">删除</span>
                                    		<#else>
                                    			<span default_id = ${data.id} status = "${value.status}" class="btn delete btn-success">正常</span>
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
     <script>
         $(document).ready(function () {
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination", "#itemForm", currPageDiv, totalDiv, sizeDiv);
	    });
 		$("button.defaultdelete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var id = $(this).attr("defaultId");
 			var reverId = $(this).attr("reverId");
 			var reverIdCtl = $("#" + reverId);
 			var platform = $(this).attr("platform");
 			var defaultStatus = $(this).attr("defaultStatus"); 			 			
 			var action = defaultStatus == 1 ? "":"不再";
 			var type = 1;//1 platform 2:osversion;
 			var postUrl = "${rc.contextPath}/auth/client/modifyplatformosversion";
 			var infoMsg="将设备型号" + platform + action + "设为默认设备型号";
	 		bootbox.confirm("是否" + infoMsg + "?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {"id": id, "type": type, "defaultStatus": defaultStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(reverIdCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					alert("操作成功，已"+ infoMsg + "!");
	 				}
	 			});
			});
		}); 
		
 		$("span.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id"); 			
 			var preStatus = $(this).attr("status");
 			var status = preStatus == 0 ? -1 : 0;
 			var postUrl = "${rc.contextPath}/auth/pcsuite/programmer/driver/modify.json";
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
    </script>
</body>
</html>