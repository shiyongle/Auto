<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增工厂管理</title>

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
    $("#saveFactoryButton").click(function() {
        if ($('#flogitude').val() == '' || $('#flatitude').val() == '') {
            $.messager.alert('提示', '获取详细地址请点击地图');
            return;
        }
        if ($("#createFactoryForm").form("validate")) {
            var params = decodeURIComponent($("#createFactoryForm").serialize(), true);
            $.ajax({
                type: "POST",
                dataType: 'JSON',
                url: "${ctx}/factory/save.do",
                data: params,
                success: function(response) {
                    if (response.success == "success") {
                        $.messager.alert('提示', '操作成功', 'info',
                        function() {
                            $("#createWindow").window("close");
                            $("#factoryTable").datagrid('reload');
                        });
                    } else {
                        $.messager.alert('提示', "操作失败！");
                    }
                }
            });
        }
    });
    //地图加载
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [120.7, 28.0],
        zoom: 10
    });
    //输入提示
    var autoOptions = {
        input: "tipinput"
    };
    var auto = new AMap.Autocomplete(autoOptions);
    var placeSearch = new AMap.PlaceSearch({
        map: map
    }); //构造地点查询类
    AMap.event.addListener(auto, "select", select); //注册监听，当选中某条记录时会触发
    //提示款下拉事件
    function select(e) {
        placeSearch.setCity(e.poi.adcode);
        placeSearch.search(e.poi.name); //关键字查询查询
    }
    //地图绑定点击事件
    var lnglatXY;
    var marker = [];
    map.on('click',
    function(e) {
        if (marker != null) {
            map.remove(marker);
        }

        $('#flogitude').val(e.lnglat.getLng());
        $('#flatitude').val(e.lnglat.getLat());

        lnglatXY = [e.lnglat.getLng(), e.lnglat.getLat()];
        marker = new AMap.Marker({
            map: map,
            position: lnglatXY,
            icon: new AMap.Icon({
                size: new AMap.Size(40, 50),
                //图标大小
                image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png"
            })
        });
        regeocoder();
    });

    function regeocoder() { //逆地理编码
        var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "all"
        });
        geocoder.getAddress(lnglatXY,function(status, result) {
            //     	console.log(status);
            if (status === 'complete' && result.info === 'OK') {
                regeocoder_CallBack(result);
            }
        });

        map.setFitView();
    }
    //逆地理的回调函数
    function regeocoder_CallBack(data) {
        var address = data.regeocode.formattedAddress //返回地址描述
        $('#tipinput').val(address);
    }
    //
});
</script>
</head>
	<form id="createFactoryForm" method="post">
			<input name ="flongitude" id="flogitude" type="hidden">
			<input name ="flatitude" id="flatitude" type="hidden">
			<table >
				<tr>
					<td class="m-info-title" style="width:62px;"> 工厂名称<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="factory" name="factory" class="easyui-validatebox" data-options="required:true,missingMessage:'工厂名称必须填写！',validType:['maxLength[10]']"/>
					</td>
				 	<td class="m-info-title"> 联系人<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="flinkman" name="flinkman" class="easyui-validatebox" data-options="required:true,missingMessage:'联系人必须填写！',validType:['maxLength[10]']" />
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">联系电话<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 210px; height: 25px;" type="text" id="flinkphone" name="flinkphone" class="easyui-numberbox" data-options="required:true,missingMessage:'联系电话必须填写！',validType:['maxLength[15]']"/>
					</td>
				 	<td class="m-info-title">状态<span class="red">*</span>:</td>
					<td>
						<select id="fstatus" class="easyui-combobox" name="fstatus" style="width:213px; height:30px;" data-options="editable:false">
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
										<input id="tipinput" name ="faddress" class="easyui-validatebox" required="true" missingMessage="地址必须填写！" placeholder="请提示框提示为准！！" style="height: 25px; width:480px;"/>
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
			    		 <textarea name="fremark"  class="easyui-validatebox"  data-options="required:true,missingMessage:'备注必须填写！',validType:['maxLength[50]']" style="width:100%; height:50px; resize:none; outline:none;"></textarea>
			    	</td>
			    </tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveFactoryButton">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>