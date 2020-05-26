/**
 * @Author: wujing
 * @Email: wujing@datcent.com
 * @CreateDate: 2018/11/20 15:36
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO 柱状图组件
 **/

var seriesdata=[];
var legendData=[];
for (var i = 0; i < myData.seriesData.length; i++) {
    var barItem = new serbarItem();
    barItem.name=myData.seriesData[i].name;
    barItem.data=myData.seriesData[i].data;
    legendData.push(myData.seriesData[i].name);
    seriesdata.push(barItem);
}

option3 = {
    title : {
        text: myData.title
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {}
        }
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:legendData
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis:  {
        type: 'category',
        data: myData.axisData

    },
    yAxis: {
        type: 'value'
    },
    series: seriesdata
};

//柱状图数据封装
function serbarItem(){
    return {
        name: '',
        type:'bar',
        data: [],
        markPoint:{
            data : [
                {type : 'max', name: '最大值'},
                {type : 'min', name: '最小值'}
            ]
        },
        markLine:{
            data : [
                {type : 'average', name: '平均值'}
            ]
        }
    }
}
