Ext.define('DJ.cardboard.order.saleOrder.CSProductplanList', {
			extend : 'Ext.c.GridPanel',
			title : "纸板制造商订单管理",
			id : 'DJ.cardboard.order.saleOrder.CSProductplanList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCProductPlanList.do',
			exporturl:"extportCProductPlanList.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				var me = this;
				var button = ['新  增','修  改','查   看','删    除'];//,'查    询'
				Ext.Array.forEach(button,function(item,index,allitems){
					me.down('[text='+item+']').hide();
				})
				
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [
			{
				text : '订单关闭',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CSProductplanList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录导入EAS！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids += record[i].get("fid") ;
						if (i < record.length - 1) {
								ids = ids + ",";
							}
						if(record[i].get('fstate')==1){
							Ext.MessageBox.alert("信息","已确认的订单不能关闭！");
							return;
						}
					}
					
					Ext.Ajax.request({
						timeout: 600000,
						url : "orderclose.do",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.cardboard.order.saleOrder.CSProductplanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								
							}
						}
					});
					
				}
			},
			{
				text : '订单反关闭',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CSProductplanList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录导入EAS！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids +=  record[i].get("fid") ;
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					
					Ext.Ajax.request({
						timeout: 600000,
						url : "UnOrderclose.do",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								//Ext.MessageBox.alert('成功', obj.msg);
								djsuccessmsg( obj.msg);

								Ext.getCmp("DJ.cardboard.order.saleOrder.CSProductplanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
								
							}
						}
					});
					
				}
			},{
				xtype:'combobox',
				displayField: 'name',
				valueField: 'value',
				value:'d.fid,',
				editable:false,
				listeners:{
					select:function(me){
						me.up('grid').findCproductplan();
					}
				},
				store:Ext.create('Ext.data.Store',{
					fields: ['name', 'value'],
					data : [
					         {"name":"待确认", "value":"d.fstate,0"},
					         {"name":"已确认", "value":"d.fstate,1"},
					         {"name":"部分入库", "value":"d.fstate,2"},
					         {"name":"全部入库", "value":"d.fstate,3"},
					         {"name":"未关闭", "value":"d.fcloseed,0"},
					         {"name":"已关闭", "value":"d.fcloseed,1"},
					         {"name":"全部", "value":"d.fid,"}
					     ]
				})
			},{
				xtype:'textfield',
				emptyText :'回车搜索...',
				enableKeyEvents:true,
				listeners:{
					keypress:function(me,e,eOpts ){
						if(e.getKey()==13){
							me.up('grid').findCproductplan();
						}
					},
					render:function(me){
						Ext.tip.QuickTipManager.register({
						    target: me.id,
//						    text: '可输入制造订单号、客户名称、制造商...'
						    text: '可输入制造订单号、客户名称、制造商、纸箱规格、下料规格、成型方式、材料、订单来源'
						});
					}
				}
			}],
			findCproductplan:function(){
//				var combox = this.down('[xtype=combobox]').getValue();
//				var text = this.down('[xtype=textfield]').getValue();
//				var value = combox.split(',');
//				this.getStore().setDefaultfilter([{
//					myfilterfield : value[0],
//					CompareType : "like",
//					type : "string",
//					value : value[1]
//				},{
//					myfilterfield : 'd.fnumber',
//					CompareType : "like",
//					type : "string",
//					value : text
//				},{
//					myfilterfield : 'c.fname',
//					CompareType : "like",
//					type : "string",
//					value : text
//				},{
//					myfilterfield : 's.fname',
//					CompareType : "like",
//					type : "string",
//					value : text
//				}]);
//				this.getStore().setDefaultmaskstring(" #0 and (#1 or #2 or #3)");
//				this.getStore().loadPage(1);
				var combox = this.down('[xtype=combobox]').getValue();
				var text = this.down('[xtype=textfield]').getValue();
				var value = combox.split(',');
				var filter =[{
					myfilterfield : value[0],
					CompareType : "like",
					type : "string",
					value : value[1]
				},{
					myfilterfield : 'd.fnumber',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : 'c.fname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : 's.fname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : "CONCAT_WS('x',da.fboxlength,da.fboxwidth,da.fboxheight)",
					CompareType : "like",
					type : "string",
					value : text.replace(/\*/g,'X')
				},{
					myfilterfield :"CONCAT_WS('x',da.fmateriallength,da.fmaterialwidth)",
					CompareType : "like",
					type : "string",
					value : text.replace(/\*/g,'X')
				},{
					myfilterfield : 'da.fseries',
					CompareType : "=",
					type : "string",
					value : text
				},{
					myfilterfield : 'f.fqueryname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : "CASE d.ftype  WHEN 1 THEN '客户订单' ELSE '平台订单' END",
					CompareType : "like",
					type : "string",
					value : text
				}];
				var defaultmaskstring =" #0 and (#1 or #2 or #3 or #4 or #5 or #6 or #7 or #8)";
				this.getStore().loadPage(1,{params:{defaultfilter:Ext.encode(filter),defaultmaskstring:defaultmaskstring}});
			},
			fields : [{
				name : 'fid'
			}, {
				name : 'fnumber',
				myfilterfield : 'd.fnumber',
				myfiltername : '制造订单号',
				myfilterable : true
			}, {
				name : 'cname',
				myfilterfield : 'c.fname',
				myfiltername : '客户名称',
				myfilterable : true
			}, {
				name : 'fname',
				myfilterfield : 'f.fname',
				myfiltername : '材料',
				myfilterable : true
			}, {
				name : 'sname',
				myfilterfield : 's.fname',
				myfiltername : '制造商',
				myfilterable : true
			}, {
				name : 'ftype',
				myfilterfield : 'd.ftype',
				myfiltername : '订单来源(1客户订单、0平台订单)',
				myfilterable : true
			}, {
				name : 'fstate'
			}, {
				name : 'fboxmodel',
				myfilterfield : 'da.fboxmodel',
				myfiltername : '箱型(1普通、2全包、2半包、0其他)',
				myfilterable : true
			}, {
				name : 'fboxlength'
			}, {
				name : 'fboxheight'
			}, {
				name : 'fboxwidth'
			}, {
				name : 'fmateriallength'
			}, {
				name : 'fmaterialwidth'
			}, {
				name : 'fstavetype'
			}, {
				name : 'fvformula'
			},{
				name : 'fhformula'
			}, {
				name : 'fseries',
				myfilterfield : 'da.fseries',
				myfiltername : '成型方式',
				myfilterable : true
			},{
				name : 'famount'
			}, {
				name : 'famountpiece'
			}, {
				name : 'fstockinqty'
			}, {
				name : 'fstockoutqty'
			}, {
				name : 'farrivetime'
			}, {
				name : 'fimportEAS',
				myfilterfield : 'd.fimportEAS',
				myfiltername : '是否导入(1是、0否)',
				myfilterable : true
			},{
				name : 'fcreatetime'
			}, {
				name : 'fcreatorid'
			}, {
				name : 'fdeliverapplyid'
			},'fcloseed'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
				'header' : 'fid',
				'dataIndex' : 'fid',
				hidden : true,
				hideable : false,
				sortable : true
			}, {
				'header' : '制造订单号',
				'dataIndex' : 'fnumber',
				sortable : true
			}, {
				'header' : '制造商',
				'dataIndex' : 'sname',
				sortable : true
			}, {
				'header' : '订单来源',
				'dataIndex' : 'ftype',
				sortable : true,
				 renderer: function(value){
				        if (value == 0) {
				            return '平台订单';
				        }else if(value==1){
				        	return '客户订单';}
			        	
				    }
			
			}, {
				'header' : '客户名称',
				'dataIndex' : 'cname',
				sortable : true
			},  {
				'header' : '订单状态',
				'dataIndex' : 'fstate',
				sortable : true,
				 renderer: function(value,items){
					 var val = '';
					 switch(value){
						 case '0' :val = '待确认';break;
						 case '1' :val = '已确认';break;
						 case '2' :val = '部分入库';break;
						 case '3' :val = '全部入库';break;
					 }
					 if(items.record.get('fcloseed')==1){
						 val = '已关闭';
					 }
					 return val;
				    }
			}, {
				'header' : '材料',
				'dataIndex' : 'fname',
				width:130,
				sortable : true
			}, {
				'header' : '箱型',
				width:50,
				'dataIndex' : 'fboxmodel',
				sortable : true,
				renderer : function(value) {
					if (value == 1) {
						return '普通';
					}else if(value == 2){
						return '全包';
					}else if(value == 3){
						return '半包';
				    }else {
						return '其他';
					}
				}
			}, {
				'header' : '纸板规格(CM)',
				draggable:false,
				columns:[{
					header : '长',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fboxlength'
				},{
					header : '宽',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fboxwidth'
				},{
					header : '高',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fboxheight'
				}]
			}, {
				'header' : '下料规格(CM)',
				draggable:false,
				columns:[{
					header : '总长',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fmateriallength'
				},{
					header : '总高',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fmaterialwidth'
				}]
			}, {
				'header' : '压线方式',
				'dataIndex' : 'fstavetype',
				sortable : true
			},{
				'header' : '压线公式',
				draggable:false,
				columns:[{
					header : '纵向公式',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fvformula'
				},{
					header : '横向公式',
					draggable:false,
					align : 'center',
					'dataIndex' : 'fhformula'
				}]
			}, {
				'header' : '成型方式',
				'dataIndex' : 'fseries',
				hidden : false,
				hideable : false,
				sortable : true
			},
				{
				'header' : '配送数量',
				draggable:false,
				columns:[{
					header : '只',
					draggable:false,
					align : 'center',
					'dataIndex' : 'famount'
				},{
					header : '片',
					draggable:false,
					align : 'center',
					'dataIndex' : 'famountpiece'
				}]
			}, {
				'header' : '入库数量/片',
				'dataIndex' : 'fstockinqty',
				sortable : true
			}, {
				'header' : '出库数量/片',
				'dataIndex' : 'fstockoutqty',
				sortable : true
			}, {
					text : '配送时间',
					dataIndex : 'farrivetime',
					sortable : true,
					renderer:function(value){
						return value.substring(0,16);
					}
				}, {
					text : '导入接口',
					dataIndex : 'fimportEAS',
					sortable : true,
					renderer:function(val){
						return val=='1'?'是':'否'
					}

			},{
					text : '创建人',
					dataIndex : 'fcreatorid',
					sortable : true
				}, {
					text : '创建时间',
					dataIndex : 'fcreatetime',
					width : 150,
					sortable : true,
					renderer:function(value){
						return value.substring(0,16);
					}
				}],
				
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})

		
		
