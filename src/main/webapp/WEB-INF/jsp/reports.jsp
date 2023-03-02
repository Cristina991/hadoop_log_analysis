<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/4/1
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/app.css"/>
    <link rel="stylesheet" type="text/css" href="css/order_Reports.css"/>
    <link rel="stylesheet" type="text/css" href="css/chunk.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <!-- 引入 ECharts 文件 -->
    <script src="static/echarts.min.js"></script>
    <script src="static/jquery-3.5.1.min.js"></script>
    <script src="static/reports.js"></script>
</head>
<body onload= "systemTime()">
    <div id="app">
        <div class="screen-container" style="background-color: rgb(22,21,34);color: rgb(255,255,255);">
            <header class="screen-header">
                <div>
                    <img src="images/header_border_dark.png" alt>
                </div>
                <span title="返回后台" class="logo">
                    <a style="color: rgb(255,255,255);" href="index">返回</a>
                    <span class="icon-enter">

                    </span>
                </span>
                <span class="title">用户行为分析</span>
                <div class="title-right">
                    <div>
                        <span class="screenfull-svg icon-enlarge">

                        </span>
                    </div>
                    <div class="datetime" id="start"></div>


                </div>
            </header>
            <div  class="screen-body">
                <section class="screen-left">
                    <div id="left-top" >
                        <div class="dropdown">
                            <span class="before-icon" style="padding-left: 5px;font-weight:bolder ">丨 </span>
                            <span style="font-size: 18px" id="span_title1">周流量分析</span>
                            <span class="icon-arrow-down1" style="font-size: 14.688px;color: rgb(255,255,255);cursor: pointer">

                                </span>
                            <div id="myDropdown1" class="dropdown-content1">
                                <a class="select-item3">周流量分析</a>
                                <a class="select-item4" >日流量分析</a>
                            </div>
                        </div>
                        <div id="main1" style="position: relative;width: 408px;height: 365px;padding: 0px;margin: 0px;border-width: 0px;cursor: default;">

                        </div>
                        <script type="text/javascript">
                            showecharts3();
                        </script>

<%--                        <div class="resize">--%>
<%--                            <span class="iconfont icon-expand-alt " onclick="toggleFullScreen()">--%>

<%--                            </span>--%>
<%--                        </div>--%>
                    </div>
                    <div id="left-bottom">
                        <div class="com-container">
                            <div class="com-chart" style="-webkit-tap-highlight-color: transparent;user-select: none;position: relative;">

                                <div id="main2" style="position: relative;width:408px;height: 270px;padding: 0px;margin: 0px;border-width: 0px;">

                                </div>
                            </div>
<%--

<%--                            </script>--%>
                            <div style="position:absolute;right:40px;top:20px;cursor:pointer">
                                <button id='return-button' value=''>back</button>
                            </div>
                            <script type="text/javascript">

                                var myChart = echarts.init(document.getElementById('main2'));
                                var catid=[];
                                var buyamount=[];
                                var option=null;
                                $.ajax({
                                    type: "post",
                                    url: "/selltopcat",
                                    contentType: "application/json",
                                    async:false,
                                    success: function (res) {

                                        console.log("=====res: =======");
                                        console.log(res);

                                        for (var i = 0; i < res.length; i++) {
                                            catid.push(res[i].catid);
                                            buyamount.push(res[i].buyamount);
                                        }
                                        option = {
                                            title: {
                                                text: '丨热销商品类目/商品',
                                                textStyle: {
                                                    fontSize: 18,
                                                    color: "rgba(238, 241, 250, 1)"
                                                },
                                            },
                                            tooltip: {
                                                trigger: 'axis',
                                                axisPointer: {
                                                    type: 'shadow'
                                                },

                                            },
                                            textStyle: {
                                                color: "#B9B8CE",
                                                font: 12
                                            },
                                            xAxis: {
                                                type: 'category',
                                                data: catid
                                            },
                                            yAxis: {
                                                type: 'value'
                                            },
                                            series: [{
                                                data:buyamount,
                                                type: 'bar',
                                                name: '成交量'
                                            }]
                                        };
                                        myChart.setOption(option,true);
                                    }

                                })


                                myChart.on('click', function (object) {
                                    $.ajax({
                                        type:'get',
                                        async:false,
                                        url:"selltopitem?cat="+object.name,
                                        // contenttype: "application/json",
                                        dataType : 'json',
                                        success : function (res){
                                            var itemid =[];
                                            var buyamount2 = [];
                                            for (var i = 0; i < res.length ; i++) {
                                                itemid.push(res[i].itemid);
                                                buyamount2.push(res[i].buyamount);
                                            }
                                            option.xAxis.data = itemid;
                                            option.series[0].data = buyamount2;
                                            myChart.setOption(option, true);
                                        },
                                        error : function(errorMsg) {
                                            alert(errorMsg.responseText);

                                        }
                                    });
                                })
                                // 使用刚指定的配置项和数据显示图表。
                                myChart.setOption(option,true);

                                $('#return-button').on('click',function(){
                                    option.xAxis.data = catid;
                                    option.series[0].data = buyamount;
                                    myChart.setOption(option, true);
                                })
                            </script>
