//左上
left_top();
function left_top () {
    $.ajax({
        type: "GET",
        url: ctx+"module/information/queryEveCount",
        data: {},
        dataType: "json",
        success: function (data) {
            $('#clue').find('p').text(data.allCount);
            $('#clue_add').find('p').text(data.addCount);
            $('#clue_ok').find('p').text(data.jxzCount);
            $('#clue_num').find('p').text(data.finishCount);
        }
    });
}

//左中
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryCountWcAndJxz",
    data: {},
    dataType: "json",
    success: function (data) {
        var myWork1 = echarts.init(document.getElementById('work-1'));
        option1 = {
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color: ['#005fbf', "#61a0a8"],
            legend: {
                orient : 'vertical',
                x : 'left',
                y : 'bottom',
                textStyle:{color:'#fff'},
                data:['已完成','进行中']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'监察部门工作总数',
                    type:'pie',
                    radius : '55%',
                    center: ['60%', '50%'],
                    data:[
                        {value:data.jxz, name:'进行中'},
                        {value:data.wc, name:'已完成'}
                    ]
                }
            ]
        };
        myWork1.setOption(option1);
    }
});

// 左下
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryTaskCountByMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 1; i < data.length; i++) {
            arr.push(data[i].name)
            var obj = {};
            obj.data = data[i].value;
            obj.name = data[i].name;
            obj.type = 'line';
            obj.stack = '总量';
            obj.areaStyle = {};
            arr1.push(obj)
        }
        var myWork2 = echarts.init(document.getElementById('work-2'));
        option2 = {
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                textStyle:{color:'#fff'},
                // orient : 'vertical',
                x : 'center',
                y : 'bottom',
                data:arr
            },
            grid: {
                left: '8%',
                right: '9%',
                top: '15%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    name: "月份",
                    boundaryGap : false,
                    data : data[0],
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    }
                }

            ],
            yAxis : [

                {
                    type : 'value',
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    }
                }
            ],
            series : arr1

        };

        myWork2.setOption(option2);
    }
});

//中下
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryByStatusAndMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = data[0].total;
        arr.sort(function(a,b){return a-b;});
        var minN = arr[0];
        var maxN = arr[arr.length-1];
        var maxNs = arr.indexOf(Math.max.apply(Math, arr)) - 1;
        var minNs = arr.indexOf(Math.min.apply(Math, arr))
        var myWork3 = echarts.init(document.getElementById('work-3'));
        option3 = {
            tooltip : {
                trigger: 'axis'
            },
            color: ['#005fbf', "#61a0a8"],
            legend: {
                textStyle:{color:'#fff'},
                data:['完成数量','任务数量']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    },
                    data : data[1]
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    }
                }
            ],
            grid: {
                left: '3%',
                right: '4%',
                bottom: '10%',
                containLabel: true
            },
            series : [
                {
                    name:'完成数量',
                    type:'bar',
                    data:data[0].wc,
                    itemStyle: {
                        normal: {
                            label: {
                                color: '#fff'
                            }
                        }
                    },
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name:'任务数量',
                    type:'bar',
                    data:data[0].total,
                    itemStyle: {
                        normal: {
                            label: {
                                color: '#61a0a8'
                            }
                        }
                    },
                    markPoint : {
                        data : [
                            {name : '年最高', value : maxN, xAxis: minNs, symbolSize:18},
                            {name : '年最低', value : minN, xAxis: maxNs, }
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ]
        };
        myWork3.setOption(option3);
    }
});

//右上
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryTaskCountByMonthAndImportance",
    data: {},
    dataType: "json",
    success: function (data) {

        var myWork4 = echarts.init(document.getElementById('work-4'));
        option4 = {
            tooltip: {
                trigger: "axis"
            },
            color: ['#9fdabf', '#43afff', '#8dc6ff', '#005594'],
            legend: {
                data: ["一般", "重要",],
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
                // name: "月份",
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
                // name: "案件数量",
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
                name: "一般",
                type: "line",
                data: data[0].yb,
                itemStyle: {
                    normal: {
                        label: {
                            show: true
                        }
                    }
                }
            },
                {
                    name: "重要",
                    type: "line",
                    data: data[0].zy,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: "top"
                            }
                        }
                    }
                },

            ],
            grid: {
                borderWidth: 0,
                left: '3%',
                right: '6%',
                bottom: '10%',
                containLabel: true
            }
        };
        myWork4.setOption(option4);
    }
});

// 右中
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryByMonthAndLevel",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 1; i < data.length; i++) {
            arr.push(data[i].name)
            var obj = {};
            obj.data = data[i].value;
            obj.name = data[i].name;
            obj.type = 'line';
            obj.stack = '总量';
            obj.areaStyle = {};
            arr1.push(obj)
        }


        var myWork5 = echarts.init(document.getElementById('work-5'));
        option5 = {
            // title: {
            //     text: '堆叠区域图'
            // },
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                textStyle:{color:'#fff'},
                // orient : 'vertical',
                x : 'center',
                y : 'bottom',
                data:arr
            },
            grid: {
                left: '8%',
                right: '8%',
                bottom: '10%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : data[0],
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    }
                }

            ],
            yAxis : [

                {
                    type : 'value',
                    nameTextStyle: {
                        color: "rgb(255, 255, 255)"
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#fff"
                        }
                    }
                }
            ],
            series : arr1
        };
        myWork5.setOption(option5);
    }
});

// 右下
$.ajax({
    type: "GET",
    url: ctx+"module/information/queryCountByTaskType",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 0; i < data.length; i++) {
            arr.push(data[i].name)
            var obj = {};
            obj.value = data[i].value;
            obj.name = data[i].name;
            arr1.push(obj)
        }
        var myWork6 = echarts.init(document.getElementById('work-6'));

        option6 = {
            // title : {
            //     text: '某站点用户访问来源',
            //     subtext: '纯属虚构',
            //     x:'center'
            // },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                y : 'bottom',
                textStyle:{color:'#fff'},
                data:arr
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                // max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'',
                    type:'pie',
                    radius : '55%',
                    center: ['62%', '65%'],
                    data:arr1
                }
            ]
        };
        myWork6.setOption(option6);

    }
});
