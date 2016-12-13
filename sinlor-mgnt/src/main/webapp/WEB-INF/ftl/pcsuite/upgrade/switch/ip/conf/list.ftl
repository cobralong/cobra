<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台-PC助手升级开关IP配置列表</title> <#include
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
	<#include "/lib/header.ftl">
	<div>
		<ul class="breadcrumb">
			<li>AppStore管理后台</li>
			<span class="divider">/</span>
			<li>PC助手升级开关IP配置列表</li>
		</ul>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="container span12">
				<#include "lib/alert.ftl">
				<div class='main-content'>
					<button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
					<div id="div_add" class="well form-inline">
						<div class='main-action'>
							<form id="searchForm" name="searchForm"
								enctype="multipart/form-data"
								action="${rc.contextPath}/auth/pcsuite/upgrade/switch/ip/conf/add"
								method="post">
								<div class="input-prepend">
									<span class="add-on">限制升级时可升级IP:</span> <input type="text"
										name="ip" class="span3" value="${para.ip}">
								</div>
								<button class="btn btn-info add">
									<i class="icon-add icon-white"></i>增加
								</button>
								<div id="addInfoDiv" errMsg="${addMsg}">
									<#if addMsg??> <font>${addMsg}</font> </#if>
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
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>Id</th>
										<th>Ip</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr>
										<td>${value.id}</td>
										<td>${value.ip}</td>
										<td><#if value.status == 0> <font
											id="status_font_${value.id?c}" color="green">√</font> <#else>
											<font id="status_font_${value.id?c}" color="red">X</font>
											</#if>
										</td>
										<td><#if value.status == 0> <span
											default_id="${value.id?c}" set_value="-1"
											class="btn delete btn-danger">删除</span> <#else> <span
											default_id="${value.id?c}" set_value="0"
											class="btn delete btn-success">恢复</span> </#if>
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
		$(document).ready(function() {
		    var currPageDiv = "#pager_page";
		    var totalDiv = "#pager_total";
		    var sizeDiv = "#pager_size";
		    $.manage.pageList(".pagination", "#itemForm", currPageDiv, totalDiv, sizeDiv);
		});
		$("span.delete").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var eventCtl = $(this);
		    var id = $(this).attr("default_id");
		    var setStatus = $(this).attr("set_value");
		    var postUrl = "${rc.contextPath}/auth/pcsuite/upgrade/switch/ip/conf/modify.json";
		    var actionName = setStatus == 0 ? "恢复" : "删除";
		    bootbox.confirm("是否" + actionName + "Id为" + id + "的记录?", function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = $.post(postUrl, {
		            'id': id,
		            "status": setStatus
		        });
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            }
		            else {
		                var statusInfo = setStatus == 0 ? "删除" : "恢复";
		                var removeClass = setStatus == 0 ? "btn-success" : "btn-danger";
		                var addClass = setStatus == 0 ? "btn-danger" : "btn-success";
		                $(eventCtl).removeClass(removeClass).addClass(addClass);
		                $(eventCtl).text(statusInfo);
		                var curSetValue = setStatus == 0 ? -1 : 0;
		                $(eventCtl).attr("set_value", curSetValue);
		                var statusFont = $("#status_font_" + id);
		                var fontInfo = setStatus == 0 ? "√" : "X";
		                var fontClass = setStatus == 0 ? "green" : "red";
		                $(statusFont).text(fontInfo);
		                $(statusFont).attr("color", fontClass);
		                alert("操作成功，已" + actionName + "记录");
		            }
		        });
		    });
		});
	</script>
</body>
</html>