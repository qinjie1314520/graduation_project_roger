package com.article.controller;

import com.article.service.ArticleCollectServiceImpl;
import com.common.pojo.Result;
import com.controller.controller.article.ArticleCollectControllerInterface;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC
 */
@RestController
public class ArticleCollectController implements ArticleCollectControllerInterface {
    @Autowired
    private ArticleCollectServiceImpl articleCollectService;

    @Override
    public Result updBlogCollect(UpdBlogCollectDto updBlogCollectDto, String authorization) {
        return articleCollectService.updBlogCollect(updBlogCollectDto,authorization);
    }

    @Override
    public Result selCollectList(Integer pageNum, Integer pageSize, String content, String authorization) {
        return articleCollectService.selCollectList(pageNum,pageSize,content,authorization);
    }


}
