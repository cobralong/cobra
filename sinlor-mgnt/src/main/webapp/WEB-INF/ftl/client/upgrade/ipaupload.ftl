<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>客户端应用包上传</li>
        </ul>
    </div>
    
    <div id="addInfoDiv">
    	<#if errMsg??>
    		<font color="red">${errMsg}</font>
    	</#if>
    	<#if resultMsg??>
    		<font color="green">${resultMsg}</font>
    	</#if>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/client/upgrade/ipaupload" method="post">
                            	<div class="input-prepend">
                                	<span class="add-on">当前测试版本待上传目录下客户端ipa数：</span>
                                    <label>${testIpas?c}</label>
                                </div><br/><br/>
                                <div class="input-prepend">
                                	<span class="add-on">当前发布版本待上传目录下客户端ipa数：</span>
                                    <label>${productIpas?c}</label>
                                </div><br/><br/>
                                <div class="input-prepend">
                                	<span class="add-on">升级说明:</span>
                                    <textarea name="upgradeInfo" class="span5" cols="360" rows="4"></textarea>
                                </div><br/><br/>
                                 <div class="input-prepend">
                                	<span class="add-on">测试版:</span>
                                    <input type="checkbox" class="checkbox" name="test" value="1"/>
                                </div><br/><br/>
                                <#if testIpas == 0 && productIpas == 0>
                                	<button class="btn btn-info add" disabled>上传</button>
                                <#else >
                                	<button class="btn btn-info add">上传</button>
                                </#if>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
</body>
</html>