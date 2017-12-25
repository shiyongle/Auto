<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/designschemes/css/publish.css" />
<script type="text/javascript" language="javascript"
	src="${ctx}/js/jquery.form.js"></script>
<script type="text/javascript" language="javascript"
	src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/pages/designschemes/js/publish.js"></script>

<title>作品发布</title>
</head>
<body>

	<div id="container">
		<p class="p1">平台首页 > 用户中心 >作品管理</p>
		<div class="publish_main">
			<div class="publish_tip1">
				Hi,<span id="userName"></span>请确认您拥有该作品的版权；带有<font class="red">*</font>的项目都是必填的哦。
			</div>
			<div class="publish_form">
				<form id="publish_pro">

					<div class="pro_div1">
						<div class="publish_pro_left">
							作品名称<span class="red">*</span>
						</div>
						<div class="publish_pro_right">
							<input type="hidden" id="fid" name="fid" value="${fid}" /> <input
								type="text" id="pro_name" name="fname"
								onkeyup="wordName_check()" maxlength="50"><span
								class="pro_name_tip"> 还可以输入<font class="red"
								id="pro_name_num">50</font>字符/汉字
							</span>
						</div>
					</div>

					<div class="pro_div2">
						<div class="pro_div2_01">
							<div class="publish_pro_left">
								作品说明<span class="red">*</span>
							</div>
							<div class="publish_pro_right">
								<textarea id="pro_detial" name="fdescription"
									onkeyup="wordDetial_check()" maxlength="200"></textarea>
							</div>
						</div>
						<div class="pro_detial_tip">
							还可以输入<font class="red" id="pro_detial_num">200</font>字符/汉字
						</div>
					</div>

					<div class="pro_div3">
						<div class="publish_pro_left pro_div3_left">
							上传图片<span class="red">*</span>
						</div>
						<div class="publish_pro_right">
							<!--<a href="javascript:void(0)" class="pro_img_a" id="picker">添加图片</a>-->
							<div id="picker">添加图片</div>
							<div class="pro_img_tip">
								支持jpg/gif/png格式，RGB模式，单张（长<8000，宽<2000，大小<10M），最多上传100张图片，支持批量上传。不要在图片上放置设计师商业推广信息（案例信息可以放置）
							</div>
							<div id="fileList" class="uploader-list"></div>

							<div class="pro_img_list">
								<ul>
<!-- 									<li><img src="01.png"/><div class="close"></div> -->
<!-- 									<textarea type="text" class="pro_img_ms" placeholder="这里是图片描述......"></textarea> -->
<!-- 									</li> -->
<!-- 									<li><img src="01.png"/><div class="close"></div> -->
<!-- 									<textarea type="text" class="pro_img_ms" placeholder="这里是图片描述......"></textarea> -->
<!-- 									</li> -->
								</ul>
							</div>

							<div class="pro_img_upload">
								<a href="javascript:void(0)" class="pro_img_start"
									style="display: none">开始上传</a>
							</div>

						</div>
					</div>

					<div class="pro_div6">
						<div class="publish_pro_left pro_div3_left">
							作品类别<span class="red">*</span>
						</div>
						<div class="publish_pro_right">
							<div class="pro_type">
								<select name="ftype">
									<option value="低压电气">低压电气</option>
									<option value="汽摩配">汽摩配</option>
									<option value="鞋/服/箱包">鞋/服/箱包</option>
									<option value="家用电器">家用电器</option>
									<option value="电子电器">电子电器</option>
									<option value="五金制品业">五金制品业</option>
									<option value="设备制造">设备制造</option>
									<option value="家具">家具</option>
									<option value="文教用品及玩具">文教用品及玩具</option>
									<option value="食品与饮料">食品与饮料</option>
									<option value="健康、运动、娱乐器材">健康、运动、娱乐器材</option>
									<option value="其它">其它</option>
								</select>
							</div>
						</div>
					</div>


					<div class="pro_div4">
						<div class="publish_pro_left pro_div3_left">
							作品授权<span class="red">*</span>
						</div>
						<div class="publish_pro_right">
							<div class="pro_authorize">
								<select name='fauthtype'>
									<option value="0">禁止匿名转载；禁止商业使用；禁止个人使用。</option>
									<option value="1">禁止匿名转载；禁止商业使用。</option>
									<option value="2">不限制作品用途。</option>
								</select>
							</div>
						</div>

					</div>

					<div class="pro_div5">
						<a class="pro_img_a" href="javascript:void(0)" onclick="save(this)">确定</a>
						<a class="pro_img_a" style="margin-left: 50px;" href="javascript:void(0)" onclick="javascript :history.back(-1);">返回</a>
					</div>
				</form>
			</div>
		</div>

	</div>

</body>
</html>
