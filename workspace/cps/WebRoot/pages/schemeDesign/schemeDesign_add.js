$(function(){
	//高端设计界面数据数据构造？？？？？？？？ 写在这里报错
//	var eachMain = {};
//	var eachInfo = [];
});
//-----------------------------------------------------------------------高端设计方案js------------------------------------------------------

//返回按钮 后退历史记录
function lastStep(){
	//history.back();
	var win= parent.$('#iframepage')[0].contentWindow;
	win.loadScheme(1,win.demanid);
	parent.layer.closeAll();
}

function checkGdsjData(){
	var flag = true;	
	eachMain.fcustomerid = $('.addSchemeDesign').find("input[id=fcustomerid]").val();
	eachMain.fsupplierid = $('.addSchemeDesign').find("input[id=fsupplierid]").val();
	eachMain.ffirstproductid = $('.addSchemeDesign').find("input[id=ffirstproductid]").val();
	eachMain.fcreatorid = $('.addSchemeDesign').find("input[id=fcreatorid]").val();
	if( $('.addSchemeDesign').find("input[id=fcreatetime]")) eachMain.fcreatetime = $('.addSchemeDesign').find("input[id=fcreatetime]").val();
// 	eachInfo.length = 0;
	eachInfo=[];
	//构造传到后台的数据
	$.each($(".content"),function(index, item){
		var design = {};
		if($(item).find("input[name=fid]").val()){
			design.fid = $(item).find("input[name=fid]").val();
		}
		if($(item).find("input[name=fnumber]").val()){
			design.fnumber = $(item).find("input[name=fnumber]").val();
		}
		if($(item).find("input[name=fname]").val()){
			design.fname = $(item).find("input[name=fname]").val();
		}
		if($(item).find("textarea[name=fdescription]").val()){
			design.fdescription = $(item).find("textarea[name=fdescription]").val();
		}		
		if(design.hasOwnProperty('fid') && design.hasOwnProperty('fnumber') && design.hasOwnProperty('fname')){
			eachInfo.push(design);
		}
		else{
			flag = false;
		}
	});
	return flag;
}

function del(me){
	if(me){
		if($('.content').length>1){
			$(me).parent().remove();
		}else{
			$(me).parent().hide();
		}
		getHtmlBodyHeight();
	}
}

function addSchemeDesign(){
	
	if($('.content').is(":hidden")){
		$('.content').show();
	}else{
		var length = $('.content').length;
		var e = $($('.content')[0]).clone();
		e.find('input,textarea').each(function(){
			$(this).val('').text('');
		});
		$('.addSchemeDesign').append(e);		
		$.ajax({
			url: window.getBasePath()+"/schemedesign/getDesignID.net",
			async: false, 
			dataType:"json",
			//data: {},
			//type: "post",
			success: function(data){
				if(data.success){
					$.each($(".content"),function(index, item){
						//批量新增时，用于重新赋值，防止图片上传时候id凌乱
						if(index == length){
							//把文件列表清空
							$(item).find("#thelist").css('display','none');
							$(item).find("#thelist").find(".infoafter").remove();
							$(item).find("input[name=fid]").val(data.data.fid);	
							$(item).find("input[name=fnumber]").val(data.data.fnumber);	
							//保证上传控件定位到新增的方案处
							uploadFile($(item));
						}						
					});					
				}
			}
		});
	}
	
}
//-----------------------------------------------------------------------需求报方案js------------------------------------------------------

