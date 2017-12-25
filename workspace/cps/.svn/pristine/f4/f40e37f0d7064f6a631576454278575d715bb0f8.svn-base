$(function(){
	getHtmlLoadingBeforeHeight();
	if(!WebUploader.Uploader.support()){//不支持插件
		layer.open({
		    type: 1,
		    area: ['400px', '100px'],
		    closeBtn: 0,
		    title: false, //不显示标题
		    content: '<div class="flashTip"><p class="flashp">为了更好体验,请安装/升级Flash,现在<a class="flasha" href="https://get.adobe.com/flashplayer/?loc=cn"  target="_blank">安装</a>?安装后请刷新页面！</p></div>'
		});
	}
	 template =  '<div class="product"><form>' +
					'            <div class="product_info">' +
					'				 <input type="hidden" name="fid" />' +
					'				 <input type="hidden" name="fparentid" />' +
					'				 <input type="hidden" name="schemedesignid" />' +
					'                <label>产品名称：</label>' +
					'                <input class="name" name="fname"/>' +
					'                <label>规格：</label>' +
					'                <div class="spec">' +
					'                    <input class="length" placeholder="长/mm" name="fboxlength"/> X <input class="width" placeholder="宽/mm" name="fboxwidth"/> X <input class="height" placeholder="高/mm" name="fboxheight"/>' +
					'                </div>' +
					'                <label>材料：</label>' +
					'                <input class="material" name="fmaterialcodeid"/>' +
					'            </div>' +
					'			<div class="product_info" >'+
					'                      <label>产品编码：</label>'+
					'            	      <input class="fnumber" name="fnumber" />'+
					'            </div> '+
					'            <div class="file">' +
					'                <div class="file_count">' +
					'                    <span class="file_label">附件数：</span>' +
					'                    <div class="input_group">' +
					'                        <span class="minus">-</span><input value="1" name="subProductAmount" class="amount"/><span class="plus">+</span>' +
					'                    </div>' +
					'                </div>' +
					'                <div class="ope_add fl">' +
					'                    添加附件：' +
					'                    <div class="plus" >+</div>' +
					'                </div>' +
					'					<div  class="uploader-list fl thelist" ></div>'+
					'                <br class="clear" />' +
					'            </div></form>' +
					'            <a href="javascript:void(0);" class="delete delproduct">&times;</a>' +
					'        </div>' ;
	template = template.replace(/\t+/g,'');
	$('#addProduct').click(function(){
			productclickEvent($(this));
	});
	$('#main').delegate('.delproduct','click',function(){
		deleteProductEvent($(this));
	}).delegate('.minus','click',function(e){
		if(tipmessage($(this),e)){
		var $input = $(this).next(),
			val = parseInt($input.val()) || 0;
		if(val>0){
			$input.val(val-1);
			$input.change();
		}}
	}).delegate('span.plus','click',function(e){
		if(tipmessage($(this),e)){
		var $input = $(this).prev(),
			val = parseInt($input.val()) || 0;
		$input.val(val+1);
		$input.change();
		}
	}).delegate('.amount','keyup paste',function(e){ //产品名称为空，点击其他，弹出提示
			 $(this).val($(this).val().replace(/\D|^0/g,''));
	}).delegate('.width,.length,.height','keypress change mousewheel blur',function(e){ //产品名称为空，点击其他，弹出提示
		floatOnlyEvent($(this),e,1);
	}).delegate('.product input:visible[class!=name]','focus',function(e){ //产品名称为空，点击其他，弹出提示
			tipmessage($(this),e);
		}).delegate('.product div.plus','click',function(e){ //产品名称为空，点击其他，弹出提示
			tipmessage($(this),e);
		}).delegate('.name,.length,.width,.height,.material,.fnumber,.amount','keyup',function(e){ //产品名称为空，点击其他，弹出提示
			if(e.keyCode == 13){
			enterKeyFocus($(this),e);
			}
		}).delegate('.infoafter','mouseout',function(e){//附件删除事件
			 $(this).find('a.delfile').css('display','none');
		}).delegate('.editinfoafter','mouseover',function(e){//附件删除事件
				infoafteroverEvent($(this));
		});
	//返回
	$("#operate .fr").click(function(){
	/*parent.$('#8f3b223239bbc3454aaf308e406a351b').click();//跳转*/
	 parent.layer.closeAll();
	});
	//保存
	$("#operate .fl").click(function(){
		saveclickEvent($(this));
	});
	if(productState==="add"){
	//默认显示一个产品
	$('#addProduct').click();
	}else
	{
		if($(".product").length==0) $('#addProduct').click();
		AfterProductEvent($(".product"));
	}
});

