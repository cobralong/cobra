<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-授权应用下载反馈列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权应用下载反馈列表</li>
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
										<span class="add-on">Root Id：</span> <input class="span4"
											name="rootId" type="text" placeholder="rootId"
											value="${para.rootId}">
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
										<span class="add-on">反馈状态</span> <@spring.formSingleSelect
										"para.feedbackStatus", feedbackStatus, " " />
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
						<div class="table-content">
							<form id="itemForm" name="itemForm" action="" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>Id</th>
											<th>账号信息</th>
											<th>应用信息</th>
											<th>处理详情</th>
											<th>处理状态</th>
											<th>处理状态操作</th>
											<th>反馈信息</th>
											<th>状态</th>
											<th>记录操作</th>
											<th>详情</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr id="tr_${value.id?c}">
											<td>${value.id?c}</td>
											<td>${value.appleId!}</td>
											<td><@shortdesc rootIdApplicationSimpleMap[value.rootId?c].name/></td>
											<td><@shortdesc value.info/></td>
											<td>
											<font id="font_feedback_${value.id?c}">${feedbackStatus[value.feedbackStatus?c]}</font>
											</td>
											<td><#if value.status == -1 || value.errMsg?length != 0>
												<span class="btn" disabled>下载异常</span> <#else> <#if
												value.feedbackStatus == 9> <span class="btn" disabled
												style="color: green">
													${feedbackStatus[value.feedbackStatus?c]} </span> <#elseif
												value.feedbackStatus ==-1> <span default_id=${value.id}
												set_value="0" set_value_desc = "等待处理" property="feedbackStatus" cur_value="-1" cur_value_desc="无法处理"
												class="btn delete btn-danger">重新执行</span> <#else> <span
												class="btn" disabled>${feedbackStatus[value.feedbackStatus?c]}</span>
												</#if> </#if>
											</td>
											<td><@shortdesc value.errMsg!/></td>
											<td><#if value.status == 0> <font
												id="status_font_${value.id}" color="green">√</font> <#else>
												<font id="status_font_${value.id}" color="red">X</font>
												</#if>
											</td>
											<td><#if value.status != 0> <font color="red">X</font>
												<#else> <span default_id=${value.id} set_value="-1" set_value_desc="删除"
												property="status" cur_value="0" cur_value_desc="正常"
												class="btn delete btn-danger">不再执行</span> </#if>
											</td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/authorizer/app/download/feedback/detail?id=${value.id?c}">详情查看</a>
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
			    $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
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
            var preValue = $(this).attr("cur_value");
            var setValue = $(this).attr("set_value");
            var property = $(this).attr("property");
            var postUrl = "${rc.contextPath}/auth/authorizer/app/download/feedback/modify.json";
            var postData = "id=" + id + "&" + property + "=" + setValue;
            var typeName = property == "feedbackStatus" ? "反馈处理状态" : "反馈记录状态";
            var curValueDesc = $(this).attr("cur_value_desc");
            var setValueDesc = $(this).attr("set_value_desc");
                var confirmMsg = "将下载反馈记录Id【" + id + "】的【" + typeName + "】由【" + curValueDesc + "】 改为【" + setValueDesc + "】?"; bootbox.confirm(confirmMsg, function(result) {
                    if (!result) {
                        return;
                    }
                    var posting = $.post(postUrl, postData);
                    posting.done(function(resp) {
                        if (!resp.data) { //
                            //show msg	 					
                            alert("操作失败,请联系该死的开发人员.Msg:" + resp.message);
                        }
                        else {
                            alert("操作成功!");
                            if (property == "feedbackStatus") {
                                $("#font_feedback_" + id).html("等待处理");
                                $(eventCtl).removeClass("delete");
                                $(eventCtl).removeClass("btn-danger");
                                $(eventCtl).attr("disabled", "disabled");
                                $(eventCtl).html("等待处理");
                            }
                            else if (property == "status") {
                                $("#tr_" + id).remove();
                            }
                        }
                    });
                });
            });
		</script>
</body>
</html>