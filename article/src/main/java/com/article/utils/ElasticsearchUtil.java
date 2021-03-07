package com.article.utils;

import com.controller.pojo.PageInfoResult;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * es相关操作的服务方法
 *
 * @author roger
 * @since 2021/1/14 14:37
 */
@Component
public class ElasticsearchUtil {
    @Value("${ElasticSearch.Hosts}")
    private String host;
    @Value("${ElasticSearch.Port}")
    private Integer port;
    RestHighLevelClient client;
    RestHighLevelClient getClient(){
        if(client==null){
            client=new RestHighLevelClient(
                    RestClient.builder(new HttpHost(host, port, "http")));
        }
        return client;
    }
    /**
     * 分页处理es的数据时，在这里设置总数
     *
     * @param searchRequest  es restful api
     * @param pageInfoResult 分页时在这里设置总数
     * @return 查询结果
     */
    public List<Map<String, Object>> getEsDatas(SearchRequest searchRequest, PageInfoResult pageInfoResult) {
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            SearchResponse response = getClient().search(searchRequest, RequestOptions.DEFAULT);
            //只是为了不修改代码，而使用引用传递帮助获得总条数
            if (pageInfoResult != null) {
                pageInfoResult.setTotal(response.getHits().getTotalHits().value);
            }
            SearchHit[] searchHits = response.getHits().getHits();
            for (SearchHit hit : searchHits) {
                //从es中取出来的数据
                data.add(hit.getSourceAsMap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 直接查询es得到结果
     *
     * @param searchRequest es restful api
     * @return java.util.List
     */
    public List<Map<String, Object>> getEsDatas(SearchRequest searchRequest) {
        return getEsDatas(searchRequest, null);
    }

}
