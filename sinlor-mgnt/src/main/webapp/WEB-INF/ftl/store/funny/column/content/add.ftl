<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-管理后台</title>
        <#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
		<script type="text/javascript" src='${rc.contextPath}/js/ckeditor/ckeditor.js'></script>
     	<script>
        	$(function(){
        		$("#ctypeId").change(function(){
        			var p1=$(this).children('option:selected').text();
        			if(p1=='限免' || p1=='应用集'){
        				$("#referenceColumnIdDiv").hide();
        			}else{
        				$("#referenceColumnIdDiv").show();
        			}
        			if(p1=='应用集'){
        				$("#applicationDescriptionDiv").show();
        			}else{
        				$("#applicationDescriptionDiv").hide();
        			}
        		});
        	});
        </script>
    </head>
    <body>
        <div class='whole-container'>
            <#include "/lib/store_header.ftl">
            <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
            <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
            <div>
                <ul class="breadcrumb">
                    <li>管理后台</li><span class="divider">/</span>
                    <li>专栏内容添加</li>
                </ul>
            </div>
           
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="container span12">
                        <#include "lib/alert.ftl">
                        <div class='main-content'>
                            <div class="well form-inline">  
                             <div id="addInfoDiv">
				                <#if errMsg??>
				                <#if saveStatus==0>
				                <font color="green">${errMsg}</font>
				                <#else>
				                <font color="red">${errMsg}</font>
				                </#if>
				                </#if>
				            </div>                     
					        </div>					        
                            <div class="well form-inline">
                                <div class='main-action'>
                                    <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/funny/column/content/add.ftl" method="post">		
                                        <input type="hidden" name="id" value="${value.id}"/>
                                        <input type="hidden" name="publish" id="isPublish"/>
                                        <input type="hidden" name="currentType" id="currentType" value="${currentStatus}"/>
                                        <input type="hidden" name="columnContentId" value="${value.columnContentId}"/>
                                        <input type="hidden" name="applicationSetsId" value="${value.applicationSetsId}"/>
                                        <input type="hidden" name="viewUrl" value="${value.viewUrl}"/>
                                        <input type="hidden" name="viewNoApplicationUrl" value="${value.viewNoApplicationUrl}"/>
                                        <input type="hidden" name="views" value="${value.views}"/>
                                        <div class="input-prepend">
                                            <span class="add-on">标题</span>
                                            <input class="span2 search" id="title" name="title" type="text" placeholder="标题" style=width:915px; value="${value.title}">
                                        </div><br/>
                                        <div class="input-prepend">
                                            <span class="add-on">作者</span>
                                            <@spring.formSingleSelect "value.authorId", authorsall, " " />
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">分类</span>
                                            <@spring.formSingleSelect "value.ctypeId", ctypesall, " " />
                                        </div>
									    <span class="add-on">展示日期</span>
									    <div id="showDate" class="input-append date datetimepicker" data-date-format="yyyy-mm-dd hh:ii:ss" data-date-autoclose="true">
									      <input class="span2" id="showDateString" name="showDate" size="16" type="text" value="${value.showDate?string("yyyy-MM-dd HH:mm:ss")}">
									      <span class="add-on"><i class="icon-calendar"></i></span>
									    </div><br/>
									    <#if currentStatus==1>
									    <div class="input-prepend">
                                            <span class="add-on">没找到的应用包</span>
                                            <input class="span2 search" id="noBundleIds" name="noBundleIds" type="text" style=width:915px; value="${value.noBundleIds}">
                                        </div><br/>
									    </#if>
		    						    <div class="input-prepend" id="applicationDescriptionDiv" style="display:<#if value==null || value.ctypeId==null || ctypesall[value.ctypeId?c]!='应用集'>none<#else>block</#if>">
                                            <span class="add-on">简介描述</span>
                                            <textarea class="span2 search" id="applicationDescription" name="applicationDescription" style="width:890px;height:150px;">${value.applicationDescription}</textarea>
                                        </div><br/>
                                         <div class="input-prepend">
                                            <span class="add-on">内容</span><br/>
                                     		<textarea class="span2 search" name="content" id="content" type="text" ></textarea>
                                     		<script type="text/javascript">CKEDITOR.replace("content");</script>
                                     		<script id="container" type="text/plain">${value.content}</script>
                                     		<script type="text/javascript">
                                     			CKEDITOR.instances.content.setData($("#container").html());
                                     		</script>
                                        </div>
                                        <div class="input-prepend" style="display:none;">
                                            <span class="add-on">文本内容</span><br/>
                                     		<textarea class="span2 search" name="contentText" id="contentText" type="text" >${value.contentText}</textarea>
                                        </div><br/>
                                        <div class="input-prepend" id="referenceColumnIdDiv" style="display:<#if ctypesall[value.ctypeId?c]!='限免' && ctypesall[value.ctypeId?c]!='应用集'>block<#else>none</#if>">
											<span class="add-on">相关阅读</span>
											<input class="span2 search"  id="referenceColumnId" name="referenceColumnId" type="text" placeholder="请输入相关专栏id，用英文,分隔" style=width:885px; value="${value.referenceColumnId}">
			            				</div>
                                        </br><br/>
                                        
                                        <#if currentStatus==1>
                                        	<#if value.status!=1>
		                                        <button class="btn btn-info add" id="contentNotPublish">
		                                            <i class="icon-white"></i>保存草稿
		                                        </button>
		                                        <button class="btn btn-info add" id="contentPublish">
		                                            <i class="icon-white"></i>保存发布
		                                        </button>
	                                        </#if>
	                                    <#elseif currentStatus==0>
	                                     	<button class="btn btn-info add" id="contentPublish">
	                                            <i class="icon-white"></i>保存发布
	                                        </button>
	                                    <#else>
	                                    	<button class="btn btn-info add" id="contentNotPublish">
	                                            <i class="icon-white"></i>保存草稿
	                                        </button>
	                                        <button class="btn btn-info add" id="contentPublish">
	                                            <i class="icon-white"></i>保存发布
	                                        </button>
                                        </#if>
                                        <div id="previewBtnDiv">
                                        <input id="previewBtn" class="btn btn-info" type="button" value="预览" />
                                    	</div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
        <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
        <script>
	        $("#showDate").datetimepicker({
	            'date-format': 'yyyy-mm-dd hh:ii:ss',
	            stepYear: 1,
	            stepMonth: 1,
	            stepDay: 1,
	            startView: 2,
	            minView: 0,
	            maxView: 2           
	        });
	        
	        $("#contentNotPublish").click(function(){
	        	$("#isPublish").val(0);
	        	$("#contentText").val(CKEDITOR.instances.content.document.getBody().getText());
	        	$("#searchForm").submit();
	        });
	        $("#contentPublish").click(function(){
	        	$("#isPublish").val(1);
	        	$("#contentText").val(CKEDITOR.instances.content.document.getBody().getText());
	        	$("#searchForm").submit();
	        });
	        
	        var previewWindow;
	        $("#previewBtn").click(function () {
	        	$.ajax({
	        		url:'${rc.contextPath}/auth/store/funny/column/content/preview.ftl',
	        		data:{
	        			content:CKEDITOR.instances.content.getData(),
	        			authorId:$("#authorId").val(),
	        			referenceColumnId:$("#referenceColumnId").val(),
	        			title:$("#title").val(),
	        			showDate:$("#showDateString").val(),
	        			ctypeId:$("#ctypeId").val(),
	        			applicationDescription:$("#applicationDescription").val()
	        		},
	        		dataType:'json',
	        		type:'post',
	        		success:function(resp){
	        			if(resp.success){
	        				previewWindow= window.open("", "preview", "height=480, width=320,directories=no,titlebar=no,location=no,status=no,toolbar=no,scrollbars=yes,menubar=no");
	        				previewWindow.document.write(resp.data);
	        			}else{
	        				alert(resp.message);
	        			}
	        		}
	        	});
	        });
        
	</script>
        
    </body>
</html>