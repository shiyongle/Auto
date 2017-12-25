var dj = {};
var djSelect = function(id,config){
	config = config || {};
	if(typeof id == 'string'){
		config.id = id;
	}else{
		config = id;
	}
	return new dj.Select(config);
};
/*config: url、data、id、enabled、readonly、autoLoad、onLoad(加载后的回调)、minChars(远程查询的最小输入，默认2个字符)、dropDownHeight(下拉高度)、autoSet(一条数据，自动赋值)、hideTrigger(隐藏下拉箭头)、noHideValue(不取隐藏值)、afterSelect(函数)*/
dj.Select = function(id,config){
	config = config || {};
	if(typeof id == 'string'){
		config.id = id;
	}else{
		config = id;
	}
	this.initialize(config);
};
dj.Select.prototype = {
	constructor: dj.Select,
	enabled: true,
	autoLoad: true,
	minChars: 2,
	initialize: function(config){
		var id = config.id;
		var me = $('#'+id);
		this.initConfig(config,me);
		this.initOptions(me);
		me.replaceWith(this.template.replace('$id',id));
		var $el = this.$el = $('#'+id).data('djselect',this);
		this.$input = $el.find('.select_text');
		this.$arrow = $el.find('.select_arrow');
		this.$dropdown = $el.find('.select_dropdown');
		this.$hidden = $el.find('.select_val').data('djselect',this);
		this.renderOptions(this.options);
		this.initEvent();
		this.addClass();
		this.initElProp(me);
		this.setEnable(this.enabled);
	},
	initElProp: function($dom){
		if(this.readonly){
			this.$input.prop('readonly',true);
		}
		if(this.hideTrigger){
			this.$arrow.css('display','none');
		}
		this.$el.addClass($dom.attr('class'));
		this.$input.prop('placeholder',$dom.prop('placeholder'));
		if(this.noHideValue){
			this.$input.prop('name',$dom.attr('name'));
		}else{
			this.$hidden.prop('name',$dom.attr('name'));
		}
	},
	initConfig: function(config,$dom){
		$.extend(this,config);
		var clientTop = $dom[0].clientTop;
		if(!this.width){
			this.width = $dom.outerWidth() - 2*clientTop;
		}else{
			this.width = parseInt(this.width);
		}
		if(!this.height){
			this.height = $dom.outerHeight() - 2*clientTop;
		}else{
			this.height = parseInt(this.height);
		}
	},
	initOptions: function($dom){
		var options = this.options = [/*{text:'abc',val:'1'}*/],
			me = this,
			option;
		if(this.url){
			if(this.autoLoad){
				this.load(this.data || {});
			}
		}else if($dom.is('select')){
			$dom.children().each(function(index,item){
				item = $(item);
				option = {text: item.html(),val: item.val()};
				options.push(option);
				if(item.attr('selected')){
					option.selected = true;
					me.defaultOption = option;
				}
			});
		}else{
			$.error($dom.attr('id')+'的url未配置');
		}
	},
	reload: function(data){
		this.load(data || this.loadData,!this.queryRemote);
	},
	load: function(config,queryRemote){
		queryRemote = (queryRemote!=null) ? !!queryRemote : true;
		var me = this;
		this.loading = true;
		this.loadData = config || {};
		$.ajax({
			url: this.url,
			data: config || {},
			type: 'post',
			dataType: 'json',
			async: queryRemote,
			success: function(res){
				me.lastQueryData = config;
				me.options = me.transformData(res);
				me.afterLoad();
			},
			failure: function(){
				$.error('[ '+url+' ]访问异常！');
			}
		});
	},
	afterLoad: function(){
		this.onLoad(this.options);
		if(this.tempValue){
			var option = this.getOption(this.tempValue);
			option && this.selectItem(option);
			this.tempValue = null;
		}else if(this.autoSet && this.options.length == 1){
			this.selectItem(this.options[0]);
		}else if(!this.autoSet && this.autoDefaultSet){
			this.selectItem(this.options[0]);
		}
		this.loading = false;
	},
	onLoad: $.noop,
	setEnable: function(bool){
		this.enabled = bool;
		if(!bool){
			this.$arrow.css('display','none');
			this.$input.prop('readonly',true);
		}else{
			if(!this.hideTrigger){
				this.$arrow.css('display','');
			}
			if(!this.readonly){
				this.$input.prop('readonly',false);
			}
		}
	},
	getEnable: function(){
		return this.enabled;
	},
	transformData: function(res){		//自定义
		return $.map(res.list,function(item,index){
			return {text: item.fname,val: item.fid};
		});
	},
	initEvent: function(){
		var me = this;
		me.$input.click(function(){
			if(me.readonly){
				me.toggleOpen();
			}else if(me.hideTrigger && !me.isOpen()){
				me.find();
				if(!me.queryRemote){
					me.open();
				}
			}
		}).keyup(function(e){
			var key = e.which;
			if(me.isOpen()){
				switch(key){
					case 38: me.activePrev();break;
					case 40: me.activeNext();break;
					case 13: me.selectItem(me.$dropdown.find('.active'));break;
				}
			}else{
				if(key == 40){
					me.find();
					if(!me.queryRemote){
						me.open();
					}
				}
			}
			if(!me.readonly && !me.isSpecialKey(key)){
				me.find();
				if(!me.isOpen()){
					me.open();
				}
			}
		}).blur(function(e){
			var temp;
			if(me.allowBlur){
				if(temp = me.hasMatch()){
					me.addHiddenValue(temp);
				}else if(me.forceSelection){
					if(me.$hidden.val()){
						me.setValue(me.getOption(me.$hidden.val()));
					}else{
						me.setValue(me.defaultOption || '');
					}
					me.$el.trigger('selected');
				}else if(me.noHideValue){
					me.setValue(me.$input.val());
				}
				if(me.isOpen()){
					me.hide();
				}
			}
		});
		me.$el.mousedown(function(){
			me.allowBlur = false;
			setTimeout(function(){
				me.allowBlur = true;
			},30);
		});
		me.$arrow.click(function(){
			if(me.isOpen()){
				me.hide();
			}else{
				if(!me.hasMatch()){
					me.selectItem('');
				}
				me.find();
				if(!me.queryRemote){
					me.open();
				}
				me.$input.focus();
			}
			return false;	//阻止$input的点击事件
		});
		me.$dropdown.delegate('.select_item','mouseover',function(e){
			me.activeItem(e.target);
		}).delegate('.select_item','click',function(e){
			me.selectItem(e.target);
			return false;	// 注释掉会触发$input的click事件
		});
	},
	selectItem: function(dom){
		var $el = this.$el;
		var option;
		var temp = $el.data('model');
		if(dom === ''){
			$el.removeData('model');
			this.setValue('');
			option = undefined;
		}else if($.isPlainObject(dom)){
			option = dom;
			$el.data('model',option);
			this.setValue(option);
		}else{
			var val = String($(dom).data('val'));
			$.each(this.options,function(index,item){
				if(item.val === val){
					option = item;
					return false;
				}
			});
			$el.data('model',option);
			this.setValue(option);
		}
		if(temp !== option){
			this.$el.trigger('selected'); //change
			this.afterSelect(option);
		}
		if($el.data('model')){
			this.$hidden.data('model',$el.data('model'));
		}
		this.hide();
		this.$input.focus();
	},
	afterSelect: $.noop,
	isSpecialKey: function(key){
		if(key==9 || key==13 || key==27 || key==44 || key==45 || (key>=33 && key<=40) || (key>=16 && key<=20)){
			return true;
		}else{
			return false;
		}
	},
	activePrev: function(){
		var active = this.$dropdown.find('.active').prev();
		if(active.length==0){
			active = this.$dropdown.children().last();
		}
		this.activeItem(active);
	},
	activeNext: function(){
		var active = this.$dropdown.find('.active').next();
		if(active.length==0){
			active = this.$dropdown.children().first();
		}
		this.activeItem(active);
	},
	addClass: function(){
		this.$el[0].style.cssText = 'width:'+this.width+'px;height: '+this.height+'px;position: relative;border: 1px solid #ddd;-webkit-user-select:none;-moz-user-select: none;-ms-user-select:none;user-select:none;';
		this.$input[0].style.cssText = 'padding:0 5px;margin:0;width:100%;height:100%;background: transparent;outline: none;border: none;';
		this.$dropdown[0].style.cssText = 'overflow-y:auto;display:none;z-index:999;list-style-type: none;margin:0;padding: 0;width:100%;border: 1px solid #ccc;position: absolute;top: 100%;left: -1px;margin-top: -1px;background: #fff;border-radius: 0 0 4px 4px;'
			+(this.dropdownHeight?'height:'+this.dropdownHeight+'px':'') ;
		this.$arrow[0].style.cssText = 'width:16px;height: 80%;position: absolute;top: 10%;right: 2px;cursor: pointer;';
		this.$arrow.find('.select_glyph')[0].style.cssText = 'position: absolute;border: 4px solid transparent;border-top-color: black;top: 50%;margin-top: -2px;left: 50%;margin-left: -4px;cursor: pointer;';
	},
	activeItem: function(opt){
		opt = $(opt);
		opt.addClass('active').css('background','#eee').siblings().removeClass('active').css('background','');
	},
	addItemClass: function(){
		this.$dropdown.find('.select_item').each(function(index,item){
			item.style.cssText = 'height: 28px;line-height: 28px;padding-left: 5px;background: #fff;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;';
		});
		this.$dropdown.css('height',Math.min(this.dropdownHeight|| 9999,this.options.length*this.$dropdown.children().first().outerHeight()));
		this.$dropdown.find('.active').css('background','#eee');
	},
	value: function(val,allowDirty){		// val为隐藏值 --string
		var option;
		if(val == null){
			return this.$hidden.val();
		}else{
			if(this.loading){
				this.tempValue = val;
			}else{
				if(val === ''){
					this.setValue('');
				}else if(option = this.getOption(val)){
					this.selectItem(option);
				}else if(allowDirty){
					this.setValue(val);
				}
			}
		}
	},
	setValue: function(val){
		if(typeof val === 'string'){
			this.$input.val(val);
			this.$hidden.val(val);
		}else{
			this.$input.val(val.text);
			this.$hidden.val(val.val);
		}
	},
	hasMatch: function(text){
		var ret = false;
		if(text == null){
			text = this.$input.val();
		}
		$.each(this.options,function(index,item){
			if(item.text === text){
				ret = item;
				return false;
			}
		});
		return ret;
	},
	getOption: function(val){
		var ret = null;
		val = String(val);
		$.each(this.options,function(index,item){
			if(item.val === val){
				ret = item;
				return false;
			}
		});
		return ret;
	},
	addHiddenValue: function(option){
		this.$hidden.val(option.val);
	},
	find: function(){
		var val = this.$input.val(),
			ret = [],
			me = this,
			temp;
		if(this.queryRemote){
			var lastVal = me.lastQueryData? me.lastQueryData[me.queryName] : null;
			if((val==='' || val.length>=me.minChars) && val!=lastVal){
				clearTimeout(me.loadTimer);
				me.loadTimer = setTimeout(function(){
					var data = {};
					var temp = me.hasMatch(val);
					if(temp && temp.val === me.$hidden.val()){
						data[me.queryName] = '';
					}else{
						data[me.queryName] = val;
					}
					me.reload($.extend(me.loadData,data));
					ret = me.options;
					me.renderOptions(ret);
					if(ret.length){
						me.open();
					}
				},500);
			}else if(val==lastVal){
				me.open();
			}
		}else{
			var options = this.options;
			if(val===''){
				ret = options;
			}else if(temp = this.hasMatch(val)){
				temp.active = true;
				ret = options;
			}else{
				$.each(options,function(index,item){
					if(item.text.indexOf(val) != -1){
						ret.push(item);
					}
				});
			}
			this.renderOptions(ret);
		}
		return this;
	},
	renderOptions: function(items){
		var me = this;
		var list = [],
			activeCls = '';
		$.each(items,function(index,item){
			if(item.active){
				activeCls = ' active';
				delete item.active;
			}else{
				activeCls = '';
			}
			list.push('<li class="select_item'+activeCls+'"  data-val="'+item.val+'">'+item.text+'</li>');
			if(item.selected){		//赋默认值
				me.selectItem(item);
				delete item.selected;
			}
		});
		this.$dropdown.html(list.join(''));
		this.addItemClass();
		return items.length;
	},
	toggleOpen: function(){
		if(this.isOpen()){
			this.hide();
		}else{
			this.open();
		}
	},
	activeSelect: function(){
		if(this.enabled){
			this.$input.focus();
		}
	},
	isOpen: function(){
		return this.$dropdown.css('display') != 'none';
	},
	open: function(){
		if(this.enabled && (this.$dropdown.children().length || this.queryRemote)){
			this.$dropdown.css('display','block');
		}
	},
	hide: function(){
		this.$dropdown.css('display','none');
	},
	template: 	'<div class="select_wrap" id="$id">' +
			'		<input type="text" class="select_text" />' +
			'		<div class="select_arrow"><span class="select_glyph"></span></div>' +
			'		<ul class="select_dropdown">' +
			'		</ul>' +
			'		<input class="select_val" type="hidden">' +
			'	</div>'
};