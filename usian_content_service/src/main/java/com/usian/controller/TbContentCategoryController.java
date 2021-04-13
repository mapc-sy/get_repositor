package com.usian.controller;

import com.usian.pojo.TbContentCategory;
import com.usian.service.TbContentCategoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/content")
public class TbContentCategoryController {
    @Autowired
    private TbContentCategoryService tbContentCategoryService;
    @RequestMapping("/selectContentCategoryByParentId")
    public List<TbContentCategory> selectContentCategoryByParentId(@RequestParam Long parentId) {
        return this.tbContentCategoryService.selectContentCategoryByParentId(parentId);
    }
}
