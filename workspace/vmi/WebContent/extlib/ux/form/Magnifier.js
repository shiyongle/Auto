Ext.define('Ext.ux.form.Magnifier',{
	cacheImages: {},
	cacheMagImages: {},
	autoHide: true,		//是否自动消失;boolean
	duration: 2000,		//消失时间 ，单位为毫秒;int
	width: null,		//宽度，单位为px;int
	height: null,		//高度，单位为px;int
	isCache: false,		//是否缓存图片,若为true，不再对同一个产品重新向服务器请求
	scale: 3,			//放大比例，默认为3倍
	isScrolling: true,	//是否允许图片翻页
	constructor: function(config){
		config = config || {};
		if(!config.containerId){
			config.containerId = Ext.id();
		}
		Ext.apply(this,config);
		this.initFrame(config);
	},
	/**
	 * 加载替换图片：
	 * config为{},含配置fid,images
	 * fid - 产品id
	 * images - 数组对象，包含图片路径。若无此参数，用fid查找；若有，直接使用
	 */
	loadImages: function(config){
		var me = this;
		if(!me.cacheImages[config.fid] && !config.images){
			url = 'getImageFilesByParent.do?fid='+config.fid;
			Ext.Ajax.request({
				url: url,
				async: false,
				success:function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						var images = [];
						if(obj.msg){
							images = obj.msg.split(',');
							Ext.each(images,function(item,index,arr){
								arr[index] = 'getFileSource.do?fid='+item;
							});
						}
						config.images = images;
						me.updateImages(config);
					}else{
						Ext.Msg.alert('错误',obj.msg);
					}
				}
			});
		}else{
			me.updateImages(config);
		}
		
	},
	/**
	 * 在某一位置显示图片组件：
	 * coord是坐标对象，含下列属性
	 * left-相对于文档页面的横坐标，单位为px
	 * top-相对于文档页面的纵坐标，单位为px
	 */
	showAt: function(coord){
		if(!this.isFloating){
			return;
		}
		var imgDiv = this.imgDiv,
			imgDivX = coord.left,
			imgDivY = coord.top,
			imgDivWidth;
		this.stopHide();
		if(!this.images){
			imgDiv.setStyle({
				display: 'none'
			})
			return;
		}
		imgDiv.setStyle({
			left: imgDivX + 'px',
			top: imgDivY + 'px',
			display: 'block'
		});
		imgDivWidth = imgDiv.getWidth();
		this.magDiv.setStyle({
			left: imgDivX + imgDivWidth + 20 + 'px',
			top: imgDivY + 'px'
		});
		this.startHide();
	},
	/**
	 * 隐藏组件
	 */
	hide: function(){
		this.imgDiv.dom.style.display = 'none';
	},
	setParent: function(dom){
		if(dom == document.body){
			return;
		}
		var imgDiv = this.imgDiv;
		dom.appendChild(imgDiv);
		imgDiv.parentNode = this.parentDom = dom;
		this.isFloating = false;
	},
	stopHide: function(){
		if(this.autoHide){
			var imgDiv = this.imgDiv;
			imgDiv.stopAnimation();
			imgDiv.setStyle({
				opacity: 1,
				display: 'block'
			});
		}
	},
	startHide: function(){
		if(this.autoHide && this.isFloating){
			var imgDiv = this.imgDiv;
			imgDiv.animate({
				   duration: this.duration,
				   easing: 'backIn',
				   to: {
				       opacity: 0
				   },
				   listeners: {
					   afteranimate: function() {
			                imgDiv.setStyle({
			                	display: 'none',
			                	opacity: 1
			                });
			           }
				   }
			});
		}
	},
	initFrame: function(config){
		var id = config.containerId,
			scale = this.scale,
			imgDiv = document.getElementById(id+'-imgDiv'),
			isBig = false,
			parentDom;
		if(imgDiv == null){
			imgDiv = document.createElement('div');
			imgDiv.id = id+'-imgDiv';
			imgDiv.style.width = (this.width || 320) + 'px';
			imgDiv.style.height = (this.height || 280) + 'px';
			imgDiv.style.position = 'absolute';
			imgDiv.style.boxShadow = '0 0 6px #888';
			imgDiv.style.zIndex = 99999;
			imgDiv.style.display = 'none';
			document.body.appendChild(imgDiv);
			this.isFloating = true;
		}else{
			imgDiv.style.width = '100%';
			imgDiv.style.height = '100%';
			this.isFloating = false;
		}
		parentDom = this.parentDom = imgDiv.parentNode;
		imgDiv = this.imgDiv = Ext.get(imgDiv);
		imgDiv.setHTML('<div id="'+id+'-dragDiv"></div><span id="'+id+'-imgPage"></span>'
								+'<div class="updown-sign" id="'+id+'-prev">&lt;</div>'
								+'<div class="updown-sign" id="'+id+'-next">&gt;</div>');
		var dragDiv = Ext.get(id+'-dragDiv'),
			imgPage = this.imgPage = Ext.get(id+'-imgPage'),
			prev = Ext.get(id+'-prev'),
			next = Ext.get(id+'-next');
		var imgDivWidth = imgDiv.getWidth() || parseInt(imgDiv.dom.style.width);	//若是div悬浮,display ='none',getWidth()等于0
		var imgDivHeight = imgDiv.getHeight() || parseInt(imgDiv.dom.style.height);
		dragDiv.setStyle({
			height: imgDivHeight/scale + 'px',
			width: imgDivWidth/scale + 'px',
			background: '#ccc',
			opacity: 0.5,
			cursor: 'crosshair',
			position: 'absolute',
			display: 'none',
			cursor: 'crosshair'
		});
		imgPage.setStyle({
			background: '#000',
			opacity: 0.4,
			color: '#fff',
			position: 'absolute',
			right: 30+'px',
			bottom: 30+'px'
		});
		prev.setStyle({
			left: '20px',
			top: ((imgDivHeight-prev.getHeight())>>1) + 'px',
			display: 'none'
		});
		next.setStyle({
			right: '20px',
			top: ((imgDivHeight-next.getHeight())>>1) + 'px',
			display: 'none'
		});
		var magDiv = document.createElement('div');
		magDiv.id = id+'-mag';
		document.body.appendChild(magDiv);
		magDiv = this.magDiv = Ext.get(magDiv);
		magDiv.setStyle({
			position: 'absolute',
			width: imgDivWidth + 'px',
			height: imgDivHeight + 'px',
			left: imgDiv.getX() + imgDivWidth + 20 + 'px',
			top: imgDiv.getY() + 'px',
			boxShadow : '0 0 6px #888',
			overflow: 'hidden',
			display: 'none',
                        zIndex : 999999
		});

		//局部放大事件
		var me = this;
		imgDiv.on('mouseover',function(e){
			me.stopHide();
			if(me.images && !isBig){
				dragDiv.setStyle({
					display: ''
				});
				magDiv.setStyle({
					display: ''
				});
				if(me.isScrolling){
					prev.setStyle({
						display: ''
					});
					next.setStyle({
						display: ''
					});
				}
			}
			e.stopEvent();
		});
		imgDiv.on('mouseout',function(e){
			if(me.images && !isBig){
				prev.setStyle({
					display: 'none'
				});
				next.setStyle({
					display: 'none'
				});
				dragDiv.setStyle({
					display: 'none'
				});
				magDiv.setStyle({
					display: 'none'
				});
			}
			me.startHide();
			e.stopEvent();
		});
		imgDiv.on('mousemove',function(e){
			if(me.images){	//已装载图片
				var curIndex = me.curIndex,
				magImage = me.magImages[curIndex],
				imgDivX = imgDiv.getX(),
				imgDivY = imgDiv.getY();
				if(!isBig){
					var coord = e.getXY(),
					top = Math.max(0,Math.min(coord[1]-imgDivY-dragDiv.getHeight()/2,imgDiv.getHeight()-dragDiv.getHeight())),
					left = Math.max(0,Math.min(coord[0]-imgDivX-dragDiv.getWidth()/2,imgDiv.getWidth()-dragDiv.getWidth()));
					dragDiv.setStyle({
						top: top + 'px',
						left:  left+ 'px'
					});
					magImage.style.top = -top*scale + 'px';
					magImage.style.left = -left*scale + 'px';
				}
				e.stopEvent();
			}
		});
		//切换事件
		prev.on('click',function(e){
			var curIndex = me.curIndex,
				images = me.images,
				magImages = me.magImages,
				imagesLen = me.imagesLen;
			if(curIndex>0){
				images[curIndex].style.display = 'none';
				magImages[curIndex].style.display = 'none';
				images[--curIndex].style.display = '';
				magImages[curIndex].style.display = '';
				imgPage.setHTML(curIndex+1+'/'+imagesLen);
				me.curIndex--;
			}
			e.stopEvent();
		});
		prev.on('dblclick',function(e){
			e.stopEvent();
		});
		next.on('click',function(e){
			var curIndex = me.curIndex,
				images = me.images,
				magImages = me.magImages,
				imagesLen = me.imagesLen;
			if(curIndex<imagesLen-1){
				images[curIndex].style.display = 'none';
				magImages[curIndex].style.display = 'none';
				images[++curIndex].style.display = '';
				magImages[curIndex].style.display = '';
				imgPage.setHTML(curIndex+1+'/'+imagesLen);
				me.curIndex++;
			}
			e.stopEvent();
		});
		next.on('dblclick',function(e){
			e.stopEvent();
		});
		//双击放大事件
		var imgDivState = {
			width: imgDiv.getWidth()+'px',
			height: imgDiv.getHeight()+'px',
			position: 'static'
		};
		imgDiv.on('dblclick',function(e){
			var images = me.images;
			if(!images || me.isFloating){
				return;
			}
			if(!isBig){
				document.body.appendChild(imgDiv.dom);
				imgDiv.setStyle({
					position: 'absolute',
					width: '100%',
					height: '100%',
					left: 0,
					top: 0,
					zIndex:99999
				});
				isBig = true;
				dragDiv.setStyle({
					display: 'none'
				});
				Ext.each(images,function(item,index){
					item.style.width = '100%';
					item.style.height = '100%';
				});
			}else{
				parentDom.appendChild(imgDiv.dom);
				imgDiv.setStyle(imgDivState);
				dragDiv.dom.style.display = '';
				isBig = false;
				dragDiv.setStyle({
					display: ''
				});
			}
			prev.setStyle({
				top: ((imgDiv.getHeight()-prev.getHeight())>>1) + 'px'
			});
			next.setStyle({
				top: ((imgDiv.getHeight()-next.getHeight())>>1) + 'px'
			});
			e.stopEvent();
		});
	},
	updateImages: function(config){
		var imgDiv = this.imgDiv,
			magDiv = this.magDiv,
			scale = this.scale;
		var imgDivWidth = imgDiv.getWidth() || parseInt(imgDiv.dom.style.width);
		var imgDivHeight = imgDiv.getHeight() || parseInt(imgDiv.dom.style.height);
		var images,existImages,magImages,img,magImg;
		//删除原图
		if(Ext.isIE){
			existImages = imgDiv.dom.getElementsByTagName("img");
		}else{
			existImages = imgDiv.dom.querySelectorAll('img');
		}
		Ext.Array.each(existImages,function(item,index){
			imgDiv.dom.removeChild(item);
		});
		if(Ext.isIE){
			existImages = magDiv.dom.getElementsByTagName("img");
		}else{
			existImages = magDiv.dom.querySelectorAll('img');
		}
		
		Ext.Array.each(existImages,function(item,index){
			magDiv.dom.removeChild(item);
		});
		//添加图片
		if(this.isCache && this.cacheImages[config.fid]){
			images = this.images = this.cacheImages[config.fid];
			magImages = this.magImages = this.cacheMagImages[config.fid];
			Ext.each(images,function(item,index){
				if(index == 0){
					item.style.display = 'block';
				}else{
					item.style.display = 'none';
				}
				imgDiv.appendChild(item);
			});
			Ext.each(magImages,function(item,index){
				if(index == 0){
					item.style.display = 'block';
				}else{
					item.style.display = 'none';
				}
				magDiv.appendChild(item);
			});
		}else{
			images = this.images = config.images;
			magImages = this.magImages = [];
			if(this.isCache){
				this.cacheImages[config.fid] = images;
				this.cacheMagImages[config.fid] = magImages;
			}
			Ext.each(images,function(item,index){
				img = document.createElement('img');
				img.src = item;
				img.alt = '图片无法显示，请尝试下载！';
				imgDiv.dom.appendChild(img);
				Ext.get(img).setStyle({
					width: imgDivWidth + 'px',	//此处若直接设置100%，对大图片不起作用
					height: imgDivHeight + 'px'
				});
				if(index != 0){
					Ext.get(img).setStyle({
						display: 'none'
					});
				}
				//mag
				magImg = document.createElement('img');
				magImg.src = images[index];
				Ext.get(magImg).setStyle({
					width: imgDivWidth * scale + 'px',
					height: imgDivHeight * scale + 'px',
					position: 'absolute'
				});
				if(index != 0){
					Ext.get(magImg).setStyle({
						display: 'none'
					});
				}
				magDiv.dom.appendChild(magImg);
				images[index] = img;
				magImages.push(magImg);
			});
		}
		//更改状态
		this.curIndex = 0;
		var len = this.imagesLen = images.length;
		if(len ==0){
			this.images = null;
			this.imgPage.setHTML('');
		}
		this.imgPage.setHTML('1/'+ len);
	}
});
