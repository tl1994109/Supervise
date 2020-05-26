// 左上
$.ajax({
        type: "GET",
        url: ctx+"module/clue/queryEveClueCount",
        data: {},
        dataType: "json",
        success: function (data) {

            $('#clue').find('p').text(data.total);
            $('#clue_ok').find('p').text(data.finishCount);
            $('#clue_num').find('p').text(data.czzCount);
        }
 });

// 左中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryFinishCzjg",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 0; i < data.length; i++) {
            arr.push(data[i].czjg)
            var obj = {};
            obj.value = data[i].count;
            obj.name = data[i].czjg;
            arr1.push(obj)
        }
        var myChart1 = echarts.init(document.getElementById('echarts-1'));
        option1 = {
            // title : {
            //     text: '某站点用户访问来源',
            //     subtext: '纯属虚构',
            //     x:'center'
            // },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            color: ['#005fbf', '#43afff', '#8dc6ff', '#005594',"#61a0a8",],
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
                    name:'访问来源',
                    type:'pie',
                    radius : '55%',
                    center : ['65%', '50%'],
                    data:arr1
                }
            ]
        };
        myChart1.setOption(option1);
    }
});


// 左下
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryFinishByCbtName",
    data: {},
    dataType: "json",
    success: function (data) {

        // var sa = data.cq ; // 澄清数组
        // var ss = data.question ; // 问题数组
        var myChart2 = echarts.init(document.getElementById('echarts-2'));
        option2 = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                textStyle:{color:'#fff'},
                x: "center",
                y: "bottom",
                data:['澄清数量', '问题数量']
            },
            toolbox: {
                show : false,
                feature : {
                    // dataView : {show: true, readOnly: false}
                    // mark : {show: true},
                    // ,
                    // magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    // restore : {show: true},
                    // saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : data[0]['cbtList'],
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        },
                        interval:0,
                        rotate:-30,
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            series : [
                {
                    name:'澄清数量',
                    type:'bar',
                    stack: '总量',
                    color: ['#005fbf',],
                    itemStyle : { normal: {label : { color:'#fff', position: 'insideRight'}}},
                    data:data[0]['cq']
                },
                {
                    name:'问题数量',
                    type:'bar',
                    color: [ '#43afff', ],
                    stack: '总量',
                    itemStyle : { normal: {label : { color:'#fff', position: 'insideRight'}}},
                    data:data[0]['question']
                }
            ]
        };
        myChart2.setOption(option2);


    }
});


// 中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCountByCzzAndMonth",
    data: {},
    dataType: "json",
    success: function (data) {

        var myChart3 = echarts.init(document.getElementById('echarts-3'));
        option3 = {
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                x: "center",
                y: "bottom",
                textStyle:{color:'#fff'},
                data:['谈话函询','初步核实','立案调查','审理阶段']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : data[1],
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            series : [
                {
                    name:'谈话函询',
                    type:'line',
                    // stack: '总量',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].thhx
                },
                {
                    name:'初步核实',
                    type:'line',
                    // stack: '总量',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].cbhs
                },
                {
                    name:'立案调查',
                    type:'line',
                    // stack: '总量',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].ladc
                },
                {
                    name:'审理阶段',
                    type:'line',
                    // stack: '总量',
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].sl
                }
            ]
        };
        myChart3.setOption(option3);
    }
});


// 右上
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryCzzCountByAge",
    data: {},
    dataType: "json",
    success: function (data) {

        var arr = [];
        var arr1 = [];
        for (var i = 0; i < data.length; i++) {
            arr.push(data[i].ajlb)
            var obj = {};
            obj.value = data[i].data;
            obj.name = data[i].ajlb;
            arr1.push(obj)
        }
        var myChart4 = echarts.init(document.getElementById('echarts-4'));
        option4 = {
            tooltip: {},
            legend: {
                x: "left",
                y: "center",
                orient: "vertical",
                textStyle:{color:'#fff'},
                data: arr
            },
            radar: {
                // shape: 'circle',
                name: {
                    textStyle: {
                        color: '#fff',
                        backgroundColor: '#999',
                        borderRadius: 3,
                        padding: [3, 5]
                    }
                },
                indicator: [
                    { name: '21-30岁', max: 200},
                    { name: '31-40岁', max: 200},
                    { name: '41-50岁', max: 200},
                    { name: '51-60岁', max: 200},
                    { name: '60岁以上', max: 200},

                ],
                center : ['60%', '50%'],
                radius : '55%',
            },
            series: [{
                name: '',
                type: 'radar',
                // areaStyle: {normal: {}},
                itemStyle: {
                    normal: {
                        label: {
                            show: false,
                            color: '#fff'
                        }
                    }
                },
                data : arr1
            }]
        };
        myChart4.setOption(option4);
    }
});


