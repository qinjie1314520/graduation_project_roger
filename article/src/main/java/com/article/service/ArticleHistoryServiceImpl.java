package com.article.service;

import com.article.mapper.GraduationUserBlogHistoryMapper;
import com.article.mapper.GraduationUserCollectMapper;
import com.article.pojo.LatestArticle;
import com.article.pojo.UserBlogHistory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.constant.UserCenterConstantUtils;
import com.controller.controller.article.ArticleCollectControllerInterface;
import com.controller.controller.article.ArticleHistoryControllerInterface;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationUserCollect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文章服务提供的首页的业务处理信息
 * @author roger
 * @since  2020/12/7 14:33
 */
@Service
public class ArticleHistoryServiceImpl implements ArticleHistoryControllerInterface {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Resource
    private GraduationUserBlogHistoryMapper graduationUserBlogHistoryMapper;
    @Override
    public Result selHistoryBlogList(Integer pageNum, Integer pageSize, String authorization) {
        IPage<UserBlogHistory> page = new Page<>(pageNum,pageSize);
        IPage e = graduationUserBlogHistoryMapper.selectUserBlogHistoryList(page,JwtUtils.getUserId(jwtSecret, authorization));
        PageInfoResult<UserBlogHistory> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(pageSize);
        re.setPageNum(pageNum);
        return Result.ok(re);
    }
}