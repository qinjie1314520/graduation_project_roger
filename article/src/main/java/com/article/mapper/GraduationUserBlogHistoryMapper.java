package com.article.mapper;

import com.article.pojo.UserBlogHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.model.entity.GraduationUserBlogHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户历史博客
 * @author roger
 * @since  2021/1/24 19:08
 */
public interface GraduationUserBlogHistoryMapper extends BaseMapper<GraduationUserBlogHistory> {
    /**
      * 查询历史列表
      * @param userId 用户id
      * @param page 分页信息
      * @return com.baomidou.mybatisplus.core.metadata.IPage
      */
    @Select("select graduation_article.id,title,image_url as imageUrl,description from graduation_user_blog_history LEFT JOIN graduation_article ON (graduation_article.id = graduation_user_blog_history.blog_id and graduation_user_blog_history.user_id =#{userId}) order by graduation_user_blog_history.create_time desc ")
    IPage<UserBlogHistory> selectUserBlogHistoryList(IPage<UserBlogHistory> page, @Param("userId") Long userId);


}
