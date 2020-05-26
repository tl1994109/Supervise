/**
 * @Author: wujing
 * @Email: wujing@datcent.com
 * @CreateDate: 2018/11/20 15:36
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO 折线图组件
 **/
var seriesdata=[];
var legendData=[];
for (var i = 0; i < myData.seriesData.length; i++) {
    var lineItem=new serLineItem();
    lineItem.name=myData.seriesData[i].name;
    lineItem.data=myData.seriesData[i].data;
    legendData.push(myData.seriesData[i].name);
    seriesdata.push(lineItem);
}

option2 = {
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
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
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



//折线柱状图数据封装
function serLineItem(){
    return {
        type:"line",
        name:'',
        data:[],
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