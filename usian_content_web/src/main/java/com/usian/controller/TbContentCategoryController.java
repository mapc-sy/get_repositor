package com.usian.controller;

import cn.jiyun.utils.Result;
import com.usian.feign.ContentFeign;
import com.usian.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class TbContentCategoryController {
    @Autowired
    private ContentFeign contentFeign;
    @RequestMapping("/selectContentCategoryByParentId")
    public Result selectContentCategoryByParentId(@RequestParam(defaultValue = "0") Long id){
        List<TbContentCategory> list= contentFeign.selectContentCategoryByParentId(id);
        if(list!=null && list.size()>0){
            return Result.ok(list);
        }
        return Result.error("查不到....");
    }
}
