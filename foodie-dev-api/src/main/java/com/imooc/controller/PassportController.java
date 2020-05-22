package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @FileName PassportController
 * @Description
 * @Author jiuhao
 * @Date 2020/5/14 19:21
 * @Modified
 * @Version 1.0
 */
@Api(value = "注册登陆", tags = {"用于注册登陆的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "检查用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        // 判断用户名不能为空
        if(!StringUtils.isNotBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        // 查找注册用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);

        if(isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult register(@RequestBody UserBO userBO, HttpServletRequest request,
                                    HttpServletResponse response) {

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 0.判断不为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        // 1.查询用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(username);

        if(isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        // 2.密码长度不能少于6位
        if(password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能小于6位");
        }
        // 3.判断两次密码是否一致
        if(!password.equals(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次密码不一致");
        }
        // 4.实现注册
        Users userResult = userService.createUser(userBO);

        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult),
                true);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return IMOOCJSONResult.ok(userResult);
    }

    @GetMapping("/test")
    public IMOOCJSONResult test(HttpServletRequest request,
                                HttpServletResponse response) {
        CookieUtils.setCookie(request, response, "user",
                "dasdas", true);

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

    @ApiOperation(value = "退出登陆")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,HttpServletRequest request,
                                  HttpServletResponse response) {
        // 清除Cookie

        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登陆，需要清空购物车
        // TODO 分布式会话中需要清楚用户数据

        return IMOOCJSONResult.ok();
    }
}
