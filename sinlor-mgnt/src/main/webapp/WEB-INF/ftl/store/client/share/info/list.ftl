<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台-客户端分享信息列表</title> <#include
"lib/base_source.ftl">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<style type="text/css">
.small_sel {
	width: 120px;
}
</style>
</head>
<body>
	<#include "/lib/store_header.ftl">
	<div>
		<ul class="breadcrumb">
			<li>AppStore管理后台</li>
			<span class="divider">/</span>
			<li>客户端分享信息列表</li>
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
							<form method="post">
								<div class="input-prepend">
									<span class="add-on">客户端</span> <select name="bundleId">
										<#list bundleIdMap?keys as key>
										<option value="${key}">${bundleIdMap[key]}</option> </#list>
									</select>
								</div>
								<button class="btn btn-info query">
									<i class="icon-query icon-white"></i>查询
								</button>
							</form>
						</div>
					</div>
					<div class='main-content'>
						<button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<form id="searchForm" name="searchForm"
									enctype="multipart/form-data"
									action="${rc.contextPath}/auth/store/client/share/info/add"
									method="post">
									<div class="input-prepend">
										<span class="add-on">客户端</span> <select name="bundleId"
											type='add' class="client_choose"> <#list
											pureBundleIdMap?keys as key>
											<option value="${key}">${pureBundleIdMap[key]}</option>
											</#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">展示阶段</span> <select name="showInType">
											<#list marketFlags?keys as key>
											<option value="${key}">${marketFlags[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">分享标题:</span> <input type="text"
											name="title" class="span3" value="${para.title}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">分享内容:</span> <input type="text"
											name="content" class="span3" value="${para.content}">
									</div>
									<div class="input-prepend">
										<span class="add-on">分享内容指向地址:</span> <input type="text"
											name="url" class="span4" value="${para.url}">
									</div>
									<span class="add-on">分享Icon:</span>
									<div class="fileinput fileinput-new" data-provides="fileinput">
										<div class="fileinput-new thumbnail"
											style="width: 72px; height: 72px;">
											<img data-src="holder.js/100%x100%" alt="...">
										</div>
										<div class="fileinput-preview fileinput-exists thumbnail"
											style="max-width: 72px; max-height: 72px;"></div>
										<div>
											<span class="btn btn-default btn-file"><span
												class="fileinput-new">Select image</span><span
												class="fileinput-exists">Change</span><input type="file"
												name="icon"></span> <a href="#"
												class="btn btn-default fileinput-exists"
												data-dismiss="fileinput">Remove</a>
										</div>
									</div>
									<button class="btn btn-info add">
										<i class="icon-add icon-white"></i>增加
									</button>
									<div id="addInfoDiv" errMsg="${errMsg}">
										<#if errMsg??> <font>${errMsg}</font> </#if>
									</div>
								</form>
							</div>
						</div>
						<div class="table-content">
							<#-- table -->
							<form id="itemForm" name="itemForm" action="" method="post">
								<input type="hidden" id="pager_page" name="pager.page"
									value="${para.pager.page}"> <input type="hidden"
									id="pager_size" name="pager.size" value="${para.pager.size}">
								<input type="hidden" id="pager_total" name="pager.total"
									value="${para.pager.total}">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>Id</th>
											<th>客户端</th>
											<th>分享标题</th>
											<th>分享内容</th>
											<th>分享目标地址</th>
											<th>分享图标</th>
											<th>展示阶段</th>
											<th>状态</th>
											<th>操作</th>
											<th>详情</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.id}</td>
											<td>
												${value.bundleId}---${pureBundleIdMap[value.bundleId]}</td>
											<td class="edit_td" default_id="${value.id?c}"
												property="title"
												td_name="${value.bundleId}---${pureBundleIdMap[value.bundleId]}"
												for_class="span3"><label for="name" class="span3"><span
													id="span_title_${value.id?c}" class="text-info"
													preValue="${value.title}">${ value.title! }</span></label></td>
											<td class="edit_td" default_id="${value.id?c}"
												property="content"
												td_name="${value.bundleId}---${pureBundleIdMap[value.bundleId]}"
												for_class="span3"><label for="name" class="span4"><span
													id="span_content_${value.id?c}" class="text-info"
													preValue="${value.content}">${ value.content! }</span></label></td>

											<td class="edit_td" default_id="${value.id?c}" property="url"
												td_name="${value.bundleId}---${pureBundleIdMap[value.bundleId]}"
												for_class="span3"><label for="name" class="span4"><span
													id="span_url_${value.id?c}" class="text-info"
													preValue="${value.url}">${ value.url! }</span></label></td>
											<td><img width="25px" height="25px" src="${value.icon}" /></td>
											<td><#list marketFlags?keys as key> <#if key ==
												value.showInType> ${marketFlags[key]}</#if> </#list></td>
											<td><#if value.status == 0> <font
												id="status_font_${value.id}" color="green">√</font> <#else>
												<font id="status_font_${value.id}" color="red">X</font>
												</#if>
											</td>
											<td><#if value.status == 0> <span default_id=${value.id}
												pre_status="${value.status}" pre_text="删除" set_status="-1"
												set_text="正常" class="btn delete btn-danger">删除</span>
												<#else> <span default_id=${value.id}
												pre_status="${value.status}" set_status="0" pre_text="正常"
												set_text="删除" class="btn delete btn-success">正常</span>
												</#if>
											</td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/store/client/share/info/detail?id=${value.id}">详情</a>
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
						$.manage.pageList(".pagination", "#itemForm",
								currPageDiv, totalDiv, sizeDiv);
					});
			$("span.delete")
					.click(
							function() {
								event.preventDefault();
								bootbox.setDefaults({
									locale : "zh_CN"
								});
								var eventCtrl = $(this);
								var id = $(this).attr("default_id");
								var setStatus = $(this).attr("set_status");
								var preStatus = $(this).attr("pre_status");
								var setText = $(this).attr("set_text");
								var preText = $(this).attr("pre_text");
								var trCtrl = $("#tr_" + id);
								bootbox
										.confirm(
												"确认将应用包的分享信息【" + id + "】的状态改为【"
														+ preText + "】状态？",
												function(result) {
													if (!result) {
														return;
													}
													var postUrl = "${rc.contextPath}/auth/store/client/share/info/modify.json";
													var postData = "id=" + id
															+ "&status="
															+ setStatus;
													var posting = $.post(
															postUrl, postData);
													posting
															.done(function(resp) {
																if (!resp.data) {//
																	//show msg	 					
																	alert("操作失败,请联系该死的开发人员.Msg:"
																			+ resp.message);
																} else {
																	alert("修改成功.");
																	$(eventCtrl)
																			.attr(
																					"pre_status",
																					setStatus);
																	$(eventCtrl)
																			.attr(
																					"pre_text",
																					setText);
																	$(eventCtrl)
																			.attr(
																					"set_status",
																					preStatus);
																	$(eventCtrl)
																			.attr(
																					"set_text",
																					preText);
																	$(eventCtrl)
																			.html(
																					setText);
																	var fontCtrl = $("#status_font_"
																			+ id);
																	if (setStatus == 0) {
																		$(
																				eventCtrl)
																				.removeClass(
																						"btn-success")
																				.addClass(
																						"btn-danger");
																		$(
																				fontCtrl)
																				.removeAttr(
																						"color");
																		$(
																				fontCtrl)
																				.attr(
																						"color",
																						"green");
																		$(
																				fontCtrl)
																				.html(
																						"√");
																	} else {
																		$(
																				eventCtrl)
																				.removeClass(
																						"btn-danger")
																				.addClass(
																						"btn-success");
																		$(
																				fontCtrl)
																				.removeAttr(
																						"color");
																		$(
																				fontCtrl)
																				.attr(
																						"color",
																						"red");
																		$(
																				fontCtrl)
																				.html(
																						"X");
																	}

																}
															});
												});

							});
			$('.edit_td')
					.click(
							function() {
								var id = $(this).attr("default_id");
								var property = $(this).attr("property");
								var spanCtr = $("#span_" + property + "_" + id);
								var preValue = $(spanCtr).attr("preValue");
								var tdName = $(this).attr("td_name");
								var spanClass = $(this).attr('for_class')
								var input = $('<input id="attribute" type="text" class="' + spanClass + '" preValue="' + preValue + '" value="' + preValue + '" />')
								$(spanCtr).text('').append(input);
								input.select();
								input
										.blur(function() {
											var curValue = $('#attribute')
													.val();
											if (curValue == preValue) {
												var text = $('#attribute')
														.val();
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
											var typeName = (property == "title") ? "分享标题"
													: (property == "url") ? "分享指向链接"
															: "分享内容";
											bootbox
													.confirm(
															"将应用【" + tdName
																	+ "】的"
																	+ typeName
																	+ "由【"
																	+ preValue
																	+ "】改为【"
																	+ curValue
																	+ "】?",
															function(result) {
																if (result) {
																	var postUrl = "${rc.contextPath}/auth/store/client/share/info/modify.json";
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
																					resp) {
																				if (!resp.data) { //
																					//show msg	 					
																					alert("操作失败,请联系该死的开发人员.Msg:"
																							+ resp.message);
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
																	$(
																			'#attribute')
																			.parent()
																			.text(
																					preValue);
																	$(
																			'#attribute')
																			.remove();
																}
															});
										});
							});
		</script>
</body>
</html>