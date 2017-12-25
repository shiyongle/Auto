/*设计图切换*/
function toggleImg(){
	var imgs,left,right,currentImg,len,height;
	dom = $(this);
	imgs = dom.find('img');
	left = dom.find('.left');
	right = dom.find('.right');
	len = imgs.length,
	height = parseInt(dom.css('height'));
	if(len<2){
		left.css('display','none');
		right.css('display','none');
		return;	
	}
	currentImg = imgs.first();
	imgs.css('display',function(index){
		if(index!=0){
			return 'none';
		}
	});
	imgs.load(function(e) {
        if(this.height < height){
			$(this).css('margin-top',(height-this.height)/2);
		}
    });
	left.click(function(){
		if(currentImg[0] != imgs.first()[0]){
			currentImg.css('display','none');
			currentImg = currentImg.prev('img').css('display','inline-block');
		}
		if(currentImg[0] == imgs.first()[0]){
			left.css('display','none');
		}
		right.css('display','');
	});
	right.click(function(){
		if(currentImg[0] != imgs.last()[0]){
			currentImg.css('display','none');
			currentImg = currentImg.next('img').css('display','inline-block');
		}
		if(currentImg[0] == imgs.last()[0]){
			right.css('display','none');
		}
		left.css('display','');
	});
}
function chooseDesign(){
	var checks = $('.design h5');
	checks.click(function(){
		var me = $(this);
		$input = me.parent().toggleClass('selected').find('input[type=checkbox]');
		$input.prop('checked',!$input.prop('checked'));
	});
}