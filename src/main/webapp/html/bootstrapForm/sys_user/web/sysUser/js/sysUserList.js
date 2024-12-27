
var dataPage = {};
$(function(){
    initQueryParam();//初始化查询条件
    //使用page需要引入loserStarPage.js组件，否则如果用其它方式则移除该部分代码--------begin
    var pageCfg = {
        pageId:"pageDiv",
        pageSize:1000,
        position:"left",
        gotoCusPageCallback: listPageEvent.queryPageList,
        gotoPreviousPageCallback: listPageEvent.queryPageList,
        gotoNextPageCallback: listPageEvent.queryPageList,
    }
    dataPage = new loserStarPage(pageCfg);
    // 这两个顺序不能放前面

    listPageEvent.queryPageList();
    //使用page需要引入loserStarPage.js组件，否则如果用其它方式则移除该部分代码--------end

});

/**
 * 清空查询条件（适配原生的查询条件字段方式）
 */
function initQueryParam(){
        var id = sessionStorage.getItem("id");
        if(id!=null&&id!=undefined&&id!=''){
            $("#id").val(id);
        }
        var user_name = sessionStorage.getItem("user_name");
        if(user_name!=null&&user_name!=undefined&&user_name!=''){
            $("#user_name").val(user_name);
        }
        var password = sessionStorage.getItem("password");
        if(password!=null&&password!=undefined&&password!=''){
            $("#password").val(password);
        }
        var pwd_err_count = sessionStorage.getItem("pwd_err_count");
        if(pwd_err_count!=null&&pwd_err_count!=undefined&&pwd_err_count!=''){
            $("#pwd_err_count").val(pwd_err_count);
        }
        var del = sessionStorage.getItem("del");
        if(del!=null&&del!=undefined&&del!=''){
            $("#del").val(del);
        }
        var create_time = sessionStorage.getItem("create_time");
        if(create_time!=null&&create_time!=undefined&&create_time!=''){
            $("#create_time").val(create_time);
        }
        var create_user_code = sessionStorage.getItem("create_user_code");
        if(create_user_code!=null&&create_user_code!=undefined&&create_user_code!=''){
            $("#create_user_code").val(create_user_code);
        }
        var create_user_name = sessionStorage.getItem("create_user_name");
        if(create_user_name!=null&&create_user_name!=undefined&&create_user_name!=''){
            $("#create_user_name").val(create_user_name);
        }
        var update_time = sessionStorage.getItem("update_time");
        if(update_time!=null&&update_time!=undefined&&update_time!=''){
            $("#update_time").val(update_time);
        }
        var update_user_code = sessionStorage.getItem("update_user_code");
        if(update_user_code!=null&&update_user_code!=undefined&&update_user_code!=''){
            $("#update_user_code").val(update_user_code);
        }
        var update_user_name = sessionStorage.getItem("update_user_name");
        if(update_user_name!=null&&update_user_name!=undefined&&update_user_name!=''){
            $("#update_user_name").val(update_user_name);
        }
        var lock_begin_date = sessionStorage.getItem("lock_begin_date");
        if(lock_begin_date!=null&&lock_begin_date!=undefined&&lock_begin_date!=''){
            $("#lock_begin_date").val(lock_begin_date);
        }

    //初始化一些组件
    initControl();
}

function initControl() {
    //初始话颜色选择器bootstrap-colorpicker：https://github.com/farbelous/bootstrap-colorpicker
    $('.my-colorpicker1').colorpicker();
    //初始化高级下拉框select2:https://select2.github.io/
    $('.select2').select2({
        placeholder: '请选择'
    });
    //初始化jquery-datetimepricker时间选择器
    $('.jquery-datetimepricker').datetimepicker({
        step: 1,
        format: 'Y年m月d日 H时i分'
    });
}