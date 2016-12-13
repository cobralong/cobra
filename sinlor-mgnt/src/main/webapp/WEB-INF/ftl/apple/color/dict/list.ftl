<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-苹果设备颜色字典列表</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>苹果设备颜色字典列表</li>
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
								<form id="addForm" name="addForm"
									action="${rc.contextPath}/auth//apple/color/dict/add"
									method="post">
									<div class="input-prepend">
										<span class="add-on">设备颜色描述</span> <input id="input_app_name"
											class="span2" name="color" type="input" placeholder="设备颜色"
											value="">
									</div>
									<div class="input-prepend">
										<span class="add-on">颜色RGB值</span> <input id="input_app_name"
											class="span2" name="rgb" type="input"
											placeholder="设备颜色RGB值以#开头" value="">
									</div>
									<button class="btn btn-info add">
										<i class="icon-search icon-white"></i>增加
									</button>
									<div id="addInfoDiv" errMsg="${errMsg}">
										<#if errMsg??> <font>${errMsg}</font> </#if>
									</div>
								</form>
							</div>
						</div>
						<div class="table-content">
							<#-- table -->
							<form id="itemForm" name="itemForm" method="post">
								<table
									class="table table-striped table-bordered table-condensed"
									id="itemTable">
									<thead>
										<tr>
											<th>Id</th>
											<th>颜色[RGB]</th>
											<th>颜色[描述]</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody class="sortable">
										<#if values??> <#list values as value>
										<tr>
											<td>${value.id?c}</td>
											<td style="color: ${value.rgb}">${value.rgb}</td>
											<td class="edit_td" pid="${value.id?c}"
												for="span_color_${value.id?c}" property="rgb"><label
												class="span4"> <span id="span_color_${value.id?c}"
													class="text-info" preValue="${value.color!}">${
														value.color! }</span>
											</label></td>
											<td><#if value.status == 0> <font
												id="status_font_${data.id}" color="green">√</font> <#else> <font
												id="status_font_${data.id}" color="red">X</font> </#if>
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
		<script>
    $('.edit_td').click(function() {
        var id = $(this).attr("pid");
        var spanId = $(this).attr("for");
        var spanCtr = $("#" + spanId);
        var preValue = $(spanCtr).attr("preValue");
        var property = $(this).attr("property");
        var input = $('<input id="attribute" type="text" class="span4" preValue="' + preValue +
            '" value="' + preValue + '" />')
        $(spanCtr).text('').append(input);
        input.select();
        input.blur(function() {
            var preValue = $(this).parent().attr("preValue");
            var curValue = $('#attribute').val();
            if (curValue == "") {
                $('#attribute').parent().text(preValue);
                $('#attribute').remove();
                return;
            }
            if (curValue == preValue) {
                var text = $('#attribute').val();
                $('#attribute').parent().text(preValue);
                $('#attribute').remove();
                return;
            }
            var confirmMsg = "将颜色描述由【" + preValue + "】改为【" + curValue + "】?";
            bootbox.confirm(confirmMsg, function(result) {
                if (result) {
                    var postUrl = "${rc.contextPath}/auth/apple/color/dict/modify.json";
                    var posting = $.post(postUrl, {'id': id, 'color': curValue});                    
                    posting.done(function(errMsg) {
                        if (errMsg) { //
                            //show msg	 					
                            alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                            $('#attribute').parent().text(preValue);
                            $('#attribute').remove();
                        }
                        else {
                            alert("修改成功.");
                            $('#attribute').parent().attr("preValue",
                                curValue);
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