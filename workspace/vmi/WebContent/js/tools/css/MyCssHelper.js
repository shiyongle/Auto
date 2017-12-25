/**
 * 
 * css帮助工具.
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-3-31 上午11:15:56
 * 
 */
(function() {

	Ext.define('DJ.tools.css.MyCssHelper', {
		singleton : true,
		alternateClassName : "MyCssHelper",
		statics : {
			ToolbarButtonStyle : {
				borderColor : 'rgba(0, 0, 0, 0.2)'
			},

			/*
			 * 要改toolbar的grid的id
			 */
			gridIds : ['DJ.order.Deliver.DeliversBoardList',
					'DJ.order.Deliver.FistproductdemandList',
					'DJ.System.product.CustproductCustTreeList',
					'DJ.order.Deliver.MystockList',
					'DJ.order.Deliver.DeliversCustList']
		},

		/*
		 * 改样式
		 */
		setThoseDefaultGrids : function() {
			return this.setGridCusStyles(this.self.gridIds);
		},
		changeToolbarButtonCss : function(toolbar) {
			var btn, btns, i, len;
			btns = toolbar.items.filterBy(function(item, key) {
				return item.getXTypes().indexOf('button') !== -1;
			});
			for (i = 0, len = btns.length; i < len; i++) {
				btn = btns.items[i];
				
				//控制高度
				btn.setHeight( 22 );
				
				
				var clsS = 'x-btn x-unselectable x-window-item x-btn-default-small x-icon-text-left x-btn-icon-text-left x-btn-default-small-icon-text-left';
				
				if (Ext.isEmpty(btn.iconCls)) {
				
					clsS = 'x-btn x-unselectable x-box-item x-btn-default-small x-noicon x-btn-noicon x-btn-default-small-noicon';
					
				}
				
				btn.addCls(clsS.split(' '));
				
				if (btn.getEl()) {
				
					this._setButtonCss(btn.getEl());
					
				}
			}
		},
		_setButtonCss : function(ele) {
			if (ele.applyStyles) {
			
				ele.applyStyles(this.self.ToolbarButtonStyle);
				
			}
			
		},
		setGridCusStyles : function(ids) {
			var idsT, me;
			me = this;
			idsT = Ext.clone(this.self.gridIds);
			return Ext.ComponentManager.all.on('add', function(thiss, key,
					value, eOpts) {
				if (Ext.Array.contains(idsT, value.id)) {
					Ext.Array.remove(idsT, value.id);
					MyCommonToolsZ.setComAfterrender(value, function() {
						me.changeToolbarButtonCss(value
								.getDockedItems('toolbar[dock="top"]')[0]);
								
						
						
						
					});
					if (idsT.length === 0) {
						return Ext.ComponentManager.all.un(this);
					}
				}
			});
		}
	});

}).call(this);
