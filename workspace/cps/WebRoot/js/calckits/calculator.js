$(document).ready(function() {
	// bootstrap引入后 layer的圆角样式失效
	// var mylayer=parent.document.body.querySelector(".layui-layer-iframe
	// .layui-layer-content");
	// mylayer.setAttribute("style","border-radius:10px");

	var i, j;
	var ctt = "",  st = "";
	var a = "", b = "", c = ""; // 输入值
	var a1, b1, c1; // 内径
	var a2, b2, c2; // 制径
	var a3, b3, c3; // 外径
	var arr = [ 
	            [ 'BCC', '13', '13', '20', '7', '7', '20', '20', '20', '40', '50', '10' ], 
	            [ 'EBC', '10', '10', '17', '6', '6', '15', '16', '16', '32', '50', '8' ], 
	            [ 'BC', '8', '8', '13', '6', '6', '12', '14', '14', '25', '40', '6' ], 
	            [ 'BE', '6', '6', '9', '4', '4', '6', '10', '10', '15', '35', '4' ], 
	            [ 'C', '5', '5', '7', '3', '3', '5', '8', '8', '12', '30', '2' ], 
	            [ 'B', '3', '3', '6', '3', '3', '3', '6', '6', '9', '30', '2' ], 
	            [ 'E', '2', '2', '4', '2', '2', '2', '4', '4', '6', '30', '0' ] 
	            ];
	ctt = $("#changetype").val();// 获得瓦楞类型
	st = $("#changesize").val();// 获得尺寸类型
	if (ctt != "" && st != "") {
		jisuan();
	}

	$("#changetype,#changesize").change(function() {
		ctt = $("#changetype").val();// 获得瓦楞类型
		st = $("#changesize").val();// 获得尺寸类型
		jisuan();
	});

	$("#changetype,#changesize").change(function() {
		if (a != "" || b != "" || c != "") {
			for (i = 0; i < 7; i++) {
				if (ctt == arr[i][0]) {
					var i1 = arr[i][1];
					var i2 = arr[i][2];
					var i3 = arr[i][3];
					var i4 = arr[i][4];
					var i5 = arr[i][5];
					var i6 = arr[i][6];
					var i7 = arr[i][7];
					var i8 = arr[i][8];
					var i9 = arr[i][9];
					var i10 = arr[i][10];
					var i11 = arr[i][11];
					if (st == "内径") {
						neijing(i1, i2, i3, i7, i8, i9);
					}
					if (st == "制径") {
						zhijing(i1, i2, i3, i4, i5, i6);
					}
					if (st == "外径") {
						waijing(i3, i4, i5, i6, i7, i8, i9);
					}
				}
			}
		}
	});
	//内径
	function neijing(i1, i2, i3, i7, i8, i9) {
		a = $('#a').val(); // 获得用户输入的长宽高
		a1 = a;
		$('#a1').val(a1); // 生成内径的长宽高
		a2 = Number(a) + Number(i1);
		$('#a2').val(a2);
		a3 = Number(a) + Number(i7);
		$('#a3').val(a3);

		b = $('#b').val();
		b1 = b;
		$('#b1').val(b1);
		b2 = Number(b) + Number(i2);
		$('#b2').val(b2);
		b3 = Number(b) + Number(i8);
		$('#b3').val(b3);

		c = $('#c').val();
		c1 = c;
		$('#c1').val(c1);
		c2 = Number(c) + Number(i3);
		$('#c2').val(c2);
		c3 = Number(c) + Number(i9);
		$('#c3').val(c3);
		area(a2,b2,c2);
	}
	//制径
	function zhijing(i1, i2, i3, i4, i5, i6) {

		a = $('#a').val();
		a1 = Number(a) - Number(i1);
		$('#a1').val(a1);
		a2 = a;
		$('#a2').val(a2);
		a3 = Number(a) + Number(i4);
		$('#a3').val(a3);

		b = $('#b').val();
		b1 = Number(b) - Number(i2);
		$('#b1').val(b1);
		b2 = b;
		$('#b2').val(b2);
		b3 = Number(b) + Number(i5);
		$('#b3').val(b3);

		c = $('#c').val();
		c1 = Number(c) - Number(i3);
		$('#c1').val(c1);
		c2 = c;
		$('#c2').val(c2);
		c3 = Number(c) + Number(i6);
		$('#c3').val(c3);
		area(a2,b2,c2);

	}
	//外径
	function waijing(i3, i4, i5, i6, i7, i8, i9) {

		a = $('#a').val();
		a1 = Number(a) - Number(i7);
		$('#a1').val(a1);
		a2 = Number(a) - Number(i4);
		$('#a2').val(a2);
		a3 = a;
		$('#a3').val(a3);

		b = $('#b').val();
		b1 = Number(b) - Number(i8);
		$('#b1').val(b1);
		b2 = Number(b) - Number(i5);
		$('#b2').val(b2);
		b3 = b;
		$('#b3').val(b3);

		c = $('#c').val();
		c1 = Number(c) - Number(i9);
		$('#c1').val(c1);
		c2 = Number(c) - Number(i6);
		$('#c2').val(c2);
		c3 = c;
		$('#c3').val(c3);
		area(a2,b2,c2);

	}
	//面积
	function area(a2,b2,c2){ 
		for (i = 0; i < 7; i++) {
			if (ctt == arr[i][0]) {
				
				var i10 = arr[i][10];
				var i11 = arr[i][11];
				
					var d1 = 2 * Number(a2);
					var d2 = 2 * Number(b2);
					var d3 = Number(d1) + Number(d2) + Number(i10);
					var d4 = Number(b2) + Number(c2) + Number(i11);
					var area = Number(d3) * Number(d4) * 0.000001;
					area = Number(area).toFixed(6);
					area = subZeroAndDot(area);
					$('#area').val(area);
				}
	}
	}

	function jisuan() {
		for (i = 0; i < 7; i++) {
			if (ctt == arr[i][0]) {
				var i1 = arr[i][1];
				var i2 = arr[i][2];
				var i3 = arr[i][3];
				var i4 = arr[i][4];
				var i5 = arr[i][5];
				var i6 = arr[i][6];
				var i7 = arr[i][7];
				var i8 = arr[i][8];
				var i9 = arr[i][9];
				var i10 = arr[i][10];
				var i11 = arr[i][11];
				
				if (st == "内径") {
					$("#a,#b,#c").keyup(function() {
						neijing(i1, i2, i3, i7, i8, i9);
						clean();

					});

				}
				if (st == "制径") {
					$("#a,#b,#c").keyup(function() {
						zhijing(i1, i2, i3, i4, i5, i6);
						clean();
					});

				}
				if (st == "外径") {
					$("#a,#b,#c").keyup(function() {
						waijing(i3, i4, i5, i6, i7, i8, i9);
						clean();
					});

				}
			}
		}

	}

	function clean() {
		if (a == "") {
			$('#a1').val("");
			$('#a2').val("");
			$('#a3').val("");
			$('#area').val("");
		}
		if (b == "") {
			$('#b1').val("");
			$('#b2').val("");
			$('#b3').val("");
			$('#area').val("");
		}
		if (c == "") {
			$('#c1').val("");
			$('#c2').val("");
			$('#c3').val("");
			$('#area').val("");
		}
	}

});

//小数取六位
function subZeroAndDot(s) {
	if (!(/^(\+|-)?\d+$/.test(s))) { // 如果不是整数
		s = Number(s).toFixed(6);

		var str = s.toString();
		if (str.toString().indexOf(".") > 0) {
			str = str.replace(/(0+)$/, '');// 去掉多余的0
		}
		return str;
	} else {
		return s;
	}
}
