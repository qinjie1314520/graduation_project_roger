package com.user.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.entity.GraduationPersonBlogHomeCarousel;
import com.model.entity.GraduationPersonBlogIntroduce;
import org.apache.ibatis.annotations.Insert;

/**
 * 用户中心——个人博客自我介绍
 * @author roger
 * @since  2021/1/23 10:30
 */
public interface PersonSelfIntroductionMapper extends BaseMapper<GraduationPersonBlogIntroduce> {
    /**
      * 插入更新个人博客的自我介绍
      * @param graduationPersonBlogIntroduce 实体参数
      * @return void
      */
    @Insert("replace into graduation_person_blog_introduce(user_id,content) value(#{userId},#{content})")
    public void replaceIntoGraduationPersonBlogIntroduce(GraduationPersonBlogIntroduce graduationPersonBlogIntroduce);
}
