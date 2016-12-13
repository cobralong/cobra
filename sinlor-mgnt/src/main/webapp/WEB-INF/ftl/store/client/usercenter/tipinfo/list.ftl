<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台-客户端用户中心面板Tip信息列表</title> <#include
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
			<li>客户端用户中心面板Tip信息列表</li>
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
										<#list bundleIds?keys as key>
										<option value="${key}">${bundleIds[key]}</option> </#list>
									</select>
								</div>
								<div class="input-prepend">
									<span class="add-on">状态</span> <select name="status">
										<#list statuses?keys as key>
										<option value="${key}">${statuses[key]}</option> </#list>
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
									action="${rc.contextPath}/auth/store/client/usercenter/tipinfo/add"
									method="post">
									<div class="input-prepend">
										<span class="add-on">客户端</span> <select id="sel_bundleid"
											name="bundleId" type='add' class="client_choose">
											<#list pureBundleIds?keys as key>
											<option value="${key}">${bundleIds[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">展示阶段</span> <select id="sel_market_flag"
											name="marketFlag" class="client_choose"> <#list
											marketFlags?keys as key>
											<option value="${key}">${marketFlags[key]}</option> </#list>
										</select>
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">文章ID</span> <input class="span2 search"
											name="articleId" type="text" placeholder="articleId"
											value="${articleId}">
									</div>
									<div class="input-prepend">
										<span class="add-on">文章标题</span> <input
											id="input_article_title" class="span3" name="title"
											type="text" placeholder="文章标题" value="${title}"> <span
											class="add-on">文章地址</span> <input id="input_article_url"
											class="span3" type="text" placeholder="文章链接" disabled
											value=""> <span class="add-on">文章图标</span> <img
											id="img_article_icon" width="25px" height="25px"
											src="iconUrl" name="iconUrl">
									</div>
									<br /> <br /> <span class="add-on">自定义图标:</span>
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
											<th>Tip名称</th>
											<th>Tip链接</th>
											<th>Tip图标</th>
											<th>展示阶段</th>
											<th>状态</th>
											<th>修改时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr id="tr_${value.id?c}">
											<td>${value.id}</td>
											<td>${value.bundleId}---${bundleIds[value.bundleId]}</td>
											<td class="edit_td" default_id="${value.id?c}"
												property="title"
												td_name="${value.bundleId}---${bundleIds[value.bundleId]}"
												for_class="span3"><label for="name" class="span3">
													<span id="span_title_${value.id?c}" class="text-info"
													preValue="${value.title}">${ value.title! }</span>
											</label></td>
											<td><a width="25px" height="25px" href="${value.url}"
												target="_blank" />${value.title}</a></td>
											<td><img width="25px" height="25px"
												src="${value.iconUrl}" /></td>
											<td><#list marketFlags?keys as key> <#if key ==
												value.marketFlag> ${marketFlags[key]}</#if> </#list></td>
											<td><#if value.status == 0> <span
												class="label label-success">√</span> <#else> <span
												class="label label-danger" disabled>X</span> </#if>
											</td>
											<td><#if value.updateTime ??> ${
												value.updateTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
												</#if></td>
											<td><#if value.status == 0> <span default_id=${value.id}
												set_status="-1" class="btn delete btn-danger">删除</span>
												<#else> <span default_id=${value.id} set_status="0"
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
						$.manage.pageList(".pagination", "#itemForm",
								currPageDiv, totalDiv, sizeDiv);
					});
			$("input.search").bind(
					"blur",
					function() {
						var ctl = $(this);
						var rid = $(ctl).val();
						if (rid == "") {
							return;
						}
						var txtCtl = $("#input_article_title");
						var urlCtl = $("#input_article_url")
						var iconCtl = $("#img_article_icon")
						var btn = $("button.add");
						var posting = $.post(
								"${rc.contextPath}/auth/store/article/search",
								{
									"id" : rid
								});
						posting.done(function(result) {
							if (result.success) {
								$(txtCtl).val(result.data.title);
								$(urlCtl).val(result.data.articleUrl);
								$(iconCtl).attr("src", data.icon);
							} else {
								$(ctl).focus();
								$(txtCtl).val(result.message);
							}
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
											bootbox
													.confirm(
															"将tips【" + tdName
																	+ "】由【"
																	+ preValue
																	+ "】改为【"
																	+ curValue
																	+ "】?",
															function(result) {
																if (result) {
																	var postUrl = "${rc.contextPath}/auth/store/client/usercenter/tipinfo/modify.json";
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
								var trCtl = $("#tr_" + id);
								var setStatus = $(this).attr("set_status");
								var postUrl = "${rc.contextPath}/auth/store/client/usercenter/tipinfo/modify.json";
								var confirmMsg = "";
								if (status == -1) {
									confirmMsg = "删除Id为【" + id + "】的当前记录";
								} else {
									confirmMsg = "恢复Id为【" + id + "】的当前记录";
								}
								bootbox.confirm(confirmMsg + "?", function(
										result) {
									if (!result) {
										return;
									}
									var posting = $.post(postUrl, {
										'id' : id,
										"status" : setStatus
									});
									posting.done(function(errMsg) {
										if (errMsg) { //
											//show msg	 					
											alert("操作失败,请联系该死的开发人员.Msg:"
													+ errMsg);
										} else {
											alert("操作成功，已" + confirmMsg + "!");
											$(trCtl).remove();
										}
									});
								});
							});
		</script>
</body>
</html>