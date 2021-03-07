package com.user.center.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.user.PersonCenterUserController;
import com.controller.dto.usercenter.InsLeaveWordsDto;
import com.controller.dto.usercenter.InsSelfIntroductionDto;
import com.controller.dto.usercenter.UpdSelfIntroductionDto;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationPersonBlogHomeCarousel;
import com.model.entity.GraduationPersonBlogIntroduce;
import com.model.entity.GraduationPersonBlogMessageBoard;
import com.model.entity.GraduationUser;
import com.user.center.mapper.PersonHomeCarouselMapper;
import com.user.center.mapper.PersonSelfIntroductionMapper;
import com.user.center.mapper.PersonsMessageBoardMapper;
import com.user.center.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 个人博客——业务层
 *
 * @author roger
 * @since 2021/1/23 10:30
 */
@Service(value = "PersonServiceImpl")
@Slf4j
public class PersonServiceImpl implements PersonCenterUserController {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Resource
    private PersonHomeCarouselMapper personHomeCarouselMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PersonsMessageBoardMapper personsMessageBoardMapper;
    @Resource
    private PersonSelfIntroductionMapper personSelfIntroductionMapper;

    @Override
    public Result selCarousel(Long userId) {
        //获得用户id
        GraduationPersonBlogHomeCarousel graduationPersonBlogHomeCarousel = new GraduationPersonBlogHomeCarousel();
        graduationPersonBlogHomeCarousel.setUserId(userId);
        return Result.ok(personHomeCarouselMapper.selectList(new QueryWrapper<>(graduationPersonBlogHomeCarousel)));
    }

    @Override
    public Result selSelfIntroduction(Long userId) {
        return Result.ok(personSelfIntroductionMapper.selectById(userId));
    }

    @Override
    public Result selLeaveList(Long id, Integer pageNum, Integer pageSize) {
        IPage<GraduationPersonBlogMessageBoard> page = new Page<>(pageNum, pageSize);
        GraduationPersonBlogMessageBoard g = new GraduationPersonBlogMessageBoard();
        g.setUserId(id);
        IPage<GraduationPersonBlogMessageBoard> e = personsMessageBoardMapper.selectPage(page, new QueryWrapper<>(g).orderByDesc("create_time"));
        PageInfoResult<GraduationPersonBlogMessageBoard> pageInfoResult = new PageInfoResult<>();
        pageInfoResult.setTotal(e.getTotal());
        pageInfoResult.setList(e.getRecords());
        pageInfoResult.setPageSize(pageSize);
        pageInfoResult.setPageNum(pageNum);
        return Result.ok(pageInfoResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insLeaveWords(InsLeaveWordsDto content, String authentication) {
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);
        GraduationUser graduationUser = userMapper.selectById(claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID, Long.class));
        GraduationPersonBlogMessageBoard g = new GraduationPersonBlogMessageBoard();
        g.setSourceId(graduationUser.getId());
        g.setSourceName(graduationUser.getUsername());
        g.setSourceProfile(graduationUser.getProfile());
        g.setCreateTime(System.currentTimeMillis());
        g.setContent(content.getContent());
        g.setUserId(content.getUserId());
        personsMessageBoardMapper.insert(g);
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insSelfIntroduction(InsSelfIntroductionDto content, String authentication) {
        GraduationPersonBlogIntroduce graduationPersonBlogIntroduce = new GraduationPersonBlogIntroduce();
        graduationPersonBlogIntroduce.setContent(content.getContent());
        graduationPersonBlogIntroduce.setUserId(JwtUtils.getUserId(jwtSecret, authentication));
        personSelfIntroductionMapper.replaceIntoGraduationPersonBlogIntroduce(graduationPersonBlogIntroduce);
        return Result.ok();
    }

    @Override
    public Result updSelfIntroduction(UpdSelfIntroductionDto updSelfIntroductionTitleDto, String authentication) {
        Long userId = JwtUtils.getUserId(jwtSecret, authentication);
        GraduationPersonBlogIntroduce graduationPersonBlogIntroduce = new GraduationPersonBlogIntroduce();
        graduationPersonBlogIntroduce.setContent(updSelfIntroductionTitleDto.getContent());
        graduationPersonBlogIntroduce.setImageUrl(updSelfIntroductionTitleDto.getImageUrl());
        graduationPersonBlogIntroduce.setTitleCh(updSelfIntroductionTitleDto.getTitleCh());
        graduationPersonBlogIntroduce.setTitleEn(updSelfIntroductionTitleDto.getTitleEn());
        graduationPersonBlogIntroduce.setUserId(userId);
        if (personSelfIntroductionMapper.selectById(userId) == null) {
            personSelfIntroductionMapper.insert(graduationPersonBlogIntroduce);
        } else {
            personSelfIntroductionMapper.updateById(graduationPersonBlogIntroduce);
        }
        return Result.ok();
    }

}
