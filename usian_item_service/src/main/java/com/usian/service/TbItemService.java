package com.usian.service;

import cn.jiyun.utils.IDUtils;
import cn.jiyun.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usian.mapper.*;
import com.usian.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TbItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    public PageResult selectTbItemAllByPage(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        //查询状态是1 并且  按修改时间逆序排列
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated DESC");
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andStatusEqualTo((byte)1);
        List<TbItem> tbItemList = tbItemMapper.selectByExample(tbItemExample);
        for (int i = 0; i < tbItemList.size(); i++) {
            TbItem tbItem =  tbItemList.get(i);
            tbItem.setPrice(tbItem.getPrice()/100);

        }
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItemList);
        //返回PageResult
        PageResult pageResult = new PageResult();
        pageResult.setResult(tbItemPageInfo.getList());
        pageResult.setTotalPage(Long.valueOf(tbItemPageInfo.getPages()));
        pageResult.setPageIndex(tbItemPageInfo.getPageNum());
        return pageResult;
    }

    public List<TbItemCat> selectItemCategoryByParentId(Long id) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        criteria.andStatusEqualTo(1);
        List<TbItemCat> list = this.tbItemCatMapper.selectByExample(example);
        return list;
    }

    public Integer insertTbItem(TbItem tbItem, String desc, String itemParams) {
        //补齐 Tbitem 数据
        Long tbItemId= IDUtils.genItemId();
        Date date = new Date();
        tbItem.setId(tbItemId);
        tbItem.setStatus((byte) 1);
        tbItem.setUpdated(date);
        tbItem.setCreated(date);
        tbItem.setPrice(tbItem.getPrice()*100);
        Integer l1 = tbItemMapper.insert(tbItem);

        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setUpdated(date);
        tbItemDesc.setCreated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(tbItemId);
        Integer l2 = tbItemDescMapper.insert(tbItemDesc);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setItemId(tbItemId);
        Integer l3 = tbItemParamItemMapper.insertSelective(tbItemParamItem);
        return l1+l2+l3;
    }

    public Map<String, Object> preUpdateItem(Long itemId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        //查询商品
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        hashMap.put("item",item);
        //查询商品类别l
        TbItemCat itemCat = tbItemCatMapper.selectByPrimaryKey(item.getCid());
        hashMap.put("itemCat",itemCat.getName());
        //查询描述
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        hashMap.put("itemDesc",itemDesc.getItemDesc());
        //查询商品规格信息
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list =tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            hashMap.put("itemParamItem", list.get(0).getParamData());
        }
        return hashMap;
    }

    public Integer updateTbItem(TbItem tbItem, String desc, String itemParams) {

        Date date = new Date();
        tbItem.setStatus((byte)1);
        tbItem.setUpdated(date);
        //tbItem.setCreated(date);
        tbItem.setPrice(tbItem.getPrice()*100);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);

        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setUpdated(date);
        //tbItemDesc.setCreated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);

        TbItemParamItemExample tbItemParamExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemIdEqualTo(tbItem.getId());
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamExample);
        TbItemParamItem tbItemParamItem = tbItemParamItems.get(0);
        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setParamData(itemParams);
        paramItem.setUpdated(date);
        paramItem.setId(tbItemParamItem.getId());
        tbItemParamItemMapper.updateByPrimaryKeySelective(paramItem);
        return 3;
    }

    public Integer deleteItemById(Long itemId) {
        /*tbItemMapper.updateById(itemId);*/
        /*Integer l1 = tbItemMapper.deleteByPrimaryKey(itemId);
        Integer l2 = tbItemDescMapper.deleteByPrimaryKey(itemId);
        TbItemParamItemExample tbItemParamItemExample=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);

        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        TbItemParamItem tbItemParamItem = tbItemParamItems.get(0);
        Integer l3 = tbItemParamItemMapper.deleteByPrimaryKey(tbItemParamItem.getId());*/
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        tbItem.setStatus((byte) 0);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        return 3;
    }
}
