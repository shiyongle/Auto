var CONTROL_HALT=false;
$.fn.value = function(val){
	var obj = this.data('djselect');
	if(obj){
		return obj.value(val);
	}
};
$(function(){
	initSelect();
	if(orderState == 'edit'){
		$('#fsupplierid').value(orderInfo.fsupplierid);
	}
	initialInputText();
	initControl();
	enterKeyFocus();
	initAction();
});
function setData(){
	var inputs = $('#order_form').find('input[name]');
	inputs.each(function(index,elem){
		var val;
		if(elem.name in orderInfo){
			val = orderInfo[elem.name];
			if($(elem).data('djselect')){
				$(elem).data('djselect').value(val);
			}else{
				elem.value = val;
			}
		}
	});
	
}
function enterKeyFocus(){
	var ids = ['flabel','fsupplierid','fmaterialfid','fboxmodel','fstavetype','fmateriallength','fmaterialwidth','fboxlength','fboxwidth',
	     'fboxheight','fseries','famount','fhformula','fhformula1','fdefine2','fdefine3','fvformula','fhline','fvline','faddressid','fdescription'];
	var len = ids.length;
	$.each(ids,function(index,id){
		var field = $('#'+id),$input;
		if(!field.data('hasKeyupEvent')){
			field.keyup(function(e){
				if(e.keyCode == 13){
					$input = getValidField(index);
					if($input){
						getFocus($input);
					}else{
						$('#place_order').click();
					}
				}
			}).data('hasKeyupEvent',true);
		}
	});
	$('#flabel').find('.select_text').focus();
	//获取下一个可跳转的表单元素
	function getValidField(index){
		while(++index<len){
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
			return !((field.data('djselect') && !field.data('djselect').getEnable()) || field.prop('readonly') || field.parents('.disable').length);
		}
		return false;
	}
}
function initAction(){
	//立即下单
	$('#place_order').on('click',function(){
		if(CONTROL_HALT){
			return;
		}
		$('#fstate').val('0');
		submit();
	});
	
	//加入购物车
	$('#add_car').on('click',function(){
		if(CONTROL_HALT){
			return;
		}
		$('#fstate').val('7');
		submit();
	});
	$('#addAddress').on('click',function(){
		parent.layer.open({
		    title: ['新增收货地址','background-color:#CC0000; color:#fff;'],
		    type: 2,
		    anim:true,
		    area: ['530px', '200px'],
		    content: ROOT_PATH+"/custproduct/add_address.net"
		});
	});
	//设为默认
	$('#descSetBtn').on('click',function(e){
		$.ajax({
			url: ROOT_PATH+'/board/setCustomerDescription.net',
			data: {description: $('#fdescription').val()},
			type: 'post',
			success: function(res){
				layer.msg('操作成功！');
			},
			failure: function(){
				layer.msg('操作失败！');
			}
		});
		e.preventDefault();
	});
	//删除标签
	$('#delLabel').on('click',function(e){
		var obj = $('#flabel').data('djselect'),
			val = obj.value();
		if(val){
			$.ajax({
				url: ROOT_PATH+'/board/delCustomerLabelInfo.net',
				data: {labelName: val},
				type: 'post',
				success: function(res){
					layer.msg('操作成功！');
				},
				failure: function(){
					layer.msg('操作失败！');
				}
			});
			obj.value('');
		}
	});
	
	//加入购物车-确认
	function submit(){
		if(validateData()){
			$('#layer').val($('#fmaterialfid').data('model').flayer);
			layer.load(2, {shade: 0.1});
			$.ajax({
				url: ROOT_PATH+'/board/checkBdDeliverapply.net',
				data: $('#order_form').serialize(),
				dataType: 'json',
           		type: 'post',
           		success: function(res){
           			layer.closeAll('loading');
					if (res.success) {
						if(res.data[0].flag == 1){
							var index = layer.confirm(res.msg,{btn: ['是', '否']},function(action){
								layer.close(index);
								submitboard();
							});
							$('a.layui-layer-btn0').attr('tabindex','0').focus().keyup(function(e){
								if(e.which === 13){
									$(this).click();
								}
							});
						}else{
							submitboard();
						}
	           		}
			}});
		}
	}
	function submitboard()
	{
				layer.load(2, {shade: 0.1});
			//	$('#fvformula').val(parseFloat($('#fvformula').val()).toFixed(1));
				$('#fvformula').val(Math.round(parseFloat($('#fvformula').val()*10))/10);
				$.ajax({
				url: ROOT_PATH+'/board/saveBoardSignleDeliverapply.net',
				data: $('#order_form').serialize(),
				dataType: 'json',
           		type: 'post',
           		success: function(res){
           			layer.closeAll('loading');
           			if(res.success){
           				getFrameWin().getboardsTable(1);
           				if(orderState == 'add'){
           					$.ajax({
           						url: ROOT_PATH+'/board/getCustBoardFormula.net',
           						dataType: 'json',
           						success: function(data){
           							if(!$.isEmptyObject(data)){
           								custformulas = data;
           							}
           						}
           					});
           					CONTROL_HALT = true;
           					$('#fmateriallength').val('');
           					$('#fmaterialwidth').val('');
           					$('#fboxlength').val('');
           					$('#fboxwidth').val('');
           					$('#fboxheight').val('');
           					$('#famount').val('');
           					$('#famountpiece').val('');
           					$('#fhline').val('');
           					$('#fvline').val('');
           					CONTROL_HALT = false;
           					top.layer.msg('保存成功！');
           					$('#fboxlength').focus();
           				}else{
           					top.layer.msg('保存成功！');
           					top.layer.close(top.layerIndex);
           				}
           			}else{
           				layer.msg(res.msg);
           			}
           		},
           		failure: function(res){
           			layer.closeAll('loading');
           			layer.msg('保存失败！');
           		}
			});
	}
	function validateData(){
		var rline = /^(\d+\.?\d*(\+\d+\.?\d*)*)?$/,
			fmateriallength = val('fmateriallength'),
			fmaterialwidth = val('fmaterialwidth'),
			boxmodel = $('#boxmodel').value(),
			amount = $('#famount').val(),
			fhline;
		if(!amount || amount==0){
			return error('请填写配送数量！');
		}
		if(!(fmateriallength && fmaterialwidth)){
			return error('下料规格不允许为空，请填写！');
		}
		if($('#fstavetype').value() != '不压线'){
			fhline = $('#fhline').val();
			fvline = $('#fvline').val();
			if(!fhline){
				return error('请填写横向压线！');
			}else if(!rline.test(fhline)){
				return error('横向压线格式不正确！');
			}
			if(!fvline){
				return error('请填写纵向压线！');
			}else if(!rline.test(fvline)){
				return error('纵向压线格式不正确！');
			}
		}
		if(!materialLimit){
			layer.open({
			    title: false,
			    closeBtn:0,
			    type: 2,
			    anim:true,
			    area: ['742px', '200px'],
			    content: ROOT_PATH+"/pages/board/MaterialLimitWin.jsp"
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
		var boxLength = val('fboxlength'),
			boxWidth = val('fboxwidth'),
			boxHeight = val('fboxheight');
		if($('#fsupplierid').value()=='39gW7X9mRcWoSwsNJhU12TfGffw='){
			if(fmateriallength>350 || fmateriallength<15 || fmaterialwidth>247 || fmaterialwidth<13){
				layer.msg('落料超过制造商最值,请联系制造商！');
				return false;
			}
			if(boxLength){
				if(boxmodel=="普通"){						
					if(boxLength<5 || boxWidth<5 || boxHeight<5){
						layer.msg('普通箱型规格过小,请联系制造商！');
						return false;
					}	
				}else{
					if(boxLength<1 || boxWidth<1 || boxHeight<1){
						layer.msg('所选箱型规格过小,请联系制造商！');
						return false;							
					}
				}
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
}

function initSelect(){
	
	//客户标签下拉数据
	djSelect('flabel',{
		url:ROOT_PATH+'/board/getCustomerLabelList.net',
		hideTrigger: true,
		noHideValue: true,
		transformData: function(res){
			return $.map(res.list,function(item,index){
				return {text: item.fname,val: item.fname};
			});
		}
	});
	
	//制造商下拉数据
	djSelect({
		id: 'fsupplierid',
		url: ROOT_PATH+'/board/getSupplierAppList.net',
		forceSelection: true,
		firstLoad: orderState != 'add',
		autoSet: true,
		dropdownHeight:160,
		afterSelect: function(data){
			var selectObj = $('#fmaterialfid').data('djselect');
			selectObj.load({fsupplierid: data.val});
			if(this.firstLoad){
				selectObj.value(orderInfo.fmaterialfid);
				this.firstLoad = false;
			}
		}
	});
	
	//材料下拉数据
	top.materialSelectObj = djSelect({
		id: 'fmaterialfid',
		url: ROOT_PATH+'/productdef/loadMaterial.net',
		queryRemote: true,
		queryName: 'term',
		forceSelection: true,
		autoLoad: false,
		dropdownHeight:300,
		firstLoad: orderState != 'add',
		transformData: function(res){
			return $.map(res.list,function(item,index){
				return {text: item.fqueryname,val: item.fmaterialid,flayer: item.flayer};
			});
		},
		afterSelect: function(option){
			if(this.firstLoad){
				setData();
				this.firstLoad = false;
			}
			//本地存储选择的材料
			var materialValue = option.val,
				index;
			if(!localStorage.$BoardOrderedProducts){
				localStorage.$BoardOrderedProducts = materialValue;
			}else{
				orderedProducts = localStorage.$BoardOrderedProducts.split(',');
				index = $.inArray(materialValue,orderedProducts);
				if(index !=-1){
					orderedProducts.splice(index,1);
				}
				if(orderedProducts){
					orderedProducts.unshift(materialValue);
				}
				localStorage.$BoardOrderedProducts = orderedProducts.join(',');
			}
		},
		onLoad: function(options){
			if(orderState!='add'){
				if(!this.getOption(orderInfo.fmaterialfid)){
					this.options.unshift({
						val: orderInfo.fmaterialfid,
						text: orderInfo.materialname,
						flayer: orderInfo.layer
					});
				}
			}
			//最近选的材料，排序在首位
			if(options.length && localStorage.$BoardOrderedProducts){
				orderedProducts = localStorage.$BoardOrderedProducts.split(',');
				options.sort(function(a,b){
					index1 = orderedProducts.indexOf(a.val);
					index2 = orderedProducts.indexOf(b.val);
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
	djSelect({
		id: 'fboxmodel',
		forceSelection: true
	});
	djSelect({
		id: 'fstavetype',
		forceSelection: true
	});
	djSelect({
		id: 'fseries',
		forceSelection: true
	});
	
	//
	top.addressSelectObj = djSelect({
		id: 'faddressid',
		url: ROOT_PATH+'/address/loadDefaultFirst.net',
		forceSelection: true,
		autoDefaultSet: true,
		dropdownHeight: 140,
		transformData: function(res){
			return $.map(res,function(item,index){
				return {text: item.fdetailaddress,val: item.fid};
			});
		}
	});
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
			var value = fseries.value(),newValue;
			if(fstavetype.value()=='不压线'){
				newValue = '连做';
				fseries.data('djselect').setEnable(false);
			}else if(fboxmodel.value()==7){
				newValue = '连做';
				fseries.data('djselect').setEnable(false);
			}else if(fboxmodel.value()==3){
				newValue = '不连做';
				fseries.data('djselect').setEnable(false);
			}else{
				newValue = value;
				fseries.data('djselect').setEnable(true);
			}
			fseries.value(newValue);
			if(value != newValue){
				return true;
			}
		}
	},{
		from: 'fseries famount',to: 'famountpiece',callback: function(fseries,famount,famountpiece){
			if(famount.val()===''){
				famountpiece.val('');
				return;
			}
			var num = fseries.value()=='连做' ? 1 : 2;
			famountpiece.val(num * famount.val());
			return true;
		}
	},{
		from: 'fsupplierid', to: 'fmaterialfid',callback: function(fsupplierid,fmaterialfid){
			$('#fmaterialfid').value('');
			return true;
		}
	},{
		from: 'fmaterialfid',to: 'fstavetype',callback: function(fmaterialfid,fstavetype){
			if(fmaterialfid.value()===''){
				return;
			}
			var state = !$('#fstavetype').data('djselect').getEnable();
			var	flayer = fmaterialfid.data('model').flayer,newState;
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
		from: 'fboxmodel fstavetype',to: 'fhline fvline',callback: function(fboxmodel,fstavetype,fhline,fvline){
			var state = fhline.prop('readonly'),
				newState = (fstavetype.value()=='不压线' || fboxmodel.value()!=0) ? true: false;
				if(fstavetype.value()=='不压线')
				{
					fhline.prop('value',"");
					fvline.prop('value',"");
				}
				if(state != newState){
					fhline.prop('readonly',newState);
					fvline.prop('readonly',newState);
					return true;
				}
		}
	},{
		from: 'hgongsi fboxlength fboxwidth fboxheight fhformula fhformula1',to: 'fhline',callback: function(hgongsi,fboxlength,fboxwidth,fboxheight,fhformula,fhformula1,fhline){
			var state = ! fhline.val(),
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
		from: 'vgongsi fboxlength fboxwidth fboxheight fvformula fdefine2 fdefine3',to: 'fvline',callback: function(vgongsi,fboxlength,fboxwidth,fboxheight,fvformula,fdefine2,fdefine3,fvline){
			var state = ! fvline.val(),
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
		from: 'fhline fvline',to: 'materialspec fmateriallength fmaterialwidth',callback: function(fhline,fvline,materialspec,fmateriallength,fmaterialwidth){
			var rline = /^(\d+\.?\d*(\+\d+\.?\d*)*)$/;
			fhline = fhline.val();
			fvline = fvline.val();
			fmateriallength.val(rline.test(fhline)?parseFloat((eval(fhline)).toFixed(1)):'');
			fmaterialwidth.val(rline.test(fvline)?parseFloat((eval(fvline)).toFixed(1)):'');
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
		from: 'fdefine2 fdefine3 fboxwidth fvformula',to: 'fdefine1',callback: function(fdefine2,fdefine3,fboxwidth,fvformula,fdefine1){
			var value;
			if(fdefine2.length>0){
				value = (parseFloat(fboxwidth.val() || 0)+parseFloat(fvformula.val() || 0))/2 - fdefine2.val() - fdefine3.val();
				fdefine1.val(value.toFixed(2));
				return true;
			}
		}
	},{
		from: 'fvformula',to: 'fvformula1',callback: function(fvformula,fvformula1){
			fvformula1.val(fvformula.val());
			return true;
		}
	},{
		from: 'fmaterialfid',to: 'farrivetime',callback: function(fmaterialfid,farrivetime){
			if(fmaterialfid.value()){
				$.ajax({
					url: ROOT_PATH+'/board/getfirstDateofMaterial.net',
					dataType: 'json',
					type: 'post',
					data: {fmaterialfid:fmaterialfid.value(),fsupplierid:$('#fsupplierid').value()},
					success: function(data){
						if(data.success){
							farrivetime.val(data.msg);
						}else{
							alert("获取配送时间出错！"+data.msg);
						}
					}
				});
			}
		}
	}];

	var formulaObj = {
		221: ['fhformula','+长+宽+长+(宽-','fhformula1',')'],		/*横向连做普通箱型*/
		211: ['fhformula','+长+(宽-','fhformula1',')'],				/*横向不连做普通箱型*/
		222: ['fhformula','+长+宽+长+宽'],							/*横向连做剩余箱型*/
		212: ['fhformula','+长+宽'],									/*横向不连做剩余箱型*/
		227: ['高+长+高'],											/*横向连做天地盖箱型*/
		/*纵向*/
		101: ['(宽+','fvformula',')/2+高+(宽+','fvformula1',')/2'],
		102: ['(宽-','fvformula',')+高+(宽-','fvformula1',')'],
		103: ['宽+高+','fvformula'],
		104: ['高+(宽+','fvformula',')/2'],
		105: ['(宽+','fvformula',')/2+高'],
		106: ['高'],
		107: ['高+宽+高'],
		108: ['fdefine1','+','fdefine2','+','fdefine3','+高+(宽+','fvformula',')/2']
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
	function generateHline(gongsi,fboxlength,fboxwidth,fboxheight,fhformula,fhformula1,fhline){
		if(gongsi!==false){
			var length = parseFloat(fboxlength.val()) || 0,
				width = parseFloat(fboxwidth.val()) || 0,
				height = parseFloat(fboxheight.val()) || 0,
				x = parseFloat(fhformula.val()) || 0,
				y = parseFloat(fhformula1.val()) || 0,
				token = gongsi.data('token');
			fhline.val($.map(lineObj[token].split('_'),function(item,index){
				return eval(item);
			}).join('+'));
		}else{
			$('#fhline').val('');
		}
	}
	function generateVline(gongsi,fboxlength,fboxwidth,fboxheight,fvformula,fdefine2,fdefine3,fvline){
		if(gongsi!==false){
			var length = parseFloat(fboxlength.val()) || 0,
				width = parseFloat(fboxwidth.val()) || 0,
				height = parseFloat(fboxheight.val()) || 0,
				x = parseFloat(fvformula.val()) || 0,
				y2 = parseFloat(fdefine2.val()) || 0,
				y3 = parseFloat(fdefine3.val()) || 0,
				y1 = ((width+x)/2 - y2 - y3).toFixed(2),
				token = gongsi.data('token');
			fvline.val($.map(lineObj[token].split('_'),function(item,index){
				return Math.round(eval(item)*100)/100;
			}).join('+'));
		}else{
			$('#fvline').val('');
		}
	}
	function generateFormula(fmaterialfid,fboxmodel,fstavetype,fseries,hgongsi,vgongsi){
		var flayer = $('#fmaterialfid').data('model').flayer,htoken,vtoken;
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
			addGongsiValue(fboxmodel,flayer);
			enterKeyFocus();
		}
		return true;
	}
	function addGongsiValue(boxmodel,layer){
		var defaultValueObj = null,
			hformulaValue = 0,
			hformula1Value = 0,
			vformulaValue = 0,
			unset = true,
			field,fdefine1,fdefine2,fdefine3;
		if(orderState== 'add'){
			$.each(custformulas,function(_,item){
				if(item.fboxmodel == boxmodel && item.flayer == layer){
					defaultValueObj = item;
				}
			});
			if(defaultValueObj){
				$.each(defaultValueObj,function(key,value){
					if(key=='fboxmodel' || key=='flayer'){
						return;
					}
					field = $('#'+key);
					if(field.length){
						field.val(value);
					}
				});
				unset = false;
			}else{
				layer = parseInt(layer);
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
			}
		}else if(orderState == 'edit'){
			hformulaValue = orderInfo.fhformula;
			hformula1Value = orderInfo.fhformula1;
			vformulaValue = orderInfo.fvformula;
			fdefine1 = orderInfo.fdefine1;
			fdefine2 = orderInfo.fdefine2;
			fdefine3 = orderInfo.fdefine3;
		}
		if(unset){
			setValue('fhformula',hformulaValue);
			setValue('fhformula1',hformula1Value);
			setValue('fvformula',vformulaValue);
			setValue('fdefine1',fdefine1);
			setValue('fdefine2',fdefine2);
			setValue('fdefine3',fdefine3);
		}
		setValue('fvformula1',$('#fvformula').val());
		function setValue(id,value){
			var field = $('#'+id);
			if(field.length){
				field.val(value);
			}
		}
	}
	function addEvent(gongsi){
		var rinput = /^(fhformula|fhformula1|fvformula|fdefine2|fdefine3)$/,
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
			rinput1 = /^(fhformula|fhformula1|fvformula|fdefine2|fdefine3)$/,
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

function initialInputText(){
	window.addfloatOnlyEvent($('#fmateriallength'),1);
	window.addfloatOnlyEvent($('#fmaterialwidth'),1);
	window.addfloatOnlyEvent($('#fboxlength'),1);
	window.addfloatOnlyEvent($('#fboxwidth'),1);
	window.addfloatOnlyEvent($('#fboxheight'),1);
	window.addNumOnlyEvent($('#famount'));
}
function initialHFormulaInputText(){
	window.addfloatOnlyEvent($('#fhformula'),1);
	window.addfloatOnlyEvent($('#fhformula1'),1);
}
function initialVFormulaInputText(){
	window.addfloatOnlyEvent($('#fvformula'),1);
	window.addfloatOnlyEvent($('#fdefine1'));
	window.addfloatOnlyEvent($('#fdefine2'));
	window.addfloatOnlyEvent($('#fdefine3'));
}