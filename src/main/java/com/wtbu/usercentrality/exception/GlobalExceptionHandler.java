package com.wtbu.usercentrality.exception;

import com.wtbu.usercentrality.common.BaseResponse;
import com.wtbu.usercentrality.common.ErrorCode;
import com.wtbu.usercentrality.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author cg
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException: " + e.getMessage(),e);
       return  ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    /**作用
     * 捕获代码中所有的异常 内部消化 让前端得到更详细的业务报错/信息
     * 同时捕获掉项目框架本身的异常 不暴露服务器内部状态
     * 集中处理 比如记录日志
     *@param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(BusinessException e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

}
