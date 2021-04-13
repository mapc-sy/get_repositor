package com.usian.controller;

import cn.jiyun.utils.PageResult;
import cn.jiyun.utils.Result;
import com.usian.feign.TbItemFeign;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/backend")
public class TbItemController {
    @Autowired
    TbItemFeign tbItemFeign;
    @RequestMapping("/item/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer rows){
        PageResult pageResult= tbItemFeign.selectTbItemAllByPage(page,rows);
        if(pageResult.getResult()!=null && pageResult.getResult().size()>0 ){
            return Result.ok(pageResult);
        }
        return Result.error("没有此数据......");
    }
    @RequestMapping("itemCategory/selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(@RequestParam(defaultValue = "0") Long id){
        List<TbItemCat> list= tbItemFeign.selectItemCategoryByParentId(id);
        if(list!=null && list.size()>0){
            return Result.ok(list);
        }
        return Result.error("查无结果....");
    }

    @RequestMapping("/item/insertTbItem")
    public Result insertTbItem(TbItem tbItem,String desc,String itemParams){
        Integer coutn = tbItemFeign.insertTbItem(tbItem, desc, itemParams);
        if(coutn == 3){
            return Result.ok();
        }
        return Result.error("添加不进去..");
    }
    @RequestMapping("/item/preUpdateItem")
    public Result preUpdateItem(Long itemId){
        Map<String,Object> map=tbItemFeign.preUpdateItem(itemId);
        if(map.size()>0){
            return Result.ok(map);
        }
        return Result.error("查不出来.....");
    }
    @RequestMapping("/item/updateTbItem")
    public Result updateTbItem(TbItem tbItem,String desc,String itemParams){
        Integer coutn = tbItemFeign.updateTbItem(tbItem, desc, itemParams);
        if(coutn == 3){
            return Result.ok();
        }
        return Result.error("修改不了.....");
    }
    @RequestMapping("/item/deleteItemById")
    public Result deleteItemById(Long itemId){
        Integer coutn=tbItemFeign.deleteItemById(itemId);
        if(coutn==3){
            return Result.ok();
        }
        return Result.error("删除不了。。报错了。。。");
    }
}
