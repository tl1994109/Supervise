
var myChart = echarts.init(document.getElementById('nanyang_map'));

myChart.showLoading();

$.get(ctx+"module/clue/queryCountByFy", function (geoJson) {

    myChart.hideLoading();

    echarts.registerMap('nanyang', geoJson);

    // 南阳地级市坐标
    var geoJson = {
        "卧龙区":[112.528789,32.989877],
        "宛城区":[112.544591,32.994857],
        "镇平县":[112.232722,33.036651],
        "南召县":[112.435583,33.488617],
        "内乡县":[111.843801,33.046358],
        "西峡县":[111.485772,33.302981],
        "方城县":[113.010933,33.255138],
        "社旗县":[112.938279,33.056126],
        "唐河县":[112.838492,32.687892],
        "桐柏县":[113.406059,32.367153],
        "新野县":[112.365624,32.524006],
        "邓州市":[112.092716,32.681642],
        "淅川县":[111.489026,33.136106]
    }

    var convertData = function (data) {
        var res = [];
        for (var i = 0; i < data.length; i++) {
            var geoCoord = geoJson[data[i].name];
            if (geoCoord) {
                res.push(geoCoord.concat(data[i].value));
            }
        }
        return res;
    };

    $.ajax({
        type: "post",
        url: ctx+"module/clue/map_two",
        data: {},
        dataType: "json",
        success: function (data) {

            var arr = [];
            var arr1 = [];
            var max1;
            for (var i = 0; i < data.length; i++) {
                arr1.push(data[i].count)
                var obj = {};
                obj.value = data[i].count;
                obj.name = data[i].name;
                arr.push(obj)
            }
            arr1.sort(function(a,b){return a-b;});
            var maxN = arr1[arr1.length-1];
            if(maxN > 500) {
                max1 = maxN
            }else {
                max1 = 500
            }
            myChart.setOption(option = {
                title: {
                    text: '南阳市热力地图',
                    textStyle:{color:'#fff'},
                    x:'center'
                },
                // backgroundColor: '#404a59',
                visualMap: {
                    min: 0,
                    max: max1,
                    splitNumber: 5,
                    inRange: {
                        color: ['#d94e5d','#eac736','#50a3ba'].reverse()
                    },
                    textStyle: {
                        color: '#fff'
                    }
                },
                geo: {
                    map: '南阳',
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    roam: false,  // 禁止地图拖动
                    itemStyle: {
                        normal: {
                            areaColor: '#61a0a8',
                            borderColor: '#111'
                        },
                        emphasis: {
                            areaColor: '#61a0a8'
                        }
                    }
                },
                series: [{
                    name: 'AQI',
                    type: 'heatmap',
                    coordinateSystem: 'geo',
                    data: convertData(arr)
                }]


            });
        }
    });

});