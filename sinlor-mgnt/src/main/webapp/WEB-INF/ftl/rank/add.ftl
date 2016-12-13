<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-multiselect.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>分类添加</li>
        </ul>
    </div>
    
    <div id="addInfoDiv">
    	<#if errMsg??>
    		<font color="red">${errMsg}</font>
    	</#if>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/banner/add" method="post">	
                                <div class="input-prepend">
                                    <span class="add-on">分类名称</span>
                                    <input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="分类名称" disabled value="${name}">
                                </div>                                
                                <div class="input-prepend">
                                    <span class="add-on">短描述</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="shortDesc" type="text" placeholder="短描述" disabled value="${shortDesc}">
                                </div>
                                <br/>
                                <div class="input-prepend">
                                	<span class="add-on">分类</span>
                                     <@spring.formSingleSelect "rootCate", rootCates, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">苹果分类</span>
                                     <@spring.catecheckbox appCates, " " />
                                </div>
                                <button class="btn btn-info add" >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-multiselect.js'></script>
</body>
</html>