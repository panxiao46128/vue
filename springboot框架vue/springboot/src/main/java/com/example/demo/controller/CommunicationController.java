package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Communication;
import com.example.demo.mapper.CommunicationMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/Communication")
public class CommunicationController {

    @Resource
    CommunicationMapper CommunicationMapper;


    @PostMapping
    public Result<?> save(@RequestBody Communication Communication){
    
        CommunicationMapper.insert(Communication);
      return Result.success();
    }
  
    @PutMapping
    public Result<?> update(@RequestBody Communication Communication){

        CommunicationMapper.updateById(Communication);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id){

        CommunicationMapper.deleteById(id);
        return Result.success();
    }
//    @GetMapping("/{id}")
//    public Result<?> getById(@PathVariable Long id){return Result.success(CommunicationMapper.selectById(id));
//    }

    @GetMapping
    public Result<?> findPage(@RequestParam (defaultValue = "1")Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "")String search){

    /*    LambdaQueryWrapper<Communication> wrapper = Wrappers.<Communication>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Communication::getNickName,search);
        }*/  // 这个wrappe 分页也用了
        //return Result.success(CommunicationMapper.selectPage(new Page<>(pageNum, pageSize),wrapper));
//        ---------------这里是一对多查询------------------------------
        LambdaQueryWrapper<Communication> wrapper = Wrappers.<Communication>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Communication::getName,search);
        }
        return Result.success(CommunicationMapper.selectPage(new Page<>(pageNum, pageSize),wrapper));
    }

}
