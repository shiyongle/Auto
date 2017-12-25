<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link href="${ctx}/pages/schemeDesign/css/schemeDesign_add.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/pages/schemeDesign/schemeDesign_add.js"></script>
<title>新增方案(需求包)</title>
<script type="text/javascript">
//方案集合
var scInfoArr = [];
$(document).ready(function(){
	uploadFile($('.a'));
	uploadFile($('.b'));
	uploadFile($('.parentProduct'));
	uploadFile($('.childProduct'));	
	getHtmlBodyHeight();
	
	window.addfloatOnlyEvent2($('[name="fboxlength"]'),1);
	window.addfloatOnlyEvent2($('[name="fboxwidth"]'),1);
	window.addfloatOnlyEvent2($('[name="fboxheight"]'),1);
	window.addNumOnlyEvent2($("[name='fentryamount']"));
	window.addNumOnlyEvent2($("[name='subProductAmount']"));
	
	//添加订单
	$('.titleTable img').click(function(){
		var divtype = $(this).data('type');
		if($('.'+divtype).is(":hidden")){
			$('.addSchemeDesign').append($('.'+divtype));			
			$('.'+divtype).show();
			
			getHtmlBodyHeight();
		}else{
			// 克隆一个classs 为“a”的元素 
			var length = $('.'+divtype).length;
			//true 表示 连同事件一起复制元素
			$('.addSchemeDesign').append($($('.'+divtype)[0]).clone(true));	
			
			$.ajax({
				url: "${ctx}/schemedesign/getDesignID.net",
				async: false, 
				dataType:"json",
				//data: {},
				//type: "post",
				success: function(data){
					if(data.success){
						$.each($("."+divtype),function(index, item){
							if(index == length){
								//批量新增时，用于重新赋值，防止图片上传时候id凌乱
								$(item).find("input[type='text']").each(function(index,ev){
									ev.value="";
								});
								$(item).find("input[name=fid]").val(data.data.fid);	
								$(item).find("input[name=fnumber]").first().val(data.data.fnumber);	
								$(item).find(".parentProduct").find("input[name=fid]").val(data.data.ppdtid);	
								$(item).find(".childProduct").find("input[name=fid]").val(data.data.pdtid);
								$(item).find("#farrivetime").attr("id", 'farrivetime'+ index);
								$(item).find(".for_date").attr("onclick", "WdatePicker({el:$dp.$('farrivetime"+ index +"'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 00:00:00',startDate:'%y-%M-%d 09:00:00',onpicked:function(){$(this).removeClass('WdateFmtErr');}})");
 								$(item).find("input[name=fdescription]").val('');	
								//保证上传控件定位到新增的方案处
								uploadFile($(item));
							}
							});						
						}			
					}
				});
			 getHtmlBodyHeight();
		}
	});
	
	/////提交
	$("#tj_Button").click(function(){		        	
			if(checkXqbData()){
				//加个阴影 防止重复保存		
				//layer.load(2, {shade: 0.1});
				scInfoArr = JSON.stringify(scInfoArr)
				var options = {  
	              url : "${ctx}/schemedesign/saveXqbjnfo.net",
	              //dataType:"json",
	              //后台返回的是字符串 必须 类型是text 否则一直进error中
	              dataType:"text",
	              type : "POST",   
	              data : {scInfoArr:scInfoArr},
	              success : function(msg) {
	                  if(msg == "success"){
	                  	layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									//加载列表界面
									var win= parent.$('#iframepage')[0].contentWindow;
									win.loadScheme(1,win.demanid);
									$(win.document).find("tr[data-fid="+win.demanid+"]").find("td.fstate").html("已设计");
									parent.layer.closeAll();
									//parent.$('#8f3b223239bbc3454aaf308e406a351b').click();
									//window.location.href="${ctx}/menuTree/center.net?menu=7a403c6ed40df9351325af3b5cfdce5r";
							});
	                  }else{
	                  	layer.alert(msg, function(alIndex){
									layer.close(alIndex);
							});
	                  }
	              },
	              error:function(msg){
	              	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
						});
	              }
	      };  
		  $.ajax(options);
		}
		else{
			/* layer.tips('方案名称、编号、不能为空！', '#fdescription', {
			    tips: [1, '#F7874E'],
			    time: 4000
			}); */
		}	
	});
	
