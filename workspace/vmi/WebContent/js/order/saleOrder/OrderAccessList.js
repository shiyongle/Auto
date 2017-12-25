/**
 * 新增：出入库明细
 */
Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.define('DJ.order.saleOrder.OrderAccessList', {
			extend : 'Ext.c.GridPanel',
			title : "出入库明细",
			id : 'DJ.order.saleOrder.OrderAccessList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'orderAccessRecord.do',
			exporturl:"outIntoexcel.do",
			onload : function() {
				var me = this;
				MyCommonToolsZ.setComAfterrender(me, function(com) {

					var fieldT = com.down("textfield[id=findBy]");

					MyCommonToolsZ.setQuickTip(fieldT.id, "", "可输入:客户名称、制造订单号、包装物名称、操作人");

				});
				
				Ext.getCmp('DJ.order.saleOrder.OrderAccessList.addbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAccessList.editbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAccessList.delbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAccessList.viewbutton').hide();
				Ext.getCmp('DJ.order.saleOrder.OrderAccessList.querybutton').hide();
			},
			custbar : [{ xtype : "textfield",
				itemId : 'textfield',
				width : 120,
				id : 'findBy',
				emptyText : '回车搜索',
				enableKeyEvents : true,
				listeners : {
					keypress : function(me,e){
						if(e.getKey()==13){
							this.up('grid').relevanceByName();
						}
					}
				}
			    
			},{
				xtype : "mydatetimesearchbox",
				conditionDateField : "b.pcreatetime",
				labelFtext : "出入库日期"
			},{
				xtype : 'radiogroup',
				fieldLabel : '　报表类别',
				itemId : 'radiotext',
				labelWidth:70,
				id : 'groups',
				layout : {type : 'hbox'
					},
					
				columns : 3,
				hidden : false,
				vertical: true,
				items : [
				     {boxLabel: "全部明细　", name: "outIn", inputValue: "",id: "radio1" ,checked: true},
				     {boxLabel: "入库明细　", name: "outIn", inputValue: "入库" ,id: "radio2" },
				     {boxLabel: "出库明细　", name: "outIn", inputValue: "出库" ,id: "radio3"}
				     ],
				     _filterByValue : function(){
				    	var store = Ext.getCmp('DJ.order.saleOrder.OrderAccessList').getStore();
				    	store.setDefaultfilter([{
				    		myfilterfield : "ftype",
							CompareType : " like ",
							type : "string",
							value : this.getValue().outIn
				    	}]);
				    	store.setDefaultmaskstring(" #0 ");
						store.loadPage(1);
				     },
				     listeners : {
				    	 'change':function(){
				    		 for(var i=1;i<4;i++){
								 var cmp = "radio"+i;
								 var check = Ext.getCmp(cmp);
								 if(check.checked){
									 if(check.boxLabel == '全部明细'){
										 Ext.getCmp('DJ.order.saleOrder.OrderAccessList').setTitle('出入库明细');
										}else{
											Ext.getCmp('DJ.order.saleOrder.OrderAccessList').setTitle(check.boxLabel);
										}
									 this.up('grid').relevanceByName();
								 }
							 }
				    	 }
				     }
			}],
			relevanceByName : function(){
				var store = this.getStore();
				var valText = Ext.getCmp('findBy').getValue();
				var valRdaio = Ext.getCmp('groups').getValue().outIn;
				store.setDefaultfilter([{
					myfilterfield : "ftype",
					CompareType : " like ",
					type : "string",
					value : valRdaio
				},{
					myfilterfield : "cname",
					CompareType : " like ",
					type : "string",
					value : valText
				},{
					myfilterfield : "pname",
					CompareType : " like ",
					type : "string",
					value : valText
				},{
					myfilterfield : "pnumber",
					CompareType : " like ",
					type : "string",
					value : valText
				},{
					myfilterfield : "uname",
					CompareType : " like ",
					type : "string",
					value : valText
				}
				                       
				]);
				store.setDefaultmaskstring("#0 and (#1 or #2 or #3 or #4)");
				store.loadPage(1);
			},
			features: [{
				ftype : 'summary'
			}]
			,fields : [{
				name : 'pcreatetime',
				myfilterfield : 'pcreatetime',
				myfiltername : '配送日期',
				myfilterable : true
			}, {
				name : 'cname',
				myfilterfield : 'cname',
				myfiltername : '客户名称',
				myfilterable : true
			}, {
				name : 'ftype',
				myfilterfield : 'ftype',
				myfiltername : '类型',
				myfilterable : true
			},{
				name : 'pnumber',
				myfilterfield : 'pnumber',
				myfiltername : '制造订单号',
				myfilterable : true
			},{
				name : 'pname',
				myfilterfield : 'pname',
				myfiltername : '包装物名称',
				myfilterable : true
			}, {
				name : 'pspec',
				myfilterfield : 'pspec',
				myfiltername : '规格',
				myfilterable : true
			}, {
				name : 'outIn',
				myfilterfield : 'outIn',
				myfiltername : '出入库数量',
				type : 'int',
				myfilterable : true
			},  {
				name : 'uname',
				myfilterfield : 'uname',
				myfiltername : '修改人',
				myfilterable : true
			}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
				'header' : '出入库日期',
				'dataIndex' : 'pcreatetime',
				sortable : true,
				//xtype : "datecolumn",
				format : "Y-m-d H:i",
				summaryType : '',
				summaryRenderer : function(value){
					return '本页总计'
				}
				
			}, {
				'header' : '客户名称',
				'dataIndex' : 'cname',
				sortable : true
			}, {
				'header' : '制造订单号',
				'dataIndex' : 'pnumber',
				sortable : true
			}, {
				'header' : '包装物名称',
				'dataIndex' : 'pname',
				sortable : true
			}, {
				'header' : '规格',
				'dataIndex' : 'pspec',
				sortable : true
			}, {
				'header' : '类型',
				'dataIndex' : 'ftype',
				sortable : true
			}, {
				'header' : '出入库数量',
				'dataIndex' : 'outIn',
				sortable : true,
				summaryType : 'sum',
				summaryRenderer : function(value){
					return value
				}
			}, {
				'header' : '操作人',
				'dataIndex' : 'uname',
				sortable : true
			}],
			
			
			selModel:Ext.create('Ext.selection.CheckboxModel')
})