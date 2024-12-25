<#setting classic_compatible=true>
var table;
<#--查询table表格的js代码(适配datatables) -->
var listPageEvent = {}
/**
 * 刷新
 */
listPageEvent.refresh = function() {
    listPageEvent.queryPageList();
    <#--  listPageEvent.queryList();  -->
}

/**
 * 查询数据并渲染出表格（附带初始化datatables）
 */
listPageEvent.queryList = function () {
    //构建查询条件
    var queryObj = {};
    <#list fieldList as field>
    queryObj.${field.name} = $("#${field.name}").val();//${field.remarks}
    </#list>
    ${fristLowerClassName}Action.getListData(queryObj, function (data) {
        //绘制表头和表尾
        var thead = "";
        thead += "                            <tr>";
        thead += "                                <th><input type=\"checkbox\" class=\"checkall\"></th>";
        thead += "                                <th>#</th>";
        <#list fieldList as field>
        thead += "                                <th>${field.remarks}</th>";
        </#list>
        thead += "                                <th>操作</th>";
        thead += "                            </tr>";

        //绘制表体
        $("#dataList_tbody").html("");
        //先清空现有的datatables实例的对象
        if (table) {
            //如果已经有datatables的实例，先销毁实例再从新构建dom，再初始话datatables实例，否则刷新会失效，或者排序会自动清空数据等等
            table.clear();
            table.destroy();//销毁数据对象
        }
        for (var i = 0; i < data.length; i++) {
            var tmp = data[i];
            var text = "";
            text += "                        <tr id=\""+ tmp.${primaryKey}+ "\">";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_checkbox\"><input type=\"checkbox\" value=\"" + tmp.${primaryKey} + "_checkbox\" class=\"checkchild\"></td>";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_index\">" + (i + 1) + "</td>";
            <#list fieldList as field>
            text += "                            <td id=\"" + tmp.${primaryKey} + "_${field.name}\">" + tmp.${field.name} + "</td>";
            </#list>
            text += "                            <td>";
            <#if listBtnStyle=='all'||listBtnStyle=='original'>
            <#--  生成原始按钮  -->
            text += "                            <div class=\"btn-group\">";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\">删除</button>";
            text += "                            </div>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='icon'>
            <#--  生成图标按钮  -->
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\"><i class=\"fa fa-trash\"></i>删除</button>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='dropdown'>
            <#--  生成下拉按钮  -->
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\"></span>";
            text += "                                   </button>";
            text += "                                   <ul class=\"dropdown-menu\">";
            text += "                                       <li><a href=\"javascript:window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-green\">查看</a></li>";
            text += "                                       <li><a href=\"javascript:window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-light-blue\">编辑</a></li>";
            text += "                                       <li class=\"divider\"></li>";
            text += "                                       <li><a href=\"javascript:listPageEvent.del('"+ tmp.${primaryKey} +"')\" class=\"text-red\">删除</a></li>";
            text += "                                   </ul>";
            text += "                               </div>";
            </#if>
            text += "                            </td>";
            text += "                        </tr>";

            $("#dataList_tbody").append(text);
            $("#" + tmp.${primaryKey}).data(tmp);
        }

        //再添加dom
        $("#dataList_thead").html(thead);
        $("#dataList_tfoot").html(thead);
        //最后初始化datatables
        table = loserStarDatatablesCfg.initDataTables("${tableName}_table");
    });
}

/**
 * 查询数据并渲染出分页表格（适配loserStar自己写的原生bootstrap分页组件）
 */
