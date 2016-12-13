<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台PC助手已连接设备列表页面</title> <#include "lib/base_source.ftl">
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
				<li>PC助手已连接设备列表页面</li>
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
										<span class="add-on">设备IMEI:</span> <input class="span3"
											name="imei" type="text" placeholder="imei"
											value="${para.imei}">
									</div>
									<div class="input-prepend">
										<span class="add-on">设备UDID:</span> <input class="span3"
											name="udid" type="text" placeholder="udid"
											value="${para.udid}">
									</div>
									<div class="input-prepend">
										<span class="add-on">设备GUID:</span> <input class="span3"
											name="deviceGuid" type="text" placeholder="deviceGuid"
											value="${para.deviceGuid}">
									</div>
									<div class="input-prepend">
										<span class="add-on">助手ClientId:</span> <input class="span3"
											name="clientId" type="text" placeholder="clientId"
											value="${para.clientId}">
									</div>
									<div class="input-prepend">
										<span class="add-on">助手GUID:</span> <input class="span3"
											name="guid" type="text" placeholder="guid"
											value="${para.guid}">
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>查询
									</button>
								</form>
							</div>
						</div>
						当前条件下 <#if para.imei?length!=0>设备IMEI:${para.imei}</#if> <#if
						para.udid?length!=0>设备udid:${para.udid}</#if> <#if
						para.clientId?length!=0>助手ClientId:${para.clientId}</#if> <#if
						para.guid?length!=0>助手Guid:${para.guid}</#if>
						共有${para.pager.total}条记录
						<div class="table-content">
							<#-- table -->
							<form id="itemForm" name="itemForm" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>IMEI</th>
											<th>UDID</th>
											<th>设备GUID</th>
											<th>设备颜色</th>
											<th>设备iOS系统</th>
											<th>设备型号</th>
											<th>ClientId</th>
											<th>助手GUID</th>
											<th>最近连接时间</th>
											<th>开始连接时间</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.imei!''}</td>
											<td>${value.udid!''}</td>
											<td>${value.deviceGuid!''}</td>
											<td><font color="${value.color}">${value.color!}</font></td>
											<td>${value.iosVersion!''}</td>
											<td><#list platforms as platform> <#if platform.platform
												== value.platform> ${platform.content}</#if> </#list></td>											
											<td>${value.guid!''}</td>
											<td>${value.clientId!''}</td>
											<td><#if value.updateTime ??> ${
												value.updateTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td><#if value.createTime ??> ${
												value.createTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
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
<script>
	$(document).ready(
			function() {
				var currPageDiv = "#pager_page";
				var totalDiv = "#pager_total";
				var sizeDiv = "#pager_size";
				$.manage.pageList(".pagination", "#searchForm", currPageDiv,
						totalDiv, sizeDiv);
			});
</script>
</html>