var geoCoordMap = {
    "南阳市卧龙区人民法院":[112.528789,32.989877],
    "南阳市宛城区人民法院":[112.544591,32.994857],
    "镇平县人民法院":[112.232722,33.036651],
    "南召县人民法院":[112.435583,33.488617],
    "内乡县人民法院":[111.843801,33.046358],
    "西峡县人民法院":[111.485772,33.302981],
    "方城县人民法院":[113.010933,33.255138],
    "社旗县人民法院":[112.938279,33.056126],
    "唐河县人民法院":[112.838492,32.687892],
    "桐柏县人民法院":[113.406059,32.367153],
    "新野县人民法院":[112.365624,32.524006],
    "邓州市人民法院":[112.092716,32.681642],
    "淅川县人民法院":[111.489026,33.136106],
    "南阳高新技术产业开发区人民法院":[112.563726,33.034017]
};
var convertData = function(data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap[data[i].name];
        // console.log(geoCoord)
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    // console.log(res)
    return res;
};

$.ajax({
    type: "post",
    url: ctx+"module/information/map",
    data: {},
    dataType: "json",
    success: function (data) {
        var arr = [];
// var arr1 = [];
        for (var i = 0; i < data.length; i++) {
            // arr1.push(data[i].count)
            var obj = {};
            obj.value = data[i].count;
            obj.name = data[i].name;
            arr.push(obj)
        }
        option = {
            title: {
                text: '南阳市3D地图',
                x: 'center',
                // top: "10",
                textStyle: {
                    color: '#FFF',
                    // fontSize: 14
                }
            },
            tooltip: {
                show: true,
                formatter:function(params){
                    // console.log(params)
                    var data = params.name + "<br/>"+"工作任务数:"+ params.value[2];
                  return data;
                },
            },
            visualMap: [{
                type: 'continuous',
                seriesIndex: 1,
                text: ['scatter3D'],
                left: 'right',
                //max: 100,
                calculable: true,
                inRange: {
                    color: ['#000', 'blue', 'purple']
                }
            }],
            geo3D: {
                map: '南阳',
                roam: true,
                itemStyle: {
                    color: '#1d5e98',
                    opacity: 1,
                    borderWidth: 0.4,
                    borderColor: '#000'
                },
                // label: {
                //     show: false,
                //     textStyle: {
                //         color: '#000', //地图初始化区域字体颜色
                //         fontSize: 8,
                //         opacity: 1,
                //         backgroundColor: 'rgba(0,23,11,0)'
                //     },
                // },
                emphasis: { //当鼠标放上去  地区区域是否显示名称
                    label: {
                        show: true,
                        textStyle: {
                            color: '#fff',
                            fontSize: 3,
                            backgroundColor: 'rgba(0,23,11,0)'
                        }
                    }
                },
                //shading: 'lambert',
                light: { //光照阴影
                    main: {
                        color: '#fff', //光照颜色
                        intensity: 1.2, //光照强度
                        //shadowQuality: 'high', //阴影亮度
                        shadow: false, //是否显示阴影
                        alpha:55,
                        beta:10

                    },
                    ambient: {
                        intensity: 0.3
                    }
                }
            },
            series: [
                {
                    name: '',
                    type: "scatter3D",
                    coordinateSystem: 'geo3D',
                    symbol: 'pin',
                    symbolSize: 20,
                    // opacity: 0,
                    label: {
                        normal: {
                            show: true,
                            formatter: function (params) {
                                var data = params.name;
                                return data
                            },
                            position: 'top',
                            backgroundColor: 'transparent',
                            padding: [4, 5],
                            borderRadius: 3,
                            borderWidth: 1,
                            borderColor: 'rgba(0,0,0,0.5)',
                            color: '#777',
                            rich: {
                                valueUp: {
                                    color: '#019D2D',
                                    fontSize: 14
                                },
                                valueDown: {
                                    color: 'red',
                                    fontSize: 14
                                }
                            }
                        }
                    },
                    itemStyle: {
                        borderWidth: 0.5,
                        borderColor: '#fff'
                    },
                    data: convertData(arr)
                }]
        }
        echarts.init(document.getElementById('nanyang_map')).setOption(option);
    }
})

