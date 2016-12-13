<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-授权账号授权用户设备列表</title> <#include "lib/base_source.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权账号授权用户设备列表</li>
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
										<span class="add-on">AppleId:</span> <input name="appleId"
											value="" type="input" />
									</div>
									<div class="input-prepend">
										<span class="add-on">IMEI:</span> <input name="imei" value=""
											type="input" />
									</div>
									<div class="input-prepend">
										<span class="add-on">Udid:</span> <input name="udid" value=""
											type="input" />
									</div>
									<div class="input-prepend">
										<span class="add-on">状态</span> <@spring.formSingleSelect
										"para.status", status, " " />
									</div>
									<div class="input-prepend">
										<span class="add-on">写入状态</span> <select name="writeStatus">
											<#list writeStatusMap?keys as key> <#if para.writeStatus ==
											key>
											<option value="${key}" selected>${writeStatusMap[key]}</option>
											<#else>
											<option value="${key}">${writeStatusMap[key]}</option> </#if>
											</#list>
										</select>
									</div>
									<button class="btn btn-info query" onClick="modifyPage()">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
					</div>

					<#if para.appleAccountId>当前账号${para.appleAccountId}总共在${para.pager.total}台设备上写入授权</#if> 
					<#if para.appleId>当前账号${para.appleId}总共在${para.pager.total}台设备上写入授权</#if> <#if para.imei>当前设备${para.imei}总共写入${para.pager.total}个授权账号</#if>
					<#if para.udid>当前设备${para.udid}总共写入${para.pager.total}个授权账号</#if>
					<div class="table-content">
						<form id="itemForm" name="itemForm" action="" method="post">
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>AppleId</th>
										<th>IMEI</th>
										<th>UDID</th>
										<th>更新时间</th>
										<th>写入时间</th>
										<th>记录状态</th>
										<th>授权状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr>
										<td>${value.appleAccountEmail!}</td>
										<td>${value.imei!}</td>
										<td>${value.udid!}</td>
										<td>
                                        	${ value.updateTime?string("yyyy-MM-dd hh:mm:ss") }
                                        </td>
                                        <td>
                                        	${ value.createTime?string("yyyy-MM-dd hh:mm:ss") }
                                        </td>
										<td><#if value.status == 0> <font
											id="status_font_${value.id}" color="green">√</font> <#else> <font
											id="status_font_${value.id}" color="red">X</font> </#if>
										</td>
										<td>${writeStatusMap[value.writeStatus?c]}</td>
										<td><#if value.status == 0> <span default_id=${value.id}
											status="${value.status}" class="btn delete btn-danger">删除</span>
											<#else> <span default_id=${value.id} status="${value.status}"
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
							var preStatus = $(this).attr("status");
							var status = preStatus == 0 ? -1 : 0;
							var postUrl = "${rc.contextPath}/auth/appleaccount/authorizer/auth/device/info/modify.json";
							var confirmMsg = "";
							if (status == -1) {
								confirmMsg = "删除当前记录";
							} else {
								confirmMsg = "恢复当前记录";
							}
							bootbox.confirm(confirmMsg + "?", function(result) {
								if (!result) {
									return;
								}
								var posting = $.post(postUrl, {
									'id' : id,
									"status" : status
								});
								posting.done(function(resp) {
									if (!resp.data) { //
										//show msg	 					
										alert("操作失败,请联系该死的开发人员.Msg:"
												+ resp.message);
									} else {
										alert("操作成功，已" + confirmMsg + "!");
										var fontCtl = $("#status_font_" + id);
										$(eventCtl).attr("status", status);
										if (status == 0) {
											$(eventCtl).attr("class",
													"btn delete btn-danger");
											$(eventCtl).html("删除");
											$(fontCtl).attr("color", "green");
											$(fontCtl).html("√");
										} else {
											$(eventCtl).attr("class",
													"btn delete btn-success");
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