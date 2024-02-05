package com.pignest.api_interface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api_interface.model.DirtyJoke;
import com.pignest.api_interface.service.DirtyJokeService;
import com.pignest.api_interface.mapper.DirtyJokeMapper;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【dirty_joke(黄段子)】的数据库操作Service实现
* @createDate 2024-01-15 12:14:38
*/
@Service
public class DirtyJokeServiceImpl extends ServiceImpl<DirtyJokeMapper, DirtyJoke>
    implements DirtyJokeService{

}