//添加产品
function addProduct(me){
	if(me){
		if($('.childProduct').is(':hidden')){
			$('.childProduct').show();
			addfloatOnlyEvents($('.childProduct'));
		}else{
			var length = $('.childProduct').length;
			var node =$($('.childProduct')[0]).clone(true);
			node.find('input,textarea').each(function(e,i){
				$(this).val('').text('');
			});
	 		$(me).parents('tr').next().find('td').eq(1).append(node);
	 		//新增的产品 增加上传控件
			$.each($(".childProduct"),function(index, item){
				if(index == length){
					uploadFile($(item));
				}
				});	
	 		addfloatOnlyEvents(node);
		}
		getHtmlBodyHeight();
	}
}
//删除产品
function delProduct(me){
	if(me){
		if($('.childProduct').length>1){
			$(me).parent().remove();
		}else{
			$(me).parent().hide();
		}
		getHtmlBodyHeight();
	}
}
//数字输入 +-
function addfloatOnlyEvents(obj){
	 $(obj).find('.num input').keyup(function(){
		 $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
	 }).bind("paste",function(){
		 $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
	 }).css("ime-mode", "disabled");
	 $(obj).find('.num input').next().click(function(){
		 $(this).prev().val(eval($(this).prev().val()||0)+1);
	 });
	 $(obj).find('.num input').prev().click(function(){
		 if($(this).next().val()!=0){
			 $(this).next().val(eval($(this).next().val())-1);
		 }
	 });
};
//删除订单
function delOrder(me){
	if(me){
		var div = $(me).parents('table').parent();
		if($('.'+div[0].className).length>1){
			div.remove();
		}else{
			div.hide();
		}
		getHtmlBodyHeight();
	}
	
}
//计算数量总和
function sum(me){
	if(me){
		var sum = 0;
		var table = $(me).parents('table')[0];
		$.each(table.rows,function(i,r){
			$.each(r.cells,function(j,c){
				if(j==2){//数量列
					sum +=parseInt($.isEmptyObject($(c).find('input').val())?0:$(c).find('input').val());
				}
			});
		});
		$(table.rows[table.rows.length-1].cells[2]).text("合计："+sum);
	}
}
//添加产品特性
function addCharacter(me){
	if(me){
		var tr = '<tr class="trBorder" onmousemove="showDel(this)" onmouseout="hideDel(this)">'+
					'<td><input type="text" class="editTd" name="fpurchasenumber"/></td>'+
					'<td><input type="text" class="editTd" name="fcharacter"/></td>'+
					'<td><input type="text" class="editTd" onblur="sum(this)" name="fentryamount"/ ></td>'+
					'<td class="delTd"><a href="javascript:void(0);" class="delTr" onclick="delTr(this)">删除</a></td>'+
					'</tr>';
		
		$(tr).insertBefore($(me).parents('tr').next().find('.character tr')[$('.character')[0].rows.length-1]);
		window.addNumOnlyEvent2($($(me).parents('tr').next().find('.character tr')[$('.character')[0].rows.length-2]).find("[name='fentryamount']"));
		getHtmlBodyHeight();
	}
}
//显示删除按钮
function showDel(me){
	if(me){
		$(me).find('a').show();
	}
}
//隐藏删除按钮
function hideDel(me){
	if(me){
		$(me).find('a').hide();
	}
}
//删除特性分录行
function delTr(me){
	if(me){
		//删除前先记录table  否则parent不到
		var sum = 0;
		var table = $(me).parents('table')[0];
		$($(me).parents('tr')[0]).remove();	
		$.each(table.rows,function(i,r){
			$.each(r.cells,function(j,c){
				if(j==2){//数量列
					sum +=parseInt($.isEmptyObject($(c).find('input').val())?0:$(c).find('input').val());
				}
			});
		});
		$(table.rows[table.rows.length-1].cells[2]).text("合计："+sum);
		getHtmlBodyHeight();
	}
}

function isNullOrEmpty(){
	 var bool = true;
	 $('input[data-required]:visible').each(function(index,ev){
	  if($(ev).data('required')){
	   if($.isEmptyObject($(this).val())){
	   if($(this).attr("class") != "date"){
		   $(ev).focus();
	    }
	    layer.tips('请输入内容！', $(ev));
	    bool = false;
	    return bool;
	   }
	  }
	 });
	 return bool;
	}

