 // window.onload = function(){ showecharts1(); }
//获取系统时间，将时间以指定格式显示到页面。
function systemTime()
{
    //获取当前时间
    var date = new Date();
    //格式化为本地时间格式
    var date1 = date.toLocaleString();
    //获取div
    var div1 = document.getElementById("start");
    //将时间写入div
    div1.innerHTML = date1;

    //每隔1000ms执行方法systemTime()。
    setTimeout("systemTime()",1000);
}

//补位函数。
function extra(x)
{
    //如果传入数字小于10，数字前补一位0。
    if(x < 10)
    {
        return "0" + x;
    }
    else
    {
        return x;
    }
}

$(document).on('click', '.icon-arrow-down', function(e){
    // alert("hello!");

    // var div1 = document.getElementById("select-item");
    //     // div1.style.display='block';
    document.getElementById("myDropdown").classList.toggle("show");
    //将时间写入div
});

 $(document).on('click', '.icon-arrow-down1', function(e){
     // alert("hello!");

     // var div1 = document.getElementById("select-item");
     //     // div1.style.display='block';
     document.getElementById("myDropdown1").classList.toggle("show");
     //将时间写入div
 });

window.onclick = function (ev) {
    if(!ev.target.matches('.icon-arrow-down')){
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for(i=0;i<dropdowns.length;i++){
            var openDropdown = dropdowns[i];
            if(openDropdown.classList.contains('show')){
                openDropdown.classList.remove('show');
            }
        }
    }
    if(!ev.target.matches('.icon-arrow-down1')){
        var dropdowns = document.getElementsByClassName("dropdown-content1");
        var i;
        for(i=0;i<dropdowns.length;i++){
            var openDropdown = dropdowns[i];
            if(openDropdown.classList.contains('show')){
                openDropdown.classList.remove('show');
            }
        }
    }
}

$(document).on('click', '.select-item2', function(e){
   showecharts2();
});

$(document).on('click', '.select-item1', function(e){
    showecharts1();
});

 $(document).on('click', '.select-item3', function(e){
     showecharts3();
 });

 $(document).on('click', '.select-item4', function(e){
     showecharts4();
 });

function showecharts1(){
    document.getElementById("span_title").innerText="用户行为习惯(日均)";
    // 基于准备好的dom，初始化echarts图表
    var myChart3 = echarts.init(document.getElementById('main3'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/habit",
        contentType: "application/json",
        success: function(res){
            var dates=[];
            var pv=[];
            var buy=[];
            var cart=[];
            var fav=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                dates.push(res[i].date);
                pv.push(res[i].pvsum);
                buy.push(res[i].buysum);
                cart.push(res[i].cartsum);
                fav.push(res[i].favsum);
            }
            myChart3.setOption({
                textStyle:{
                    color:"#B9B8CE",
                    font:12

                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#B9B8CE'
                        }
                    }
                },

                legend: {
                    data: ['点击数', '购买数', '加购数','收藏数'],
                    top:'2%',
                    textStyle: {
                        color: "#B9B8CE",

                    }
                },
                xAxis:
                    {
                        type: 'category',
                        data: dates,
                        axisPointer: {
                            type: 'shadow'
                        }
                    },

                yAxis: [
                    {
                        type: 'value',
                    },
                    {
                        type: 'value',
                    }
                ],

                series: [{
                    // 根据名字对应到相应的系列
                    name: '点击数',
                    type: 'bar',
                    data: pv
                },{
                    name: '购买数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:buy
                },{
                    name: '加购数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:cart
                },{
                    name: '收藏数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:fav
                }]
            });
        },
        error : function(errorMsg) {

            alert("不好意思,图表请求数据失败啦!");

            myChart3.hideLoading();

        }
    });

}

