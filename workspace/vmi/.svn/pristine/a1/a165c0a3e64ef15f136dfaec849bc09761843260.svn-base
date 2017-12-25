Ext.define('DJ.tools.grid.MergeGrid',{
	extend:'Ext.AbstractPlugin',
	alias:'plugin.mergegrid',
	key:'fid',
	cols:[],
	isNoMerge:false,//true 将clos设为不合并的列，其他列合并
	selectMerge:false,//是否允许合并行一起选择
	init:function(){
		var me = this;
		/**
		 * 合并单元格
		 * @param {} grid  要合并单元格的grid对象,不需要配置
		 * @param {} cols  要合并哪几列 [1,2,4]，可不填写，默认全部
		 * @param {} key  相同key值的行合并，必填，且应放到其他列前面
		 */
		var mergeCells = function(grid,cols,key){
			var arrayTr=document.getElementById(grid.getId()+"-body").firstChild.firstChild.lastChild.getElementsByTagName('tr');	
			var trCount = arrayTr.length;
			var arrayTd;
			var td;
			if(Ext.isEmpty(key)){
				return;
			}else{
//					grid.columns.forEach(function(columns,index){
//						if(columns.dataIndex==key){
//							key =  index;
//						}
//					})
					Ext.each(grid.columns,function(columns,index){
						if(columns.dataIndex==key){
							key =  index;
						}
					})
			}
			var merge = function(rowspanObj,removeObjs){ //定义合并函数
				if(rowspanObj.rowspan != 1){
					arrayTd =arrayTr[rowspanObj.tr].getElementsByTagName("td"); //合并行
					td=arrayTd[rowspanObj.td];
					td.rowSpan=rowspanObj.rowspan;
					td.vAlign="middle";				
					Ext.each(removeObjs,function(obj){ //隐身被合并的单元格
						arrayTd =arrayTr[obj.tr].getElementsByTagName("td");
						arrayTd[obj.td].style.display='none';							
					});
				}	
			};	
			var rowspanObj = {}; //要进行跨列操作的td对象{tr:1,td:2,rowspan:5}	
			var removeObjs = []; //要进行删除的td对象[{tr:2,td:2},{tr:3,td:2}]
			var col;
			if(cols.length==0){
//				 grid.columns.forEach(function(columns,index){
//				 	if(grid.columns.length)
//					cols.push(index);
//				});
				Ext.each(grid.columns,function(columns,index){
					if(grid.columns.length)
					cols.push(index);
				})
			}
			Ext.each(cols,function(colIndex){ //逐列去操作tr
				var rowspan = 1;
				var divHtml = null;//单元格内的数值		
				for(var i=0;i<trCount;i++){
					arrayTd = arrayTr[i].getElementsByTagName("td");
					
					col=colIndex;
					if(!divHtml){
						divHtml = arrayTd[col].innerHTML;
						rowspanObj = {tr:i,td:col,rowspan:rowspan}
					}else{
						var cellText = arrayTd[col].innerHTML;
						var addf=function(){ 
							rowspanObj["rowspan"] = rowspanObj["rowspan"]+1;
							removeObjs.push({tr:i,td:col});
							if(i==trCount-1)
								merge(rowspanObj,removeObjs);//执行合并函数
						};
						var mergef=function(){
							merge(rowspanObj,removeObjs);//执行合并函数
							divHtml = cellText;
							rowspanObj = {tr:i,td:col,rowspan:rowspan}
							removeObjs = [];
						};
						if(cellText == divHtml){
							if(colIndex!=cols[key]){ 
								var leftDisplay=arrayTd[key].style.display;//判断左边单元格值是否已display
								if(leftDisplay=='none'){
									addf();	
								}
								else{
									mergef();		
								}
							}else{
								if(!Array.indexOf)//IE6 
									{
									    Array.prototype.indexOf = function(obj)
									    {              
									        for(var i=0; i<this.length; i++)
									        {
									            if(this[i]==obj)
									            {
									                return i;
									            }
									        }
									        return -1;
									    }
									}
								if(!Ext.isEmpty(arrayTd[col].getAttribute("class"))){
									if(arrayTd[col].getAttribute("class").indexOf("x-grid-cell-special") == -1){//RowNumber列和check列不合并
										addf();		
									}
								}
									
							}
						}else
							mergef();			
					}
				}
			});	
		};
		var cols = me.cols;
		if(me.isNoMerge){
				var allCols = [];
//				me.cmp.columns.forEach(function(column,index){
//					allCols.push(index);
//				})
				Ext.each(me.cmp.columns,function(column,index){
					allCols.push(index);
				})
				cols = Ext.Array.difference(allCols,cols);
		}
		me.cmp.store.on('load',function(){
			mergeCells(me.cmp,cols,me.key);
		})
		if(me.selectMerge){
			me.cmp.on('select',function(m, record, index, eOpts){
//				me.cmp.getSelectionModel().store.data.items.forEach(function(rec,index){
//					if(record.get('fid')==rec.get('fid')){
//						me.cmp.getSelectionModel().select(index,true);
//					}
//				})
				Ext.each(me.cmp.getSelectionModel().store.data.items,function(rec,index){
					if(record.get('fid')==rec.get('fid')){
						me.cmp.getSelectionModel().select(index,true);
					}
				})
			})
		}
	}
	

})