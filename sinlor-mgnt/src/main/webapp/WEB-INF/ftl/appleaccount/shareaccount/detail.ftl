<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-共享账号详情</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
    		<div>
        		<ul class="breadcrumb">
            		<li>管理后台</li><span class="divider">/</span>
            		<li>共享账号详情</li>
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
			                                	<span class="add-on">AppleId：</span>
		                                		<input class="span2" name="platform" readonly="readonly" type="text"value="${value.appleId}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">密码：</span>
			                                	<input class="span2" name="color" readonly="readonly" type="text"value="${value.password}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">账号名称：</span>
			                                	<input class="span2" name="height" readonly="readonly" type="text" value="${value.name}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">账号描述：</span>
			                                	<input class="span2" name="width" readonly="readonly" type="text"value="${value.info}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">账号类型：</span>
			                                	<input class="span2" name="width" readonly="readonly" type="text"value="${type}">
			                                </div>
			                                <div class="input-prepend">
			                                	<span class="add-on">账号区域：</span>
			                                	<input class="span2" name="width" readonly="readonly" type="text"value="${value.locale}">
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
										<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/appleaccount/shareaccount/modify/icon" method="post">
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