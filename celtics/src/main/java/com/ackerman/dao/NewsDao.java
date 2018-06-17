package com.ackerman.dao;

import com.ackerman.model.News;
import com.ackerman.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 下午10:49 18-6-4
 */
@Mapper
public interface NewsDao {
    public static final String TABLE = "news";
    public static final String INSERT_FIELDS = "type, user_id, like_count, comment_count, title, link, image_link, create_date";
    public static final String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"INSERT INTO ", TABLE, "(" , INSERT_FIELDS, ")",
            "VALUES(#{type}, #{userId}, #{likeCount}, #{commentCount}, #{title}, #{link}, #{imageLink}, #{createDate})"})
    public int addNews(News news);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE, " WHERE id = #{id}"})
    public News getNewsById(int id);

    @Select({"SELECT ", SELECT_FIELDS, " FROM ", TABLE, " LIMIT #{offset}, #{limit}"})
    public List<News> getNewsByOffsetAndLimit(@Param("offset") int offset,
                                              @Param("limit") int limit);
}
