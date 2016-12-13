<#macro hrefarticle url title>	
	<a class="mark-visited" href="${url}" target="_blank" title="${title}">
	   <#if title?length gt 8>
	   	${title?substring(0,8)}...
	   <#else>
	   	${title}
	   </#if>
	</a>
</#macro>

<#macro hrefapp rootId title>
	<a class="mark-visited" href="http://ios.appchina.com/app/${ rootId! }" target="_blank" title="${title}">
	   <#if title?length gt 8>
	   	${title?substring(0,8)}...
	   <#else>
	   	${title}
	   </#if>
	</a>
</#macro>

<#macro wrongrightspan status right id>
	<#if status == right>
		<#if id??>
			<span id="status_font_${id}" class="label label-success">√</span>
		<#else>
			<span class="label label-success">√</span>
		</#if>
	<#else>
		<#if id??>
			<span id="status_font_${id}" class="label label-delete">X</span>
		<#else>
			<span class="label label-delete">X</span>
		</#if>
	</#if>
</#macro>

<#macro tristatespan status done wait>
	<#if status == done>
		<span class="label label-success">√</span>
	<#elseif status == wait>
		<span class="label label-success">...</span>
	<#else>
		<span class="label label-danger" disabled>X</span>
	</#if>
</#macro>

<#macro shorthref url>
	<#if url?length ==0>
		<span>暂无链接可用</span>
	</#if>
	<#if url??>
		<a class="mark-visited" href="${url}" title="${url}">
		   <#if url?length gt 18>
				${url?substring(0,18)}...
		   <#else>
		   		${url}
		   </#if>
		</a>
	<#else>
		<span>暂无链接可用</span>
	</#if>
</#macro>

<#macro shortdesc desc>	
	 <#if desc?length gt 8>
		${desc?substring(0,8)}...
   <#else>
   		${desc}
   </#if>
</#macro>


<#macro machrefapp id title>	
	<a class="mark-visited" href="https://itunes.apple.com/cn/app/id${id}?mt=8&l=cn" target="_blank" title="${title}">
	   	${title}
	</a>
</#macro>

<#macro hrefipaplist url>
	<#if url??>		
		   <#if url?length gt 11>
		   	<a class="mark-visited" href="${url}" target="_blank" title="${url!}">
			   		...${url?substring(url?length-11)}
			</a>
			<#else>
				${url}
		   </#if>
	<#else>
		${url}
	</#if>
</#macro>

<#macro appicons icons title>
	<#if (icons?size > 0)>
		<#list icons as icon>
			<img style="width:51px; height:51px" src="${icon}" alt="${title}的截图" title="${title}的截图"/>
			<#break>
		</#list>
	<#else>
		暂无图标
	</#if>
</#macro>


<#macro appicon icon title>
	<#if icon??>
		<img style="width:51px; height:51px" src="${icon}" alt="${title}的截图" title="${title}的截图"/>
	<#else>
		暂无图标
	</#if>
</#macro>

<#macro normalicon icon imgalt>
    <#if icon??>
    <img style="width:51px; height:51px" src="${icon}" alt="${imgalt}" />
    <#else>
    暂无图标
    </#if>
</#macro>

<#macro catecheckbox icons>
	<select id="example-getting-started" multiple="multiple">
        <#if options?is_hash>
            <#list options?keys as value>
            	<option value="${value?html}">${options[value]?html}</option>
            </#list>
        <#else> 
            <#list options as value>
            <option value="${value?html}">${value?html}</option>
            </#list>
        </#if>
    </select>
</#macro>

<#macro selformSingleSelect name options attributes="">
    <select name="${name}" ${attributes}>
        <#if options?is_hash>
            <#list options?keys as value>
            <option value="${value?html}"<@checkSelected value/>>${options[value]?html}</option>
            </#list>
        <#else> 
            <#list options as value>
            <option value="${value?html}"<@checkSelected value/>>${value?html}</option>
            </#list>
        </#if>
    </select>
</#macro>