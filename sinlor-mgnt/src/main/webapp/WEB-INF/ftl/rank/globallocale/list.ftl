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
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>全球榜国家列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="table-content">                    
                    <#-- table -->
                        <form id="submmitForm" name="itemForm" method="post" action="${rc.contextPath}/auth/rank/globallocale/modify">
                        	<input id="submmitForm_id" type="hidden" value="" name="id"/>
                        	<input id="submmitForm_position" type="hidden" value="" name="position"/>
                        	<input id="submmitForm_status" type="hidden" value="" name="status"/>
                        </form>
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>国家/地区名称</th>
                                <th>国家/地区编码</th>
                                <th>国家/地区图标</th>
                                <th>列表位置</th>
                                <th>状态</th>
                                <th>修改时间</th>
                                <th>入库时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                                <#list values as value>
                                <tr>
                                    <td>
                                    	${value.name!''}
                                    </td> 
                                    <td>
                                    	${value.locale!''}
                                    </td> 
                                    <td>
                                    	<img id="icon-img" class="apk-icon" src="${ value.img }" />
                                    </td>
                                    <td class="edit_td" pid="${value.id?c}">
                                    	<label for="name" class="span1"><span id="p_${value.id?c}" class="text-info" preValue="${value.position?c}" country="${value.name!''}">${ value.position! }</span></label>
                                    </td>
                                    <td>
                                    	<#if value.status == 0>
                                			<span class="label label-success">√</span>
                                		<#else>
                                			<span class="label label-danger" disabled>X</span>
                                		</#if>
                                    </td>
                                    <td>
                                        <#if value.updateTime ??>
                                        ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                        <#if value.createTime ??>
                                        ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                    	<button id="btn_recover_${value.id?c}" class="btn btn-success delete" country="${value.name!''}" reverId="btn_delete_${value.id?c}" defaultId="${value.id?c}" status="0" <#if value.status == 0>disabled</#if>>
                                    		恢复                             	
                            			</button>
                                    	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" country="${value.name!''}" reverId="btn_recover_${value.id?c}" defaultId="${value.id?c}" status="-1" <#if value.status != 0>disabled</#if>>
                                    		删除
                            			</button>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    	<div class="pagination" id="itemPage"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <script>
 		$('.edit_td').click(function() {
 			var pid = $(this).attr("pid");
 			var preValue = $('#p_'+pid).attr("preValue");
 			var country = $('#p_'+pid).attr("country");
 			var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
 			$('#p_'+pid).text('').append(input);
 			input.select();
 			input.blur(function() {
 				var preValue = $(this).parent().attr("preValue");
 				var curValue = $('#attribute').val();
 				if(curValue == ""){
 					$(this).val(preValue);
 					$(this).focus();
 					reutrn;
 				}
 				if(curValue == preValue){
		   			var text = $('#attribute').val();
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return;
	   			}
 				bootbox.confirm("将推荐全球榜国家【"+country+"】在列表中的位置由"+preValue+"------>"+curValue+"?(为影响其前面和后面国家的列表位置!)", function(result) {
		 			if(result){
		 				$("#submmitForm_id").val(pid);
		 				$("#submmitForm_position").val(curValue);
		 				$("#submmitForm").submit();
		 			}else{
			   			$('#attribute').parent().text(preValue);
			   			$('#attribute').remove();
		 			}
				});
 			});
		});
		
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var id = $(this).attr("defaultId");
 			var reverId = $(this).attr("reverId");
 			var reverIdCtl = $("#" + reverId);
 			var status = $(this).attr("status");
 			var country= $(this).attr("country");
 			var postUrl = "${rc.contextPath}/auth/rank/modifygloballocale"; 			
 			var action = status == 0 ? "恢复展示":"不再展示";
 			var infoMsg=action + "全球排行榜【" + country + "】区列表";
 			
	 		bootbox.confirm("是否" + infoMsg + "?", function(result) {
	 			if(!result){
	 				return;
	 			}	 			
 				$("#submmitForm_id").val(id);
 				$("#submmitForm_status").val(status);
 				$("#submmitForm").submit();
			});
		}); 
    </script>
</body>
</html>