
//myChart.showLoading();

$.ajax({
    type: "post",
    url: ctx + "module/clue/map",
    data: {},
    dataType: "json",
    success: function (data) {

        //myChart.hideLoading();
        var arr = [];
        for (var i = 0; i < data.length; i++) {
            var obj = {};
            obj.value = data[i].count;
            obj.name = data[i].name;
            arr.push(obj)
        }

        // echarts.registerMap('nanyang', data);
        var myCharts = echarts.init(document.getElementById('nanyang_map'));
        option = {
            title: {
                text: '南阳市地图',
                textStyle:{color:'#fff'},
                x:'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b}<br/>{c}'
            },
            toolbox: {
                show: false,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            visualMap: {
                min: 0,
                max: 10000,
                text:['高','低'],
                textStyle:{color:'#fff'},
                realtime: false,
                calculable: true,
                inRange: {
                    color: ['lightskyblue','yellow', 'orangered']
                }
            },
            series: [
                {
                    name: '',
                    type: 'map',
                    mapType: '南阳', // 自定义扩展图表类型
                    itemStyle:{
                        normal:{label:{
                            show:true,
                                formatter: function (params) {
                                    return params.name+"\n"+ params.value;    //地图上展示文字 + 数值
                                }
                            }},
                        emphasis:{label:{show:true}}
                    },
                    data:arr
                }
            ]
        };
        myCharts.setOption(option);

    }
})