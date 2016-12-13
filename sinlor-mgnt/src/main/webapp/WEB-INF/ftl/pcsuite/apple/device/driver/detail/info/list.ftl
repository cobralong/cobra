<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手苹果设备通用驱动明细列表</title> <#include
"lib/base_source.ftl"> <#include "ftl_macro.ftl">
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
				<li>PC助手苹果设备通用驱动明细列表</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div class="well form-inline">
							<div class='main-action'>
								<#if info??>
								<div class="input-prepend">
									<span class="add-on">Id:</span> <input class="span2" name="id"
										readonly="readonly" value="${info.id}">
								</div>
								<div class="input-prepend">
									<span class="add-on">名称：</span> <input class="span5"
										readonly="readonly" value="${info.name}">
								</div>
								<div class="input-prepend">
									<span class="add-on">说明：</span> <input class="span3"
										readonly="readonly" value="${info.desc}">
								</div>
								<div class="input-prepend">
									<span class="add-on">适用CPU架构：</span> <input class="span2"
										name="arch" readonly="readonly" value="${info.arch}">
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">文件下载地址：</span> <input class="span9"
										readonly="readonly" value="${info.url}">
								</div>
								<div class="input-prepend">
									<span class="add-on">文件md5：</span> <input class="span3"
										readonly="readonly" value="${info.md5}">
								</div>
								</#if> <#if errMsg> <font color="red">查询数据失败，原因：${errMsg}</font>
								</#if>
							</div>
						</div>
						<div class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth/pcsuite/apple/device/driver/detail/info/add">
									<input class="span2" name="driverId" type="hidden"
										value="${info.id}">
									<div class="input-prepend">
										<span class="add-on">文件名</span> <input class="span2"
											name="name" type="text" placeholder="文件名" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">文件控制面板版本号</span> <input class="span2"
											name="version" type="text" placeholder="版本号" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">安装参数</span> <input class="span2"
											name="installParam" type="text" placeholder="/qn" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">控制面板名称</span> <input class="span2"
											name="installDesc" type="text" placeholder="控制面板名称" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">安装顺序</span> <input class="span2"
											name="installOrder" type="text" placeholder="数字,越小越早安装"
											value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">卸载字段</span> <input class="span4"
											name="uninstallStr" type="text" placeholder="MsiExec.exe /I{83CAF0DE-8D3B-*****} 注册表中数据"
											value="">
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>添加
									</button>
								</form>
								<#if addErrMsg> <font color="red">添加失败，原因：${addErrMsg}</font>
								</#if>
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
													<th>名称</th>
													<th>版本号</th>
													<th>安装参数</th>
													<th>安装顺序</th>
													<th>卸载字段</th>
													<th>控制面板名称</th>
												</tr>
											</thead>
											<tbody class="sortable">
												<#if datas??> <#list datas as data>
												<tr>
													<td>${data.id}</td>
													<td>${data.name}</td>
													<td class="edit_td" default_id="${data.id?c}"
														property="version" driver_name="${data.installDesc}"
														for_class="span1"><label for="name" class="span1"><span
															id="span_version_${data.id?c}" class="text-info"
															preValue="${data.version!}">${ data.version! }</span></label></td>
													<td class="edit_td" default_id="${data.id?c}"
														property="installParam" driver_name="${data.installDesc}"
														for_class="span1"><label for="name" class="span1"><span
															id="span_installParam_${data.id?c}" class="text-info"
															preValue="${data.installParam!}">${
																data.installParam! }</span></label></td>
													<td class="edit_td" default_id="${data.id?c}"
														property="installDesc" driver_name="${data.installDesc}"
														for_class="span1"><label for="name" class="span1"><span
															id="span_installOrder_${data.id?c}" class="text-info"
															preValue="${data.installOrder!}">${
																data.installOrder! }</span></label></td>
													<td class="edit_td" default_id="${data.id?c}"
														property="uninstallStr" driver_name="${data.uninstallStr}"
														for_class="span1"><label for="name" class="span1"><span
															id="span_uninstallStr_${data.id?c}" class="text-info"
															preValue="${data.uninstallStr!}">${
																data.uninstallStr! }</span></label></td>
													<td class="edit_td" default_id="${data.id?c}"
														property="installDesc" driver_name="${data.installDesc}"
														for_class="span3"><label for="name" class="span3"><span
															id="span_installDesc_${data.id?c}" class="text-info"
															preValue="${data.installDesc!}">${
																data.installDesc! }</span></label></td>
												</tr>
												</#list> </#if>
											</tbody>
										</table>
									</form>
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
		$('.edit_td')
				.click(
						function() {
							var id = $(this).attr("default_id");
							var property = $(this).attr("property");
							var spanCtr = $("#span_" + property + "_" + id);
							var preValue = $(spanCtr).attr("preValue");
							var driverName = $(this).attr("driver_name");
							var spanClass = $(this).attr('for_class')
							var input = $('<input id="attribute" type="text" class="' + spanClass + '" preValue="' + preValue + '" value="' + preValue + '" />')
							$(spanCtr).text('').append(input);
							input.select();
							input
									.blur(function() {
										var curValue = $('#attribute').val();
										if (curValue == preValue) {
											var text = $('#attribute').val();
											$('#attribute').parent().text(
													preValue);
											$('#attribute').remove();
											return;
										}
										if (curValue == "") {
											$(this).val(preValue);
											$(this).focus();
											return;
										}
										var typeName = (property == "installParam") ? "安装参数"
												: (property == "installOder") ? "安装顺序"
														: "应用面板名称";
										bootbox
												.confirm(
														"将驱动【" + driverName
																+ "】的"
																+ typeName
																+ "由【"
																+ preValue
																+ "】改为【"
																+ curValue
																+ "】?",
														function(result) {
															if (result) {
																var postUrl = "${rc.contextPath}/auth/pcsuite/apple/device/driver/detail/info/modify.json";
																var postData = "id="
																		+ id
																		+ "&"
																		+ property
																		+ "="
																		+ curValue;
																var posting = $
																		.post(
																				postUrl,
																				postData);
																posting
																		.done(function(
																				errMsg) {
																			if (errMsg) { //
																				//show msg	 					
																				alert("操作失败,请联系该死的开发人员.Msg:"
																						+ errMsg);
																				$(
																						'#attribute')
																						.parent()
																						.text(
																								preValue);
																				$(
																						'#attribute')
																						.remove();
																			} else {
																				alert("修改成功.");
																				$(
																						'#attribute')
																						.parent()
																						.attr(
																								"preValue",
																								curValue);
																				$(
																						'#attribute')
																						.parent()
																						.text(
																								curValue);
																				$(
																						'#attribute')
																						.remove();
																			}
																		});
															} else {
																$('#attribute')
																		.parent()
																		.text(
																				preValue);
																$('#attribute')
																		.remove();
															}
														});
									});
						});
	</script>
</body>
</html>