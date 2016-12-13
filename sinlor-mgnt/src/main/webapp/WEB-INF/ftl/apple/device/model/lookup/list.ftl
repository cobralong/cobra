<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
<title>应用汇-管理后台-PC助手iPhone模型图【缺失】列表</title> <#include
"lib/base_source.ftl"> <#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>PC助手iPhone模型图【缺失】列表</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div class="table-content">
							<#-- table -->
							<h4>
								当前缺失设备背景图<font color="green">${total}</font>条
							</h4>
							<form id="itemForm" name="itemForm" action="" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>Id</th>
											<th>设备型号</th>
											<th>设备颜色</th>
											<th>发现时间</th>
											<th>操作</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as data>
										<tr id="tr_${data.id}">
											<td>${data.id}</td>
											<td>${data.platform}------${platforms[data.platform]}</td>
											<td>${data.color}------${colors[data.color]}</td>
											<td>${ data.createTime?string("yyyy-MM-dd HH:mm:ss") }</td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/apple/device/model/lookup/fix?id=${data.id?c}">前去添加</a></td>
											<td><span default_id="${data.id}" set_status="-1"
												set_text="删除" class="btn delete btn-danger">删除</span></td>
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
		$("span.delete")
				.bind(
						"click",
						function() {
							event.preventDefault();
							bootbox.setDefaults({
								locale : "zh_CN"
							});
							var eventCtl = $(this);
							var id = $(this).attr("default_id");
							var status = $(this).attr("set_status");
							var postUrl = "${rc.contextPath}/auth/apple/device/model/lookup/modify.json";
							var confirmMsg = "";
							if (status == -1) {
								confirmMsg = "删除当前记录";
							} else {
								confirmMsg = "恢复当前记录";
							}
							var trCtrl = $("#tr_" + id);
							bootbox.confirm(confirmMsg + "?", function(result) {
								if (!result) {
									return;
								}
								var posting = $.post(postUrl, {
									'id' : id,
									"status" : status
								});
								posting.done(function(errMsg) {
									if (errMsg) { //
										//show msg	 					
										alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
									} else {
										alert("操作成功，已" + confirmMsg + "!");
										$(trCtrl).remove();
									}
								});
							});
						});
	</script>
</body>
</html>