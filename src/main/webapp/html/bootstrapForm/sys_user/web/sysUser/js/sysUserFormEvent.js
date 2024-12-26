var formPageEvent = {}

/**
 * 保存
 */
formPageEvent.save = function () {
    var saveData = {};
    saveData.id = $("#id").val();//主键id，用作账号名
    saveData.user_name = $("#user_name").val();//用户名
    saveData.password = $("#password").val();//密码
    saveData.pwd_err_count = $("#pwd_err_count").val();//密码输入错误次数
    saveData.del = $("#del").val();//删除标志
    saveData.create_time = $("#create_time").val();//创建时间
    saveData.create_user_code = $("#create_user_code").val();//创建人编号
    saveData.create_user_name = $("#create_user_name").val();//创建人姓名
    saveData.update_time = $("#update_time").val();//更新时间
    saveData.update_user_code = $("#update_user_code").val();//更新人编号
    saveData.update_user_name = $("#update_user_name").val();//更新人姓名
    saveData.lock_begin_date = $("#lock_begin_date").val();//密码输入次数过多时被锁定的时间记录
    //校验值
    if (!loserStarJsUtils.checkObj(saveData.id)){
        loserStarSweetAlertUtils.alertWarning("请填写“主键id，用作账号名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“用户名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.password)){
        loserStarSweetAlertUtils.alertWarning("请填写“密码”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.pwd_err_count)){
        loserStarSweetAlertUtils.alertWarning("请填写“密码输入错误次数”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.del)){
        loserStarSweetAlertUtils.alertWarning("请填写“删除标志”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_time)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建时间”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_user_code)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建人编号”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建人姓名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_time)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新时间”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_user_code)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新人编号”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新人姓名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.lock_begin_date)){
        loserStarSweetAlertUtils.alertWarning("请填写“密码输入次数过多时被锁定的时间记录”","",function(){});
        return;
    }

    //保存
    sysUserAction.save(saveData, function (data, msg, result) {
        loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
            $("#id").val(data.id);
            initData();
        });
    }, function (data, msg, result) {
        loserStarSweetAlertUtils.alertError(msg, "", function () { });
    });
}