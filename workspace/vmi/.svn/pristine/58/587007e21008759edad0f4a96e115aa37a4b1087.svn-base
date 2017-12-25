Ext.ygdd = {};
Ext.define('Ext.ux.form.MyDDProxy', {
	extend: 'Ext.dd.DDProxy',
	constructor: function(id) {
		var img = document.getElementById(id),
			imgWrap,dd;
		if(Ext.ygdd[id]){
			var dd = Ext.ygdd[id];
			imgWrap = document.getElementById(id+'-rzwrap');
			imgWrap.insertBefore(img,imgWrap.firstChild);
			dd.resizer.getEl().dom.style.display = '';
		}else{
			var dd = this;
			dd.originalImg = img;
			dd.originalParent = img.parentNode;
			var resizer = dd.resizer = Ext.create('Ext.resizer.Resizer', {
				target: img,
				pinned:true,
				minWidth: Ext.Element.getViewportWidth(),
				minHeight: Ext.Element.getViewportHeight(),
				preserveRatio: true,
				handles: 'all',
				dynamic: true,
				disabled: true
			});
			var resizerEl = resizer.getEl();
			document.body.insertBefore(resizerEl.dom,document.body.firstChild);
			resizerEl.setStyle({
				width : Ext.Element.getViewportWidth()+'px',
				height : Ext.Element.getViewportHeight()+'px',
				zIndex : '9999',
				cursor : 'move'
			});
			resizerEl.on('mousewheel',function(e){
				var me = this;
				var box = me.getBox();//width,height,left,top
				var radio = box.height/box.width;
				if(Math.abs(e.getWheelDelta())!=1){
					return;
				}
				var wheelIncreament = 30 * e.getWheelDelta();
				var docWidth = Ext.Element.getViewportWidth();
				var docHeight = Ext.Element.getViewportHeight();
				if(wheelIncreament<0&& (me.getWidth()<=docWidth|| me.getHeight()<=docHeight)){
					return;
				}
				if(wheelIncreament>0 && (me.getWidth()>docWidth+1000 || me.getHeight()>docHeight+1000)){
					return;
				}
				box.width += wheelIncreament*2;
				box.height += wheelIncreament*2 * radio;
				box.x = Math.max(Math.min(box.x - wheelIncreament,0),docWidth-box.width);
				box.y = Math.max(Math.min(box.y - wheelIncreament,0),docHeight-box.height);
				me.setBox(box);
				Ext.get(id+'-dd').setBox(box);
			});
			this.init(id+"-rzwrap", undefined, {dragElId: id+'-dd'});
			this.initFrame();
			resizerEl.on('dblclick', function(){
				dd.originalParent.appendChild(dd.originalImg);
				dd.originalImg.style.width = '100%';
				resizerEl.setBox({
					width: Ext.Element.getViewportWidth(),
					height: Ext.Element.getViewportHeight(),
					x: 0,
					y: 0
				});
				resizerEl.dom.style.display = 'none';
			});
			Ext.ygdd[id] = dd;
		}
		return dd;
    },
	alignElWithMouse: function(el, iPageX, iPageY) {
        var oCoord = this.getTargetCoord(iPageX, iPageY),
            fly = el.dom ? el : Ext.fly(el, '_dd'),
            elSize = fly.getSize(),
            EL = Ext.Element,
            vpSize,
            aCoord,
            newLeft,
            newTop;
	    if (!this.deltaSetXY) {
	        vpSize = this.cachedViewportSize = { width: EL.getViewportWidth(), height: EL.getViewportHeight() };
	        aCoord = [
	            Math.min(0, Math.max(oCoord.x, vpSize.width - elSize.width)),
	            Math.min(0, Math.max(oCoord.y, vpSize.height - elSize.height))
	        ];
	        fly.setXY(aCoord);
	        newLeft = this.getLocalX(fly);
	        newTop  = fly.getLocalY();
	        this.deltaSetXY = [newLeft - oCoord.x, newTop - oCoord.y];
	    } else {
	        vpSize = this.cachedViewportSize;
	        this.setLocalXY(
	            fly,
	            Math.min(0, Math.max(oCoord.x + this.deltaSetXY[0], vpSize.width - elSize.width)),
	            Math.min(0, Math.max(oCoord.y + this.deltaSetXY[1], vpSize.height - elSize.height))
	        );
	    }
	
	    this.cachePosition(oCoord.x, oCoord.y);
	    this.autoScroll(oCoord.x, oCoord.y, el.offsetHeight, el.offsetWidth);
	    return oCoord;
	}
});