<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>优惠券列表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
        $(function(){
            $('#luckTicketManageTable').datagrid({
                title : '优惠券管理',
                loadMsg : '数据装载中......',
                url:'${ctx}/coupons/load.do',
                fit:true,
                fitColumns:false,
                singleSelect:true,
                nowrap : true,
                striped : true,    //为true,显示斑马线效果。
                multiSort : true,  //定义是否允许多列排序
                remoteSort : true, //定义从服务器对数据进行排序
                pagination : true, //为true,则底部显示分页工具栏
                rownumbers : true, //为true,则显示一个行号列
                frozenColumns : [[{field : 'id1',checkbox : true}]],
                pageSize : 20,//每页显示的记录条数，默认为10 
                pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
                columns : [[
                        {title : '用户ID',hidden:true,field : 'id',rowspan : 1,align : 'center',width : '100'},
                        {title : '业务类型',field : 'fbusinessType',rowspan : 1,align : 'center',width : '120'}, 
                        {title : '方案名称',field : 'fcouponsName',rowspan : 1,align : 'center',width : '150'},
                        {title : '优惠券类型',field : 'couponsType',rowspan : 1,align : 'center',width : '150'},
                        {title : '操作人',field : 'creatorName',rowspan : 1,align : 'center',width : '150'},
                        {title : '操作时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '150'}
                    ]],
                toolbar:'#toolbar'
            });
            // 时间戳转换 
            function formatterTime(value){
                var d = new Date(value);    //根据时间戳生成的时间对象
                var date = (d.getFullYear()) + "-" + toDou(d.getMonth() + 1)+"-"+toDou(d.getDate())+" "+toDou(d.getHours())+":"+toDou(d.getHours())+":"+toDou(d.getSeconds());
                return date;
            }
            //时间补零
            function toDou(n){
                return n<10?"0"+n:""+n;
            }
            $('#luckTicketManageTable').datagrid('getPager').pagination({
        		beforePageText : '第',//页数文本框前显示的汉字 
        		afterPageText : '页    共 {pages} 页',
        		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        		onBeforeRefresh : function(pageNumber, pageSize) {
        			$(this).pagination('loading');
        		}
        	});
            //新增优惠券
            $('#add').click(function(){
                $("#createWindow").window({
                    title : "新增优惠券",
                    href : '${ctx}/coupons/create.do',
                    width : 470,
                    height : 270,
                    draggable:true,//是否拖动
                    resizable:true,//是否可以拉动大小
                    maximizable:false,//最大化
                    minimizable:false,//最小化
                    collapsible:false,//折叠按钮是否显示
                    modal : true//模式化窗口
                });
            });
            //查看明细
            $('#see').click(function(){
                var rows = $("#luckTicketManageTable").datagrid('getSelections');
                if(rows.length == 0){
                    $.messager.alert('提示', '选择一条数据！', 'info');
                    return;
                }
                $("#createWindow").window({
                    title : "查看明细",
                    href : '${ctx}/coupons/view.do?id='+rows[0].id,
                    width : 320,
                    height : 200,
                    draggable:true,//是否拖动
                    resizable:false,//是否可以拉动大小
                    maximizable:false,//最大化
                    minimizable:false,//最小化
                    collapsible:false,//折叠按钮是否显示
                    modal : true//模式化窗口
                });
            });
        });
    </script>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="luckTicketManageTable">
			</table>
		</div>
        <div id="toolbar">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">新增优惠券</a>┆
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="see">查看明细</a>
        </div>
		<div id="createWindow" style="margin:0 auto; overflow:hidden;" ></div>
	</div>
</body>
</html>