function showecharts2(){
    document.getElementById("span_title").innerText="用户日活跃时间段";
    // 基于准备好的dom，初始化echarts图表
    var myChart3 = echarts.init(document.getElementById('main3'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/hour",
        contentType: "application/json",
        success: function(res){
            var hours=[];
            var pv=[];
            var buy=[];
            var cart=[];
            var fav=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                hours.push(res[i].hour);
                pv.push(res[i].pvhour);
                buy.push(res[i].buyhour);
                cart.push(res[i].carthour);
                fav.push(res[i].favhour);
            }
            myChart3.setOption({
                // title: {
                //     text: '丨用户日活跃时间段',
                //     textStyle: {
                //         fontSize: 18,
                //         color: "rgba(238, 241, 250, 1)"
                //     },
                // },
                textStyle:{
                    color:"#B9B8CE",
                    font:12

                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross',
                        crossStyle: {
                            color: '#B9B8CE'
                        }
                    }
                },

                legend: {
                    data: ['点击数', '购买数', '加购数','收藏数'],
                    right:'10%',
                    textStyle: {
                        color: "#B9B8CE",

                    }
                },
                xAxis: {
                    type: 'category',

                    axisPointer: {
                        type: 'shadow'
                    },
                    data: hours
                },
                yAxis: [
                    {
                        type: 'value',
                    },
                    {
                        type: 'value',
                    }
                ],
                series: [{
                    // 根据名字对应到相应的系列
                    name: '点击数',
                    type: 'bar',
                    data: pv
                },{
                    name: '购买数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:buy
                },{
                    name: '加购数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:cart
                },{
                    name: '收藏数',
                    type: 'line',
                    yAxisIndex: 1,
                    data:fav
                }]
            });
        },
        error : function(errorMsg) {

            alert("不好意思,图表请求数据失败啦!");

            myChart3.hideLoading();

        }
    });

}

 function showecharts3(){
     document.getElementById("span_title1").innerText="周流量分析";
     // 基于准备好的dom，初始化echarts图表
     var myChart1 = echarts.init(document.getElementById('main1'));
     // 定义x、y轴数据数组

     //请求后台数据
     $.ajax({
         type: "post",
         url: "/userflow",
         contentType: "application/json",
         success: function(res){
             var date=[];
             var visitors=[];
             var views=[];
             var avgviews=[];
             console.log("=====res: =======")
             console.log(res);
             for (var i = 0; i < res.length ; i++) {
                 date.push(res[i].date);
                 visitors.push(res[i].visitors);
                 views.push(res[i].views);
                 avgviews.push(res[i].avgviews);
             }
             myChart1.setOption({
                 textStyle:{
                     color:"#B9B8CE",
                     font:12

                 },
                 tooltip: {
                     trigger: 'axis',
                     axisPointer: {
                         type: 'cross',
                         crossStyle: {
                             color: '#B9B8CE'
                         }
                     }
                 },

                 legend: {
                     data: ['浏览量', '独立访客数'],
                     right:'10%',
                     textStyle: {
                         color: "#B9B8CE",

                     }
                 },
                 xAxis: {
                     type: 'category',


                     axisPointer: {
                         type: 'shadow'
                     },
                     data: date
                 },
                 yAxis: [
                     {
                         type: 'value',
                         splitLine:false,
                         min: function (value) {
                             return value.min - 2000;
                         },
                         max:function (value) {
                             return value.max + 2000;
                         },
                         axisLine:{show:true},
                     },
                     {
                         type: 'value',
                         min: function (value) {
                             return value.min - 10000;
                         },
                         max: function (value) {
                             return value.max + 10000;
                         },
                         splitLine:false,
                         axisLine:{show:true},

                     }
                 ],
                 series: [{
                     // 根据名字对应到相应的系列
                     name: '独立访客数',
                     type: 'line',
                     data: visitors
                 },{
                     name: '浏览量',
                     type: 'line',
                     yAxisIndex: 1,
                     data:views
                 }]
             });
         },
         error : function(errorMsg) {

             alert("不好意思,图表请求数据失败啦!");

             myChart1.hideLoading();

         }
     });

 }

 function showecharts4(){
     document.getElementById("span_title1").innerText="日流量分析";
     // 基于准备好的dom，初始化echarts图表
     var myChart1 = echarts.init(document.getElementById('main1'));
     // 定义x、y轴数据数组

     //请求后台数据
     $.ajax({
         type: "post",
         url: "/userhourflow",
         contentType: "application/json",
         success: function(res){
             var hours=[];
             var visitors=[];
             var views=[];
             var avgviews=[];
             console.log("=====res: =======")
             console.log(res);
             for (var i = 0; i < res.length ; i++) {
                 hours.push(res[i].date);
                 visitors.push(res[i].visitors);
                 views.push(res[i].views);
                 avgviews.push(res[i].avgviews);
             }
             myChart1.setOption({
                 // title: {
                 //     text: '丨用户日活跃时间段',
                 //     textStyle: {
                 //         fontSize: 18,
                 //         color: "rgba(238, 241, 250, 1)"
                 //     },
                 // },
                 textStyle:{
                     color:"#B9B8CE",
                     font:12

                 },
                 tooltip: {
                     trigger: 'axis',
                     axisPointer: {
                         type: 'cross',
                         crossStyle: {
                             color: '#B9B8CE'
                         }
                     }
                 },

                 legend: {
                     data: ['浏览量', '独立访客数'],
                     right:'10%',
                     textStyle: {
                         color: "#B9B8CE",

                     }
                 },
                 xAxis: {
                     type: 'category',


                     axisPointer: {
                         type: 'shadow'
                     },
                     data: hours
                 },
                 yAxis: [
                     {
                         type: 'value',
                         splitLine:false,
                         min: function (value) {
                             return value.min - 2000;
                         },
                         max:function (value) {
                             return value.max + 2000;
                         },
                         axisLine:{show:true},
                     },
                     {
                         type: 'value',
                         min: function (value) {
                             return value.min - 10000;
                         },
                         max: function (value) {
                             return value.max + 10000;
                         },
                         splitLine:false,
                         axisLine:{show:true},

                     }
                 ],
                 series: [{
                     // 根据名字对应到相应的系列
                     name: '独立访客数',
                     type: 'line',
                     data: visitors
                 },{
                     name: '浏览量',
                     type: 'line',
                     yAxisIndex: 1,
                     data:views
                 }]
             });
         },
         error : function(errorMsg) {

             alert("不好意思,图表请求数据失败啦!");

             myChart1.hideLoading();

         }
     });

 }

