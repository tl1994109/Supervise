/**
 * @Author: Chanyin尹强
 * @Email: yinqiang@datcent.com
 * @CreateDate: 2018/11/20 15:36
 * @Copyright: © 2018 德讯科技 版权所有
 * @Description: TODO pieCharts饼图组件
 **/

    var newData = [];
    var legendData = [];
    for (var i = 0; i < myData.seriesData.length; i++) {
        var pieItem = new serPieItem();
        pieItem.name = myData.seriesData[i].name;
        legendData.push(myData.seriesData[i].name);
        pieItem.value = sum(myData.seriesData[i].data);
        newData.push(pieItem);
    }
    var option1= {
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
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            left: 'left',
            data: legendData
        },
        series: [{
            name:myData.title,
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:newData,
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
 function serPieItem(){
    return {
        name: '',
        value: []
    }
}
//饼状图 数据求和
function sum(arr) {
    return arr.reduce(function(prev, curr, idx, arr){
        return prev + curr;
    });
}
