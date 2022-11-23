package com.itwu.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwu.entity.BackGround;

public interface BackGroundService extends IService<BackGround> {

    IPage<BackGround> getPage(int current, int size, BackGround backGround);
}
