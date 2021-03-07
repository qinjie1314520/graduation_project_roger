package com.article.service;

import com.article.mapper.GraduationArticleMapper;
import com.article.mapper.GraduationArticleTypeMapper;
import com.article.mapper.GraduationUserCollectMapper;
import com.article.pojo.LatestArticle;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.constant.UserCenterConstantUtils;
import com.controller.controller.article.ArticleCollectControllerInterface;
import com.controller.controller.article.ArticleHomeControllerInterface;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationArticle;
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
public class ArticleCollectServiceImpl implements ArticleCollectControllerInterface {
    @Resource
    private GraduationUserCollectMapper graduationUserCollectMapper;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Result updBlogCollect(UpdBlogCollectDto updBlogCollectDto, String authorization) {
        GraduationUserCollect graduationUserCollect = new GraduationUserCollect();
        graduationUserCollect.setBlogId(updBlogCollectDto.getId());
        graduationUserCollect.setUserId(JwtUtils.getUserId(jwtSecret, authorization));
        QueryWrapper<GraduationUserCollect> graduationUserCollectQueryWrapper = new QueryWrapper<>(graduationUserCollect);
        List<GraduationUserCollect> graduationUserCollect1 = graduationUserCollectMapper.selectList(graduationUserCollectQueryWrapper);
        if(graduationUserCollect1==null || graduationUserCollect1.size()==0){
            graduationUserCollect.setCreateTime(System.currentTimeMillis());
            graduationUserCollectMapper.insert(graduationUserCollect);
            return Result.ok(UserCenterConstantUtils.CONSTANT_STR_COLLECT_SUCCESS);
        }else{
            graduationUserCollectMapper.delete(graduationUserCollectQueryWrapper);
            return Result.ok(UserCenterConstantUtils.CONSTANT_STR_CANCEL_COLLECT);
        }
    }

    @Override
    public Result selCollectList(Integer pageNum, Integer pageSize, String content, String authorization) {
        IPage<LatestArticle> page = new Page<>(pageNum,pageSize);
        IPage e = graduationUserCollectMapper.selectUserCollectBlogs(page,JwtUtils.getUserId(jwtSecret, authorization));
        PageInfoResult<LatestArticle> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(pageSize);
        re.setPageNum(pageNum);
        return Result.ok(re);
    }
}