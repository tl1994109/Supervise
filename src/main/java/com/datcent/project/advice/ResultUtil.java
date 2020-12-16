package com.datcent.project.advice;

import net.sf.json.JSONObject;

/**
 * @Author xusi
 * @Description 返回结果操作类
 * @Date 10:26 2018/1/11
 * @Param
 * @return
 **/
public class ResultUtil {

    public static ApiResult resultSucess(){
        ApiResult baseResult = new ApiResult(ReturnEnum.SUCCESS_RES.getCode(),
                ReturnEnum.SUCCESS_RES.getMsg(), new JSONObject());
        return baseResult;
    }

    public static ApiResult resultSucess(Object o){
        ApiResult baseResult = new ApiResult(ReturnEnum.SUCCESS_RES.getCode(),
                ReturnEnum.SUCCESS_RES.getMsg(), o);
        return baseResult;
    }

    public static ApiResult resultSucess(Object o, String sucessMsg){
        if(StringUtil.availableStr(sucessMsg)){
            return new ApiResult(ReturnEnum.SUCCESS_RES.getCode(), sucessMsg, o);
        }else {
            return new ApiResult(ReturnEnum.SUCCESS_RES.getCode(), ReturnEnum.SUCCESS_RES.getMsg(), o);
        }
    }

    public static ApiResult resultWarn(String warnMsg){
        ApiResult baseResult = new ApiResult(ReturnEnum.WARN_RES.getCode(),
                ReturnEnum.WARN_RES.getMsg(), warnMsg);
        return baseResult;
    }

    public static ApiResult resultWrong(String warnMsg){
        ApiResult baseResult = new ApiResult(10000,
                "参数异常", warnMsg);
        return baseResult;
    }

    public static ApiResult resultError(ApiResultException ex){
        ApiResult baseResult = new ApiResult(ReturnEnum.ERROR_RES.getCode(),
                ReturnEnum.ERROR_RES.getMsg(), ex);
        return baseResult;
    }

    public static ApiResult resultError(String exMsg){
        ApiResult baseResult = new ApiResult(ReturnEnum.ERROR_RES.getCode(),
                ReturnEnum.ERROR_RES.getMsg(), exMsg);
        return baseResult;
    }
}
