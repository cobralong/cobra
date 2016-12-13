<#assign security=JspTaglibs["/WEB-INF/security.tld"] />
<#-- Header -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand">AppStore管理后台</a>

            <div class="nav-collapse">
            <#-- JS填充header数据 -->
                <ul class="nav" id="nav-header">
                </ul>
                <ul class="nav pull-right">
	                <@security.authorize access="isAuthenticated()">
					  您好: <@security.authentication property="principal.username" /> 
					</@security.authorize>		
                    <li class="divider-vertical"></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog icon-white"></i>操作
                            <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="http://www.appchina.com" target="blank">应用汇首页</a></li>
                            <li><a href="http://dev.appchina.com" target="blank">应用汇开发者</a></li>
                            <li><a href="${rc.contextPath}/auth/reset">修改密码</a></li>
                            <li><a href="${rc.contextPath}/j_spring_security_logout">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<#-- /Header -->
<script type="text/javascript">
function initHeadMenus(menus){
//填充header
        var content = "";
        for (var i = 0; i < menus.length; i++) {
        	var menu = menus[i];
            // 有下拉菜单
            if (menu.children.length > 0) {
                content += '<li class="dropdown root_menu" id = "menu_' + menu.id + '">' +
                        '<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="root_name">'
                        + menu.name + '</span><b class="caret"></b></a><ul class="dropdown-menu">'                        
                for (var j = 0; j < menu.children.length; j++) {
                    content += '<li><a class="navbar-title" id = "menu_' + menu.children[j].id + '" href="' + $.backend.opts.base + menu.children[j].url + '">' + menu.children[j].name + '</a></li>'
                }
                content += '</ul></li>'
            } else {
                content += '<li class="root_menu" id = "menu_' + menu.id + '"><a class="navbar-title" href="' + $.backend.opts.base + menu.url + '">' + menu.name + '</a></li>'
            }
        }
        return content;
}
    $(document).ready(function () {
   		var menus = $.backend.allMenuByName($.backend.opts.rootMenuName, 1);   		
    	var content = initHeadMenus(menus);
   

        $("#nav-header").html(content);
        // 用cookie显示欢迎信息
        var menu_active = $.cookie("manage_menu_active");
        // 用cookie回填选中
        console.log("cookie menu active: ", menu_active);
        if (menu_active != undefined) {
            $("#" + menu_active).parents(".root_menu").addClass("active");
        }

        $('.navbar-title').click(function () {
            // 设置cookie,当前选中的submenu是什么，下拉菜单
            var menuId = $(this).attr("id");
            console.log("u have click the sub menu, id:", menuId);
            $.cookie("manage_menu_active", menuId, { expires: 7, path: $.backend.opts.base});
        });
    });
</script>
