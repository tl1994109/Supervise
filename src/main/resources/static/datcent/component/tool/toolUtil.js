/**
*@Author: 张梦圆
*@Email: zhangmengyuan@datcent.com
*@CreateDate: 2018/11/22 16:07
*@Copyright: © 2018 德讯科技 版权所有
*@Description: TODO 工具类
*/

var toolUtil = {
    // 判断字符串是否为空
    isEmpty: function (value) {
        if (value == null || this.trim(value) == "") {
            return true;
        }
        return false;
    },
    // 判断一个字符串是否为非空串
    isNotEmpty: function (value) {
        return !this.isEmpty(value);
    },
    // 是否显示数据 为空默认为显示
    visible: function (value) {
        if (this.isEmpty(value) || value == true) {
            return true;
        }
        return false;
    },
    // 空格截取
    trim: function (value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
    },
    // 指定随机数返回
    random : function (min, max) {
        return Math.floor((Math.random() * max) + min);
    },
    //将form表单序列化为json字符串
    serializeObject: function(id){
        var o = {};
        var a = $(id).serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    },
    s4:function(){
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    },
    guid:function(){
        return (toolUtil.s4()+toolUtil.s4()+toolUtil.s4()+toolUtil.s4()+toolUtil.s4()+toolUtil.s4()+toolUtil.s4()+toolUtil.s4());
    }
    /*guid: function () {
        s4: function (){
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        }
        return (this.S4()+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+this.S4()+this.S4());
    }*/
};