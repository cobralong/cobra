<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>客户端审核列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form id="searchForm" name="searchForm" action="" method="post">
	                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
			                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
			                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
	                            	<div class="input-prepend">
	                                    <span class="add-on">应用包名</span>
	                                     <@spring.formSingleSelect "para.bundleId", bundleIds, " " />
	                            	</div>
	                                <button class="btn btn-info search" >
	                                    <i class="icon-search icon-white"></i>查询
	                                </button>
	                            </form>
	                        </div>
	                    </div>
	                </button>
                
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/store/client/addauditinfo" method="post">                            
                                <div class="input-prepend">
                                    <div class="input-prepend">
	                                    <span class="add-on">应用包名</span>
	                                     <@spring.formSingleSelect "para.bundleId", pureBundleIds, " " />
	                            	</div>
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">客户端版本号</span>
                                    <input class="span2" name="clientVersion" type="text" placeholder="版本号" value="${para.clientVersion}">
                            	</div>
                                <button class="btn btn-info add" >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${errMsg}">
                                	<#if errMsg??>
                        				<font>${errMsg}</font>
                        			</#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">                    
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>客户端版本号</th>
                                    <th>客户端包名</th>
                                    <th>状态</th>
                                    <th>修改时间</th>
                                    <th>入库时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>                      	
                                        <td>
                                        	${value.clientVersion}
                                        </td> 
                                        <td>
                                        	${value.bundleId}
                                        </td>
                                        <td>
                                        	${audits[value.auditStatus?c]}
                                        </td>
                                        <td>
                                            <#if value.updateTime ??>
                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.createTime ??>
                                            ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                        	<a class="btn btn-info query" <#if value.auditStatus !=0>disabled</#if> href="${rc.contextPath}/auth/store/client/auditdetail?id=${value.id}">详情</a>
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
	    $(document).ready(function () {
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
	    });		
    </script>
</body>
</html>