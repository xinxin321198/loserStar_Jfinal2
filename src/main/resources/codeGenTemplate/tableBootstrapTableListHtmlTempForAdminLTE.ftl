<#setting classic_compatible=true>
<!-- 页面框架代码 -->
${r'<#setting classic_compatible=true />'}
${r'<#include "public.html" />'}
<!DOCTYPE html>
<html>
${r'<@loserStarHeader title="'}${tableRemarks}${r'">'}
    <!-- 这里放生成的引入的js--begin -->
    <!--此部分代码放在head中引入该表的相关js--begin-->
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}Action.js"></script>
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}List.js"></script>
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}ListEvent.js"></script>

    <!--如使用树形表格，则使用此段js-->
    <!--<script src="../../HthyServer/view/${fristLowerClassName}/js/${fristLowerClassName}TreeListEvent.js"></script>-->

    <!--此部分代码放在head中引入该表的相关js--end-->
    <!-- 这里放生成的引入的js--end -->
    <script>
        //这里写自定义的js代码

    </script>
${r'</@loserStarHeader>'}

${r'<@loserStarBody title="'}${tableRemarks}${r'" title2="'}${tableRemarks}${r'">'}
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">盒子标题</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">

                    <!--查询条件字段---begin -->
                    <div id="toolbar" class="btn-group">
                        <form class="form-inline">
                        <#list fieldList as field>
                            <div class="form-group">
                                <label for="org_id">${field.remarks}</label>
                                <input id="${field.name}" type="text" class="form-control"  placeholder="${field.remarks}">
                            </div>
                        </#list>
                            <button id="search" class="btn btn-success" type="button" onclick="listPageEvent.queryList()">查询</button>
                        </form>
                    </div>
                    <!--查询条件字段---begin -->

                    <!-- 树形列表的一些按钮，当然也可以放表格的按钮---begin -->
                    <div id="toolbar" class="btn-group">
                                <button type="button" class="btn btn-primary" onclick="listPageEvent.addTopNode()">
                                    <span class="glyphicon glyphicon-plus"></span>新增顶层节点
                                </button>
                                <button id="addSub" type="button" class="btn btn-primary" onclick="listPageEvent.addSubNode()" disabled>
                                    <span class="glyphicon glyphicon-plus"></span>新增下级节点
                                </button>
                                <button type="button" class="btn bg-olive" onclick="listPageEvent.expandAll()">
                                    <span class="fa fa-angle-double-down"></span>全部展开
                                </button>
                                <button type="button" class="btn bg-olive" onclick="listPageEvent.collapseAll()">
                                    <span class="fa fa-angle-double-up"></span>全部收缩
                                </button>
                                <button type="button" class="btn btn-info" onclick="listPageEvent.refresh()">
                                    <span class="glyphicon glyphicon-repeat"></span>刷新
                                </button>
                    </div>
                    <!-- 树形列表的一些按钮，当然也可以放表格的按钮---end -->



                    <!-- 这里放生成的html代码 -->
                    <!-- 本段代码为代码生成器根据表[${tableName}]结构自动生成的bootstrap的表格的html，犯懒时候从这里粘过去用----begin-->
                    <#--  ${tableRemarks}  -->
                    <table id="${tableName}_table" class="table table-bordered table-striped" >
                        <thead id="dataList_thead">
                        </thead>
                        <tbody id="dataList_tbody">
                        </tbody>
                        <tfoot id="dataList_tfoot">
                        </tfoot>
                    </table>
                    <!-- 本段代码为代码生成器根据表[${tableName}]结构自动生成的bootstrap的表格的html，犯懒时候从这里粘过去用----end-->
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
        <!-- /.col -->
    </div>
${r'</@loserStarBody>'}

${r'<@loserStarFooter />'}

</html>


