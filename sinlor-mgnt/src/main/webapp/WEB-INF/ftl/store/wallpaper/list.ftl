<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title> <#include "lib/base_source.ftl"> <#include
"ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/store_header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>启动页壁纸列表</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div id="div_query" class="well form-inline">
							<div class='main-action'>
								<form id="searchForm" name="searchForm" action="" method="post">
									<input type="hidden" id="pager_page" name="pager.page"
										value="${para.pager.page}"> <input type="hidden"
										id="pager_size" name="pager.size" value="${para.pager.size}">
									<input type="hidden" id="pager_total" name="pager.total"
										value="${para.pager.total}">
									<div class="input-prepend">
										<span class="">精选</span> <@spring.formSingleSelect
										"para.chosen", chosens, " " />
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div class="table-content">
							<#-- table --> <#if para.status == 0>
							<p>
								在<b><font color="green">${para.st}</font></b>时间正在线上的广告列表:
							</p>
							</#if>
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>标题</th>
										<th>壁纸原图路径</th>
										<th>壁纸压缩路径</th>
										<th>查看次数</th>
										<th>是否精选</th>
										<th>精选日期</th>
										<th>精选操作</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr id="tr_${value.id}">
										<input id="hidden_${value.id}" type="hidden"
											ad_id="${value.id!}" promote_status="${value.status}" />
										<td><#if value.title ??> ${value.title} <#else> 无 </#if>
										</td>
										<td><a href="${value.url}" target="_blank"><img
												style="width: 150px; height: 70px" src="${value.url}"
												alt="${value.url}" /></a></td>
										<td><a href="${value.resizeUrl}" target="_blank"><img
												style="width: 150px; height: 70px" src="${value.resizeUrl}"
												alt="${value.resizeUrl}" /></a></td>
										<td><#if value.views ??> ${value.views} <#else> 0 </#if>
										</td>
										<td>${chosens[value.chosen?c]}</td>
										<td><#if value.inChosenTime ??> ${
											value.inChosenTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
											</#if></td>
										<td><#if value.chosen == 1>
											<button id="btn_chosen_${value.id?c}"
												class="btn btn-danger add" default_id="${value.id?c}"
												set_value="0" cur_value="1">移除精选</button> <#else>
											<button id="btn_chosen_${value.id?c}"
												class="btn btn-success add" default_id="${value.id?c}"
												set_value="1" cur_value="0">添加精选</button> </#if>
										</td>
										<td><#if value.status == 0> <font
											id="font_status_${value.id}" color="green">√</font> <#else> <font
											id="font_status_${value.id}" color="red">X</font> </#if>
										</td>
										<td><#if value.status == 0>
											<button id="btn_delete_${value.id?c}"
												class="btn btn-danger delete" default_id="${value.id?c}"
												set_value="-1" cur_value="0">删除</button> <#else>
											<button id="btn_delete_${value.id?c}"
												class="btn btn-success delete" default_id="${value.id?c}"
												set_value="0" cur_value="-1">恢复</button> </#if>
										</td>
									</tr>

									</#list> </#if>
								</tbody>
							</table>
							<div class="pagination" id="itemPage"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
	<script>
		$(document).ready(function() {
		    var currPageDiv = "#pager_page";
		    var totalDiv = "#pager_total";
		    var sizeDiv = "#pager_size";
		    $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
		});
		$("button.delete").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var eventCtl = $(this);
		    var id = $(this).attr("default_id");
		    var trCtl = $("#tr_" + id);
		    var curValue = $(this).attr("cur_value");
		    var setValue = $(this).attr("set_value");
		    var postUrl = "${rc.contextPath}/auth/store/wallpaper/modify";
		    var actionMsg = curValue == 0 ? "是否将壁纸从列表中移除?" : "是否将壁纸添加到列表?";
		    bootbox.confirm(actionMsg, function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = $.post(postUrl, {
		            'id': id,
		            "status": setValue
		        });
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            } else {
		                var responseMsg = curValue == 0 ? "成功将壁纸从列表中移除!" : "成功将壁纸添加到上线列表!";
		                alert(responseMsg);
		                trCtl.remove();
		            }
		        });
		    });
		});
		$("button.add").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var eventCtl = $(this);
		    var id = $(this).attr("default_id");
		    var trCtl = $("#tr_" + id);
		    var curValue = $(this).attr("cur_value");
		    var setValue = $(this).attr("set_value");
		    var postUrl = "${rc.contextPath}/auth/store/wallpaper/chosen/modify";
		    var actionMsg = curValue == 1 ? "是否将壁纸从精选列表中移除?" : "是否将壁纸添加到精选列表?";
		    bootbox.confirm(actionMsg, function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = $.post(postUrl, {
		            'id': id,
		            "chosen": setValue
		        });
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            } else {
		                var responseMsg = curValue == 1 ? "成功将壁纸从列表中移除!" : "成功将壁纸添加到上线列表!";
		                alert(responseMsg);
		                trCtl.remove();
		            }
		        });
		    });
		});
	</script>
</body>
</html>