<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/bootstrap-responsive.css">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/jquery.fileupload-ui.css">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/bootstrap-tagmanager.css">
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/style.css">
<script type="text/javascript" src='${rc.contextPath}/js/jquery-1.7.2.min.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/bootstrap.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-typeahead.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-tagmanager.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jQuery.dualListBox-1.3.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jquery.form.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jquery.cookie.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jquery.myPagination.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/backend.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/manage.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jquery.tipTip.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/jquery.tipTip.minified.js'></script>
<link rel="stylesheet" type="text/css" href="${rc.contextPath}/css/tipTip.css">
<script type="text/javascript" src="${rc.contextPath}/js/holder.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/bootbox.js"></script>
<script type ="text/javascript">
	$.backend.init({
		base : "${rc.contextPath}"
	});
</script>
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
    <div>
        <ul class="breadcrumb">
            <li><a href="${rc.contextPath}/intern/signatures">管理后台</a></li><span class="divider">/</span>
            <li>签名下plist文件列表</li>
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
                    	<table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>签名</th>
                                    <th>公司</th>
                                    <th>已有ipa包数</th>
                                    <th>损坏ipa包数</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                    <tr id="tr_${value.id}">                                    	    
                                    	<td>
	                                    	${enterpriseSignature.signatures!''}       	
                                        </td> 
                                        <td>
                                        	${enterpriseSignature.name!''}
                                        </td>
                                        <td>
                                        	${enterpriseSignature.ipas?c}
                                        </td>
                                        <td>
                                        	${enterpriseSignature.ipasBanned?c}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>苹果Id</th>
                                    <th>应用名</th>
                                    <th>应用包名</th>
                                    <th>快速测试</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">    
                                    	<td>
	                                    	<#if value.itemId??>
	                                    		<@machrefapp value.itemId, value.appName/>	
	                                    	</#if>                                        	
                                        </td> 
                                		<#if value.rootId??>
                                			 <td>
	                                        	<@hrefapp value.rootId, value.appName/>
	                                        </td>
                                		<#else>
                                			<td></td>
                                		</#if>
                                        <td>${ value.bundleId! }</td>
                                        <td>
                                        	<a class="btn btn-info test" target="_blank" href="itms-services://?action=download-manifest&url=${value.enterprisePlistUrl!''}">下载</a>
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
    <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	</script>
</body>
</html>