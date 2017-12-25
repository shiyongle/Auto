$(function(){
		window.getHtmlLoadingBeforeHeight();
		$("._submit").on('click',function(){
			loadData();
		});
		loadData();
		//下拉框
		/*var $num1=$("#selectList option").size();
		$("#selectList").selectlist({
			zIndex: $num1,
			width: 230,
			height: 27
		});
		$("#selectList li").click(function(){
	    	var value =$(this).attr("data-value");
	    	$("#supplier1").val(value);
	     	window.getHtmlLoadingBeforeHeight();
			loadData();
		});*/
			$("#selectList").jqxComboBox({
			    width: 200, 
			    height: 27,
			    animationType:'fade',
				autoDropDownHeight:false,
				keyboardSelection : false,//下拉框键盘上下不触发选择
				autoComplete:true,
			    searchMode:'contains'//模糊搜索,
			   });
			$("#selectList").on('open', function (event) {
				var owner= event.owner;
				if(owner.listBox.items.length<9)
				{
					$("#selectList").jqxComboBox({autoDropDownHeight: true});
				}
				});
			$('#selectList').on('select', function (event) {
            window.getHtmlLoadingBeforeHeight();
			loadData();
            });
            
           $('._del').on('click',function(event)
           {
           		var obj=getParamid();
           		delteRecord(obj);
           });
           $('._add').on('click',function(e){
        	   var customerid = $('#selectList').jqxComboBox('val');
        	   if(customerid){
        		   location.href = ROOT_PATH + '/productdef/productCreate.net?customerid='+customerid;
        	   }else{
        		   top.layer.msg('请先选择客户...');
        	   }
           });
           $("._excel").on('click',function()
           {
           	var i = layer.open({
           		skin:'execlskin',
			    title:'',move:false,
			    type: 2,
			    offset: '250px',
			    anim:true,
			    area: ['425px', '245px'],
			    content:  window.getRootPath()+"/productdef/execlproduct.net?fcustomerid="+$("#selectList").jqxComboBox('val')
			});
           });
});
function set_DeliverTimeConfig(){
 if("${session.user.fname}" !=""){
  var i = parent.layer.open({
      title:'',move:false,
      type: 2,
      anim:true,
      area: ['525px', '290px'],
      content: ROOT_PATH+"/productdef/DeliverTimeConfig.net"
  });
  parent.layer.style(i,{
   'border-radius':'15px'
  });
 }else{
  window.goToSmallLogin();
 }
}
function loadData(pageNo){
	pageNo = pageNo || 1;
	var params =$(".condition_form").serialize();
	var loadIndex = parent.layer.load(2);
	$("#kkpager").html("");
	$.ajax({
		url: '${ctx}/productdef/loadproduct.net?pageNo='+pageNo,
		data: params,
		type: 'post',
		dataType: 'json',
		success: function(res){
			var list = res.list;
			var template = ['<tr class="data_title dynamicTR">',
			                '<td colspan="7">',
			                '<span class="span1">客户:{{customerName}}</span>',
			                '<span class="span2">创建时间:{{fcreatetime}}</span>',
			                '<span class="span3" onclick="viewProduct(\'{{fid}}\')">详情</span>',
			                '<span class="span4">&nbsp;</span>',
			                '</td></tr>',
			 '<tr class="_data dynamicTR" data-fid={{fid}}> ',
			'<td><input type="checkbox" name="product" class="productcheck"/></td>',
			'<td><img src="'+ROOT_PATH+'/productfile/getFileSourceByParentId.net?fid={{fid}}" alt="1"   height="87" width="87" /></td>',
			'<td>{{productName}}</td>',
			'<td>{{productSpec}}</td>',
			'<td>{{fmaterial}}</td>',
			'<td class="remark">{{fdescription}}</td>',
			'<td class="buttonTR">',
		 	'<a href="javascript:void(0);" onclick="editProduct(\'{{fid}}\')">修改<br/></a>',
			'</td>',
			'</tr><tr class="dynamicTR" height="5px"></tr>'].join("");
			var html = getHtml(list,template);
			$('.dynamicTR').remove();
			if(html){
				$('.tbl').append(html);
				getPagination(res,'kkpager',loadData);
				afterEvent();
				window.getHtmlLoadingAfterHeight();
			}
			parent.layer.close(loadIndex); 
		}
	});
}
function editProduct(id){
	location.href = ROOT_PATH + '/productdef/productEdit.net?id='+id;
}
function viewProduct(id){
	location.href = ROOT_PATH + '/productdef/productEdit.net?view=true&id='+id;
}
function afterEvent(){
		
		  $('.span4').on('click',function(event)
           {
           		delteRecord($(this).parents("tr").next().data("fid"));
           });
           addAllCheckEvent($('#product'),$('.productcheck'),$("tr._data"));//复选框选择事件
          	}
function getParamid()
	{
		var fids="";
			$("input[name='product']:checked").parents("tr").each(function(index, e) {
				fids+=$(this).data("fid");
				if($('input[name=product]:checked').length-1>index){
				fids += ",";
				}
			});
			return fids;
	}
	
	function delteRecord(obj)
	{
           		if($.isEmptyObject(obj)){
				parent.layer.alert('请选择数据！');
				return false;
				}
           		$.ajax({
				url:ROOT_PATH+"/productdef/forbiddenproduct.net",
				type:"post",
				dataType:"json",
				data:{"id":obj},
				success:function(data){
					if(data.success ===true){
						   parent.layer.alert('操作成功！', function(index){
							loadData();
							parent.layer.close(index);
							});
					}else{
						parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'), function(index){parent.layer.close(index);});
					}
				},
				error:function (){
					parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
				}
			});
	}