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
            <li>企业签名应用包上传</li>
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
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/enterprise/ipaupload" method="post">
                               <span class="add-on">企业签名ipa( 最大1.5G,如果超过这个大小，请联系管理员):</span>
                               <div class="fileinput fileinput-new" data-provides="fileinput">								 
								  <div>
								    <span class="btn btn-default btn-file"><span class="fileinput-new">Select ipa</span><span class="fileinput-exists">Change</span><input type="file" name="ipa"></span>
								    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
								  </div>
								</div>
								<br/>
                                <button class="btn btn-info add">上传</button>
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