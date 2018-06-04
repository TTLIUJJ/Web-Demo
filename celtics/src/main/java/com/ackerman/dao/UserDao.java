package com.ackerman.dao;

import com.ackerman.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午5:39 18-6-4
 */
@Mapper
public interface UserDao {
    public static final String TABLE = "user";
    public static final String INSERT_FIELDS = " username, password, salt, head_image_url ";
    public static final String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE, "(" + INSERT_FIELDS + ")",
            "VALUES(#{username}, #{password}, #{salt}, #{headImageUrl})"})
    public int addUser(User user);
}
