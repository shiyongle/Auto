<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/vmi/extlib/resources/css/ext-all.css">
<!-- ext-all-gray.css \ext-all.css -->
<script type="text/javascript" src="/vmi/extlib/ext-all.js"></script>

<title>Insert title here</title>
<style type="text/css">
.align-center {
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
	width: 100%; /* 给个宽度 顶到浏览器的两边就看不出居中效果了 */
	height: 100%;
	background: white; /* 背景色 */
	text-align: center; /* 文字等内容居中 */
}
</style>
</head>
<script type="text/javascript" src="/vmi/js/MD5.js"></script>

<!---<script type="text/javascript" src="/vmi/js/test.js"></script>-->
<!---<script type="text/javascript" src="/vmi/js/LoginForm.js"></script>  -->

<body>
	<script type="text/javascript">
		function LoginForm() {
			Ext.define('mypanle1', {
				extend : 'Ext.panel.Panel',
				width : 100,
				flex : 1,
				title : 'panel1',
				html : 'flex 1'
			});
			Ext.define('mypanle2', {
				extend : 'Ext.panel.Panel',
				flex : 2,
				width : 100,
				title : 'panel2',
				html : 'flex:2'
			});
			Ext.define('mypanle3', {
				extend : 'Ext.panel.Panel',
				height : 100,
				width : 100,
				title : 'panel3',
				html : 'height:100'
			});
			//将3个panel放在一个弹出层中
			Ext.define('myWindow', {
				extend : 'Ext.window.Window',
				initComponent : function() {
					Ext.apply(this,
							{
								width : 300,
								height : 300,
								title : 'vbox布局',
								layout : 'vbox',
								items : [ Ext.create('mypanle1'),
										Ext.create('mypanle2'),
										Ext.create('mypanle3') ]
							}), this.callParent(arguments);
				}

			});
			var w = Ext.create('myWindow');
			w.show();
		}
		Ext.onReady(LoginForm);
		// private method for UTF-8 decoding
	</script>
	<div id="win" class="x-hidden"></div>

</body>

</html>