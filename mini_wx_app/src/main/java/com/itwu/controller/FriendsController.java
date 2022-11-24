package com.itwu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itwu.entity.Classs;
import com.itwu.entity.R;
import com.itwu.entity.User;
import com.itwu.mapper.UserMapper;
import com.itwu.service.ClassService;
import com.itwu.service.LoginService;
import com.itwu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendsController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;

//    //检查token
//    @GetMapping("check")
//    public R check(@RequestHeader("Authorization") String token){
//        return new R(true,token,"token");
//    }


    //查看班级
    @GetMapping("{current}/{size}")
    public R getAll(@PathVariable Integer current, @PathVariable Integer size, Classs classs){
        return new R(true,classService.getPage(current,size,classs));
    }

    //查看班级成员信息
    @GetMapping("{id}/{current}/{size}")
    public R getMember(@RequestHeader("Authorization") String token,@PathVariable Integer id, @PathVariable Integer current, @PathVariable Integer size, User user){
        user.setClassNum(id);
        IPage<User> page=userService.getClassPage(current,size,user);

        R<User> r = loginService.checkToken(token);
        User nuser =r.getData();
        nuser = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, nuser.getId()));
        if(nuser.getClassNum()==id){

            return new R(true,page,"当前用户在该班级");

        }else {

            return new R(true,page,"当前用户未在该班级");
        }
    }

    //加入班级
    @PutMapping("{id}")
    public R joinClass(@RequestHeader("Authorization") String token,@PathVariable Integer id){
        R<User> r = loginService.checkToken(token);
        User user =r.getData();
        user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        if(user.getClassNum()==0){
            user.setClassNum(id);

            return new R(true,userService.save(user),"加入成功");

        }else{
            return new R(false,"你已加入班级，不可重复加入");
        }
    }

    //退出班级
    @DeleteMapping("{id}")
    public R getOut(@RequestHeader("Authorization") String token,@PathVariable Integer id){
        R<User> r = loginService.checkToken(token);
        User user =r.getData();
        user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, user.getId()));
        user.setClassNum(0);
        return new R(true,userService.save(user),"退出成功");
    }

}