//特殊性分录添加动态时间
// 	$('.character').find("input[name='fentryamount']").blur(sum);
// 	$('.delTr').click(delTr);
});
</script>
</head>
<body>
<div class="title">平台首页 > 我的业务 > 新增方案</div>
<div>
	<div class="hx">
			<img alt="" src="${ctx}/css/images/hx-l.png" class="hx_l"/> 
			<span class="hx_title">新增方案(需求包)</span>
			<img alt="" src="${ctx}/css/images/hx-r.png"  class="hx_r"/> 
	</div>
	<div class="addSchemeDesign">
		<table class="titleTable">
			<tr>
				<td width="135px"><div class="titleDiv"><span class="redLine">|</span>新增订单：</div></td>
				<td colspan="3" width="745px" class="imageTd">
					<div>
					<span>一次性产品：<image src="${ctx}/css/images/addSchemeDesign1.png"  data-type="a"/></span>
					<span>循环产品：<image src="${ctx}/css/images/addSchemeDesign1.png"  data-type="b"/></span>
					</div>
					<input type="hidden" id="fcustomerid" name="fcustomerid" value="${fcustomerid}"/>
					<input type="hidden" id="fsupplierid" name="fsupplierid" value="${fsupplierid}"/>
					<input type="hidden" id="ffirstproductid" name="ffirstproductid" value="${ffirstproductid}"/>
					<input type="hidden" id="fcreatorid" name="fcreatorid" value="${fcreatorid}"/>	
				</td>
			</tr>
		</table>
		<div class="a">
			<table class="scinfo">
				<thead><tr><td colspan="4"><span class="redLine">|</span>一次性订单：<a class="delOrder" onclick="delOrder(this)">删除模板</a></td></tr></thead>
				<tbody>
					<tr>
						<td width="135px"><span>订单名称：<input type="hidden" id="fid" name="fid" value="${fid}"/> </span></td>
						<td width="320px"><input type="text" name = "fname" value="${fname}" data-required="true"/></td>
						<td width="135px"><span>订单编号：</span></td>
						<td width="320px"><input type="text" name = "fnumber" value="${fnumber1}" data-required="true" readOnly="true"/></td>
					</tr>
					<tr>
						<td><span>订单描述：</span></td>
						<td colspan="3">
							<textarea id="fdescription" name="fdescription" maxlength="200"></textarea>
						</td>
					</tr>
					<tr>
						<td><span>添加附件：</span></td>
						<td colspan="3">
							<div id="uploader" class="wu-example">
							    <div class="btns">									    	
							        <div id="picker" ><img src="${ctx}/css/images/addSchemeDesign1.png" /></div>
							    </div>
							    <div id="thelist" class="uploader-list">
							    </div>
							</div>		
						</td>					</tr>
					<tr>
						<td colspan="4"><div style="border-top:1px dashed #a0a0a0;height: 1px;overflow:hidden"></div></td>
					</tr>
					<tr>
						<td><span>愣&nbsp;&nbsp;型：</span></td>
						<td><input type="text" name="ftilemodel"  data-required="true"/></td>
						<td><span>材&nbsp;&nbsp;料：</span></td>
						<td><input type="text" name="fmaterial" data-required="true"/></td>
					</tr>
					<tr>
						<td><span>规&nbsp;&nbsp;格：</span></td>
						<td  class="fspec">
							<input type="text" name="fboxlength" id="fboxlength" data-required="true" placeholder="长/mm" /><span>X</span>
							<input type="text" name="fboxwidth" id="fboxwidth" data-required="true" placeholder="宽/mm" /><span>X</span>
							<input type="text" name="fboxheight" id="fboxheight" data-required="true" placeholder="高/mm"/></td>
						<td><span>交&nbsp;&nbsp;期：</span></td>
						<td>
							<input type="text" name="foverdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 00:00:00',startDate:'%y-%M-%d 09:00:00',onpicked:function(){$(this).removeClass('WdateFmtErr');}})" id="farrivetime" class="date" data-required="true"/>
							<a href="javascript:void(0);" class="for_date" onclick="WdatePicker({el:$dp.$('farrivetime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 00:00:00',startDate:'%y-%M-%d 09:00:00',onpicked:function(){$(this).removeClass('WdateFmtErr');}})"><img src="${ctx}/css/images/sj.png"  style="width:30px; height:26px;"/></a>
						</td>
					</tr>
					<tr>
						<td><span>模板产品：</span></td>
						<td><input type="text" name="ftemplateproduct" data-required="true"/></td>
						<td><span>模板编号：</span></td>
						<td><input type="text" name="ftemplatenumber" data-required="true"/></td>
					</tr>
					<tr>
						<td><span>产品特性：</span></td>
						<td colspan="3"><img src="${ctx}/css/images/addSchemeDesign.png" onclick="addCharacter(this)"/></td>
					</tr>
					<tr>
						<td colspan="4">
							<table class="character" >
								<tr class="titleTr">
									<td width="250px">采购订单号</td>
									<td width="250px">特性</td>
									<td style="border-right:1px solid #bfbfbf;width:249px;">数量</td>
									<td style="display:none"></td>
								</tr>		
								<tr class="trBorder" onmousemove="showDel(this)" onmouseout="hideDel(this)">
						        	<td><input type="text" class="editTd" name="fpurchasenumber" /></td>
							        <td><input type="text" class="editTd" name="fcharacter" maxlength="100" /></td>
							        <td><input type="text" class="editTd" onblur="sum(this)" name="fentryamount" /></td>
							        <td class="delTd"><input type="hidden" id="entryid" name="entryid" value="${entryid}"/>
							        <a href="javascript:void(0);" class="delTr" onclick="delTr(this)">删除</a></td>
       							</tr>
								<tr>
									<td></td>
									<td></td>
									<td>合计：</td>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="b">
			<table>
				<thead><tr><td colspan="4"><span class="redLine">|</span>循环订单：<a class="delOrder" onclick="delOrder(this)">删除模板</a></td></tr></thead>
				<tbody>
					<tr>
						<td width="135px"><span>订单名称：</span><input type="hidden" id="fid" name="fid" value="${scfid}"/></td>
						<td width="320px"><input type="text" name = "fname" value="${fname}" data-required="true"/></td>
						<td width="135px"><span>订单编号：</span></td>
						<td width="320px"><input type="text" name="fnumber" value="${fnumber2}" data-required="true" readOnly="true"/></td>
					</tr>
					<tr>
						<td><span>订单描述：</span></td>
						<td colspan="3"><textarea id="fdescription" name="fdescription" maxlength="200"></textarea></td>
					</tr>
					<tr>
						<td><span>添加附件：</span></td>
						<td colspan="3">
							<div id="uploader" class="wu-example">
							    <div class="btns">									    	
							        <div id="picker" ><img src="${ctx}/css/images/addSchemeDesign1.png" /></div>
							    </div>
							    <div id="thelist" class="uploader-list">
							    </div>
							</div>		
						</td>	
					</tr>
					<tr>
						<td colspan="4"><div style="border-top:1px dashed #a0a0a0;height: 1px;overflow:hidden"></div></td>
					</tr>
					<tr>
						<td><span>新增产品：</span></td>
						<td colspan="3"><img src="${ctx}/css/images/addSchemeDesign.png" onclick="addProduct(this)"/></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3" class="addproduct">
							<div class="parentProduct">
								<table>
									<tr>
										<td><span>产品名称：</span><input type="hidden" id="fid" name="fid" value="${ppdtid}" data-required="true"/></td>
										<td><input type="text" name="fname"  data-required="true"/></td>
										<td><span>规&nbsp;&nbsp;格：</span></td>
										<td class="fspec"><input type="text" name="fboxlength" placeholder="长/mm" data-required="true"/><span>X</span>
										<input type="text"  name="fboxwidth" placeholder="宽/mm" data-required="true"/><span>X</span>
										<input type="text" name="fboxheight" placeholder="高/mm" data-required="true"/></td>
										<td><span>材&nbsp;&nbsp;料：</span></td>
										<td><input type="text" name="fmaterialcodeid" /></td>
									</tr>
									<tr>
										<td><span>产品编号：</span></td>
										<td colspan="5"><input type="text" name="fnumber" data-required="true"/></td>								
									</tr>
									<tr style="display:none;">
										<td>添加附件：</td>
										<td colspan="5">
												<div id="uploader" class="wu-example">
												    <div class="btns">									    	
												        <div id="picker" ><img src="${ctx}/css/images/addSchemeDesign1.png" /></div>
												    </div>
												    <div id="thelist" class="uploader-list">
												    </div>
												</div>		
										</td>											
									</tr>
								</table>
							</div>
							<div class="childProduct">
								<a href="javascript:void(0);" class="del" onclick="delProduct(this)">删除</a>
								<table>
									<tr>
										<td><span>产品名称：</span><input type="hidden" id="fid" name="fid" value="${pdtid}" data-required="true"/></td>
										<td><input type="text" name="fname"  data-required="true"/></td>
										<td><span>规&nbsp;&nbsp;格：</span></td>
										<td class="fspec"><input type="text" name="fboxlength" placeholder="长/mm" data-required="true"/><span>X</span>
										<input type="text"  name="fboxwidth" placeholder="宽/mm" data-required="true"/><span>X</span>
										<input type="text" name="fboxheight" placeholder="高/mm" data-required="true"/></td>
										<td><span>材&nbsp;&nbsp;料：</span></td>
										<td><input type="text" name="fmaterialcodeid" data-required="true"/></td>
									</tr>
									<tr>
										<td><span>产品编号：</span></td>
										<td><input type="text" name="fnumber" data-required="true"/></td>
										<td><span>附件数：</span></td>
										<td colspan="3" class="num unselect"><a>-</a><input type="text" name="subProductAmount" data-required="true"/><a>+</a></td>
									</tr>
									<tr style="display:none;">
										<td>添加附件：</td>
										<td colspan="5">
												<div id="uploader" class="wu-example">
												    <div class="btns">									    	
												        <div id="picker" ><img src="${ctx}/css/images/addSchemeDesign1.png" /></div>
												    </div>
												    <div id="thelist" class="uploader-list">
												    </div>
												</div>		
										</td>											
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="button">
<!-- 		<span class="save">确定</span> -->
<!-- 		<span class="back">返回</span> -->
		<input type="button" value="确定" class="_btn" id="tj_Button"  />
		<input type="button" value="返回" class="_btn" id="bc_Button" onclick="lastStep()"  />
</div>
</body>
</html>