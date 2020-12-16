package com.datcent.project.advice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.regex.Pattern;

@RestControllerAdvice(annotations={Controller.class})
@Slf4j
public class ApiResultHandler implements ResponseBodyAdvice {
    private ThreadLocal<ObjectMapper>  mapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);

    private static final Class[] annos = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    private static final String notFilterStr = "(.*/oauth/.*)";
    /**
     * 对所有RestController的接口方法进行拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object out;
        ObjectMapper mapper = mapperThreadLocal.get();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        Pattern notFilterStrRegex = Pattern.compile(notFilterStr);
        if(notFilterStrRegex.matcher(request.getURI().getPath()).matches()){
            out = body;
        }else{
            if(body instanceof ApiResult){
                out = body;
            }
            else if (body instanceof String){
                ApiResult result = ResultUtil.resultSucess(body);
                try {
                    //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
                    out = mapper.writeValueAsString(result);
                } catch (JsonProcessingException e) {
                    out = ResultUtil.resultError(new ApiResultException(e));
                }
            }
            else{
                out = ResultUtil.resultSucess(body);
            }
        }

        return out;
    }


    /**
     * 自定义异常的捕获
     * 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     * @return ApiResult
     */
    @ExceptionHandler(value={ApiResultException.class})
    @ResponseBody
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult errorHandler(ApiResultException ex)throws Exception{
        JSONObject errInf = new JSONObject();
        if(ex.getInfType() == ReturnEnum.ERROR_RES.getCode()){
            return ResultUtil.resultError(ex);
        }else if(ex.getInfType() == ReturnEnum.WARN_RES.getCode()){
            return ResultUtil.resultWarn(ex.getMessage());
        }else{
            return ResultUtil.resultWarn(ex.getMessage());
        }
    }

    /**
     * 自定义异常的捕获
     * 自定义抛出异常。统一的在这里捕获返回JSON格式的友好提示。
     * @return ApiResult
     */
    @ExceptionHandler(value={Exception.class})
    @ResponseBody
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult errorHandler(Exception exception)throws Exception{
        if(exception.getMessage()!=null){
            if(exception.getMessage().equals("1") || exception.getMessage().equals("2") || exception.getMessage().equals("3") || exception.getMessage().equals("4")
                    || exception.getMessage().equals("5")){
                Throwable cause = exception.getCause();
                WrongException ex1=new WrongException(cause);
                if(ex1.getInfType()==10000){
                    return ResultUtil.resultWrong(ex1.getMessage());
                }
            }
        }

        ApiResultException ex = new ApiResultException(exception);
        if(ex.getInfType() == ReturnEnum.ERROR_RES.getCode()){
            return ResultUtil.resultError(ex.getMessage());
        }else if(ex.getInfType() == ReturnEnum.WARN_RES.getCode()){
            return ResultUtil.resultWarn(ex.getMessage());
        }else{
            return ResultUtil.resultWarn(ex.getMessage());
        }
    }
}
