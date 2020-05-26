
// 左上
left_top();
function left_top () {
    $.ajax({
        type: "GET",
        url: ctx+"module/clue/queryAllCount",
        data: {},
        dataType: "json",
        success: function (data) {

            $('#clue').find('p').text(data.allClueCount);
            $('#clue_add').find('p').text(data.yearCount);
            $('#clue_handle').find('p').text(data.dealCount);
            $('#clue_visit').find('p').text(data.xfCount);
        }
    });
}

// 中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountByAjlb",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 0; i < data.length; i++) {
            var str = "<p>" + data[i].jbxx_ajlb + "：" + data[i].count + "</p>";
            arr.push(data[i].jbxx_ajlb)
            var obj = {};
            obj.value = data[i].count;
            obj.name = data[i].jbxx_ajlb;
            arr1.push(obj)
            $('#left_middle').append(str)
        }
        var bingA = echarts.init(document.getElementById('bingA'));
        var optionbingA = {
            title: {
                x: "center"
            },
            tooltip: {
                trigger: "item",
                formatter: "{a} <br/>{b} : {c} ({d}%)",
                show: true,
                axisPointer: {
                    type: "line"
                }
            },
            color: ['#005fbf', '#43afff', '#8dc6ff', '#005594'],
            legend: {
                x: "left",
                orient: "vertical",
                y: "center",
                data: arr,
                textStyle: {
                    color: ['#005fbf', '#43afff', '#8dc6ff', '#005594']
                },
                // backgroundColor: "rgba(0,0,0,0)"
            },
            toolbox: {
                show: true,
                feature: {
                    // dataView: {
                    //     show: true,
                    //     readOnly: true
                    // },
                    // restore: {
                    //     show: true
                    // },
                    // saveAsImage: {
                    //     show: true
                    // }
                },
                showTitle: true
            },
            calculable: true,
            series: [{
                name: "案件类别占比",
                type: "pie",
                radius: [30, 90],
                center: ["50%", "50%"],
                roseType: "area",
                data: arr1
            }]
        }
        bingA.setOption(optionbingA);
    }
});

// 中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/findCounByAgeAndAjlb",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = data[0].data; // 类型 名称与数据
        var arrr = data[0].secondData; // 年龄 名称与数据
        var arr1 = ["20-30岁", "31-40岁", "41-50岁", "51-60岁", "60岁以上"]  // 所有名称
        var arr2 = [];
        var obj = {}
        for (var i = 0; i < arr.length; i++) {
            arr1.push(arr[i].jbxx_ajlb);
            obj.value = arr[i].count;
            obj.name = arr[i].jbxx_ajlb;
            arr2.push(obj)
        }

        var zhuA = echarts.init(document.getElementById('zhuA'));
        var optionzhuA = {
            tooltip: {
                trigger: "item",
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            grid: {
                // left: '3%',
                right: '4%',
                // bottom: '3%',
                containLabel: true
            },
            legend: {
                orient: "vertical",
                x: "left",
                data: arr1,
                textStyle: {
                    color: "rgb(255, 255, 255)"
                }
            },
            toolbox: {
                feature: {
                    dataView: {
                        readOnly: true
                    }
                },
                show: false
            },
            calculable: false,
            series: [{
                name: "相关人员",
                type: "pie",
                color: ['#005fbf', '#43afff', '#8dc6ff', '#005594'],
                selectedMode: "single",
                radius: [0, 50],
                center : ['65%', '50%'],
                itemStyle: {
                    normal: {
                        label: {
                            position: "inside"
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                data: arr2
            },
                {
                    name: "相关人员",
                    type: "pie",
                    color: ["#91c7ae","#749f83","#e57b5c","#61a0a8","#bda29a",],
                    radius: [70, 100],
                    center : ['65%', '50%'],
                    data: [{
                        value: arrr[0]['20-30'],
                        name: "20-30岁"
                    },
                        {
                            value: arrr[1]['31-40'],
                            name: "31-40岁"
                        },
                        {
                            value: arrr[2]['41-50'],
                            name: "41-50岁"
                        },
                        {
                            value: arrr[3]['51-60'],
                            name: "51-60岁"
                        },
                        {
                            value: arrr[4]['60以上'],
                            name: "60岁以上"
                        }
                    ]
                }
            ]
        }
        zhuA.setOption(optionzhuA);
    }
});

// 左下
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountByAjlbAndCbt",
    data: {},
    dataType: "json",
    success: function (data) {

        var zhuB = echarts.init(document.getElementById('zhuB'));
        var optionzhuB = {
            tooltip: {
                trigger: "axis"
            },
            legend: {
                data: ["筛查"],
                x: "center",
                backgroundColor: "rgba(0,0,0,0)",
                textStyle: {
                    color: "rgb(191, 0, 0)"
                },
                y: "bottom"
            },
            toolbox: {
                show: true,
                feature: {
                    // dataView: {
                    //     readOnly: true
                    // },
                    // magicType: {
                    //     type: ["line", "bar"],
                    //     show: false
                    // }
                }
            },
            calculable: true,
            xAxis: [{
                type: "category",
                data: data[1],
                name: "庭院",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "#43afff"
                    }
                },
                axisLabel: {
                    interval:0,
                    rotate:-30,
                },
                splitLine: {
                    show: false
                }
            }],
            yAxis: [{
                type: "value",
                name: "线索数量",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            }],
            series: [{
                name: "线索",
                type: "bar",
                data: data[0],
                color: "#43afff",
                smooth: true,
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
            }],
            title: {
                x: "center"
            },
            grid: {
                borderWidth: 0
            },
            backgroundColor: "rgba(0,0,0,0)"
        }
        zhuB.setOption(optionzhuB);
    }
});