// 右中
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryFinishRiskCount",
    data: {},
    dataType: "json",
    success: function (data) {

        var myChart5 = echarts.init(document.getElementById('echarts-5'));
        option5 = {
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            color:['#8dc6ff','#005fbf', '#43afff'],
            legend: {
                x: "center",
                y: "bottom",
                textStyle:{color:'#fff'},
                data:['低风险','中风险','高风险']
            },
            toolbox: {
                // feature: {
                //     saveAsImage: {}
                // }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '10%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : data[1],
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine: {
                        lineStyle: {
                            color: '#fff'
                        }
                    }
                }
            ],
            series : [
                {
                    name:'低风险',
                    type:'line',
                    stack: '总量',
                    areaStyle: {},
                    data:data[0].lowRisk,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    }
                },
                {
                    name:'中风险',
                    type:'line',
                    stack: '总量',
                    areaStyle: {},
                    data:data[0].centerRisk,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    }
                },
                {
                    name:'高风险',
                    type:'line',
                    stack: '总量',
                    areaStyle: {},
                    data:data[0].highRisk,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    }
                }
            ]
        };
        myChart5.setOption(option5);

    }
});


// 右下
$.ajax({
    type: "GET",
    url: ctx+"module/clue/queryFinishClueByDeptAndAjlb",
    data: {},
    dataType: "json",
    success: function (data) {

        var myChart6 = echarts.init(document.getElementById('echarts-6'));
        var placeHoledStyle = {
            normal:{
                barBorderColor:'rgba(0,0,0,0)',
                color:'rgba(0,0,0,0)'
            },
            emphasis:{
                barBorderColor:'rgba(0,0,0,0)',
                color:'rgba(0,0,0,0)'
            }
        };
        var dataStyle = {
            normal: {
                label : {
                    show: true,
                    position: 'insideLeft',
                    formatter: '{c}%'
                }
            }
        };

        option = {
            // title: {
            //     text: '多维条形图',
            //     subtext: 'From ExcelHome',
            //     sublink: 'http://e.weibo.com/1341556070/AiEscco0H'
            // },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                // formatter : '{b}<br/>{a0}:{c0}<br/>{a1}:{c1}<br/>{a2}:{c2}<br/>{a3}:{c3}'
            },
            color: ['#005fbf', '#43afff', '#8dc6ff', '#005594'],
            legend: {
                x: "center",
                y: "bottom",
                // itemGap : document.getElementById('echarts-6').offsetWidth / 8,
                data:['刑事', '民事','行政', '执行'],
                textStyle:{color:'#fff'}
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            // grid: {
            //     y: 80,
            //     y2: 30
            // },
            xAxis : [
                {
                    type : 'value',
                    position: 'top',
                    splitLine: {show: false},
                    axisLabel: {show: false},
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'category',
                    splitLine: {show: false},
                    data : data[0].cbt,
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#fff',
                        }
                    }
                }
            ],
            series : [
                {
                    name:'刑事',
                    type:'bar',
                    stack: '总量',
                    // itemStyle : dataStyle,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].xs
                },

                {
                    name:'民事',
                    type:'bar',
                    stack: '总量',
                    // itemStyle : dataStyle,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].ms
                },

                {
                    name:'行政',
                    type:'bar',
                    stack: '总量',
                    // itemStyle : dataStyle,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].xz
                },

                {
                    name:'执行',
                    type:'bar',
                    stack: '总量',
                    // itemStyle : dataStyle,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                color: '#fff'
                            }
                        }
                    },
                    data:data[0].zx
                },

            ]
        };
        myChart6.setOption(option);
    }
});




