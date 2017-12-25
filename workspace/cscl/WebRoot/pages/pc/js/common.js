window.document.title="同城供应链调度管理平台";



function getRootPath(){
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return projectName;
}

function getBasePath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}



function convertArray(o) {
	var v = {};
	for ( var i in o) {
		if (o[i].name != '__VIEWSTATE') {
			if (typeof (v[o[i].name]) == 'undefined')
				v[o[i].name] = o[i].value;
			else
				v[o[i].name] += "," + o[i].value;
		}
	}
	return v;
}

/** xml字符串编码，用户前端导出excel,参照vmi项目ExcelHelper.js  **/
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

function encode(input) {
	var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
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
/** xml字符串编码，用户前端导出excel,参照vmi项目ExcelHelper.js  **/

$(document).ready(function() {
	//判断下拉框高度
	//$("#carSpecId").combobox('getData').length	
	$.extend($.fn.validatebox.defaults.rules, {
		comboRequired : { // 下拉框判断
			validator : function(value, param) {
				return value.length > 0 && value != '请选择';
			},
			message:'请选择！'
		},
		lessOneMillon:{
			validator:function(value){
				if(/^\d+(\.\d+)?$/.test(value)){
					return parseFloat(value)<1000000;
				}else if(/^\d+\.\d+e\+\d+$/.test(value)){
					return false;
				}else{
					return true;
				}
			},
			message:'数值过大'
		},
		lessOneHundred:{
			validator:function(value){
				if(/^\d+(\.\d+)?$/.test(value)){
					return parseFloat(value)<100;
				}else if(/^\d+\.\d+e\+\d+$/.test(value)){
					return false;
				}else{
					return true;
				}
			},
			message:'小于100%'
		},
		//验证combobox 里面有没有输入的值
		comboxValidate: {
			validator: function(value, param) {
	            var data = $('#' + param[0]).combobox('getData');
	            /* 下拉框所有选项 */
	            var bFlag = false;
	            /* 标识是否在下拉列表中找到了用户输入的字符 */
	            for (var i = 0; i < data.length; i++) {
	                if (data[i].optionName == value) {
	                    bFlag = true;
	                    break;
	                }
	            }
	            return bFlag;
	        },
	        message: "输入正确的值"
	    },
	    //验证 combogird 里面有没有输入的值
	    combogirdValidate:{
	    	validator: function(value, param) {
	            var data = $('#' + param[0]).combogrid('grid').datagrid('getData').rows;
	            var bFlag = false;
	            for (var i = 0; i < data.length; i++) {
	                if (data[i][param[1]] == value) {	                
	                    bFlag = true;
	                    break;
	                }
	            }
	            return bFlag;
	        },
	        message: "输入正确的值"
	    },
		emptyText : { // 如果输入框内容为空或者显示无，提示错误
			validator : function(value, param) {
				return value.length > 0 && value != '无';
			}
		},
		posNum : { // 正数
			validator : function(value, param) {
				if (value.length > 10 || value.length == 0) {
					return false;
				}
				var reg = /^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|0|([1-9][0-9]*)|([0][.][0-9]+[1-9]*))$/;
				return reg.test(value);
			}
		},
		decimal : { // TODO m,n位小数
			validator : function(value, param) {
				var reg = /^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|0|([1-9][0-9]*)|([0][.][0-9]+[1-9]*))$/;
				return reg.test(value);
			}
		},
		pInteger : {//正整数
			validator : function(value, param) {
				var reg = /^[0-9]*[1-9][0-9]*$/;
				return reg.test(value);
			}
		},
		greatZero:{
			validator:function(value,param){
				if(/^\d+(\.{0,1}\d+){0,1}$/.test(value)){
					if(value == '0' || value == '0.00'){
						return true;
					}
					return true;
				}else{
					return false;
				}
			},
			message:'不能输入负数！'
		},
		greaterZero: {
            validator: function(value,param){
                return parseFloat(value)>0;
            },
            message: '输入非零的正数!'
        },
        limitChineseNum: {
    		validator:function(value,param){
    			var reg = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/;
    			return reg.test(value);
    		},
    		message:'只能输入中字，数字和字母！'
    	},
		 maxLength: {
			validator: function(value, param){
				return value.length <= param[0];
			},
			message: '最多{0}个字'
		}
	});

	//combobox搜索的扩展方法
	$.extend($.fn.combobox.methods, { 
	    selectedIndex: function (jq, index) { 
	        if (!index) { 
	            index = 0; 
	        } 
	        $(jq).combobox({ 
	            onLoadSuccess: function () { 
	                var opt = $(jq).combobox('options'); 
	                var data = $(jq).combobox('getData'); 

	                for (var i = 0; i < data.length; i++) { 
	                    if (i == index) { 
	                        $(jq).combobox('setValue', eval('data[index].' + opt.valueField)); 
	                        break; 
	                    } 
	                } 
	            } 
	        }); 
	    } 
	});
	
	/*列拖拽方法实现*/
	$.extend($.fn.datagrid.methods,{
		
		 autoMergeCells: function(jq, fields) {
		        return jq.each(function() {
		            var target = $(this);
		            if (!fields) {
		                fields = target.datagrid("getColumnFields");
		            }
		            var rows = target.datagrid("getRows");
		            var i = 0,
		            j = 0,
		            temp = {};
		            for (i; i < rows.length; i++) {
		                var row = rows[i];
		                j = 0;
		                for (j; j < fields.length; j++) {
		                    var field = fields[j];
		                    var tf = temp[field];
		                    if (!tf) {
		                        tf = temp[field] = {};
		                        tf[row[field]] = [i];
		                    } else {
		                        var tfv = tf[row[field]];
		                        if (tfv) {
		                            tfv.push(i);
		                        } else {
		                            tfv = tf[row[field]] = [i];
		                        }
		                    }
		                }
		            }
		            $.each(temp,
		            function(field, colunm) {
		                $.each(colunm,
		                function() {
		                    var group = this;

		                    if (group.length > 1) {
		                        var before, after, megerIndex = group[0];
		                        for (var i = 0; i < group.length; i++) {
		                            before = group[i];
		                            after = group[i + 1];
		                            if (after && (after - before) == 1) {
		                                continue;
		                            }
		                            var rowspan = before - megerIndex + 1;
		                            if (rowspan > 1) {
		                                target.datagrid('mergeCells', {
		                                    index: megerIndex,
		                                    field: field,
		                                    rowspan: rowspan
		                                });
		                            }
		                            if (after && (after - before) != 1) {
		                                megerIndex = after;
		                            }
		                        }
		                    }
		                });
		            });
		        });
		    },
		
		
		
		//2016-4-22 byles   隐藏、顺序列可配置 必须显示调用hideColumn方法
		hideColumn : function(jq, _6c9) {
			return jq.each(function() {
				//by les 根据localStorage配置设置隐藏    hideColumns配置的隐藏列
			 	var gridConfig = localStorage.getItem(this.id);
			 	if(gridConfig){
			 		if(JSON.parse(gridConfig).hideColumns){
			 			var hideColumns = JSON.parse(gridConfig).hideColumns;
			 			for(var i=0;i<hideColumns.length;i++){
			 				var _6ca = $(this).datagrid("getPanel");
			 				_6ca.find("td[field=\"" +hideColumns[i] + "\"]").hide();
			 				$(this).datagrid("getColumnOption", hideColumns[i]).hidden = true;
			 				$(this).datagrid("fitColumns");
			 			}
			 		}
			 	}
			 	//easyUI自带方法
				var _6ca = $(this).datagrid("getPanel");
				_6ca.find("td[field=\"" + _6c9 + "\"]").hide();
				$(this).datagrid("getColumnOption", _6c9).hidden = true;
				$(this).datagrid("fitColumns");
				//easyUI自带方法
				
				//by ht 根据localStorage配置设置列显示位置   
				var storageName=this.id;
				storageName=localStorage.getItem("move_"+storageName);
				var dataClumns=$(this).datagrid('options').columns;
				var dataClumnsOne=dataClumns[0];
				if(storageName)
		 		{	
		 			var newarr=[];
		 			var newdataClumns=storageName.split(",");
		 			for(var i in newdataClumns)
		 				{
		 				for(var j in dataClumnsOne)
		 					{
		 					if(newdataClumns[i]==dataClumnsOne[j].field)
		 						{
		 						newarr.push(dataClumnsOne[j]);
		 						}
		 					}
		 				}
		 			//将缓存中的配置顺序 赋值给 options
		 			if(newarr.length>0){
		 				$(this).datagrid('options').columns[0] = newarr;
		 			}
		 		}	
				$(this).datagrid();
			});
		},		
		columnMoving: function(jq){
			return jq.each(function(){
				var target = this;
				var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
				cells.draggable({
					revert:true,
					cursor:'pointer',
					edge:5,
					proxy:function(source){
						var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000"/>').appendTo('body');
						p.html($(source).text());
						p.hide();
						return p;
					},
					onBeforeDrag:function(e){
						//阻止右键事件
						if(e.button == "2"){  
				            return false; 
				        };  
						e.data.startLeft = $(this).offset().left;
						e.data.startTop = $(this).offset().top;
					},
					onStartDrag: function(){
						$(this).draggable('proxy').css({
							left:-10000,
							top:-10000
						});
					},
					onDrag:function(e){
						$(this).draggable('proxy').show().css({
							left:e.pageX+15,
							top:e.pageY+15
						});
						return false;
					}
				}).droppable({
					accept:'td[field]',
					onDragOver:function(e,source){
						$(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
						$(this).css('border-left','1px solid #ff0000');
					},
					onDragLeave:function(e,source){
						$(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
						$(this).css('border-left',0);
					},
					onDrop:function(e,source){

						$(this).css('border-left',0);
						var fromField = $(source).attr('field');
						var toField = $(this).attr('field');
						setTimeout(function(){
							moveField(fromField,toField);
							/*数组缓存*/
							var positionArr=$(target).datagrid('getColumnFields');
							window.localStorage.setItem("move_"+target.id,positionArr);
							$(target).datagrid();
							$(target).datagrid('columnMoving');
							/**/
						},0);
					}
				});
				
				// move field to another location
				function moveField(from,to){
					var columns = $(target).datagrid('options').columns;
					var cc = columns[0];
					var c = _remove(from);
					if (c){
						_insert(to,c);
					}
					
					function _remove(field){
						for(var i=0; i<cc.length; i++){
							if (cc[i].field == field){
								var c = cc[i];
								cc.splice(i,1);
								return c;
							}
						}
						return null;
					}
					function _insert(field,c){
						var newcc = [];
						for(var i=0; i<cc.length; i++){
							if (cc[i].field == field){
								newcc.push(c);
							}
							newcc.push(cc[i]);
						}
						columns[0] = newcc;
					}
				}
			});
		},
	    getExcelXml: function (jq, param) {  
	        var worksheet = this.createWorksheet(jq, param);  
	        //alert($(jq).datagrid('getColumnFields'));  
	        var totalWidth = 0;  
	        var cfs = $(jq).datagrid('getColumnFields');  
	        for (var i = 1; i < cfs.length; i++) {  
	            totalWidth += $(jq).datagrid('getColumnOption', cfs[i]).width;  
	        }  
	        //var totalWidth = this.getColumnModel().getTotalWidth(includeHidden);  
	       var excelXml =  '<?xml version="1.0" encoding="utf-8"?>' +//xml申明有问题，以修正，注意是utf-8编码，如果是gb2312，需要修改动态页文件的写入编码  
		    '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">' +  
		    '<o:DocumentProperties><o:Title>' + param.title + '</o:Title></o:DocumentProperties>' +  
		    '<ss:ExcelWorkbook>' +  
		    '<ss:WindowHeight>' + worksheet.height + '</ss:WindowHeight>' +  
		    '<ss:WindowWidth>' + worksheet.width + '</ss:WindowWidth>' +  
		    '<ss:ProtectStructure>False</ss:ProtectStructure>' +  
		    '<ss:ProtectWindows>False</ss:ProtectWindows>' +  
		    '</ss:ExcelWorkbook>' +  
		    '<ss:Styles>' +  
		    '<ss:Style ss:ID="Default">' +  
		    '<ss:Alignment ss:Vertical="Top"  />' +  
		    '<ss:Font ss:FontName="arial" ss:Size="10" />' +  
		    '<ss:Borders>' +  
		    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +  
		    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +  
		    '<ss:Border  ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +  
		    '<ss:Border ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +  
		    '</ss:Borders>' +  
		    '<ss:Interior />' +  
		    '<ss:NumberFormat />' +  
		    '<ss:Protection />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:ID="title">' +  
		    '<ss:Borders />' +  
		    '<ss:Font />' +  
		    '<ss:Alignment  ss:Vertical="Center" ss:Horizontal="Center" />' +  
		    '<ss:NumberFormat ss:Format="@" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:ID="headercell">' +  
		    '<ss:Font ss:Bold="1" ss:Size="10" />' +  
		    '<ss:Alignment  ss:Horizontal="Center" />' +  
		    '<ss:Interior ss:Pattern="Solid"  />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:ID="even">' +  
		    '<ss:Interior ss:Pattern="Solid"  />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="even" ss:ID="evendate">' +  
		    '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="even" ss:ID="evenint">' +  
		    '<ss:NumberFormat ss:Format="0" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="even" ss:ID="evenfloat">' +  
		    '<ss:NumberFormat ss:Format="0.00" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:ID="odd">' +  
		    '<ss:Interior ss:Pattern="Solid"  />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="odd" ss:ID="odddate">' +  
		    '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="odd" ss:ID="oddint">' +  
		    '<ss:NumberFormat ss:Format="0" />' +  
		    '</ss:Style>' +  
		    '<ss:Style ss:Parent="odd" ss:ID="oddfloat">' +  
		    '<ss:NumberFormat ss:Format="0.00" />' +  
		    '</ss:Style>' +  
		    '</ss:Styles>' +  
		    worksheet.xml +  
		    '</ss:Workbook>';  
           return 'data:application/vnd.ms-excel;base64,' + encode(excelXml);	       
	    },  
	    createWorksheet: function (jq, param) {  
	        // Calculate cell data types and extra class names which affect formatting  
	        var cellType = [];  
	        var cellTypeClass = [];  
	        //var cm = this.getColumnModel();  
	        var totalWidthInPixels = 0;  
	        var colXml = '';  
	        var headerXml = '';  
	        var visibleColumnCountReduction = 0;  
	        var cfs = $(jq).datagrid('getColumnFields');  
	        var colCount = cfs.length;  
	        for (var i = 1; i < colCount; i++) {  
	            if (cfs[i] != '') {  
	                var w = $(jq).datagrid('getColumnOption', cfs[i]).width;  
	                totalWidthInPixels += w;  
	                if (cfs[i] === "") {  
	                    cellType.push("None");  
	                    cellTypeClass.push("");  
	                    ++visibleColumnCountReduction;  
	                }  
	                else {  
	                    colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="130" />';  
	                    headerXml += '<ss:Cell ss:StyleID="headercell">' +  
	                '<ss:Data ss:Type="String">' + $(jq).datagrid('getColumnOption', cfs[i]).title + '</ss:Data>' +  
	                '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';  
	                    cellType.push("String");  
	                    cellTypeClass.push("");  
	                }  
	            }  
	        }  
	        var visibleColumnCount = cellType.length - visibleColumnCountReduction;  
	        var result = {  
	            height: 9000,  
	            width: Math.floor(totalWidthInPixels * 30) + 50  
	        };  
	        var rows = $(jq).datagrid('getRows');  
	        // Generate worksheet header details.  
	        var t = '<ss:Worksheet ss:Name="' + param.title + '">' +  
	    '<ss:Names>' +  
	    '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\'' + param.title + '\'!R1:R2" />' +  
	    '</ss:Names>' +  
	    '<ss:Table x:FullRows="1" x:FullColumns="1"' +  
	    ' ss:ExpandedColumnCount="' + (visibleColumnCount + 2) +  
	    '" ss:ExpandedRowCount="' + (rows.length + 2) + '">' +  
	    colXml +  
	    '<ss:Row ss:AutoFitHeight="1">' +  
	    headerXml +  
	    '</ss:Row>';  
	        // Generate the data rows from the data in the Store  
	        //for (var i = 0, it = this.store.data.items, l = it.length; i < l; i++) {  
	        for (var i = 0, it = rows, l = it.length; i < l; i++) {  
	            t += '<ss:Row>';  
	            var cellClass = (i & 1) ? 'odd' : 'even';  
	            r = it[i];  
	            var k = 0;  
	            for (var j = 1; j < colCount; j++) {  
	                //if ((cm.getDataIndex(j) != '')  
	                if (cfs[j] != '') {  
	                    //var v = r[cm.getDataIndex(j)];  
	                    var v = r[cfs[j]]; 
	                    //若easyui的datagrid里的数据没有值，则不显示undefined 
	                    if(v==null){
	                    	v="";
	                    }
	                    if (cellType[k] !== "None") {  
	                        t += '<ss:Cell ss:StyleID="' + cellClass + cellTypeClass[k] + '"><ss:Data ss:Type="' + cellType[k] + '">';  
	                        if (cellType[k] == 'DateTime') {  
	                            t += v.format('Y-m-d');  
	                        } else {  
	                            t += v;  
	                        }  
	                        t += '</ss:Data></ss:Cell>';  
	                    }  
	                    k++;  
	                }  
	            }  
	            t += '</ss:Row>';  
	        }  
	        result.xml = t + '</ss:Table>' +  
	    '<x:WorksheetOptions>' +  
	    '<x:PageSetup>' +  
	    '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />' +  
	    '<x:Footer x:Data="Page &P of &N" x:Margin="0.5" />' +  
	    '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />' +  
	    '</x:PageSetup>' +  
	    '<x:FitToPage />' +  
	    '<x:Print>' +  
	    '<x:PrintErrors>Blank</x:PrintErrors>' +  
	    '<x:FitWidth>1</x:FitWidth>' +  
	    '<x:FitHeight>32767</x:FitHeight>' +  
	    '<x:ValidPrinterInfo />' +  
	    '<x:VerticalResolution>600</x:VerticalResolution>' +  
	    '</x:Print>' +  
	    '<x:Selected />' +  
	    '<x:DoNotDisplayGridlines />' +  
	    '<x:ProtectObjects>False</x:ProtectObjects>' +  
	    '<x:ProtectScenarios>False</x:ProtectScenarios>' +  
	    '</x:WorksheetOptions>' +  
	    '</ss:Worksheet>';  
	        return result;  
	    }  

	});
	
	
});
//2016-4-30 方法改造如下
/*easyui的列隐藏，为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中 */	
//var gridHeaderMenu = function(e, field) {			
//	e.preventDefault();//阻止默认行为
//	var grid = $(this);//当前grid
//	var gridID = grid.get(0).id;
//	var headerContextMenu = this.headerContextMenu;//当前所有表头名称获取
//	/*grid上的列头菜单对象 */
//	if (!headerContextMenu) {
//	    var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
//	    var fields = grid.datagrid('getColumnFields');
//	    for (var i = 0; i < fields.length; i++) {
//	        var fildOption = grid.datagrid('getColumnOption', fields[i]);
//	        if (!fildOption.hidden) {
//	            $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
//	        } else {
//	            $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
//	        }
//	    }
//    //menu构造一级菜单树木
//    headerContextMenu = this.headerContextMenu = tmenu.menu({
//        onClick: function(item) {
//            var field = $(item.target).attr('field');
//            /********隐藏列的缓存实现*******/
//            // by　les 构造缓存对象   gridID :{ hideColumns:[........]}       
//            var mygirdConfig = {};                
//            if(localStorage.getItem(gridID)){
//            	mygirdConfig = JSON.parse(localStorage.getItem(gridID));
//            	//hideColumns 隐藏的列
//            	if(!mygirdConfig.hideColumns){
//            		mygirdConfig.hideColumns = [];                		
//            	}
//            }else{
//            	mygirdConfig.hideColumns = [];
//            }     
//            var notexsit = true;
//            if (item.iconCls == 'icon-ok') {
//                grid.datagrid('hideColumn', field);//隐藏点击打钩的列
//                for(var i=0;i<mygirdConfig.hideColumns.length;i++){
//                	if(mygirdConfig.hideColumns[i]==field){
//                		notexsit = false;
//                		break;
//                	}
//                }
//                //隐藏
//                if(notexsit){
//                	mygirdConfig.hideColumns.push(field);                	
//                }
//                
//                $(this).menu('setIcon', {
//                    target: item.target,
//                    iconCls: 'icon-empty'
//                });
//            } else {  
//            	grid.datagrid('showColumn', field);
//            	/********隐藏列的缓存实现*******/
//            	var hideColumns = mygirdConfig.hideColumns;
//            	for(var i=0;i<hideColumns.length;i++){
//            		if(hideColumns[i]==field){
//            			hideColumns.splice(i,1);
//            		}
//            	}                	
//                $(this).menu('setIcon', {
//                    target: item.target,
//                    iconCls: 'icon-ok'
//                });
//            }
//            /********隐藏列的缓存实现*******/
//            //localStorage 存 string
//             localStorage.setItem(gridID,JSON.stringify(mygirdConfig));
//        }
//    });
//    
//}
//headerContextMenu.menu('show', {
//    left: e.pageX,
//    top: e.pageY
//});
//};

/*easyui的列隐藏，为datagrid、treegrid增加表头菜单，用于显示或隐藏列 */	

var gridHeaderMenu = function(e, field) {			
	e.preventDefault();//阻止默认行为
	var grid = $(this);//当前grid
	var gridID = grid.get(0).id;
	var headerContextMenu = this.headerContextMenu;//当前所有表头名称获取
    var treedata = [];
	/*grid上的列头菜单对象 */
	if (!headerContextMenu) {
	    var tmenu = $('<ul id="tree"></ul>').appendTo('body');
	    var fields = grid.datagrid('getColumnFields');
	    for (var i = 0; i < fields.length; i++) {
	    	var dataitem ={};
	        var fildOption = grid.datagrid('getColumnOption', fields[i]);
	        if (!fildOption.hidden) {
	        	dataitem.text = fildOption.title;
	        	dataitem.attributes= {"field":fields[i]};
//	            $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
	        } else {
	        	dataitem.text = fildOption.title;
	        	dataitem.checked = "true"
	            dataitem.attributes= {"field":fields[i]};
	        }
	        treedata.push(dataitem);
	    }
    //menu构造一级菜单树木
	    this.headerContextMenu = headerContextMenu  = tmenu.tree({
    	data : treedata,
    	checkbox : true,
    	onCheck : function(node, checked){
    		var field = node.attributes.field;
            /********隐藏列的缓存实现*******/
            // by　les 构造缓存对象   gridID :{ hideColumns:[........]}       
            var mygirdConfig = {};                
            if(localStorage.getItem(gridID)){
            	mygirdConfig = JSON.parse(localStorage.getItem(gridID));
            	//hideColumns 隐藏的列
            	if(!mygirdConfig.hideColumns){
            		mygirdConfig.hideColumns = [];                		
            	}
            }else{
            	mygirdConfig.hideColumns = [];
            } 
            if(checked){
            	grid.datagrid('hideColumn', field);//隐藏点击打钩的列
            	mygirdConfig.hideColumns.push(field);
            }else{
            	grid.datagrid('showColumn', field);
            	/********隐藏列的缓存实现*******/
            	var hideColumns = mygirdConfig.hideColumns;
            	for(var i=0;i<hideColumns.length;i++){
            		if(hideColumns[i]==field){
            			hideColumns.splice(i,1);
            		}
            	}                	
            }
          /********隐藏列的缓存实现*******/
          //localStorage 存 string
           localStorage.setItem(gridID,JSON.stringify(mygirdConfig));
    	}
    });   
	}
	//移除tree的原生图标
	$(headerContextMenu).find(".tree-file").remove();
	$(headerContextMenu).show();
	$(headerContextMenu).on({
		"mouseover":function(){$(this).show()},
		"mouseout":function(){$(this).hide()}
	});
    $(headerContextMenu).css({
    	"left" :e.pageX,
    	"top" : e.pageY+10,
    	"position":"absolute",
    	 background:"#fff",
    	 border:"1px solid #ccc",
    	 padding:"3px"});
};

//datagrid表格控件中多行操作钮的控制(带条件)
function multipleButtonS(rowData, attr, value, button) {
	var rowsLength = rowData.length;
	if (rowsLength <= 0) {
		button.linkbutton('disable');
	} else {
		if (value instanceof Array) {
			for ( var i = 0; i < rowData.length; i++) {
				if (!isInArray(value, rowData[i][attr])) {
					button.linkbutton('disable');
					return;
				}
			}
		} else {
			for ( var i = 0; i < rowData.length; i++) {
				if (rowData[i][attr] != value) {
					button.linkbutton('disable');
					return;
				}
			}
		}
		button.linkbutton('enable');
	}
}

/* 
 * 工具栏toolbar的搜索功能,针对单个搜索
 *  formId   		 : 搜索框表单的名字
 *  searchInputId 	 : 搜索框控件的Id
 *  searchInputName  : 搜索框控件的Name
 *  searchButton	 : 搜索按钮的ID 
 *  type       		 : 1代表下拉框
 *  type       		 : 2代表下拉表
 *  
 */
function toolbarSearch(formId,searchInputId,searchInputName,searchButton,type){
	var html;
	if(type==1){//下拉框
	html='<div style="padding:2px 0px  0px 4px;margin-bottom:2px">'+
	'<form id="'+formId+'" method="post">'+
	'<img src="'+getRootPath()+'/pages/pc/js/easyui/themes/icons/search.png" style="margin-bottom:-6px;margin-right:5px;"/>'+
	'<select id="'+searchInputId+'" name="'+searchInputName+'">'+
	'<option value="">请选择</option>'+
	'</select>'+
	'<a id="'+searchButton+'" href="javascript:;"  style="width:60px;height:22px;margin-left:10px">查询</a>'+
	'</form></div>';
	}
	else if(type==2){//下拉表
		html='<div style="padding:2px 0px  0px 4px;margin-bottom:2px">'+
		'<form id="'+formId+'" method="post">'+
		'<img src="'+getRootPath()+'/pages/pc/js/easyui/themes/icons/search.png" style="margin-bottom:-6px;margin-right:5px;"/>'+
		'<select id="'+searchInputId+'" name="'+searchInputName+'"  class="Msearch-key">'+
		'</select>'+
		'</form></div>';
	}
	else{
		html='<div style="padding:2px 0px  0px 4px;margin-bottom:2px">'+
		'<form id="'+formId+'" method="post">'+
		'<img src="'+getRootPath()+'/pages/pc/js/easyui/themes/icons/search.png" style="margin-bottom:-6px;margin-right:5px;"/>'+
		'<input id="'+searchInputId+'" name="'+searchInputName+'"/>'+
		'<a id="'+searchButton+'" href="javascript:;"  style="width:60px;height:22px;margin-left:10px">查询</a>'+
		'</form></div>';
		}
$(".datagrid-toolbar table").css({"float":"left"});
$(".datagrid-toolbar").append(html);
$('#'+searchButton).linkbutton({});//按钮样式初始化
}

/**************************************遮罩层插件***************************************************/
/* 
 *  Document   : mask 1.1
 *  Created on : 2011-12-11, 14:37:38
 *  Author     : GodSon
 *  Email      : wmails@126.com
 *  Link       : www.btboys.com 
 *  
 */
(function($){
    function init(target,options){
        var wrap = $(target);
		if($("div.mask",wrap).length) wrap.mask("hide");
		
        wrap.attr("position",wrap.css("position"));
		wrap.attr("overflow",wrap.css("overflow"));
        wrap.css("position", "relative");
		wrap.css("overflow", "hidden");
        
        var maskCss = {
            position:"absolute",
            left:0,
            top:0,
			cursor: "wait",
            background:"#ccc",
            opacity:options.opacity,
            filter:"alpha(opacity="+options.opacity*100+")",
            display:"none"
        };
        
        var maskMsgCss = {
            position:"absolute",
            width:"auto",
            padding:"10px 20px",
            border:"2px solid #ccc",
            color:"white",
			cursor: "wait",
            display:"none",
            borderRadius:5,
            background:"black",
            opacity:0.6,
            filter:"alpha(opacity=60)"
        };
		var width,height,left,top;
		if(target == 'body'){
			width = Math.max(document.documentElement.clientWidth, document.body.clientWidth);
			height = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
		}else{
			width = wrap.outerWidth() || "100%";
			height = wrap.outerHeight() || "100%";
		}
        $('<div class="mask"></div>').css($.extend({},maskCss,{
            display : 'block',
            width : width,
            height : height,
            zIndex:options.zIndex
        })).appendTo(wrap);

		var maskm= $('<div class="mask-msg"></div>').html(options.maskMsg).appendTo(wrap).css(maskMsgCss);
		
		if(target == 'body'){
			left = (Math.max(document.documentElement.clientWidth,document.body.clientWidth) - $('div.mask-msg', wrap).outerWidth())/ 2;
			if(document.documentElement.clientHeight > document.body.clientHeight){
				top = (Math.max(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.mask-msg', wrap).outerHeight())/ 2;
			}else{
				top = (Math.min(document.documentElement.clientHeight,document.body.clientHeight)  - $('div.mask-msg', wrap).outerHeight())/ 2;
			}
			
		}else{
			left = (wrap.width() - $('div.mask-msg', wrap).outerWidth())/ 2;
			top = (wrap.height() - $('div.mask-msg', wrap).outerHeight())/ 2;
		}
		
        maskm.css({
            display : 'block',
            zIndex:options.zIndex+1,
            left : left,
            top :  top
        });
        
        setTimeout(function(){
            wrap.mask("hide");
        }, options.timeout);
            
        return wrap;
    }
       
    $.fn.mask = function(options){   
        if (typeof options == 'string'){
            return $.fn.mask.methods[options](this);
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init(this,options);
    };
    $.mask = function(options){  
        if (typeof options == 'string'){
            return $.fn.mask.methods[options]("body");
        }
        options = $.extend({}, $.fn.mask.defaults,options);
        return init("body",options);
    };
	
	$.mask.hide = function(){
		$("body").mask("hide");
	};
	
    $.fn.mask.methods = {
        hide : function(jq) {
            return jq.each(function() {
                var wrap = $(this);
                $("div.mask",wrap).fadeOut(function(){
                    $(this).remove();
                });
                $("div.mask-msg",wrap).fadeOut(function(){
                    $(this).remove();
                    wrap.css("position",  wrap.attr("position"));
					wrap.css("overflow", wrap.attr("overflow"));
                });
            });
        }
    };
    
    $.fn.mask.defaults = {
        maskMsg:'loading...',
        zIndex:100000,
        timeout:30000,
        opacity:0.6
    };
})(jQuery);
/**************************************遮罩层插件***************************************************/




// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//前端时间转换
function localTime(value,row,index){
	if(value==null||value==""){
		return "";
	}
	else{		
		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	}
	}

/***图片拖拽***/
function dragImg(obj) {
	obj.onmousedown = function(ev) {
		var ev = ev || event;
		var disX = ev.clientX - this.offsetLeft;
		var disY = ev.clientY - this.offsetTop;
		document.onmousemove = function(ev) {
			var ev = ev || event;
			
			obj.style.left = ev.clientX - disX + 'px';
			obj.style.top = ev.clientY - disY + 'px';
		}
		document.onmouseup = function() {
			document.onmousemove = document.onmouseup = null;	
		}
		return false;	
	}
}

/*easyui下拉框(combobox)搜索过滤*/
function searchItem(q, row){
	var opts = $(this).combobox('options');
	if(row[opts.textField]){
	return row[opts.textField].indexOf(q) == 0;
	}
} 

//表单信息转JSON
function formToJOSN(formId) {
	var jObject=$("#"+formId).serializeArray();
	var results = '{';
	jQuery.each(jObject, function(i, field) {
		if (i == 0) {
			results += '"' + field.name + '":"' + field.value + '"';
		} else {
			results += ',"' + field.name + '":"' + field.value + '"';
		}
	});
	results=results + '}';
	return JSON.parse(results);
}
