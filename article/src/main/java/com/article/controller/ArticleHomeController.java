package com.article.controller;

import com.article.service.ArticleHomeServiceImpl;
import com.common.pojo.Result;
import com.controller.controller.article.ArticleHomeControllerInterface;
import com.controller.dto.SelectLatestArticleDTO;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.NameAndValueAndChildren;
import com.controller.pojo.PageInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PC
 */
@RestController
public class ArticleHomeController implements ArticleHomeControllerInterface {
    @Autowired
    private ArticleHomeServiceImpl articleHomeService;

    @Override
    public Result searchArticle(String content, Integer pageNum, Integer pageSize,String authentication) {
        return articleHomeService.searchArticle(content, pageNum, pageSize,authentication);
    }

    @Override
    public Result<List<NameAndValue>> selectArticleType(Integer id) {
        return articleHomeService.selectArticleType(id);
    }

    @Override
    public Result<List<NameAndValue>> selectArticlePrograma() {
        return articleHomeService.selectArticlePrograma();
    }

    @Override
    public Result<List<NameAndValueAndChildren>> selectArticleTypePrograma() {
        return articleHomeService.selectArticleTypePrograma();
    }

    @Override
    public Result selectHotArticle() {
        return articleHomeService.selectHotArticle();
    }

    @Override
    public Result<PageInfoResult> selectLatestArticle(@Validated SelectLatestArticleDTO selectLatestArticleDTO,String authentication) {
        return articleHomeService.selectLatestArticle(selectLatestArticleDTO,authentication);
    }

    @Override
    public Result selectDailySentence() {
        return articleHomeService.selectDailySentence();
    }

    @Override
    public Result selectDailyRecommendation() {
        return articleHomeService.selectDailyRecommendation();
    }


}
