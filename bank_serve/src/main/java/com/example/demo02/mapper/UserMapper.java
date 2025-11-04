package com.example.demo02.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo02.domain.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<Users>
{



    // 查询所有用户
    @Select("SELECT user_id, open_id, blance, user_name FROM users")
    List<Users> findAll();

    // 根据用户ID查询用户
    @Select("SELECT user_id, open_id, blance, user_name FROM users WHERE user_id = #{userId}")
    Users findByUserId(String userId);

    // 根据微信openId查询用户
    @Select("SELECT user_id, open_id, blance, user_name FROM users WHERE open_id = #{openId}")
    Users findByOpenId(String openId);

    // 根据用户名模糊查询
    @Select("SELECT user_id, open_id, blance, user_name FROM users WHERE user_name LIKE CONCAT('%', #{userName}, '%')")
    List<Users> findByUserName(String userName);

    // 更新用户信息
    @Update("UPDATE users SET open_id=#{openId}, blance=#{blance}, user_name=#{userName} WHERE user_id=#{userId}")
    int update(Users user);


    // 检查用户是否存在
    @Select("SELECT COUNT(*) FROM user_table WHERE user_id = #{userId}")
    int existsByUserId(@Param("userId") String userId);

    // 更新用户余额 - 注意字段名修正为 balance
    @Update("UPDATE users SET blance = #{blance} WHERE user_id = #{userId}")
    int updateBalance(@Param("userId") String userId, @Param("blance") Double balance);

}
