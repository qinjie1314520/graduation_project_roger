package com.article.controller;

import com.article.service.ArticleCollectServiceImpl;
import com.article.service.ArticleHistoryServiceImpl;
import com.common.pojo.Result;
import com.controller.controller.article.ArticleCollectControllerInterface;
import com.controller.controller.article.ArticleHistoryControllerInterface;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC
 */
@RestController
public class ArticleHistoryController implements ArticleHistoryControllerInterface {
    @Autowired
    private ArticleHistoryServiceImpl articleHistoryService;
    @Override
    public Result selHistoryBlogList(Integer pageNum, Integer pageSize, String authorization) {
        return articleHistoryService.selHistoryBlogList(pageNum, pageSize, authorization);
    }

}
