package com.imooc.exception;

import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @FileName CustomExceptionHandler
 * @Description
 * @Author jiuhao
 * @Date 2020/6/3 19:30
 * @Modified
 * @Version 1.0
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // 捕获MaxUploadSizeExceededException

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500k");
    }

}