function checkXqbData(){
	var flag = true;
	//方案对象 var scInfo = {}; 方案集合  var scInfoArr = []; 方案特性对象  var scentryInfo = {}; 方案特性对象数组 var scentryInfoArr = []; 产品对象  var pdtInfo = {}; 产品对象数组var pdtInfoArr = [];
	if(!isNullOrEmpty()){
		return false;
	}
	scInfoArr = [];
	//构造传到后台的数据     有特性的方案（一次性产品）
	$.each($(".a:visible"),function(index, item){	
		var scentryInfoArr = [];
		var scInfo = {};
		scInfo.fcustomerid = $('.addSchemeDesign').find("input[id=fcustomerid]").val();
		scInfo.fsupplierid = $('.addSchemeDesign').find("input[id=fsupplierid]").val();
		scInfo.ffirstproductid = $('.addSchemeDesign').find("input[id=ffirstproductid]").val();	
		scInfo.fcreatorid = $('.addSchemeDesign').find("input[id=fcreatorid]").val();
		if( $('.addSchemeDesign').find("input[id=fcreatetime]")) scInfo.fcreatetime = $('.addSchemeDesign').find("input[id=fcreatetime]").val();

		var scInfoMain = {};
		
		$.each($(item).find("input"),function(i,ev){
			var clstype = $(ev).parents('table')[0].className;
			if(clstype && clstype=="scinfo"){				
				scInfo[ev.name] = ev.value; 
			}
		});
		//方案描述是textarea 所以要另外构造
		$.each($(item).find("textarea"),function(i,ev){
			var clstype = $(ev).parents('table')[0].className;
			if(clstype && clstype=="scinfo"){				
				scInfo[ev.name] = ev.value; 
			}
		});
		//构造特性分录
		$.each($(item).find(".character").find(".trBorder"),function(cevindex,cev){
			var scentryInfo = {};
			scentryInfo.fparentid = scInfo.fid;
			var isScentryInfoEmpty = true;
			$.each($(cev).find("input"),function(i,ev){
				scentryInfo[ev.name] = ev.value;
				if(!$.isEmptyObject(ev.value)){
					isScentryInfoEmpty = false;
				}
			});
			
			if(!$.isEmptyObject(scentryInfo) && !isScentryInfoEmpty){
				if(!$.isEmptyObject(scentryInfo.fentryamount) && !$.isEmptyObject(scentryInfo.fcharacter)){
					scentryInfoArr.push(scentryInfo);
				}
				else if(scentryInfoArr.length == 0){
					if($.isEmptyObject(scentryInfo.fentryamount)){
						var fentryamount = $(cev).find("input[name='fentryamount']");
						fentryamount.focus();
						layer.tips('请输入内容！', fentryamount);
					} else if($.isEmptyObject(scentryInfo.fcharacter)){
						var fcharacter = $(cev).find("input[name='fcharacter']");
						fcharacter.focus();
						layer.tips('请输入内容！', fcharacter);
					}
					flag = false;
					return;
				}
			}
		});
		
		scInfoMain.scInfo = scInfo;
		if(scentryInfoArr.length == 0){
			var scentryTbody = $(item).find(".character").find("tbody");
			scentryTbody.focus();
			layer.tips('请至少输入一条产品特性！', scentryTbody);
			flag = false;
			return;
		}
		
		scInfoMain.scInfo = scInfo;
		scInfoMain.scentrys = scentryInfoArr;
		scInfoArr.push(scInfoMain);
	});
	
	//构造传到后台的数据     有产品的方案（循环产品）
	$.each($(".b:visible"),function(index, item){	
		var pdtInfoArr = [];
		var scInfo = {};
		scInfo.fcustomerid = $('.addSchemeDesign').find("input[id=fcustomerid]").val();
		scInfo.fsupplierid = $('.addSchemeDesign').find("input[id=fsupplierid]").val();
		scInfo.ffirstproductid = $('.addSchemeDesign').find("input[id=ffirstproductid]").val();	
		scInfo.fcreatorid = $('.addSchemeDesign').find("input[id=fcreatorid]").val();
		if( $('.addSchemeDesign').find("input[id=fcreatetime]")) scInfo.fcreatetime = $('.addSchemeDesign').find("input[id=fcreatetime]").val();
		scInfo.fid = $(item).find("input[name=fid]").val();
		scInfo.fname = $(item).find("input[name=fname]").val();
		scInfo.fdescription = $(item).find("textarea[id=fdescription]").val();	
		scInfo.fnumber = $(item).find("input[name=fnumber]").val();
		var scInfoMain = {};
		var pdtinfo = {};
		$.each($(item).find(".parentProduct:visible").find("input"),function(i,ev){				
			pdtinfo[ev.name] = ev.value; 
		});
		if(!$.isEmptyObject(pdtinfo)){
			pdtinfo.schemedesignid = scInfo.fid;
			pdtInfoArr.push(pdtinfo);
		}
		//pdtinfo清空重新构造 子产品
		$.each($(item).find(".childProduct:visible"),function(i,ev){
			pdtinfo = {};
			//pdtinfo.schemedesignid = scInfo.fid;
			$.each($(ev).find("input"),function(index,item){
				pdtinfo[item.name] = item.value; 
			});
			if(!$.isEmptyObject(pdtinfo)){
			pdtInfoArr.push(pdtinfo);
		}
		});	
		scInfoMain.scInfo = scInfo;
		scInfoMain.pdtentys = pdtInfoArr;
		scInfoArr.push(scInfoMain);
	});
	//console.log(scInfo);
	return flag;
}

