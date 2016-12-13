<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台编辑推荐</title> <#include "lib/base_source.ftl"> <#include
"ftl_macro.ftl">
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
				<li>编辑推荐列表</li>
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
										<span class="">状态</span> <@spring.formSingleSelect
										"para.status", status, " " />
									</div>
									<span class="">开始时间</span>
									<div id="startDatetimePicker"
										class="input-append date datepicker"
										data-date-format="yyyy-mm-dd hh:ii:00"
										data-date-autoclose="true">
										<input class="span2" id="search_startDateString" name="st"
											size="16" type="text" value="${para.st}"> <span
											class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="">结束时间</span>
									<div id="endDatetimePicker"
										class="input-append date datepicker"
										data-date-format="yyyy-mm-dd hh:ii:00"
										data-date-autoclose="true">
										<input class="span2" id="endDateString" name="et" size="16"
											type="text" value="${para.et}"> <span class="add-on"><i
											class="icon-calendar"></i></span>
									</div>
									<button class="btn btn-info search">
										<i class="icon-search icon-white"></i>过滤
									</button>
									<button id="btn_now_promote" class="btn btn-info search">
										<i class="icon-search icon-white"></i>正在推广
									</button>
								</form>
							</div>
						</div>
						<div class="table-content">
							<#-- table --> <#if para.status == 0>
							<p>
								在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表:
							</p>
							</#if>
							<form id="itemForm" name="itemForm" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>RootID</th>
											<th>Icon</th>
											<th>下载地址</th>
											<th>操作</th>
											<th>Rank</th>
											<th>渠道</th>
											<th>状态</th>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>授权下载状态</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr id="tr_${value.rootId}" class="gr_${value.rootId?c}">
											<#assign rootSimple = rootSimples[value.rootId?c]>
											<input id="hidden_${value.rootId}" type="hidden"
												root_id="${value.rootId!}" root_title="${rootSimple.name}" />
											<#assign rowspan=1> <#if value.promotes??> <#if
											value.promotes?size gt 1> <#assign rowspan =
											value.promotes?size> </#if> </#if>
											<td rowspan="${rowspan?c}"><@hrefapp value.rootId,
												rootSimple.name/></td>
											<td rowspan="${rowspan?c}"><@appicon rootSimple.icon,
												rootSimple.name/></td>
											<td rowspan="${rowspan?c}"><@hrefipaplist
												rootSimple.downloadUrl!''/></td>
											<td rowspan="${rowspan?c}">
												<button class="btn btn-danger groupdown"
													title="${rootSimple.name!}" root_id="${value.rootId?c}"<#if
													para.status == -1>disable</#if>> 所有渠道停止</button>
											</td> <#if value.promotes??> <#list value.promotes as promote>
											<#if promote_index == 0>
											<td>${ promote.rank! }</td>
											<td>${ promote.channel! }</td>
											<td><#if promote.status == 0> <span
												id="status_span_${promote.id}" class="btn btn-success"
												disabled>正常</span> <#else> <span
												id="status_span_${promote.id}" class="btn btn-danger"
												disabled>删除</span> </#if>
											</td>
                                                <td class="edit_starttime" pid="${promote.id?c}">
                                                    <span id="p_starttime_${promote.id?c}" actionChannel = "${promote.channel!}" preValue="<#if promote.startTime ??>${ promote.startTime?string("yyyy-MM-dd HH:mm:ss") }</#if>"><#if promote.startTime ??>${ promote.startTime?string("yyyy-MM-dd HH:mm:ss") }<#else>-</#if></span>
                                                </td>
                                                <td class="edit_endtime" pid="${promote.id?c}">
                                                    <span id="p_endtime_${promote.id?c}" actionChannel = "${promote.channel!}" preValue="<#if promote.endTime ??>${ promote.endTime?string("yyyy-MM-dd HH:mm:ss") }</#if>"><#if promote.endTime ??>${ promote.endTime?string("yyyy-MM-dd HH:mm:ss") }<#else>-</#if></span>
                                                </td>
                                                <td>${rootIdBoughtMap[value.rootId?c]}</td>
											<#--<td><#if promote.startTime ??> ${-->
												<#--promote.startTime?string("yyyy-MM-dd HH:mm:ss") } <#else> --->
												<#--</#if></td>-->
											<#--<td><#if promote.endTime ??> ${-->
												<#--promote.endTime?string("yyyy-MM-dd HH:mm:ss") } <#else> --->
												<#--</#if></td> -->
                                            </#if>
                                            </#list> </#if>
										</tr>
										<#if value.promotes??> <#if value.promotes?size gt 1> <#list
										value.promotes as promote> <#if promote_index != 0>
										<tr class="gr_${value.rootId?c}">
											<td>${ promote.rank! }</td>
											<td>${ promote.channel! }</td>
											<td><#if promote.status == 0> <span
												id="status_span_${promote.id}" class="btn btn-success"
												disabled>正常</span> <#else> <span
												id="status_span_${promote.id}" class="btn btn-danger"
												disabled>删除</span> </#if>
											</td>
                                            <td class="edit_starttime" pid="${promote.id?c}">
                                                <span id="p_starttime_${promote.id?c}" actionChannel = "${promote.channel!}" preValue="<#if promote.startTime ??>${ promote.startTime?string("yyyy-MM-dd HH:mm:ss") }</#if>"><#if promote.startTime ??>${ promote.startTime?string("yyyy-MM-dd HH:mm:ss") }<#else>-</#if></span>
                                            </td>
                                            <td class="edit_endtime" pid="${promote.id?c}">
                                                <span id="p_endtime_${promote.id?c}" actionChannel = "${promote.channel!}" preValue="<#if promote.endTime ??>${ promote.endTime?string("yyyy-MM-dd HH:mm:ss") }</#if>"><#if promote.endTime ??>${ promote.endTime?string("yyyy-MM-dd HH:mm:ss") }<#else>-</#if></span>
                                            </td>
											<td>${rootIdBoughtMap[value.rootId?c]}</td>
											<#--<td><#if promote.startTime ??> ${-->
												<#--promote.startTime?string("yyyy-MM-dd HH:mm:ss") } <#else> --->
												<#--</#if></td>-->
											<#--<td><#if promote.endTime ??> ${-->
												<#--promote.endTime?string("yyyy-MM-dd HH:mm:ss") } <#else> --->
												<#--</#if></td>-->
										</tr>
										</#if> </#list> </#if> </#if> </#list> </#if>
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

		$("#btn_now_promote").bind("click", function() {
			$("#search_startDateString").val("");
		});

		$("button.groupdown")
				.bind(
						"click",
						function() {
							event.preventDefault();
							bootbox.setDefaults({
								locale : "zh_CN"
							});
							var rootId = $(this).attr("root_id");
							var title = $(this).attr("title");
							var postUrl = "${rc.contextPath}/auth/promote/offlinegrouppromote";

							bootbox.confirm("是否将应用" + title
									+ "在所有渠道所有时间段的推荐停止[不可逆]？",
									function(result) {
										if (!result) {
											return;
										}
										var posting = $.post(postUrl, {
											'rootId' : rootId
										});
										posting.done(function(errMsg) {
											if (errMsg) {//
												//show msg	 					
												alert("操作失败,请联系该死的开发人员.Msg:"
														+ errMsg);
											} else {
												alert("已成功将应用" + title
														+ "在所有渠道所有时间段的推荐停止！");
												$("tr.gr_" + rootId).remove();
											}
										});
									});

						});


        $('.edit_starttime').click(function() {
            var pid = $(this).attr("pid");
            var preValue = $('#p_starttime_'+pid).attr("preValue");
            var actionChannel = $('#p_starttime_'+pid).attr("actionChannel");
            var input = $('<input id="starttimeattribute" type="text" preValue="' + preValue + '" value="' + preValue + '" />')
            $('#p_starttime_'+pid).text('').append(input);
            input.select();
            input.blur(function() {
                var preValue = $(this).parent().attr("preValue");
                var hidden = $("#hidden_"+pid);
                var appTitle = $(hidden).attr("app_title");
                var curValue = $('#starttimeattribute').val();
                if(curValue == ""){
                    $(this).val(preValue);
                    $(this).focus();
                }
                if(curValue == preValue){
                    $('#starttimeattribute').parent().text(preValue);
                    $('#starttimeattribute').remove();
                    return;
                }
                bootbox.confirm("将应用"+appTitle+"的推荐开始时间由"+preValue+"------>"+curValue+"?", function(result) {
                    if(result){
                        bootbox.confirm("是否应用到全部渠道?", function(isall) {
                            var postUrl = "${rc.contextPath}/auth/promote/modifystarttime";
                            var posting = $.post(postUrl, {'id': pid, "st": curValue, "allchannel": isall});
                            posting.done(function(errMsg){
                                if(errMsg){//
                                    //show msg
                                    alert("操作失败,请联系开发人员.Msg:"+errMsg);
                                    $('#starttimeattribute').parent().text(preValue);
                                    $('#starttimeattribute').remove();
                                }else{
                                    alert("修改成功.");
                                    $('#starttimeattribute').parent().text(curValue);
                                    $('#p_starttime_'+pid).attr("preValue", curValue);
                                    $('#starttimeattribute').remove();
                                }
                            });
                        });
                    }else{
                        $('#starttimeattribute').parent().text(preValue);
                        $('#starttimeattribute').remove();
                    }
                });
            });
        });

        $('.edit_endtime').click(function() {
            var pid = $(this).attr("pid");
            var preValue = $('#p_endtime_'+pid).attr("preValue");
            var actionChannel = $('#p_endtime_'+pid).attr("actionChannel");
            var input = $('<input id="endtimeattribute" type="text" preValue="' + preValue + '" value="' + preValue + '" />')
            $('#p_endtime_'+pid).text('').append(input);
            input.select();
            input.blur(function() {
                var preValue = $(this).parent().attr("preValue");
                var hidden = $("#hidden_"+pid);
                var appTitle = $(hidden).attr("app_title");
                var curValue = $('#endtimeattribute').val();
                if(curValue == ""){
                    $(this).val(preValue);
                    $(this).focus();
                }
                if(curValue == preValue){
                    $('#endtimeattribute').parent().text(preValue);
                    $('#endtimeattribute').remove();
                    return;
                }
                bootbox.confirm("将应用"+appTitle+"的推荐开始时间由"+preValue+"------>"+curValue+"?", function(result) {
                    if(result){
                        bootbox.confirm("是否应用到全部渠道?", function(isall) {
                            var postUrl = "${rc.contextPath}/auth/promote/modifyendtime";
                            var posting = $.post(postUrl, {'id': pid, "et": curValue, "allchannel": isall});
                            posting.done(function(errMsg){
                                if(errMsg){//
                                    //show msg
                                    alert("操作失败,请联系开发人员.Msg:"+errMsg);
                                    $('#endtimeattribute').parent().text(preValue);
                                    $('#endtimeattribute').remove();
                                }else{
                                    alert("修改成功.");
                                    $('#endtimeattribute').parent().text(curValue);
                                    $('#p_endtime_'+pid).attr("preValue", curValue);
                                    $('#endtimeattribute').remove();
                                }
                            });
                        });
                    }else{
                        $('#endtimeattribute').parent().text(preValue);
                        $('#endtimeattribute').remove();
                    }
                });
            });
        });

		$("#startDatetimePicker, #endDatetimePicker").datetimepicker({
			timeFormat : 'yyyy-mm-dd',
			stepYear : 1,
			stepMonth : 1,
			stepDay : 1
		});
	</script>
</body>
</html>