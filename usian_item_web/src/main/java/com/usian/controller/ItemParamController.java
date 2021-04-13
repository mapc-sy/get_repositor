package com.usian.controller;

import cn.jiyun.utils.PageResult;
import cn.jiyun.utils.Result;
import com.usian.feign.TbItemFeign;
import com.usian.pojo.TbItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backend")
public class ItemParamController {
    @Autowired
    TbItemFeign tbItemFeign;
    @RequestMapping("/itemParam/selectItemParamByItemCatId/{itemCatId}")
    public Result selectItemParamByItemCatId(@PathVariable Long itemCatId){
        TbItemParam tbItemParam = tbItemFeign.selectItemParamByItemCatId(itemCatId);
        if(tbItemParam!=null){
            return Result.ok(tbItemParam);
        }
        return Result.error("查无数据....");
    }
    @RequestMapping("/itemParam/selectItemParamAll")
    public Result selectItemParamAll(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "2")Integer rows){
        PageResult pageResult=tbItemFeign.selectItemParamAll(page,rows);
        if(pageResult!=null && pageResult.getResult().size()>0){
            return Result.ok(pageResult);
        }
        return Result.error("查不到这个东西....");
    }
    @RequestMapping("/itemParam/insertItemParam")
    public Result insertItemParam(Long itemCatId,String paramData){
        Integer coutn=tbItemFeign.insertItemParam(itemCatId,paramData);
        if(coutn==1){
            return Result.ok();
        }
        return Result.error("插入不进去....");
    }
    @RequestMapping("/itemParam/deleteItemParamById")
    public Result deleteItemParamById(Long id){
        Integer count=tbItemFeign.deleteItemParamById(id);
        if(count==1){
            return Result.ok();
        }
        return Result.error("你写错了...删除不了");
    }
}
