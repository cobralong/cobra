<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台-客户端用户中心面板信息列表</title> <#include
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
			<li>客户端用户中心面板信息列表</li>
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
									<span class="add-on">客户端</span> <select> <#list
										bundleIds?keys as key>
										<option value="${key}">${bundleIds[key]}</option> </#list>
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
									action="${rc.contextPath}/auth/store/client/usercenter/configure/add"
									method="post">
									<div class="input-prepend">
										<span class="add-on">客户端</span> <select id="sel_bundleid"
											name="bundleId" type='add' class="client_choose">
											<#list pureBundleIds?keys as key>
											<option value="${key}">${bundleIds[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">客户端版本号</span> <select id="sel_version"
											name="clientVersion" type='add'>
											<option value="all">全部</option>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">展示阶段</span> <select id="sel_market_flag"
											name="marketFlag" class="client_choose"> <#list
											marketFlags?keys as key>
											<option value="${key}">${marketFlags[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">展示状态</span> <select id="sel_default_flag"
											name="defaultFlag" class="client_choose"> <#list
											defaultFlags?keys as key>
											<option value="${key}">${defaultFlags[key]}</option> </#list>
										</select><span class="small">在客户端未严格按过滤条件获取数据时，进行展示</span>
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">用户中心名称:</span> <input type="text"
											name="title" class="span3" value="${para.title}">
									</div>
									<div class="input-prepend">
										<span class="add-on">用户中心底部文字:</span> <input type="text"
											name="buttom" class="span3" value="${para.buttom}">
									</div>

									<span class="add-on">Icon:</span>
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
											<th>用户中心名称</th>
											<th>用户中心图标</th>
											<th>展示阶段</th>
											<th>默认展示</th>
											<th>用户中心底部文字</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.id}</td>
											<td>
												${value.bundleId}---${bundleIds[value.bundleId]}---${value.clientVersion}
												<#list bundleIdVersionList as bundleIdVersion> <#if
												value.bundleId == bundleIdVersion.bundleId &&
												value.clientVersion == bundleIdVersion.clientVersion>
												---${audits[bundleIdVersion.auditStatus?c]} </#if> </#list>
											</td>
											<td class="edit_td" default_id="${value.id?c}"
												property="title"
												td_name="${value.bundleId}---${bundleIds[value.bundleId]}---${value.clientVersion}"
												for_class="span3"><label for="name" class="span3"><span
													id="span_title_${value.id?c}" class="text-info"
													preValue="${value.title}">${ value.title! }</span></label></td>
											<td><img width="25px" height="25px"
												src="${value.iconUrl}" /></td>
											<td><#list marketFlags?keys as key> <#if key ==
												value.marketFlag> ${marketFlags[key]}</#if> </#list></td>
											<td><#list defaultFlags?keys as key> <#if key ==
												value.defaultFlag> ${defaultFlags[key]}</#if> </#list></td>
											<td class="edit_td" default_id="${value.id?c}"
												property="buttom"
												td_name="${value.bundleId}---${bundleIds[value.bundleId]}---${value.clientVersion}"
												for_class="span3"><label for="name" class="span3"><span
													id="span_buttom_${value.id?c}" class="text-info"
													preValue="${value.buttom}">${ value.buttom! }</span></label></td>
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
			$(document).on('change', 'select.client_choose', function() {
			    var selCtr = $(this);
			    var bundleId = $(this).val();
			    var optionHtml = "<option value='all'>全部</option>";
			    var postUrl = "${rc.contextPath}/auth/store/client/listclientversions.json";
			    if (bundleId == "all") {
			        $("#sel_version").html(optionHtml);
			    }
			    else {
			        var posting = $.post(postUrl, {
			            "bundleId": bundleId
			        });
			        posting.done(function(data) {
			            for (version in data.data) {
			                optionHtml += "<option value='" + data.data[version] + "'>" + data.data[version] + "</option>";
			            }
			            $("#sel_version").html(optionHtml);
			        });
			    }
			});
			$('.edit_td').click(function() {
			    var id = $(this).attr("default_id");
			    var property = $(this).attr("property");
			    var spanCtr = $("#span_" + property + "_" + id);
			    var preValue = $(spanCtr).attr("preValue");
			    var tdName = $(this).attr("td_name");
			    var spanClass = $(this).attr('for_class')
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
			        var typeName = (property == "buttom") ? "用户中心底部说明" : "用户中心标题";
			        bootbox.confirm("将应用【" + tdName + "】的" + typeName + "由【" + preValue + "】改为【" + curValue + "】?", function(result) {
			            if (result) {
			                var postUrl = "${rc.contextPath}/auth/store/client/usercenter/configure/modify.json";
			                var postData = "id=" + id + "&" + property + "=" + curValue;
			                var posting = $.post(postUrl, postData);
			                posting.done(function(errMsg) {
			                    if (errMsg) { //
			                        //show msg	 					
			                        alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
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
		</script>
</body>
</html>