package com.datcent.project.advice;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * @ClassName BaseResult
 * @Description 统一返回值对象
 * @Author xusi
 * @Date 2019-07-02 23:47
 * @Version 1.0
 */
@ApiModel("ApiResultVo")
public class ApiResult {
    /**
     * 请求是否成功
     */
    @ApiModelProperty(value = "状态码")
    private int code;
    /**
     * 对错误的具体解释
     */
    @ApiModelProperty(value = "返回状态")
    private String status;

    /**
     * 对错误的具体解释
     */
    @ApiModelProperty(value = "错误信息")
    private String message;
    /**
     * 服务器当前时间（添加该字段的原因是便于查找定位请求时间，因为实际开发过程中服务器时间可能跟本地时间不一致，加上这个时间戳便于日后定位）
     */
    @ApiModelProperty(value = "返回时间")
    private String currentTime;

    /**
     * 成功时返回的数据，失败时返回具体的异常信息
     */
    @ApiModelProperty(value = "返回数据")
    private Object data;

    @ApiModelProperty(value = "返回数据")
    private Object result;

    @ApiModelProperty(value = "异常信息")
    private Object errData;

    public ApiResult() {
    }

    @Override
    public String toString() {
        return "RestResult{" +
                ", code='" + code + '\'' +
                ", status='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data + '\'' +
                ", result=" + data + '\'' +
                ", errData=" + errData + '\'' +
                ", currentTime=" + currentTime +
                '}';
    }

    public ApiResult(int code, String message, Object data) {
        this.code = code;
        this.status=String.valueOf(code);
        this.message = message;
        if (data instanceof ApiResultException) {
            JSONObject errInf = new JSONObject();

            try {
                errInf.put("exception_class", ((ApiResultException) data).getCause().getClass().getName());
                errInf.put("exception_info", ((ApiResultException) data).getMessage());
                errInf.put("exception_class_path", ExceptionUtil.getExceptionStackTrace(((ApiResultException) data).getCause()));
                this.errData = MapperUtil.json2map(errInf.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.data = null;
        } else {
            this.data = data;
            this.result =data;
            this.errData = null;
        }
        this.currentTime = DateUtil.getDateTimeFormat(new Date());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getErrData() {
        return errData;
    }

    public void setErrData(Object errData) {
        this.errData = errData;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
