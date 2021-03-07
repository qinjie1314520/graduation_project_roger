package com.controller.controller.article;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.LoginDTO;
import com.controller.dto.SelectLatestArticleDTO;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.NameAndValueAndChildren;
import com.controller.pojo.PageInfoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 文章服务相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "文章服务——相关接口")
public interface ArticleHomeControllerInterface {
    /**
     * 关键字文章搜索
     * @param content 栏目id
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param authentication jwt
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "关键字文章搜索")
    @RequestMapping(value = "/article/home/search",method = RequestMethod.GET)
    Result searchArticle(@RequestParam(value = "content", defaultValue = "") String content,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @RequestHeader(name=ConstantUtils.FRONT_KEY_AUTHORIZATION,required = false) String authentication);
    /**
     * 查询文章类型
     * @param id 栏目id
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询文章类型")
    @RequestMapping(value = "/article/home/selArticleType",method = RequestMethod.GET)
    Result<List<NameAndValue>> selectArticleType(Integer id);
    /**
     * 查询文章栏目
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询文章栏目")
    @RequestMapping(value = "/article/home/selArticlePrograma",method = RequestMethod.GET)
    Result<List<NameAndValue>> selectArticlePrograma();
    /**
     * 查询文章类型栏目
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询文章类型栏目")
    @RequestMapping(value = "/article/home/selArticleTypePrograma",method = RequestMethod.GET)
    Result<List<NameAndValueAndChildren>> selectArticleTypePrograma();
    /**
     * 查询热门文章
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询热门文章")
    @RequestMapping(value = "/article/home/selHotArticle",method = RequestMethod.GET)
    Result selectHotArticle();

    /**
     * 分页查询首页最新文章
     * @param selectLatestArticleDTO 保存页码页大小，文章类型
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "分页查询首页最新文章")
    @RequestMapping(value = "/article/home/selLatestArticle",method = RequestMethod.GET)
    Result<PageInfoResult> selectLatestArticle(SelectLatestArticleDTO selectLatestArticleDTO,@RequestHeader(name=ConstantUtils.FRONT_KEY_AUTHORIZATION,required = false) String authentication);

    /**
     * 查询每日一句
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询每日一句")
    @RequestMapping(value = "/article/home/selDailySentence",method = RequestMethod.GET)
    Result selectDailySentence();

    /**
     * 查询每日推荐
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "查询每日推荐")
    @RequestMapping(value = "/article/home/selDailyRecommendation",method = RequestMethod.GET)
    Result selectDailyRecommendation();



}