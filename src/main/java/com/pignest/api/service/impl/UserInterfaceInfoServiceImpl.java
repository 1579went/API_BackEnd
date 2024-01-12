package com.pignest.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api.model.entity.UserInterfaceInfo;
import com.pignest.api.service.UserInterfaceInfoService;
import com.pignest.api.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-12-11 16:08:25
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




