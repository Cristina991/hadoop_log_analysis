var drillDown = {
    getOption : function () {
        var option = null;
        option = {
            title: {
                text: 'Basic drilldown',
                left: 'center'
            },
            legend: {
                left: 'left',
                data: ['category']
            },
            xAxis: {
                type: 'category',
                data: ['Animals', 'Fruits', 'Cars']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'category',
                    type: 'bar',

                }
            ]
        };
        $.ajax({
            type: 'get',
            //url : interfaceUrl + '&category=' + object.name, // 此处替换成写逻辑的地方,下面是我自己的
            url: "/category/getAllCategory.do",
            dataType: 'json',
            success: function (msg) {
                option.xAxis.data = msg.xAxis;
                //option.series[0].data = msg.yAxis[0];
                option.series[0].data = msg.yAxis;
                myChart.setOption(option, true);
            }
        });
        return option;
    },
    initChart : function (myChart,option) {
        myChart.setOption(option);
        myChart.on('click',function(object){

            // 初始化
            var myChart = echarts.init(dom);

            $.ajax({
                type: 'get',
                //url : interfaceUrl + '&category=' + object.name, // 此处替换成写逻辑的地方,下面是我自己的
                url: "/category/getAllCategory.do?product=" + object.product,
                dataType: 'json',
                success: function (msg) {
                    option.xAxis.data = msg.xAxis;
                    //option.series[0].data = msg.yAxis[0];
                    option.series[0].data = msg.yAxis;
                    myChart.setOption(option, true);
                }
            });

            myChart.setOption(option, true);
        });
    },
};