function deleteProductEvent(obj)
{
	obj.removeClass("delproduct");
		$.ajax({
							type : "POST",
							url : "${ctx}/schemedesign/delfileByfparent.net",
							dataType:"json",
							data:{id:$("#firstdemandfid").val(),fparentid:obj.parents('.product').find("input[name=fid]").val()},
							success : function(response) {
								if(response.success)
								{
										obj.parents('.product').remove();
										var products = $('.product');
										if(products.length==1){
											products.removeClass('parentProduct');
										}
								}else
								{
									layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'), function(index){layer.close(index);});
								}
								obj.addClass("delproduct");
							},
							error:function (){
								layer.alert('网络异常！', function(index){layer.close(index);});
								obj.addClass("delproduct");
							}
			});
}
function saveclickEvent(obj)
{
	obj.unbind("click");
	var productObject={};
	var productName=[];
	var pindex=0;
		productObject["id"]=$("#firstdemandfid").val();
		 $('.product form').each(function(i){
		 	var form=$(this);
		 	if(form.find("input[name=fname]").val()){
		 		if(i>0&&!form.find(".material").val())
		 		{
		 			form.find(".material").focus().select();
		 			layer.tips('子件材料为必填', form.find(".material"), {tips: [1, '#F7874E'],time: 5000});
		 			productObject["isOk"]=true;
					return false;
		 		}
		 		if(i>0&&!productObject["products[0].fid"])
		 		{
		 			$(".parentProduct input[name=fname]").focus().select();
		 			layer.tips('总产品材料不能为空', $(".parentProduct input[name=fname]"), {tips: [1, '#F7874E'],time: 5000});
		 			productObject["isOk"]=true;
					return false;
		 		}
		 		if(i>0&&$.inArray(form.find("input[name=fname]").val(),productName)!=-1)
		 		{
		 			form.find("input[name=fname]").focus().select();
		 			layer.tips('此产品名称已存在,请重新填写', form.find("input[name=fname]"), {tips: [1, '#F7874E'],time: 5000});
		 			productObject["isOk"]=true;
					return false;
		 		}
			 	var params = $(this).serializeArray();  
			 		for (var item in params) {  
	                    productObject["products["+pindex+"]."+params[item].name] = params[item].value; 
	                } 
	                 productName.push(productObject["products["+pindex+"].fname"]);
	                 pindex++;
			 	}
			 });
			 if(productObject["isOk"]) {obj.click(function(){saveclickEvent(obj);}); return false;}//自件为空
				$.ajax({
				url:"${ctx}/schemedesign/SaveFirstdemandProduct.net",
				type:"post",
				dataType:"json",
				data:productObject,
				success:function(data){
					if(data.success){
						layer.alert("保存成功！", function(index){		
						/*	parent.$('#8f3b223239bbc3454aaf308e406a351b').click();*/
							parent.layer.closeAll();
						});
					}else
					{
					layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'), function(index){layer.close(index);});
					}
					obj.click(function(){saveclickEvent(obj);});
				},
				error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
					obj.click(function(){saveclickEvent(obj);});
				}
			});
}
//新增产品事件
function productclickEvent(obj){
	obj.unbind("click");
		$.ajax({
							type : "POST",
							url : "${ctx}/schemedesign/getProductSubId.net",
							dataType:"json",
							success : function(response) {
								var product = $(template).insertBefore($('#operate'));
								var products = $('.product');
								if(products.length == 1){
									product.find('.file_count').hide();
									product.find('input[name=schemedesignid]').val($("#firstdemandfid").val());
									
								}else if(products.length == 2){
										product.prev().addClass('parentProduct');
										product.find('input[name=fparentid]').val($(".parentProduct input[name=fid]").val());
								}
								product.find('input[name=fid]').val(response);
								AfterProductEvent(product);
								obj.click(function(){productclickEvent(obj);});
							},
							error:function (){
								layer.alert('网络异常！', function(index){layer.close(index);});
								obj.click(function(){productclickEvent(obj);});
								return false;
							}
			});
}
//附件上传后删除事件
function infoafteroverEvent(obj,uploader)
{
	obj.find('a.delfile').css('display','inline-block');
		    	var aId = obj.find('a.delfile').attr('id');
		    	$('#'+aId).unbind().click( function () {
		    			$.ajax({
								type : "POST",
								url : window.getBasePath()+'/productfilenol/deleteImg.net',
								dataType:"text",
								async:false,
								data: {"fid":aId},  
								success : function(response) {
									if(response =="success"){
											var divId =$("#"+aId).parent("p").parent("div");
											if(uploader&&uploader.getFile(divId.attr('id'))) uploader.removeFile(divId.attr('id'),true);
											divId.remove();
									}else{
										layer.alert('操作失败！', function(index){layer.close(index);});
									}
								},
								error:function (){
									layer.alert('操作失败！', function(index){layer.close(index);});
								}
						});
		    	});
}

