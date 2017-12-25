
var isOpera = Object.prototype.toString.call(window.opera) == '[object Opera]',

	guiValuePairs = [
		["size", "px"],
		["minversion", ""],
		["quiet", ""],
		["radius", "%"],
		["fontsize", "%"],
		["imagesize", "%"]
	],

	updateGui = function () {

		for (var idx in guiValuePairs) {

			var pair = guiValuePairs[idx],
				$label = $('label[for="' + pair[0] + '"]');

			$label.text($label.text().replace(/:.*/, ': ' + $('#' + pair[0]).val() + pair[1]));
		}
	},

	updateQrCode = function () {
	  var a,b,c,d;
		
		if($('li[class=active]').text()=="文本"){
			a=$("#text").val();
			
		}
		if($('li[class=active]').text()=="网页"){
			a=$("#webtext").val();
			
		}
		if($('li[class=active]').text()=="名片"){
			
			b=$("#name").val().replaceAll(" ",""),
			c=$("#tel").val().replaceAll(" ",""),	
			d=$("#c_email").val().replaceAll(" ","");	
			//名片
			a="BEGIN:VCARD",  
			
			a+="\r\nN: "+b+"  ",
			c&&(a+="\r\nTEL:"+c),	
			d&&(a+="\r\nEMAIL:"+d),	
			a+="\r\nEND:VCARD";
		}
		if($('li[class=active]').text()=="邮件"){
			
			b=$("#address").val().replaceAll(" ",""),
			c=$("#tit").val().replaceAll(" ",""),	
			d=$("#text3").val().replaceAll(" ","");	
			//名片
			a="MATMSG:",  
			
			a+="\r\nTO: "+b+";",
			c&&(a+="\r\nSUB:"+c+";"),	
			d&&(a+="\r\nBODY:"+d+";"),	
			a+=";";
		}
		var options = {
			
				render: $("#render").val(),                      //渲染模式                      
				ecLevel: $("#eclevel").val(),					 //误差校正级
				minVersion: parseInt($("#minversion").val(), 10),//像素点
				color: $("#color").val(),						 //前景颜色
				bgColor: $("#bg-color").val(),                   //背景颜色
				
				/*text: utf16to8($("#text").val()),*/
				text:utf16to8(a),
				
				size: parseInt($("#size").val(), 10),             //大小
				radius: parseInt($("#radius").val(), 10) * 0.01,  //圆角半径
				quiet: parseInt($("#quiet").val(), 10),           //外边距
				mode: parseInt($("#mode").val(), 10),             //嵌入模式
				label:$("#label").val(),				          //嵌入文字
				labelsize: parseInt($("#fontsize").val(), 10) * 0.01, //文字大小
				fontname: $("#font").val(),                        //字体名称
				fontcolor: $("#fontcolor").val(), 				   //字体颜色
				image: $("#img-buffer")[0],                        //img-buffer
				imagesize: parseInt($("#imagesize").val(), 10) * 0.01 //图片大小
			};
		$("#container").empty().qrcode(options);
	},

	update = function () {

		updateGui();
		updateQrCode();
	},

	onImageInput = function () {

		var input = $("#image")[0];

		if (input.files && input.files[0]) {

			var reader = new FileReader();

			reader.onload = function (event) {
				$("#img-buffer").attr("src", event.target.result);
				$("#mode").val("4");
				setTimeout(update, 250);
			};
			reader.readAsDataURL(input.files[0]);
		}
	},

	download = function (event) {

		var data = $("#container canvas")[0].toDataURL('image/png');
		$("#download").attr("href", data);
	};


$(function () {

	if (isOpera) {
		$('html').addClass('opera');
		$('#radius').prop('disabled', true);
	}

	$("#download").on("click", download);
	$("#image").on('change', onImageInput);
	$("input, textarea, select").on("input change", update);
	$(window).load(update);
	update();
});
String.prototype.replaceAll=function(a,b){
	return this.replace(
	new RegExp(a.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g,"\\$1"),"ig"),b)};

function utf16to8(str) {   //解决中文乱码
            var out, i, len, c;  
            out = "";  
            len = str.length;  
            for (i = 0; i < len; i++) {  
                c = str.charCodeAt(i);  
                if ((c >= 0x0001) && (c <= 0x007F)) {  
                    out += str.charAt(i);  
                } else if (c > 0x07FF) {  
                    out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));  
                    out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));  
                    out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));  
                } else {  
                    out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));  
                    out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));  
                }  
            }  
            return out;  
        }  