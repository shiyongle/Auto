Ext.define('DJ.myComponent.viewer.PdfViewer', {
	statics: {
        show: function (fid, titleTxt){
        	if(!titleTxt){
        		titleTxt = 'PDF预览';
        	}
        	
        	var supportsPdfMimeType = (typeof navigator.mimeTypes['application/pdf'] !== "undefined")
        	var isIE = function (){ return !!(window.ActiveXObject || "ActiveXObject" in window); };
        	var supportsPdfActiveX = function (){ return !!(createAXO("AcroPDF.PDF") || createAXO("PDF.PdfCtrl")); };
        	var createAXO = function (type){
        	        var ax;
        	        try {
        	            ax = new ActiveXObject(type);
        	        } catch (e) {
        	            ax = null; //ensure ax remains null
        	        }
        	        return ax;
        	    };
        	var supportsPDFs = (supportsPdfMimeType || (isIE() && supportsPdfActiveX()));
        	
        	var  content = '<iframe style="width:100%;height:100%;" src="viewpdf.do?fid='+fid+'"></iframe>';
        	if(!supportsPDFs){
	        	content = '<p style="color: red;text-align: center;padding-top: 20px;font-size: 20px;">'
	        		+ '无法阅览,您还没有安装PDF阅读器！</br>'
	        		+ '<a href="https://get.adobe.com/cn/reader/" target="_blank">点击下载安装</a>'
	        		+ '</p>';
        	}
        	
        	new Ext.window.Window({
                title: titleTxt,
                height: 600,
                width: 800,
                layout: 'fit',
                items: [{
                    html: content
                }]
            }).show();
        	
        }
    }
});
