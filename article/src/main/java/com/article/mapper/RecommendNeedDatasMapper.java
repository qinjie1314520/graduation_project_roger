package com.article.mapper;

import com.article.pojo.ScoreInfo;

import java.util.List;

public interface RecommendNeedDatasMapper {

    List<ScoreInfo> selectScoreInfo();

    List<Long> selectMatrixUserIdsOrderByUserIdsAsc();

    List<Long> selectMatrixBlogIdsOrderByBlogIdsAsc();
}
