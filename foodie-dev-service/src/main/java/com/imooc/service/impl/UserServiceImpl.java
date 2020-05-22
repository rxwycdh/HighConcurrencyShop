package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author jiuhao
 * @Date 2020/5/14 19:16
 * @Modified
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_FACE = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2103755331,3068281572&fm=26&gp=0.jpg";


    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUserNameIsExist(String username) {

        Example userExample = new Example(Users.class);
        // 创建条件
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andLike("username", username);

        Users result = usersMapper.selectOneByExample(userExample);

        return result != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try{
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        }catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户名称是用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 设置默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 不要写死数据 尽量用枚举
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        userExample.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("password", password);

        return usersMapper.selectOneByExample(userExample);
    }
}
