package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resouce.FileUpload;
import com.imooc.service.ccenter.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName CenterUserController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/28 18:01
 * @Modified
 * @Version 1.0
 */
@Api(value = "center - 用户信息接口", tags = {"用户信息展示的相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation("修改用户信息")
    @PostMapping("update")
    public IMOOCJSONResult update(@RequestParam String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);
            return IMOOCJSONResult.errorMap(errors);
        }

        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        Users userResult = setNullProperty(users);
        // 覆盖cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        // TODO 后续增加令牌token， 会整合进redis实现分布式会话
        return IMOOCJSONResult.ok();
    }

    @ApiOperation("用户头像修改")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(@RequestParam String userId, MultipartFile file, HttpServletResponse response,HttpServletRequest request) {

//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        // 在路径上为每一个用户增加一个userid区分不同用户
        String uploadPathPrefix = File.separator + userId;

        // 开始上传
        if(file != null) {
            FileOutputStream fileOutputStream = null;
            String filename = file.getOriginalFilename();
            if(StringUtils.isNotBlank(filename)) {

                try {
                    // 命名格式face-{userId}.png
                    // 分割 example : imooc-face.png -> fileNameArr = ["imooc-face", "png"]
                    String[] fileNameArr = filename.split("\\.");
                    // 后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if(!suffix.equalsIgnoreCase("png")
                            && !suffix.equalsIgnoreCase("jpg")
                            && !suffix.equalsIgnoreCase("jpeg")) {
                        return IMOOCJSONResult.errorMsg("格式不正确");
                    }


                    // 文件名称重组 覆盖式上传 增量式:额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;

                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile() != null) {
                        outFile.getParentFile().mkdirs();
                    }

                    // 文件输出保存到目标目录
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();

                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        // 更新用户头像到数据库
        String imageServerUrl = fileUpload.getImageServerUrl();

        // 浏览器存在缓存，在这里加上时间戳保证更新后图片及时更新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);


        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        userResult = setNullProperty(userResult);
        // 覆盖cookie
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        // TODO 后续增加令牌token， 会整合进redis实现分布式会话


        return IMOOCJSONResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> resultMap = new HashMap<>();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            String errorField = error.getField();
            String errorMsg = error.getDefaultMessage();
            resultMap.put(errorField, errorMsg);
        }
        return resultMap;
    }
}
