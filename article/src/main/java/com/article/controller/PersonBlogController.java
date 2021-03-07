package com.article.controller;

import com.article.service.PersonBlogServiceImpl;
import com.common.pojo.Result;
import com.controller.controller.article.PersonBlogControllerInterface;
import com.controller.dto.article.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author PC
 */
@RestController
public class PersonBlogController implements PersonBlogControllerInterface {
    @Autowired
    private PersonBlogServiceImpl personBlogService;

    @Override
    public Result selPersonBlogList(SelPersonBlogListDto selPersonBlogListDto, String authentication) {
        return personBlogService.selPersonBlogList(selPersonBlogListDto, authentication);
    }

    @Override
    public Result delPersonBlog(DelPersonBlogDto delPersonBlogDto, String authentication) {
        return personBlogService.delPersonBlog(delPersonBlogDto, authentication);
    }

    @Override
    public Result insPersonBlog(InsPersonBlogDto insPersonBlogDto, String authentication) {
        return personBlogService.insPersonBlog(insPersonBlogDto, authentication);
    }

    @Override
    public Result updPersonBlog(UpdPersonBlogDto updPersonBlogDto, String authentication) {
        return personBlogService.updPersonBlog(updPersonBlogDto, authentication);
    }

    @Override
    public Result personBlogDetails(@RequestParam(value = "id", defaultValue = "1") Long id,String authentication) {
        return personBlogService.personBlogDetails(id,authentication);


    }
    @Override
    public Result personBlogDetailsComments(@RequestParam(value = "id", defaultValue = "1") Long id,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return personBlogService.personBlogDetailsComments(id, pageNum, pageSize);
    }

    @Override
    public Result personBlogDetailsRecommend(String authentication) {
        return personBlogService.personBlogDetailsRecommend(authentication);
    }

    @Override
    public Result insertBlogComment(InsertBlogCommentDto insertBlogCommentDto, String authentication) {
        return personBlogService.insertBlogComment(insertBlogCommentDto,authentication);
    }
    @GetMapping("/insertArticleRecommend")
    public Result insertArticleRecommend() throws IOException {
        return Result.ok(personBlogService.insertArticleRecommend());
    }
    @GetMapping("/insertArticleAi")
    public void insertArticleAi() throws ParseException {
        personBlogService.insertArticleAi();
    }
}