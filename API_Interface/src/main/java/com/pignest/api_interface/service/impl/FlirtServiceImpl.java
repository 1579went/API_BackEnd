package com.pignest.api_interface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api_interface.model.Flirt;
import com.pignest.api_interface.service.FlirtService;
import com.pignest.api_interface.mapper.FlirtMapper;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【flirt(情话)】的数据库操作Service实现
* @createDate 2024-01-15 17:01:06
*/
@Service
public class FlirtServiceImpl extends ServiceImpl<FlirtMapper, Flirt>
    implements FlirtService{

}




