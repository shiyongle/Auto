
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改工厂管理</title>
<style>
	.button-group {
		position: absolute;
		bottom: 20px;
		right: 20px;
		font-size: 12px;
		padding: 10px;
	}

	.button-group .button {
		height: 28px;
		line-height: 28px;
		background-color: #0D9BF2;
		color: #FFF;
		border: 0;
		outline: none;
		padding-left: 5px;
		padding-right: 5px;
		border-radius: 3px;
		margin-bottom: 4px;
		cursor: pointer;
	}
	.button-group .inputtext {
		height: 26px;
		line-height: 26px;
		border: 1px;
		outline: none;
		padding-left: 5px;
		padding-right: 5px;
		border-radius: 3px;
		margin-bottom: 4px;
		cursor: pointer;
	}
	#tip {
		background-color: #fff;
		padding-left: 10px;
		padding-right: 10px;
		position: absolute;
		font-size: 12px;
		right: 10px;
		top: 20px;
		border-radius: 3px;
		border: 1px solid #ccc;
		line-height: 30px;
	}
	.amap-info-content {
		font-size: 12px;
	}

	#myPageTop {
		position: relative;
		background: #fff none repeat scroll 0 0;
		font-family: "Microsoft Yahei", "微软雅黑", "Pinghei";
		font-size: 14px;
	}
	#myPageTop label {
		margin: 0 20px 0 0;
		color: #666666;
		font-weight: normal;
	}
	#myPageTop input {
		width: 170px;
	}
	#myPageTop .column2{
		padding-left: 25px;
	}
	#tooltip{
		width:200px;
	}
	.panel-noscroll .amap-sug-result{
		z-index:9999999999;
	}
</style>
<script type="text/javascript">
$(document).ready(function() {
    //fstatus
    var fstatus = "${factory.fstatus}";
    $('#fstatus').combobox({
        editable: false
    }).combobox('select', fstatus).combobox('disable');
    //地图加载
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: ["${factory.flongitude}", "${factory.flatitude}"],
        zoom: 15
    });
    var Marker = new AMap.Marker({
        map: map,
        position: ["${factory.flongitude}", "${factory.flatitude}"],
        icon: new AMap.Icon({
            size: new AMap.Size(40, 50),
            //图标大小
            image: "https://webapi.amap.com/theme/v1.3/markers/n/mark_r.png"
        })
    });
});
</script>
</head>
	<form id="viewFactoryForm" method="post">
			<table>
				<tr>
					<td class="m-info-title" style="width:62px;"> 工厂名称<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="factory" readonly="readonly"name="factory" class="easyui-validatebox"  required="true" missingMessage="工厂名称" value="${factory.factory}"/>
					</td>
				 	<td class="m-info-title"> 联系人<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="flinkman" name="flinkman" readonly="readonly" class="easyui-validatebox"  required="true" missingMessage="联系人必须填写！" value="${factory.flinkman}"/>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">联系电话<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="flinkphone" readonly="readonly" name="flinkphone" class="easyui-numberbox"  required="true" missingMessage="联系电话必须填写！" value="${factory.flinkphone}"/>
					</td>
				 	<td class="m-info-title">状态<span class="red">*</span>:</td>
					<td>
						<select id="fstatus" name="fstatus" style="width:213px; height:30px;">
						    <option value="1">启用</option>
						    <option value="2">停用</option>
						</select>
					</td>
			    </tr>
			    <tr>
			    	<td class="m-info-title">
			    		地址<span class="red">*</span>:
			    	</td>
			    	<td colspan="3" class="m-info-content">
			    		<div id="myPageTop">
							<table>
								<tr>
									<td>
										<input id="tipinput" readonly="readonly" name ="faddress" class="easyui-validatebox" required="true" missingMessage="地址必须填写！" placeholder="请提示框提示为准！！" style="height: 25px; width:480px;" value="${factory.faddress}"/>
									</td>
								</tr>
							</table>
						</div>
			    	</td>
			    </tr>
			    <tr>
			    	<td colspan="4">
			    		<div style="width:510px; height:250px; position:relative;border:3px solid #ccc; margin:5px 0px 5px 45px; ">
			    			<div id="container" style="width:100%; height:100%;"></div>
			    		</div>
			    	</td>
			    </tr>
			    <tr>
			    	<td class="m-info-title">
			    		备注<span class="red">*</span>:
			    	</td>
			    	<td colspan="3" class="m-info-content">
			    		 <textarea name="fremark" readonly="readonly" class="easyui-validatebox"  data-options="required:true,missingMessage:'备注必须填写！',validType:['maxLength[50]']" style="width:100%; height:50px; resize:none; outline:none;">${factory.fremark}</textarea>
			    	</td>
			    </tr>
			</table>
	</form>
</html>