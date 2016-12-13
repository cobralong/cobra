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
            <#if para.showInAudit==1>
            	<li>用户可见轮播图列表</li>
            <#else>
            	<li>审核人员可见轮播图列表</li>
            </#if>
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
                                    <span class="add-on">推荐客户端</span>
                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="">状态</span>
                                	<@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <span class="">开始时间</span>

                                <div id="startDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="search_startDateString" name="st" size="16" type="text"
                                           value="${para.st}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <span class="">结束时间</span>

                                <div id="endDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="endDateString" name="et" size="16" type="text"
                                           value="${para.et}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
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
                    <#-- table -->
                    <#if para.status == 0>
                        <p>在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表:</p>
                    </#if>
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                	<th>id</th>
                                    <th>RefId</th>
                                    <th>Icon</th>
                                    <th>资源地址</th>
                                    <th>客户端</th>
                                    <th>展示位置</th>
                                    <th>Type</th>
                                    <th>图片</th>
                                    <th>状态</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <input id="hidden_${value.id}" type="hidden" banner_id="${value.id!}"
                                               promote_status="${value.status}"/>
                                         <td>${value.id}</td>
                                        <#if value.type == 1>
                                            <#assign rootSimple = rootSimples[value.refId?c]>                                            
                                            <td>
                                               <@hrefapp value.refId, rootSimple.name/>
                                            </td>
                                            <td>
                                                <@appicon rootSimple.icon, rootSimple.name/>
                                            </td>
                                            <td>
                                                <@hrefipaplist rootSimple.downloadUrl!''/>
                                            </td>
                                        <#else>
                                            <#assign articleSimple = articleSimples[value.refId?c]>
                                            <td>
                                                <@hrefarticle articleSimple.articleUrl, articleSimple.title/>
                                            </td>
                                            <td>
                                                <@appicon articleSimple.icon, articleSimple.title/>
                                            </td>
                                            <td>
                                                <@hrefipaplist articleSimple.bannerUrl!''/>
                                            </td>
                                        </#if>
										<td>
											${bundleIds[value.bundleId]}
										</td>
					                	<#if para.showInAudit == 1>
                                        	<td name="auditedPosition" class="edit_td" cur_value="${value.auditedPosition?c}" default_id="${value.id?c}" for="p_${value.id?c}" desc="用户所见位置">
                                        		<span id="p_${value.id?c}" class="text-info">${ value.auditedPosition?c }</span>
                                        	</td>
                                        <#else>
                                        	<td name="auditingPosition" class="edit_td" cur_value="${value.auditingPosition?c}" default_id="${value.id?c}" for="p_${value.id?c}"  desc="审核人员所见位置">
												<span id="p_${value.id?c}">${ value.auditingPosition?c }</span>
											</td>
                                        </#if>
                                        <td>${ types[value.type?string]! }</td>
                                        <td>
                                            <a href="${value.bannerUrl}" target="_blank"><img
                                                    style="width:150px; height:70px" src="${value.bannerUrl}"
                                                    alt="${value.bannerUrl}"/></a>
                                        </td>
                                        <td>
                                            <#if value.status == 0>
                                                <font id="font_status_${value.id}" color="green">√</font>
                                            <#else>
                                            	<font id="font_status_${value.id}" color="red">X</font>
                                            </#if>
                                        </td>
										<td>
                                            <#if value.startTime ??>
                                            ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td name="et" class="edit_td" cur_value="${value.endTime?string("yyyy-MM-dd HH:mm:ss")}" default_id="${value.id?c}" for="p_time_${value.id?c}" desc="结束时间">
                                    		<span id="p_time_${value.id?c}" class="text-info">${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }</span>
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

    $("#btn_now_promote").bind("click", function () {
        $("#search_startDateString").val("");
    });
    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
		var curValue = $(eventCtl).attr("cur_value");
		var setValue = $(eventCtl).attr("set_value");
        var postUrl = "${rc.contextPath}/auth/store/banner/modify";
        var actionMsg = curValue == 0 ?
                "是否将【id=" + id + "】Banner从列表中移除?" :
                "是否将【id=" + id + "】Banner添加到轮播列表?";

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
                            "成功将Banner从列表中移除!" :
                            "成功将Banner添加到轮播列表!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });

    $('.edit_td').click(function () {
    	var eventCtl = $(this);
        var id = $(eventCtl).attr("default_id");
        var curValue=$(eventCtl).attr("cur_value");
        var paraName = $(eventCtl).attr("name");
        var forId=$(eventCtl).attr("for");
        var desc=$(eventCtl).attr("desc");
        var input = $('<input id="attribute" type="text" value="' + curValue + '" />');
        $('#' + forId).text('').append(input);
        input.select();
        input.blur(function () {
            var setValue = $(this).val();
            if (setValue == "") {
                $(this).val(curValue);
                $(this).focus();
                return;
            }
            if (curValue == setValue) {
                $(this).parent().text(curValue);
                $(this).remove();
                return;
            }
            bootbox.confirm("将Banner【" + id + "】的" + desc + "由" + curValue + "------>" + setValue + "?", function (result) {
                if (result) {                
			    	var postUrl = "${rc.contextPath}/auth/store/banner/modify";
			    	var postData = "id=" + id + "&" + paraName + "=" + setValue;
		 			var posting = $.post(postUrl, postData);
                    posting.done(function (errMsg) {
                        if (errMsg) {//
                            //show msg
                            alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                            $('#attribute').parent().text(curValue);
                            $('#attribute').remove();
                        } else {
                            alert("修改成功.");
                            $('#attribute').parent().text(setValue);
                            $('#' + forId).attr("cur_value", setValue);
                            $('#attribute').remove();
                        }
                    });
                } else {
                    $('#attribute').parent().text(curValue);
                    $('#attribute').remove();
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