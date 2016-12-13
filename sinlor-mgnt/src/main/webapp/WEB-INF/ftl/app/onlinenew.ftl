<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>编辑提交应用上架</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/app/addnewapp" method="post" enctype="multipart/form-data">
                                <div class="input-prepend">
                                	<span class="add-on">应用名称</span>
                                    <input class="span2" name="title" type="text" placeholder="应用名称" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">appStoreUrl</span>
                                    <input class="span" name="appStoreUrl" type="text" placeholder="appStoreUrl" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">bundleId</span>
                                    <input class="span" name="bundleId" type="text" placeholder="bundleId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">itemId</span>
                                    <input class="span" name="itemId" type="text" placeholder="itemId" value="">
                                </div><br/>
                                <div class="input-prepend">
                                	<span class="add-on">语言</span>
                                    <input class="span2" name="language" type="text" placeholder="language" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">版本 </span>
                                    <input class="span" name="version" type="text" placeholder="version" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">&nbsp;作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者</span>
                                    <input class="span" name="author" type="text" placeholder="author" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">&nbsp;大&nbsp;&nbsp;&nbsp;小&nbsp; </span>
                                    <input class="span" name="size" type="text" placeholder="size" value="">
                                </div></br>
                                <div class="input-prepend">
                                	<span class="add-on">价格 </span>
                                    <input class="span2" name="price" type="text" placeholder="price" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">评分 </span>
                                    <input class="span" name="score" type="text" placeholder="score" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">发布日期</span>
                                    <input class="span" name="upgradeDate" type="text" placeholder="upgradeDate" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">系统需求 </span>
                                    <input class="span" name="support" type="text" placeholder="requirements" value="">
                                </div>
                                </br>
                                <div class="input-prepend">
                                	<span class="add-on">区域</span>
                                    <input class="span2" name="local" type="text" placeholder="local" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">分类 </span>
                                    <input class="span" name="cate" type="text" placeholder="cate" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">价格单位 </span>
                                    <input class="span" name="priceSymbol" type="text" placeholder="priceSymbol" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">&nbsp;&nbsp;&nbsp;&nbsp;tag&nbsp;&nbsp;&nbsp;</span>
                                    <input class="span" name="tag" type="text" placeholder="tag" value="">
                                </div><br/>
                                <div class="input-prepend">
                                	<span class="add-on">内容</span>
                                    <textarea class="span10" style=height:200px;"" name="content" placeholder="内容" value=""></textarea>
                                </div>
                                <br/><br/></br/>
                                <span class="add-on">icon截图:</span>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="icon"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                <span class="add-on">iphone截图:</span>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="iphone"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                <span class="add-on">ipad截图:</span>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><input type="file" name="ipad"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                <button class="btn btn-info query" >
                                    <i class="btn-info btn-white">上架</i>
                                </button>
                            </form>
                        </div>
                        <#if errMsg??><span class="add-on" style="color:red"> ${errMsg} </span></#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
     <script>
   	 </script>
	 <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
</html>