var CONTROL_HALT = false;
$.fn.value = function(val){
	var obj = this.data('djselect');
	if(obj){
		return obj.value(val);
	}
};
$(function(){
	$('#fhformula,#fvformula').blur(function(){
		if(!/^(\d+\.?\d*(\+\d+\.?\d*)*)?$/.test(this.value)){
			this.value = '';
		}
	});
	initSelect();
	initialInputText();
	initControl();
	setData();
	if(orderState == 'edit'){
		loadImages();
	}
	enterKeyFocus();
	$('#fileUL').load(function(e){
		var res = $(e.target).contents()[0].body.innerHTML;
		if(res && res.length<50){
			var path = ROOT_PATH+'/productfile/getFileSource.net?fid='+res;
			$.addFdImg(path,path,path,res);
		}else{
			top.layer.msg('图片上传失败！');
		}
	});
	$('#deleteImg').click(function(e){
		var $li = $('#thumblist .tb-selected');
		if($li.length>0){
			var index = top.layer.confirm('确认删除此图片吗？',function(yes){
				if(yes){
					top.layer.close(index);
					$.ajax({
						url: ROOT_PATH+'/productfile/deleteImg.net',
						data: {fid: $li.data('fileid')},
						success: function(res){
							if(res == 'success'){
								top.layer.msg('操作成功！');
								$.delFdImg($li);
							}else{
								top.layer.msg('图片删除失败！');
							}
						}
					});
				}
			});
		}else{
			top.layer.msg('您尚未上传图片！');
		}
	});
	// 保存
	$('#_submit').click(function(){
		if(validateData()){
			var formId = $('.tab_title .choice').data('form'),
			data = $('#product_info,#'+formId).serialize();
			$.ajax({
				url: ROOT_PATH+'/productdef/saveProduct.net',
				data: data,
				type: 'post',
				success: function(res){
					if(res === 'success'){
						top.myProduct_list();
					}else{
						top.layer.msg(res);
					}
				}
			});
		}
	});
	//放大镜
	doFANGDA();
	// 查看操作
	handleView();
	getHtmlLoadingBeforeHeight();
	getHtmlLoadingAfterHeight();
});
function handleView(){
	if(viewProduct){
		CONTROL_HALT = true;
		$('input,textarea').prop('readonly',true);
		$('#_submit').css('display','none');
		$('#uploadImg').css('display','none');
		$('#deleteImg').css('display','none');
		$('.select_wrap').each(function(index,item){
			$(item).data('djselect').setEnable(false);
		});
	}
}

//下拉列表
function initSelect(){
	djSelect('fsupplierid',{
		url: ROOT_PATH+'/board/getSupplierAppList.net',//制造商
		forceSelection: true,
		firstLoad: orderState != 'add',
		afterSelect: function(data){
			var selectObj = $('#fmaterialfid').data('djselect');
			selectObj.load({fsupplierid: data.val});
			if(this.firstLoad){
				selectObj.value(orderInfo.fmaterialfid);
				setData();
				this.firstLoad = false;
			}
		}
	});
	djSelect('fmaterialfid',{
		url: ROOT_PATH+'/productdef/loadMaterial.net',//材料
		forceSelection: true,
		transformData: function(res){
			return $.map(res.list,function(item,index){
				return {text: item.fqueryname,val: item.fid,flayer: item.flayer};
			});
		},
		afterSelect: function(option){
			$('#fqueryname').val(option.text);
		}
	});
	djSelect('fboxmodel',{
		forceSelection:true
	});
	djSelect('fstavetype',{
		forceSelection:true
	});
	djSelect('fseries',{
		forceSelection:true
	});
}
//选项卡
function checkTab(id,t){
	$(t).addClass("choice").siblings().removeClass("choice");
	$("#form_"+id).show().siblings().hide();
}
//加载图片
function loadImages(){
	$.ajax({
		url: ROOT_PATH+'/productfile/loadByParentId.net',
		data: {id: $('#fcustpdtid').val()},
		dataType: 'json',
		success: function(res){
			$.each(res.data,function(index,item){
				var path = ROOT_PATH+'/productfile/getFileSource.net?fid='+item.fid;
				$.addFdImg(path,path,path,item.fid);
			});
		}
	});
}
function setData(){
	if(orderState == 'edit'){
		var boardForm = 'form_pagebox';
		if(orderInfo.fisboardcard == '0'){
			checkTab('paddingbox','#paddingboxTab');
			boardForm = 'form_paddingbox';
		}
		var inputs = $('#product_info,#'+ boardForm).find('input[name],textarea[name]');
		inputs.each(function(index,elem){
			var val;
			if(elem.name == 'fisboardcard'){
				return;
			}
			if(elem.name == 'fmaterialfid'){
				$(elem).data('djselect').setValue({val: orderInfo.fmaterialfid_fid,text: orderInfo.fmaterialfid_fqueryname});
				return;
			}
			if(elem.name in orderInfo){
				val = orderInfo[elem.name];
				if($(elem).hasClass('select_val')){
					$(elem).data('djselect').value(val);
				}else{
					elem.value = val;
				}
			}
		});
		$('#fvformula1').val($('#fvformula0').val());
		$('#fseries').trigger('change');
		$('#fhformula').trigger('change');
	}
}

