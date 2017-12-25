<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/vmi/extlib/resources/css/ext-all.css">
		<link rel="stylesheet" type="text/css" href="/vmi/extlib/ux/grid/css/GridFilters.css" />
        <link rel="stylesheet" type="text/css" href="/vmi/extlib/ux/grid/css/RangeMenu.css" />
<!-- ext-all-gray.css \ext-all.css -->
<script type="text/javascript" src="/vmi/extlib/ext-all.js"></script>

<title>Insert title here</title>
<style type="text/css">
.align-center {
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须 */
	width: 100%; /* 给个宽度 顶到浏览器的两边就看不出居中效果了 */
	height: 100%;
	background: white; /* 背景色 */
	text-align: center; /* 文字等内容居中 */
}
</style>

</head>
<script type="text/javascript" src="/vmi/js/MD5.js"></script>
<script type="text/javascript">
Ext.onReady(function MainForm() {
	 getTools: function(){
	        return [{
	            xtype: 'tool',
	            type: 'gear',
	            handler: function(e, target, header, tool){
	                var portlet = header.ownerCt;
	                portlet.setLoading('Loading...');
	                Ext.defer(function() {
	                    portlet.setLoading(false);
	                }, 2000);
	            }
	        }];
	    };

	var tabs= new Ext.TabPanel({  
        id:'tab',  
        region: 'center', // a center region is ALWAYS required for border layout  
        deferredRender: true,  
        activeTab: 0,     // first tab initially active  
        enableTabScroll:true,  
        plain:true,  
        Frame:true,  
        layoutConfig:{   
        animate:true   
        },   
        items:   
        [  
               /*对TabPanel中的第一个Panel进行布局 
                 xtype:'portal'面板就具有自动拖拽效果 
               */  
               {  
                        title:'工作提醒',  
                        layout:'border',  
                        items:  
                        [{  
                            xtype:'portal',  
                            region:'center',  
                            margins:'0 0 0 0',  
                            cmargins:'35 5 5 5',  
                            items:  
                            [  {  
                                columnWidth:.33,  
                                style:'padding:10px 10px 10px 10px',  
                                items:  
                                    [  
                                        {  
                                        id:"PersonInfo",  
                                        title: '个人信息',  
                                        html: ""  
                                        }  
                                         ,  
                                        {  
                                        id:"DevelopMentTaskInfo",  
                                        title: '开发员近期任务进展',  
                                        html: ""  
                                        }  
                                    ]  
                                }  
                                ,  
                                {  
                                columnWidth:.43,  
                                style:'padding:10px 10px 10px 10px',  
                                items:  
                                    [  
                                        {  
                                        id:"TaskChecked",  
                                        title: '任务审核',  
                                        html: ""  
                                        }  
                                    ]  
                                },  
                                {  
                                columnWidth:.23,  
                                style:'padding:10px 10px 10px 10px',  
                                items:  
                                    [  
                                        {  
                                        id:"CurrentDate",  
                                        title: '当前日期',  
                                        html: ""  
                                        },  
                                        {  
                                        id:"NewsList",  
                                        title: '公告栏',  
                                        html: ""  
                                        }  
                                    ]  
                                }  
                            ]  
                        }]  
              }  
//             {  
//                contentEl: 'center2',  
//                title: '工作提醒',  
//                closable: false,  
//                html:"",  
//                frame:true,   
//                autoScroll: true  
//            }  
             
        ]  
    });  
	Ext.create("Ext.app.Portal");
	
	
	
	
	
	
});
</script>

<!---<script type="text/javascript" src="/vmi/js/test.js"></script>-->
<!---<script type="text/javascript" src="/vmi/js/LoginForm.js"></script>  -->

<body>
	<div id="mydiv" class="align-center"></div>

</body>

</html>