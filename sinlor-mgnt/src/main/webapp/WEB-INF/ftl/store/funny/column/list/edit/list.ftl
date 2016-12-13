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
				<li>值得玩专栏列表草稿</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div id="div_query" class="well form-inline">
							<div class='main-action'>
								<form id="searchForm" name="searchForm" action="${rc.contextPath}/funny/client/edit/list" method="post">
									<input type="hidden" id="pager_page" name="pager.page"
										value="${para.pager.page}"> <input type="hidden"
										id="pager_size" name="pager.size" value="${para.pager.size}">
									<input type="hidden" id="pager_total" name="pager.total"
										value="${para.pager.total}">
									<div class="input-prepend">
										<span class="">类型</span> <@spring.formSingleSelect
										"para.ctype", ctypes, " " />
									</div>
									<div class="input-prepend">
										<span class="">发布状态</span>
										<select name="editStatus">
											<option value ="0" <#if para.editStatus==0>selected="selected"</#if>>未发布</option>
											<option value ="1" <#if para.editStatus==1>selected="selected"</#if>>已发布</option>
										</select>
									</div>
									<div class="input-prepend">
										<span class="">数据来源</span>
										<select name="source">
											<option value ="">所有数据</option>
											<option value ="0" <#if para.source==0>selected="selected"</#if>>编辑数据</option>
											<option value ="1" <#if para.source==1>selected="selected"</#if>>安卓数据</option>
										</select>
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>过滤
									</button>
								</form>
							</div>
						</div>
						<div class="table-content">
							<#if errMsg??>
	                            <font color="red">${errMsg}</font>
	                        </#if>
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>标题</th>
										<th>背景图</th>
										<th>昵称</th>
										<th>头像</th>
										<th>分类</th>
										<th>发布时间</th>
										<th>专栏详情</th>
										<th>状态</th>
										<th>来源</th>
										<th>编辑</th>
										<th>发布</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr id="tr_${value.id}">
										<input id="hidden_${value.id}" type="hidden"
											ad_id="${value.id!}" promote_status="${value.status}" />
										<td id="title_${value.id}"><#if value.title??> ${value.title} <#else> 无 </#if>
										</td>
										<td><a href="${value.img}" target="_blank"><img
												style="width: 150px; height: 70px" src="${value.img}"
												alt="${value.img}" /></a></td>
										<td>${authors[value.authorId?string]}</td>
										<td><a href="${authoricons[value.authorId?string]}" target="_blank"><img
												style="width: 66px; height: 66px" src="${authoricons[value.authorId?string]}"
												alt="${authoricons[value.authorId?string]}" /></a></td>
										<td><#if value.ctype ??>${ctypes[value.ctype?c]} <#else> 无 </#if></td>
										<td><#if value.relaeseTime ??> ${
											value.relaeseTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
											</#if></td>
										<td><a href="${rc.contextPath}/auth/store/funny/column/content/detail?id=${value.columnDetailId}&type=0">专栏详情-${value.columnDetailId}</a></td>
										<td><#if value.status == 0> <font
											id="font_status_${value.id}" color="green">√</font> <#else> <font
											id="font_status_${value.id}" color="red">X</font> </#if>
										</td>
										<td><#if value.source == 1> <font
											id="font_source_${value.id}" color="green">安卓</font> <#else> <font
											id="font_source_${value.id}" color="red">编辑</font> </#if>
										</td>
										<td>
											<#if value.editStatus==0>
												<a class="btn btn-info query" href="${rc.contextPath}/funny/client/edit/detail?id=${value.id}">修改</a>
											<#else>
												<a class="btn btn-info query" href="${rc.contextPath}/funny/client/list">专栏列表</a>
	                                        </#if>                                            
                                        </td>
                                        <td>
                                        	<#if value.editStatus==0>
                                        		<a class="btn btn-info query" href="${rc.contextPath}/funny/client/edit/publish?id=${value.id}">发布</a>
                                        	<#else>
                                        		<button class="btn btn-success delete" disabled>已发布</button>
                                        	</#if>
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
		    var name = $("#title"+id).text();
		    var curValue = $(this).attr("cur_value");
		    var setValue = $(this).attr("set_value");
		    var postUrl = "${rc.contextPath}/funny/client/edit/modify";
		    var actionMsg = curValue == 0 ? "是否将【"+name+"】从列表中移除?" : "是否将【"+name+"】添加到列表?";
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
		                var responseMsg = curValue == 0 ? "成功将【"+name+"】从列表中移除!" : "成功将【"+name+"】添加到上线列表!";
		                alert(responseMsg);
		                trCtl.remove();
		            }
		        });
		    });
		});
	</script>
</body>
</html>