package com.user.center.controller;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.user.PersonCenterUserController;
import com.controller.dto.usercenter.InsLeaveWordsDto;
import com.controller.dto.usercenter.InsSelfIntroductionDto;
import com.controller.dto.usercenter.UpdSelfIntroductionDto;
import com.user.center.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 个人博客相关接口
 * @author roger
 * @since  2021/1/23 10:29
 */
@RestController
public class PersonController implements PersonCenterUserController {
    @Autowired
    private PersonServiceImpl personService;



    @Override
    public Result selCarousel(Long userId) {
        return personService.selCarousel(userId);
    }

    @Override
    public Result selSelfIntroduction(Long userId) {
        return personService.selSelfIntroduction(userId);
    }

    @Override
    public Result selLeaveList(Long id,Integer pageNum,Integer pageSize) {
        return personService.selLeaveList(id,pageNum,pageSize);
    }

    @Override
    public Result insLeaveWords(@Validated InsLeaveWordsDto content,String authentication) {
        return personService.insLeaveWords(content,authentication);
    }

    @Override
    public Result insSelfIntroduction(@Validated InsSelfIntroductionDto content,String authentication) {
        return personService.insSelfIntroduction(content, authentication);
    }

    @Override
    public Result updSelfIntroduction(@Validated UpdSelfIntroductionDto updSelfIntroductionTitleDto, String authentication) {
        return personService.updSelfIntroduction(updSelfIntroductionTitleDto,authentication);
    }

}
