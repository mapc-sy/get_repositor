package com.usian.service;

import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    public TbItem findById(Long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
}
