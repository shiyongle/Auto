$(function() {
	wordName_check();
	wordDetial_check();
	slectType();//作品分类的数据读取
	selectAuthorize();//作品版权的数据读取
	window.getHtmlLoadingBeforeHeight();//子页面加载前文档高度
	window.getHtmlLoadingAfterHeight();//子页面加载后文档高度
	//点击删除缩略图的按钮显示隐藏
	$(".pro_img_list ul li").mouseover(function(){
		$(this).find('.close').show();
	}).mouseout(function(){
		$(this).find('.close').hide();
	});
	/*************************************************************上传插件构造******************************************************/
	var content = $('.publish_pro_right');
	uploadFile(content);		
});

//上传插件配置
function uploadFile(content){
	//var $list = content.find("#thelist").first();
	var $imglist = content.find('.pro_img_list ul');
	var $pick=  content.find("#picker");
	var fileId = content.find("#fid").val();	
	var uploader = WebUploader.create({
		swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
		server: window.getBasePath()+'/productfilenol/schemesuploadImg.net?fid='+fileId,  // 文件接收服务端。
		pick: $pick,
		auto : true, //设置为 true后,不需要手动调用上传,有文件选择即开始上传
		//单个文件大小限制，这个数字有待确认是否正确。
		fileSingleSizeLimit : 10485760,
		//总共上传图片的数量限制
		fileNumLimit : 100,
		//文件去重判断,true表示可以重复上传
		duplicate : true,
		// 只允许选择图片文件上传。
		accept : {
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},

		//缩略图配置
		thumb : {
			quality : 100
			//缩略图的质量不压缩
		}
	});
	// 当有文件添加进来的时候
	uploader.on('fileQueued',function(file) {
		var html = $('<li id="' + file.id + '"><img/><div class="close"></div><textarea type="text" class="pro_img_ms" placeholder="这里是图片描述......"></textarea></li>'), 
		$img = html.find('img');
		//上传的缩略图显示的位置
		$imglist.append(html);
		// 创建缩略图
		// 如果为非图片文件，可以不用调用此方法。
		// thumbnailWidth x thumbnailHeight 默认为 100 x 100
		uploader.makeThumb(file,function(error, src) {
			if (error) {
				$img.replaceWith('<span>图片暂时无法预览</span>');
				return;
			}
			$img.attr('src', src);}, 100, 80);
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
		$percent.css( 'width', percentage * 100 + '%' );
	});
	/*** 当文件上传成功时触发*/
	uploader.on( 'uploadSuccess', function( file ,response) {
		//点击删除缩略图的按钮显示隐藏
		$(".pro_img_list ul li").mouseover(function(){
			$(this).find('.close').show();
		}).mouseout(function(){
			$(this).find('.close').hide();
		});
		$( '#'+file.id ).children('img').attr('id',response._raw);
		var aId =  response._raw;
		$( '#'+file.id ).children('.close').click( function () {
			$.ajax({
				type : "POST",
				url :  window.getBasePath()+"/productfilenol/schemedeleteImg.net",
				dataType:"text",
				async:false,
				data: {"fid":aId},  
				success : function(response) {
					if(response =="success"){
						//队列中同时删除文件
						if(uploader.getFile(file.id)){
							uploader.removeFile(file.id,true);
						}
						$( '#'+file.id ).remove();
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

	/*** 页面加载进来的时候，数据库里读取的图片的缩略图的删除事件*/
	$(".pro_img_list ul li").mouseover(function(){
		$(this).find('.close').show();
		var aId =$(this).find('img').attr('id');
		$( '#'+aId).siblings('.close').click( function () {
			$.ajax({
				type : "POST",
				url :  window.getBasePath()+"/productfilenol/schemedeleteImg.net",
				dataType:"text",
				async:false,
				data: {"fid":aId},  
				success : function(response) {
					if(response =="success"){
						var divId =$("#"+aId).parent().attr("id");
						$('#'+divId ).remove();
					}else{
						layer.alert('操作失败！', function(index){layer.close(index);});
					}
				},
				error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
				}

			});
		});
	}).mouseout(function(){
		$(this).find('.close').hide();
	});

	/*** 不管成功或者失败，文件上传完成时触发*/
	uploader.on( 'uploadComplete', function( file ) {
		$( '#'+file.id ).find('.progress').fadeOut();
		window.getHtmlLoadingAfterHeight();//图片添加后的页面iframe高度设置
	});

	/*** 当validate不通过时，会以派送错误事件的形式通知调用者*/
	uploader.onError = function( type ) {
		if(type=='F_EXCEED_SIZE'){
			layer.msg("单个文件不允许超过10M！");
		}
	};
}


//作品名称字数判断
function wordName_check() {
	var maxLength = 50;
	var nameLength = $('#pro_name').val().length;
	if (nameLength > maxLength) {
		var word = $("#pro_name").val().substr(0, maxLength);//截取前50个字符
		$('#pro_name').val(word);
	} else {
		$('#pro_name_num').text(maxLength - nameLength);
	}
}

//作品说明字数判断
function wordDetial_check() {
	var maxLength = 200;
	var nameLength = $('#pro_detial').val().length;
	if (nameLength > maxLength) {
		var word = $("#pro_detial").val().substr(0, maxLength);//截取前50个字符
		$('#pro_detial').val(word);
	} else {
		$('#pro_detial_num').text(maxLength - nameLength);
	}
}

//表单提交前的校验
function checkForm(){
	if($("#pro_name").val()==""){
		layer.tips('作品名称不能为空！', '#pro_name', {
			tips: [1, '#F7874E'],
			time: 4000
		});
		$("#pro_name").focus();
		return false;
	}else if($("#pro_detial").val()==""){
		layer.tips('作品说明不能为空！', '#pro_detial', {
			tips: [1, '#F7874E'],
			time: 4000
		});
		$("#pro_detial").focus();
		return false;
	}    
	else{
		return true;
	}
}

//方案作品修改
function edit(obj){
	var Iid=$('.pro_img_list ul li img');
	var Ims=$('.pro_img_ms');
	var pid=new Array(),pms=new Array();
	var jsonArr=[];
	$(Iid).each(function(i,e){
		var id=$(e).attr("id");
		pid.push(id);
	});
	$(Ims).each(function(i,e){
		var ms=$(e).val();
		pms.push(ms);
	});
	for(i=0;i<pid.length;i++)
	{
		var add={"id":pid[i],"ms":pms[i]};
		jsonArr.push(add);
	}
	var jsonStr = JSON.stringify(jsonArr);

	if(checkForm()==true){
		var options = {  
				url : window.getBasePath()+"/designschemes/edit.net",
				dataType:"text",
				type : "POST",
				data : {"jsonStr":jsonStr},
				success : function(response) {
					if(response == "success"){
						layer.alert('修改成功！', function(alIndex){
							layer.close(alIndex);
							window.location.href=ROOT_PATH+"/pages/designschemes/product_list.jsp";
						});
					}else{
						layer.alert('修改失败！', function(alIndex){
							layer.close(alIndex);
						});
					}
				},
				error:function(){
					layer.alert('操作失败！', function(alIndex){
						layer.close(alIndex);
					});
				}
		};  
		$("#publish_pro").ajaxSubmit(options);//绑定页面中form表单的id
	}
}

//作品类别的数据加载判断
function slectType(){
	var selectType=$(".pro_type select option");
	var protype=$("#selectType").val();
	switch(protype){
	case "鞋类包装设计": $(selectType).eq(0).attr("selected","selected"); break;
	case "电气类包装设计": $(selectType).eq(1).attr("selected","selected"); break;
	case "锁具、烟具包装设计":$(selectType).eq(2).attr("selected","selected"); break;
	case "五金汽配类包装设计": $(selectType).eq(3).attr("selected","selected"); break;
	case "箱包类包装设计": $(selectType).eq(4).attr("selected","selected"); break;
	case "灯饰类包装设计":$(selectType).eq(5).attr("selected","selected"); break;
	case "文具类包装设计": $(selectType).eq(6).attr("selected","selected"); break;
	case "服装类包装设计": $(selectType).eq(7).attr("selected","selected"); break;
	}	
}


//作品版权的数据加载判断
function selectAuthorize(){
	var selectAuthorize=$(".pro_authorize select option");
	var proAuthorize=$("#selectAuthtype").val();
	switch(proAuthorize){
	case "0": $(selectAuthorize).eq(0).attr("selected","selected"); break;
	case "1": $(selectAuthorize).eq(1).attr("selected","selected"); break;
	case "2": $(selectAuthorize).eq(2).attr("selected","selected"); break;
	}	
}
