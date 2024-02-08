<#setting classic_compatible=true>
var table;
<#--查询table表格的js代码(适配datatables) -->
var listPageEvent = {}
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
        initDataTables();
    });
}

/**
 * 初始化datatables，该方法得放在渲染html的dom数据之前，否则刷新数据会有问题
 * 具体配置参考 http://datatables.club/reference/option/
 */
function initDataTables(){
        table = $('#${tableName}_table').DataTable({
                columnDefs: [{ "orderable": false, "targets": [0] }],
        //使用配置项的方式
        // language: datatablesCfg.language,
        //使用配置文件的方式
        language: {
            url:'../bower_components/DataTables-1.13.6/i18n/zh.json'
        },
        //初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
        destroy: true,
        //当显示更少的记录时，是否允许表格减少高度
        scrollCollapse:false,
        //是否保存表格状态
        stateSave: false,
        //是否自动宽度
        autoWidth:true,
        //水平滚动
        scrollX:true,
        //垂直滚动
        scrollY:false,
        //移动端适配
        responsive: false,
        //选择行
        select: 'multi',
        //拖动列
        colReorder: true,
        //是否开启排序
        ordering:true,
        //是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
        processingOption:true,
        //控制是否显示表格左下角的信息
        info:true,
        // 是否开启本地分页
        paging: true,
        /**
         * 分页按钮显示选项
         *  numbers 只显示数字 （1.10.8版本）
            simple 只有上一页和下一页两个按钮
            simple_numbers 上一页和下一页两个按钮，加上页数按钮
            full 首页，尾页，上一页和下一页四个按钮
            full_numbers 首页，尾页，上一页和下一页四个按钮,加上数字按钮
            first_last_numbers 首页，尾页两个按钮,加上数字按钮
         */
        pagingTypeOption:"full_numbers",
        //改变初始化页长度（每页多少条数据）
        pageLengthOption:100,
        //是否允许调整每页多少条
        lengthChange: true,
        //是否允许Datatables开启本地搜索
        searching:true,
        //高亮显示表格中排序的列
        orderCellsTopOption: true,
        //表格周围元素显示
        dom: '<"toolbar"B>frtipl',
        buttons: [
            {
                text: '新增',
                action: function (dt, node, config) {
                    window.open('formPage.do', '_self');
                },
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("btn-primary");
                }
            },
            {
                text: '刷新',
                action: function (dt, node, config) {
                    listPageEvent.queryList();
                },
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("btn-info");
                }
            },
            {
                text: '获取选中行的html数据',
                action: function (dt, node, config) {
                    var rows = table.getCheckedRows();
                    console.log(rows);
                },
                init: function (dt, node, config) {
                }
            },
            {
                text: '获取选中行id',
                action: function (dt, node, config) {
                    var ids = table.getChkedIds();
                    console.log(ids);
                },
                init: function (dt, node, config) {
                }
            },
            {
                text: '获取选中行的数据对象',
                action: function (dt, node, config) {
                    var datas = table.getChkedDatas();
                    console.log(datas);
                },
                init: function (dt, node, config) {
                }
            },
            {
                extend: 'collection',
                text: '导出',
                enabled: true,
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("bg-olive");
                },
                buttons: [{
                    extend: 'copy',
                    text: '复制',
                },
                {
                    extend: 'csv',
                    text: '导出csv'
                },
                {
                    extend: 'excel',
                    text: '导出Excel'
                },
                {
                    extend: 'pdf',
                    text: '导出pdf'
                },
                {
                    extend: 'print',
                    text: '打印'
                },]
            },
        ],
    });
    //单击行事件
    table.off("click","tr");//先移除之前的事件（否则刷新后会多次触发事件）
    table.on('click', 'tr',function (e) {
        var jqRow = $(this);//获取行的jQuery对象
        var row = table.row(this);//获取tr对象
        var rowid = row.id();;//获取tr的id
        var index = row.index();//获取序号
        var rowData = row.data();//获取行数据

        //单击行时选中checkbox
        var check = $(this).find(".checkchild").prop("checked");
        if (check && check == true) {
            $(this).find('.checkchild').prop("checked", false);
        } else {
            $(this).find('.checkchild').prop("checked", true);
        }
    });

    //给表头的checkbox添加全选的事件方法
    $(".checkall", table.containers()).off("click");//先移除之前的事件（否则刷新后会多次触发事件）
    $(".checkall", table.containers()).on('click', function () {
        var check = $(this).prop("checked");
        $(".checkchild").prop("checked", check);
    });

    //添加一个方法，可获取到选中行的数据
    table.getCheckedRows = function(){
        return table.rows('.selected').data().toArray();
    }
    
    //添加一个方法，可获取到选中行row的id集合
    table.getChkedIds = function () {
        return table.rows('.selected').ids().toArray();
    }

    //添加一个方法，可获取到选中行row的数据对象集合
    table.getChkedDatas = function () {
        var ids = table.rows('.selected').ids().toArray();
        var datas = [];
        for (var i = 0; i < ids.length; i++) {
            datas.push($("#" + ids[i]).data());
        }
        return datas;
    }
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

        var text = "";
        for (var i = 0; i < data.list.length; i++) {
            var tmp = data[i];
            text += "                        <tr>";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_index\">" + (i + 1) + "</td>";
            <#list fieldList as field>
            text += "                            <td id=\"" + tmp.${primaryKey} + "_${field.name}\">" + tmp.${field.name} + "</td>";
            </#list>
            text += "                            <td><button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary\">编辑</button>";
            text += "                        </td>";
            text += "                        </tr>";
        }
        $("#dataList_tbody").html(text);
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
                listPageEvent.queryList();
            });
        }, function (data, msg, result){
            loserStarSweetAlertUtils.alertError(msg,"",function(){
            });
        });
    });
}