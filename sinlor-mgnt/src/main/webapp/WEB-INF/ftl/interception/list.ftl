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
                    <li>榜单列表</li>
                </ul>
            </div>
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="container span12">
                        <#include "lib/alert.ftl">
                        <div class='main-content'>
                            <div class="well form-inline">
                                
                            </div>
                            <div class="table-content">
                                <#-- table -->
                                <form id="itemForm" name="itemForm" action="" method="post">
                                    <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                        <thead>
                                            <tr>
                                                <th>应用id</th>
                                                <th>列表分类</th>
                                                <th>列表类型</th>
                                                <th>渠道</th>
                                                <th>添加人</th>
                                                <th>创建时间</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody class="sortable">
                                            <#if values??>
                                            <#list values as value>
                                            <#assign name = rootName[value.rootId?c]>                                   
                                            <tr id="tr-${value.id}">
                                                <td>
                                                    ${value.root_id}
                                                </td>
                                                <td>            
                                                    ${ value.cata_id }
                                                </td>
                                                <td>${ value.rank_type! }</td>
                                                <td>
                                                    ${ value.channel! }
                                                </td>
                                                <td>
                                                    ${value.interception_src}
                                                </td>
                                                <td>
                                                    
                                                    ${ value.update_time }
                                                   
                                                </td>
                                                <td>
                                                    <a class="btn btn-info del-info" item="${value.id}">删除</a>
                                                </td>
                                            </tr>
                                            </#list>
                                            </#if>
                                        </tbody>
                                    </table>
                                </form>
                                <div class="pagination" id="itemPage"></div>
                            </div>           
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
        <script>
            $(document).ready(function () {

            });
        </script>
    </body>
</html>