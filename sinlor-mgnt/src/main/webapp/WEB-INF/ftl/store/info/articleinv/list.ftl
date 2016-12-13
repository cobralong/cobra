<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>资讯干预列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <button class="btn queryToggle  btn-info">
                                <span class="glyphicon glyphicon-search"></span>查询框
                            </button>
                            <div id="div_query" class="well form-inline">
                                <div class='main-action'>
                                    <form id="searchForm" name="searchForm" action="" method="post">
                                        <input type="hidden" id="pager_page" name="pager.page"
                                               value="${para.pager.page}"/>
                                        <input type="hidden" id="pager_size" name="pager.size"
                                               value="${para.pager.size}"/>
                                        <input type="hidden" id="pager_total" name="pager.total"
                                               value="${para.pager.total}"/>
		                                <div class="input-prepend">
		                                    <span class="add-on">推荐客户端</span>
		                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
		                                </div>
                                        <div class="input-prepend">
                                            <span class="">状态</span>
                                        <@spring.formSingleSelect "para.status", status, " " />
                                        </div>
                                        <button class="btn btn-info search">
                                            <i class="icon-search icon-white"></i>过滤
                                        </button>
                                    </form>
                                </div>
                            </div>

                            <br/>
                            <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>

                            <div id="div_add" class="well form-inline">
                                <div class='main-action'>
                                    <form id="searchForm" name="searchForm"
                                          action="${rc.contextPath}/auth/store/article/recom/intervention/add"
                                          method="post">
		                                <div class="input-prepend">
		                                    <span class="add-on">推荐客户端</span>
		                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
		                                </div>
                                        <div class="input-prepend">
                                            <span class="add-on">ID</span>
                                            <input class="span2 info-search" name="articleId" type="text" placeholder="ID" value="">
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">资讯名称</span>
                                            <input id="input_article_name" class="span2" style="width:600px" name="name"
                                                   type="text"
                                                   placeholder="资讯名称" disabled value="">
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">位置</span>
                                            <input class="span1" size="10" name="position" type="text" placeholder="position"
                                                   value="">
                                        </div>
                                        <button class="btn btn-info add" disabled>
                                            <i class="icon-search icon-white"></i>增加
                                        </button>
                                        <div id="addInfoDiv" errMsg="${errMsg!''}">
                                        <#if errMsg??>
                                            <font color='red'>${errMsg!''}</font>
                                        </#if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                	<th>客户端</th>
                                    <th>资讯</th>
                                    <th>Icon</th>
                                    <th>地址</th>
                                    <th>排行</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <input id="hidden_${value.id}" type="hidden" title="${value.title}"
                                               status="${value.status}"/>
                                        <#assign articleSimple = articleSimples[value.articleId?c]>
                                        <td>${bundleIds[value.bundleId]}</td>
                                        <td>
                                        ${articleSimple.title}
                                        </td>
                                        <td>
                                            <@appicon articleSimple.icon, articleSimple.title/>
                                        </td>
                                        <td>
                                            <@hrefipaplist value.articleUrl!''/>
                                        </td>
                                        <td class="edit_td" pid="${value.id?c}">
                                            <label for="name" class="span1"><span id="p_${value.id?c}" class="text-info"
                                                                                  preValue="${value.position?c}">${ value.position! }</span></label>
                                        </td>
                                        <td>
                                            <#if value.status == 0>
                                                <font id="font_status_${value.id}" color="green">√</font>
                                            <#else>
                                            	<font id="font_status_${value.id}" color="red">X</font>
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.status == 0>
	                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" default_id="${value.id?c}" set_value="-1" cur_value="0">
	                                        		删除
	                                			</button>
                                			<#else>                                			
                                        		<button id="btn_delete_${value.id?c}" class="btn btn-success delete" default_id="${value.id?c}" set_value="0" cur_value="-1">
                                        			恢复                             	
                                				</button>
	                                		</#if>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
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
<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
<script>
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });

    $("input.info-search").bind("blur", function () {
        var ctl = $(this);
        var rid = $(ctl).val();
        var txtCtl = $("#input_article_name");
        var btn = $("button.add");
        var posting = $.post("${rc.contextPath}/auth/store/article/search", {"id": rid});
        posting.done(function (result) {
            if (result.success) {
                $(txtCtl).val(result.data.title);
                $(btn).removeAttr("disabled");
            } else {
                //$(ctl).focus();
                $(txtCtl).val(result.message);
                $(btn).attr("disabled", "disabled");
            }
        });
    });

    $('.edit_td').click(function () {
        var pid = $(this).attr("pid");
        var preValue = $('#p_' + pid).attr("preValue");
        var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
        $('#p_' + pid).text('').append(input);
        input.select();
        input.blur(function () {
            var preValue = $(this).parent().attr("preValue");
            var hidden = $("#hidden_" + pid);
            var curValue = $('#attribute').val();
            if (curValue == "") {
                $(this).val(preValue);
                $(this).focus();
                return;
            }
            if (curValue == preValue) {
                var text = $('#attribute').val();
                $('#attribute').parent().text(preValue);
                $('#attribute').remove();
                return;
            }
            bootbox.confirm("将推荐干预位置由" + preValue + "------>" + curValue + "?", function (result) {
                if (result) {
                    var postUrl = "${rc.contextPath}/auth/store/article/recom/intervention/modify";
                    var posting = $.post(postUrl, {'id': pid, "position": curValue});
                    posting.done(function (errMsg) {
                        if (errMsg) {//
                            //show msg
                            alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                            $('#attribute').parent().text(preValue);
                            $('#attribute').remove();
                        } else {
                            alert("修改成功.");
                            $('#attribute').parent().text(curValue);
                            $('#attribute').remove();
                        }
                    });
                } else {
                    $('#attribute').parent().text(preValue);
                    $('#attribute').remove();
                }
            });
        });
    });

    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var trCtl = $("#tr_" + id);
        var postUrl = "${rc.contextPath}/auth/store/article/recom/intervention/modify";
        var curStatus = $(this).attr("cur_value");
        var setStatus = $(this).attr("set_value");
        var actionMsg = curStatus == 0 ?
                "是否将干预移除?" :
                "是否将应用干预到线上列表?";

        bootbox.confirm(actionMsg, function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'id': id, "status": setStatus});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var responseMsg = curStatus == 0 ?
                            "成功移除干预!" :
                            "成功干预!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });


    $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
        timeFormat: 'yyyy-mm-dd',
        stepYear: 1,
        stepMonth: 1,
        stepDay: 1
    });

</script>
</body>
</html>