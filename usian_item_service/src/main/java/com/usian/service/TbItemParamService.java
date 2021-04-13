package com.usian.service;

import cn.jiyun.utils.PageResult;
import cn.jiyun.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.TbItemParamMapper;
import com.usian.pojo.TbItemParam;
import com.usian.pojo.TbItemParamExample;
import com.usian.pojo.TbItemParamItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TbItemParamService {
    @Autowired
    TbItemParamMapper tbItemParamMapper;
    public TbItemParam selectItemParamByItemCatId(Long itemCatId) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public PageResult selectItemParamAll(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        TbItemParamExample example=new TbItemParamExample();
        example.setOrderByClause("updated DESC");
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> paramPageInfo=new PageInfo<>(tbItemParams);
        PageResult pageResult = new PageResult();
        pageResult.setPageIndex(paramPageInfo.getPageNum());
        pageResult.setTotalPage(paramPageInfo.getTotal());
        pageResult.setResult(paramPageInfo.getList());
        return pageResult;
    }

    public Integer insertItemParam(Long itemCatId, String paramData) {
        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemCatId);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(tbItemParams.size()>0){
            return 0;
        }

        //没有值 传进去l
        Date date = new Date();
        TbItemParam tbItemParam = new TbItemParam();
        tbItemParam.setUpdated(date);
        tbItemParam.setCreated(date);
        tbItemParam.setParamData(paramData);
        tbItemParam.setItemCatId(itemCatId);
        int i = tbItemParamMapper.insertSelective(tbItemParam);
        return i;
    }

    public Integer deleteItemById(Long id) {
        return tbItemParamMapper.deleteByPrimaryKey(id);
    }
}
