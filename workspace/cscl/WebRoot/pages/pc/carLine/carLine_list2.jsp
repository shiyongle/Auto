<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>车辆排队2</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<style>
	td[field="_1carSpec"],td[field="_2carSpec"],td[field="_3carSpec"],td[field="_4carSpec"],td[field="_5carSpec"],td[field="_6carSpec"],td[field="_7carSpec"],td[field="_8carSpec"],td[field="_9carSpec"],td[field="_10carSpec"]{
		border-right:2px solid blue;
	}
	.datagrid-view2 .datagrid-header-row:nth-child(1) td{
		border-right:2px solid blue;
	}
</style>
<script type="text/javascript">
$(document).ready(function(){   
    //查询select
    $("#factory").combobox({
        url : "${ctx}/select/getUseFactory.do",
        editable : true,
        width:120,
        mode: 'local',
        valueField: 'optionId',//提交值
        textField: 'optionName',//显示值
        validType:"comboxValidate['factory']",
        delay:2000,
        filter:searchItem
    }).combobox('select','-1');
    //车辆排队表 
    $('#carLineTable').datagrid({
        title : '车辆排队',
        loadMsg : '数据装载中......',
        url:"${ctx}/carLine/load2.do",
        fit:true,
        fitColumns:false,
        nowrap : true,
        striped : true,    //为true,显示斑马线效果。
        multiSort : true,  //定义是否允许多列排序
        remoteSort : true, //定义从服务器对数据进行排序
        pagination : true, //为true,则底部显示分页工具栏
        rownumbers : true, //为true,则显示一个行号列
        singleSelect:true,
        pageSize : 20,//每页显示的记录条数，默认为10 
        pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
        toolbar:[{
                    id : "pai",
                    text : '派车',
                    iconCls : 'icon-add',
                    handler : function(){
                        if($('input[name="getId"]:checked').length == 0){
                            $.messager.alert('提示', '请选择撒！！');
                            return;
                        }
                        var thisId = $('input[name="getId"]:checked').data('thisid');
                        if(thisId == null){
                        	$.messager.alert('提示', '选择非空的数据！！');
                            return;
                        }
						 $.messager.confirm('提示', '你确定要派车吗?', function(r){
							if (r){
								$.ajax({
		                            type : "POST",
		                            dataType:"json",
		                            url : "${ctx}/carLine/send.do?id="+thisId,
		                            success : function(response) {
		                                if(response.success == "success"){
		                                    $.messager.alert('提示', '派车成功','info',function(){
		                                        $('#carLineTable').datagrid('reload');
		                                     });
		                                }else{
		                                    $.messager.alert('提示', '操作失败');
		                                } 
		                            }
		                        });
							}
						});                       
                    }
                },'-',{
                    id : "cancel",
                    text : '取消签到',
                    iconCls : 'icon-cancel',
                    handler:function(){
                        if($('input[name="getId"]:checked').length == 0){
                            $.messager.alert('提示', '请选择撒！！');
                            return;
                        }
                        var thisId = $('input[name="getId"]:checked').data('thisid');
                        if(thisId == null){
                        	$.messager.alert('提示', '选择非空的数据！！');
                            return;
                        }
                        $("#createWindow").window({
                            title : "取消签到",
                            href : "${ctx}/carLine/view.do?id="+thisId,
                            width : 460,    
                            height :220,
                            modal : true,
                            draggable:true,
                            resizable:true,
                            maximizable:false,
                            minimizable:false,
                            collapsible:false,
                            inline:false
                        });
                    }

        }],
        columns :[
           [
            {title : '苍南',colspan:3},
            {title : '瑞安',colspan:3},
            {title : '平阳',colspan:3},
            {title : '乐清',colspan:3},
            {title : '龙湾',colspan:3},
            {title : '瓯海',colspan:3},
            {title : '鹿城',colspan:3},
            {title : '台州',colspan:3},
            {title : '永嘉',colspan:3},
            {title : '其他',colspan:3}
           ],
           [
            /*苍南*/
            {title : '',field : '_1fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_1carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_1carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*瑞安*/
            {title : '',field : '_2fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_2carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_2carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*平阳*/
            {title : '',field : '_3fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_3carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_3carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*乐清*/
            {title : '',field : '_4fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_4carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_4carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*龙湾*/
            {title : '',field : '_5fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_5carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_5carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*瓯海*/
            {title : '',field : '_6fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_6carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_6carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*鹿城*/
            {title : '',field : '_7fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_7carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_7carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
             /*台州*/
            {title : '',field : '_8fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_8carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_8carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
              /*永嘉*/
            {title : '',field : '_9fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_9carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_9carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec},
              /*其他*/
            {title : '',field : '_10fcar_line_id',rowspan : 1,align : 'center',width : '40',formatter:check},
            {title : '车牌',field : '_10carNum',rowspan : 1,align : 'center',width : '80'},
            {title : '车型',field : '_10carSpec',rowspan : 1,align : 'center',width : '40',formatter:carSpec}
            ]
        ]
    });
    $('#carLineTable').datagrid('getPager').pagination({
        beforePageText : '第',//页数文本框前显示的汉字 
        afterPageText : '页    共 {pages} 页',
        pageSize:500,
        pageList: [500],
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh : function(pageNumber, pageSize) {
            $(this).pagination('loading');
        }
    });
    //余额查询按钮点击事件
    $("#carLineButton").click(function() {
        $('#carLineTable').datagrid('load', JSON.parse(toJOSNStr($("#searchCarLineForm").serializeArray())));
    });
});
    // raido
    function check(value,rowData,rowIndex){
        return '<input type="radio" name="getId" data-thisid="'+value+'">';
    }
    //规格
    function carSpec(value,rowData,rowIndex){
        if(value == null){
            return " ";
        } else if(value == 1){
            return "面包车";
        } else if (value == 2) {
            return "2.5";
        } else if (value == 3) {
            return "4.2";
        } else if (value == 4) {
            return "5.2";
        } else if (value == 5) {
            return "6.8";
        } else if (value == 6){
            return "9.6";
        } else {
            return "规格有误";
        }
    }
    //表单信息转JSON
    function toJOSNStr(jObject) {
        var results = '{';
        jQuery.each(jObject, function(i, field) {
            if (i == 0) {
                results += '"' + field.name + '":"' + field.value.trim() + '"';
            } else {
                results += ',"' + field.name + '":"' + field.value.trim() + '"';
            }
        });
        return results + '}';
    }

</script>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
        <div data-options="region:'north',title:'查询条件'" style="height: 120px; border: 0px;">
            <form id="searchCarLineForm">
                <table style="margin: 20px auto;">
                    <tr>
                        <td class="m-info-title" align="right">签到工厂:</td>
                        <td>
                            <select name="factory_id" id="factory" class="easyui-combobox" data-options="width:120,editable: false">
                            </select>
                        </td>
                        <td class="m-info-title" align="right"> 车辆规格:</td>
                        <td>
                            <select name="carSpec" id="carSpec" class="easyui-combobox" data-options="width:120,editable: false">
                                <option value="">全部</option>
                                <option value="1">面包车</option>
                                <option value="2">2.5米</option>
                                <option value="3">4.2米</option>
                                <option value="4">5.2米</option>
                                <option value="5">6.8米</option>
                                <option value="6">9.6米</option>
                            </select>
                        </td>
                        <td class="m-info-title" align="right"> 车牌号:</td>
                        <td>
                            <input name="keyWord" class="easyui-validatebox" placeholder="请输入车牌号" style="width: 140px;"/>
                        </td>
                        <td>
                            <a id="carLineButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>  
        <div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
            <table id="carLineTable">
            </table>
        </div>
        <div id="createWindow" style="margin: 0 auto; overflow:hidden;" ></div>
    </div>
</body>
</html>
