<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<title>应用汇-管理后台-应用信息列表</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
			<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    		<div>
        		<ul class="breadcrumb">
            		<li>管理后台</li><span class="divider">/</span>
            		<li>上架应用列表</li>
            		<li><a href="${rc.contextPath}/auth/app/upload/crawler/application/list">更多</a></li>
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
                        				<div class = "well form-horizontal">
											<div class="control-group">
												<label class="control-label">苹果Id:</label>
												<div class="controls">
													${value.appId}
												</div>
											</div>
										</div>
                        				<div class = "well form-horizontal">
											<div class="control-group">
												<label class="control-label">苹果地址:</label>
												<div class="controls">
													${value.url}
												</div>
											</div>
										</div>
										<div class = "well form-horizontal">
											<div class="control-group">
												<label class="control-label">应用版本:</label>
												<div class="controls">
													${value.version}
												</div>
											</div>
										</div>
										<div class = "well form-horizontal">
											<div class="control-group">
												<label class="control-label">应用内容:</label>
												<div class="controls">
													${value.appJson}
												</div>
											</div>
										</div>
									</div>
								</div>
                    		</#if>
            			</div>
        			</div>
    			</div>
			</div>
		</div>
	</body>
</html>