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
            <li>资讯列表</li>
            <li><a href="${rc.contextPath}/auth/store/article/recom/intervention/list">资讯列表干预</a></li>
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
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}"/>
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}"/>
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}"/>
                                <div class="input-prepend">
                                    <span class="add-on">推荐客户端</span>
                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="">状态</span>
                                	<@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">资讯名称</span>
                                    <input class="span2" name="para.id" type="text" placeholder="资讯Id" value="${para.id}">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">资讯名称</span>
                                    <input class="span2" name="para.title" type="text" placeholder="名称" value="${para.title}">
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
                                    <th>ID</th>
                                    <th>标题</th>
                                    <th>Icon</th>
                                    <th>短描述</th>
                                    <th>标签</th>
                                    <th>文章地址</th>
                                    <th>更新时间</th>
                                    <th>文章状态</th>
                                    <th>操作</th>                                    
                                    <#if recomArticleIds??>
                                    	<th>推荐客户端</th>
                                    	<th>推荐状态</th>
                                    	<th>推荐操作</th>
                                    </#if>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <td>
                                        ${value.id}
                                        </td>
                                        <td>
                                        ${value.title}
                                        </td>
                                        <td>
                                            <@normalicon value.iconUrl, "icon"/>
                                        </td>
                                        <td>${ value.shortDesc! }</td>
                                        <td class="edit_tag_td" pid="${value.id?c}">
                                            <#assign tag = tags[value.tagId?c]>
                                            <label for="name" class="span1"><span id="p_${value.id?c}" class="text-info"
                                                                                  preValue="${value.tagId?c}">${ tag.name! }</span></label>
                                        </td>
                                        <td>
                                            <@hrefipaplist value.articleUrl!''/>
                                        </td>
                                        <td>
                                        ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
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
	                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" default_id="${value.id?c}" bundle_id="${para.bundleId}" set_value="-1" cur_value="0">
	                                        		删除
	                                			</button>
                                			<#else>                                			
                                        		<button id="btn_delete_${value.id?c}" class="btn btn-success delete" default_id="${value.id?c}" bundle_id="${para.bundleId}" set_value="0" cur_value="-1">
                                        			恢复                             	
                                				</button>
	                                		</#if>
                                        </td>
                                        <#if recomArticleIds??>
	                                        <td>
	                                            <#if bundleIds??>
	                                            	<#list bundleIds?keys as key>
	                                            		<#if key == para.bundleId>
	                                            			${bundleIds[key]}
	                                            		</#if>
			                            			</#list>
	                                            </#if>
	                                        </td>
	                                        <td>
	                                            <#if recomArticleIds?seq_contains(value.id)>
	                                                <font id="font_recom_status_${value.id}" color="green">√</font>
	                                            <#else>
	                                            	<font id="font_recom_status_${value.id}" color="red">X</font>
	                                            </#if>
	                                        </td>
	                                        <td>
		                                    	<#if recomArticleIds?seq_contains(value.id)>
		                                        	<button class="btn btn-danger recomdelete" default_id="${value.id?c}" set_value="-1" cur_value="0">
		                                        		不推荐
		                                			</button>
	                                			<#else>                                			
	                                        		<button class="btn btn-success recomdelete" default_id="${value.id?c}" set_value="0" cur_value="-1">
	                                        			推荐                            	
	                                				</button>
		                                		</#if>
	                                        </td>
                                        </#if>
                                        <td>
                                            <a href="${rc.contextPath}/auth/store/article/detail?id=${value.id!}" type="button"
                                                    class="btn btn-success delete glyphicon glyphicon-trash ">
                                                详情
                                            </a>
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
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
		var curValue = $(eventCtl).attr("cur_value");
		var setValue = $(eventCtl).attr("set_value");
        var postUrl = "${rc.contextPath}/auth/store/article/modifystatus.json";
        var actionMsg = curValue == 0 ?
                "是否将【id=" + id + "】的资讯移除?" :
                "是否将【id=" + id + "】的资讯恢复?" ;

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
                            "成功将【id=" + id + "】的资讯移除!" :
                            "成功将【id=" + id + "】的资讯恢复到列表!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });
        
    $("button.recomdelete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var bundleId = $(this).attr("bundle_id");
        var fontCtrl = $("#font_recom_status_" + id);
        var hidden = $("#hidden_" + id);
		var curValue = $(eventCtl).attr("cur_value");
		var setValue = $(eventCtl).attr("set_value");
        var postUrl = "${rc.contextPath}/auth/store/article/recom/modifystatus.json";
        var actionMsg = curValue == 0 ?
                "是否将【id=" + id + "】的资讯在当前客户端的推荐列表移除?" :
                "是否将【id=" + id + "】的资讯在当前客户端的推荐列表恢复?" ;

        bootbox.confirm(actionMsg, function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'articleId': id, "bundleId": bundleId,"status": setValue});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var responseMsg = curValue == 0 ?
                            "成功将【id=" + id + "】的资讯在当前客户端的推荐移除!" :
                            "成功将【id=" + id + "】的资讯恢复到在当前客户端的推荐列表!";
                    alert(responseMsg);
                    if(setValue == 0){
                    	fontCtrl.attr("font", "green");
                    	fontCtrl.val("√");
                    }else{
                    	fontCtrl.attr("font", "red");
                    	fontCtrl.val("X");
                    }
                    
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