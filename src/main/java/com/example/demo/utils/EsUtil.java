package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vincent
 * @description es帮助 index建议手动创建mapping 切勿直接post创建index 避免字段type与预想不一致
 * es index的mapping是无法后期维护的，只能创建新index 映射和数据迁移都是问题
 * POST _reindex
 * {
 *   "source": {
 *     "index": "test"
 *   },
 *   "dest": {
 *     "index": "test_new"
 *   }
 * }
 * @Date 2020/4/22 5:54 下午
 */
@Component
public class EsUtil {

    @Autowired
    RestHighLevelClient client;

    public static final String INDEXNAME = "fd_mall_b_search_history";


    public void index(String indexName){
        CreateIndexRequest indexRequest = getIndexRequest(indexName);
    }

    public CreateIndexRequest getIndexRequest(String indexName){
        return new CreateIndexRequest(indexName);
    }

    public void listHistory() throws IOException {
        EsQuery esQuery = new EsQuery(INDEXNAME);
//        SearchRequest search = esQuery.search(QueryBuilders.matchAllQuery());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder
            .must(QueryBuilders.termQuery("create_user", "044d67797e73420485cf19b0471ccc3b"))
            .must(QueryBuilders.termQuery("is_deleted", "0"));

        SearchSourceBuilder searchSourceBuilder = esQuery.getSearchSourceBuilder();
        searchSourceBuilder.collapse(new CollapseBuilder("content.keyword"));

        SearchRequest search = esQuery.search(boolQueryBuilder);

        SearchResponse response = client.search(search, RequestOptions.DEFAULT);
        List<Map> maps = responseAnalysis(response);
        System.out.println(JSON.toJSONString(maps));
    }


    public List<Map> responseAnalysis(SearchResponse response){
        SearchHits hits = response.getHits();
        SearchHit[] hitsArray = hits.getHits();
        List<Map> list = new ArrayList<>();
        for (SearchHit documentFields : hitsArray) {
            Map map = new HashMap();
            map.put("esId",documentFields.getId());
            map.put("body",documentFields.getSourceAsMap());
            list.add(map);
        }
        return list;
    }

}
