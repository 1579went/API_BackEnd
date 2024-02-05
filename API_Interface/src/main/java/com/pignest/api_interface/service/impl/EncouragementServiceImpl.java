package com.pignest.api_interface.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api_interface.model.Encouragement;
import com.pignest.api_interface.service.EncouragementService;
import com.pignest.api_interface.mapper.EncouragementMapper;
import org.springframework.stereotype.Service;

/**
* @author Black
* @description 针对表【encouragement(心灵鸡汤)】的数据库操作Service实现
* @createDate 2024-01-15 17:52:18
*/
@Service
public class EncouragementServiceImpl extends ServiceImpl<EncouragementMapper, Encouragement>
    implements EncouragementService{

}




