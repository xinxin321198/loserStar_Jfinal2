var table;
var listPageEvent = {}
/**
 * 刷新
 */
listPageEvent.refresh = function() {
    listPageEvent.queryPageList();
}

/**
 * 查询数据并渲染出分页表格（适配loserStar自己写的原生bootstrap分页组件）
 */
listPageEvent.queryPageList = function () {
    //构建查询条件
    var queryObj = {};
    queryObj.id = $("#id").val();
    queryObj.user_name = $("#user_name").val();
    queryObj.password = $("#password").val();
    queryObj.pwd_err_count = $("#pwd_err_count").val();
    queryObj.del = $("#del").val();
    queryObj.create_time = $("#create_time").val();
    queryObj.create_user_code = $("#create_user_code").val();
    queryObj.create_user_name = $("#create_user_name").val();
    queryObj.update_time = $("#update_time").val();
    queryObj.update_user_code = $("#update_user_code").val();
    queryObj.update_user_name = $("#update_user_name").val();
    queryObj.lock_begin_date = $("#lock_begin_date").val();
    //分页参数
    queryObj.pageNumber = dataPage.getPageNumber();//获取当前页码当做查询参数
    queryObj.pageSize = dataPage.getPageSize();//获取每页多少条当做查询参数

    //排序参数
    queryObj.sort_filed = $("#sort_filed").val();
    queryObj.sort_type = $("#sort_type").val();

    sysUserAction.getPage(queryObj, function (data) {
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
        thead += "                                <th>主键id，用作账号名</th>";
        thead += "                                <th>用户名</th>";
        thead += "                                <th>密码</th>";
        thead += "                                <th>密码输入错误次数</th>";
        thead += "                                <th>删除标志</th>";
        thead += "                                <th>创建时间</th>";
        thead += "                                <th>创建人编号</th>";
        thead += "                                <th>创建人姓名</th>";
        thead += "                                <th>更新时间</th>";
        thead += "                                <th>更新人编号</th>";
        thead += "                                <th>更新人姓名</th>";
        thead += "                                <th>密码输入次数过多时被锁定的时间记录</th>";
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
            text += "                        <tr id=\""+ tmp.id+ "\">";
            text += "                            <td id=\"" + tmp.id + "_checkbox\"><input type=\"checkbox\" value=\"" + tmp.id + "_checkbox\" class=\"checkchild\"></td>";
            text += "                            <td id=\"" + tmp.id + "_index\">" + (i + 1) + "</td>";
            text += "                            <td id=\"" + tmp.id + "_id\">" + tmp.id + "</td>";
            text += "                            <td id=\"" + tmp.id + "_user_name\">" + tmp.user_name + "</td>";
            text += "                            <td id=\"" + tmp.id + "_password\">" + tmp.password + "</td>";
            text += "                            <td id=\"" + tmp.id + "_pwd_err_count\">" + tmp.pwd_err_count + "</td>";
            text += "                            <td id=\"" + tmp.id + "_del\">" + tmp.del + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_time\">" + tmp.create_time + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_user_code\">" + tmp.create_user_code + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_user_name\">" + tmp.create_user_name + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_time\">" + tmp.update_time + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_user_code\">" + tmp.update_user_code + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_user_name\">" + tmp.update_user_name + "</td>";
            text += "                            <td id=\"" + tmp.id + "_lock_begin_date\">" + tmp.lock_begin_date + "</td>";
            text += "                            <td>";
            text += "                            <div class=\"btn-group\">";
            text += "                            <button id=\""+ tmp.id + "_viewBtn\" type=\"button\" class=\"btn btn-success\" onclick=\"window.open('formPageView.do?id=" + tmp.id +"','_self')\">查看</button>";
            text += "                            <button id=\""+ tmp.id + "_editBtn\" type=\"button\" class=\"btn btn-primary\" onclick=\"window.open('formPage.do?id=" + tmp.id +"','_self')\">编辑</button>";
            text += "                            <button id=\""+ tmp.id + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"listPageEvent.del('"+ tmp.id+ "')\">删除</button>";
            text += "                            </div>";
            text += "                            <button id=\""+ tmp.id + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?id=" + tmp.id +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.id + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?id=" + tmp.id +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.id + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"listPageEvent.del('"+ tmp.id+ "')\"><i class=\"fa fa-trash\"></i>删除</button>";
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\"></span>";
            text += "                                   </button>";
            text += "                                   <ul class=\"dropdown-menu\">";
            text += "                                       <li><a href=\"javascript:window.open('formPageView.do?id=" + tmp.id +"','_self')\" class=\"text-green\">查看</a></li>";
            text += "                                       <li><a href=\"javascript:window.open('formPage.do?id=" + tmp.id +"','_self')\" class=\"text-light-blue\">编辑</a></li>";
            text += "                                       <li class=\"divider\"></li>";
            text += "                                       <li><a href=\"javascript:listPageEvent.del('"+ tmp.id +"')\" class=\"text-red\">删除</a></li>";
            text += "                                   </ul>";
            text += "                               </div>";
            text += "                            </td>";
            text += "                        </tr>";

            $("#dataList_tbody").append(text);
            $("#" + tmp.id).data(tmp);
        }
        //再添加dom
        $("#dataList_thead").html(thead);
        $("#dataList_tfoot").html(thead);
        //最后初始化datatables
        table = loserStarDatatablesCfg.initDataTables("sys_user_table");
    });
}

/**
 * 更新del字段
 * @param {*} del 
 */
listPageEvent.del = function (id){
    var title = "是否删除" + id;
    loserStarSweetAlertUtils.confirm(title,"",function(){
        sysUserAction.delById(id,function(data,msg,result){
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