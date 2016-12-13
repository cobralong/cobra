<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
	    <title>应用汇-管理后台-H5小游戏</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
	<div class='whole-container'>
	<#include "/lib/store_header.ftl">
	    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
	    <div>
	        <ul class="breadcrumb">
	            <li>AppStore管理后台</li>
	            <span class="divider">/</span>
	            <li>H5小游戏管理列表</li>
	        </ul>
	    </div>
	    <div class="container-fluid">
	        <div class="row-fluid">
	            <div class="container span12">
	            <#include "lib/alert.ftl">
	                <div class='main-content'>
		                <button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
		                    <div id="div_search" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="searchForm" name="searchForm" action="" method="post">
		                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
				                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
				                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
				                        <div class="input-prepend">
		                                    <span class="add-on">推荐客户端</span>
		                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
	                               		</div>
		                                <button class="btn btn-info search" >
		                                    <i class="icon-search icon-white"></i>查询
		                                </button>
		                            </form>
		                        </div>
		                    </div>
		                     <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/store/h5game/add" enctype="multipart/form-data"  method="post">
				                        <div class="input-prepend">
		                                    <span class="add-on">推荐客户端</span>
		                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
	                               		</div>
				                        <div class="input-prepend">
		                                    <span class="add-on">H5游戏名称</span>
		                             		<input type="input" name="name" value="${para.title}"/>
	                               		</div>
				                        <div class="input-prepend">
		                                    <span class="add-on">H5游戏描述</span>
		                             		<input type="input" name="desc" value="${para.title}"/>
	                               		</div>
				                        <div class="input-prepend">
		                                    <span class="add-on">H5游戏地址</span>
		                             		<input type="input" class="span3" name="url" value="${para.title}"/>
	                               		</div>
	                               		<div class="fileinput fileinput-new" data-provides="fileinput">
			                                <div class="fileinput-new thumbnail" style="width: 72px; height: 72px;">
			                                    <img data-src="holder.js/100%x100%" alt="...">
			                                </div>
		                                	<div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 72px; max-height: 72px	;"></div>
			                                <div>
			                                    <span class="btn btn-default btn-file">
			                                    	<span class="fileinput-new">Select image</span>
			                                    	<span class="fileinput-exists">Change</span>
			                                    	<input type="file" name="icon">
			                                    </span>
			                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
			                                </div>
		                        		</div>
			                            <button class="btn btn-info add" >
			                                <i class="icon-add icon-white"></i>添加
			                            </button>
		                            </form>
		                        </div>
		                    </div>
		                </button>
	                    <div class="table-content">
	                    <#-- table -->
	                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
	                            <thead>
		                            <tr>
		                            	<th>Id</th>  
		                            	<th>推荐客户端</th>  
		                                <th>名称</th>                                
		                                <th>地址</th>
		                                <th>描述</th>
		                                <th>点击次数</th>
		                                <th>图标</th>
		                                <th>状态</th>
		                                <th>操作</th>
		                            </tr>
	                            </thead>
	                            <tbody class="sortable">
	                            <#if values??>
	                                <#list values as value>
	                                <tr>
	                                	<td>${value.id}</td>
	                                	<td>
	                                    	${value.bundleId}---${bundleIds[value.bundleId]}
	                                    </td>
	                                    <td>
	                                    	${value.name}
	                                    </td>
	                                    <td>
	                                    	${value.url}
	                                    </td>
	                                    <td>
	                                    	${value.desc}
	                                    </td>
	                                    <td>
	                                    	${value.click}
	                                    </td>
	                                    <td>
	                                    	<img style="width:150px; height:70px" src="${value.icon}"/>
	                                    </td>
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
	                        <div class="pagination" id="itemPage"></div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script>
	    $(document).ready(function () {
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
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
	 			var postUrl = "${rc.contextPath}/auth/store/h5game/modify.json";
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