/**
 *
 * 前端grid导出excel助手类
 *
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-15 下午2:59:39
 * @version 0.2 2015-1-26 上午10:33:39,增加store传入功能，使得自定义导出成为可能
 * @version 0.21 2015-2-28 上午9:16:42,增加列数据的renderer转换
 *
 */
Ext.define("DJ.tools.grid.ExcelHelper", {
    singleton : true,

    alternateClassName : "ExcelHelper",

    requires : ['DJ.tools.grid.MyGridTools'],

    constructor : function() {

        return this;
    },

    base64 : (function() {

        // 运用闭包技术实现封装，so wonderful，漂亮

        // private property
        var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        // private method for UTF-8 encoding
        function utf8Encode(string) {
            string = string.replace(/\r\n/g, "\n");
            var utftext = "";
            for (var n = 0; n < string.length; n++) {
                var c = string.charCodeAt(n);
                if (c < 128) {
                    utftext += String.fromCharCode(c);
                } else if ((c > 127) && (c < 2048)) {
                    utftext += String.fromCharCode((c >> 6) | 192);
                    utftext += String.fromCharCode((c & 63) | 128);
                } else {
                    utftext += String.fromCharCode((c >> 12) | 224);
                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
            }
            return utftext;
        }

        // public method for encoding
        return {
            // This was the original line, which tries to use Firefox's built in
            // Base64 encoder, but this kept throwing exceptions....
            // encode : (typeof btoa == 'function') ? function(input) { return
            // btoa(input); } : function (input) {

            encode : function(input) {
                var output = "";
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
                var i = 0;
                input = utf8Encode(input);
                while (i < input.length) {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                        + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                }
                return output;
            }
        };
    })(),
    getType : function(value) {
        var type = Ext.type(value);
        var result = "";
        switch (type) {
            case "number" :
                result = "Number";
                break;
            case "int" :
                result = "Number";
                break;
            case "float" :
                result = "Number";
                break;
            case "bool" :
            case "boolean" :
                result = "String";
                break;
            case "date" :
                result = "DateTime";
                break;
            default :
                result = "String";
                break;
        }
        return result;
    },

    getClass : function(value) {
        var type = Ext.type(value);
        var result = "";
        switch (type) {
            case "number" :
                result = "float";
                break;
            case "int" :
                result = "int";
                break;
            case "float" :
                result = "float";
                break;
            case "bool" :
            case "boolean" :
                result = "";
                break;
            case "date" :
                result = "date";
                break;
            default :
                result = "";
                break;
        }
        return result;
    },

    buildExcelHeader : function(columns, title) {
    	
    	var length = columns.length;
    	
    	
        var temp = '<ss:Worksheet ss:Name="ExportTable Grid">';
        var headerXml = '<ss:Cell ss:StyleID="headercell" ss:MergeAcross="'
            + (columns.length - 1) + '">' + '<ss:Data ss:Type="String">'
            + title + '</ss:Data>'
            + '<ss:NamedCell ss:Name="Print_Titles" />' + '</ss:Cell>';

        // ss:AutoFitWidth="1"

		var columnSs = [];
		
		(function() {

			for (var i = 0; i < length; i++) {

				var width = columns[i].width || 180;

				columnSs.push(Ext.String.format(
						'<ss:Column ss:AutoFitWidth="0" ss:Width="{0}"/>',
						width));

			}

		})();

		temp += '<ss:Table>' + columnSs.join('')
				+ '<ss:Row ss:AutoFitHeight="1">' + headerXml + '</ss:Row>';

        temp += '<ss:Row >';
        for (var k = 0; k < columns.length; k++) {
            temp += '<ss:Cell ss:StyleID="headercell"><ss:Data ss:Type="String">';
            temp += columns[k].text;
            temp += '</ss:Data></ss:Cell>';
        }
        temp += '</ss:Row>';
        return temp;
    },

    addCell : function(columns, model, temp, cellClass, me) {

        var rowA = [];

        for (var j = 0; j < columns.length; j++) {

            var name = columns[j].dataIndex;
            var value = model.get(name);
            
            if (columns[j].renderer) {
            
            	value = columns[j].renderer(value);
            	
            }

            var type =  me.getType(value);

            //日期进行特殊处理
            if (type == "DateTime"){

                type = "String";

            }
//
//            temp += '<ss:Cell ss:StyleID="' + cellClass + me.getClass(value)
//                + '"><ss:Data ss:Type="' + type + '">';

            rowA.push( '<ss:Cell ss:StyleID="' + cellClass + me.getClass(value)
                + '"><ss:Data ss:Type="' + type + '">');

            if (me.getType(value) == 'DateTime') {
                var date = Ext.util.Format.date(value, 'Y-m-d H:i:s');
//				var date = Ext.util.Format.date(value, 'Y-m-d');

//				var date = value.toLocaleString( );
//                temp += date;

                rowA.push(date);

            } else {
//                temp += value;
                rowA.push(value);
            }
//            temp += '</ss:Data></ss:Cell>';
            rowA.push( '</ss:Data></ss:Cell>');
        }
        return {
            j : j,
            name : name,
            value : value,
            rowA : rowA.join('')
//            temp : temp
            // ,
            // date : date
        };
    },

    storeToXmlForTree : function(tree, title, condition, storeG) {

        var me = this;

        var store = storeG || tree.getStore();

        var columns = MyGridTools.buildColumns(tree, condition);

        var temp = me.buildExcelHeader(columns, title);

        var storeA = store.getRootNode().childNodes;

        var getFirstLevelChildNodes = function(storeA) {

            var storeAT = [];

            Ext.each(storeA, function(ele, index, all) {

                if (ele.childNodes.length != 0) {

                    storeAT = storeAT.concat(ele.childNodes);

                }

            });

            return storeA.concat(storeAT);

        }

        // 只取第一层叶子
        storeA = getFirstLevelChildNodes(storeA);

        var count = storeA.length;

        temp = this._buildBody(count, storeA, temp, me, columns);

//        for (var i = 0; i < count; i++) {
//            var cellClass = (i & 1) ? 'odd' : 'even';
//
//            var model = storeA[i];
//
//            var fields = model.fields;
//
//            temp += '<ss:Row ss:Height="20">';
//
//            // add the data
//            var __ret = me.addCell(columns, model, temp, cellClass, me);
//            var j = __ret.j;
//            var name = __ret.name;
//            var value = __ret.value;
//            temp = __ret.temp;
//
//            temp += '</ss:Row>';
//        }
//        temp += '</ss:Table>';
//        temp += '</ss:Worksheet>';

        var main = this._buildMainLAble(temp);
        return main;
    },

    _buildBody: function (count, store, temp, me, columns) {

        var bodySa = [];

        for (var i = 0; i < count; i++) {
            var cellClass = (i & 1) ? 'odd' : 'even';

            var model;
            
            if (store.getAt) {
            
            	model = store.getAt(i);
            } else {
            
            	model = store[i];
            	
            }
            
            var fields = model.fields;
//            temp += '<ss:Row ss:Height="20">';

            bodySa.push('<ss:Row ss:Height="20">');

            // add the data
            var __ret = me.addCell(columns, model, temp, cellClass, me);

            var j = __ret.j;
            var name = __ret.name;
            var value = __ret.value;
//            temp = __ret.temp;

            bodySa.push(__ret.rowA);
//            temp += '</ss:Row>';
            bodySa.push('</ss:Row>');
        }

        temp += bodySa.join('');

        temp += '</ss:Table>';
        temp += '</ss:Worksheet>';


        return temp;
    }, storeToXml : function(grid, title, condition, storeG, columns2) {

        var me = this;

        var store = storeG || grid.store;

        var count = store.getCount();

        var columns;
        
        if(!Ext.isEmpty(columns2)) {
        
        	columns = columns2;
        	
        } else {
        
        	columns = MyGridTools.buildColumns(grid, condition);
        	
        }
        

        var temp = me.buildExcelHeader(columns, title);

        temp = this._buildBody(count, store, temp, me, columns);

        var main = this._buildMainLAble(temp);
        return main;
    },
    getExcelUrl : function(inputGrid, inputTitle, isTree, conditon, storeG, columns) {

        var me = this;

        var content;

        if (isTree) {
            content = me.storeToXmlForTree(inputGrid, inputTitle, conditon, storeG);

        } else {

            content = me.storeToXml(inputGrid, inputTitle, conditon, storeG, columns);

        }

      var url = 'data:application/vnd.ms-excel;base64,'
            + me.base64.encode(content);
            if(Ext.isIE){
            	url=content;
          
            }

        return url;
    },

    /**
     * 下载excel for grid
     * @param {} grid
     * @param {} title
     * @param {} condtion
     */
    downLoadExcel : function(grid, title, condtion, storeG, columns) {

        var me = this;

        var titleT = title || grid.title || "";

        var url = me.getExcelUrl(grid, titleT, false, condtion, storeG, columns);

        if(!Ext.isIE){
        	 window.location = url;
        }else
        {
	
        	  
        	me.downFileWithIe(titleT,url,IndexMessageRel.projectBasePath+'js/tools/grid/exportExcelIe.jsp',true);
   			
			//另一种方式保存
//        	var xlsWin = window.open("about:blank", "Excel", "widht=0, height=0"); 
//			xlsWin.document.write(url);  
//            xlsWin.document.close();  
//            xlsWin.document.execCommand('Saveas', true,titleT+".xls");  
//            xlsWin.close(); 
        }
    },
    
    //IE下载文件 通过跳转输出方式
    /**
     * 
     * @param {} titleT 文件名称
     * @param {} url 文件内容	
     * @param {} actionurl	文件解析方式
     * @param {} istitlefile 是否按文件名+年月日形式
     */
	    downFileWithIe:function (titleT,url,actionurl,istitlefile){
	    var me=this;
    	var fd=Ext.get('frmDummy');   
           if (!fd) {   
            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',
            action:'', target:'_blank',
            name:'frmDummy',cls:'x-hidden',cn:[   
            {tag:'input',name:'execlfileName',id:'execlfileName',type:'hidden'},   
            {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}   
             ]},true);   
             }   
 			 if(istitlefile)  titleT=me.getExcelFileName(titleT);
 			 fd.set({action:actionurl});
             fd.child('#execlfileName').set({value:encodeURI(Ext.isEmpty(titleT)?"下载":titleT)});   
			 fd.child('#exportContent').set({value:url});   
             fd.dom.submit();
    },


    /**
     * 下载excel for tree
     * @param {} tree
     * @param {} title
     * @param {} condtion
     */
    downLoadExcelForTree : function(tree, title, condtion, storeG) {

        var me = this;

        var titleT = title || tree.title || "";

        var url = me.getExcelUrl(tree, titleT, true, condtion, storeG);

        // window.window.open(url,titleT);

         if(!Ext.isIE){
        	 window.location = url;
        }else
        {

           var fd=Ext.get('frmDummy');   
           if (!fd) {   
            fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',
            action:IndexMessageRel.projectBasePath+'js/tools/grid/exportExcelIe.jsp', target:'_blank',
            name:'frmDummy',cls:'x-hidden',cn:[   
            {tag:'input',name:'execlfileName',id:'execlfileName',type:'hidden'},   
            {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}   
             ]},true);   
             }   
			 titleT=me.getExcelFileName(titleT);
             fd.child('#execlfileName').set({value:encodeURI(Ext.isEmpty(titleT)?"下载":titleT)});   
			 fd.child('#exportContent').set({value:url});   
             fd.dom.submit();
        }
    },
    _buildMainLAble : function(temp) {
        var main = '<xml version="1.0" encoding="utf-8">'
            + '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">'
            + '<o:DocumentProperties><o:Title>'
            + 'title111111111111111111111'
            + '</o:Title></o:DocumentProperties>'
            + '<ss:ExcelWorkbook>'
            + '<ss:WindowHeight>'
            + 100
            + '</ss:WindowHeight>'
            + '<ss:WindowWidth>'
            + 500
            + '</ss:WindowWidth>'
            + '<ss:ProtectStructure>False</ss:ProtectStructure>'
            + '<ss:ProtectWindows>False</ss:ProtectWindows>'
            + '</ss:ExcelWorkbook>'
            + '<ss:Styles>'
            + '<ss:Style ss:ID="Default">'
            + '<ss:Alignment ss:Vertical="Top" ss:WrapText="1" />'
            + '<ss:Font ss:FontName="arial" ss:Size="10" />'
            + '<ss:Borders>'
            + '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />'
            + '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />'
            + '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />'
            + '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />'
            + '</ss:Borders>'
            + '<ss:Interior />'
            + '<ss:NumberFormat />'
            + '<ss:Protection />'
            + '</ss:Style>'
            + '<ss:Style ss:ID="title">'
            + '<ss:Borders />'
            + '<ss:Font />'
            + '<ss:Alignment ss:WrapText="1" ss:Vertical="Center" ss:Horizontal="Center" />'
            + '<ss:NumberFormat ss:Format="@" />' + '</ss:Style>'
            + '<ss:Style ss:ID="headercell">'
            + '<ss:Font ss:Bold="1" ss:Size="10" />'
            + '<ss:Alignment ss:WrapText="1" ss:Horizontal="Center" />'
            + '<ss:Interior ss:Pattern="Solid" ss:Color="#A3C9F1" />'
            + '</ss:Style>' + '<ss:Style ss:ID="even">'
            + '<ss:Interior ss:Pattern="Solid" ss:Color="#CCFFFF" />'
            + '</ss:Style>'
            + '<ss:Style ss:Parent="even" ss:ID="evendate">'
            + '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' + '</ss:Style>'
            + '<ss:Style ss:Parent="even" ss:ID="evenint">'
            + '<ss:NumberFormat ss:Format="0" />' + '</ss:Style>'
            + '<ss:Style ss:Parent="even" ss:ID="evenfloat">'
            + '<ss:NumberFormat ss:Format="0.00" />' + '</ss:Style>'
            + '<ss:Style ss:ID="odd">'
            + '<ss:Interior ss:Pattern="Solid" ss:Color="#CCCCFF" />'
            + '</ss:Style>' + '<ss:Style ss:Parent="odd" ss:ID="odddate">'
            + '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' + '</ss:Style>'
            + '<ss:Style ss:Parent="odd" ss:ID="oddint">'
            + '<ss:NumberFormat ss:Format="0" />' + '</ss:Style>'
            + '<ss:Style ss:Parent="odd" ss:ID="oddfloat">'
            + '<ss:NumberFormat ss:Format="0.00" />' + '</ss:Style>'
            + '</ss:Styles>' + temp + '</ss:Workbook>';
        return main;
    },
   	getExcelFileName:function (filename) { 
		var d = new Date(); 
		var curYear = d.getFullYear();
		var curMonth = "" + (d.getMonth() + 1); 
		var curDate = "" + d.getDate(); 
		if (curMonth.length == 1) { 
		curMonth = "0" + curMonth; 
		} 
		if (curDate.length == 1) { 
		curDate = "0" + curDate; 
		} 
		var fileName = filename + curYear + curMonth + curDate ; 
		return fileName; 
		} 

})
