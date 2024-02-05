package com.pignest.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import com.pignest.api.common.IDRequest;
import com.pignest.api.model.dto.goods.TopUpRequest;
import com.pignest.api.model.dto.user.UserQueryRequest;
import com.pignest.api.model.vo.LoginUserVO;
import com.pignest.api_common.model.entity.User;
import com.pignest.api_common.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author Black

 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     */
    boolean isNotAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     */
    boolean isNotAdmin(User user);

    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 更新凭证
     * @param idRequest
     * @return
     */
    Boolean updateKey(IDRequest idRequest);

    /**
     * 充值
     * @param topUpRequest
     * @return
     */
    Boolean topUp(TopUpRequest topUpRequest);

}
