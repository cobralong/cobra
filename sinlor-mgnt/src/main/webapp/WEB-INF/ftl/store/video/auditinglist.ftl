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
            <li>AppStore管理后台</li>
            <span class="divider">/</span>
            <li>视频审核列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<form id="searchForm" name="searchForm" action="" method="post">
	                    <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
	                    <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
	                    <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		            </form>
                    <div class="table-content">
                    <#-- table -->
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>视频文件名</th>
                                <th>URL</th>
                                <th>文件MD5</th>
                                <th>文件大小</th>
                                <th>上传日期</th>
                                <th>编辑</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                                <#list values as value>
                                <tr id="tr_${value.id}">
                                    <td>
                                    	${value.originName}
                                    </td>
                                    <td>
                                    	${value.videoUrl}
                                    </td>
                                    <td>
                                    	${value.fileMd5}
                                    </td>
                                    <td>
                                    	${value.size}
                                    </td>
                                    <td>
                                        <#if value.createTime ??>
                                        	${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                    	 <a class="btn btn-info query" href="${rc.contextPath}/auth/store/video/auditingdetail?id=${value.id}">详情</a>
                                    </td>
                                    <td>
                                    	<button class="btn btn-danger delete" default_id="${value.id?c}" title="${value.originName!}">
                                    		删除
                            			</button>
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
    
	$("button.delete").bind("click",function(){
		event.preventDefault();
		bootbox.setDefaults({
			locale: "zh_CN"
		});
		var eventCtl = $(this); 			
		var defaultId = $(this).attr("default_id");
		var title = $(this).attr("title");
		var trCtl = $("#tr_"+defaultId);
		var postUrl = "${rc.contextPath}/auth/store/video/disablevideometa.json";
		
 		bootbox.confirm("是否删除" + title + "的视频上传记录?", function(result) {
 			if(!result){
 				return;
 			}
 			var posting = $.post(postUrl, {'id': defaultId});
 			posting.done(function(errMsg){
 				if(errMsg){//
 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
 				}else{
 					alert("操作成功，已删除" + title + "的视频上传记录!");
 					$(trCtl).remove();
 				}
 			});
		});
	}); 
</script>
</body>
</html>