<#-- alert 提示 -->
<div class='alert-information'>
    <#comment>
    FAQ:
    Q: 怎么用这个消息显示内容
    A:
        Message message = new Message();
        message.warning("just a test");
        // 如果是返回ftl时可直接加入
        model.addAttribute("messages", message.getMessages());

        // 如果是redirect跳转时使用消息显示使用RedirectAttributes
        redirectAttributes.addFlashAttribute("messages", message.getMessages());

    </#comment>
    <#if messages??>
        <#list messages as message>
            <div class="alert alert-${ message.level }">
                <a class="close">×</a> <span>${ message.message }</span>
            </div>
        </#list>
    </#if>
</div>

<script type="text/javascript">
$.extend({
	alert:function(message, type){
		//$(".alert-information .alert").remove();
		var alertStyle = "alert-success";
		switch(type){
			case 'error':
				alertStyle = "alert-error";
				break;
			case 'info':
				alertStyle = "alert-info";
				break;
			case 'warning':
				alertStyle = "alert-warning";
				break;
			case 'success':
				alertStyle = "alert-success";
				break;
			default:
				break;
		}
		var content = '<div class="alert  ' + alertStyle + '">';
         	content +=  '<a class="close">×</a> <span>' + message  + '</span>';
          	content +=  '</div>';
		$(".alert-information").append(content);
		$("." + alertStyle).show(4000);
	},
	
	closeAlert:function(ele){
		if(ele){
			ele.parent(".alert").remove();
		}else{
		    $(".alert").removeClass("alert-success").removeClass("alert-error").removeClass("alert-info").removeClass("alert-warning");
			$(".alert span").text("");
			$(".alert").hide();
		}
	}
});

$(document).ready(function(){
	//点击后面的阴影时消失 关闭弹出摸框
	$(".alert .close").live("click",function(){
		$.closeAlert($(this));
	});
});
</script>