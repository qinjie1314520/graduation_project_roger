package com.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.entity.GraduationHomeRotation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HomeRotationMapper extends BaseMapper<GraduationHomeRotation> {
    /**
      * 根据状态查询轮播图
      * @param status 轮播图状态，1-显示，0-不显示
      * @return
      */
    @Select("select * from graduation_home_rotation where status = #{status}")
    List<GraduationHomeRotation> selectGraduationHomeRotationByStatus(Integer status);
}
