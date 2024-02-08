var loserStarFileWindow;

$(function () {
    //初始化下拉框数据
    //var selectOptionHtml = loserStarBoostrapUtils.getSelectHtml(dict.字典类型, true, "value", "name");
    //$("#select的id").html(selectOptionHtml);
    initData();//初始化主数据

    //初始化附件上传
    initFileWindow();

    initFileData();
});

/**
 * 初始化数据
 */
function initData() {
    var id = $("#id").val();
    if (!loserStarJsUtils.checkObj(id)) {
        $("#form_title2").text("新增");
        initControl();//初始化控件
    } else {
        $("#form_title2").text("修改");
        sysUserAction.getById(id, function (data, msg, result) {
            $("#id").val(data.id);
            $("#user_name").val(data.user_name);
            $("#password").val(data.password);
            $("#pwd_err_count").val(data.pwd_err_count);
            $("#del").val(data.del);
            $("#create_time").val(data.create_time);
            $("#create_user_code").val(data.create_user_code);
            $("#create_user_name").val(data.create_user_name);
            $("#update_time").val(data.update_time);
            $("#update_user_code").val(data.update_user_code);
            $("#update_user_name").val(data.update_user_name);
            initControl();//初始化控件

            //select控件赋值
            //loserStarJsUtils.selectedOption("#select的id", data.字段值);

            //radio控件赋值
            //loserStarJsUtils.setSelectedForRadio("#radio的name", data.字段值);

            //textarea自动高度
            //loserStarJsUtils.autoTextAreaHeight($("#remarks")[0]);
        });
    }
}

/**
 * 初始化一些页面上的组件（通常在dom数据加载完成后再执行，否则会有诡异问题，比如颜色选择器点开都是白色，而且会自动给你赋值白色）
 */
function initControl(){
    //初始话颜色选择器bootstrap-colorpicker：https://github.com/farbelous/bootstrap-colorpicker
    $('.my-colorpicker1').colorpicker();
    //初始化高级下拉框select2:https://select2.github.io/
    $('.select2').select2({
        placeholder: '请选择'
    });
}

/**
 * 初始化附件上传组件
 */
function initFileWindow() {
    //添加附件上传window
    var fileOpt = {
        flagId: "loserStar",
        // fileTypeList: [],
        uploadFinishedCallback: function () {
            initFileData();
        }
    }
    loserStarFileWindow = new loserStarFileUploadBootstrapWindow_WebUploader(fileOpt);
}

/**
 * 初始化附件记录
 */
function initFileData(){
    var param = {};
    param.from_id = $("#id").val();
    param.from_table = "loserstar.sys_user";
    sysFileAction.getListData(param, function (data, msg, result){
        var text = "";
        for(var i=0;i<data.length;i++){
            var tmp = data[i];
            $("#fileList").show();
            text += "<li href=\"#\" class=\"list-group-item list-group-item-default\"";
            text += "                                                        target=\"_self\">";
            text += "                                                        <a type=\"button\" class=\"btn btn-link\" href=\"../sysFile/downFile.do?id=" + tmp.id+"\">下载</a>";
            text += "                                                        <a type=\"button\" class=\"btn btn-link\" href=\"javascript:formPageEvent.delFile('"+tmp.id+"')\">删除</a>";
            text += tmp.name;
            text += "                                                    </li>";
        }
        $("#fileList").html(text);

    });
}