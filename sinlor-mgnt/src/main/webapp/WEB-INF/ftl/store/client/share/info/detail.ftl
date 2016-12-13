<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-应用分享信息详情</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<div class='whole-container'>
		<#include "/lib/store_header.ftl">
    		<div>
        		<ul class="breadcrumb">
            		<li>管理后台</li><span class="divider">/</span>
            		<li>应用分享信息详情</li>
        		</ul>
    		</div>
    		<div class="container-fluid">
        		<div class="row-fluid">
            		<div class="container span12">
            			<#include "lib/alert.ftl">
                		<div class='main-content'>
                    		<div class="well form-inline">
                        		<div class='main-action'>
                        			<#if value??>
                        				<div class='main-action'>		                        
			                                <div class="input-prepend">
			                                	<span class="add-on">BundleId：</span>
		                                		<input class="span2" name="bundleId" readonly="readonly" type="text"value="${value.bundleId}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">分享内容标题：</span>
			                                	<input class="span3" name="title" readonly="readonly" type="text"value="${value.title}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">分享内容：</span>
			                                	<input class="span4" name="content" readonly="readonly" type="text" value="${value.content}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">分享内容链接地址：</span>
			                                	<input class="span3" name="url" readonly="readonly" type="text"value="${value.url}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">显示阶段：</span>
			                                	<input class="span2" name="showInType" readonly="readonly" type="text"value="${showInTypes[value.showInType?c]}">
			                                </div>
			                                <br/><br/>
			                                <div class="input-prepend">
			                                	<span class="add-on">图标：</span>
			                                	<div class="controls input-prepend">
													<img width="75" height="75" src="${value.icon}">
												</div>
			                                </div>
		                                <br/><br/>
	                                	<span class="add-on">更换图标：</span>
										<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/client/share/info/modify/icon" method="post">
											<input type="hidden" name="id" value="${value.id?c}"/>
											<div class="fileinput fileinput-new" data-provides="fileinput">
				                                <div class="fileinput-new thumbnail" style="width: 75px; height: 75px;">
				                                    <img data-src="holder.js/100%x100%" alt="...">
				                                </div>
				                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 75px; max-height: 75px	;"></div>
				                                <div>
				                                    <span class="btn btn-default btn-file">
				                                    	<span class="fileinput-new">Select image</span>
				                                    	<span class="fileinput-exists">Change</span>
				                                    	<input type="file" name="icon">
				                                    </span>
				                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
				                                </div>
				                        	</div>
				                        	<br/>
				                            <button class="btn btn-info add" >
				                                <i class="icon-search icon-white"></i>修改图标
				                            </button>		
				                            <#if errMsg??>
					                        	<font color="red">修改图标失败，原因：${errMsg}</font> 
	                				        </#if>
										</form>
		                       	 	</div>
                    			</#if>
            				</div>
        				</div>
    				</div>
				</div>
			</div>		
			<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
		</body>
</html>