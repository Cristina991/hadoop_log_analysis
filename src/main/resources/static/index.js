function show(idName) {
    var secondMenuId = document.getElementById(idName);

    if(secondMenuId.style.display == "none") {
        secondMenuId.style.display = "block"
    }else{
        secondMenuId.style.display = "none"
    }
}

function toggleFullScreen() {
    if (!document.fullscreenElement && !document.msFullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement) {
        var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        else if (docElm.msRequestFullscreen) {  //IE
            docElm = document.body; //overwrite the element (for IE)
            docElm.msRequestFullscreen();
        }
        else if (docElm.mozRequestFullScreen) { //火狐
            docElm.mozRequestFullScreen();
        }
        else if (docElm.webkitRequestFullScreen) {  //谷歌
            docElm.webkitRequestFullScreen();
        }
    } else {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        }
    }
}

function showtotalpvs(){
    var div1 = document.getElementById("pvtotal");

        //请求后台数据
        $.ajax({
            type: "post",
            url: "/pvstotal",
            contentType: "application/json",
            success: function(res) {
                console.log("=====res: =======")
                console.log(res);
                div1.innerText=res;

            },
            error : function(errorMsg) {
                alert("不好意思,请求数据失败啦!");
            }
        });
}

function showtotalip(){
    var div1 = document.getElementById("iptotal");

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/iptotal",
        contentType: "application/json",
        success: function(res) {
            console.log("=====res: =======")
            console.log(res);
            div1.innerText=res;

        },
        error : function(errorMsg) {
            alert("不好意思,请求数据失败啦!");
        }
    });
}

function showavgPv(){
    var div1 = document.getElementById("avgPv");

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/avgpv",
        contentType: "application/json",
        success: function(res) {
            console.log("=====res: =======")
            console.log(res);
            div1.innerText=res.toFixed(2);

        },
        error : function(errorMsg) {
            alert("不好意思,请求数据失败啦!");
        }
    });
}

function showavgVisit() {
    var div1 = document.getElementById("avgVisit");

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/avgvisit",
        contentType: "application/json",
        success: function(res) {
            console.log("=====res: =======")
            console.log(res);
            div1.innerText=res.toFixed(2);

        },
        error : function(errorMsg) {
            alert("不好意思,请求数据失败啦!");
        }
    });
}

$(document).on('click', '#select_item1', function(e){
    showechart1();
});

$(document).on('click', '#select_item2', function(e){
    showecharts2();
});

$(document).on('click', '#select_item3', function(e){
    showecharts3();
});

$(document).on('click', '#select_item4', function(e){
    showecharts4();
});

function showechart1() {
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('main1'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/pvseveryhour",
        contentType: "application/json",
        success: function(res){
            var dates=[];
            var pvs=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                dates.push(res[i].month+"."+res[i].day+" "+res[i].hour+":00");
                pvs.push(res[i].pvs);
            }
            myChart1.setOption({
                title: {
                    text: '丨一天中的各小时页面浏览量',
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

                },
                xAxis: {
                    type: 'category',
                    axisPointer: {
                        type: 'shadow'
                    },
                    data: dates
                },
                yAxis: [
                    {
                        type: 'value',
                        splitLine:false,
                        axisLine:{show:true},
                    },
                ],
                series: [{
                    // 根据名字对应到相应的系列
                    name: '页面浏览量',
                    type: 'line',
                    data: pvs
                }
                ]
            });
        },
        error : function(errorMsg) {
            alert("不好意思,图表请求数据失败啦!");
            myChart1.hideLoading();
        }
    });

}

function showecharts2() {
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('main1'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/ipeveryhour",
        contentType: "application/json",
        success: function(res){
            var dates=[];
            var dstcipcnts=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                dates.push(res[i].hour);
                dstcipcnts.push(res[i].dstcipcnts);
            }
            myChart1.setOption({
                title: {
                    text: '丨一天中的各小时独立访客数',
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

                },
                xAxis: {
                    type: 'category',
                    axisPointer: {
                        type: 'shadow'
                    },

                    data: dates
                },
                yAxis: [
                    {
                        type: 'value',
                        splitLine:false,
                        axisLine:{show:true},
                    },
                ],
                series: [{
                    // 根据名字对应到相应的系列
                    name: '独立访客数',
                    type: 'line',
                    data: dstcipcnts
                }
                ]
            });
        },
        error : function(errorMsg) {

            alert("不好意思,图表请求数据失败啦!");

            myChart1.hideLoading();

        }
    });

}

function showecharts3() {
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('main1'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/returning",
        contentType: "application/json",
        success: function(res){
            var addr=[];
            var cnt=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                addr.push(res[i].remoteaddr);
                cnt.push(res[i].acccnt);
            }
            myChart1.setOption({
                title: {
                    text: '丨回头访客访问次数Top10',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },

                },
                xAxis: {
                    type: 'category',
                    data: addr,
                    axisLabel: {
                        interval:0
                    }
                },
                legend: {

                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data:cnt,
                    type: 'bar',
                    name: '访问次数'
                }]
            });
        },
        error : function(errorMsg) {

            alert("不好意思,图表请求数据失败啦!");

            myChart1.hideLoading();

        }
    });

}

function showecharts4() {
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('main1'));
    // 定义x、y轴数据数组

    //请求后台数据
    $.ajax({
        type: "post",
        url: "/hotpages",
        contentType: "application/json",
        success: function(res){
            var url=[];
            var pvs=[];
            console.log("=====res: =======")
            console.log(res);
            for (var i = 0; i < res.length ; i++) {
                url.push(res[i].url);
                pvs.push(res[i].pvs);
            }
            myChart1.setOption({
                title: {
                    text: '丨最热门的页面Top10',
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },

                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        // 坐标轴刻度标签换行处理
                        formatter: function (params) {
                            var newParamsName = ""; // 最终拼接成的字符串
                            var paramsNameNumber = params.length; // 实际标签的个数
                            var provideNumber =12; // 每行能显示的字的个数
                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber); // 换行的话，需要显示几行，向上取整
                            /**
                             * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                             */
                            // 条件等同于rowNumber>1
                            if (paramsNameNumber > provideNumber) {
                                /** 循环每一行,p表示行 */
                                for (var p = 0; p < rowNumber; p++) {
                                    var tempStr = ""; // 表示每一次截取的字符串
                                    var start = p * provideNumber; // 开始截取的位置
                                    var end = start + provideNumber; // 结束截取的位置
                                    // 此处特殊处理最后一行的索引值
                                    if (p == rowNumber - 1) {
                                        // 最后一次不换行
                                        tempStr = params.substring(start, paramsNameNumber);
                                    } else {
                                        // 每一次拼接字符串并换行
                                        tempStr = params.substring(start, end) + "\n";
                                    }
                                    newParamsName += tempStr; // 最终拼成的字符串
                                }
                            } else {
                                // 将旧标签的值赋给新标签
                                newParamsName = params;
                            }
                            //将最终的字符串返回
                            return newParamsName;
                        },
                        interval:0
                    },
                    data: url
                },
                legend: {

                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data:pvs,
                    type: 'bar',
                    name: '页面浏览量'
                }]
            });
        },
        error : function(errorMsg) {
            alert("不好意思,图表请求数据失败啦!");
            myChart1.hideLoading();
        }
    });


}
