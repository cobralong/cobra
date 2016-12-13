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
            <li>值得玩广告列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/funny/client/ad/list" method="post">
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
								<div class="input-prepend">	                                    
                                    <span class="add-on">客户端</span>
                                    <select id="sel_bundleid" name="bundleId" type='add' class="client_choose">
                                		<#list bundleIds?keys as key>
                            				<option value="${key}">${bundleIds[key]}</option>
                            			</#list>
                                	</select>
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
                    <div class="table-content">
                    <#-- table -->
                    <#if para.status == 0>
                        <p>在<b><font color="green">${para.st}</font></b>时间正在线上的广告列表:</p>
                    </#if>
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>客户端</th>
                                <th>图片</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                                <#list values as value>
                                <tr id="tr_${value.id}">
                                    <input id="hidden_${value.id}" type="hidden" ad_id="${value.id!}"
                                           promote_status="${value.status}"/>
                                     <td>
                                    	${value.bundleId}---${bundleIds[value.bundleId]}
                                    </td>
                                    <td>
                                        <a href="${value.img}" target="_blank"><img
                                                style="width:150px; height:70px" src="${value.img}"
                                                alt="${value.img}"/></a>
                                    </td>
                                    <td>
                                        <#if value.startTime ??>
                                        ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td class="edit_time_td" pid="${value.id?c}">
                                            <span id="p_time_${value.id?c}"
                                                  preValue="${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }">${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }</span>
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
        var trCtl = $("#tr_" + id);
        var curValue = $(this).attr("cur_value");
        var setValue = $(this).attr("set_value");
        var postUrl = "${rc.contextPath}/funny/client/ad/modify";
        var actionMsg = curValue == 0 ?
                "是否将广告从列表中移除?" :
                "是否将广告添加到轮播列表?";

        bootbox.confirm(actionMsg, function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'id': id, "status": setValue});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var responseMsg = curValue == 0 ?
                            "成功将广告从列表中移除!" :
                            "成功将广告添加到上线列表!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });

    $('.edit_time_td').click(function () {
        var pid = $(this).attr("pid");
        var preValue = $('#p_time_' + pid).attr("preValue");
        var input = $('<input id="timeattribute" type="text" preValue="' + preValue + '" value="' + preValue + '" />')
        $('#p_time_' + pid).text('').append(input);
        input.select();
        input.blur(function () {
            var preValue = $(this).parent().attr("preValue");
            var hidden = $("#hidden_" + pid);
            var curValue = $('#timeattribute').val();
            if (curValue == "") {
                $(this).val(preValue);
                $(this).focus();
                return;
            }
            if (curValue == preValue) {
                $('#timeattribute').parent().text(preValue);
                $('#timeattribute').remove();
                return;
            }
            bootbox.confirm("将广告推荐时间由" + preValue + "------>" + curValue + "?", function (result) {
                if (result) {
                    var postUrl = "${rc.contextPath}/funny/client/ad/modify";
                    var posting = $.post(postUrl, {'id': pid, "et": curValue});
                    posting.done(function (errMsg) {
                        if (errMsg) {//
                            //show msg
                            alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                            $('#timeattribute').parent().text(preValue);
                            $('#timeattribute').remove();
                        } else {
                            alert("修改成功.");
                            $('#timeattribute').parent().text(curValue);
                            $('#p_time_' + pid).attr("preValue", curValue);
                            $('#timeattribute').remove();
                        }

                    });
                } else {
                    $('#timeattribute').parent().text(preValue);
                    $('#timeattribute').remove();
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