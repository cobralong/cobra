<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>${cateType}分类列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="table-content">
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>图标</th>
                                <th>名称</th>
                                <th>英文名</th>
                                <th>短描述</th>
                                <th>应用数</th>
                                <th>对应苹果分类</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                            	<#list values as value>
                            		<#assign categoryNames = []>
									<#assign categoryNameSize = categoryNames?size>
		                            <#list map?keys as mKey>
		                            	<#if mKey == value.id>		                            	
										    <#assign categoryNames = map[mKey]>
										    <#assign categoryNameSize = categoryNames?size>
		                            	</#if>
								   </#list>
                                	<tr>
                                		<#if categoryNameSize < 2>
		                                	<td>
		                                     	${value.id}
		                                     </td>
		                                    <td>
			                                    <#if value.icon!=''>
			                                    	<img id="icon-img" class="apk-icon" src="${ value.icon }" />
			                               	 	</#if>
		                                    </td>
		                                    <td>${ value.name! }</td>
                                            <td>${ value.enName! }</td>
		                                    <td>${ value.shortDesc! }</td>
		                                    <td>${ value.apps! }</td>
		                                    <#if categoryNames??>
												<#list categoryNames as categoryName>
		                                    		<td>${categoryName}</td>
		                                    	</#list>
		                                    <#else>
		                                    	<td></td>
		                                    </#if>
		                                    
		                                <#else>
		                                	<td rowspan="${categoryNameSize}">
		                                     	${value.id}
		                                     </td>
		                                    <td rowspan="${categoryNameSize}">
			                                    <#if value.icon!=''>
			                                    	<img id="icon-img" class="apk-icon" src="${ value.icon }" />
			                               	 	</#if>
		                                    </td rowspan="${categoryNameSize}">
		                                    <td rowspan="${categoryNameSize}">${ value.name! }</td>
                                            <td rowspan="${categoryNameSize}">${ value.enName! }</td>
		                                    <td rowspan="${categoryNameSize}">${ value.shortDesc! }</td>
		                                    <td rowspan="${categoryNameSize}">${ value.apps! }</td>
		                                    <#list categoryNames as categoryName>
		                                    	<#if categoryName_index == 0>
			                                    	<td>${categoryName}</td>
			                                    <#else>
			                                    	<tr>              	
			                                    		<td>${categoryName}</td>
			                                    	</tr>
			                                    </#if>
		                                    </#list>
                                		</#if>
                                	</tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div><!-- end div class table-content-->

                    <div class="modal hide" id="batch-modal">
                        <div class="modal-header">
                            <button type="button" class="close close-modal" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h3 id="batch-operate-title"></h3>
                        </div>
                        <div class="modal-body">
                            <textarea class="input-block-level" id="reason"></textarea>
                        </div>
                        <div class="modal-footer">
                            <a href="#" class="btn close-modal">关闭</a>
                            <a class="btn btn-primary batch-submit" id="">确定</a>
                        </div>
                    </div>
                </div><!-- end div class main-content-->
            </div><!-- end div class container span12-->
        </div><!-- end div class row-fluid-->
    </div><!-- end div class container-fluid-->
</div><!-- end div class whole-container-->
</body>
</html>