<html>
<head>
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
<title>应用汇-管理后台</title>
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>第三方企业签名列表</li>
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
                        <form id="itemForm" name="itemForm" method="post">
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
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<input id = "hidden_${value.id}" type="hidden" intervene="${value.intervene}" plist_version="${value.shortVersion}" app_version="${value.version}" plist_id="${value.id}" root_id = "${value.rootId}" app_title = "${value.appName!''}"></input>    
                                    	<td>
	                                    	${value.signatures!''}       	
                                        </td> 
                                        <td>
                                        	${value.name!''}
                                        </td>
                                        <td>
                                        	<a href="${rc.contextPath}/intern/signatureipas?signatures=${value.signatures!''}"> ${value.ipas?c}</a>
                                        </td>
                                        <td>
                                        	<a href="${rc.contextPath}/intern/signatureipas?status=-1&signatures=${value.signatures!''}"> ${value.ipasBanned?c}</a>
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