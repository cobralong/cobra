<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-苹果授权服务列表</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>苹果授权服务列表</li>
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
			                               	<span class="add-on">状态</span>
			                               	<@spring.formSingleSelect "para.status", status, " " />
		                               </div>
		                                <button class="btn btn-info query" onClick="modifyPage()">
		                                    <i class="icon-search icon-white"></i>过滤
		                                </button>
		                            </form>
		                        </div>
		                    </div>
	                        <#if addMsg>
	                        	<font color="red">添加失败，原因：${addMsg}</font> 
	                        </#if>
		                    <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/authorizer/pc/server/info/add"  method="post">
		                                <div class="input-prepend">
		                                	<span class="add-on">机器名称：</span>
		                                	<input class="span4" name="osName" type="text" placeholder="os_name" value="${para.osName}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器Guid：</span>
		                                	<input class="span4" name="osGuid" type="text" placeholder="os_guid" value="${para.osGuid}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器kMachineA：</span>
		                                	<input class="span4" name="kmachineIda" type="text" placeholder="kMachineA" value="${para.kmachineIda}">
		                                </div>
		                                <br/><br/>
		                                <div class="input-prepend">
		                                	<span class="add-on">机器kMachineB：</span>
		                                	<input class="span4" name="kmachineIdb" type="text" placeholder="kMachineB" value="${para.kmachineIdb}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">Url：</span>
		                                	<input class="span4" name="url" type="text" placeholder="http://" value="${para.url}">
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
		                                    <th>Id</th>
		                                    <th>机器名称</th>
		                                    <th>机器Guid</th>
		                                    <th>机器kMachineA</th>
		                                    <th>机器kMachineB</th>
		                                    <th>Url</th>
		                                    <th>状态</th>
		                                    <th>操作</th>
		                                    <th>已授权账号</th>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
		                                    <tr>
		                                        <td>${value.id?c}</td>
		                                        <td>${value.osName}</td>
		                                        <td><@shortdesc value.osGuid!/></td>
		                                        <td><@shortdesc value.kmachineIda!/></td>
		                                        <td><@shortdesc value.kmachineIdb!/></td>
		                                        <td>${value.url}</td>
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
			                                    <td>
			                                    	<a class="btn btn-info query" href="${rc.contextPath}/auth/appleaccount/authorizer/auth/pc/server/info/list?pcServerId=${value.id?c}">已信任授权账号列表</a>
			                                    </td>
		                                    </tr>
		                                    </#list>
		                                </#if>
		                                </tbody>
		                            </table>
		                        </form>
	                    		<div class="pagination" id="itemPage">
	                    	</div>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
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
			$("span.delete").bind("click",function(){
		 			event.preventDefault();
		 			bootbox.setDefaults({
		 				locale: "zh_CN"
		 			});
		 			var eventCtl = $(this);
		 			var id = $(this).attr("default_id"); 			
		 			var preStatus = $(this).attr("status");
		 			var status = preStatus == 0 ? -1 : 0;
		 			var postUrl = "${rc.contextPath}/auth/authorizer/pc/server/info/modify.json";
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
			 			posting.done(function(resp){
			 				if(!resp.data){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);
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