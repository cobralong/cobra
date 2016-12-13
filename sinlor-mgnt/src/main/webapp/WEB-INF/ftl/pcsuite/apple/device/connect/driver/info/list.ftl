<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手连接苹果设备驱动列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>PC助手连接苹果设备驱动列表</li>
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
									<input type="hidden" id="pager_page" name="pager.page"
										value="${para.pager.page}"> <input type="hidden"
										id="pager_size" name="pager.size" value="${para.pager.size}">
									<input type="hidden" id="pager_total" name="pager.total"
										value="${para.pager.total}">
									<div class="input-prepend">
										<div class="input-prepend">
											<span class="add-on">系统版本</span><select name="system">
												<#list windows?keys as key> <#if para.system == key>
												<option selected value="${key}">${windows[key]}</option>
												<#else>
												<option value="${key}">${windows[key]}</option> </#if>
												</#list>
											</select>
										</div>
										<div class="input-prepend">
											<select name="arch"> <#list arches?keys as key> <#if
												para.arch == key>
												<option selected value="${key}">${arches[key]}</option>
												<#else>
												<option value="${key}">${arches[key]}</option> </#if>
												</#list>
											</select>
										</div>
										<div class="input-prepend">
											<span class="add-on">状态</span> <select name="status">
												<#list status?keys as key> <#if para.status == key>
												<option selected value="${key}">${status[key]}</option>
												<#else>
												<option value="${key}">${status[key]}</option> </#if>
												</#list>
											</select>
										</div>
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth/pcsuite/apple/device/connect/driver/info/add"
									enctype="multipart/form-data" method="post">
									<div class="input-prepend">
										<span class="add-on">系统版本</span> <select name="system">
											<#list pureWindows?keys as key>
											<option value="${key}">${pureWindows[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">系统架构</span> <select name="arch">
											<#list pureArches?keys as key>
											<option value="${key}">${pureArches[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">驱动</span> <select class="span4"
											name="driverId"> <#list drivers as driver>
											<#if driver.status == 0>
												<option value="${driver.id?c}">${driver.name}---${driver.version}----${driver.arch}</option>
											</#if>
											</#list>
											<option value="9999">所有驱动</option>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">默认驱动</span><select name="defaultFlag">
											<#list defaultFlags?keys as key>
											<option value="${key}">${defaultFlags[key]}</option> </#list>
										</select>
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>添加
									</button>
								</form>
								<#if errMsg> <font color="red">添加失败，原因：${errMsg}</font> </#if>
							</div>
						</div>
						<div class="table-content">
							<#-- table -->
							<h4>
								当前有记录<font color="green">${total}</font>条
								<h4>
									<form id="itemForm" name="itemForm" action="" method="post">
										<table
											class="table table-striped table-bordered table-condensed"
											id="itemTable">
											<thead>
												<tr>
													<th>Id</th>
													<th>系统版本</th>
													<th>支持架构</th>
													<th>驱动名称</th>
													<th>默认驱动</th>
													<th>状态</th>
													<th>默认变更</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody class="sortable">
												<#if datas??> <#list datas as data>
												<tr id="tr_${data.id?c}">
													<td>${data.id?c}</td>
													<td>${data.system! }----${data.systemDesc}</td>
													<td>${data.arch! }</td>
													<td><#list drivers as driver> <#if driver.id ==
														data.driverId> <a class="btn btn-info query"
														href="${rc.contextPath}/auth/pcsuite/apple/device/driver/detail/info/list?driverId=${data.driverId}">${driver.name}---${driver.itunesVersion}</a>
														</#if> </#list>
													</td>
													<td><#if data.defaultFlag == 1> <font
														id="status_defaultFlag_${data.id}" color="green">√</font>
														<#else> <font id="status_defaultFlag_${data.id}"
														color="red">X</font> </#if>
													</td>
													<td><#if data.status == 0> <font
														id="status_font_${data.id}" color="green">√</font> <#else>
														<font id="status_font_${data.id}" color="red">X</font>
														</#if>
													</td>
			                                        <td>
			                                        	<button id="btn_default_${data.id?c}" rever_ctrl="btn_normal_${data.id?c}" 
			                                        		class="btn btn-success defaultdelete" default_id="${data.id?c}" 
			                                        		set_value="1" <#if data.defaultFlag == 1>disabled</#if>>
			                                        		设为默认
			                                			</button>
			                                        	<button id="btn_normal_${data.id?c}" rever_ctrl="btn_default_${data.id?c}" 
			                                        		 class="btn btn-danger defaultdelete" default_id="${data.id?c}" 
			                                        		set_value="0" <#if data.defaultFlag != 1>disabled</#if>>
			                                        		取消默认
			                                			</button>
		                                			</td>
													<td><#if data.status == 0> <span default_id=${data.id}
														set_status="-1" class="btn delete btn-danger">删除</span>
														<#else> <span default_id=${data.id} set_status="0"
														class="btn delete btn-success">正常</span> </#if>
													</td>
												</tr>
												</#list> </#if>
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
	<script type="text/javascript"
		src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript"
		src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
	<script>
		$(document).ready(
				function() {
					var currPageDiv = "#pager_page";
					var totalDiv = "#pager_total";
					var sizeDiv = "#pager_size";
					$.manage.pageList(".pagination", "#searchForm",
							currPageDiv, totalDiv, sizeDiv);
				});

 		$("button.defaultdelete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var id = $(this).attr("default_id");
 			
 			var defaultFlag = $(this).attr("set_value");
 			var postUrl = "${rc.contextPath}/auth/pcsuite/apple/device/connect/driver/info/modify";
 			if(defaultFlag == 1){
 				var infoMsg="将Id：" + id + "对应的数据设为默认驱动";
 			}else{
 				var infoMsg="不再将Id：" + id + "对应的数据设为默认驱动?";
 			}
 			var defaultFlagTd = $("#status_defaultFlag_" + id);
 			var reverCtrlId = $(this).attr("rever_ctrl");
 			var reverCtrl = $("#" + reverCtrlId);
 			var fontCtrl=$("#status_defaultFlag_" +  id);
	 		bootbox.confirm("是否" + infoMsg + "?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {"id": id, "defaultFlag": defaultFlag});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(reverCtrl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					if(defaultFlag == 1){
	 						$(fontCtrl).removeAttr("color");
	 						$(fontCtrl).attr("color", "green");
	 						$(fontCtrl).html("√");
	 					}else{
	 						$(fontCtrl).removeAttr("color");
	 						$(fontCtrl).attr("color", "red");
	 						$(fontCtrl).html("X");
	 					}
	 					alert("操作成功!");
	 				}
	 			});
			});
		}); 
		$("span.delete")
				.bind(
						"click",
						function() {
							event.preventDefault();
							bootbox.setDefaults({
								locale : "zh_CN"
							});
							var id = $(this).attr("default_id");
							var setStatus = $(this).attr("set_status");
							var trCtrl = $("#tr_" + id);
							var statusDesc = setStatus == -1 ? "删除" : "正常";
							bootbox
									.confirm(
											"确认将记录【" + id + "】置为" + statusDesc
													+ "状态？",
											function(result) {
												if (!result) {
													return;
												}
												var postUrl = "${rc.contextPath}/auth/pcsuite/apple/device/connect/driver/info/modify";
												var postData = "id=" + id
														+ "&status="
														+ setStatus;
												var posting = $.post(postUrl,
														postData);
												posting
														.done(function(errMsg) {
															if (errMsg) { //
																//show msg	 					
																alert("操作失败,请联系该死的开发人员.Msg:"
																		+ errMsg);
															} else {
																alert("修改成功.");
																$(trCtrl)
																		.remove();
															}
														});
											});
						});
	</script>
</body>
</html>