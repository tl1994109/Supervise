// 南阳市地图

function analysis(data) {
    var end_obj = [];
    for (var i in data) {
        var obj = {name: '', datas: []};
        obj.name = data[i]['fyName'];
        obj.value = data[i]['allCount'];
        obj.datas[0] = data[i]['dealCount'];
        obj.datas[1] = data[i]['addCount'];
        end_obj.push(obj);
    }
    return end_obj;

}

myMap();
function myMap() {
    var myChart = echarts.init(document.getElementById('nanyang_map'));

    option = {
        title : {
            text: '南阳市地图',
            textStyle:{color:'#fff'},
            x:'center'
        },
        tooltip : {
            trigger: 'item',
//                            formatter: '{b} : {c0}'
            formatter: function (a) {
                return (a['name'] + '</br>' +
                    '线索总数量:' + a['value'] + '<br>' +
                    '办结数量:' + a['data'].datas[0] + '<br>' +
                    '新增数量:' + a['data'].datas[1]
                )
            }
        },
        legend: {
            orient: 'vertical',
            x:'left'
        },
        dataRange: {
            min: 0, //标尺最小值
            max: 2000,   //标尺最大值
            x: 'right',
            y: 'top',
            text:['高','低'],           // 文本，默认为数值文本
            textStyle:{color:'red'},
            calculable : true
        },
        series : [
            {
                name: '',
                type: 'map',
                mapType: '南阳',
                roam: false,
                itemStyle: {
                    normal: {
                        label:{ // 显示地图名称
                            show:true
                        }
                    },
                    emphasis: {  // 地图上的点
                        label:{
                            show:true
                        }
                    }
                },
                data:[]
            }
        ]
    };


    $.getJSON(ctx+"module/clue/queryCountByFy").done(function (res) {
        var data = res.data;
        var name = [];
        for (i in data) {
            name.push(data[i]['fyName']);
        }
        option.series[0].data = analysis(res.data);
        // option.yAxis.data = name;
        option.data = name;
        myChart.setOption(option);
    });
}