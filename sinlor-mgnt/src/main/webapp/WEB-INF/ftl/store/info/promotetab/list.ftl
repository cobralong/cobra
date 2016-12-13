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
            <li>推荐页列表</li>
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
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
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
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>类型</th>
                                    <th>排序</th>
                                    <th>图标</th>
                                    <th>操作</th>
                                    <th>编辑</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<td>
                                    		<font color="#${value.color}">${value.name}</font>                                        
                                        </td>
                                        <td>
                                        ${types[value.type?string]}
                                        </td>
                                        <td class="edit_td" pid="${value.id?c}">
                                            <label for="name" class="span1">
                                                <span id="p_${value.id?c}" class="text-info"
                                                      preValue="${value.rank!''}"
                                                      >${ value.rank! }</span>
                                            </label>
                                        </td>
                                        <td>
                                        	<img src="${value.icon}"/>
                                        </td>
                                        <td>
                                        	<button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " name="${value.name}" pre_status= "${value.status}" default_id="${value.id}" <#if value.status == 0>disabled</#if>>
                                        		展示                                       	
                                			</button>
                                        	<button id="unpromote_btn_${value.id}" class="btn btn-danger delete" default_id="${value.id}"  name="${value.name}" pre_status= "${value.status}" <#if value.status != 0>disabled</#if>>
                                        		停止
                                			</button>
                                        </td>
                                        <td>
                                            <a class="btn btn-info query" href="${rc.contextPath}/auth/store/promotetab/detail?id=${value.id}">编辑</a>                                            
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


    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var name= $(this).attr("name");
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
        var postUrl = "${rc.contextPath}/auth/store/promotetab/modify";
        var preStatus = $(this).attr("pre_status");
        var actionMsg = preStatus == 0 ?
                "是否将【【" + name + "】】从Tab列表中移除?" :
                "是否将【【" + name + "】】添加到Tab列表?";
        var setStatus = preStatus == 0 ? -1 : 0;

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
                    var responseMsg = preStatus == 0 ?
                            "成功将【【" + name + "】】从Tab列表中移除!" :
                            "成功将【【" + name + "】】添加到Tab列表!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });

</script>
</body>
</html>