//快速下单列表中的下载
function downLoad(str){
	parent.location.href =str;
}
//下单按钮
function sure_xd(productid,supplierid){
	var supplierid = $("input[name='custproductQuery.supplierId']").val();
		if("${session.user.fname}" !=""){
			//快速下单下单按钮 跳转
			parent.$('#iframepage').attr("src",window.getBasePath()+"/custproduct/orderOnline.net?fcustproductid="+encodeURIComponent(productid)+"&fsupplierid="+encodeURIComponent(supplierid)+"&type=0");
		}else{
			window.goToSmallLogin();
		}
}
//设为常用按钮
function setcommon(obj){
	$.ajax({
		url:window.getBasePath()+"/custproduct/isCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:obj},
		success:function(data){
			if(data ="success"){
				parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getQuickOrderTable(1);
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}
//取消常用
function newcancelCommon(obj){
	$.ajax({
		url:window.getBasePath()+"/custproduct/cancelCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:obj},
		success:function(data){
			if(data ="success"){
				   parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getQuickOrderTable(1);
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}

//添加购物车按钮
function addCar(productid,supplierid){
	var supplierid = $("input[name='custproductQuery.supplierId']").val();
	if("${session.user.fname}" !=""){
		//快速下单 加入购物车按钮 跳转
		parent.$('#iframepage').attr("src",window.getBasePath()+"/custproduct/orderOnline.net?fcustproductid="+encodeURIComponent(productid)+"&fsupplierid="+encodeURIComponent(supplierid)+"&type=1");
	}else{
		window.goToSmallLogin();
	}
}
//批量设为常用
function pl_setCommon(){
	var fids = '';
	$(':checked').each(function(i,c){
		if($(this).attr('productid')!=undefined){
			fids += $(this).attr('productid');
			if($(':checked').length-1>i){
				fids += ",";
			}
		}
	});
	if(fids.length==0){
		parent.layer.msg('请选择数据！');
		return false;
	}
	$.ajax({
		url:window.getBasePath()+"/custproduct/isCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:fids},
		success:function(data){
			if(data ="success"){
				parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getQuickOrderTable(1);
					
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}

//批量取消常用
function _cancelCommon(){
	var fids = '';
	$(':checked').each(function(i,c){
		if($(this).attr('productid')!=undefined){
			fids += $(this).attr('productid');
			if($(':checked').length-1>i){
				fids += ",";
			}
		}
	});
	if(fids.length==0){
		parent.layer.msg('请选择数据！');
		return false;
	}
	$.ajax({
		url:window.getBasePath()+"/custproduct/cancelCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:fids},
		success:function(data){
			if(data ="success"){
				parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getQuickOrderTable(1);
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}

//批量加入购物车
function pl_addCar(){
	if("${session.user.fname}" !=""){
		//循环取客户产品ID 放入变量fids中
		var fids = '';
		$(':checked').each(function(i,c){
			if($(this).attr('productid')!=undefined){
				fids += $(this).attr('productid');
				if($(':checked').length-1>i){
					fids += ",";
				}
			}
		});
		if(fids.length==0){
			parent.layer.msg('请选择数据！');
			return false;
		}
		var supplierid = $("input[name='custproductQuery.supplierId']").val();
		parent.$('#iframepage').attr("src",window.getBasePath()+"/custproduct/orderOnline.net?fcustproductid="+encodeURIComponent(fids)+"&fsupplierid="+encodeURIComponent(supplierid)+"&type=2");
	}else{
		window.goToSmallLogin();
	}

	
}
//常用
function pl_common(obj){
	if(obj ==0 ){//全部
		$("#fiscommon").val(0);
		$("#cy_btn").attr("class","common");
		$("#qb_btn").attr("class","allPro");
		window.getQuickOrderTable(1);
		$(".settingCommon").attr("onclick","pl_setCommon()");
		$(".settingCommon").text("设为常用");
	}else if(obj ==1){//常用
		$("#fiscommon").val(1);
		$("#cy_btn").attr("class","allPro");
		$("#qb_btn").attr("class","common");
		$(".settingCommon").attr("onclick","_cancelCommon()");
		$(".settingCommon").text("取消常用");
		window.getQuickOrderTable(1);
	}
}
/***订单记录-备货详情*/
function orderDetail_BH(obj){
	/*parent.layer.msg("订单记录-备货详情");*/
	if("${session.user.fname}" !=""){
		parent.$('#iframepage').attr("src",window.getBasePath()+"/deliverapply/getStockDetail.net?fid="+obj);
	}else{
		window.goToSmallLogin();
	}
}

/***订单记录-要货修改*/
function edit_yh(obj){
	//parent.layer.msg("开发中-订单记录-要货修改")
	if("${session.user.fname}" !=""){
		parent.$('#iframepage').attr("src",window.getBasePath()+"/deliverapply/editDeliverOrder.net?fid="+obj);
	}else{
		window.goToSmallLogin();
	}
}

/***订单记录-备货修改*/
function edit_bh(obj){
	/*parent.layer.msg("开发中-订单记录-备货修改");*/
	if("${session.user.fname}" !=""){
		parent.$('#iframepage').attr("src",window.getBasePath()+"/mystock/editMystockOrder.net?fid="+obj);
	}else{
		window.goToSmallLogin();
	}
	
}

/***订单记录-要货删除*/
function del_yh(obj){
	$.ajax({
		url:window.getBasePath()+"/deliverapply/delete.net",
		type:"post",
		dataType:"json",
		data:{"id":obj},
		success:function(data){
			if(data.success ===true){
				   parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getOrderRecordTable(1);
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('<div style="text-align:center">'+data.msg+'</div>', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}

/***订单记录-备货备货*/
function del_bh(obj){
	$.ajax({
		url:window.getBasePath()+"/mystock/delete.net",
		type:"post",
		dataType:"text",
		data:{"id":obj},
		success:function(data){
			if(data ==="success"){
				   parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getOrderRecordTable_BH(1);
					parent.layer.close(index);
					});
			}else{
				parent.layer.alert('<div style="text-align:center">'+data+'</div>', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}

/*** 需求包记录-删除*/
function del_mandpackage(obj){
	$.ajax({
		type : "POST",
		url : window.getBasePath()+"/firstproductdemand/delete.net",
		data : {"fids":obj},
		success : function(response) {
			if (response == "success") {
				parent.layer.alert('操作成功！', function(alIndex){
					parent.document.getElementById("iframepage").contentWindow.getMandPackageTable(1);
					parent.layer.close(alIndex);
			    });
			}else {
				parent.layer.alert('操作失败！', function(alIndex){
					parent.layer.close(alIndex);
			    });
			}
		}
    });
}
/*** 需求包记录-草稿箱列表-删除*/
function del_mandpackage_cgx(obj){
	$.ajax({
		type : "POST",
		url : window.getBasePath()+"/firstproductdemand/delete.net",
		data : {"fids":obj},
		success : function(response) {
			if (response == "success") {
				parent.layer.alert('操作成功！', function(alIndex){
					parent.document.getElementById("iframepage").contentWindow.getMandPackage_ccg(1);
					parent.layer.close(alIndex);
			    });
			}else {
				parent.layer.alert('操作失败！', function(alIndex){
					parent.layer.close(alIndex);
			    });
			}
		}
    });
}
/***需求包列表-修改功能*/
function edit_mandpackage(obj){
	window.location.href=window.getBasePath()+"/firstproductdemand/mand_edit.net?id="+obj ;
	
}
