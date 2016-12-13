<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-授权账号授权授权服务器列表</title> <#include "lib/base_source.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权账号授权授权服务器列表</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<form id="searchForm" name="searchForm" action="" method="post">
						<input type="hidden" id="pager_page" name="pager.page"
							value="${para.pager.page}"> <input type="hidden"
							id="pager_size" name="pager.size" value="${para.pager.size}">
						<input type="hidden" id="pager_total" name="pager.total"
							value="${para.pager.total}">
					</form>
					<br />
					<br />
					<div class="table-content">
						<form id="itemForm" name="itemForm" action="" method="post">
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>AppleId</th>
										<th>授权服务机器名称</th>
										<th>授权服务访问地址</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr>
										<td><#list authorizerAppleAccountIdMap as
											authorizerAppleAccount> <#if authorizerAppleAccount.id ==
											value.appleAccountId> ${authorizerAppleAccount.appleId}
											</#if> </#list></td>
										<td><#list authorizerPcServerInfoMap as
											authorizerPcServerInfo> <#if authorizerPcServerInfo.id ==
											value.pcServerId> ${authorizerPcServerInfo.osName} </#if>
											</#list></td>
										<td><#list authorizerPcServerInfoMap as
											authorizerPcServerInfo> <#if authorizerPcServerInfo.id ==
											value.pcServerId> ${authorizerPcServerInfo.url} </#if>
											</#list></td>
										<td><#if value.status == 0> <font
											id="status_font_${value.id}" color="green">√</font> <#else> <font
											id="status_font_${value.id}" color="red">X</font> </#if>
										</td>
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
		$("span.delete").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var eventCtl = $(this);
		    var id = $(this).attr("default_id");
		    var preStatus = $(this).attr("status");
		    var status = preStatus == 0 ? -1 : 0;
		    var postUrl = "${rc.contextPath}/auth/appleaccount/authorizer/auth/pc/server/info/modify.json";
		    var confirmMsg = "";
		    if (status == -1) {
		        confirmMsg = "删除当前记录";
		    }
		    else {
		        confirmMsg = "恢复当前记录";
		    }
		    bootbox.confirm(confirmMsg + "?", function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = $.post(postUrl, {
		            'id': id,
		            "status": status
		        });
		        posting.done(function(resp) {
		            if (!resp.data) { //
		                //show msg	 					
		                alert("操作失败,请联系该死的开发人员.Msg:" + resp.message);
		            }
		            else {
		                alert("操作成功，已" + confirmMsg + "!");
		                var fontCtl = $("#status_font_" + id);
		                $(eventCtl).attr("status", status);
		                if (status == 0) {
		                    $(eventCtl).attr("class", "btn delete btn-danger");
		                    $(eventCtl).html("删除");
		                    $(fontCtl).attr("color", "green");
		                    $(fontCtl).html("√");
		                }
		                else {
		                    $(eventCtl).attr("class", "btn delete btn-success");
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