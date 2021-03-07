package com.admin.service;

import com.admin.mapper.HomeRotationMapper;
import com.admin.mapper.WebsiteAnnouncementMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.controller.controller.admin.PublicControllerInterface;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationHomeRotation;
import com.model.entity.GraduationWebsiteAnnouncement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PublicService implements PublicControllerInterface {
    @Resource
    HomeRotationMapper homeRotationMapper;
    @Resource
    WebsiteAnnouncementMapper websiteAnnouncementMapper;

    @Override
    public Result selHomeRotation(Integer pageNum, Integer pageSize) {
        IPage<GraduationHomeRotation> page = new Page<>(pageNum,pageSize);
        GraduationHomeRotation g = new GraduationHomeRotation();
        g.setStatus(GraduationHomeRotation.STATUS_VALUE_SHOW);
        IPage e = homeRotationMapper.selectPage(page, new QueryWrapper<>(g));
        PageInfoResult<GraduationHomeRotation> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(pageSize);
        re.setPageNum(pageNum);
        return Result.ok(re);
    }

    @Override
    public Result selAnnouncement(Integer pageNum, Integer pageSize) {
        IPage<GraduationWebsiteAnnouncement> page = new Page<>(pageNum,pageSize);
        GraduationWebsiteAnnouncement g = new GraduationWebsiteAnnouncement();
        g.setStatus(GraduationWebsiteAnnouncement.STATUS_VALUE_SHOW);
        IPage e = websiteAnnouncementMapper.selectPage(page, new QueryWrapper<>(g));
        PageInfoResult<GraduationWebsiteAnnouncement> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(pageSize);
        re.setPageNum(pageNum);
        return Result.ok(re);
    }
}
