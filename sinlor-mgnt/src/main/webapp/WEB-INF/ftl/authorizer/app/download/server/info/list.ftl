<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-授权应用下载服务器列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权应用下载服务器列表</li>
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
										<span class="add-on">状态</span> <@spring.formSingleSelect
										"para.status", status, " " />
									</div>
									<button class="btn btn-info query" onClick="modifyPage()">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth/authorizer/app/download/server/info/add"
									enctype="multipart/form-data" method="post">
									<div class="input-prepend">
										<span class="add-on">名称</span>
										<input class="input-ext span2" name="name" value=""/>
									</div>
									<div class="input-prepend">
										<span class="add-on">服务器地址</span>
										<input class="input-ext span3" name="url" value=""/>
									</div>
									<div class="input-prepend">
										<span class="add-on">测试响应地址</span>
										<input class="input-ext span3" name="testUrl" value=""/>
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>添加
									</button>
								</form>
								<#if addMsg> <font color="red">添加失败，原因：${addMsg}</font> </#if>
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
											<th>服务器名称</th>
											<th>服务器地址</th>
											<th>服务器测试响应地址</th>
											<th>发送次数/成功次数</th>
											<th>反馈次数/成功次数</th>
											<th>操作</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.id?c}</td>
											<td class="edit_td" default_id="${value.id?c}" property="name"
												td_name="${value.name}" for_class="span2"><label
												for="name" class="span2"><span
													id="span_name_${value.id?c}" class="text-info"
													preValue="${value.name!}">${ value.name! }</span></label></td>
											<td class="edit_td" default_id="${value.id?c}" property="url"
												td_name="${value.name}" for_class="span4"><label
												for="url" class="span4"><span
													id="span_url_${value.id?c}" class="span4 text-info"
													preValue="${value.url!}">${ value.url! }</span></label></td>
											<td class="edit_td" default_id="${value.id?c}"
												property="testUrl" td_name="${value.name}" for_class="span4"><label
												for="testUrl" class="span4"><span
													id="span_testUrl_${value.id?c}" class="text-info span4"
													preValue="${value.testUrl!}">${ value.testUrl! }</span></label></td>
											<td>${value.pushTimes?c} / <font color="green">${value.pushSuccessTimes?c}</font></td>
											<td>${value.feedbackTimes?c} / <font color="green">${value.feedbackSuccessTimes?c}</font></td>
											<td>
												<#if value.status == 0> 
													<font id="status_font_${value.id}" color="green">√</font>
												<#else>
													<font id="status_font_${value.id}" color="red">X</font>
												</#if>
											</td>
											<td>
												<#if value.status == 0>
													<span default_id=${value.id?c} set_status="-1" pre_status = "0" class="btn delete btn-danger">删除</span>
												<#else>
													<span default_id=${value.id?c} set_status="0" pre_status = "-1" class="btn delete btn-success">正常</span>
												</#if>
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
			$('.edit_td').click(function() {
			    var id = $(this).attr("default_id");
			    var property = $(this).attr("property");
			    var spanCtr = $("#span_" + property + "_" + id);
			    var preValue = $(spanCtr).attr("preValue");
			    var driverName = $(this).attr("td_name");
			    var spanClass= $(this).attr('for_class')
			    var input = $('<input id="attribute" type="text" class="' + spanClass + '" preValue="' + preValue + '" value="' + preValue + '" />')
			    $(spanCtr).text('').append(input);
			    input.select();
			    input.blur(function() {
			        var curValue = $('#attribute').val();
			        if (curValue == preValue) {
			            var text = $('#attribute').val();
			            $('#attribute').parent().text(preValue);
			            $('#attribute').remove();
			            return;
			        }
			        if (curValue == "") {
			            $(this).val(preValue);
			            $(this).focus();
			            return;
			        }
			        var typeName = (property == "name") ? "服务器名称" : (property == "url") ? "服务器地址" : "测试响应地址";
			        bootbox.confirm("将下载服务【" + driverName + "】的" + typeName + "由【" + preValue + "】改为【" + curValue + "】?", function(result) {
			            if (result) {
			                var postUrl = "${rc.contextPath}/auth/authorizer/app/download/server/info/modify.json";
			                var postData = "id=" + id + "&" + property + "=" + curValue;
			                var posting = $.post(postUrl, postData);
			                posting.done(function(resp) {
			                    if (!resp.data) { //
			                        //show msg	 					
			                        alert("操作失败,请联系该死的开发人员.Msg:" + resp.message);
			                        $('#attribute').parent().text(preValue);
			                        $('#attribute').remove();
			                    }
			                    else {
			                        alert("修改成功.");
			                        $('#attribute').parent().attr("preValue", curValue);
			                        $('#attribute').parent().text(curValue);
			                        $('#attribute').remove();
			                    }
			                });
			            }
			            else {
			                $('#attribute').parent().text(preValue);
			                $('#attribute').remove();
			            }
			        });
			    });
			});
			
			$("span.delete").bind("click", function() {
			    event.preventDefault();
			    bootbox.setDefaults({
			        locale: "zh_CN"
			    });
			    var eventCtl = $(this);
			    var id = $(this).attr("default_id");
			    var preStatus = $(this).attr("pre_status");
			    var setStatus = $(this).attr("set_status");
			    var postUrl = "${rc.contextPath}/auth/authorizer/app/download/server/info/modify.json";
			    var confirmMsg = "";
			    if (setStatus == -1) {
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
			            "status": setStatus
			        });
			        posting.done(function(resp) {
			            if (!resp.data) { //
			                //show msg	 					
			                alert("操作失败,请联系该死的开发人员.Msg:" + resp.message);
			            }
			            else {
			                alert("操作成功，已" + confirmMsg + "!");
			                var fontCtl = $("#status_font_" + id);
			                $(eventCtl).attr("pre_status", setStatus);
			                $(eventCtl).attr("set_status", preStatus);
			                if (setStatus == 0) {
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