listPageEvent.queryPageList = function () {
    //构建查询条件
    var queryObj = {};
    <#list fieldList as field>
    queryObj.${field.name} = $("#${field.name}").val();
    </#list>
    //分页参数
    queryObj.pageNumber = dataPage.getPageNumber();//获取当前页码当做查询参数
    queryObj.pageSize = dataPage.getPageSize();//获取每页多少条当做查询参数

    //排序参数
    queryObj.sort_filed = $("#sort_filed").val();
    queryObj.sort_type = $("#sort_type").val();

    ${fristLowerClassName}Action.getPage(queryObj, function (data) {
        console.log(data);
        dataPage.setPageNumber(data.pageNumber);//把后端返回的页码设置到分页组件中
        dataPage.setPageSize(data.pageSize);//把后端返回的每页多少条设置到分页组件中
        dataPage.setTotalPage(data.totalPage);//把后端总页数设置到分页组件中
        dataPage.setTotalRow(data.totalRow);//把后端返回的数据总条数设置到分页组件中
        data.firstPage ? dataPage.hidePreBtn() : dataPage.showPreBtn();//根据后端返回的是否是第一页，隐藏或显示上一页按钮
        data.lastPage ? dataPage.hideNextBtn() : dataPage.showNextBtn();//根据后端返回的是否是尾页，隐藏或显示下一页按钮

        //绘制表头和表尾
        var thead = "";
        thead += "                            <tr>";
        thead += "                                <th><input type=\"checkbox\" class=\"checkall\"></th>";
        thead += "                                <th>#</th>";
        <#list fieldList as field>
        thead += "                                <th>${field.remarks}</th>";
        </#list>
        thead += "                                <th>操作</th>";
        thead += "                            </tr>";

        //绘制表体
        $("#dataList_tbody").html("");
        //先清空现有的datatables实例的对象
        if (table) {
            //如果已经有datatables的实例，先销毁实例再从新构建dom，再初始话datatables实例，否则刷新会失效，或者排序会自动清空数据等等
            table.clear();
            table.destroy();//销毁数据对象
        }
        for (var i = 0; i < data.list.length; i++) {
            var tmp = data.list[i];
            var text = "";
            text += "                        <tr id=\""+ tmp.${primaryKey}+ "\">";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_checkbox\"><input type=\"checkbox\" value=\"" + tmp.${primaryKey} + "_checkbox\" class=\"checkchild\"></td>";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_index\">" + (i + 1) + "</td>";
            <#list fieldList as field>
            text += "                            <td id=\"" + tmp.${primaryKey} + "_${field.name}\">" + tmp.${field.name} + "</td>";
            </#list>
            text += "                            <td>";
            <#if listBtnStyle=='all'||listBtnStyle=='original'>
            <#--  生成原始按钮  -->
            text += "                            <div class=\"btn-group\">";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\">删除</button>";
            text += "                            </div>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='icon'>
            <#--  生成图标按钮  -->
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\"><i class=\"fa fa-trash\"></i>删除</button>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='dropdown'>
            <#--  生成下拉按钮  -->
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\"></span>";
            text += "                                   </button>";
            text += "                                   <ul class=\"dropdown-menu\">";
            text += "                                       <li><a href=\"javascript:window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-green\">查看</a></li>";
            text += "                                       <li><a href=\"javascript:window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-light-blue\">编辑</a></li>";
            text += "                                       <li class=\"divider\"></li>";
            text += "                                       <li><a href=\"javascript:listPageEvent.del('"+ tmp.${primaryKey} +"')\" class=\"text-red\">删除</a></li>";
            text += "                                   </ul>";
            text += "                               </div>";
            </#if>
            text += "                            </td>";
            text += "                        </tr>";

            $("#dataList_tbody").append(text);
            $("#" + tmp.${primaryKey}).data(tmp);
        }
        //再添加dom
        $("#dataList_thead").html(thead);
        $("#dataList_tfoot").html(thead);
        //最后初始化datatables
        table = loserStarDatatablesCfg.initDataTables("${tableName}_table");
    });
}

/**
 * 更新del字段
 * @param {*} del 
 */
listPageEvent.del = function (${primaryKey}){
    var title = "是否删除" + ${primaryKey};
    loserStarSweetAlertUtils.confirm(title,"",function(){
        ${fristLowerClassName}Action.delById(${primaryKey},function(data,msg,result){
            loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
                listPageEvent.queryPageList();
            });
        }, function (data, msg, result){
            loserStarSweetAlertUtils.alertError(msg,"",function(){
            });
        });
    });
}


//刷新
listPageEvent.refreshList = function () {
    listPageEvent.queryPageList();
}

//导出Excel
listPageEvent.exportExcel = function () {
    $('.btn.buttons-excel.buttons-html5.btn-primary').click();
}