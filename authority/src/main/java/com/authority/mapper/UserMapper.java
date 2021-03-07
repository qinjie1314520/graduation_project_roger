package com.authority.mapper;

import com.authority.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.entity.GraduationAuthority;
import com.model.entity.GraduationUser;

import java.util.List;

public interface UserMapper extends BaseMapper<GraduationUser> {
    /**
     * 通过邮箱查询用户
     * @param email 邮箱
     * @return com.authority.entity.User
     */
    User selUserOneByEmail(String email);

    /**
     * 通过账户查询用户
     * @param account 账户
     * @return com.authority.entity.User
     */
    User selUserOneByAccount(String account);

    /**
     * 查询角色的权限
     * @param roleId 角色
     * @return java.util.List
     */
    List<GraduationAuthority> selRoleAuthorities(Long roleId);
}