// 左中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountByRiskLevel",
    data: {},
    dataType: "json",
    success: function (data) {

        var bingB = echarts.init(document.getElementById('bingB'));
        var arr = data[0];
        var arr1 = [];
        var arr2 = [];
        var obj = {}
        for (var i = 0; i < arr.length; i++) {
            arr1.push(arr[i].jbxx_ajlb);
        }
        for (var i = 1; i < data.length; i++) {
            var sss = '';
            if(data[i].riskLevel == 1) {
                sss = "轻风险"
            }else if(data[i].riskLevel == 2) {
                sss = "中风险"
            }else if(data[i].riskLevel == 3) {
                sss = "高风险"
            }
            obj.type = 'bar';
                obj.data= data[i].countList;
                obj.coordinateSystem= 'polar';
                obj.name= sss;
                obj.stack= 'a';
                arr2.push(obj)
        }

        var optionbingB = {
            angleAxis: {
                type: 'category',
                data: arr1,
                z: 10,
                axisLine: {
                    lineStyle: {
                        color: '#fff',
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {

                }
            },
            tooltip: {
                show: true,
                formatter: function (params) {
                    var id = params.dataIndex;
                    return data[0][id].jbxx_ajlb + '<br>轻风险：' + data[2].countList[id] + '<br>中风险：' + data[1].countList[id] + '<br>高风险：' + data[3].countList[id];
                }
            },
            color:['#8dc6ff','#005fbf', '#43afff'],
            legend: {
                data: arr1,
            },
            radiusAxis: {
                // nameTextStyle: {
                //     color: '#fff'
                // }
            },
            polar: {},
            series: arr2,
            legend: {
                show: true,
                data: ['轻风险', '中风险', '高风险'],
                textStyle: {
                    color: "#fff"
                },
                y: "bottom",
                x: "left",
            }
        };
        bingB.setOption(optionbingB);
    }
});

// 右上
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountByAjlbAndMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var lineA = echarts.init(document.getElementById('lineA'));
        var optionlineA = {
            tooltip: {
                trigger: "axis"
            },
            color: ['#9fdabf', '#43afff', '#8dc6ff', '#005594'],
            legend: {
                data: ["刑事", "民事", "行政", "执行"],
                x: "left",
                y: "bottom",
                backgroundColor: "rgba(0,0,0,0)",
                textStyle: {
                    color: "rgb(255, 255, 255)"
                }
            },
            toolbox: {
                show: true,
                feature: {

                }
            },
            calculable: true,
            xAxis: [{
                type: "category",
                boundaryGap: false,
                data: data[1],
                name: "月份",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                splitLine: {
                    show: false
                }
            }],
            yAxis: [{
                type: "value",
                name: "案件数量",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            }],
            series: [{
                name: "刑事",
                type: "line",
                data: data[0].xs,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                }
            },
                {
                    name: "民事",
                    type: "line",
                    data: data[0].ms,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "bottom"
                            }
                        }
                    }
                },
                {
                    type: "line",
                    name: "行政",
                    data: data[0].xz,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "right"
                            }
                        }
                    }
                },
                {
                    type: "line",
                    name: "执行",
                    data: data[0].zx,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "left"
                            }
                        }
                    }
                }
            ],
            // title: {
            //     subtext: "最近6月统计",
            //     x: "center"
            // },
            grid: {
                borderWidth: 0
            }
        }
        lineA.setOption(optionlineA);
    }
});

