<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>公众号菜单编辑</li>
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
                            <font color="red">${errMsg}</font>
                        </#if>
                        </div>
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data"
                                  action="${rc.contextPath}/wechat/menu/add" method="post">
                                  
                                <button class="btn btn-success add" id="submitParam">
                                    <i class="icon-download icon-white"></i>提交
                                </button>
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
    $(document).ready(function () {
        queryMenu();
    });
    
    function queryMenu(){
    	var account = $("#sel_account").val();
        var posting = $.post("${rc.contextPath}/wechat/menu/get", {"account": account});
        posting.done(function (result) {
            if (result.success) {
            	$("#jsonParam").val(result.data);
            } else {
            	//nothing
                $("#addInfoDiv").append("<font color='red'>查询出错errMsg:"+result.message+"</font>");
            }
        });
    }
    
    $("#submitParam").click(function(){
	        	$("#searchForm").submit();
	 });
	 
	 $("#delMenu").click(function(){
	 		event.preventDefault();
	 		bootbox.setDefaults({
		        locale: "zh_CN"
		    });
	 		var account = $("#sel_account").val();
	 		var postUrl = "${rc.contextPath}/wechat/menu/del";
	 		var remindMsg="是否将 公众号【"+account+"】菜单列表清空!";
	 		bootbox.confirm(remindMsg, function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = $.post(postUrl, {'account': account});
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            } else {
		                var responseMsg = "成功将 公众号【"+account+"】菜单列表清空!";
		                alert(responseMsg);
		            }
		        });
		    });
	 });

    $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
        timeFormat: 'yyyy-mm-dd',
        stepYear: 1,
        stepMonth: 1,
        stepDay: 1
    });


</script>
</body>
</html>