function validateData(){
	var fmateriallength = val('fmateriallength'),
	fmaterialwidth = val('fmaterialwidth');
	if(fmateriallength && fmaterialwidth){
		if(!materialLimit){
			layer.open({
				title: false,
				closeBtn:0,
				type: 2,
				anim:true,
				area: ['742px', '200px'],
				content: ROOT_PATH+"/productdef/CreateCustLimit.net?id="+$('#fcustomerid').val()
			});
			return;
		}else if(!(fmateriallength<=materialLimit.fmaxlength && fmateriallength>=materialLimit.fminlength 
				&& fmaterialwidth>=materialLimit.fminwidth && fmaterialwidth<=materialLimit.fmaxwidth)){
			var index = layer.confirm('落料规格不符合最值，是否更改最值设置',function(yes){
				if(yes){
					layer.close(index);
					layer.open({
						title: false,
						closeBtn:0,
						type: 2,
						anim:true,
						area: ['742px', '200px'],
						content: ROOT_PATH+"/pages/board/MaterialLimitWin.jsp"
					});
				}
			});
			return;
		}
	}
	return true;
}
function val(id){
	return parseInt($('#'+id).val());
}
function error(msg){
	layer.msg(msg);
}
function initControl(){
	var relations = {};
	function doControl(relation){
		var me = $(this),
		newRelation = [];
		$.each(relation,function(_,index){
			var action = actions[index];
			var	args = $.map((action.from+' '+action.to).split(/\s+/),function(id){
				return $('#'+id);
			});
			if(action.callback.apply(me,args)===true){
				newRelation = newRelation.concat(action.to.split(' '));
			}
		});
		$.each($.unique(newRelation).reverse(),function(_,id){
			if(relations[id]){
				doControl.call($('#'+id),relations[id]);
			}
		});
	}
	function control(actions){
		$.each(actions,function(index,action){
			$.each(action.from.split(' '),function(_,id){
				relations[id] = (relations[id] || []);
				relations[id].push(index);
			});
		});
		$.each(relations,function(id,relation){
			var rselect = /fsupplierid|fmaterialfid|fboxmodel|fstavetype|fseries/,
			event = rselect.test(id)? 'selected' : 'change';
			$('#'+id).on(event,function(){
				if(CONTROL_HALT){
					return;
				}
				CONTROL_HALT = true;
				doControl.call(this,relation);
				CONTROL_HALT = false;
			});
		});
	}
	//actions = [{from:'fboxmodel',to:'farrivetime',ev:'change',callback:fn}]
	//relations = {fboxmodel:[1,2],farrivetime:[3,4]}
	var actions = [{
		from: 'fboxmodel fstavetype',to: 'fseries',callback: function(fboxmodel,fstavetype,fseries){
			var state = !fseries.data('djselect').getEnable();
			var	newState = (fboxmodel.value()==7 || fstavetype.value()=='不压线')?true:false;
			if(state !== newState){
				if(newState){
					fseries.value('连做');
				}
				fseries.data('djselect').setEnable(!newState);
				return true;
			}
		}
	},{
		from: 'fsupplierid', to: 'fmaterialfid',callback: function(fsupplierid,fmaterialfid){
			$('#fmaterialfid').value('');
			return true;
		}
	},{
		from: 'fmaterialfid',to: 'fstavetype',callback: function(fmaterialfid,fstavetype){
			var state = !$('#fstavetype').data('djselect').getEnable();
			var	model = fmaterialfid.data('model'),
			newState,flayer;
			if(model){
				flayer = model.flayer;
			}else{	//未选择制造商
				return;		
			}
			if(flayer==1 || flayer==2 || flayer==4 || flayer==6){
				newState = true;
			}else{
				newState = false;
			}
			if(state !== newState){
				fstavetype.value(newState?'不压线': '普通压线');
				fstavetype.data('djselect').setEnable(!newState);
				return true;
			}
		}
	},{
		from: 'fstavetype',to: 'materialspec boxspec',callback: function(fstavetype,materialspec,boxspec){
			var state = boxspec.hasClass('disable'),
			newState = (fstavetype.value()=='不压线')?true:false;
			if(state != newState){
				boxspec.toggleClass('disable',newState);
				materialspec.toggleClass('disable',!newState);
				$('#fmateriallength').prop('readonly',!newState).prop('tabIndex',newState?0:-1);
				$('#fmaterialwidth').prop('readonly',!newState).prop('tabIndex',newState?0:-1);
				if(newState){
					boxspec.find('input').val('');
				}
				return true;
			}
		}
	},{
		from: 'fmaterialfid fboxmodel fstavetype fseries',to: 'hgongsi vgongsi',callback:function(fmaterialfid,fboxmodel,fstavetype,fseries,hgongsi,vgongsi){
			if(!fmaterialfid.value()){
				return;
			}
			return generateFormula.apply(this,arguments);
		}
	},{
		from: 'fboxmodel fstavetype',to: 'fhformula fvformula',callback: function(fboxmodel,fstavetype,fhformula,fvformula){
			var state = fhformula.prop('readonly'),
			newState = (fstavetype.value()=='不压线' || fboxmodel.value()!=0) ? true: false;
			if(state != newState){
				fhformula.prop('readonly',newState);
				fvformula.prop('readonly',newState);
				return true;
			}
		}
	},{
		from: 'hgongsi fboxlength fboxwidth fboxheight fhformula0 fhformula1',to: 'fhformula',callback: function(hgongsi,fboxlength,fboxwidth,fboxheight,fhformula0,fhformula1,fhformula){
			var state = ! fhformula.val(),
			newState = ! hgongsi.data('token');	
			if(!fboxlength.val() || !fboxwidth.val() || !fboxheight.val()){
				return;
			}
			if(newState && !state){
				generateHline(false);
			}else if(newState){
				return;
			}else{
				generateHline.apply(this,arguments);
			}
			return true;
		}
	},{
		from: 'vgongsi fboxlength fboxwidth fboxheight fvformula0 fdefine2 fdefine3',to: 'fvformula',callback: function(vgongsi,fboxlength,fboxwidth,fboxheight,fvformula0,fdefine2,fdefine3,fvformula){
			var state = ! fvformula.val(),
			newState = ! vgongsi.data('token');
			if(!fboxlength.val() || !fboxwidth.val() || !fboxheight.val()){
				return;
			}
			if(newState && !state){
				generateVline(false);
			}else if(newState){
				return;
			}else{
				generateVline.apply(this,arguments);
			}
			return true;
		}
	},{
		from: 'fhformula fvformula',to: 'materialspec fmateriallength fmaterialwidth',callback: function(fhformula,fvformula,materialspec,fmateriallength,fmaterialwidth){
			var rline = /^(\d+\.?\d*(\+\d+\.?\d*)*)$/;
			fhformula = fhformula.val();
			fvformula = fvformula.val();
			fmateriallength.val(rline.test(fhformula)?parseFloat((eval(fhformula)).toFixed(1)):'');
			fmaterialwidth.val(rline.test(fvformula)?parseFloat((eval(fvformula)).toFixed(1)):'');
			if(fmateriallength.val() || fmaterialwidth.val()){
				materialspec.removeClass('disable');
			}else{
				if($('#fstavetype').value()!='不压线'){
					materialspec.addClass('disable');
				}
			}
			return true;
		}
	},{
		from: 'fdefine2 fdefine3 fboxwidth fvformula0',to: 'fdefine1',callback: function(fdefine2,fdefine3,fboxwidth,fvformula0,fdefine1){
			var value;
			if(fdefine2.length>0){
				value = (parseFloat(fboxwidth.val() || 0)+parseFloat(fvformula0.val() || 0))/2 - fdefine2.val() - fdefine3.val();
				fdefine1.val(value.toFixed(2));
				return true;
			}
		}
	},{
		from: 'fvformula0',to: 'fvformula1',callback: function(fvformula0,fvformula1){
			fvformula1.val(fvformula0.val());
			return true;
		}
	}];

	var formulaObj = {
			221: ['fhformula0','+长+宽+长+(宽-','fhformula1',')'],		/*横向连做普通箱型*/
			211: ['fhformula0','+长+(宽-','fhformula1',')'],				/*横向不连做普通箱型*/
			222: ['fhformula0','+长+宽+长+宽'],							/*横向连做剩余箱型*/
			212: ['fhformula0','+长+宽'],									/*横向不连做剩余箱型*/
			227: ['高+长+高'],											/*横向连做天地盖箱型*/
			/*纵向*/
			101: ['(宽+','fvformula0',')/2+高+(宽+','fvformula1',')/2'],
			102: ['(宽-','fvformula0',')+高+(宽-','fvformula1',')'],
			103: ['宽+高+','fvformula0'],
			104: ['高+(宽+','fvformula0',')/2'],
			105: ['(宽+','fvformula0',')/2+高'],
			106: ['高'],
			107: ['高+宽+高'],
			108: ['fdefine1','+','fdefine2','+','fdefine3','+高+(宽+','fvformula0',')/2']
	};
	var lineObj = {
			221: 'x_length_width_length_width-y',
			211: 'x_length_width-y',
			222: 'x_length_width_length_width',
			212: 'x_length_width',
			227: 'height_length_height',
			101: '((width+x)/2).toFixed(2)_height_((width+x)/2).toFixed(2)',
			102: 'width-x_height_width-x',
			103: 'width_height_x',
			104: 'height_((width+x)/2).toFixed(2)',
			105: '((width+x)/2).toFixed(2)_height',
			106: 'height',
			107: 'height_width_height',
			108: 'y1_y2_y3_height_((width+x)/2).toFixed(2)'
	};
	function generateHline(gongsi,fboxlength,fboxwidth,fboxheight,fhformula0,fhformula1,fhformula){
		if(gongsi!==false){
			var length = parseFloat(fboxlength.val()) || 0,
			width = parseFloat(fboxwidth.val()) || 0,
			height = parseFloat(fboxheight.val()) || 0,
			x = parseFloat(fhformula0.val()) || 0,
			y = parseFloat(fhformula1.val()) || 0,
			token = gongsi.data('token');
			fhformula.val($.map(lineObj[token].split('_'),function(item,index){
				return eval(item);
			}).join('+'));
		}else{
			$('#fhformula').val('');
		}
	}
	function generateVline(gongsi,fboxlength,fboxwidth,fboxheight,fvformula0,fdefine2,fdefine3,fvformula){
		if(gongsi!==false){
			var length = parseFloat(fboxlength.val()) || 0,
			width = parseFloat(fboxwidth.val()) || 0,
			height = parseFloat(fboxheight.val()) || 0,
			x = parseFloat(fvformula0.val()) || 0,
			y2 = parseFloat(fdefine2.val()) || 0,
			y3 = parseFloat(fdefine3.val()) || 0,
			y1 = (width+x)/2 - y2 - y3,
			token = gongsi.data('token');
			fvformula.val($.map(lineObj[token].split('_'),function(item,index){
				return eval(item);
			}).join('+'));
		}else{
			$('#fvformula').val('');
		}
	}
	function generateFormula(fmaterialfid,fboxmodel,fstavetype,fseries,hgongsi,vgongsi){
		var htoken,vtoken;
		fboxmodel = parseInt(fboxmodel.value());
		fseries = fseries.value();
		fstavetype = fstavetype.value();
		htoken = getToken(hgongsi,fseries,fboxmodel,fstavetype);
		vtoken = getToken(vgongsi,fseries,fboxmodel,fstavetype);
		if(htoken==hgongsi.data('token') && vtoken==vgongsi.data('token')){
			return false;
		}
		if(htoken==0){
			hgongsi.html('');
			vgongsi.html('');
			hgongsi.data('token',0);
			vgongsi.data('token',0);
		}else{
			if(htoken!=hgongsi.data('token')){
				hgongsi.html(getFormulaString(formulaObj[htoken]));
				hgongsi.data('token',htoken);
				initialHFormulaInputText();
				addEvent(hgongsi);
			}
			if(vtoken!=vgongsi.data('token')){
				vgongsi.html(getFormulaString(formulaObj[vtoken]));
				vgongsi.data('token',vtoken);
				initialVFormulaInputText();
				addEvent(vgongsi);
			}
			addGongsiValue(fboxmodel);
			enterKeyFocus();
		}
		return true;
	}
	function addGongsiValue(boxmodel){
		var hformulaValue = 0,
		hformula1Value = 0,
		vformulaValue = 0,
		unset = true,
		field,fdefine1,fdefine2,fdefine3;
		if(orderState== 'add'){
			layer = parseInt($('#fmaterialfid').data('model').flayer);
			switch(layer){
			case 3: hformulaValue=3.5;break;
			case 5: hformulaValue=4;break;
			case 7: hformulaValue=4.5;break;
			}
			switch(boxmodel){
			case 1: ;
			case 8: vformulaValue = (layer==3||layer==5)?0.4:(layer==7?0.6:null);break;
			case 3: vformulaValue = ($('#fboxwidth').val()/2).toFixed(1);break;
			case 4: ;
			case 5: vformulaValue = 0.4;break;
			default: vformulaValue = 0;
			}
			hformula1Value = hformulaValue;
			fdefine1 = fdefine2 = fdefine3 = 0;
		}else if(orderState == 'edit'){
			hformulaValue = orderInfo.fhformula0;
			hformula1Value = orderInfo.fhformula1;
			vformulaValue = orderInfo.fvformula0;
			fdefine1 = orderInfo.fdefine1;
			fdefine2 = orderInfo.fdefine2;
			fdefine3 = orderInfo.fdefine3;
		}
		if(unset){
			setValue('fhformula0',hformulaValue);
			setValue('fhformula1',hformula1Value);
			setValue('fvformula0',vformulaValue);
			setValue('fdefine1',fdefine1);
			setValue('fdefine2',fdefine2);
			setValue('fdefine3',fdefine3);
		}
		setValue('fvformula1',$('#fvformula0').val());
		function setValue(id,value){
			var field = $('#'+id);
			if(field.length){
				field.val(value);
			}
		}
	}
	function addEvent(gongsi){
		var rinput = /^(fhformula0|fhformula1|fvformula0|fdefine2|fdefine3)$/,
		$input = gongsi.find('input');
		$input.each(function(index,elem){
			if(rinput.test(elem.id)){
				$(elem).on('change',function(){
					if(CONTROL_HALT){
						return;
					}
					CONTROL_HALT = true;
					doControl.call(this,relations[elem.id]);
					CONTROL_HALT = false;
				});
			}
		});
	}
	function getFormulaString(formulas){
		var ret = [],
		rinput1 = /^(fhformula0|fhformula1|fvformula0|fdefine2|fdefine3)$/,
		rinput2 = /^(fvformula1|fdefine1)$/;
		$.each(formulas,function(index,text){
			if(rinput1.test(text)){
				ret.push('<input id="'+text+'" name="'+text+'" />');
			}else if(rinput2.test(text)){
				ret.push('<input id="'+text+'" readonly="readonly"/>');
			}else{
				ret.push('<span>'+text+'</span>');
			}
		});
		return ret.join('');
	}
	function getToken(gongsi,fseries,fboxmodel,fstavetype){
		if(fstavetype=='不压线' || fboxmodel=='0'){
			return 0;
		}
		var x = gongsi.selector=='#hgongsi'? 2 : 1,
				y = (x-1)* (fseries=='连做'? 2 : 1),
				z = (x-1)? (fboxmodel==1 || fboxmodel==7) ? fboxmodel : Math.min(2,fboxmodel)
						: fboxmodel;
				return x*100+y*10+z;
	}
	control(actions);
}

