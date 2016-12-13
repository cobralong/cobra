<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
	<#include "/lib/store_header.ftl">
	
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>推荐分类${pageName}</li>
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
                            <form id="searchForm" name="searchForm" method="post">              
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                    <span class="add-on">客户端</span>
                                    <@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                            	</div>
                            	<div class="input-prepend">
                                    <span class="">状态</span>
                                	<@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-query icon-white"></i>查询
                                </button>
                            </form>
                        </div>
                    </div>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <#if errMsg??>
                    	<font color="green">${errMsg}</font>
                    </#if>
                	 <div class="well form-inline">
                        <form action="${rc.contextPath}/auth/store/cate/addrecomcate" method="post">                                  	
                            <div class="input-prepend">
                            	<span class="add-on">客户端</span>
                                <@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                        	</div>
                            <div class="input-prepend">
                                <span class="add-on">推荐名称</span>
                                <input id="input_app_name" class="span2" name="name" type="text" placeholder="推荐名称" value="">
                            </div>                           
                            <div class="input-prepend">
                                <span class="add-on">推荐类别</span>
                                <@spring.formSingleSelect "para.type", types, " " />
                    		</div>
                            <div class="input-prepend">
                                <span class="add-on">颜色展示</span>
                                <input id="input_app_name" class="span" name="color" type="color" placeholder="颜色展示" value="">
                            </div>
                            <br/>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on">展示状态</span>
                         		<@spring.formSingleSelect "para.showInAudit", shows, " " />
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">审核人员中的位置</span>
                                <input class="span1" size="10" name="auditingPosition" type="text" placeholder="rank" value="">                                   
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">用户查看时的位置</span>
                                <input class="span1" size="10" name="auditedPosition" type="text" placeholder="rank" value="">                                   
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
                    <div class="table-content">
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>客户端</th>
                                <th>颜色</th>
                                <th>推荐类别</th>
                                <th>展示阶段</th>
                                <th>位置</th>
                                <th>推荐状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if datas??>
                            	<#list datas as value>
                            		<tr>
	                                    <td>${ value.name! }</td>
                                        <td>
                                        	${value.bundleId}---${bundleIds[value.bundleId]}
                                        </td>
	                                    <td>
	                                    	<font color="#${value.color}">展示颜色</font>
	                                    </td>
	                                    <td>${types[value.type?c]}</td>
	                                    <td>
	                                    	${shows[value.showInAudit?c]}                                    	
	                                    </td>
	                                    <#if para.showInAudit == 0>
	                                    	<td>${ value.auditingPosition }</td>	
	                                    <#else>
	                                    	<td>${ value.auditedPosition }</td>	
	                                    </#if>
	                                    <td>	                                    	
                                            <#if value.status == 0>
                                                <font id="font_status_${value.id}" color="green">√</font>
                                            <#else>
                                            	<font id="font_status_${value.id}" color="red">X</font>
                                            </#if>             	
	                                    </td>
	                                    <td> <a class="btn btn-info query" href="${rc.contextPath}/auth/store/cate/recomcatedetail?id=${value.id}">详情</a></td>
                                	</tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    	<div class="pagination" id="itemPage"></div>
                    </div><!-- end div class table-content-->
                </div><!-- end div class main-content-->
            </div><!-- end div class container span12-->
        </div><!-- end div class row-fluid-->
    </div><!-- end div class container-fluid-->
</div><!-- end div class whole-container-->
</body>
<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript">
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	</script>
</html>