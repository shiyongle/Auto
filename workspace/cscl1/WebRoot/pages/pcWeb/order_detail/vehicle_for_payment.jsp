<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>整车待付款--订单详情</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/OrderDetails.css"  rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/vehicle_for_payment.js"></script>
</head>
<body>


<div class="body">
    <div class="body_info jumbotron">
        <div>
            <div class="row">
                <div class="col-xs-10  col-md-3 message">
                    <span>运单号：</span>
                    <span class="text-describe">201512210001</span>
                </div>
                <div class="col-xs-12 col-md-3 message">
                    <span>用车时间：</span>
                    <span class="text-describe">2015-12-22 14:20</span>
                </div>
                <div class="col-xs-12 col-md-3 message">
                    <span>下单时间：</span>
                    <span class="text-describe">2015-12-22 14:20</span>
                </div>
                <div class="col-xs-12 col-md-3 message">
                <span>
                    <span class="text-warning">整车</span>
                    <span class="text-warning">代付款</span>
                </span>
                </div>
            </div>
        </div>
        <hr>
        
        <!--提货点卸货点图-->
        <!--<div>
            <ul>
                <span class="Address-list-Line"></span>
                <li>
                    <span class="Address-list-box">
                        <img style="position: absolute;left: -38px;top: -5px;" src="${ctx}/pages/pcWeb/css/images/order_details/ti.png">
                    </span>
                    <span>
                        <span>
                            提货点：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>

                <li>
                    <span class="Address-list-box">
                        <span class="Address-list-dont"></span>
                    </span>
                    <span>
                        <span>
                            卸货点：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>

                <li>
                    <span class="Address-list-box">
                        <span class="Address-list-dont"></span>
                    </span>
                    <span>
                        <span>
                            卸货点：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>

                <li>
                    <span class="Address-list-box">
                        <span class="Address-list-dont"></span>
                    </span>
                    <span>
                        <span>
                            卸货点：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>


                <li>
                    <span class="Address-list-box">
                        <img style="position: absolute;left: -39px;top: -9px;" src="${ctx}/pages/pcWeb/css/images/order_details/zd.png">
                    </span>

                    <span>
                        <span>
                            卸货点：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>
            </ul>
            <ul>
                <li class="margin-bottom-10">
                    <span>
                        <span>
                            卸货点数：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>
                <li>
                    <span>
                        <span>
                            里程：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>
            </ul>
        </div>
        <hr style="border-top: 1px dashed #eee">-->
        
        <!--提货点卸货点信息-->
        <div>
        	<div class="row margin-bottom-22">
        		 <div class="col-xs-12  message">
                    <span>
                        <span>提货点：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                        <span>卸货点：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                        <span>卸货点数：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                        <span>里程：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
        	</div>
        </div>
        
        <hr style="border-top: 1px dashed #eee">
        <div>
            <div class="row">
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>货物类型：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>所需车型：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
            </div>
            <div class="row ">
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>其他：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>注意事项：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px dashed #eee">
        <div>
            <div class="row margin-bottom-22">
                <div class="col-xs-6 col-md-4">
                    <span>
                        <span>增值服务：</span>
                        <span class="text-describe">XXX</span>
                    </span>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px dashed #eee">
        <div>
            <ul class="margin-bottom-18">
                <li>
                    <span class="text-warning">
                        <span>
                            应付款：
                        </span>
                        <span>
                            XXXXX
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            好运劵：
                        </span>
                        <span>
                            XXXXX
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            追加款：
                        </span>
                        <span>
                            XXXXX
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            实付款：
                        </span>
                        <span>
                            XXXXX
                        </span>
                    </span>
                </li>
            </ul>
        </div>
        <hr>
        <div class="row">
            <div class="col-xs-12" style="margin-bottom: 20px; text-align: right;">
                <button type="button" class="btn btn-danger">付款</button>
                <button type="button" class="btn ">取消订单</button>
            </div>
        </div>

    </div>
</div>


</body>
</html>