function enterKeyFocus(){
	var ids = ['fsupplierid','fmaterialfid','fboxmodel','fmaterialinfo','fmateriallength','fmaterialwidth','fstavetype','fseries','fboxlength','fboxwidth',
	           'fboxheight','fhformula0','fhformula1','fdefine2','fdefine3','fvformula0','fhformula','fvformula','fcbnumber','fybnumber','fprintcolor','fpackway','fworkproc',
	           'fboxlength1','fboxwidth1','fboxheight1','pad0','pad1','pad2','pad3','pad4','pad5','pad6','pad7','pad8','pad9','pad10'];
	var len = ids.length;
	$.each(ids,function(index,id){
		var field = $('#'+id);
		if(!field.data('hasKeyupEvent') || field[0].id == 'fworkproc'){
			if(field.hasClass('select_wrap')){
				field = field.find('.select_text');
			}
			field.keyup(function(e){
				if(e.keyCode == 13){
					getFocus(getValidField(index));
				}
			}).data('hasKeyupEvent',true);
		}
	});
	//获取下一个可跳转的表单元素
	function getValidField(index){
		while(++index<len || ids[index]!='fboxlength1'){
			if(isValid(ids[index])){
				return $('#'+ids[index]);
			}
		}
		return false;
	}
	//获取焦点操作
	function getFocus(field){
		if(field && field.length){
			if(field.data('djselect')){
				field.data('djselect').activeSelect();
			}else{
				field.focus().select();
			}
		}
	}
	//是否是支持跳转，获取焦点的表单元素
	function isValid(id){
		var field = $('#'+id);
		if(field.length){
			return !(field.prop('readonly') || field.parents('.disable').length || (field.data('djselect') && !field.data('djselect').getEnable()));
		}
		return false;
	}
}

function initialInputText(){
	window.addfloatOnlyEvent($('#fprice'),3);
	window.addfloatOnlyEvent($('#fmateriallength'),1);
	window.addfloatOnlyEvent($('#fmaterialwidth'),1);
	window.addfloatOnlyEvent($('#fboxlength'),1);
	window.addfloatOnlyEvent($('#fboxwidth'),1);
	window.addfloatOnlyEvent($('#fboxheight'),1);
	window.addfloatOnlyEvent($('#fboxlength1'),1);
	window.addfloatOnlyEvent($('#fboxwidth1'),1);
	window.addfloatOnlyEvent($('#fboxheight1'),1);
}
function initialHFormulaInputText(){
	window.addfloatOnlyEvent($('#fhformula0'),1);
	window.addfloatOnlyEvent($('#fhformula1'),1);
}
function initialVFormulaInputText(){
	window.addfloatOnlyEvent($('#fvformula0'),1);
	window.addfloatOnlyEvent($('#fdefine1'));
	window.addfloatOnlyEvent($('#fdefine2'));
	window.addfloatOnlyEvent($('#fdefine3'));
}