//上传附件功能
function uploadFile($fparentId){
	$.each($fparentId,function(index,item){
	/***********************************上传****************开始*******************************/
	var $list = $(item).find(".uploader-list");
	var $pick= $(item).find(".ope_add .plus");
	var fileId = $(item).find("input[name=fid]");
	var uploader = WebUploader.create({
			    swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
			    server: window.getBasePath()+'/productfilenol/uploadImg.net?fid='+fileId.val(),  // 文件接收服务端。
			    pick: $pick,
			    fileSingleSizeLimit: 10485760,
			    auto : true //设置为 true后,不需要手动调用上传,有文件选择即开始上传
    });
    /*** 当文件被加入队列以后触发*/
	uploader.on( 'fileQueued', function( file ) {
	    $list.append( '<div id="' + file.id + '" class="item">'+
	    					'<p class="info"><a class="filename" style="color:#000;text-decoration:none;" target="_blank">' + file.name + '</a><a id="' + file.id + '"  class="delfile" href="javascript:void(0);">&times;</a></p>'+
	    					'<p class="state">等待上传...</p>'+
	    			'</div>' );
	});
	/*** 上传过程中触发，携带上传进度*/
	uploader.on( 'uploadProgress', function( file, percentage ) {
    	var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          				'<div class="progress-bar" role="progressbar" style="width: 0%"></div>' +
	        			'</div>').appendTo( $li ).find('.progress-bar');
	    }
	    $li.find('p.state').text('上传中');
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	/*** 当文件上传成功时触发*/
	uploader.on( 'uploadSuccess', function( file ,response) {
    	$( '#'+file.id ).find('p.state').text('已上传');
    	$( '#'+file.id ).find('.delfile').attr('id',response._raw);
    	$( '#'+file.id +' .filename').attr('href',ROOT_PATH+'/productfile/getFileSource2.net?fid='+response._raw);
    	$( '#'+file.id ).attr("class","itemafter");
    	$( '#'+file.id ).find('p').first().attr("class","infoafter").mouseover(function(e){
    		infoafteroverEvent($(this),uploader);
	    });
	});
	
	/*** 不管成功或者失败，文件上传完成时触发*/
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	    $( '#'+file.id ).find('.state').fadeOut();
	});
	
	/*** 当validate不通过时，会以派送错误事件的形式通知调用者*/
	uploader.onError = function( type ) {
		if(type=='F_EXCEED_SIZE'){
		    layer.msg("单个文件不允许超过10M！");
		}
	};
	});
}
//产品新增后渲染事件
function AfterProductEvent(parentid){
		uploadFile(parentid);
		window.getHtmlLoadingAfterHeight();	
}
//验证产品是否输入
 function tipmessage(obj,e)
	  {
	  	var product=obj.parents(".product").find(".name");
		if(!obj.hasClass("name")&&!product.val()){
				obj.blur();
				layer.tips('请先输入产品名称', product, {tips: [1, '#F7874E'],time: 3000});
				product.focus().select();
				e.preventDefault();
				return false;
		}
		var material=obj.parents(".product").find(".material");
		if(obj.parents(".product").prev(".product").length>0&&!material.val()&&!obj.hasClass("material"))
		{
				obj.blur();
				layer.tips('子件材料为必填', material, {tips: [1, '#F7874E'],time: 3000});
				material.focus().select();
				e.preventDefault();
				return false;
		}
		
		return true;
	  }

//回车事件
function enterKeyFocus(field,e){
				var ids = ['name','length','width','height','material','fnumber','amount'];
				var len = ids.length;
				$fparentid=field.parents(".product");
				var index=ids.indexOf(field.attr("class"));
				if(index===-1) return false;
				
					$input = getValidField(index);
					if($input){
						getFocus($input);
					}else{
						return;
					}
				
	//获取下一个可跳转的表单元素
	function getValidField(index){
		while(++index<len){
			if(isValid(ids[index])){
				return $fparentid.find('.'+ids[index]);
			}
		}
		if(index===len&&$fparentid.next()) return $fparentid.next().find('.'+ids[0]);
		return false;
	}
	//获取焦点操作
	function getFocus(field){
		if(field && field.length){
			field.focus().select();
		}
	}
	//是否是支持跳转，获取焦点的表单元素
	function isValid(classname){
		var field = $fparentid.find('.'+classname);
		if(field.length){
			return !(field.prop('readonly') || field.is(':hidden')||field.parents('.disable').length);
		}
		return false;
	}
}	  

function floatOnlyEvent(field,event,precision) {
	if(!field.length){
		return;
	}
	if(event.type=="keypress"){  
			var event= event || window.event;  
			var getValue = field.val();  
			//控制第一个不能输入小数点"."  
			if (getValue.length == 0 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入一个小数点"."  
			if (getValue.indexOf('.') != -1 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入的值  
			if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
			    event.preventDefault();  
			     return;  
			    }  
	} 
		//失去焦点是触发  
		if(event.type=="change"){  
		
			var value = field.val(),reg = /^\d*\.{0,1}\d*$/;  
			if (!reg.test(value)) {  
				field.val("");  
			}else if(precision){
				field.val(parseFloat(parseFloat(value).toFixed(precision)));
			}
		}
		if(event.type=="mousewheel"){
			if(this.readOnly || !field.is(':focus')){
				return;
			}
			//2015-12-01 如果使用jQuery来进行事件绑定，在事件回调的参数中e是被jQuery重新封装的，所以我们必须使用e.originalEvent来指向原始的事件对象，就是这样任性。
			ScrollText(this,event.originalEvent.wheelDelta);
			event.stopPropagation();	
			window.event.preventDefault();	
		}
		if(event.type=="blur"){
			field.trigger('change');
		}
}