// 右下
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountBySourceAndMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var lineB = echarts.init(document.getElementById('lineB'));
        var optionlineB = {
            tooltip: {
                trigger: "axis"
            },
            legend: {
                data: ["信访", "筛查","筛查已办","信访已办"],
                x: "center",
                y: "bottom",
                orient: "horizontal",
                textStyle: {
                    color: "rgb(255, 255, 255)"
                },
                selected: {
                    '筛查已办': false,
                    '信访已办': false,
                }
            },
            color:["#9fdabf","#005594","#43afff","#005fbf",],
            toolbox: {
                show: true,
                feature: {
                    // dataView: {
                    //     readOnly: true
                    // },
                    // magicType: {
                    //     type: ["line", "bar", "stack", "tiled"],
                    //     show: false
                    // }
                }
            },
            calculable: true,
            xAxis: [{
                type: "category",
                boundaryGap: false,
                data: data[1],
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    show: true
                }
            }],
            yAxis: [{
                type: "value",
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                }
            }],
            series: [{
                name: "筛查已办",
                type: "line",
                stack: "总量",
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: "default",
                            // color: "rgb(0, 95, 191)"
                        },
                        label: {
                            show: true
                        }
                    }
                },
                data: data[0][3],
                smooth: true
            },
                {
                    name: "信访已办",
                    type: "line",
                    stack: "总量",
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: "default",
                                // color: "rgb(0, 95, 191)"
                            },
                            label: {
                                show: true
                            }
                        }
                    },
                    data: data[0][4],
                    smooth: true
                },{
                name: "信访",
                type: "line",
                color:["#9fdabf","#005594","#43afff","#005fbf",],
                stack: "总量",
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: "default",
                            // color: "#1DBB37"
                        },
                        label: {
                            show: true
                        }
                    }
                },
                data: data[0][2],
                smooth: true
            },
                {
                    name: "筛查",
                    type: "line",
                    stack: "总量",
                    itemStyle: {
                        normal: {
                            areaStyle: {
                                type: "default",
                                // color: "rgb(0, 95, 191)"
                            },
                            label: {
                                show: true
                            }
                        }
                    },
                    data: data[0][1],
                    smooth: true
                },

            ]
        }
        lineB.setOption(optionlineB);
    }
});

// 右中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryRiskCountByMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var lineC = echarts.init(document.getElementById('lineC'));
        var optionlineC = {
            tooltip: {
                trigger: "axis"
            },
            color: ['#9fdabf', '#43afff', '#8dc6ff', '#005594'],
            legend: {
                data: ["轻风险", "中风险", "高风险",],
                x: "left",
                y: "bottom",
                backgroundColor: "rgba(0,0,0,0)",
                textStyle: {
                    color: "rgb(255, 255, 255)"
                }
            },
            toolbox: {
                show: true,
                feature: {
                    // mark: {
                    //     show: true
                    // },
                    // dataView: {
                    //     show: true,
                    //     readOnly: true
                    // },
                    // magicType: {
                    //     show: false,
                    //     type: ["line", "bar"]
                    // },
                    // restore: {
                    //     show: true
                    // },
                    // saveAsImage: {
                    //     show: true
                    // }
                }
            },
            calculable: true,
            xAxis: [{
                type: "category",
                boundaryGap: false,
                data: data[1],
                name: "月份",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                splitLine: {
                    show: false
                }
            }],
            yAxis: [{
                type: "value",
                name: "案件数量",
                nameTextStyle: {
                    color: "rgb(255, 255, 255)"
                },
                axisLine: {
                    lineStyle: {
                        color: "rgb(255, 255, 255)"
                    }
                },
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            }],
            series: [{
                name: "轻风险",
                type: "line",
                data: data[0].lowRisk,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                }
            },
                {
                    name: "中风险",
                    type: "line",
                    data: data[0].centerRisk,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "bottom"
                            }
                        }
                    }
                },
                {
                    type: "line",
                    name: "高风险",
                    data: data[0].highRisk,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "right"
                            }
                        }
                    }
                },

            ],
            // title: {
            //     subtext: "最近6月统计",
            //     x: "center"
            // },
            grid: {
                borderWidth: 0
            }
        }
        lineC.setOption(optionlineC);
    }
});
