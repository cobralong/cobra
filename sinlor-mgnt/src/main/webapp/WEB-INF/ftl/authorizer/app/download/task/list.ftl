<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<style type="text/css">
.small_sel {
	width: 120px;
}
</style>
<title>应用汇-管理后台-授权应用下载任务列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权应用下载任务列表</li>
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
										<span class="add-on">ID</span> <input class="span2 search"
											name="rootId" type="text" placeholder="ID" value="${rid}">
									</div>
									<div class="input-prepend">
										<span class="add-on">应用名称</span> <input id="input_app_name"
											class="span4" type="text" placeholder="应用名称" disabled
											value=""> <input id="input_item_id" name="itemId"
											type="hidden" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">授权账号：</span> <select name="appleId">
											<option value="all">全部</option> <#list
											appleAuthorizerAccounts as appleAuthorizerAccount> <#if
											appleAuthorizerAccount.appleId == para.appleId>
											<option selected value="${appleAuthorizerAccount.appleId}">${appleAuthorizerAccount.appleId}</option>
											<#else>
											<option value="${appleAuthorizerAccount.appleId}">${appleAuthorizerAccount.appleId}</option>
											</#if> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">下载状态</span> <@spring.formSingleSelect
										"para.taskStatus", taskStatus, " " />
									</div>
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
						<br /> <br />
						<div class="table-content">
							<form id="itemForm" name="itemForm" action="" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>Id</th>
											<th>应用信息</th>
											<th>授权账号</th>
											<th>任务服务器</th>
											<th>是否下载ipa</th>
											<th>说明</th>
											<th>任务执行状态</th>
											<th>操作</th>
											<th>下载反馈列表</th>
											<th>更新时间</th>
											<th>入库时间</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.id?c}</td>
											<td><@hrefapp value.rootId, itemIdRootSimples[value.itemId?c].name/>---${value.itemId?c}---${value.version}</td>
											<td>${value.appleId!}</td>
											<td><#if value.downloadServerId?? &&
												value.downloadServerId !=0> <#list downloadServers as
												downloadServer> <#if downloadServer.id ==
												value.downloadServerId> ${downloadServer.name} </#if>
												</#list> <#else> 暂未分配下载服务器 </#if></td>
											<td><select id="td_select_pure_ipa_${value.id}"
												tr_name="下载纯净ipa" name="pureIpa" default_id="${value.id?c}"
												pre_value="${value.pureIpa}" class="small_sel">
													<#list pureIpas?keys as key> <#if key == value.pureIpa>
													<option value="${key}" selected>${pureIpas[key]}</option>
													<#else>
													<option value="${key}">${pureIpas[key]}</option> </#if>
													</#list>
											</select></td>
											<td>${value.info}</td>
											<td><#if value.taskStatus == 0> <font
												id="font_${value.id?c}">
													${taskStatus[value.taskStatus?c]}</font> <#elseif value.taskStatus
												== 1> <font color="blue">${taskStatus[value.taskStatus?c]}</font>
												<#elseif value.taskStatus == 2> <font color="green">${taskStatus[value.taskStatus?c]}</font>
												<#else> <font color="red">${taskStatus[value.taskStatus?c]}</font>
												</#if>
											</td>
											<td><#if value.taskStatus == 0> <span
												default_id=${value.id} disabled class="btn">等待执行</span>
												<#elseif value.taskStatus == 1> <span default_id=${value.id}
												disabled class="btn">执行中</span> <#elseif value.taskStatus ==
												2> <span default_id=${value.id} disabled class="btn">执行成功</span>
												<#else> <span default_id=${value.id} set_status="0"
												class="btn delete btn-danger">重新执行</span> </#if>
											</td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/authorizer/app/download/feedback/list?taskId=${value.id?c}">反馈查看</a>
											</td>
											<td><#if value.updateTime ??> ${
												value.updateTime?string("yyyy-MM-dd HH:mm:ss") } </#if></td>
											<td><#if value.createTime ??> ${
												value.createTime?string("yyyy-MM-dd HH:mm:ss") } </#if></td>
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
		<script type="text/javascript"
			src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
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
			$("input.search")
					.bind(
							"blur",
							function() {
								var ctl = $(this);
								var rid = $(ctl).val();
								if (rid == "") {
									return;
								}
								var txtCtl = $("#input_app_name");
								var btn = $("button.add");
								var posting = $
										.post(
												"${rc.contextPath}/auth/root/application/search",
												{
													"id" : rid
												});
								posting.done(function(result) {
									if (result.success) {
										$(txtCtl).val(result.data.editorTitle);
										$("#input_item_id").val(
												result.data.itemId);
									} else {
										$(ctl).focus();
										$(txtCtl).val(result.message);
										$("#input_item_id").val();
									}
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
								var eventCtl = $(this);
								var id = $(this).attr("default_id");
								var preStatus = $(this).attr("set_status");
								var postUrl = "${rc.contextPath}/auth/authorizer/app/download/task/modify.json";
								bootbox
										.confirm(
												"将下载任务Id:【" + id + "】重新下载?",
												function(result) {
													if (!result) {
														return;
													}
													var posting = $
															.post(
																	postUrl,
																	{
																		'id' : id,
																		"taskStatus" : 0
																	});
													posting
															.done(function(resp) {
																if (!resp.data) {//
																	//show msg	 					
																	alert("操作失败,请联系该死的开发人员.Msg:"
																			+ resp.message);
																} else {
																	alert("操作成功!");
																	$(
																			"#font_"
																					+ id)
																			.removeAttr(
																					"color");
																	$(
																			"#font_"
																					+ id)
																			.html(
																					"任务等待执行");
																	$(eventCtl)
																			.attr(
																					"disabled",
																					"disabled");
																	$(eventCtl)
																			.html(
																					"等待执行");
																}
															});
												});
							});
			$(document)
					.on(
							'change',
							'select.small_sel',
							function() {
								var selCtr = $(this);
								var id = $(this).attr('default_id');
								var ctrlId = $(this).attr("id");
								var preValue = $(this).attr('pre_value');
								var paraName = $(this).attr("name");
								var trName = $(this).attr("tr_name");
								var curValue = $(this).val();
								var preDesc = "";
								$("#" + ctrlId + " option").each(function() {
									if (preValue == $(this).val()) {
										preDesc = $(this).text();
										return false;
									}
								});
								var curDesc = $(
										"#" + ctrlId + " option:selected")
										.text();
								bootbox
										.confirm(
												"将下载任务【" + id + "】的" + trName
														+ "由【" + preDesc
														+ "】改为【" + curDesc
														+ "】?",
												function(result) {
													if (!result) {
														return;
													}
													var postUrl = "${rc.contextPath}/auth/authorizer/app/download/task/modify.json";
													var postData = "id=" + id
															+ "&" + paraName
															+ "=" + curValue;
													var posting = $.post(
															postUrl, postData);
													posting
															.done(function(resp) {
																if (!resp.data) {//
																	//show msg
																	alert("操作失败,请联系该死的开发人员.Msg:"
																			+ resp.message);
																	$(selCtr)
																			.attr(
																					"pre_value",
																					preValue);
																	$(selCtr)
																			.val(
																					preValue);
																} else {
																	$(selCtr)
																			.attr(
																					"pre_value",
																					curValue);
																	alert("修改成功.");
																}
															});
												});
							});
		</script>
</body>
</html>