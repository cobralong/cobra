<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手苹果设备驱动列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>PC助手苹果设备驱动列表</li>
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
										<div class="input-prepend">
											<span class="add-on">系统架构</span>
											<select name="arch">
		                                		<#list arches?keys as key>
		                                			<#if para.arch == key>
		                                				<option selected value="${key}">${arches[key]}</option>
		                                			<#else>
		                                				<option value="${key}">${arches[key]}</option>
		                                			</#if>
		                                		</#list>
		                                	</select>
										</div>
										<div class="input-prepend">
											<span class="add-on">状态</span>
											<select name="status">
		                                		<#list status?keys as key>
		                                			<#if para.status == key>
		                                				<option selected value="${key}">${status[key]}</option>
		                                			<#else>
		                                				<option value="${key}">${status[key]}</option>
		                                			</#if>
		                                		</#list>
		                                	</select>
										</div>
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth/pcsuite/apple/device/driver/info/add"
									enctype="multipart/form-data" method="post">
									<div class="input-prepend">
										<span class="add-on">系统架构</span> <select name="arch">
											<#list pureArches?keys as key>
											<option value="${key}">${pureArches[key]}</option> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">文件名称：</span> <input class="span3"
											name="name" type="text" placeholder="文件名称" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">相关描述：</span> <input class="span4"
											name="desc" type="text" placeholder="相关描述" value="">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">文件版本号：</span> <input class="span2"
											name="version" type="text" placeholder="文件版本号" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">文件所属iTunes版本号：</span> <input
											class="span2" name="itunesVersion" type="text"
											placeholder="文件所属iTunes版本号" value="">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">iTunes驱动:</span>
										<div class="fileinput fileinput-new" data-provides="fileinput">
											<div class="input-prepend">
												<span class="btn btn-default btn-file"><span
													class="fileinput-new">Select Driver:</span> <span
													class="fileinput-exists">Change</span><input type="file"
													name="driver"></span> <a href="#"
													class="btn btn-default fileinput-exists"
													data-dismiss="fileinput">Remove</a>
											</div>
										</div>
									</div>
									<br />
									<div class="input-prepend">
										<span class="add-on">驱动文件地址：</span> <input class="span4"
											name="url" type="text" placeholder="文件CDN地址" value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">驱动文件Md5值：</span> <input class="span2"
											name="md5" type="text" placeholder="驱动文件Md5值" value="">
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>添加
									</button>
								</form>
								<#if errMsg> <font color="red">添加失败，原因：${errMsg}</font> </#if>
							</div>
						</div>
						<div class="table-content">
							<#-- table -->
							<h4>
								当前有记录<font color="green">${total}</font>条
								<h4>
									<form id="itemForm" name="itemForm" action="" method="post">
										<table
											class="table table-striped table-bordered table-condensed"
											id="itemTable">
											<thead>
												<tr>
													<th>Id</th>
													<th>文件名称</th>
													<th>相关描述</th>
													<th>支持架构</th>
													<th>文件版本</th>
													<th>iTunes版本</th>
													<th>文件地址</th>
													<th>文件MD5值</th>
													<th>状态</th>
													<th>操作</th>
													<th>明细</th>
												</tr>
											</thead>
											<tbody class="sortable">
												<#if datas??> <#list datas as data>
												<tr id="tr_${data.id?c}">
													<td>${data.id?c}</td>
													<td>${data.name! }</td>
													<td>${data.desc! }</td>
													<td>${data.arch! }</td>
													<td>${data.version! }</td>
													<td>${data.itunesVersion! }</td>
													<td><@shorthref data.url!/></td>
													<td><@shortdesc data.md5!/></td>
													<td><#if data.status == 0> <font
														id="status_font_${data.id}" color="green">√</font> <#else>
														<font id="status_font_${data.id}" color="red">X</font>
														</#if>
													</td>
													<td><#if data.status == 0> <span default_id="${data.id}" driver_name="${data.name}"
														set_status="-1" class="btn delete btn-danger">删除</span>
														<#else> <span default_id="${data.id}"
														set_status="0" class="btn delete btn-success">正常</span>
														</#if>
													</td>
													<td><a class="btn btn-info query" href="${rc.contextPath}/auth/pcsuite/apple/device/driver/detail/info/list?driverId=${data.id}">详情</a></td>
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
	<script type="text/javascript"
		src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript"
		src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
	<script>
		$(document).ready(
				function() {
					var currPageDiv = "#pager_page";
					var totalDiv = "#pager_total";
					var sizeDiv = "#pager_size";
					$.manage.pageList(".pagination", "#searchForm",
							currPageDiv, totalDiv, sizeDiv);
				});
		

		$("span.delete").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var id = $(this).attr("default_id");
		    var setStatus = $(this).attr("set_status");
		    var name = $(this).attr("driver_name");
		    var trCtrl = $("#tr_" + id);
		    var statusDesc = setStatus == -1 ? "删除": "正常";
		    bootbox.confirm("确认将记录【" + name + "】置为" + statusDesc + "状态？", function(result) {
		        if (!result) {
		            return;
		        }
		        var postUrl = "${rc.contextPath}/auth/pcsuite/apple/device/driver/info/modify";
		        var postData = "id=" + id + "&status=" + setStatus;
		        var posting = $.post(postUrl, postData);
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg	 					
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            }
		            else {
		                alert("修改成功.");
		                $(trCtrl).remove();
		            }
		        });
		    });
		});
	</script>
</body>
</html>