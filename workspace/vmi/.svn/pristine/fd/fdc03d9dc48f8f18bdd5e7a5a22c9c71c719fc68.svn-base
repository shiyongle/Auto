Ext.require(["DJ.myComponent.viewer.PdfViewer"]);

Ext.define('Ext.form.field.Month', {
    extend:'Ext.form.field.Date',
    alias: 'widget.monthfield',
    requires: ['Ext.picker.Month'],
    alternateClassName: ['Ext.form.MonthField', 'Ext.form.Month'],
    selectMonth: null,
    createPicker: function() {
        var me = this,
            format = Ext.String.format;
        return Ext.create('Ext.picker.Month', {
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: { 
        select:        { scope: me,   fn: me.onSelect     }, 
        monthdblclick: { scope: me,   fn: me.onOKClick     },    
        yeardblclick:  { scope: me,   fn: me.onOKClick     },
        OkClick:       { scope: me,   fn: me.onOKClick     },    
        CancelClick:   { scope: me,   fn: me.onCancelClick }        
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
    },
    onCancelClick: function() {
        var me = this;    
    me.selectMonth = null;
        me.collapse();
    },
    onOKClick: function() {
        var me = this;    
    if( me.selectMonth ) {
               me.setValue(me.selectMonth);
            me.fireEvent('select', me, me.selectMonth);
    }
        me.collapse();
    },
    onSelect: function(m, d) {
        var me = this;    
    me.selectMonth = new Date(( d[0]+1 ) +'/1/'+d[1]);
    }
});

Ext.define('DJ.order.Deliver.StatementList', {
	extend : 'Ext.c.GridPanel',
	title : "对账单",
	id : 'DJ.order.Deliver.StatementList',
	pageSize : 50,
	remoteSort:false,
	url : 'GetStatementList.do',
	Delurl : "DeleteStatement.do",
	onload : function() {
		Ext.getCmp('DJ.order.Deliver.StatementList.addbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementList.editbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementList.viewbutton').hide();
		Ext.getCmp('DJ.order.Deliver.StatementList.exportbutton').hide();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	custbar : [{
			text:'上传',
			id : 'DJ.order.Deliver.StatementList.addfile',
			height : 30,
			handler : function(){
	        	var win = Ext.create('DJ.order.Deliver.StatementList.loadupWin');
				win.show();
			}
		}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fcustname',
	}, {
		name : 'fmonth',
		myfilterfield : 'tos.fmonth',
		myfiltername : '年月',
		myfilterable : true
	}, {
		name : 'ffileid',
	}, {
		name : 'fcreatename',
	}, {
		name : 'fcreatetime',
	}
	],
	columns : [{
		dataIndex:'fid',
		hidden:true,
		hideable:false
	}, {
		text:'客户',
		dataIndex:'fcustname',
		width : 200
	}, {
		text:'年月',
		dataIndex:'fmonth',
		width : 90
	}, {
		dataIndex:'fcreatename',
		text:'上传者',
		width : 150
	}, {
		dataIndex:'fcreatetime',
		text:'上传时间',
		width : 150
	}, {
		xtype:'templatecolumn',
		text:'查看',
		dataIndex:'fname',
		tpl : '<a href="javascript:void(0)" style="text-decoration:none;" onclick="DJ.myComponent.viewer.PdfViewer.show(\'{ffileid}\',\'{fmonth}\');">查看</a>',
		width : 50
	}, {
		dataIndex:'',
		text:'',
		flex:1
	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});


Ext.define('DJ.order.Deliver.StatementList.loadupWin', {
	extend : 'Ext.Window',
	id : 'DJ.order.Deliver.StatementList.loadupWin',
	modal : true,
	width : 450,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	items : [{
		xtype : 'form',
		id : 'DJ.order.Deliver.StatementList.loadupWin.form',
		baseCls : 'x-plain',
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		defaults : {
			labelSeparator : ":",
			labelWidth : 100,
			width : 200,
			allowBlank : false,
			labelAlign : "left",
			msgTarget : ""
		},

		items : [{
			fieldLabel:　'年  月',
			labelWidth: 80,
	        xtype:　'monthfield',
	        name:　'fyearmonth',
	        hiddenName : 'date',
	        format:　"Y年m月",
	        editable　:　false,
	        value : Ext.Date.add(new Date(), Ext.Date.MONTH, -1)
         },
		{
    		id : 'DJ.order.Deliver.StatementList.fcustomerid',
 			labelWidth: 80,
    		name:'fcustomerid',
    		fieldLabel : '客户名称',
    		xtype:'cCombobox',
    		displayField:'fname', // 这个是设置下拉框中显示的值
    	    valueField:'fid', // 这个可以传到后台的值
    	    allowBlank : false,
    	    blankText:'请选择客户',
    	    editable: false, // 可以编辑不
    	    MyConfig : {
						width : 800,//下拉界面
						height : 200,//下拉界面
						url : 'GetCustomerList.do',  //下拉数据来源
						fields : [ {
							name : 'fid'
						}, {
							name : 'fname',
							myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
							myfiltername : '客户名称',//在过滤下拉框中显示的名称
							myfilterable : true//该字段是否查找字段
						}, {
							name : 'fnumber'
						}, {
							name : 'findustryid'
						}, {
							name : 'faddress'
						}, {
							name : 'fisinternalcompany',
							convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
						} ],
						columns : [ {
							text : 'fid',
						dataIndex : 'fid',
						hidden : true,
						sortable : true
					}, {
						text : '编码',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '客户名称',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '行业',
						dataIndex : 'findustryid',
						sortable : true
					}, {
						text : '地址',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}, {
						text : '内部客户',
						dataIndex : 'fisinternalcompany',
						xtype:'checkcolumn',
						processEvent : function() {
						},
						sortable : true
					}]
				}
	   		}, {
			xtype : 'filefield',
			name : 'loadupfileName',
			fieldLabel : '上传PDF',
			labelWidth: 80,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			buttonText : '选择文件',
			validator: function(value){
	            var arr = value.split('.');
	            if(arr[arr.length-1] != 'pdf'){
	              return '请上传PDF文件！！';
	            }else{
	              return true;
	            }
			}
		}],
		buttons : [{
			text : '上传',
			handler : function() {
				
				var form = this.up('form').getForm();
				var fcustomerid = form.findField('fcustomerid').getValue();
				var fyearmonth = form.findField('fyearmonth').rawValue;
				// 解决特殊字符的编码问题
				var paramsArray = [];
				paramsArray.push("?fcustomerid");
				paramsArray.push("=" + encodeURIComponent(fcustomerid));
				paramsArray.push("&fyearmonth");
				paramsArray.push("=" + encodeURIComponent(fyearmonth));
				var paramsT = paramsArray.join("");
				if(form.isValid()){
					var me=Ext.getCmp("DJ.order.Deliver.StatementList.loadupWin");
					var el = me.getEl();
					el.mask("文件上传中...");
					form.submit({
						url:'uploadStatementFile.do'+paramsT,
						success:function(me,action){
							var obj = Ext.decode(action.response.responseText);
							if(obj.success){
								var store = Ext.getCmp('DJ.order.Deliver.StatementList').getStore().load();
								var windows = Ext.getCmp("DJ.order.Deliver.StatementList.loadupWin");
								if (windows != null) {
									windows.close();
								}
								djsuccessmsg(obj.msg);
							} else{
								Ext.Msg.alert('错误',obj.msg);
							}
							el.unmask();
						}
					,failure : function(response, option) {
						Ext.Msg.alert('错误',option.result.msg);
						el.unmask();
					}
					})
				}
			}
		}]
	}]
});
