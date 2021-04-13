package com.usian.controller;

import cn.jiyun.utils.PageResult;
import cn.jiyun.utils.Result;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemParam;
import com.usian.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/service/")
public class TbItemController {
    @Autowired
    TbItemService tbItemService;
    @RequestMapping("/item/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(Integer page, Integer rows){
        return this.tbItemService.selectTbItemAllByPage(page,rows);
    }
    @RequestMapping("/itemCategory/selectItemCategoryByParentId")
    public List<TbItemCat> selectItemCategoryByParentId(Long id){
        return this.tbItemService.selectItemCategoryByParentId(id);
    }
    @RequestMapping("/item/insertTbItem")
    public Integer insertTbItem(@RequestBody TbItem tbItem,@RequestParam String desc,@RequestParam String itemParams){
        return tbItemService.insertTbItem(tbItem,desc,itemParams);
    }
    @RequestMapping("/item/preUpdateItem")
    public Map<String,Object> preUpdateItem(Long itemId){
        return tbItemService.preUpdateItem(itemId);
    }
    @RequestMapping("/item/updateTbItem")
    public Integer updateTbItem(@RequestBody TbItem tbItem,@RequestParam String desc,@RequestParam String itemParams){
        return tbItemService.updateTbItem(tbItem,desc,itemParams);
    }
    @RequestMapping("/item/deleteItemById")
    public Integer deleteItemById(Long itemId){
        return tbItemService.deleteItemById(itemId);
    }
}
