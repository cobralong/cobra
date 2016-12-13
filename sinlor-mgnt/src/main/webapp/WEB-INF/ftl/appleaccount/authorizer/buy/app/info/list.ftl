<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-授权账号已购应用列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权账号已购应用列表</li>
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
										<span class="add-on">AppleId</span> <input name="appleId"
											value="" type="input" />
									</div>
									<div class="input-prepend">
										<span class="add-on">RootId</span> <input name="rootId"
											value="" type="input" />
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
						<#if errMsg> <font color="red">添加失败，原因：${errMsg}</font> </#if>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth/appleaccount/authorizer/buy/app/info/add"
									method="post">
									<div class="input-prepend">
										<span class="add-on">AppleId：</span> <select name="appleId">
											<#list appleAuthorizerAccountList as appleAuthorizerAccount>
											<#if para.appleId == appleAuthorizerAccount.appleId>
											<option selected value="${appleAuthorizerAccount.appleId}">${appleAuthorizerAccount.appleId}</option>
											<#else>
											<option value="${appleAuthorizerAccount.appleId}">${appleAuthorizerAccount.appleId}</option>
											</#if> </#list>
										</select>
									</div>
									<div class="input-prepend">
										<span class="add-on">RootId</span> <input class="span2 search"
											name="rootId" type="text" placeholder="RootId" value="">
									</div>
									<div class="input-prepend">
	                                    <span class="add-on">授权应用</span>
	                                    <input id="input_app_auth_status" class="span2" style="width:200px" name="authStatus" type="text" placeholder="应用授权状态" disabled value="">
	                                </div>
	                            	<br/>
									<div class="input-prepend">
										<span class="add-on">应用名称</span> <input id="input_app_name"
											class="span2" style="width: 200px" name="name" type="text"
											placeholder="应用名称" disabled value=""> <span
											class="add-on">OTA包</span> <input id="input_plist"
											class="span2" style="width: 600px" name="name" type="text"
											placeholder="OTA包" disabled value=""> <input
											id="input_cert" class="span2" style="width: 100px"
											name="name" type="text" placeholder="签名" disabled value="">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">搜索</span> <input
											class="span2 word-search" name="word" type="text"
											placeholder="搜索词" value=""> <select
											id="select_search_result" name="searchRootId">
										</select>
									</div>
									<button class="btn btn-info query">
										<i class="icon-search icon-white"></i>添加
									</button>
								</form>
							</div>
						</div>
						<br /> <br />
						<div class="table-content">
							<form id="itemForm" name="itemForm" action="" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>购买账号Id</th>
											<th>应用Id</th>
											<th>ItemId</th>
											<th>版本</th>
											<th>下载任务生成状态</th>
											<th>下载进度</th>
											<th>授权应用查看</th>
											<th>授权应用</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<#assign rootSimple = rootSimples[value.rootId?c]>
											<#assign statusRespMap = statusRespMaps[value.rootId?c]>
											<td>${value.appleId!}</td>
											<td><@hrefapp value.rootId, rootSimple.name/></td>
											<td>${ value.itemId?c }</td>
											<td>${ value.version!'' }</td>
											<td><#if value.taskGenFlag == 1> <font color="green">√</font>
												<#else> <font color="red">X</font> </#if>
											</td>
											<td><#if !(value.taskId)> 暂未生成下载任务 <#else> <a
												class="btn btn-info query"
												href="${rc.contextPath}/auth/authorizer/app/download/task/detail?id=${value.taskId?c}">下载任务</a>
												</#if>
											</td>
											<td><a class="btn btn-info query"
												href="${rc.contextPath}/auth/authorizer/app/ipa/list?rootId=${value.rootId?c}&appleAccountId=${value.accountId?c}">授权应用查看</a>
											</td>
											<td>
		                                        <#if statusRespMap.download==true>
		                                    		<span id="auth_status_span_${value.id}" class="btn btn-success" disabled>可下载</span>
		                                    	<#elseif statusRespMap.bought==true>
		                                    		<span id="auth_status_span_${value.id}" class="btn btn-warning" disabled>已购买/下载异常</span>
		                                    	<#else>
		                                    		<span id="auth_status_span_${value.id}" class="btn btn-danger" disabled>未购买</span>
		                                        </#if>
		                                    </td>
											<td><#if value.status == 0> <font
												id="status_font_${value.id}" color="green">√</font> <#else>
												<font id="status_font_${value.id}" color="red">X</font>
												</#if>
											</td>
											<td><#if value.status == 0> <span default_id=${value.id}
												status="${value.status}" class="btn delete btn-danger">删除</span>
												<#else> <span default_id=${value.id}
												status="${value.status}" class="btn delete btn-success">正常</span>
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
	</div>
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

		$("#startDatetimePicker, #endDatetimePicker").datetimepicker({
		    'date-format': 'yyyy-mm-dd',
		    stepYear: 1,
		    stepMonth: 1,
		    stepDay: 1,
		    startView: 2,
		    minView: 2,
		    maxView: 2
		});

		function modifyPage() {
		    $("#pager_page").val(1);
		}

		$("span.delete")
		    .bind(
		        "click",
		        function() {
		            event.preventDefault();
		            bootbox.setDefaults({
		                locale: "zh_CN"
		            });
		            var eventCtl = $(this);
		            var id = $(this).attr("default_id");
		            var preStatus = $(this).attr("status");
		            var status = preStatus == 0 ? -1 : 0;
		            var postUrl = "${rc.contextPath}/auth/appleaccount/authorizer/buy/app/info/modify.json";
		            var confirmMsg = "";
		            if (status == -1) {
		                confirmMsg = "删除当前记录";
		            } else {
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
		                        alert("操作失败,请联系该死的开发人员.Msg:" +
		                            resp.message);
		                    } else {
		                        alert("操作成功，已" + confirmMsg + "!");
		                        var fontCtl = $("#status_font_" + id);
		                        $(eventCtl).attr("status", status);
		                        if (status == 0) {
		                            $(eventCtl).attr("class",
		                                "btn delete btn-danger");
		                            $(eventCtl).html("删除");
		                            $(fontCtl).attr("color", "green");
		                            $(fontCtl).html("√");
		                        } else {
		                            $(eventCtl).attr("class",
		                                "btn delete btn-success");
		                            $(eventCtl).html("正常");
		                            $(fontCtl).attr("color", "red");
		                            $(fontCtl).html("X");
		                        }
		                    }
		                });
		            });
		        });
		$("input.word-search").bind("blur", function() {
		    var ctl = $(this);
		    var query = $(ctl).val();
		    var btn = $("button.add");
		    $.ajax({
		    	url:"http://iosapi.appchina.com/search/apps.json",
		    	type: "POST",
		    	dataType: "JSON",
		    	data: {
				        "query": query,
				        "size": 20,
				        "channel": "www.appchina.mgnt"
			    	},
		    	success: function(result){
			        if (result.data && result.data.resultList) {
			            var optionHtml = "";
			            for (index in result.data.resultList) {
			                optionHtml += "<option value='" + result.data.resultList[index].rootId + "'>" + result.data.resultList[index].name + "</option>";
			            }
			            $("#select_search_result").html(optionHtml);
			            $(btn).removeAttr("disabled");
			        }			    
		    	}
		    });
		});
		$("input.search").bind("blur", function() {
		    var ctl = $(this);
		    var rid = $(ctl).val();
		    var txtCtl = $("#input_app_name");
		    var txtStatus = $("#input_app_auth_status");
		    var btn = $("button.add");
		    var posting = $.post("${rc.contextPath}/auth/promote/search", {
		        "id": rid
		    });
		    posting.done(function(result) {
		        if (result.success) {
		            $(txtCtl).val(result.data.name);
		            $("#input_plist").val(result.data.downloadUrl);
		            $("#input_cert").val(result.data.certSerial);
		            if(result.data.authDownload){
 						$(txtStatus).val("可下载");
 					}else if(result.data.bought){
 						$(txtStatus).val("已购买/下载异常");
 					}else{
 						$(txtStatus).val("未购买");
 					}
		            $(btn).removeAttr("disabled");
		        } else {
		            $(txtCtl).val(result.message);
		            $(txtStatus).val("id无效,状态未知");
		            $(btn).attr("disabled", "disabled");
		        }
		    });
		});
	</script>
</body>
</html>