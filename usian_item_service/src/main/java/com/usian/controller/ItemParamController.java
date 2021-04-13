package com.usian.controller;

import cn.jiyun.utils.PageResult;
import cn.jiyun.utils.Result;
import com.usian.pojo.TbItemParam;
import com.usian.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ItemParamController {
    @Autowired
    TbItemParamService tbItemParamService;
    @RequestMapping("/itemParam/selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable Long itemCatId){
        return tbItemParamService.selectItemParamByItemCatId(itemCatId);
    }
    @RequestMapping("/itemParam/selectItemParamAll")
    public PageResult selectItemParamAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "2")Integer rows){
        return tbItemParamService.selectItemParamAll(page,rows);
    }
    @RequestMapping("/itemParam/insertItemParam")
    public Integer insertItemParam(@RequestParam Long itemCatId,@RequestParam String paramData){
        return tbItemParamService.insertItemParam(itemCatId,paramData);
    }
    @RequestMapping("/itemParam/deleteItemParamById")

    public Integer deleteItemById(Long id){
        return tbItemParamService.deleteItemById(id);
    }
}
