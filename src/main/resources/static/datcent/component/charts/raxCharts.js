/**
 * @Author: Chanyin尹强
 * @Email: yinqiang@datcent.com
 * @CreateDate: 2018/11/20 15:36
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO raxCharts雷达图组件
 **/

var newData = [];
var legendData = [];
var indicatordata=[];
for (var i = 0; i < myData.axisData.length; i++) {
    var indi = new indicator();
    indi.name=myData.axisData[i];
    indicatordata.push(indi)
}
for (var i = 0; i < myData.seriesData.length; i++) {
    var pieItem = new serPieItem();
    pieItem.name = myData.seriesData[i].name;
    legendData.push(myData.seriesData[i].name);
    pieItem.value = myData.seriesData[i].data;
    newData.push(pieItem);
}
var option4 = {
    title: {
        text: myData.title,
        subtext: '',
        x: 'center'
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {}
        }
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        left: 'left',
        data: legendData
    },
    radar: {
        name: {
            textStyle: {
                color: '#fff',
                backgroundColor: '#999',
                borderRadius: 100,
                padding: [3, 5]
            }
        },
        indicator:indicatordata
    },
    series: [{
        name: myData.title,
        type: 'radar',
        data: newData,
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }]
};

//饼状图数据 封装
function serPieItem() {
    return {
        name: '',
        value: []
    }
}
/// 雷达图数据封装
function indicator() {
    return {
        name:''
    }

}

