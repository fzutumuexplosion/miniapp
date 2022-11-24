package com.itwu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwu.entity.BackGround;
import com.itwu.mapper.BackGroundMapper;
import com.itwu.service.BackGroundService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackGroundServiceImpl extends ServiceImpl<BackGroundMapper, BackGround> implements BackGroundService {

    @Autowired
    private BackGroundMapper backGroundMapper;


    @Override
    public IPage<BackGround> getPage(int current, int size, BackGround backGround) {

        LambdaQueryWrapper<BackGround> qw = new LambdaQueryWrapper<BackGround>();
        qw.like(Strings.isNotEmpty(backGround.getPosition()),BackGround::getPosition,backGround.getPosition());
        IPage page =new Page(current,size);
        backGroundMapper.selectPage(page,qw);
        return page;
    }
}