<%--                            <div class="resize">--%>
<%--                                <span class="iconfont icon-expand-alt" onclick="toggleFullScreen()"></span>--%>
<%--                            </div>--%>
                        </div>
                    </div>

                </section>
                <section class="screen-middle">
                    <div id="middle-top">

                        <div class="com-chart" style="-webkit-tap-highlight-color: transparent;user-select: none;">
                            <div class="dropdown">
                                <span class="before-icon" style="padding-left: 5px;font-weight:bolder ">丨 </span>
                                <span style="font-size: 18px" id="span_title">用户行为习惯(日均)</span>
                                <span class="icon-arrow-down" style="font-size: 14.688px;color: rgb(255,255,255);cursor: pointer">

                                </span>
                                <div id="myDropdown" class="dropdown-content">
                                    <a class="select-item1">用户行为习惯(日均)</a>
                                    <a class="select-item2" >用户日活跃时间段</a>
                                </div>
                            </div>
                            <div id="main3" style="position: relative;width: 621px;height: 430px;padding: 0px;margin: 0px;border-width: 0px;cursor: default;">

                            </div>
                            <script type="text/javascript">
                                 showecharts1();
                            </script>

<%--                            <div class="resize">--%>
<%--                                <span class="iconfont icon-expand-alt" onclick="toggleFullScreen()">--%>

<%--                                </span>--%>
<%--                            </div>--%>
                        </div>
                    </div>
                    <div id="middle-bottom">
                        <div class="com-container">
                            <div>
                                <span class="before-icon" style="padding-left: 5px;font-weight:bolder ">丨 </span>
                                <span style="font-size: 18px" id="span_title3">用户复购分析</span>
                            </div>
                            <div style="margin-left: 5px">
                            <div class="left-col" style="margin: 10px;width: 30%">
                                <div class="circle out" style="margin: 10px;border: 2px solid #c95e63;background: #c95e63;">
                                    <div class="circle in" style="border: 2px solid #c95e63;">
                                        <p class="grade_num" id="again"style="color: #c95e63;"></p>
                                        <p class="grade_show_text" style="color: #4a5064">复购人数</p>
                                    </div>
                                </div>
                            </div>
                            <div class="center-col" style="margin: 5px;width: 30%">
                                <div class="circle out" style="margin: 10px;border: 2px solid #9a60b4;background: #9a60b4;">
                                    <div class="circle in" style="border: 2px solid #9a60b4;">
                                        <p class="grade_num" id="buynum" style="color: #9a60b4;"></p>
                                        <p class="grade_show_text" style="color: #4a5064">购买人数</p>
                                    </div>
                                </div>
                            </div>
                            <div class="right-col" style="margin: 5px;width: 30%">
                                <div class="circle out" style="margin: 10px;border: 2px solid #fc8452;background: #fc8452;">
                                    <div class="circle in" style="border: 2px solid #fc8452;">
                                        <p class="grade_num" id="againrate" style="color: #fc8452;"></p>
                                        <p class="grade_show_text" style="color: #4a5064">复购率</p>
                                    </div>
                                </div>
                            </div>
                            </div>
                            <script type="text/javascript">
                                //请求后台数据
                                $.ajax({
                                    type: "post",
                                    url: "/againrate",
                                    contentType: "application/json",
                                    success: function(res){
                                        console.log("=====res: =======");
                                        console.log(res);
                                       document.getElementById("again").innerHTML=eval("("+res.again+")");
                                       document.getElementById("buynum").innerHTML=res.buynum;
                                       document.getElementById("againrate").innerHTML=(res.againrate*100)+"%";

                                    },
                                    error : function(errorMsg) {
                                        alert("不好意思,请求数据失败啦!");
                                    }
                                });
                            </script>


                        </div>
                    </div>



                </section>
                <section class="screen-right">
                    <div id="right-top">
                    <div class="com-container">
                        <div class="com-chart" style="-webkit-tap-highlight-color: transparent;user-select: none;position: relative;">
                            <div id="main5" style="position: relative;width: 413px;height: 352px;padding: 0px;margin: 0px;border-width: 0px;cursor: default;">

                            </div>
                        </div>
                        <script type="text/javascript">
                            // 基于准备好的dom，初始化echarts实例
                            var myChart5 = echarts.init(document.getElementById('main5'));
                            var behaviortype=[];
                            var behavioramount=[];
                            var rate=[];
                            //请求后台数据
                            $.ajax({
                                type: "post",
                                url: "/rate",
                                contentType: "application/json",
                                success: function(res){
                                    console.log("=====res: =======");
                                    console.log(res);
                                    for (var i = 0; i < res.length ; i++) {
                                        behaviortype.push(res[i].behaviortype);
                                        behavioramount.push(res[i].behavioramount);
                                        rate.push(res[i].rate);
                                    }
                                    myChart5.setOption({
                                        title: {
                                            text: '丨整体转化情况',
                                            textStyle: {
                                                fontSize: 18,
                                                color: "rgba(238, 241, 250, 1)"
                                            },
                                        },
                                        textStyle:{
                                            color:"#B9B8CE",
                                            font:12

                                        },
                                        tooltip: {
                                            trigger: 'item',
                                            // formatter: "{a} <br/>{b} : {c}:%"
                                            formatter: function (params) {
                                                return params.data.name+"行为数："+"<br/>" +params.data.percent;
                                            }
                                        },

                                        series: [
                                            {

                                                type:'funnel',
                                                left: '5%',
                                                top: 60,
                                                //x2: 80,
                                                bottom: 60,
                                                width: '70%',
                                                // height: {totalHeight} - y - y2,
                                                min: 0,
                                                max: 100,
                                                minSize: '5%',
                                                maxSize: '90%',
                                                sort: 'descending',
                                                gap: 2,
                                                label: {
                                                    show: true,
                                                    // position: 'inside'
                                                    formatter:"{b}:{c}"+"%",
                                                },
                                                labelLine: {
                                                    length: 20,
                                                    lineStyle: {
                                                        width: 1,
                                                        type: 'solid'
                                                    }
                                                },
                                                itemStyle: {
                                                    borderColor: '#fff',
                                                    borderWidth: 1
                                                },
                                                emphasis: {
                                                    label: {
                                                        fontSize: 15
                                                    }
                                                },
                                                data: [
                                                    {value:rate[0],name:behaviortype[0],percent:behavioramount[0]},
                                                    {value:rate[1],name:behaviortype[1],percent:behavioramount[1]},
                                                    {value:rate[2],name:behaviortype[2],percent:behavioramount[2]},
                                                    {value:rate[3],name:behaviortype[3],percent:behavioramount[3]},

                                                ]
                                            }
                                        ]
                                    });
                                },
                                error : function(errorMsg) {

                                    alert("不好意思,图表请求数据失败啦!");

                                    myChart3.hideLoading();

                                }
                            });

                        </script>

                    </div>
