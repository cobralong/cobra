<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link href="${rc.contextPath}/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <!-- 配置文件 -->
    <script type="text/javascript" src="${rc.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/ueditor/ueditor.all.js"></script>

    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <#if para.id??>
            	<li>编辑资讯</li>
            <#else>
            	<li>新建资讯</li>
            </#if>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        <#include "lib/alert.ftl">
            <div class='main-content'>
                <div class="well form-inline">
                    <div class='main-action'>
                        <form id="addform" name="addform" method="post"
                              action="${rc.contextPath}/auth/store/article/add" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${para.id}"/>
                            <input type="hidden" name="articleUrl" value="${para.articleUrl}"/>
                            <input type="hidden" name="iconUrl" value="${para.iconUrl}"/>
                            <div class="input-prepend">
                                <span class="add-on">标题</span>
                                <input class="span2" id="title" name="title" type="text" placeholder="Title"
                                       value="${para.title}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">短描述</span>
                                <input class="span2" style="width:600px" name="shortDesc" type="text" placeholder="短描述"
                                       value="${para.shortDesc}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">标签</span>
                            <@spring.formSingleSelect "para.tagId", tags, " " />
                            </div>

                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                <div class="fileinput-new thumbnail" style="width: 60px; height: 60px;">
                                    <img data-src="holder.js/100%x100%" alt="..." src="${para.iconUrl}"/>
                                </div>
                                <div class="fileinput-preview fileinput-exists thumbnail"
                                     style="max-width: 60px; max-height: 60px	;"></div>
                                <div>
                                    <span class="btn btn-default btn-file"><span
                                            class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span>
                                        <input type="file" name="icon"></span>
                                    <a href="#" class="btn btn-default fileinput-exists"
                                       data-dismiss="fileinput">Remove</a>
                                </div>
                            </div>
                            <br/>
							<div class="input-prepend">   
								<span class="add-on">展示客户端</span>
		                       	<#list bundleIds?keys as key>
		                       		<#if recomBundleIds?? && recomBundleIds?seq_contains(key)>
		                       			<input name="bundleIdList" type="checkbox" checked value="${key}"><span  style="margin-right:20px;">${bundleIds[key]}</span>
		                       		<#else>
		                       			<input name="bundleIdList" type="checkbox" value="${key}"><span  style="margin-right:20px;">${bundleIds[key]}</span>
		                       		</#if>		                        	  
		        				</#list>            				
            				</div>
            				<br/>
                            <div spellcheck="false">
                                <!-- 加载编辑器的容器 -->
                                <script id="articlecontainer" name="articlecontainer"  type="text/plain"
                                        style="width:1024px;height:500px;"></script>
                            </div>
                            </script>

                            <input type="hidden" id="plainText" name="contentText"/>
                            <input type="hidden" id="allHtml" name="content"/>
                            <textarea id="editHtml" style="display: none">${para.content!"在这里编辑"}</textarea>
                            <input id="clearBtn" type="button" value="清除全部格式" />
                            <#--<input id="clearSelectedBtn" type="button" value="清除所选格式" />-->
                            <input id="previewBtn" type="button" value="预览"/>
                            <input id="submitBtn" type="submit" value="提交"/>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
    <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
    <script type="text/javascript">
        var editor;
        $(document).ready(function () {
            <!-- 实例化编辑器 -->
//            editor = UM.getEditor('articlecontainer');
            editor = UE.getEditor('articlecontainer');
            editor.ready(function () {
                editor.setContent($("#editHtml").text());
            });

        });

        var htmlFormat1 = '<!DOCTYPE html><html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8"/><meta name="applicable-device" content="pc"><title>';
        var htmlFormat2 = '</title><meta name="viewport" content="width =device-width, initial-scale=1, maximum-scale=1"/><link rel="icon" type="image/ico" href="http://ios.appchina.com/static/ios/images/favicon.ico"/><link rel="Shortcut Icon" type="image/ico" href="http://ios.appchina.com/static/ios/images/favicon.ico" /><style type="text/css">h1 {font-size: 5.9vw;}h2 {font-size: 3.0vh;}p {font-size: 2vmin;}img{width:auto;height:auto;max-width:100%;max-height:100%;}</style></head><body>';
        var htmlFormat3 = '</body></html>';

        var previewWindow;
        $("#previewBtn").click(function () {
            previewWindow = window.open("", "preview", "height=480, width=320,directories=no,titlebar=no,location=no,status=no,toolbar=no,scrollbars=yes,menubar=no");
            var htmlBody = editor.getContent();
            var title = $("#title").val();
            var wholeHtml = htmlFormat1 + title + htmlFormat2 + htmlBody + htmlFormat3;
            previewWindow.document.write(wholeHtml);
        });

        $("form").bind("keypress", function (e) {
            if (e.keyCode == 13) {
                if(!($(e.target).attr("id") == "articlecontainer")){
                    return false;
                }
            }
        });

        $("#clearBtn").click(function(){
            var htmlBody = editor.getContent();
            var myRe = /style=".*?"/g;
            htmlBody = htmlBody.replace(myRe, "");
            editor.setContent(htmlBody);
        });

//        $("#clearSelectedBtn").click(function(){
//            var elements = editor.selection.getStartElementPath();
//            for(i in elements){
//                $("*", elements[i]).removeAttr("style");
//                $(elements[i]).removeAttr("style");
////                $("*", e).style.cssText = null;
////                $(e).style.cssText = null;
//            }
//        });



		$("input[type='checkbox']").bind("click",function(){
			var eventCtl = $(this);
			var value = $(eventCtl).val();			
			if(value=="all"){
				if($(this).is(":checked")){					
					$("input[type='checkbox']").each(function(){
						$(this).prop("checked", true);
					});	
				}else{
					$("input[type='checkbox']").each(function(){
						$(this).prop("checked", false);
					});
				}
			}else{
				if(!$(this).is(":checked")){
					$("input[type='checkbox']").each(function(){
						if($(this).val() == "all"){
							$(this).prop("checked", false);	
						}
					});
				}
			}
		});

        $('#addform').submit(function (e) {
            $("#submitBtn").attr("disabled", "disabled");
            var html = editor.getContent();
            var plainTxt = editor.getContentTxt();
            $("#plainText").val(plainTxt);
            $("#allHtml").val(html);
            var formData = new FormData(document.getElementById('addform'));
            $.ajax({
                type: $(this).attr('method'),
                url: $(this).attr('action'),
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    $("#submitBtn").removeAttr("disabled");
                    if (data && data != '') {
                        alert(data);
                    }
                    else {
                        alert('添加成功');
                    }
                }
            });
            e.preventDefault();
        });

    </script>
</body>
</html>