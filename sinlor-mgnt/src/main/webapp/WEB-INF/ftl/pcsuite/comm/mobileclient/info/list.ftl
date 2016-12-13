<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-WebClip用户反馈列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>WebClip用户反馈列表</li>
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
										<span class="add-on">状态</span> <@spring.formSingleSelect
										"para.status", status, " " />
									</div>
									<button class="btn btn-info query" onClick="modifyPage()">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="searchForm"
									action="${rc.contextPath}/auth/pcsuite/comm/mobileclient/info/add">
									<div class="input-prepend">
										<span class="add-on">客户端</span> <input class="span3" size="10"
											name="bundleId" type="text" placeholder="com.***" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">开始写入版本号</span> <input class="span2"
											size="10" name="startVersion" type="text" placeholder="1.0.0"
											value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">中文名称</span> <input class="span2"
											size="10" name="name" type="text" placeholder="应用名称" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">英文名称</span> <input class="span2"
											size="10" name="enName" type="text" placeholder="App Name"
											value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">繁体名称</span> <input class="span2"
											size="10" name="twName" type="text" placeholder="應用名稱"
											value="">
									</div>
									<button id="btn_add_configure" class="btn btn-info add">
										<i class="icon-search icon-white"></i>增加
									</button>
									<div id="addInfoDiv" errMsg="${para.errMsg}">
										<#if para.errMsg??> <font>${para.errMsg}</font> </#if>
									</div>
								</form>
							</div>
						</div>
						<div class="table-content">
							<form id="itemForm" name="itemForm" action="" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>包名</th>
											<th>开始版本</th>
											<th>中文名</th>
											<th>英文名</th>
											<th>繁體中文名</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if datas??> <#list datas as value>
										<tr id="tr_${value.id}">
											<td>${value.bundleId}</td>
											<td>${value.startVersion}</td>
											<td>${value.name}</td>
											<td>${value.enName}</td>
											<td>${value.twName}</td>
											<td><#if value.status == 0> <span
												class="label label-success">√</span> <#else> <span
												class="label label-danger" disabled>X</span> </#if>
											</td>
											<td><span id="btnRecover" class="btn btn-success recover"
												bundleId="${value.bundleId}" default_id="${value.id?c}"
												set_status="0"<#if value.status == 0>disabled</#if>>
													恢复写入</span> 
												<span id="btnDelete" class="btn btn-danger delete"
												bundleId="${value.bundleId}" default_id="${value.id?c}"
												set_status="-1"<#if value.status != 0>disabled</#if>>
													禁止写入</span>
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
	<script>
		$(document).ready(
				function() {
					var currPageDiv = "#pager_page";
					var totalDiv = "#pager_total";
					var sizeDiv = "#pager_size";
					$.manage.pageList(".pagination", "#searchForm",
							currPageDiv, totalDiv, sizeDiv);
				});

		function modifyPage() {
			$("#pager_page").val(1);
		}

		$("span.btn").bind("click",function(event) {
			event.preventDefault();
			bootbox.setDefaults({
				locale : "zh_CN"
			});
			var id = $(this).attr("default_id");
			var setStatus = $(this).attr("set_status");
			var bundleId = $(this).attr("bundleId");
			var trCtrl = $("#tr_" + id);
			var confirmMessage="确认往应用包【" + bundleId + "】写入授权信息？";
			if(setStatus==-1){
				confirmMessage="确认不再往应用包【" + bundleId + "】写入授权信息？";
			}
			bootbox.confirm(confirmMessage,
					function(result) {
						if (!result) {
							return;
						}
						var postUrl = "${rc.contextPath}/auth/pcsuite/comm/mobileclient/info/modify";
						var postData = "id=" + id+ "&status="+ setStatus;
						var posting = $.post(postUrl,postData);
						posting.done(function(errMsg) {
									if (errMsg) {
										//show msg	 					
										alert("操作失败,请联系该死的开发人员.Msg:"+ errMsg);
									} else {
										alert("修改成功.");
										window.location.reload();
									}
						});
					});
		});
	</script>
</body>
</html>