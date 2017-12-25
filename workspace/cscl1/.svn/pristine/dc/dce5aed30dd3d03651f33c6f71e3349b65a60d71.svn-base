<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增好运劵优惠活动</title>
<style>
	ul,li{
		margin:0px;
		padding:0px;
	}
	li{
		list-style:none;
	}
	h3{
		margin:0px;
		padding:0px;
	}
	p{
		margin:0px;
		padding:0px;
		width:100px;
		display:inline-block;
		word-break:break-all; 
	}
</style>
<script>
        $(function(){
            // 输入正确的值并锁定
            function inputSelect(val,id){
            	$.ajax({
            		url : "${ctx}/select/getAllCoupons.do",
            		type:'post',
            		success:function(res){
            			res = JSON.parse(res);
            			for(var i=0; i<res.length; i++){
            				if(res[i].optionName == val){
            					 $(id).combobox('select',res[i].optionId);
            					 break;
            				}
            			}
            		}
            	});
            }
            // 表单控制
            $('#useEnabledDateText').numberbox({
                required:true,
                missingMessage:'有效期多少天必须填写!' ,
                validType:['greaterZero','lessOneMillon'],
                precision:0
            }).numberbox('disable').numberbox('disableValidation');

            $('#useEnabledTimeText2,#useEnabledTimeText1').validatebox({
                required:true,
                missingMessage:'使用有效期必须填写！'
            });

            $('#orderMoneyText').numberbox({
                required:true,
                missingMessage:'订单金额满多少元必须填写！',
                validType:['greatZero','lessOneMillon']
            });/* .numberbox('disable').numberbox('disableValidation'); */
            
            
            $('#grantPeople').validatebox({
            });
           
            //业务类型
            $('#fbusinessType').combobox({
                required : true,
                missingMessage : "请选择业务类型",
                validType : "comboRequired",
                invalidMessage : "请选择业务类型",
                width:145,
                editable : false
            });
            // 车型
            $.ajax({
				url:'${ctx}/select/getAllCarType.do',
				type:'post',
				success:function(rec){
					var html = '';
					rec = JSON.parse(rec);
					for(var i=0; i<rec.length; i++){
						if(rec[i].optionId == -1){
							continue;
						}
						html+= '<input type="checkbox" class="carType"  data-value="'+rec[i].optionId+'">'+rec[i].optionName;
					}
					$('#carTypeBox').append(html);
					var carTypeCount = 0;
		            $('#allCarType').click(function(){
		                if($(this).is(':checked')){
		                    $('.carType').prop('checked',true);
		                    carTypeCount = $('.carType').length;
		                }else{
		                    $('.carType').prop('checked',false);
		                    carTypeCount=0;
		                }
		            });
		            $('.carType').click(function(){
		                if($(this).is(':checked')){
		                    carTypeCount++;
		                }else{
		                    carTypeCount--;
		                }
		                if(carTypeCount == $('.carType').length){
		                    $('#allCarType').prop('checked',true);
		                }else{
		                    $('#allCarType').prop('checked',false);
		                }
		            });
				}
			});
            

            //区域
            var areaCount = 0;
            $('#allArea').click(function(){
                if($(this).is(':checked')){
                    $('.area').prop('checked',true);
                    areaCount = $('.area').length;
                }else{
                    $('.area').prop('checked',false);
                    areaCount=0;
                }
            });
            $('.area').click(function(){
                if($(this).is(':checked')){
                    areaCount++;
                }else{
                    areaCount--;
                }
                if(areaCount == $('.area').length){
                    $('#allArea	').prop('checked',true);
                }else{
                    $('#allArea').prop('checked',false);
                }
            });
           
            //	生效时间 和有效时间
            for(var i = 0; i < $('.validCheck').size(); i++){  
                $('.validCheck').get(i).onclick = function(){  
                    for (var j = 0; j < $('.validCheck').size(); j++ ){  
                        if ($(this).val() != $('.validCheck').eq(j).val() && $('.validCheck').eq(j).prop('checked') == true){  
                            $('.validCheck').eq(j).prop('checked',false);  
                        }  
                    }  
                    if($('#tomorrowCheck').prop('checked') == true || $('#todayCheck').prop('checked') == true){
                        $('#useEnabledDateText').numberbox('enable').numberbox('enableValidation');
                        $('#useEnabledTimeText2,#useEnabledTimeText1').prop('disabled',true).validatebox('disableValidation').val('');
                    }else{
                        $('#useEnabledDateText').numberbox('disable').numberbox('disableValidation').numberbox('clear');
                        $('#useEnabledTimeText2,#useEnabledTimeText1').prop('disabled',false).validatebox('enableValidation');
                    }
                };
            }

			//发放人群弹窗
			$('#peopleWindow').window({
				title:'发放人群',
			    width:340,
			    height:240,
			    modal:true
			});
			$('#chargeWindow').window({
				title:'充值',
			    width:340,
			    height:260,
			    modal:true
			});
			$('#peopleRadio').click(function(){
				$('#chargeBox').html('');
			});
			$('#chargeRadio').click(function(){
				$('#peopleBox').html('');
			});
			$('#peopleWindow,#chargeWindow').window('close');
			//添加按钮
			$('#addPeople').click(function(){
				if($('#peopleRadio').is(':checked')){
					var html ='<table>\
								<tr>\
									<td style="font-size:14px; ">发放人群:</td>\
									<td>\
									<input type="text" id="grantPeople" style="width:140px;" readonly name="fexcelName">\
										<input type="file" style="display:none;" id="UploadFile">\
										<input type="hidden" id="UploadFileValue" >\
										<button class="easyui-linkbutton" id="Upload">上传excel</button>\
									</td>\
								</tr>\
								<tr>\
									<td style="font-size:14px; ">方案选择:</td>\
									<td>\
									   <ul id="peopleScheme">\
										<li>\
											<select id="peopleSchemeSelect0"  class="peopleSelect">\
												<option value="-1">请选择</option>\
										   </select>\
										   <button  class="easyui-linkbutton" id="addPeopleSchemeBtn" >继续添加</button>\
										</li>\
				                       </ul>\
									</td>\
								 </tr>\
							</table>\
							<div style="overflow:hidden; width:160px; margin:10px auto 0;">\
								<div class="Mbutton25 createButton" style="float:left;" id="cancelPeople">取消</div>\
								<div class="Mbutton25 createButton" style="float:right;" id="sumbitPeople">提交</div>\
							</div>';
					$('#peopleWindow').html(html);
					$('#peopleWindow').window('open');
					$('#cancelPeople').click(function(){
						$('#peopleWindow').window('close');
					});
		            //方案选择
		            var n = 0;
		            $('#peopleSchemeSelect0').combobox({
		            	required : true,
		    			url : "${ctx}/select/getAllCoupons.do",
		    			missingMessage : "请选择方案选择",
		    			validType :["comboxValidate['peopleSchemeSelect0']",'comboRequired'],
		    			width:145,
		    			editable : true,
		    			filter:searchItem,
		    			valueField: 'optionId',
		    			textField: 'optionName',
		    			panelHeight:200,
		                onChange:function(val){
		                	inputSelect(val,'#peopleSchemeSelect0');
		                }
		            });
		            // 继续添加按钮
		            $('#addPeopleSchemeBtn').click(function(){
		            	var canAdd = false;
		            	$('.peopleSelect').each(function(index,el){
		            		if($(el).combobox('isValid') == false){
		            			$.messager.alert('提示', "请先选择！",'info');
		            			canAdd = true;
		            		}
		            	});
		            	if(canAdd){
		            		return;
		            	};
		            	
		                n++;
		                var newElement = $('<li>\
		                                        <select class="peopleSelect" id="peopleSchemeSelect'+n+'">\
		                                            <option value="-1">请选择</option>\
		                                        </select>\
		                                        <button class="delPeopleScheme">删除</button>\
		                                    </li>');
		                $('#peopleScheme').append(newElement);
		                $('#peopleSchemeSelect'+n).combobox({
		                	required : true,
		        			url : "${ctx}/select/getAllCoupons.do",
		        			missingMessage : "请选择方案选择",
		        			validType :['comboboxValidate','comboRequired'],
		        			width:145,
		        			editable : true,
		        			filter:searchItem,
		        			valueField: 'optionId',
		        			textField: 'optionName',
		        			panelHeight:200,
		                    onChange:function(val){
		                    	inputSelect(val,'#peopleSchemeSelect'+n);
		                    }
		                });
		                //动态加载dom的时候需要重新解析
		                $.parser.parse(newElement);
		            });

		            // 上传按钮
		            $('#Upload').click(function(){
		            	$('#UploadFile').val('').click();
		            });
		             //file变化
		            var fileName = '';
		            var issueUser = '';
		            $('#UploadFile').change(function(){
		            	fileName = '';
		            	issueUser = '';
		            	$('#grantPeople').val('');
		            	$('#issueUser').val(''); 
		            	fileName = $('#UploadFile')[0].files[0].name;
		                var fileNameArray = fileName.split('.');
		                if(fileNameArray[fileNameArray.length - 1]!='xlsx' && fileNameArray[fileNameArray.length - 1]!='xls'){
		                    $.messager.alert('提示','请选择正确的文件格式！','info');
		                    return;
		                 }
		                 $('#UploadFileValue').val($('#UploadFile').val());
		                 var data=new FormData;
		                 data.append("file",$('#UploadFile')[0].files[0]);
		                 $.ajax({
		                     url:'${ctx}/couponsActivity/readExecl.do',
		                     type:'post',
		                     dataType:'json',
		                     data:data,
		                     contentType: false,
		                     processData: false,
		                     success:function(res){
		                        if(res.success == "true"){
		                        	$('#grantPeople').val(fileName);
		                            var phoneArr = res.data.map(function(item){
		                                return item.vmiUserPhone;
		                            });
		                            if(phoneArr.length==1){
		                            	issueUser = phoneArr[0];
		                            }else{
		                            	issueUser = phoneArr.join(',');
		                            }
		                        }else{
		                        	$('#grantPeople').val('重新上传！');
		                            $.messager.alert('提示',res.msg,'info');
		                        }
		                     }
		                 });
		            });
		            //删除
		            $("#peopleScheme").on("click", ".delPeopleScheme", function(){
		            	$(this).parent().remove(); 
		            });
		          //确定按钮sumbitPeople
					$('#sumbitPeople').click(function(){
						//判断的是否有重复的方案
						var peopleStr = '';
						$('.peopleSelect').each(function(index,el){
							if(index == $('.peopleSelect').length -1){
								peopleStr +=$(el).combobox('getText');
							}else{
								peopleStr +=$(el).combobox('getText')+',';
							}
		            	});
						//判断是否上传excel
						if(issueUser == ''){
							$.messager.alert('提示','请上传excel!','info');
							return;
						}
						//判断是否有未选择的项
						var canSubmit = false;
		            	$('.peopleSelect').each(function(index,el){
		            		if($(el).combobox('isValid') == false){
		            			$.messager.alert('提示', "清空未选择项！",'info');
		            			canSubmit = true;
		            		}
		            	});
		            	if(canSubmit){
		            		return;
		            	};
		            	var html2 = '';
		            	$('.peopleSelect').each(function(index,el){
		            		var thisValue = $(el).combobox('getValue');
		            		html2 += '<input class="peopleIds" value="'+thisValue+'" type="hidden">';
		            	});
						var html = '<li style="border:1px solid #ccc; margin-left:20px; padding:5px;">'+fileName+'<br>'+peopleStr+'<input type="hidden" name="fexcelName" value="'+fileName+'"><input id="issueUser" type="hidden" value="'+issueUser+'">'+html2+'</li>';
						$('#peopleBox').html(html);
						$('#peopleWindow').window('close');
					});
				}
			});
			$('#addCharge').click(function(){
				if($('#chargeRadio').is(':checked')){
					var html ='<table>\
								<tr>\
									<td style="font-size:14px; ">充值金额:</td>\
									<td><input type="text" id="chargeMoney" name="fdollars"> 元</td>\
								</tr>\
								<tr id="chargeSchemeSelectRow">\
									<td style="font-size:14px; ">方案选择:</td>\
									<td>\
									   <ul id="chargeScheme">\
										<li>\
											<select id="chargeSchemeSelect0" class="chargeSelect">\
												<option value="-1">请选择</option>\
										   </select>\
										   <button href="javascript:;"  id="addChargeSchemeBtn">继续添加</button>\
										</li>\
				                       </ul>\
									</td>\
								 </tr>\
							</table>\
							<div style="overflow:hidden; width:160px; margin:10px auto 0;">\
								<div class="Mbutton25 createButton" style="float:left;" id="cancelCharge">取消</div>\
								<div class="Mbutton25 createButton" style="float:right;" id="sumbitCharge">提交</div>\
							</div>';
					$('#chargeWindow').window('open');
					$('#chargeWindow').html(html);
					//cancelCharge取消按钮
					$('#cancelCharge').click(function(){
						$('#chargeWindow').window('close');
					});
					 $('#chargeSchemeSelect0').combobox({
		            	required : true,
		    			url : "${ctx}/select/getAllCoupons.do",
		    			missingMessage : "请选择方案选择",
		    			validType :'comboRequired',
		    			width:145,
		    			editable : true,
		    			filter:searchItem,
		    			valueField: 'optionId',
		    			textField: 'optionName',
		    			panelHeight:200,
		                onChange:function(val){
		                	inputSelect(val,'#chargeSchemeSelect0');
		                }
		            });
					// 继续添加按钮
					var n = 0;
			            $('#addChargeSchemeBtn').click(function(){
			            	var canAdd = false;
			            	$('.chargeSelect').each(function(index,el){
			            		if($(el).combobox('isValid') == false){
			            			$.messager.alert('提示', "请先选择！",'info');
			            			canAdd = true;
			            		}
			            	});
			            	if(canAdd){
			            		return;
			            	};
			                n++;
			                var newElement = $('<li>\
			                                        <select class="chargeSelect" id="chargeSchemeSelect'+n+'">\
			                                            <option value="-1">请选择</option>\
			                                        </select>\
			                                        <button class="delChargeScheme">删除</button>\
			                                    </li>');
			                $('#chargeScheme').append(newElement);
			                $('#chargeSchemeSelect'+n).combobox({
			                	required : true,
			        			url : "${ctx}/select/getAllCoupons.do",
			        			missingMessage : "请选择方案选择",
			        			validType :['comboboxValidate','comboRequired'],
			        			width:145,
			        			editable : true,
			        			filter:searchItem,
			        			valueField: 'optionId',
			        			textField: 'optionName',
			        			panelHeight:200,
			                    onChange:function(val){
			                    	inputSelect(val,'#chargeSchemeSelect'+n);
			                    }
			                });
			                //动态加载dom的时候需要重新解析
			                $.parser.parse(newElement);
			            });
			          //删除
			            $("#chargeScheme").on("click", ".delChargeScheme", function(){
			            	$(this).parent().remove(); 
			            });
					//充值金额
			        $('#chargeMoney').numberbox({
			          	required:true,
			           	missingMessage:'充值多少必须填写！',
			           	width:145,
			          	validType:['greatZero','lessOneMillon']
			       	});
			      //确定按钮sumbitPeople
					$('#sumbitCharge').click(function(){
						//判断的是否有重复的方案
						var chargeSelectIdStr = '';
						//选择方案
						var chargeStr = '';
						$('.chargeSelect').each(function(index,el){
							if(index == $('.chargeSelect').length -1){
								chargeSelectIdStr+=$(el).combobox('getValue');
								chargeStr +=$(el).combobox('getText');
							}else{
								chargeSelectIdStr+=$(el).combobox('getValue')+',';
								chargeStr +=$(el).combobox('getText')+',';
							}
		            	});
						
						//判断是否有未选择的项
						var canSubmit = false;
		            	$('.chargeSelect').each(function(index,el){
		            		if($(el).combobox('isValid') == false){
		            			$.messager.alert('提示', "清空未选择项！",'info');
		            			canSubmit = true;
		            		}
		            	});
		            	if(canSubmit){
		            		return;
		            	};
		            	//判断充值金额
		            	if(!$('#chargeMoney').numberbox('isValid')){
		            		$.messager.alert('提示', "检查你的充值金额！",'info',function(){
		            			$('#chargeMoney').focus();
		            		});
		            		return;
		            	}
		            	var chargeObj = {};
		            	chargeObj.ides = chargeSelectIdStr;
		            	chargeObj.dollars = $('#chargeMoney').val();
		            	chargeObj = JSON.stringify(chargeObj);
		            	var html ='<li style="border:1px solid #ccc; margin-left:20px; margin-bottom:5px; padding:5px;" >'+
		            					'<input class="topUpActivityString" type="hidden" value='+chargeObj+'>'+
		            					'<p>充值：'+$('#chargeMoney').val()+'元<span style="display:inline-block; width:100%;">'+chargeStr+'</span></p>'+
		            					'<button class="chargeObjDel easyui-linkbutton" style="margin-left:20px;">删除</button>'+
		            				'</li>';
		            	$('#chargeBox').append(html);	
						$('#chargeWindow').window('close');
						$("#chargeBox").on("click", ".chargeObjDel", function(){
			            	$(this).parent().remove(); 
			            });
					});
				}
			});
			
			
            // 提交按钮
            $('#sumbit').click(function(){
            	//车型按后端需要格式化 
                var fcarSpecIdString='';
                $('.carType:checked').each(function(index,el){
                	if(index == $('.carType:checked').length-1){
                		fcarSpecIdString+=$(el).data('value');
                	}else{
                		fcarSpecIdString+=$(el).data('value')+',';
                	}
                });
                //地区按后端需要格式化 
                var fareaString='';
                $('.area:checked').each(function(index,el){
                	if(index == $('.area:checked').length-1){
                		fareaString+=$(el).data('value');
                	}else{
                		fareaString+=$(el).data('value')+',';
                	}
                });
                if(fcarSpecIdString == ''){
                	$.messager.alert('提示', "车型必须选择！");
                	return;
                }
                if(fareaString == ''){
                	$.messager.alert('提示', "地区必须选择！");
                	return;
                }
                var params = decodeURIComponent($("#luckActivityForm").serialize(), true);
                // 根据选择来获取data
                var data = '';
                if($('#peopleRadio').is(':checked')){
                	if($('#peopleBox').html().trim() == ''){
                		$.messager.alert('提示', "请填写发放人群相关信息！");
                    	return;
                	}
                	// 获取ids
                	var paramStr = '';
                	$('.peopleIds').each(function(index,el){
                		paramStr += "&ids=" + $(el).val();
                	});
                	data = params+'&farea='+fareaString+'&fcarSpecId='+fcarSpecIdString+'&fissueUser='+$('#issueUser').val()+paramStr;
                }else{
                	if($('#chargeBox').html().trim() == ''){
                		$.messager.alert('提示', "请填写充值相关信息！");
                    	return;
                	}
                	var chargeParamArr = [];
                	$('.topUpActivityString').each(function(index,el){
                		chargeParamArr.push(JSON.parse($(el).val()));
                	});
                	data = params+'&farea='+fareaString+'&fcarSpecId='+fcarSpecIdString+'&topUpActivityString='+JSON.stringify(chargeParamArr);
                }
                if($('#luckActivityForm').form('validate')){
                	$.messager.confirm('提示', '确定要提交吗？', function(r){
                		if(r){
		                    $.ajax({
		                        type:'POST',
		                        url:'${ctx}/couponsActivity/save.do',
		                        dataType:'json',
		                        data:data,
		                        success:function(res){
		                            if(res.success == 'success'){
		                               	$.messager.alert('提示','添加成功','info',function(){
			                            	$("#createWindow").window('close');
										    $("#luckTicketServerTable").datagrid('reload');
		                            	});
		                            }else{
		                                $.messager.alert('提示', res.msg,'info');
		                            }
		                        }
		                    });
                		}
                	});
                }
            });

            // 取消按钮
            $('#cancel').click(function(){
                $('#createWindow').window('close');
            });
            // 新增下拉款验证
            $.extend($.fn.validatebox.defaults.rules, {
            	comboboxValidate: {
        			validator: function(value, param) {
        	            var bFlag = false;
        				$.ajax({
                    		url : "${ctx}/select/getAllCoupons.do",
                    		type:'post',
                    		async: false,
                    		success:function(res){
                    			data = JSON.parse(res);
                    			for (var i = 0; i < data.length; i++) {
                	                if (data[i].optionName == value) {
                	                    bFlag = true;
                	                    break;
                	                }
                	            }
                    		}
                    	});
        	            return bFlag;
        	        },
        	        message: "输入正确的值"
        	    }
            });
            
        })
    </script>
