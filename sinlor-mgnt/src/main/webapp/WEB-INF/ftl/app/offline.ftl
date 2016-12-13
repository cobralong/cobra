<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>快速下架</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/app/addbannedapp" method="post">
                                <div class="input-prepend">
                                	<span class="add-on">RootId:</span>
                                    <input class="span2" name="rootId" id="application_banned_rootId" type="text" placeholder="应用rootId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">Reason:</span>
                                    <input class="span4" name="info" type="text" placeholder="下架原因" value="">
                                </div>
                                <button class="btn btn-info add" disabled>
                                    <i class="btn-info btn-white" id="application_disabled_button">快速拉黑</i>
                                </button>
                                <br/>
                                <div class="input-prepend">
                                	<span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:300px" name="name" type="text" placeholder="应用名称" disabled value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">状态</span>
                                    <input id="input_app_state" class="span2" style="width:300px" name="state" type="text" placeholder="状态" disabled value="">
                                </div>
                            </form>
                           	<form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <!--<div class="input-prepend">
                                	<span class="add-on">RootId:</span>
                                    <input class="span2" name="rootId" type="text" placeholder="应用rootId" value="${para.rootId}">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="btn-search btn-white">是否拉黑</i>
                                </button>-->
                            </form>
                        </div>
                        <#if errMsg??><span class="add-on" style="color:red"> ${errMsg} </span></#if>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>rootId</th>
                                    <th>名称</th>
                                    <th>itemId</th>
                                    <th>appStore</th>
                                    <th>下架原因</th>
                                    <th>adminId</th>
                                    <th>更新时间</th>
                                    <th>插入时间</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                	<#list values as value>
                                    <tr>
                                        <td>
                                    		<@hrefapp value.root.rootId, value.root.rootId/>	
                                        </td>
                                        <td>
                                        	${value.root.editorTitle!}
                                        </td>
                                        <td>
                                    		<@machrefapp value.root.itemId, value.root.itemId/>	
                                        </td>
                                        <td>
                                        	${value.root.appStoreUrl!}
                                        </td>
                                        <td>
                                        	${value.banned.info!}
                                        </td>
                                        <td>
                                        	${value.banned.adminId!}
                                        </td>
                                        <td>
                                            <#if value.banned.updateTime ??>
                                            	${ value.banned.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.banned.createTime ??>
                                            	${ value.banned.createTime?string("yyyy-MM-dd HH:mm:ss") }
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
</body>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    $("#application_banned_rootId").bind("blur",function(){
	    	var ctr=$(this);
	    	var rid=$(ctr).val();
	    	var textName=$("#input_app_name");
	    	var textState=$("#input_app_state");
	    	var btn=$("button.add");
	    	var posting=$.post("${rc.contextPath}/auth/app/searchrootid",{"rootId":rid});
	    	posting.done(function(result){
	    		if(result.success){
	    			$(textName).val(result.data.editorTitle);
	    			if(result.data.status==0){
	    				$(textState).val('正常');
	    			}else{
	    				$(textState).val('已下架');
	    			}
	    			$(btn).removeAttr("disabled");
	    		}else{
	    			$(textName).val(result.message);
	    			$(btn).attr("disabled","disabled");
	    		}
	    	});
	    });
	 </script>
</html>