
    var queryConditionComponent = {
        //拼接字符串
        str : '',
        //默认一行三个条件
        width : 3,
        //总行数
        row : 0,

    /*
    * 初始化查询条件
    *
    * params   [{},{},{}]   数据格式
    * */
    /*<![CDATA[*/
    init:function(id,params,conditionColmun){
        try {
            if(!Array.isArray(params)){
                throw example;
            }
            queryConditionComponent.row = Math.ceil(params.length/conditionColmun);
            queryConditionComponent.width = Math.floor(12/conditionColmun);
            //循环遍历生成查询条件
            for(var i=0;i<queryConditionComponent.row;i++){
                queryConditionComponent.str += " <div class=\"form-group select-info-container\">";
                for(var j=0;j<params.length;j++){
                    if(params[j].renderStyle == "input"){
                        queryConditionComponent.str += "<div class=\"col-sm-"+queryConditionComponent.width+"\">"
                        +" <label>"+params[j].name+"</label>"
                        +" <input type=\"text\"  name=\""+params[j].id+"\">"
                        +" </div>";
                    }else if(params[j].renderStyle == "select"){
                    /*<script type="text/html" id="thymeleafTable">
                            <table>
                            <tr>
                            <th th:text="#{Order.type}"></th>
                            <td>
                            <select name="type">
                            <option value="" th:text="#{admin.common.choose}"></option>
                            <option th:each="value : ${types}" th:value="${value}" th:attr="selected = ${value == type} ? 'selected' : ''" th:text="#{'Order.Type.' + ${value}}"></option>
                            </select>
                            </td>
                            </tr>
                            </table>
                            </script>*/
                        queryConditionComponent.str += "<script type=\"text/html\" id=\"thymeleafTable\">"
                                                    +"<div class=\"col-sm-4\">"
                                                    +" <label>\"+params[j].name+\"</label>"
                                                    +"<select id=\""+params[j].id+"\" name=\""+params[j].id+"\" th:with=\"type=${@dict.getType(\""+params[j].dicType+"\")}\">"
                                                    +"<option value=\"\">请选择</option>"
                                                    +"<option th:each=\"dict : ${type}\" th:text=\"${dict.dictLabel}\" th:value=\"${dict.dictValue}\"></option>"
                                                    +"</select>"
                                                    +"</div>"
                                                    +" </script>";
                    }
                }
                queryConditionComponent.str += "</div>";
            }

            queryConditionComponent.str += "<div class=\"form-group select-info-container\"> <div class=\"col-sm-12 select-info-btn\"> <a class=\"btn btn-primary btn-rounded btn-sm\" onclick=\"$.table.search()\"><i class=\"fa fa-search\"></i>&nbsp;搜索</a> </div> </div>";
            $(id).append(queryConditionComponent.str)
        }
        catch (e) {
            $.modal.alertWarning(e.message);
        }
    }
    /*]]>*/
}

