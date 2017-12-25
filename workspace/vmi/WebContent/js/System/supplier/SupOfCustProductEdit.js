Ext.define('DJ.System.supplier.SupOfCustProductEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.System.supplier.SupOfCustProductEdit',
	title : "新增产品",
	width : 850,
	minHeight : 450,
	maxHeight: Ext.dom.Element.getViewportHeight(),
	closable : true,
	modal : true,
	resizable:false,
	
	constrainHeader : true,
	
	overflowY: 'auto',
	requires:['Ext.ux.form.SimpleLocalCombo','Ext.ux.form.SimpleCombo','Ext.ux.form.OneClickFileField'],
	bodyPadding: 15,
	ctype: 'Productdef',
	url:'saveSProduct.do',
	infourl: 'getSProductInfo.do',
	viewurl: 'getSProductInfo.do',
	listeners: {
		show: function(){
			var win = this;
			if(win.editstate == 'add'){
				//赋值fcustpdtid
				Ext.Ajax.request({
					url: 'getSProductId.do',
					success: function(res){
						var obj = Ext.decode(res.responseText);
						if(obj.success){
							win.fcustpdtid.setValue(obj.msg);
						}else{
							win.close();
							Ext.Msg.alert('提示','与服务器失去联系，请刷新页面重试！');
						}
					}
				});
				//赋值customerid
				var customerid = Ext.getCmp('DJ.System.supplier.SupOfCustPdtList.fcustomerid').getValue();
				if(!customerid){
					Ext.Msg.alert('提示','请先选择客户！');
					win.close();
				}
				win.fcustomerid.setValue(customerid);
			}
		}
	},
	Action_BeforeSubmit: function(){
		var win = this,fields;
		if(win.fisboardcard.getValue()==-1){	//送货凭证过来的产品，未点击纸箱信息和内盒信息，默认为纸箱信息
			win.fisboardcard.setValue(1);
		}
		if(win.fisboardcard.getValue()==1){
			fields = win.right.query('field');
		}else if(win.fisboardcard.getValue()==0){
			fields = win.left.query('field');
		}
		Ext.each(fields,function(field){
			field.submitValue = false;
		});
//		win.fisboardcard.setValue(win.showLeft?1:0);
	},
	cverifyinput: function(){
		var win = this,
			totalLength = win.fmateriallength.getValue(),
			totalWidth = win.fmaterialwidth.getValue();
		if(totalLength && totalWidth){
			var extrernes = this.extrernes;
			if(!extrernes){
				win.openMaterialLimitWin();
				return false;
			}
			var maxLength = parseFloat(extrernes.fmaxlength),
				maxWidth = parseFloat(extrernes.fmaxwidth),
				minLength = parseFloat(extrernes.fminlength),
				minWidth = parseFloat(extrernes.fminwidth);
			if(win.fisboardcard.getValue()==1 && totalLength>maxLength || totalLength<minLength || totalWidth>maxWidth || totalWidth<minWidth){
				Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
					if(action == 'yes'){
						win.openMaterialLimitWin();
					}else{
						if(totalLength>maxLength || totalLength<minLength){
							fmateriallengthField.focus(true,200);
						}else{
							fmaterialwidthField.focus(true,200);
						}
					}
				});
				return false;
			}
		}
	},
	Action_Submit : function(me) {
		var editui = Ext.getCmp(me.id);
		if (me.cautoverifyinput==true && !editui.getform().isValid()) {
			throw "输入项格式不正确，请修改后再提交！";
		};
		if(me.cverifyinput()===false){
			return;
		}
		var myform = editui.getform();
		var new_params = myform.getValues();
		new_params[me.ctype] = Ext.encode(new_params);
		var items = Ext.ComponentQuery.query("cTable", me);
		for (var i = 0; i < items.length; i++) {
			new_params[items[i].name] = Ext.encode(items[i].getcvalues());
		};
		var submit = function(){
			myform.submit({
						url : me.url,
	                    clientValidation:me.cautoverifyinput,
						method : "POST",
						waitMsg : "正在处理请求……",
						timeout : 60000,
						params : new_params,
						success : function(form, action) {
							var obj = Ext.decode(action.response.responseText);
							if (editui.parent != "") {
								Ext.getCmp(editui.parent).store.load();
							}
							djsuccessmsg(obj.msg);
							editui.close();
						},
						failure : function(f, action) {
							var obj = Ext.decode(action.response.responseText);
							Ext.MessageBox.alert("提示", obj.msg);
						}
					});
		}
		if(editui.editstate=='add'){
				var  type =editui.fisboardcard.getValue()==1?'纸箱信息':'内盒信息'; 
				Ext.Msg.confirm('提示', "确认该产品为<span style='color:red;'><b>"+type+"</b></span>?",function(btn){
					if(btn=='yes'){
						submit();
					}else{
						return;
					}
				}, this)
		}else{
			submit();
		}
	},
	onload: function(){
		var win = this;
		Ext.Ajax.request({
			url: 'selectMaterialLimit.do',
			success: function(res){
				var obj = Ext.decode(res.responseText);
				if(obj.success){
					if(obj.total){
						win.extrernes = obj.data[0];
					}else{
						win.extrernes = false;
					}
				}else{
					Ext.Msg.alert('错误',obj.msg);
					win.close();
				}
			},
			failure: function(){
				win.close();
				Ext.Msg.alert('提示','获取最值失败，请刷新页面重试！');
			}
		});
	},
	onloadfields: function(){
		var win = this,data = this.editdata,btn1,btn2,formulaFields;
		btn1 = win.down('button[itemId=纸箱信息]');
		btn2 = win.down('button[itemId=内盒信息]');
		if(win.fisboardcard.getValue()==0){
			btn2.handler();
			btn1.hide();
		}else if(win.fisboardcard.getValue()==1){
			btn2.hide();
		}
		win.generateFormula();
		win.getform().setValues(data, win.fid.getValue());
		//2015-08-07 未维护过客户产品时，修改状态时默认赋值
		if(data.fisboardcard =="-1"){
			win.fboxmodelid.setValue("1");
			win.fstavetype.setValue("普通压线");
			win.fseries.setValue("连做");
		}	
		//赋值后改无name值的公式
		win.stopFormula = true;
		win.stopLine = true;
		formulaFields = win.query('numberfield[formulaItemField=1]');
		Ext.each(formulaFields,function(field){
			field.fireEvent('blur');
		});
		win.stopFormula = false;
		win.stopLine = false;
	},
	statics: {
		uploadImg: function(){
			var win = Ext.getCmp('DJ.System.supplier.SupOfCustProductEdit'),
				fid = win.fcustpdtid.getValue();
			var uploaderCfg = {
					url : "uploadQuickCustProductImg.do" + "?fid=" + fid,
					max_file_size : '11mb',
					unique_names : false,
					multiple_queues : true,
					chunk_size : '10mb',
					file_data_name : "upload1",
					multipart : true,
					multipart_params : {},
					listeners : {
						close : function(panel, eOpts) {
							win.down('image').setSrc('getFileSourceByParentId.do?fid='+fid+'&_dc='+new Date().getTime());
						}
					},
					filters : [{
						title : "图片",
						extensions : "jpg,jpeg,png,gif,bmp"
					}]
					
			};
			var fileWin = Ext.create("DJ.tools.file.MultiUploadDialog", uploaderCfg);
			fileWin.show();
		},
		deleteImg : function() {
			var grid = Ext.create("DJ.System.supplier.SupOfPdtImgShower",{
				onload: function(){
					var me = this;
					MyCommonToolsZ.setComAfterrender(me, function() {
						me._hideButtons(['addbutton', 'editbutton', 'viewbutton',
								'querybutton', 'exportbutton']);
					});
				}
			}), 
				store = grid.getStore(),
				win = Ext.getCmp('DJ.System.supplier.SupOfCustProductEdit'),
				fid = win.fcustpdtid.getValue();
			store.getProxy().extraParams.fcusproductid = fid;
			store.loadPage(1);
			var widthT = 500;
			Ext.create('Ext.window.Window', {
				modal : true,
				resizable : false,
				height : widthT / 16 * 9,
				width : widthT,
				layout : 'fit',
				items : [grid],
				listeners : {
					close : function(panel, eOpts) {
						win.down('image').setSrc('getFileSourceByParentId.do?fid='+fid+'&_dc='+new Date().getTime());
					}
				}
			}).show();
		}
	},
	stopFormula: false,
	stopLine: false,
	generateFormula: function(){
		var win = this;
		if(win.stopFormula){
			return;
		}
		win.generateHformula();
		win.generateVformula();
	},
	generateLine: function(){
		var win = this;
		win.generateHline();
		win.generateVline();
	},
	generateHformula: function(){
		var fseries = this.fseries.getValue(),
		boxmodel = parseInt(this.fboxmodelid.getValue()),
		hContainer = this.down('fieldcontainer[fieldLabel=横向公式]'),
		flayerField = this.flayer,
		fhformulaField = this.fhformula,
		fstavetypeField = this.fstavetype,
		fhstaveexpField = this.fhstaveexp,
		fhformulaField = this.fhformula,
		g = this.generateNumberField,defaultValue;
		hContainer.removeAll();
		fhformulaField.setValue('');
		fhformulaField.setReadOnly(true);
		fhstaveexpField.setValue('');
		if(!fseries){
			return;
		}
		if(!flayerField.getValue()){
			return;
		}
		if(fstavetypeField.getValue()=='不压线'){
			return;
		}
		switch(parseInt(flayerField.getValue())){
		case 3: defaultValue=3.5;break;
		case 5: defaultValue=4;break;
		case 7: defaultValue=4.5;break;
		}
		if(boxmodel==1){
			switch(fseries){
			case '连做': 
				hContainer.add(g('fhformula0',defaultValue,12),{value:'+长+宽+长+(宽-'},g('fhformula1',0,13),{value:')'});
				hContainer.formulaText = 'x_length_width_length_width-y';
				break;
			case '不连做': 
				hContainer.add(g('fhformula0',defaultValue,12),{value:'+长+(宽-'},g('fhformula1',0,13),{value:')'});
				hContainer.formulaText = 'x_length_width-y';
				break;
			}
		}else if(boxmodel==2 || boxmodel==3 || boxmodel==4 || boxmodel==5 || boxmodel==6 || boxmodel==8){
			switch(fseries){
			case '连做': 
				hContainer.add(g('fhformula0',defaultValue,12),{value:'+长+宽+长+宽'});
				hContainer.formulaText = 'x_length_width_length_width';
				break;
			case '不连做': 
				hContainer.add(g('fhformula0',defaultValue,12),{value:'+长+宽'});
				hContainer.formulaText = 'x_length_width';
				break;
			}
		}else if(boxmodel==7){
			switch(fseries){
			case '连做': 
				hContainer.add({value:'高+长+高'});
				hContainer.formulaText = 'height_length_height';
				break;
			}
		}else if(boxmodel==0){
			fhformulaField.setReadOnly(false);
			return;
		}
		this.generateHline();
	},
	generateVformula: function(){
		var win = this,
		fseries = this.down('combobox[name=fseries]').getValue(),
		boxmodel = parseInt(this.down('combobox[name=fboxmodelid]').getValue()),
		vContainer = this.down('fieldcontainer[fieldLabel=纵向公式]'),
		flayerField = this.down('combobox[name=flayer]'),
		fvformulaField = this.down('textfield[name=fvformula]'),
		fstavetypeField = this.down('combobox[name=fstavetype]'),
		fvstaveexpField = this.down('[name=fvstaveexp]'),
		g = this.generateNumberField,layer,defaultValue;
		vContainer.removeAll();
		fvformulaField.setValue('');
		fvformulaField.setReadOnly(true);
		fvstaveexpField.setValue('');
		if(!fseries){
			return;
		}
		if(!flayerField.getValue()){
			return;
		}
		if(fseries!='连做' && fseries!='不连做'){
			return;
		}
		if(fstavetypeField.getValue()=='不压线'){
			return;
		}
		switch(boxmodel){
		case 1: 
			layer = parseInt(flayerField.getValue());
			defaultValue = (layer==3||layer==5)?0.4:(layer==7?0.6:null);
			vContainer.add({value:'(宽+'},g('fvformula0',defaultValue,14),{value:')/2+高+(宽+'},g(null,defaultValue,null,true),{value:')/2'});
			vContainer.formulaText = '((width+x)/2).toFixed(2)_height_((width+x)/2).toFixed(2)';
			break;
		case 2:
			vContainer.add({value:'(宽-'},g('fvformula0',0,14),{value:')+高+(宽-'},g(null,0,null,true),{value:')'});
			vContainer.formulaText = 'width-x_height_width-x';
			break;
		case 3:
			defaultValue = this.down('numberfield[name=fboxwidth]').getValue()/2;
			vContainer.add({value:'宽+高+'},g('fvformula0',defaultValue,14));
			vContainer.formulaText = 'width_height_x';
			break;
		case 4:
			vContainer.add({value:'高+(宽+'},g('fvformula0',0.4,14),{value:')/2'});
			vContainer.formulaText = 'height_((width+x)/2).toFixed(2)';
			break;
		case 5:
			vContainer.add({value:'(宽+'},g('fvformula0',0.4,14),{value:')/2+高'});
			vContainer.formulaText = '((width+x)/2).toFixed(2)_height';
			break;
		case 6:
			vContainer.add({value:'高'});
			vContainer.formulaText = 'height';
			break;
		case 7:
			vContainer.add({value:'高+宽+高'});
			vContainer.formulaText = 'height_width_height';
			break;
		case 8:
			layer = parseInt(flayerField.getValue());
			defaultValue = (layer==3||layer==5)?0.4:(layer==7?0.6:null);
			vContainer.add(g('fdefine1',0,13,true),{value:'+'},g('fdefine2',0,14),{value:'+'},g('fdefine3',0,15),{value:'+高+(宽+'},g('fvformula0',defaultValue,16),{value:')/2'});
			vContainer.formulaText = 'y1_y2_y3_height_((width+x)/2).toFixed(2)';
			break;
		case 0:
			fvformulaField.setReadOnly(false);
			return;
		}
		//生成压线
		this.generateVline();
	},
	//如果增加新的formulaText,要更改此生成公式的方法
	generateStaveexp: function(expArr,data){
		var x = data.x,
		y = data.y,
		y1 = data.y1,
		y2 = data.y2,
		y3 = data.y3,
		me = this,
		//正则格式
		//	x,length,width,width-y,height,y1,y2,y3
		//  ((width+x)/2).toFixed(2)
		//	/^(?:\((\(width\+(x)\)\/2)\)\.toFixed\(2\))|(?:(width|height|length)[+-][xy])|(x|y1|y2|y3|length|width|height)$/
		expReg = /^(?:\((\(w\+(x)\)\/2)\)\.toFixed\(2\))|(?:(w|h|l)-([xy]))|(x|y1|y2|y3|l|w|h)$/;
		Ext.each(expArr,function(item,index){
			item = item.replace('length','l').replace('width','w').replace('height','h');
			var arr = item.match(expReg),val,temp;
			if(!arr){
				Ext.Msg.alert('提示','生成公式失败！');
				return;
			}
			if(val = arr[5]){
				if(val !='l' && val !='w' && val !='h'){
					if(val == 'y1'){		//根据y1+y2+y3=(w+x)/2推出 y1为(w-28.6)/2的格式
						temp = (x*100-2*(y2*100+y3*100))/100;
						val = temp>0? "(w+"+temp+")/2" : "(w"+temp+")/2";
					}else{
						val = eval(val);
					}
				}
			}else if(arr[3]&&arr[4]){
				temp = eval(arr[4]);
				if(temp===0){
					val = arr[3];
				}else{
					val = arr[0].replace(arr[4],temp+'');
				}
			}else if(arr[1]&&arr[2]){
				val = arr[1];
				temp = eval(arr[2]);
				if(temp===0){
					var fboxmodelid = me.down('[name=fboxmodelid]').getValue();
					val = (fboxmodelid==1)? '(w)/2':'w/2';
				}else{ 
					val = val.replace(arr[2],temp+'');
				}
			}
			expArr[index] = '['+val+']';
		});
		return expArr.join('+');
	},
	generateHline: function(){
		if(this.stopLine){
			return;
		}
		var width = this.down('numberfield[name=fboxwidth]').getValue(),
		height = this.down('numberfield[name=fboxheight]').getValue(),
		length = this.down('numberfield[name=fboxlength]').getValue(),
		hformulaField = this.down('numberfield[name=fhformula0]'),
		h1formulaField = this.down('numberfield[name=fhformula1]'),
		fhstaveexpField = this.down('[name=fhstaveexp]'),
		hContainer = this.down('fieldcontainer[fieldLabel=横向公式]'),
		formulaText = hContainer.formulaText,
		fhformulaField = this.down('textfield[name=fhformula]'),x,y,arr,newArr = [];
		if(!width || !height || !length || !hContainer.items.length){
			return;
		}
		x = hformulaField && hformulaField.getValue();
		y = h1formulaField && h1formulaField.getValue();
		arr = formulaText.split('_');
		Ext.each(arr,function(item,index){
			newArr[index] = eval(item).toFixed(2);
		});
		fhformulaField.setValue(newArr.join('+'));
		fhstaveexpField.setValue(this.generateStaveexp(arr,{x:x,y:y}));
	},
	generateVline: function(){
		if(this.stopLine){
			return;
		}
		var width = this.down('numberfield[name=fboxwidth]').getValue(),
		height = this.down('numberfield[name=fboxheight]').getValue(),
		length = this.down('numberfield[name=fboxlength]').getValue(),
		vContainer = this.down('fieldcontainer[fieldLabel=纵向公式]'),
		vformulaField = this.down('numberfield[name=fvformula0]'),
		fdefine1Field = this.down('[name=fdefine1]'),
		fdefine2Field = this.down('[name=fdefine2]'),
		fdefine3Field = this.down('[name=fdefine3]'),
		fvstaveexpField = this.down('[name=fvstaveexp]'),
		formulaText = vContainer.formulaText,
		fvformulaField = this.down('textfield[name=fvformula]'),x,arr,y1,y2,y3,newArr=[];
		if(!width || !height || !length || !vContainer.items.length){
			return;
		}
		x = vformulaField && vformulaField.getValue();
		y1 = fdefine1Field && fdefine1Field.getValue();
		y2 = fdefine2Field && fdefine2Field.getValue();
		y3 = fdefine3Field && fdefine3Field.getValue();
		arr = formulaText.split('_');
		Ext.each(arr,function(item,index){
			newArr[index] = eval(item);
		});
		fvformulaField.setValue(newArr.join('+'));
		fvstaveexpField.setValue(this.generateStaveexp(arr,{x:x,y1:y1,y2:y2,y3:y3}));
	},
	generateNumberField: function(name,val,enterIndex,readOnly){
		var config = {xtype:'numberfield',width:28,hideTrigger:true,decimalPrecision: 1,minValue: 0,formulaItemField: '1'};
		config.listeners = {
				blur: function(){
					var parentContainer = this.up('fieldcontainer'),
					win = this.up('window'),
					isH = parentContainer.fieldLabel == '横向公式';
					if(isH){
						win.generateHline();
					}else{
						var next = this.nextSibling('numberfield');
						if(next && this.name!='fdefine1' && this.name!='fdefine2' && this.name!='fdefine3'){
							next.setValue(this.getValue());
						}
						win.generateVline();
						if(win.down('[name=fboxmodelid]').getValue()=='8'){
							var fvformulaField = win.down('[name=fvformula]'),
							fvformula = fvformulaField.getValue(),arr,fdefine1Field,fdefine1;
							if(fvformula){
								arr = fvformula.split('+');
								fdefine1Field = parentContainer.down('[name=fdefine1]');
								fdefine1Field.setValue(parseFloat(arr[4])-parseFloat(arr[1])-parseFloat(arr[2]));
								arr[0] = fdefine1Field.getValue();
								fvformulaField.setValue(arr.join('+'));
							}
						}
					}
					
				},
				specialKey: specialKey
		}
		if(name){
			config.name = name;
		}
		if(val|| val===0){
			config.value = val;
		}
		if(enterIndex){
			config.enterIndex = enterIndex;
		}
		if(readOnly){
			config.readOnly = readOnly;
		}
		return config;
	},
	openMaterialLimitWin: function(){
		var win = this,mWin;
		if(win.isOpen){
			return;
		}
		mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
		mWin.parentWin = win;
		mWin.customerid = win.fcustomerid.getValue();
		mWin.show();
		win.isOpen = true;
	},
	initComponent: function(){
		var win = this,materialStore;
		materialStore = Ext.create('Ext.data.Store', {
		    fields:['fid', 'fmaterialfid', 'fsupplierid','fsuppliername','fmaterialname','fcustpdtid'],
		    proxy: {
		        type: 'ajax',
		        url: 'getMaterialOfSProduct.do',
		        reader: {
		            type: 'json',
		            root: 'data'
		        }
		    }
		});
		var items = [{	//container1
				xtype: 'container',
				itemId: 'container1',
				style: 'border-bottom: 1px solid #000;padding-bottom:10px;',
				items: [{
					xtype: 'displayfield',
					value: '<b>产品信息</b>',
					style: 'margin-bottom: 10px'
				},{
					xtype: 'container',
					layout: 'hbox',
					items: [{
						xtype: 'container',
						width: 180,
						items: [{
							xtype: 'image',
							url: '/vmifile/defaultpic.png',
							width: 140,
							height: 120,
							style: 'border:1px solid #bbb',
							listeners : {
						        render : function(p) {//渲染后给el添加mouseover事件
							         p.getEl().on('click', function(p){ 
							             var magnifier = Ext.create('Ext.ux.form.Magnifier');
							             magnifier.loadImages({
							             	fid: win.fcustpdtid.getValue()
								         });
								         var coord = p.getXY();
								         magnifier.showAt({
								            left: coord[0] + 80,
								            top: coord[1] + 5
								         });
							         })
						        }
						   }
						},{
							xtype: 'component',
							style: 'margin-top:15px',
							html: '<div style="margin:0 auto"><a href="javascript:DJ.System.supplier.SupOfCustProductEdit.uploadImg();" style="text-decoration:none;color:black;">上传图片</a><a href="javascript:DJ.System.supplier.SupOfCustProductEdit.deleteImg();" style="margin-left:45px;text-decoration:none;color:black;">删除图片</a></div>'
						}]
					},{
						xtype: 'container',
						flex: 3,
						margin: '40 0',
						defaults: {
							labelWidth: 65
						},
						items: [{
							xtype : 'combobox',
							fieldLabel: '设置默认',
							name: 'ffileid',
							labelWidth: 65,
							valueField : 'fid',
							displayField : 'fname',
							editable: false,
							listConfig: {
								emptyText: '暂无图片...'
							},
							store:Ext.create('Ext.data.Store',{
								fields: ['fid','fname'],
								proxy:{
									type:'ajax',
									url: 'getFileListByParent.do',
									reader: {
										type: 'json',
										root: 'data'
									}
								}
							}),
							listeners: {
								expand: function(){
									var store = this.getStore(),
										params = store.getProxy().extraParams;
									if(!params.fparentid){
										params.fparentid = win.fcustpdtid.getValue()
									}
									store.load();
								}
							}
						},{
							xtype : 'ocfile',
							url : 'saveFileOfSProduct.do',
							buttonText: '上传版面',
							submitValue: false,
							isFileUpload: function(){
								return false;
							},
							style: 'margin-left:69px;',
							doChange : function(){
								var me = this;
								me.params.parentid = win.fcustpdtid.getValue();
							},
							success: function(res){
								var obj = Ext.decode(res.responseText);
								if(obj.success){
									win.down('image').setSrc('getFileSourceByParentId.do?fid='+win.fcustpdtid.getValue()+'&_dc='+new Date().getTime());
									var msg = obj.msg,
										index = msg.indexOf('_'),
										ffileField = this.prev();
									ffileField.setValue(msg.substring(0,index));
									ffileField.setRawValue(msg.substr(index+1));
									djsuccessmsg('版面上传成功！');
								}else{
									Ext.Msg.alert('提示',obj.msg);
								}
							}
						},{
							xtype: 'textfield',
							name: 'fname',
							fieldLabel: '产品名称',
							allowBlank: false,
							blankText: '请填写产品名称',
							msgTarget: 'side'
						},{
							xtype: 'textfield',
							fieldLabel: '产品规格',
							name: 'fcharacter',
							listeners: {
								blur: function(){
									var reg = /^(\d+(?:\.\d*)*)([\*xX])(\d+(?:\.\d*)*)\2(\d+(?:\.\d*)*)$/,
										val = this.getValue(),
										arr = Ext.String.trim(val).match(reg),
										fisboardcard = win.fisboardcard.getValue();
									if(arr){
										if(!(win.fboxlength.getValue() || win.fboxwidth.getValue() || win.fboxheight.getValue())){
											win.fboxlength.setValue(arr[1]);
											win.fboxwidth.setValue(arr[3]);
											win.fboxheight.setValue(arr[4]);
										}
										if(!(win.fboxlength0.getValue() || win.fboxwidth0.getValue() || win.fboxheight0.getValue())){
											win.fboxlength0.setValue(arr[1]);
											win.fboxwidth0.setValue(arr[3]);
											win.fboxheight0.setValue(arr[4]);
										}
									}
								}
							}
						},{
							xtype: 'numberfield',
							fieldLabel: '单价',
							name: 'fprice',
							decimalPrecision: 3
						},{
							xtype: 'textfield',
							fieldLabel: '产品材料',
							name: 'fmaterialcode'
						},{
							xtype: 'textfield',
							name: 'fcustpdtid',
							hidden: true,
							listeners: {
								change: function(me,newValue,oldValue){
									//win.materialGrid.getStore().getProxy().extraParams.fcustpdtid = newValue;
									if(win.editstate=='edit'){
										win.down('image').setSrc('getFileSourceByParentId.do?fid='+newValue);
										// 加载默认设置的数据来回显
										var store = win.ffileid.getStore(),
											params = store.getProxy().extraParams;
										if(!params.fparentid){
											params.fparentid = win.fcustpdtid.getValue()
										}
										store.load();
									}
								}
							}
						},{
							xtype: 'textfield',
							name: 'fcustomerid',
							hidden: true
						},{
							xtype: 'textfield',
							name: 'fid',
							hidden: true
						}]
						/*items: [{
							xtype: 'displayfield',
							value: '珍正香鸭掌物流箱1号'
						},{
							xtype: 'displayfield',
							value: '42.5 X 32.5 X 21.0'
						}]*/
					},{
						xtype: 'textareafield',
						fieldLabel: '备注',
						name: 'fdescription',
						labelAlign: 'top',
						height: '90%',
						labelWidth: 55,
						flex: 5,
						listeners:{
							change:function(field,newValue,oldValue){
								var fdescription = newValue.replace(/<br\/>/g,'\r\n');
								field.setValue(fdescription);
							}
						}
					}]
				}]
			},{	//container2
				xtype: 'container',
				itemId: 'container2',
				layout: 'hbox',
				items: [{
					xtype: 'container',
					flex: 5,
					style: 'padding:10px',
					items: [{
						xtype: 'container',
						layout: 'hbox',
						style: 'margin-bottom:10px',
						items: [{
							xtype: 'button',
							text: '<span style="color:red;">纸箱信息</span>',
							itemId: '纸箱信息',
							style: 'margin-right:10px',
							handler: function(){
								var left = win.left,
								right = win.right,
								leftFields,rightFields;
								right.hide();
								left.show();
//								right.query('field')
								win.fisboardcard.setValue(1);
								this.setText('<span style="color:red;">纸箱信息</span>');
								this.nextSibling().setText('内盒信息');
							}
						},{
							xtype: 'button',
							text: '内盒信息',
							itemId: '内盒信息',
							handler: function(){
								win.left.hide();
								win.right.show();
								win.fisboardcard.setValue(0);
								this.setText('<span style="color:red;">内盒信息</span>');
								this.prev().setText('纸箱信息');
							}
						}]
					},{		//left
						xtype: 'container',
						itemId: 'left',
						anchor: '100%',
						layout: 'hbox',
						items: [{	//left-1
							xtype: 'container',
							flex: 1,
							defaultType: 'textfield',
							defaults: {
								anchor: '95%',
								labelWidth: 65,
								width: 290,
								style: 'margin-bottom:8px'
							},
							items: [{
								xtype : 'combobox',
								fieldLabel: '制造商',
								name: 'fmtsupplierid',
								itemId: 'supplier',
								valueField : 'fid',
								displayField : 'fname',
								editable:false,
								enterIndex: 1,
								store:Ext.create('Ext.data.Store',{
									fields: ['fid', 'fname'],
									proxy:{
										type:'ajax',
										url: 'getSupplierForDeliverApply.do',
								         reader: {
								             type: 'json',
								             root: 'data'
								         }
									}
								}),
								listeners:{
									afterrender: function(){ //自动设置制造商
										var me = this,
											store = me.getStore();
//										if(win.editstate!='add' || this.getValue()){
//											return;
//										}
										//2015-08-07 改为当制造商为空时赋默认值
										if(this.getValue()){
											return;
										}										
										store.load({
											callback: function(records, operation, success){
												if(success && records.length==1){
													me.setValue(records[0]);
													me.setEditable(false);
												}else if(localStorage.$singleBoardSupplier){
													var record = Ext.Array.findBy(records,function(item,index){
														return item.get('fid')== localStorage.$singleBoardSupplier;
													});
													if(record){
														me.setValue(record);
													}
												}
											}
										});
									},
									select: function(){
										var materialfidField = win.down('combobox[name=fmaterialfid]');
										materialfidField.setHiddenValue('');
										materialfidField.setRawValue('');
									}
								}
							},{
								xtype: 'slcombo',
								fieldLabel: '楞型',
								name : 'ftilemodelid',
								hidden: true,
								editable: false,
								data:[{value:'B',text:'B'},
								      {value:'C',text:'C'},
								      {value:'E',text:'E'},		
									  {value:'BC',text:'BC'},
									  {value:'BE',text:'BE'},
									  {value:'EBC',text:'EBC'}]
							},{
								fieldLabel: '箱型',
								xtype: 'slcombo',
								name: 'fboxmodelid',
								enterIndex: 3,
								editable: false,
								value: '1',
								data:[{value:'1',text:'普通'},
									  {value:'2',text:'全包'},
									  {value:'3',text:'半包'},
									  {value:'4',text:'有底无盖'},
									  {value:'5',text:'有盖无底'},
									  {value:'6',text:'围框'},
									  {value:'7',text:'天地盖'},
									  {value:'8',text:'立体箱'},
									  {value:'0',text:'其它'}],
								listeners: {
									change: function(me,newValue,oldValue){
										var fseriesField = win.fseries;
										if(newValue=='3'){
											fseriesField.setValue('不连做');
											fseriesField.setReadOnly(true);
										}else if(newValue=='7'){
											fseriesField.setValue('连做');
											fseriesField.setReadOnly(true);
										}else{
											fseriesField.setReadOnly(false);
										}
										win.generateFormula();
									}
								}
							},{
								fieldLabel: '下料规格',
								xtype: 'fieldcontainer',
								itemId: 'materialspec',
								layout: 'hbox',
								disabled: true,
								items:[{
									xtype: 'numberfield',
									enterIndex: 5,
									flex: 3,
									margins: '0 15 0 0',
									decimalPrecision: 1,
									readOnly: true,
									emptyText: '总长',
									name: 'fmateriallength',
									validator: function(val){
										if(!val){
											return true;
										}
										return val>0?true:'请填写大于0的值';
									},
									verifyLimit: function(){
										var extrernes = win.extrernes,
											val = this.getValue();
										if(val === null){
											return;
										}
										if(extrernes === false){
											win.openMaterialLimitWin();
										}else if(extrernes){
											var maxLength = parseFloat(extrernes.fmaxlength),
												minLength = parseFloat(extrernes.fminlength);
											if(val>maxLength || val<minLength){
												Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
													if(action == 'yes'){
														win.openMaterialLimitWin();
													}
												});
											}
										}
									},
									listeners: {
										blur: function(){
											if(!this.readOnly){
												this.verifyLimit();
											}
										},
										change: function(me,val){
											if(val && me.up('fieldcontainer').disabled){
												me.up('fieldcontainer').setDisabled(false);
											}
											if(this.readOnly){
												this.verifyLimit();
											}
										}
									}
								},{
									xtype: 'displayfield',
									value: 'X',
									flex: 1
								},{
									xtype: 'numberfield',
									enterIndex: 6,
									flex: 3,
									decimalPrecision: 1,
									readOnly: true,
									emptyText: '总高',
									name: 'fmaterialwidth',
									validator: function(val){
										if(!val){
											return true;
										}
										return val>0?true:'请填写大于0的值';
									},
									verifyLimit: function(){
										var extrernes = win.extrernes,
											val = this.getValue();
										if(val === null){
											return;
										}
										if(extrernes === false){
											win.openMaterialLimitWin();
										}else if(extrernes){
											var maxWidth = parseFloat(extrernes.fmaxwidth),
												minWidth = parseFloat(extrernes.fminwidth);
											if(val>maxWidth || val<minWidth){
												Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
													if(action == 'yes'){
														win.openMaterialLimitWin();
													}
												});
											}
										}
									},
									listeners: {
										blur: function(){
											if(!this.readOnly){
												this.verifyLimit();
											}
										},
										change: function(){
											if(this.readOnly){
												this.verifyLimit();
											}
										}
									}
								}]
							},{
								fieldLabel: '成型方式',
								name: 'fseries',
								enterIndex: 8,
								xtype: 'slcombo',
								editable: false,
								data:[{value:'连做',text:'连做'},
									  {value:'不连做',text:'不连做'}],
								listeners: {
									select: function(){
										win.generateFormula();
									},
									afterrender: function(){
										var me = this,
											state = me.win.editstate;
										if(state=="add"){
											me.setValue('连做');
										}
									}
								}
							},{
								xtype: 'fieldcontainer',
								fieldLabel: '横向公式',
								defaultType: 'displayfield',
//								name: 'fhformula',
								layout: 'hbox',
								items: []
							},{
								xtype: 'textfield',
								fieldLabel: '横向压线',
								name: 'fhformula',
								enterIndex: 18,
								readOnly: true,
								listeners: {
									change: function(me,value){
										if(value&&this.readOnly){
											win.fmateriallength.setValue(eval(value));
										}
									},
									blur: function(){
										var value = this.getValue();
										if(value&&!this.readOnly){
											win.fmateriallength.setValue(eval(value));
										}
									}
								},
								regex: /^(\d+\.?\d*(\+\d+\.?\d*)*)?$/,
								regexText: '格式不正确'
							},{
								xtype: 'textfield',
								fieldLabel: '冲版编号',
								name: 'fcbnumber',
								enterIndex: 20
							},{
								xtype: 'textfield',
								fieldLabel: '印刷颜色',
								name : 'fprintcolor',
								enterIndex: 22,
								emptyText: '无'
							},{
								xtype: 'textfield',
								anchor: '100%',
								name: 'fvstaveexp',
								hidden: true
							},{
								xtype: 'textfield',
								anchor: '100%',
								name: 'fhstaveexp',
								hidden: true
							},{
								xtype: 'textfield',
								name: 'fqueryname',
								hidden: true
							}]
						},{	//container2-2
							xtype: 'container',
							flex: 1,
							defaultType: 'textfield',
							defaults: {
								anchor: '100%',
								labelWidth: 65,
								width: 290,
								style: 'margin-bottom:8px'
							},
							items: [{
							
					xtype:'cCombobox',
					fieldLabel: '材料',
					name: 'fmaterialfid',
					enterIndex: 2,
					displayField:'fqueryname',
					valueField:'fid',
					blankText:'请选择材料',
					beforeExpand : function(){
						var fsupplierid = win.down('combobox[itemId=supplier]').getValue();
						if(!fsupplierid){
							fsupplierid = -1;
						}
						this.setDefaultfilter([{
							myfilterfield : "e1.fsupplierid",
							CompareType : "=",
							type : "string",
							value : fsupplierid
						}]);
						this.setDefaultmaskstring(" #0 ");
					},
					createPicker : function() {
						var cc1 = this, cc2, cc3 = Ext.baseCSSPrefix + "menu";
						var cc4 = Ext.apply({
									parentid : cc1.id,
									selModel : {
										mode : cc1.multiSelect ? "SIMPLE" : "SINGLE"
									},
									ownerCt : cc1.ownerCt,
									cls : cc1.el.up("." + cc3) ? menuCls : "",
									floating : true,
									selectable : true,
									closeable : true,
									hidden : true,
									hiddenToolbar : false,
									pageSize : 0x64,
									Defaultfilter : cc1.Defaultfilter || {},
									Defaultmaskstring : cc1.Defaultmaskstring || "",
									focusOnToFront : false,
									listeners : {
										accept : function(cc5) {
											var cc6 = cc1.picker.getView().getSelectionModel()
													.getSelection();
											cc1.onListSelectionChange(cc1.picker, cc6);
											cc1.MyDataChanged(cc6)
										},
										cancel : function() {
											cc1.collapse()
										},
										itemdblclick : function(cc5, cc6, cc7) {
											var cc8 = [];
											cc8[0x0] = cc6;
											cc1.onListSelectionChange(cc1.picker, cc8);
											cc1.MyDataChanged(cc8);
										},
										cellkeydown: function(me, td, cellIndex, record, tr, rowIndex, e){
											if(e.getKey() == Ext.EventObject.ENTER){
												this.fireEvent('itemdblclick',me,record);
											}
										}
									}
								}, cc1.MyConfig, cc1.defaultListConfig);
						cc2 = cc1.picker = Ext.create("Ext.c.QueryGridPanel", cc4);
						cc1.mon(cc2, {});
						return cc2
					},
					listeners: {
						render: function(){
							var me = this,
								picker = me.getPicker(),
								store = picker.getStore(),
								orderedProducts,index;
							store.addListener('load',function(){
								if(picker.getStore().count()){
									if(localStorage.$BoardOrderedProducts){
										orderedProducts = localStorage.$BoardOrderedProducts.split(',');
										store.doSort(function(a,b){
											index1 = orderedProducts.indexOf(a.get('fid'));
											index2 = orderedProducts.indexOf(b.get('fid'));
											if(index1==-1){
												index1 = 9999;
											}
											if(index2==-1){
												index2 = 9999;
											}
											if(index1<index2){
												return -1;
											}else{
												return 1;
											}
										});
									}
								}
							});
						}
					},
					MyDataChanged : function(records){
						var	record = records[0];
						win.flayer.setValue(record.get('flayer'));					    	
						win.fqueryname.setValue(record.data.fname +"/"+record.data.flayer+record.data.ftilemodelid);
					},
					MyConfig:{
						width:400,
						height:200,
						hiddenToolbar:true,
						url : 'getSupCustCardboard.do',
						fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'fname',
								myfiltername : '材料名称',
								myfilterable : true
							}, {
								name : 'fsupplierid'
							}
							/*, {
								name : 'fnumber',
								myfilterfield : 'fnumber',
								myfiltername : '代码',
								myfilterable : true
							}*/
							, {
								name : 'flayer'
							}, {
								name : 'ftilemodelid'
							}
							/*, {
								name : 'feffect'
							}, {
								name : 'fcreatetime'
							}
							*/
							,{
								name:'fqueryname'/*,
								convert: function(v, record){
									var fname="";
									if(!Ext.isEmpty(record.data.fname))
									{
										fname=record.data.fname;
									}
//									if(!Ext.isEmpty(record.data.fnumber))
//									{
//										if(fname.length>0) {fname=fname+" "}
//										fname+=record.data.fnumber;
//									}
									fname+="/"+record.data.flayer+record.data.ftilemodelid;
									return fname;
								}*/
							}],
							columns:[{
								text:'序号',
								xtype:'rownumberer',
								width:40
							},{
								dataIndex:'fid',
								hidden : true
							},{
								text:'材料',
								dataIndex:'fname'
							}
							/*,{
								text:'代码',
								dataIndex:'fnumber'
							}*/
							,{
								text:'层数',
								dataIndex:'flayer'
							},{
								text:'楞型',
								dataIndex:'ftilemodelid',
								flex: 1
							}]
					}
							},{
								xtype: 'textfield',
								fieldLabel: '材料信息',
								name: 'fmaterialinfo',
								enterIndex: 4
							},{
								xtype: 'slcombo',
								fieldLabel: '层数',
								name: 'flayer',
								hidden: true,
								editable: false,
								data:[{value:'1',text:'一层'},
								      {value:'2',text:'二层'},
									  {value:'3',text:'三层'},
									  {value:'4',text:'四层'},
									  {value:'5',text:'五层'},
									  {value:'6',text:'六层'},
									  {value:'7',text:'七层'}
								],
								listeners: {
									change: function(combo,value){
										var fstavetypeField = this.win.fstavetype,
											stavetypeVal = fstavetypeField.getValue();
										win.stopFormula = true;
										if(value== '2' || value == '4' || value == '6'){
											if(stavetypeVal!='不压线'){
												fstavetypeField.setValue('不压线');
												fstavetypeField.setReadOnly(true);
											}
										}else{
											if(stavetypeVal=='不压线'){
												fstavetypeField.setValue('普通压线');
												fstavetypeField.setReadOnly(false);
											}
										}
										win.stopFormula = false;
										win.generateFormula();
									}
								}
							},{
								xtype: 'slcombo',
								fieldLabel: '压线方式',
								name: 'fstavetype',
								enterIndex: 7,
								editable: false,
								value: '普通压线',
								data:[
								      {value:'不压线',text:'不压线'},
								      {value:'普通压线',text:'普通压线'},
								      {value:'内压线',text:'内压线'},
								      {value:'外压线',text:'外压线'},
								      {value:'横压',text:'横压'}
								],
								listeners: {
									change: function(field,newValue,oldValue){
										var vlineField = win.down('textfield[name=fvformula]'),
											hlineField = win.down('textfield[name=fhformula]'),
											fboxlengthField = win.fboxlength0,
											fboxwidthField = win.fboxwidth0,
											fboxheightField = win.fboxheight0,
											boxspec = win.down('fieldcontainer[itemId=boxspec]'),
											materialspec = win.down('fieldcontainer[itemId=materialspec]'),
											fseriesField = win.down('[name=fseries]'),
											fmateriallengthField = win.down('numberfield[name=fmateriallength]'),
											fmaterialwidthField = win.down('numberfield[name=fmaterialwidth]'),
											fvstaveexpField = win.down('[name=fvstaveexp]'),
											fhstaveexpField = win.down('[name=fhstaveexp]'),
											hContainer = win.down('fieldcontainer[fieldLabel=横向公式]'),
											vContainer = win.down('fieldcontainer[fieldLabel=纵向公式]');
										if(newValue=='不压线'){
											fmateriallengthField.setReadOnly(false);
											fmaterialwidthField.setReadOnly(false);
											materialspec.setDisabled(false);
											fseriesField.setValue('连做');
											fseriesField.setReadOnly(true);
											vlineField.setValue('');
											hlineField.setValue('');
											vlineField.setDisabled(true);
											hlineField.setDisabled(true);
											fboxlengthField.setValue('');
											fboxwidthField.setValue('');
											fboxheightField.setValue('');
											boxspec.setDisabled(true);
											hContainer.removeAll();
											vContainer.removeAll();
											fvstaveexpField.setValue('');
											fhstaveexpField.setValue('');
										}else if(oldValue=='不压线'){
											fmateriallengthField.setReadOnly(true);
											fmaterialwidthField.setReadOnly(true);
											fmateriallengthField.setValue('');
											fmaterialwidthField.setValue('');
											materialspec.setDisabled(true);
											vlineField.setDisabled(false);
											hlineField.setDisabled(false);
											fseriesField.setReadOnly(false);
											boxspec.setDisabled(false);
											win.generateFormula();
										}
									}
								}
								
							},{
								xtype: 'fieldcontainer',
								fieldLabel: '纸箱规格',
								layout: 'hbox',
								defaultType: 'numberfield',
								itemId: 'boxspec',
								defaults: {
									listeners: {
										blur: function(){
											var numberFields = this.up('fieldcontainer').query('numberfield'),
												staveTypeField = win.fstavetype;
											if(numberFields[0].getValue()&&numberFields[1].getValue()&&numberFields[2].getValue()&&!staveTypeField.getValue()){
												staveTypeField.setValue('普通压线');
											}
										}
									}
								},
								items: [{
									name: 'fboxlength',
									itemId: 'fboxlength0',
									enterIndex: 9,
									flex: 1,
									margins: '0 5 0 0',
									emptyText: '长',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								},{
									xtype: 'displayfield',
									value: 'X'
								},{
									name: 'fboxwidth',
									itemId: 'fboxwidth0',
									enterIndex: 10,
									flex: 1,
									margins: '0 5 0 5',
									emptyText: '宽',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								},{
									xtype: 'displayfield',
									value: 'X'
								},{
									name: 'fboxheight',
									itemId: 'fboxheight0',
									enterIndex: 11,
									flex: 1,
									margins: '0 0 0 5',
									emptyText: '高',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								}]
							},{
								xtype: 'fieldcontainer',
								layout: 'hbox',
								fieldLabel: '纵向公式',
								defaultType: 'displayfield',
//								style: 'margin-top:41px',
								items: []
							},{
								fieldLabel: '纵向压线',
								name: 'fvformula',
								enterIndex: 19,
								readOnly: true,
								listeners: {
									change: function(me,value){
										if(value&&this.readOnly){
											win.fmaterialwidth.setValue(eval(value));
										}
									},
									blur: function(){
										var value = this.getValue();
										if(value&&!this.readOnly){
											win.fmaterialwidth.setValue(eval(value));
										}
									}
								},
								regex: /^(\d+\.?\d*(\+\d+\.?\d*)*)?$/,
								regexText: '格式不正确'
							},{
								fieldLabel: '印版编号',
								enterIndex: 21,
								name: 'fybnumber'
							},{
								fieldLabel: '打包方式',
								enterIndex: 23,
								name: 'fpackway'
							}]
						}]
					},{		//right
						xtype: 'container',
						itemId: 'right',
						hidden: true,
						anchor: '100%',
						layout: 'hbox',
						items: [{	//right-1
							xtype: 'container',
							flex: 1,
							defaultType: 'textfield',
							defaults: {
								anchor: '95%',
								labelWidth: 65,
								width: 290,
								style: 'margin-bottom:8px'
							},
							items: [{
								xtype: 'fieldcontainer',
								fieldLabel: '采购规格',
								layout: 'hbox',
								defaultType: 'numberfield',
								itemId: 'boxspec',
								defaults: {
									listeners: {
										blur: function(){
											var numberFields = this.up('fieldcontainer').query('numberfield'),
												staveTypeField = win.fstavetype;
											if(numberFields[0].getValue()&&numberFields[1].getValue()&&numberFields[2].getValue()&&!staveTypeField.getValue()){
												staveTypeField.setValue('普通压线');
											}
										}
									}
								},
								items: [{
									name: 'fboxlength',
									enterIndex: 101,
									flex: 1,
									margins: '0 5 0 0',
									emptyText: '长',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								},{
									xtype: 'displayfield',
									value: 'X'
								},{
									name: 'fboxwidth',
									enterIndex: 102,
									flex: 1,
									margins: '0 5 0 5',
									emptyText: '宽',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								},{
									xtype: 'displayfield',
									value: 'X'
								},{
									name: 'fboxheight',
									enterIndex: 103,
									flex: 1,
									margins: '0 0 0 5',
									emptyText: '高',
									decimalPrecision: 1,
									minValue: 0,
									listeners: {
										blur: function(){
											win.generateHline();
											win.generateVline();
										}
									}
								}]
							},{
								fieldLabel: '冲版刀位',
								enterIndex: 105,
								name: 'fcblocation'
							},{
								fieldLabel: '面纸规格',
								enterIndex: 107,
								name: 'ffacespec'
							},{
								fieldLabel: '菲林张数',
								enterIndex: 109,
								name: 'ffylinamount'
							},{
								fieldLabel: '冲版编号',
								enterIndex: 111,
								name: 'fcbnumber'
							},{
								fieldLabel: '打包方式',
								enterIndex: 113,
								name: 'fpackway'
							}]
						},{	//right-2
							xtype: 'container',
							flex: 1,
							defaultType: 'textfield',
							defaults: {
								anchor: '100%',
								labelWidth: 65,
								width: 290,
								style: 'margin-bottom:8px'
							},
							items: [{
								fieldLabel: '拼数',
								enterIndex: 104,
								name: 'fpinamount'
							},{
								fieldLabel: '供纸规格',
								enterIndex: 106,
								name: 'fpaperspec'
							},{
								fieldLabel: '瓦楞规格',
								enterIndex: 108,
								name: 'ftmodelspec'
							},{
								fieldLabel: '印刷颜色',
								name : 'fprintcolor',
								enterIndex: 110,
								emptyText: '无'
							},{
								fieldLabel: '印版编号',
								enterIndex: 112,
								name: 'fybnumber'
							}]
						}]
					}]
				}]
			},{		// container3
				xtype: 'textfield',
				fieldLabel: '工艺流程',
				name : 'fworkproc',
				labelWidth: 65,
				padding: 10,
				anchor: '60%'
			},{
				xtype: 'textfield',
				name: 'fcreatorid',
				hidden: true
			},{
				xtype: 'textfield',
				name: 'fcreatetime',
				hidden: true
			},{
				xtype: 'textfield',
				name: 'fnumber',
				hidden: true
			},{
				xtype: 'numberfield',
				name: 'fisboardcard',
				value: 1,
				hidden: true
			}/*{
				xtype: 'textfield',
				name: 'fstate',
				value: '0',
				hidden: true
			},{
				xtype: 'textfield',
				name: 'fiscreate',
				hidden: true
			},*/];
		(function(items,listeners){
			var self = arguments.callee;
			Ext.Array.each(items,function(item,index){
				if(item.items){
					self(item.items,item.defaults && item.defaults.listeners);
				}else if(item.enterIndex != null){
					if(item.listeners){
						item.listeners.specialkey = specialKey;
					}else{
						if(listeners){
							listeners.specialkey = specialKey;
						}else{
							item.listeners = {specialkey: specialKey};
						}
					}
				}
			});
		}(items));
		Ext.apply(win,{
			items: items
		});
		this.callParent(arguments);
		var fields = win.query('component');
		Ext.each(fields,function(item){
			var prop = item.itemId || item.name;
			if(prop){
				item.win = win;
				win[prop] = item;
			}
		});
		
	}
});
function specialKey(field,e){
	var me = this,
		enterIndex = me.enterIndex;
	if(enterIndex==null){
		return;
	}
	if(e.getKey() == Ext.EventObject.ENTER){
		win = this.up('window');
		if(enterIndex==23 || enterIndex==113){
			win.fworkproc.focus(true);
			this.fireEvent('blur',this);
		}else{
			var nextField = win.down('textfield[enterIndex='+(++enterIndex)+']');
			while(!nextField || nextField.readOnly || (nextField.up('fieldcontainer') && nextField.up('fieldcontainer').disabled)){
				nextField = win.down('textfield[enterIndex='+(++enterIndex)+']');
			}
			if(nextField.xtype == 'numberfield' || nextField.xtype == 'textfield'){
				nextField.focus(true);
			}else{
				nextField.focus();
			}
			this.fireEvent('blur',this);
		}
	}else if((me.name=='fmaterialfid' || me.name=='faddressid') && e.getKey()==Ext.EventObject.DOWN){
		var picker = me.getPicker();
		picker.getSelectionModel().select(0);
	}
};