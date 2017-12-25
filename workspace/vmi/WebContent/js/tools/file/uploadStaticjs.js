/*
 * 具体的内联框架实现代码，一般无需改动。
 * 
 * 
 */

// Convert divs to queue widgets when the DOM is ready
$(function() {

	var defaultConfigObj = {
		url : "",

		max_file_size : '10mb',
		unique_names : true,
		multiple_queues : true,
		chunk_size : '3mb',

		file_data_name : "upload1",

		// Specify what files to browse for
		filters : [

		],
		multipart : false,
		multipart_params : {}
	};

	//panel里原来就有属性
	var configObj = parent.Ext.getCmp(parent.document
			.getElementById("myMultiFileUploadValue").value);

	configObj = parent.Ext.applyIf(configObj, defaultConfigObj);

	var pluploadQueueT = $("#uploader").pluploadQueue({
		// General settings
		runtimes : configObj.runtimes || 'html5,silverlight,flash,html4,gears,browserplus',
		url : configObj.url,

		max_file_size : configObj.max_file_size,
		unique_names : configObj.unique_names,
		multiple_queues : configObj.multiple_queues,
		chunk_size : configObj.chunk_size,

		file_data_name : configObj.file_data_name,

		// Specify what files to browse for
		filters : configObj.filters,
		multipart : configObj.multipart,
		multipart_params : configObj.multipart_params,
		// Flash settings
		flash_swf_url : 'plupload/js/plupload.flash.swf',
		// Silverlight settings
		silverlight_xap_url : 'plupload/js/plupload.silverlight.xap'
	});
	
	var setListerners = function(uploader) {

		if (configObj.uploadListeners) {

			for (propName in configObj.uploadListeners) {

				uploader.bind(propName, configObj.uploadListeners[propName]);

			}

		}

	}
	
	setListerners(pluploadQueueT);
	
	$('form').submit(function(e) {
		var uploader = $('#uploader').pluploadQueue();
		if (uploader.files.length > 0) {
			// When all files are uploaded submit form
			uploader.bind('StateChanged', function() {
				if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
					$('form')[0].submit();
				}
			});
			
			setListerners(uploader);
			
			uploader.start();
		} else {
			alert('请先上传数据文件.');
		}
		return false;
	});
});
