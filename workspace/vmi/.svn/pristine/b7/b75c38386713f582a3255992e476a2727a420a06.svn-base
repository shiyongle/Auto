Ext.define('Ext.ux.form.MyDatePicker',{
	extend:'Ext.picker.Date',
	alias:'widget.mydatepicker',
	renderTpl: [
	            '<div id="{id}-innerEl" role="grid">',
	                '<div role="presentation" class="{baseCls}-header">',
	                    '<a id="{id}-prevEl" class="{baseCls}-prev {baseCls}-arrow" href="#" role="button" title="{prevText}" hidefocus="on" ></a>',
	                    '<div class="{baseCls}-month" id="{id}-middleBtnEl">{%this.renderMonthBtn(values, out)%}</div>',
	                    '<a id="{id}-nextEl" class="{baseCls}-next {baseCls}-arrow" href="#" role="button" title="{nextText}" hidefocus="on" ></a>',
	                '</div>',
	                '<table id="{id}-eventEl" class="{baseCls}-inner" cellspacing="0" role="grid">',
	                    '<thead role="presentation"><tr role="row">',
	                        '<tpl for="dayNames">',
	                            '<th role="columnheader" class="{parent.baseCls}-column-header" title="{.}">',
	                                '<div class="{parent.baseCls}-column-header-inner">{.:this.firstInitial}</div>',
	                            '</th>',
	                        '</tpl>',
	                    '</tr></thead>',
	                    '<tbody role="presentation"><tr role="row">',
	                        '<tpl for="days">',
	                            '{#:this.isEndOfWeek}',
	                            '<td role="gridcell" id="{[Ext.id()]}">',
	                                '<a role="presentation" hidefocus="on" class="{parent.baseCls}-date" href="#"></a>',
	                            '</td>',
	                        '</tpl>',
	                    '</tr></tbody>',
	                '</table>',
	                '<tpl if="showToday">',
	                    '<div id="{id}-footerEl" role="presentation" class="{baseCls}-footer">{%this.renderD3Btn(values, out)%}{%this.renderD5Btn(values, out)%}{%this.renderD7Btn(values, out)%}</div>',
	                '</tpl>',
	            '</div>',
	            {
	                firstInitial: function(value) {
	                    return Ext.picker.Date.prototype.getDayInitial(value);
	                },
	                isEndOfWeek: function(value) {
	                    value--;
	                    var end = value % 7 === 0 && value !== 0;
	                    return end ? '</tr><tr role="row">' : '';
	                },
	                renderD3Btn: function(values, out) {
	                	var todayBtn = values.$comp.todayBtn;
	                	todayBtn.setText('3日内');
	                    Ext.DomHelper.generateMarkup(todayBtn.getRenderTree(), out);
	                },
	                renderD5Btn: function(values, out){
	                	Ext.DomHelper.generateMarkup(values.$comp.d5Btn.getRenderTree(), out);
	                },
	                renderD7Btn: function(values, out){
	                	Ext.DomHelper.generateMarkup(values.$comp.d7Btn.getRenderTree(), out);
	                },
	                renderMonthBtn: function(values, out) {
	                    Ext.DomHelper.generateMarkup(values.$comp.monthBtn.getRenderTree(), out);
	                }
	            }
	        ],
	        
	        finishRenderChildren: function () {
	            var me = this;
	            
	            me.callParent();
	            if(me.showToday){
	            	me.d5Btn.finishRender();
	            	me.d7Btn.finishRender();
	            }
	        },
	        
	        selectToday : function(){
	            var me = this,
	                btn = me.todayBtn,
	                handler = me.handler;

	            if(btn && !btn.disabled){
	                me.setValue(Ext.Date.clearTime(Ext.Date.add(new Date(),Ext.Date.DAY,3)));
	                me.fireEvent('select', me, me.value);
	                if (handler) {
	                    handler.call(me.scope || me, me, me.value);
	                }
	                me.onSelect();
	            }
	            return me;
	        },
	        
	        selectD5 : function(){
	            var me = this,
	                btn = me.todayBtn,
	                handler = me.handler;

	            if(btn && !btn.disabled){
	                me.setValue(Ext.Date.clearTime(Ext.Date.add(new Date(),Ext.Date.DAY,5)));
	                me.fireEvent('select', me, me.value);
	                if (handler) {
	                    handler.call(me.scope || me, me, me.value);
	                }
	                me.onSelect();
	            }
	            return me;
	        },
	        
	        selectD7 : function(){
	            var me = this,
	                btn = me.todayBtn,
	                handler = me.handler;

	            if(btn && !btn.disabled){
	                me.setValue(Ext.Date.clearTime(Ext.Date.add(new Date(),Ext.Date.DAY,7)));
	                me.fireEvent('select', me, me.value);
	                if (handler) {
	                    handler.call(me.scope || me, me, me.value);
	                }
	                me.onSelect();
	            }
	            return me;
	        },


	        
	        beforeRender: function () {
	            var me = this,
	                d3 = Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.DAY,3), me.format),
	                d5 = Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.DAY,5), me.format),
	                d7 = Ext.Date.format(Ext.Date.add(new Date(),Ext.Date.DAY,7), me.format);
	            me.todayTip = '{0}';
	            if (me.showToday) {
	                me.d5Btn = new Ext.button.Button({
	                    ownerCt: me,
	                    ownerLayout: me.getComponentLayout(),
	                    text: '5日内',
	                    tooltip: Ext.String.format(me.todayTip, d5),
	                    tooltipType: 'title',
	                    handler: me.selectD5,
	                    scope: me
	                });
	                me.d7Btn = new Ext.button.Button({
	                    ownerCt: me,
	                    ownerLayout: me.getComponentLayout(),
	                    text: '7日内',
	                    tooltip: Ext.String.format(me.todayTip, d7),
	                    tooltipType: 'title',
	                    handler: me.selectD7,
	                    scope: me
	                });
	                
	            }
	            me.callParent();
	            if(me.showToday){
	            	me.todayBtn.tooltip = Ext.String.format(me.todayTip, d3);
	            }

	        }
})