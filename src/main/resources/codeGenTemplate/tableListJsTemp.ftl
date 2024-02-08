<#setting classic_compatible=true>

var dataPage = {};
$(function(){
    //使用page需要引入loserStarPage1.1.js组件，否则如果用其它方式则移除该部分代码--------begin
    /*
    var pageCfg = {
        pageId:"userPage",
        gotoCusPageCallback: listPageEvent.queryList,
        gotoPreviousPageCallback: listPageEvent.queryList,
        gotoNextPageCallback: listPageEvent.queryList,
    }
    dataPage = new loserStarPage(pageCfg);
    // 这两个顺序不能放前面
    initQueryParam();
    listPageEvent.queryPageList();
    */
    //使用page需要引入loserStarPage1.1.js组件，否则如果用其它方式则移除该部分代码--------end

    initQueryParam();
    listPageEvent.queryList();
});

/**
 * 清空查询条件（适配原生的查询条件字段方式）
 */
function initQueryParam(){
    <#list fieldList as field>
        var ${field.name} = sessionStorage.getItem("${field.name}");
        if(${field.name}!=null&&${field.name}!=undefined&&${field.name}!=''){
            $("#${field.name}").val(${field.name});
        }
    </#list>

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