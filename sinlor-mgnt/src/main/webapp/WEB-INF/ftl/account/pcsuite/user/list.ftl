<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台PC助手用户列表页面</title> <#include "lib/base_source.ftl">
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
				<li>PC助手用户列表页面</li>
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
										<span class="add-on">ClientId:</span> <input class="span3"
											name="clientId" type="text" placeholder="clientId"
											value="${para.clientId}">
									</div>
									<div class="input-prepend">
										<span class="add-on">GUID:</span> <input class="span3"
											name="guid" type="text" placeholder="guid"
											value="${para.guid}">
									</div>
									<div class="input-prepend">
										<span class="add-on">助手渠道:</span> <select name="channel">
											<option value="all">所有渠道</option> <#list channels as channel>
											<#if para.channel == channel.channel>
											<option selected value="${channel.channel}">${channel.name}</option>
											<#else>
											<option value="${channel.channel}">${channel.name}</option>
											</#if> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">助手版本号:</span> <select
											name="pcsuiteVersion">
											<option value="all">所有版本</option> <#list versions as version>
											<#if para.pcsuiteVersion == version.version>
											<option selected value="${version.version}">${version.version}</option>
											<#else>
											<option value="${version.version}">${version.version}</option>
											</#if> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">CPU架构:</span> <@spring.formSingleSelect
										"para.arch", arches, " " />
									</div>
									<div class="input-prepend">
										<span class="add-on">操作系统版本:</span> <@spring.formSingleSelect
										"para.system", windows, " " />
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>查询
									</button>
								</form>
							</div>
						</div>
						当前条件下 <#if para.clientId?length!=0>ClientId:${para.clientId}</#if>
						<#if para.guid?length!=0>Guid:${para.guid}</#if> <#if
						para.channel?length!=0 &&
						para.channel!='all'>渠道:${para.channel}</#if> <#if
						para.arch?length!=0 &&
						para.arch!=9999>Cpu架构:${arches[para.arch]}</#if> <#if
						para.system?length!=0 &&
						para.system!=9999>操作系统:${windows[para.system]}</#if>
						共有${para.pager.total}条记录
						<div class="table-content">
							<#-- table -->
							<form id="itemForm" name="itemForm" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>GUID</th>
											<th>ClientId</th>
											<th>渠道</th>
											<th>助手版本号</th>
											<th>操作系统</th>
											<th>CPU架构</th>
											<th>最近活动时间</th>
											<th>开始使用时间</th>
											<th>曾连接设备</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.guid!''}</td>
											<td>${value.clientId!''}</td>
											<td>${value.channel!''}</td>
											<td>${value.pcsuiteVersion!''}</td>
											<td><#list arches?keys as key> <#if key == value.arch>
												${arches[key]} </#if> </#list></td>
											<td><#list windows?keys as key> <#if key ==
												value.system> ${windows[key]} </#if> </#list></td>
											<td><#if value.updateTime ??> ${
												value.updateTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td><#if value.createTime ??> ${
												value.createTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td>
												<a class="btn btn-info query" href="${rc.contextPath}/auth/account/pcsuite/connected/device/info/list?guid=${value.guid}">详情</a>
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