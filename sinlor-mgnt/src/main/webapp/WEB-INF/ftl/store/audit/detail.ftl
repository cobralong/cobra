<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-管理后台</title>
		<#include "lib/base_source.ftl">		
	</head>
	<body>
		<#include "/lib/store_header.ftl">
		<div>
	        <ul class="breadcrumb">
	            <li>管理后台</li><span class="divider">/</span>
	            <li>客户端缓存刷新时间管理</li>
	        </ul>
    	</div>
        <#include "lib/alert.ftl">
        <div class="container">
			<div class = "well form-horizontal">
					<form action="${rc.contextPath}/auth/store/client/modifyauditinfo" method="post">
						<input type="hidden" name="id" value="${data.id?c}">
						<div class="control-group">
							<label class="control-label">版本号</label>
							<div class="controls">
								<input type="text" disabled value="${data.clientVersion!}"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">包名</label>
							<div class="controls">
								<input type="text" disabled value="${data.bundleId!}"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">审核状态</label>
							<div class="controls">
								<select name="auditStatus">
									<#list audits?keys as key>
										<#if key == data.auditStatus>
                                			<option value="${key}" selected>${audits[key]}</option>
                                		<#else>
                                			<option value="${key}">${audits[key]}</option>
                                		</#if>
                            		</#list>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">AppStore Url:</label>
							<div class="controls">
								<input type="text" class="span5" name="appStoreUrl" value="${data.appStoreUrl!}"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">描述</label>
							<div class="controls">
								<input type="text" class="span5" name="auditMessage" value="${data.auditMessage}"/>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>			
                            <div class="controls">
                            	<button id="btn_add_configure" class="btn btn-info add">
                                	<i class="icon-search icon-white"></i>增加
                            	</button>
                            	<#if errMsg??>
                    				<font>${errMsg}</font>
                    			</#if>
                            </div>
						<div class="control-group">
					</form>
			</div>
		</div>
	</body>
</html>