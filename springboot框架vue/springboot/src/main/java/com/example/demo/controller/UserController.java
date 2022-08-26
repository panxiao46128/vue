package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;


    @PostMapping
    public Result<?> save(@RequestBody User user){
      if(user.getPassword()==null){
          user.setUsername("123456");
      }
        userMapper.insert(user);
      return Result.success();
    }
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){

        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if (res ==null){
            return Result.error("-1","用户名或密码错误");
        }
        res.setPassword("");
        return Result.success(res);
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        if(user.getPassword()==null){
            user.setUsername("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody User user){

        userMapper.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){

        userMapper.deleteById(id);
        return Result.success();
    }
//    @GetMapping("/{id}")
//    public Result<?> getById(@PathVariable Long id){return Result.success(userMapper.selectById(id));
//    }

    @GetMapping
    public Result<?> findPage(@RequestParam (defaultValue = "1")Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "")String search){

    /*    LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickName,search);
        }*/  // 这个wrappe 分页也用了
        //return Result.success(userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper));
//        ---------------这里是一对多查询------------------------------
        Page<User> userPage = userMapper.findPage(new Page<>(pageNum, pageSize), search);
        return Result.success(userPage);
    }

}
