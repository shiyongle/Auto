<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增好运劵优惠活动</title>
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
        $('#luckTicketServerTable').datagrid({
            title : '优惠券活动管理',
            loadMsg : '数据装载中......',
            url:'${ctx}/couponsActivity/load.do',
            fit:true,
            fitColumns:false,
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
                    {title : '用户ID',hidden:true,field : 'fid',rowspan : 1,align : 'center',width : '100'},
                    {title : '折扣',hidden:true,field : 'fdiscount',rowspan : 1,align : 'center',width : '150'},
                    {title : '直减',hidden:true,field : 'fsubtract',rowspan : 1,align : 'center',width : '150'},
                    {title : '增值服务名',hidden:true,field : 'faddserviceName',rowspan : 1,align : 'center',width : '150'},
                    {title : '活动结束时间',hidden:true,field : 'factivityEndTimeString',rowspan : 1,align : 'center',width : '150'},
                    {title : '使用时间',hidden:true,field : 'fuseTimeString',rowspan : 1,align : 'center',width : '150'},
                    {title : '前置条件',field : 'factivityType',rowspan : 1,align : 'center',width : '120',formatter:function(value,row,index){
                    	if(value == 0){
                    		return '发放人群';
                    	} else if(value == 1){
                    		return '充值';
                    	} else {
                    		return '发放人群';
                    	}
                    }}, 
                    {title : '业务类型',field : 'fbusinessType',rowspan : 1,align : 'center',width : '120'}, 
                    {title : '方案名称',field : 'fcouponsName',rowspan : 1,align : 'center',width : '150'},
                    {title : '优惠活动状态',field : 'feffective',rowspan : 1,align : 'center',width : '150',formatter:function(value,row,index){
                        if(value == 1){
                            return '有效';
                        }else if(value == 2){
                            return '待生效';
                        }else if(value == 3){
                            return '过期';
                        }else{
                            return '- -';
                        }
                    }},
                    {title : '优惠券类型',field : 'fcouponsType',rowspan : 1,align : 'center',width : '150',formatter:function(value,row,index){
                    	if(value == 1){
                    		if(row.fdiscount !=null){
                    			return '折扣';
                    		}
                    		if(row.fsubtract !=null){
                    			return '直减';
                    		}	
                    	}else{
                    		return row.faddserviceName;
                    	}
                    }},
                    {title : '活动时间',field : ' factivityStartTimeString',rowspan : 1,align : 'center',width : '250',formatter:function(value,row,index){
                        return row.factivityStartTimeString+' - '+row.factivityEndTimeString;
                    }},
                    {title : '使用有效期',field : ' fuseStartTimeString',rowspan : 1,align : 'center',width : '250',formatter:function(value,row,index){
                        if(row.fuseStartTimeString == null || row.fuseEndTimeString == null){
                        	return row.fuseTime+'天';
                        }else{
                        	return row.fuseStartTimeString+' - '+row.fuseEndTimeString;
                        }
                    }},
                    {title : '有效期结束时间',hidden:true,field : 'fuseEndTimeString',rowspan : 1,align : 'center',width : '150'},
                    {title : '操作时间',field : 'operateTimeString',rowspan : 1,align : 'center',width : '150'}
                ]],
            toolbar:'#toolbar'
        });
        // 分页
        $('#luckTicketServerTable').datagrid('getPager').pagination({
    		beforePageText : '第',//页数文本框前显示的汉字 
    		afterPageText : '页    共 {pages} 页',
    		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    		onBeforeRefresh : function(pageNumber, pageSize) {
    			$(this).pagination('loading');
    		}
    	});
        //导出
        var feffective = '';
        $('#export').click(function(){
        	$("#luckTicketServerTable").datagrid("loading");
			var rows = $("#luckTicketServerTable").datagrid('getSelections');
			$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/couponsActivity/exportExecl.do",
				data : getOrderId(rows)+ "&feffective="+feffective,
				success : function(response) {
					if(response.success){
						window.location.href ="${ctx}/excel/"+response.url;
						$("#luckTicketServerTable").datagrid("loaded");
					}else{
						$.messager.alert('提示', '操作失败');
						$("#luckTicketServerTable").datagrid("loaded");
					} 
				}
			});
        })
        //新增优惠活动
        $('#add').click(function(){
            $("#createWindow").window({
                title : "新增优惠券活动",
                href : '${ctx}/couponsActivity/create.do',
                width : 600,
                height : 460,
                draggable:true,//是否拖动
                resizable:false,//是否可以拉动大小
                maximizable:false,//最大化
                minimizable:false,//最小化
                collapsible:false,//折叠按钮是否显示
                modal : true//模式化窗口
            });
        });
        
        //查看明细
        $('#see').click(function(){
            var rows = $("#luckTicketServerTable").datagrid('getSelections');
            if(rows.length > 1){
                $.messager.alert('提示', '一次只能查看一条数据！', 'info');
                return;
            }else if(rows.length == 0){
            	$.messager.alert('提示', '请选择一条数据查看！', 'info');
                return;
            }
            $("#createWindow").window({
                title : "查看明细",
                href : '${ctx}/couponsActivity/view.do?id='+rows[0].fid,
                width : 600,
                height : 420,
                draggable:true,//是否拖动
                resizable:false,//是否可以拉动大小
                maximizable:false,//最大化
                minimizable:false,//最小化
                collapsible:false,//折叠按钮是否显示
                modal : true//模式化窗口
            });
        });
        
        //effectiveQuery
        $('#effectiveQuery').combobox({
            panelHeight:150,
            width:145,
            mode: 'local',
            filter:searchItem,//筛选
            editable:false,
            onSelect:function(record){
                $('#luckTicketServerTable').datagrid('load',{
                        feffective:record.value
                });
                feffective = record.value;
            }   
        });
        
        //有效
        $('#effective').click(function(){
        	 var rows = $("#luckTicketServerTable").datagrid('getSelections');
        	 if(rows.length > 1){
                 $.messager.alert('提示', '一次只能操作一条数据！', 'info');
                 return;
             }else if(rows.length == 0){
             	$.messager.alert('提示', '请选择一条数据进行操作！', 'info');
                 return;
             }else if(rows[0].feffective == 1 || rows[0].feffective == 3 ){
            	 $.messager.alert('提示', '请选择一条失效的数据！', 'info');
                 return;
             }
        	 $.messager.confirm('提示','确定要把这条数据生效吗？', function(r){
        		if (r){
        			$.ajax({
        				url:'${ctx}/couponsActivity/change.do?id='+rows[0].fid,
        				type : "POST",
        				dataType:"json",
        				success:function(rec){
        					if(rec.success){
        						$.messager.alert('提示', "操作成功！");
        						$("#luckTicketServerTable").datagrid('reload');
        					}else{
        						$.messager.alert('提示', "操作失败！");
        					}
        				}
        			});	
        		}
        	});
        });
        
        //待生效
		$('#invalid').click(function(){
			 var rows = $("#luckTicketServerTable").datagrid('getSelections');
        	 if(rows.length > 1){
                 $.messager.alert('提示', '一次只能操作一条数据！', 'info');
                 return;
             }else if(rows.length == 0){
             	$.messager.alert('提示', '请选择一条数据进行操作！', 'info');
                 return;
             }else if(rows[0].feffective == 2 || rows[0].feffective == 3 ){
            	 $.messager.alert('提示', '请选择一条有效的数据！', 'info');
                 return;
             }
        	 $.messager.confirm('提示','确定要把这条数据待生效吗？', function(r){
        		if (r){
        			$.ajax({
        				url:'${ctx}/couponsActivity/change.do?id='+rows[0].fid,
        				type : "POST",
        				dataType:"json",
        				success:function(rec){
        					if(rec.success){
        						$.messager.alert('提示', "操作成功！");
        						$("#luckTicketServerTable").datagrid('reload');
        					}else{
        						$.messager.alert('提示', "操作失败！");
        					}
        				}
        			});	
        		}
        	});    	
		});
        
        //批量获取id
        function getOrderId(rows) {
			var paramStr = '';
			for ( var i = 0; i < rows.length; i++) {
				if (i == 0) {
					paramStr += 'ids=' + rows[i].fid;
				} else {
					paramStr += "&ids=" + rows[i].fid;
				}
			}
			return paramStr;
		}
    });
</script>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="luckTicketServerTable">
			</table>
		</div>
        <div id="toolbar">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">新增优惠券活动</a>┆
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="see">查看明细</a>┆
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" id="export">导出明细</a>┆
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" id="effective">有效</a>┆
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" id="invalid">待生效</a>┆
       	优惠劵状态<select name="" id="effectiveQuery"  style="width:120px;">
                        <option value="">全部</option>
                        <option value="1">有效</option>
                        <option value="2">待生效</option>
                        <option value="3">过期</option>
              </select>
        </div>
		<div id="createWindow" style="margin: 0 auto; overflow:hidden;" ></div>
	</div>
</body>
</html>