</head>
    <div style="width:565px; overflow-y:scroll; height:400px; padding:10px;" >
        <form id="luckActivityForm">
            <fieldset>
                <legend><span style="font-size:16px; ">优惠券活动</span><span style="color:red;">(以下允许选择多个方法)</span></legend>
                <h3>前置条件</h3>
                <div style="border:1px solid #6c6c6c; padding:10px 0; margin-bottom:10px;">
                	<table style="width:100%; padding:0 10px;">
						<tr>
							<td>
								<input type="radio" name="factivityType" id="peopleRadio" checked value="0">发放人群
							<td>
							<td>
								<input type="radio" name="factivityType" id="chargeRadio" value="1">充值
							<td>
						</tr>
						<tr>
							<td  style="width:200px;">
								<ul id="peopleBox">
								</ul>
							<td>
							<td  style="width:200px;">
								<ul id="chargeBox">
								</ul>
							<td>
						</tr>
						<tr>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" id="addPeople" >添加</a>
							<td>
							<td>
								<a href="javascript:;" class="easyui-linkbutton" id="addCharge" >添加</a>
							<td>
						</tr>
	                </table>
                </div>
                <h3>活动选择</h3>
                <div style="border:1px solid #6c6c6c; padding:10px 0;">
                	<table style="padding:0 10px;">
	                    <tr>
	                        <td style="font-size:14px; ">业务类型:</td>
	                        <td>
	                            <select name="fbusinessType" id="fbusinessType">
	                                <option value="-1">请选择</option>
	                                <option value="一路好运">一路好运</option>
	                            </select>
	                        </td>
	                        <td></td>
	                    </tr>
	                    <tr>
	                        <td style="font-size:14px; ">活动日期:</td>
	                        <td><input type="text" id="factivityStartTime" class="easyui-validatebox datePicker MtimeInput" name="activityStartTime" data-options="required:true,missingMessage:'活动日期必须填写！'" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'factivityEndTime\')}'})"style="width:140px;"></td>
							<td>至<input type="text" class="easyui-validatebox MtimeInput" id="factivityEndTime" name="activityEndTime" data-options="required:true,missingMessage:'活动日期必须填写！'" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'factivityStartTime\')}'})" style="width:140px;"></td>
	                    </tr>
	                </table>
                </div>
            </fieldset>
            <fieldset>
                <legend><span style="font-size:16px; ">使用条件</span><span style="color:red;">(以下允许选择多个方案)</span></legend>
                <table style="padding:10px;">
                    <tr>
                        <td style="font-size:14px; ">使用时间</td>
                        <td style="font-size:14px;">生效时间:</td>
                        <td>
                            <input type="checkbox" class="validCheck" id="tomorrowCheck" value="1"  name="fgetType">领取后次日生效
                            <input type="checkbox" class="validCheck" id="todayCheck" value="2" name="fgetType">领取后即时生效&nbsp;&nbsp;
                            有效期<input type="text" style="width:50px;" id="useEnabledDateText" name="fuseTime">天
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">生效时间:</td>
                        <td>
                            <span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>使用有效期
                            <input type="text" class="easyui-validatebox datePicker MtimeInput"  style="width:100px;" id="useEnabledTimeText1" name="useStartTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:ss',maxDate:'#F{$dp.$D(\'useEnabledTimeText2\')}'})">
                            至<input type="text"  class="easyui-validatebox MtimeInput"  id="useEnabledTimeText2" style="width:100px;" name="useEndTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'useEnabledTimeText1\')}'})">
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size:14px; ">订单条件</td>
                        <td style="font-size:14px;">订单金额:</td>
                        <td>
                            <!-- <input type="checkbox" id="orderMoneyRadio"> -->订单金额满
                            <input type="text" id="orderMoneyText" name="fdollars"> 元
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">车<span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>型:</td>
                        <td id="carTypeBox">
                            <input type="checkbox" id="allCarType"><span style=" ">全车型</span>
                            <!-- <input type="checkbox" class="carType" name="fcarSpecId">5.2米
                            <input type="checkbox" class="carType" name="fcarSpecId">7.7米
                            <input type="checkbox" class="carType" name="fcarSpecId">4.2米货车
                            <input type="checkbox" class="carType" name="fcarSpecId">6.8米货车 -->
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">区<span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>域:</td>
                        <td>
                            <input type="checkbox" id="allArea"><span style=" ">全区域</span>
                            <input type="checkbox" class="area"  data-value="鹿城区">鹿城区
                            <input type="checkbox" class="area"  data-value="龙湾区">龙湾区
                            <input type="checkbox" class="area"  data-value="瓯海区">瓯海区
                            <input type="checkbox" class="area"  data-value="洞头区">洞头区
                            <input type="checkbox" class="area"  data-value="永嘉县">永嘉县<br>
                            <input type="checkbox" class="area"  data-value="平阳县">平阳县
                            <input type="checkbox" class="area"  data-value="苍南县">苍南县
                            <input type="checkbox" class="area"  data-value="文成县">文成县
                            <input type="checkbox" class="area"  data-value="泰顺县">泰顺县
                            <input type="checkbox" class="area"  data-value="瑞安市">瑞安市
                            <input type="checkbox" class="area"  data-value="乐清市">乐清市
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <div style="overflow:hidden; width:160px; margin:10px auto 0;">
            <div  class="Mbutton25 createButton" style="float:left;" id="cancel">取消</div>
            <div  class="Mbutton25 createButton" style="float:right;" id="sumbit">提交</div>
        </div>
		<div id="peopleWindow" style="margin: 0 auto; overflow-y:scroll; overflow-x:hidden; padding:10px 0" >
		</div>
		<div id="chargeWindow" style="margin: 0 auto; overflow-y:scroll; overflow-x:hidden; padding:10px 0;">
		</div>
    </div>
</html>