Ext.define('DJ.order.Deliver.singleBoardDeliverapplyEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.singleBoardDeliverapplyEdit',
	title : "纸板订单编辑界面",
	width : 600,
	minHeight : 250,
	closable : true,
	modal : true,
	resizable:false,
	layout:'fit',
	requires:['Ext.ux.form.SimpleLocalCombo'],
	bodyPadding: '15px 10px',
	ctype: 'Deliverapply',
	url:'saveBoardSingleDeliverapply.do',
	infourl: 'selectBoardSignleDeliverapply.do',
	viewurl: 'selectBoardSignleDeliverapply.do',
	cverifyinput: function(){
		var win = this;
		var fmateriallengthField = this.down('numberfield[name=fmateriallength]'),
			fmaterialwidthField = this.down('numberfield[name=fmaterialwidth]'),
			vContainer = this.down('fieldcontainer[fieldLabel=纵向公式]'),
			fstavetype = this.down('[name=fstavetype]').getValue(),
			fvline = Ext.String.trim(this.down('[name=fvline]').getValue()),
			fhline = Ext.String.trim(this.down('[name=fhline]').getValue()),
			totalLength = fmateriallengthField.getValue(),
			totalWidth = fmaterialwidthField.getValue(),
			boxLength = this.down('numberfield[name=fboxlength]').getValue(),
			boxWidth = this.down('numberfield[name=fboxwidth]').getValue(),
			boxHeight = this.down('numberfield[name=fboxheight]').getValue(),
			supplier = this.down('combobox[name=fsupplierid]').getValue(),
			boxmodel = this.down('slcombo[name=fboxmodel]').getRawValue();
		win.down('numberfield[name=famount]').fireEvent('blur');
		if((!totalLength || !totalWidth) && (!boxLength||!boxWidth||!boxHeight)){
			throw '纸箱规格和下料规格必须填一项!';
		}
		if(fstavetype != '不压线'){
			if(!fvline){
				Ext.Msg.alert('提示','纵向压线不能为空！');
				return false;
			}
			if(!fhline){
				Ext.Msg.alert('提示','横向压线不能为空！');
				return false;
			}
		}
		/*if(vContainer.formulaText == 'y1_y2_y3_height_((width+x)/2).toFixed(2)'){
			var arr = fvline.split('+');
			if(parseFloat(arr[0])+parseFloat(arr[1])+parseFloat(arr[2])!=parseFloat(arr[4])){
				Ext.Msg.alert('提示','纵向压线前3个值相加要等于最后一个值，请更改！');
				return false;
			}
		}*/
		if(totalLength && totalWidth){
			var extrernes = this.extrernes;
			if(!extrernes){
				var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
				mWin.parentWin = win;
				mWin.show();
				return false;
			}
			var maxLength = parseFloat(extrernes.fmaxlength),
				maxWidth = parseFloat(extrernes.fmaxwidth),
				minLength = parseFloat(extrernes.fminlength),
				minWidth = parseFloat(extrernes.fminwidth);
			if(totalLength>maxLength || totalLength<minLength || totalWidth>maxWidth || totalWidth<minWidth){
				Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
					if(action == 'yes'){
						var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
						mWin.parentWin = win;
						mWin.show();
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
			//2015-08-20 by lu新增   根据需求限制东经落料规格控制
			if(supplier =="39gW7X9mRcWoSwsNJhU12TfGffw="){
				if((totalLength>350 || totalLength<15) || (totalWidth>217 || totalWidth<13)){
					Ext.Msg.alert('提示','落料超过制造商最值,请联系制造商！');
					return false;
				}
				//if(boxLength) true肯定是连坐有长宽高
				if(boxLength){
					if(boxmodel=="普通"){						
						if(boxLength<5 || boxWidth<5 || boxHeight<5){
							Ext.Msg.alert('提示','普通箱型规格过小,请联系制造商！');
							return false;
					}	
					else{
						if(boxLength<1 || boxWidth<1 || boxHeight<1){
							Ext.Msg.alert('提示','所选箱型规格过小,请联系制造商！');
							return false;							
						}
					}
					}
				}
			}
		}
		var fvalues = win.getform().getValues();
		var cc3 = win.getform().getValues();
		cc3["Deliverapply"] = Ext.encode(fvalues);
		Ext.Ajax.request({
			url : 'checkBdDeliverapply.do',
			async : false,
			params : cc3,
			success : function(res) {
				var obj = Ext.decode(res.responseText);
				if (obj.success) {				
					if(obj.data[0].flag == 1){
						Ext.Msg.confirm('提示',obj.msg,function(action){
						if(action == 'yes'){
							win.Action_realSubmit();
						}
						});	
					}
					else{
						win.Action_realSubmit();
					}
				}				
			}
		});
	},
	Action_realSubmit : function(){
		win = this;
		if (win.cautoverifyinput == true && !win.getform().isValid()) {
			throw "输入项格式不正确，请修改后再提交！";
		};
		var saveBtn = Ext.getCmp(win.id+'.savebutton');
		saveBtn.setDisabled(true);
		var cc2 = win.getform();
		var cc3 = cc2.getValues();
		cc3[win.ctype] = Ext.encode(cc3);
		var cc4 = Ext.ComponentQuery.query("cTable", win);
		for (var cc5 = 0x0; cc5 < cc4.length; cc5++) {
			cc3[cc4[cc5].name] = Ext.encode(cc4[cc5].getcvalues());
		};
		cc2.submit({
					url : win.url,
					clientValidation : win.cautoverifyinput,
					method : "POST",
					waitMsg : "正在处理请求……",
					timeout : 0xea60,
					params : cc3,
					success : function(cc6, cc7) {
						var cc8 = Ext.decode(cc7.response.responseText);
						if (win.parent != "") {
							Ext.getCmp(win.parent).store.load();
						};
						if(win.editstate == 'edit' || win.editstate == 'copy'){
							djsuccessmsg('保存成功！');
							win.close();
							return;
						}
						saveBtn.setDisabled(false);
						Ext.MessageBox.alert("提示", "保存成功！",function(){
							win.down('combobox[name=fboxmodel]').focus(false, 400);
						});
						win.getFormulaDefaultValue();
						win.setInitData();
					},
					failure : function(cc6, cc7) {
						var cc9 = Ext.decode(cc7.response.responseText);
						saveBtn.setDisabled(false);
						Ext.MessageBox.alert("提示", cc9.msg);
					}
		});
	},
	Action_Submit : function(cc0) {
		cc0.cverifyinput();
//		if(cc0.cverifyinput()===false){
//			return;
//		}
	},
	onloadfields: function(){
		var win = this,data = this.editdata;
		this.down('combobox[name=fmaterialfid]').setmyvalue({
			fid: data.fmaterialfid_fid,
			fname: data.fmaterialfid_fname,
			flayer: data.fmaterialfid_flayer,
			ftilemodelid: data.fmaterialfid_ftilemodelid
		});
		this.down('combobox[name=fmaterialfid]').flayer = parseInt(data.flayer);
		this.generateFormula();
		var fdefine1Field = this.down('[name=fdefine1]'),
			fdefine2Field = this.down('[name=fdefine2]'),
			fdefine3Field = this.down('[name=fdefine3]'),
			fhformulaField = this.down('[name=fhformula]'),
			fhformula1Field = this.down('[name=fhformula1]'),
			fvformulaField = this.down('[name=fvformula]');
		if(fdefine1Field){
			fdefine1Field.setValue(data.fdefine1);
			fhformulaField.fireEvent('blur');
		}
		if(fdefine2Field){
			fdefine2Field.setValue(data.fdefine2);
			fhformulaField.fireEvent('blur');
		}
		if(fdefine3Field){
			fdefine3Field.setValue(data.fdefine3);
			fhformulaField.fireEvent('blur');
		}
		if(fhformulaField){
			fhformulaField.setValue(data.fhformula);
			fhformulaField.fireEvent('blur');
		}
		if(fhformula1Field){
			fhformula1Field.setValue(data.fhformula1);
			fhformulaField.fireEvent('blur');
		}
		if(fvformulaField){
			fvformulaField.setValue(data.fvformula);
			fvformulaField.fireEvent('blur');
		}
		if(win.editstate == 'copy'){
			win.down('[name=fid]').setValue('');
			win.down('[name=fiscommonorder]').setValue('');
			win.down('[name=fiscreate]').setValue('');
			win.down('[name=fstate]').setValue('');
			win.down('[name=fnumber]').setValue('');
			win.down('[name=fcreatetime]').setValue('');
			win.down('[name=fcreatorid]').setValue('');
			Ext.Ajax.request({
				timeout : 60000,
				url : "GetFirstDateofMaterial.do",
				params:{
					fmaterialfid: win.getFieldValue('fmaterialfid'),
					fsupplierid: win.getFieldValue('fsupplierid')
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success && obj.data.length) {
						var farrivedate= new Date(obj.data[0].farrivetime);
						if(Ext.Date.isEqual(farrivedate,new Date("2015-09-03"))){
						var farrivedate= Ext.Date.add(farrivedate, Ext.Date.DAY, 1);
						}
						win.down('datefield[name=farrivetime]').setMinValue(farrivedate);
						win.down('datefield[name=farrivetime]').setValue(farrivedate);
						

					}
				}
			});
		}
		if(data.fboxmodel === "0"){		//箱型为其它时，压线赋值
			win.getField('fhline').setValue(data.fhline);
			win.getField('fvline').setValue(data.fvline);
		}
	},
	setInitData: function(){
		var win = this;
		win.getField('fmateriallength').setValue('');
		win.getField('fmaterialwidth').setValue('');
		win.getField('fboxlength').setValue('');
		win.getField('fboxwidth').setValue('');
		win.getField('fboxheight').setValue('');
		win.getField('famount').setValue('');
		win.getField('famountpiece').setValue('');
		win.getField('fhline').setValue('');
		win.getField('fvline').setValue('');
		localStorage.$singleBoardAddress = win.getFieldValue('faddressid');
		localStorage.$singleBoardSupplier = win.getFieldValue('fsupplierid');
		var materialValue = win.getFieldValue('fmaterialfid');
		if(!localStorage.$BoardOrderedProducts){
			localStorage.$BoardOrderedProducts = materialValue;
		}else{
			orderedProducts = localStorage.$BoardOrderedProducts.split(',');
			if(Ext.Array.contains(orderedProducts,materialValue)){
				Ext.Array.remove(orderedProducts,materialValue);
			}
			orderedProducts.unshift(materialValue);
			localStorage.$BoardOrderedProducts = orderedProducts.join(',');
		}
	},
	getField: function(name){
		return this.down('[name='+name+']');
	},
	getFieldValue: function(name){
		return this.down('[name='+name+']').getValue();
	},
	generateFormula: function(){
		var win = this;
		win.generateHformula();
		win.generateVformula();
		win.setFormulaValue();
	},
	//公式的接舌和系数赋上次保存的值
	setFormulaValue: function(){
		var win = this;
		var boxmodel = parseInt(win.down('combobox[name=fboxmodel]').getValue()),
			layer = this.down('combobox[name=fmaterialfid]').flayer,
		formulaDefaultValue = win.formulaDefaultValue,val,field,defaultValueObj;
		if(!formulaDefaultValue){
			return;
		}
		defaultValueObj = Ext.Array.findBy(formulaDefaultValue,function(item,index){
			return item.fboxmodel === (boxmodel+'') && item.flayer === (layer+'');
		});
		if(defaultValueObj){
			Ext.Object.each(defaultValueObj,function(key,value){
				if(key=='fboxmodel' || key=='flayer'){
					return;
				}
				field = win.down('[name='+key+']');
				if(field && value!==''){
					field.setValue(parseFloat(value));
					field.fireEvent('blur');
				}
			});
		}
	},
	generateHformula: function(){
		var fseries = this.down('combobox[name=fseries]').getValue(),
			boxmodel = parseInt(this.down('combobox[name=fboxmodel]').getValue()),
			hContainer = this.down('fieldcontainer[fieldLabel=横向公式]'),
			materialField = this.down('combobox[name=fmaterialfid]'),
			fhlineField = this.down('textfield[name=fhline]'),
			fstavetypeField = this.down('combobox[name=fstavetype]'),
			fhstaveexpField = this.down('[name=fhstaveexp]'),
			fhlineField = this.down('textfield[name=fhline]'),
			g = this.generateNumberField,defaultValue;
		hContainer.removeAll();
		fhlineField.setValue('');
		fhlineField.setReadOnly(true);
		fhstaveexpField.setValue('');
		if(!fseries){
			return;
		}
		if(!materialField.getValue()){
			return;
		}
		if(fstavetypeField.getValue()=='不压线'){
			return;
		}
		switch(parseInt(materialField.flayer)){
		case 3: defaultValue=3.5;break;
		case 5: defaultValue=4;break;
		case 7: defaultValue=4.5;break;
		}
		if(boxmodel==1){
			switch(fseries){
			case '连做': 
				hContainer.add(g('fhformula',defaultValue,13),{value:'+长+宽+长+(宽-'},g('fhformula1',0,14),{value:')'});
				hContainer.formulaText = 'x_length_width_length_width-y';
				break;
			case '不连做': 
				hContainer.add(g('fhformula',defaultValue,13),{value:'+长+(宽-'},g('fhformula1',0,14),{value:')'});
				hContainer.formulaText = 'x_length_width-y';
				break;
//			case '四片': hContainer.add({value:'长+'},g(),{value:'&nbsp;,&nbsp;宽+'},g());
			}
		}else if(boxmodel==2 || boxmodel==3 || boxmodel==4 || boxmodel==5 || boxmodel==6 || boxmodel==8){
			switch(fseries){
			case '连做': 
				hContainer.add(g('fhformula',defaultValue,13),{value:'+长+宽+长+宽'});
				hContainer.formulaText = 'x_length_width_length_width';
				break;
			case '不连做': 
				hContainer.add(g('fhformula',defaultValue,13),{value:'+长+宽'});
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
			fhlineField.setReadOnly(false);
			return;
		}
		this.generateHline();
	},
	generateVformula: function(){
		var win = this,
			fseries = this.down('combobox[name=fseries]').getValue(),
			boxmodel = parseInt(this.down('combobox[name=fboxmodel]').getValue()),
			vContainer = this.down('fieldcontainer[fieldLabel=纵向公式]'),
			materialField = this.down('combobox[name=fmaterialfid]'),
			fvlineField = this.down('textfield[name=fvline]'),
			fstavetypeField = this.down('combobox[name=fstavetype]'),
			fvstaveexpField = this.down('[name=fvstaveexp]'),
			g = this.generateNumberField,layer,defaultValue;
		vContainer.removeAll();
		fvlineField.setValue('');
		fvlineField.setReadOnly(true);
		fvstaveexpField.setValue('');
		if(!fseries){
			return;
		}
		if(!materialField.getValue()){
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
			layer = parseInt(materialField.flayer);
			defaultValue = (layer==3||layer==5)?0.4:(layer==7?0.6:null);
			vContainer.add({value:'(宽+'},g('fvformula',defaultValue,15),{value:')/2+高+(宽+'},g(null,defaultValue,null,true),{value:')/2'});
			vContainer.formulaText = '((width+x)/2).toFixed(2)_height_((width+x)/2).toFixed(2)';
			break;
		case 2:
			vContainer.add({value:'(宽-'},g('fvformula',0,15),{value:')+高+(宽-'},g(null,0,null,true),{value:')'});
			vContainer.formulaText = 'width-x_height_width-x';
			break;
		case 3:
			defaultValue = this.down('numberfield[name=fboxwidth]').getValue()/2;
			vContainer.add({value:'宽+高+'},g('fvformula',defaultValue,15));
			vContainer.formulaText = 'width_height_x';
			break;
		case 4:
			vContainer.add({value:'高+(宽+'},g('fvformula',0.4,15),{value:')/2'});
			vContainer.formulaText = 'height_((width+x)/2).toFixed(2)';
			break;
		case 5:
			vContainer.add({value:'(宽+'},g('fvformula',0.4,15),{value:')/2+高'});
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
			layer = parseInt(materialField.flayer);
			defaultValue = (layer==3||layer==5)?0.4:(layer==7?0.6:null);
			vContainer.add(g('fdefine1',0,15,true),{value:'+'},g('fdefine2',0,16),{value:'+'},g('fdefine3',0,17),{value:'+高+(宽+'},g('fvformula',defaultValue,18),{value:')/2'});
			vContainer.formulaText = 'y1_y2_y3_height_((width+x)/2).toFixed(2)';
			break;
		case 0:
			fvlineField.setReadOnly(false);
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
					var fboxmodel = me.down('[name=fboxmodel]').getValue();
					val = (fboxmodel==1)? '(w)/2':'w/2';
				}else{ 
					val = val.replace(arr[2],temp+'');
				}
			}
			expArr[index] = '['+val+']';
		});
		return expArr.join('+');
	},
	generateHline: function(){
		var width = this.down('numberfield[name=fboxwidth]').getValue(),
			height = this.down('numberfield[name=fboxheight]').getValue(),
			length = this.down('numberfield[name=fboxlength]').getValue(),
			hformulaField = this.down('numberfield[name=fhformula]'),
			h1formulaField = this.down('numberfield[name=fhformula1]'),
			fhstaveexpField = this.down('[name=fhstaveexp]'),
			hContainer = this.down('fieldcontainer[fieldLabel=横向公式]'),
			formulaText = hContainer.formulaText,
			fhlineField = this.down('textfield[name=fhline]'),x,y,arr,newArr = [];
		if(!width || !height || !length || !hContainer.items.length){
			return;
		}
		x = hformulaField && hformulaField.getValue();
		y = h1formulaField && h1formulaField.getValue();
		arr = formulaText.split('_');
		Ext.each(arr,function(item,index){
			newArr[index] = eval(item).toFixed(2);
		});
		fhlineField.setValue(newArr.join('+'));
		fhstaveexpField.setValue(this.generateStaveexp(arr,{x:x,y:y}));
	},
	generateVline: function(){
		var width = this.down('numberfield[name=fboxwidth]').getValue(),
			height = this.down('numberfield[name=fboxheight]').getValue(),
			length = this.down('numberfield[name=fboxlength]').getValue(),
			vContainer = this.down('fieldcontainer[fieldLabel=纵向公式]'),
			vformulaField = this.down('numberfield[name=fvformula]'),
			fdefine1Field = this.down('[name=fdefine1]'),
			fdefine2Field = this.down('[name=fdefine2]'),
			fdefine3Field = this.down('[name=fdefine3]'),
			fvstaveexpField = this.down('[name=fvstaveexp]'),
			formulaText = vContainer.formulaText,
			fvlineField = this.down('textfield[name=fvline]'),x,arr,y1,y2,y3,newArr=[];
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
		fvlineField.setValue(newArr.join('+'));
		fvstaveexpField.setValue(this.generateStaveexp(arr,{x:x,y1:y1,y2:y2,y3:y3}));
	},
	generateNumberField: function(name,val,enterIndex,readOnly){
		var config = {xtype:'numberfield',width:32,hideTrigger:true,allowBlank:false,decimalPrecision: 1,minValue: 0};
		if(name == 'fhformula'){	//接舌大于0
			delete config.minValue;
			config.validator = function(val){
				if(val>0){
					return true;
				}else{
					return '请填写大于零的值！';
				}
			}
		}
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
					if(win.down('[name=fboxmodel]').getValue()=='8'){
						var fvlineField = win.down('[name=fvline]'),
							fvline = fvlineField.getValue(),arr,fdefine1Field,fdefine1;
						if(fvline){
							arr = fvline.split('+');
							fdefine1Field = parentContainer.down('[name=fdefine1]');
							fdefine1Field.setValue(parseFloat(arr[4])-parseFloat(arr[1])-parseFloat(arr[2]));
							arr[0] = fdefine1Field.getValue();
							fvlineField.setValue(arr.join('+'));
						}
					}
				}
				
			}
		}
		if(name){
			config.name = name;
		}
		if(name=='fdefine1' || name=='fdefine2' || name=='fdefine3'){
			config.decimalPrecision = 2;
		}
		if(val|| val===0){
			config.value = val;
		}
		/*if(enterIndex){
			config.enterIndex = enterIndex;
		}*/
		if(readOnly){
			config.readOnly = readOnly;
		}
		return config;
	},
	onload: function(){
		var win = this;
		win.down('combobox[name=fsupplierid]').focus(false, 600);
		//查最大高度和最大宽度
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
				Ext.Msg.alert('提示','获取最值失败，请稍后再试');
			}
		});
		if(!localStorage.$BoardOrderedProducts){ 			//存一个月内下单的materialid,以逗号分隔，最近下单的排前面
			Ext.Ajax.request({
				url: 'selectOrderedBoardDeliverapply.do',
				success: function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						if(obj.msg){
							localStorage.$BoardOrderedProducts = obj.msg;
						}
					}else{
						Ext.Msg.alert('错误',obj.msg);
						win.close();
					}
				}
			});
		}
	},
	getFormulaDefaultValue: function(){
		var win = this;
		if(win.editstate == 'add'){
			Ext.Ajax.request({
				url: 'selectBoardDeliverapplyFormulaDefaultValue.do',
				success: function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						win.formulaDefaultValue = obj.data;
					}else{
						Ext.Msg.alert('错误',obj.msg);
						win.close();
					}
				},
				failure: function(){
					win.close();
					Ext.Msg.alert('提示','获取最值失败，请稍后再试');
				}
			});
		}
	},
	listeners: {
		show: function(){
			Ext.getCmp(this.id+'.savebutton').hide();
			this.getFormulaDefaultValue();
			Ext.getCmp(this.id+'.closebutton').setText('关    闭');
		}
	},
	custbar:[{
		text: '立即下单',
		height: 30,
		handler: function(){
			var win = this.up('window');
			win.down('textfield[name=fstate]').setValue('0');
			Ext.getCmp(win.id+'.savebutton').handler();
		}
	},{
		text: '暂存订单',
		height: 30,
		handler: function(){
			var win = this.up('window');
			win.down('textfield[name=fstate]').setValue('7');
			Ext.getCmp(win.id+'.savebutton').handler();
		}
	},{
		text: '新增地址',
		height: 30,
		handler: function(){
			var editui = Ext.getCmp("DJ.System.UserAddressEdit") || Ext.create('DJ.System.UserAddressEdit');
			editui.seteditstate("add");
			Ext.getCmp("DJ.System.UserAddressEdit").down('cCombobox[name=fcustomerid]').setReadOnly(true);
		    editui.show();
		}
	},{
		text: '增加常用材料',
		height: 30,
		tooltip: '增加常用材料后，选择材料时将只显示常用材料',
		handler: function(){
			Ext.create('DJ.order.Deliver.CommonMaterialOperation').show();
		}
	}/*,'->',{
		text: '切换下单方式',
		height: 30,
		handler: function(){
			localStorage.$singleBoardOrder = '0';
			var newWin = Ext.create('DJ.order.Deliver.batchBoardDeliverapplyEdit');
			newWin.setparent('DJ.order.Deliver.DeliversBoardList');
			newWin.seteditstate("edit");
			this.up('window').close();
			newWin.show();
		}
	}*/],
	initComponent: function(){
		var win = this,
			labelStore = Ext.create('Ext.data.Store', {
			    fields: ['fid', 'fname'],
			    proxy: {
			    	type: 'ajax',
			    	url: 'getCustomerLabelList.do',
			    	reader: {
			    		type: 'json',
			    		root: 'data'
			    	}
			    }
			});
		var items = [{
			xtype: 'container',
			anchor: '100%',
			layout: 'hbox',
			defaults: {
				style: 'margin-bottom:8px'
			},
			items: [{
				xtype: 'combo',
				name: 'flabel',
				store: labelStore,
				fieldLabel: '客户标签',
				displayField: 'fname',
				valueField: 'fname',
				enterIndex: 0,
				flex: 8,
				hideTrigger: true,
				labelWidth: 65,
				anchor: '100%',
				queryParam: 'labelName',
				queryMode : 'remote',
				minChars: 1,
				doRawQuery : function() {
						if(Ext.isIE){
							this.doQuery(this.getRawValue(), false, true);	
						}else{
							this.doQuery(this.getRawValue().trim(), false, true);	
						}
				},
				listeners: {
//					afterrender:function(com){
//						var win = com.up('window');
//						if(win.editstate=='add'){
//							Ext.Ajax.request({
//								url:'getCustomerByUser.do',
//								success:function(response){
//									var obj = Ext.decode(response.responseText);
//									if(obj.success==true){
//										if(Ext.isEmpty(com.getValue())){
//											com.setValue(obj.data[0].fname);
//										}
//									}
//								}
//							})
//						}
//					},
					focus: function(){
			    		this.store.load();
			    		this.expand();
			    	},
			    	change: function(me,newVal){
			    		if(newVal==null){
			    			this.store.load();
				    		this.expand();
			    		}
			    	}
				}
			},{
				xtype: 'button',
				text: '删除标签',
				flex: 1,
				handler: function(){
					var labelCombo = this.prev(),
						labelName = labelCombo.getValue();
					if(labelName){
						Ext.Ajax.request({
							url: 'delCustomerLabelInfo.do',
							params: {
								labelName: labelName
							},
							success: function(response){
								var obj = Ext.decode(response.responseText);
								if (obj.success){
									if(obj.msg){
										djsuccessmsg(obj.msg);
									}
									labelCombo.setValue('');
								}else{
									Ext.Msg.alert('错误',obj.msg);
								}
							}
						});
					}
				}
			}]
		},{
			xtype: 'container',
			anchor: '100%',
			layout: 'hbox',
			items: [{
				xtype: 'container',
				flex: 1,
				layout: 'anchor',
				defaultType: 'textfield',
				defaults: {
					anchor: '95%',
					labelWidth: 65,
					style: 'margin-bottom:8px'
				},
				items:[{
					xtype : 'combobox',
					fieldLabel: '制造商',
					name: 'fsupplierid',
					itemId: 'supplier',
					valueField : 'fid',
					displayField : 'fname',
					editable:false,
					allowBlank: false,
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
							if(win.editstate!='add' || this.getValue()){
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
					fieldLabel: '箱型',
					xtype: 'slcombo',
					name: 'fboxmodel',
					forceSelection: true,
					value: '1',
					enterIndex: 3,
					// {value:'0',text:'其他'}
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
							var fseriesField = win.down('[name=fseries]');
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
					fieldLabel: '下料规格<br/>（厘米）',
					xtype: 'fieldcontainer',
					itemId: 'materialspec',
					layout: 'hbox',
					disabled: true,
					items:[{
						xtype: 'numberfield',
						flex: 3,
						enterIndex: 5,
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
							var extrernes = this.up('window').extrernes,
								val = this.getValue();
							if(val === null){
								return;
							}
							if(extrernes === false && !win.isOpen){
								var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
								mWin.parentWin = win;
								mWin.show();
								win.isOpen = true;
							}else if(extrernes){
								var maxLength = parseFloat(extrernes.fmaxlength),
									minLength = parseFloat(extrernes.fminlength);
								if(val>maxLength || val<minLength){
									Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
										if(action == 'yes' && !win.isOpen){
											var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
											mWin.parentWin = win;
											mWin.show();
											win.isOpen = true;
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
						flex: 3,
						enterIndex: 6,
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
							var extrernes = this.up('window').extrernes,
								val = this.getValue();
							if(val === null){
								return;
							}
							if(extrernes === false && !win.isOpen){
								var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
								mWin.parentWin = win;
								mWin.show();
								win.isOpen = true;
							}else if(extrernes){
								var maxWidth = parseFloat(extrernes.fmaxwidth),
									minWidth = parseFloat(extrernes.fminwidth);
								if(val>maxWidth || val<minWidth){
									Ext.Msg.confirm('提示','落料规格不符合最值，是否更改最值设置',function(action){
										if(action == 'yes' && !win.isOpen){
											var mWin = Ext.create('DJ.order.Deliver.MaterialLimitWin');
											mWin.parentWin = win;
											mWin.show();
											win.isOpen = true;
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
					xtype: 'slcombo',
					enterIndex: 10,
					editable: false,
					forceSelection: true,
					data:[{value:'连做',text:'连做'},
						  {value:'不连做',text:'不连做'}/*,
						  {value:'四片',text:'四片'}*/],
					listeners: {
						change: function(me,newValue,oldValue){
							win.generateFormula();
							var pieceAmountField = win.down('numberfield[name=famountpiece]'),
								amount = win.down('numberfield[name=famount]').getValue(),
								scale = 0;
							if(!amount){
								return;
							}
							switch(newValue){
							case '连做': scale=1;break;
							case '不连做': scale=2;break;
//							case '四片': scale=4;break;
							}
							pieceAmountField.setValue(amount * scale);
						},
						afterrender: function(){
							var me = this,
								state = me.up("window").editstate;
							if(state=="add"){
								me.setValue('连做');
							}
						}
					}
				},{
					xtype: 'fieldcontainer',
					fieldLabel: '横向公式',
					defaultType: 'displayfield',
//					name: 'fhformula',
					layout: 'hbox',
					items: []
				},{
					xtype: 'textfield',
					fieldLabel: '横向压线',
					name: 'fhline',
//					enterIndex: 19,
					readOnly: true,
					listeners: {
						change: function(me,value){
							if(value&&this.readOnly){
								win.down('numberfield[name=fmateriallength]').setValue(eval(value));
							}
						},
						blur: function(){
							var value = this.getValue();
							if(value&&!this.readOnly){
								win.down('numberfield[name=fmateriallength]').setValue(eval(value));
							}
						}
					},
//					regex : /^((\d+(\.?\d*)\+)+\d+(\.?\d*))?$/,
					regex: /^(\d+\.?\d*(\+\d+\.?\d*)*)?$/,
					regexText: '格式不正确'
				},{
					fieldLabel: '配送时间',
					xtype: 'datefield',
					name: 'farrivetime',
					allowBlank: false,
//					enterIndex: 21,
					format: 'Y-m-d',
					minValue: new Date(),
					maxValue:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.MONTH,1)
				}]
			},{
				xtype: 'container',
				flex: 1,
				layout: 'anchor',
				defaultType: 'textfield',
				defaults: {
					anchor: '100%',
					labelWidth: 65,
					style: 'margin-bottom:8px'
				},
				items: [{
					xtype:'cCombobox',
					fieldLabel: '材料',
					name: 'fmaterialfid',
					displayField:'fothername',
					valueField:'fid',
					enterIndex: 2,
					allowBlank:false,
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
						var fseriesField = win.down('combobox[name=fseries]'),
//							famountpieceField = win.down('numberfield[name=famountpiece]'),
//							famountField = win.down('numberfield[name=famount]'),
							fstavetypeField = win.down('textfield[name=fstavetype]'),
							record = records[0]==undefined?records:records[0],
							oldFlayer = this.flayer;
						win.materialRecord = record;
						this.flayer = record.get('flayer');
//						famountpieceField.setValue('');
//						famountField.setValue('');
//						fseriesField.setValue('');
						if(oldFlayer == '1' || this.flayer ||  (oldFlayer%2 != this.flayer%2)){
							if(this.flayer== '2' || this.flayer == '4' || this.flayer == '6' ||  this.flayer == '1'){
								fstavetypeField.setValue('不压线');
								fstavetypeField.setReadOnly(true);
							}else{
								fstavetypeField.setValue('普通压线');
								fstavetypeField.setReadOnly(false);
							}
						}
						win.generateFormula();
						Ext.Ajax.request({
							timeout : 60000,
							url : "GetFirstDateofMaterial.do",
							params:{
								fmaterialfid: record.get('fid'),
								fsupplierid: record.get('fsupplierid')
							},
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success && obj.data.length) {
									var farrivedate= new Date(obj.data[0].farrivetime);
									if(Ext.Date.isEqual(farrivedate,new Date("2015-09-03"))){
									var farrivedate= Ext.Date.add(farrivedate, Ext.Date.DAY, 1);
									}
									win.down('datefield[name=farrivetime]').setMinValue(farrivedate);
									win.down('datefield[name=farrivetime]').setValue(farrivedate);

								
								}
							}
						});
					},
					MyConfig:{
						width:400,
						height:200,
						hiddenToolbar:true,
						url : 'getCardboardListBySupplier.do',
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
								name:'fothername',
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
								}
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
							}
							/*,{
								text:'状态',
								dataIndex:'feffect',
								renderer:function(value){
									return value == '1' ? '是' : '否';
								}
							},{
								text:'创建时间',
								dataIndex:'fcreatetime'
							}
							*/]
					}
				},{
					fieldLabel: '压线方式',
					name: 'fstavetype',
					xtype: 'slcombo',
					forceSelection: true,
					enterIndex: 4,
					allowBlank: false,
					data:[
					      {value:'不压线',text:'不压线'},
					      {value:'普通压线',text:'普通压线'},
					      {value:'内压线',text:'内压线'},
					      {value:'外压线',text:'外压线'},
					      {value:'横压',text:'横压'}
					],
					listeners: {
						change: function(field,newValue,oldValue){
							var vlineField = win.down('textfield[name=fvline]'),
								hlineField = win.down('textfield[name=fhline]'),
								fboxlengthField = win.down('textfield[name=fboxlength]'),
								fboxwidthField = win.down('textfield[name=fboxwidth]'),
								fboxheightField = win.down('textfield[name=fboxheight]'),
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
//								vlineField.setReadOnly(true);
//								hlineField.setReadOnly(true);
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
					fieldLabel: '纸箱规格<br/>（厘米）',
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaultType: 'numberfield',
					itemId: 'boxspec',
					defaults: {
						listeners: {
							blur: function(){
								var numberFields = this.up('fieldcontainer').query('numberfield'),
									staveTypeField = win.down('combobox[name=fstavetype]');
								if(numberFields[0].getValue()&&numberFields[1].getValue()&&numberFields[2].getValue()&&!staveTypeField.getValue()){
									staveTypeField.setValue('普通压线');
								}
							}
						}
					},
					items: [{
						name: 'fboxlength',
						flex: 1,
						enterIndex: 7,
						margins: '0 5 0 0',
						emptyText: '长',
						decimalPrecision: 1,
						minValue: 0,
						/*validator: function(val){
							if(!val){
								return true;
							}
							return val>0?true:'请填写大于0的值';
						},*/
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
						flex: 1,
						enterIndex: 8,
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
						flex: 1,
						enterIndex: 9,
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
					fieldLabel: '配送数量',
					xtype: 'fieldcontainer',
					layout: 'hbox',
					allowBlank: false,
					items:[{
						xtype: 'numberfield',
						flex: 3,
						name: 'famount',
						enterIndex: 11,
						margins: '0 15 0 0',
						emptyText: '只',
						allowBlank: false,
						allowDecimals: false,
						minValue: 1,
						listeners: {
							blur: function(){
								var val = this.getValue(),
									pieceField = this.up('fieldcontainer').down('numberfield[name=famountpiece]'),
//									pieceVal = pieceField.getValue(),
									series = win.down('combobox[name=fseries]').getValue(),scale;
								if(!val || !series){
									return;
								}
								switch(series){
								case '连做': scale=1;break;
								case '不连做': scale=2;break;
								case '四片': scale=4;break;
								}
								pieceField.setValue(val * scale);
							}
						}
					},{
						xtype: 'displayfield',
						flex: 1
					},{
						xtype: 'numberfield',
						flex: 3,
						name: 'famountpiece',
						enterIndex: 12,
						emptyText: '片',
						allowBlank: false,
						readOnly: true,
						allowDecimals: false,
						minValue: 1,
						listeners: {
							blur: function(){
								var pieceVal = this.getValue(),
									amountField = this.up('fieldcontainer').down('numberfield[name=famount]'),
									val = amountField.getValue(),
									seriesField = win.down('combobox[name=fseries]');
								if(!pieceVal){
									return;
								}
								if(val){
									switch(Math.round(pieceVal/val)){
									case 1: seriesField.setValue('连做');break;
									case 2: seriesField.setValue('不连做');break;
									case 4: seriesField.setValue('四片');break;
									default: seriesField.setValue('');
									}
								}
							}
						}
					}]
				},{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					fieldLabel: '纵向公式',
					defaultType: 'displayfield',
//					name: 'fvformula',
					items: []
				},{
					fieldLabel: '纵向压线',
					name: 'fvline',
//					enterIndex: 20,
					readOnly: true,
					listeners: {
						change: function(me,value){
							if(value&&this.readOnly){
								win.down('numberfield[name=fmaterialwidth]').setValue(eval(value));
							}
						},
						blur: function(){
							var value = this.getValue();
							if(value&&!this.readOnly){
								win.down('numberfield[name=fmaterialwidth]').setValue(eval(value));
							}
						}
					},
//					regex : /^((\d+(\.?\d*)\+)+\d+(\.?\d*))?$/,
					regex: /^(\d+\.?\d*(\+\d+\.?\d*)*)?$/,
					regexText: '格式不正确'
				},{
					fieldLabel:'配送地址',
					xtype:'cCombobox',
					name: 'faddressid',
					enterIndex: 22,
					displayField:'fname',
					valueField:'fid',
					allowBlank:false,
					blankText:'请选择送货地址',
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
						afterrender: function(){
							var me = this;
							if(win.editstate!='add' || me.getValue()){
								return;
							}
							Ext.Ajax.request({
								timeout : 60000,
								url : "getUserDefaultAddress.do",
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										var config;
										if(obj.data &&　obj.data.length==1){
											var record = obj.data[0];
											config = {
												fid: record['fid'],
												fname: record['fname']
											};
											me.setmyvalue(config);
										}else if(obj.data && obj.data.length>1 && localStorage.$singleBoardAddress){
											var record = Ext.Array.findBy(obj.data,function(item,index){
												return item['fid']== localStorage.$singleBoardAddress;
											});
											if(record){
												config = {
													fid: record['fid'],
													fname: record['fname']
												}
												me.setmyvalue(config);
											}
										}
									}
								}
							});
						}
					},
					MyConfig:{
						width:650,
						height:200,
						url : 'getUserToCustAddress.do',
						hiddenToolbar : true,
						fields:[{
									name : 'fid'
								},{
									name : 'fname',
									myfilterfield : 'ad.fname',
									myfiltername : '名称',
									myfilterable : true
								},{
									name : 'flinkman',
									myfilterfield : 'ad.flinkman',
									myfiltername : '联系人',
									myfilterable : true
								},{
									name : 'fphone',
									myfilterfield : 'ad.fphone', 
									myfiltername : '联系电话',
									myfilterable : true
								}],
						columns:[{
									header : '地址名称',
									dataIndex : 'fname',
									flex: 5,
									sortable : true
								},{
									header : '联系人',
									dataIndex : 'flinkman',
									flex: 1,
									sortable : true
								},{
									header : '联系电话',
									dataIndex : 'fphone',
									flex: 1,
									sortable : true
						}]
					}
				}]
			}]
		},{
			xtype: 'container',
			layout: 'hbox',
			anchor: '100%',
			items: [{
				xtype: 'textfield',
				name: 'fdescription',
				enterIndex: 23,
				fieldLabel: '特殊要求',
				emptyText: '本备注不能包含生产工序信息',
				labelWidth: 65,
				flex:8,
				listeners: {
					afterrender: function(){
						var me = this;
						var state = me.up("window").editstate;//edit是修改状态,add新增状态
						if(state=="add"){
							Ext.Ajax.request({
								timeout : 60000,
								url : "getCustomerDescription.do",
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if(obj.success){
										if(obj.data.length==1){
											var record = obj.data[0].fdescription;
											me.setValue(record);
											win.fdescription=record;
										}
									}
								}
							});
						}
					}
				}
			},{
				xtype: 'button',
				text: '设为默认',
				flex: 1,
				handler: function(){
					var val = Ext.String.trim(this.prev().getValue());
					Ext.Ajax.request({
						url: 'setCustomerDescription.do',
						params: {
							description: val
						},
						success: function(res){
							var obj = Ext.decode(res.responseText);
							if(obj.success){
								djsuccessmsg(obj.msg);
							}else{
								Ext.Msg.alert('错误',obj.msg);
							}
						},
						failure: function(){
							Ext.Msg.alert('错误','操作失败，请刷新页面重试！');
						}
					})
				}
			}]
		},{
			xtype: 'textfield',
			name: 'fid',
			hidden: true
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
			xtype: 'textfield',
			name: 'fstate',
			value: '0',
			hidden: true
		},{
			xtype: 'textfield',
			name: 'fiscreate',
			hidden: true
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
			anchor: '100%',
			name: 'fiscommonorder',
			hidden: true
		}];
		win.specialKey = specialKey;
		//添加回车跳到下个输入框事件
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
		Ext.apply(this,{items:items});
		this.callParent(arguments);
	}
});
function specialKey(field,e){
	var me = this;
	if(e.getKey() == Ext.EventObject.ENTER){
		var enterIndex = this.enterIndex,
		win = this.up('window');
		if(enterIndex!=23){
			var nextField = win.down('textfield[enterIndex='+(++enterIndex)+']');
			while(!nextField || nextField.readOnly || (nextField.up('fieldcontainer') && nextField.up('fieldcontainer').disabled) || (enterIndex==22&&nextField.getValue())){
				nextField = win.down('textfield[enterIndex='+(++enterIndex)+']');
				if(enterIndex>30){ //防无限循环
					break;
				}
			}
			if(nextField.xtype == 'numberfield' || nextField.xtype == 'textfield'){
				nextField.focus(true);
			}else{
				nextField.focus();
			}
			this.fireEvent('blur',this);
		}else{
			win.down('button[text=立即下单]').handler();
		}
	}else if((me.name=='fmaterialfid' || me.name=='faddressid') && e.getKey()==Ext.EventObject.DOWN){
		var picker = me.getPicker();
		picker.getSelectionModel().select(0);
	}
};