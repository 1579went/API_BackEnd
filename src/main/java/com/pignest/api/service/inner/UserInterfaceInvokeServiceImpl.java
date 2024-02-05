package com.pignest.api.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pignest.api.mapper.UserInterfaceInfoMapper;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_common.model.entity.User;
import com.pignest.api.model.entity.UserInterfaceInfo;
import com.pignest.api.service.InterfaceInfoService;
import com.pignest.api.service.UserService;
import com.pignest.api_common.model.entity.InterfaceInfo;
import com.pignest.api_common.service.inner.InnerUserInterfaceInvokeService;
import jakarta.annotation.Resource;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;


/*
 * @description   
 * @param null   
 * @author Black
 * @date 2023/12/10 17:26
 */
@DubboService
@Slf4j
public class UserInterfaceInvokeServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements InnerUserInterfaceInvokeService {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invoke(Long interfaceInfoId, Long userId, Integer reduceScore) {
      try{
          LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
          User user =  userService.getOne(userLambdaQueryWrapper.eq(User::getId,userId).select(User::getPigCoins));
          if(!(user == null)){
              if(user.getPigCoins() <= 0){
                  throw new BusinessException(ErrorCode.OPERATION_ERROR, "余额不足，请先充值。");
              }
          }else{
              throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户不存在");
          }
          // 更新用户钱包积分
          LambdaUpdateWrapper<User> reduceCoins = new LambdaUpdateWrapper<>();
          reduceCoins.eq(User::getId, userId);
          reduceCoins.gt(User::getPigCoins,0);
          reduceCoins.setSql("pig_coins = pig_coins -" + reduceScore);
          boolean reduceCoinsSave = userService.update(reduceCoins);

          LambdaQueryWrapper<UserInterfaceInfo> invokeLambdaQueryWrapper = new LambdaQueryWrapper<>();
          invokeLambdaQueryWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
          invokeLambdaQueryWrapper.eq(UserInterfaceInfo::getUserId, userId);
          UserInterfaceInfo userInterfaceInfo = this.getOne(invokeLambdaQueryWrapper);
          // 不存在就创建一条记录
          boolean invokeResult;
          if (userInterfaceInfo == null) {
              userInterfaceInfo = new UserInterfaceInfo();
              userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
              userInterfaceInfo.setUserId(userId);
              userInterfaceInfo.setCallCnt(1L);
              invokeResult = this.save(userInterfaceInfo);
          } else {
              LambdaUpdateWrapper<UserInterfaceInfo> invokeLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
              invokeLambdaUpdateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
              invokeLambdaUpdateWrapper.eq(UserInterfaceInfo::getUserId, userId);
              invokeLambdaUpdateWrapper.setSql("call_cnt = call_cnt + 1");
              invokeResult = this.update(invokeLambdaUpdateWrapper);
          }
          // 更新接口总调用次数
          LambdaUpdateWrapper<InterfaceInfo> updateCallCnt = new LambdaUpdateWrapper<>();
          updateCallCnt.eq(InterfaceInfo::getId, interfaceInfoId);
          updateCallCnt.setSql("call_cnt = call_cnt + 1");
          boolean updateCallCntSave = interfaceInfoService.update(updateCallCnt);

          boolean updateResult = invokeResult && reduceCoinsSave && updateCallCntSave;
          if (!updateResult) {
              throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用失败");
          }
          return true;
      }catch (Exception e){
          throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
      }
    }
}