/*************************************************************上传插件构造******************************************************/
function uploadFile(content){
	 var $list = content.find("#thelist").first();
	 var $pick=  content.find("#picker");
	 var fileId = content.find("input[name=fid]").val();	
	 var uploader = WebUploader.create({
	       swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
	       server: window.getBasePath()+'/productfilenol/uploadImg.net?fid='+fileId,  // 文件接收服务端。
	       pick: $pick,
	       fileSingleSizeLimit: 10485760,
	       auto : true //设置为 true后,不需要手动调用上传,有文件选择即开始上传
	    });
	    /*** 当文件被加入队列以后触发*/
	 uploader.on( 'fileQueued', function( file ) {
	     $list.append( '<div id="' + file.id + '" class="item">'+
	          '<p class="info" style="overflow:visible;"><a class="filename" style="color:#000;text-decoration:none;" target="_blank">' + file.name + '</a><a id="' + file.id + '"  class="delfile" href="javascript:void(0);">&times;</a></p>'+
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
		 $list.css('display','');
	     $( '#'+file.id ).find('p.state').text('已上传');
	     $( '#'+file.id ).find('.delfile').attr('id',response._raw);
	     $( '#'+file.id +' .filename').attr('href',ROOT_PATH+'/productfile/getFileSource2.net?fid='+response._raw);
	     $( '#'+file.id ).attr("class","itemafter");
	     $( '#'+file.id ).find('p').first().attr("class","infoafter").mouseover(function(){
	      $(this).find('a.delfile').css('display','inline-block');
	      var aId = $(this).find('a.delfile').attr('id');
	      $('#'+aId).unbind().click( function () {
	        $.ajax({
	       type : "POST",
	       url :  window.getBasePath()+"/productfilenol/deleteImg.net",
	       dataType:"text",
	       async:false,
	       data: {"fid":aId},  
	       success : function(response) {
	        if(response =="success"){
	          var divId =$("#"+aId).parent("p").parent("div");
	          //队列中同时删除文件
	          if(uploader.getFile(divId.attr("id"))){
	        	  uploader.removeFile(divId.attr('id'),true);
	          }
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
	     });
	     $('.infoafter').mouseout(function(){
	      $(this).find('a.delfile').css('display','none');
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
	}

