<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title> <#include "lib/base_source.ftl"> <#include
"ftl_macro.ftl">
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
				<li>机器自动化--苹果帐号列表</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<form id="searchForm" name="searchForm" action="" method="post">
									<input type="hidden" id="pager_page" name="pager.page"
										value="${para.pager.page}"> <input type="hidden"
										id="pager_size" name="pager.size" value="${para.pager.size}">
									<input type="hidden" id="pager_total" name="pager.total"
										value="${para.pager.total}">
									<div class="input-prepend">
										<span class="add-on">邮箱:</span> <input name="appleId"
											value="${para.appleId!}" />
									</div>
									<div class="input-prepend">
										<span class="add-on">账号类型</span><@spring.formSingleSelect
										"para.automatedType", automatedTypes, " " />
									</div>
									<div class="input-prepend">
										<span class="add-on">激活状态</span><@spring.formSingleSelect
										"para.activeStatus", activeStatuses, " " />
									</div>
									<div id="startDatetimePicker"
										class="input-append date datepicker"
										data-date-format="yyyy-mm-dd" data-date-autoclose="true">
										<input class="span2" id="startDateString" name="st" size="16"
											type="text" value="${para.st?substring(0,10)}"> <span
											class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="add-on">结束时间</span>
									<div id="endDatetimePicker"
										class="input-append date datepicker"
										data-date-format="yyyy-mm-dd" data-date-autoclose="true">
										<input class="span2" id="endDateString" name="et" size="16"
											type="text" value="${para.et?substring(0,10)}"> <span
											class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>查询
									</button>
								</form>
							</div>
						</div>
						<div class="table-content">
							从${para.st?substring(0,10)}-----${para.et?substring(0,10)}已注册${para.pager.total}个苹果账号
							<#-- table -->
							<form id="itemForm" name="itemForm" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>邮箱</th>
											<th>姓名</th>
											<th>密保邮箱</th>
											<th>账号类型</th>
											<th>激活状态</th>
											<th>状态</th>
											<th>修改时间</th>
											<th>入库时间</th>
											<th>详情</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.appleId!''}</td>
											<td>${value.lastName!''}---${value.firstName!''}</td>
											<td>${value.securityMail!''}</td>
											<td>${automatedTypes[value.automatedType?c]}</td>
											<td>${activeStatuses[value.activeStatus?c]}</td>
											<td><#if value.status == 0> <span
												class="label label-success">√</span> <#else> <span
												class="label label-danger" disabled>X</span> </#if>
											</td>
											<td><#if value.updateTime ??> ${
												value.updateTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td><#if value.createTime ??> ${
												value.createTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/appleaccount/automated/detail.ftl?id=${value.id}">详情</a>
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
</body>
<script type="text/javascript"
	src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
<script>
	$(document).ready(
			function() {
				var currPageDiv = "#pager_page";
				var totalDiv = "#pager_total";
				var sizeDiv = "#pager_size";
				$.manage.pageList(".pagination", "#searchForm", currPageDiv,
						totalDiv, sizeDiv);
			});

	$("#startDatetimePicker, #endDatetimePicker").datetimepicker({
		'date-format' : 'yyyy-mm-dd',
		stepYear : 1,
		stepMonth : 1,
		stepDay : 1,
		startView : 2,
		minView : 2,
		maxView : 2
	});
</script>
</html>