<%--                    <div class="resize">--%>
<%--                        <span class="iconfont icon-expand-alt" onclick="toggleFullScreen()"></span>--%>
<%--                    </div>--%>
                    </div>
                    <div id="right-bottom">
                        <div class="com-container">
                            <div class="com-chart" style="-webkit-tap-highlight-color: transparent;user-select: none;position: relative;">
                                <div id="main6" style="position: relative;width: 413px;height: 274px;padding: 0px;margin: 0px;border-width: 0px;cursor: default;">

                                </div>
                            </div>

                        </div>


                        <script type="text/javascript">
                            var dom = document.getElementById("main6");
                            var myChart6 = echarts.init(dom);
                            var res=[];
                            //请求后台数据
                            $.ajax({
                                type: "post",
                                url: "/userrate",
                                contentType: "application/json",
                                success: function(data){
                                    console.log("=====res: =======");
                                    console.log(data);
                                    for (var i = 0; i < data.length ; i++) {
                                        var obj = new Object();
                                        obj.value = data[i].amount,obj.name = data[i].userrate;
                                        res.push(obj);


                                    }
                                    myChart6.setOption({
                                        title: {
                                            text: '丨用户价值分类',
                                            textStyle: {
                                                fontSize: 18,
                                                color: "rgba(238, 241, 250, 1)"
                                            },
                                        },
                                        textStyle:{
                                            color:"#B9B8CE",
                                            font:12

                                        },
                                        tooltip: {
                                            trigger: 'item',
                                        },
                                        legend: {
                                            top: "15%",
                                            padding:0,
                                            date:res.name,
                                            textStyle: {
                                                color: "#B9B8CE",

                                            }
                                        },

                                        series: [
                                            {
                                                name: '用户价值',
                                                type: 'pie',

                                                minAngle: 3,
                                                top:'25%',
                                                data:res,
                                                label:{
                                                    color: "#B9B8CE",
                                                },

                                                emphasis: {
                                                    itemStyle: {
                                                        shadowBlur: 10,
                                                        shadowOffsetX: 0,
                                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                                    }
                                                }
                                            }
                                        ]
                                    });
                                },
                                error : function(errorMsg) {

                                    alert("不好意思,图表请求数据失败啦!");

                                    myChart6.hideLoading();

                                }
                            });

                        </script>
<%--                        <div class="resize">--%>
<%--                            <span class="iconfont icon-expand-alt" onclick="toggleFullScreen()"></span>--%>
<%--                        </div>--%>
                    </div>

                </section>
            </div>
        </div>
    </div>

</body>
</html>
