package com.example.demo.utils;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.json.JSONObject;
import com.example.demo.constant.Const;
import com.example.demo.constant.EsAnalyzerEnum;
import com.example.demo.constant.EsTypeEnum;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author vincent
 * @description es body构造器
 * @Date 2020/4/23 10:39 上午
 */
public class EsQuery {
    private JSONObject json;

    private boolean mapping = false;

    private SearchRequest searchRequest;

    private SearchSourceBuilder searchSourceBuilder;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }


    public SearchSourceBuilder getSearchSourceBuilder() {
        return searchSourceBuilder;
    }

    @Deprecated
    public EsQuery() {
        this.json = new JSONObject();
    }

    public EsQuery(String indexName) {
        this.searchRequest = new SearchRequest(indexName);
        this.searchRequest.types(Const.ES_DOC);
        this.searchSourceBuilder = new SearchSourceBuilder();
        this.searchSourceBuilder.timeout(new TimeValue(Const.ES_TIMEOUT, TimeUnit.SECONDS));
    }

    @Deprecated
    public EsQuery(boolean mapping) {
        this.json = new JSONObject();
        this.mapping = mapping;
    }

    @Deprecated
    public EsQuery setMappings(){
        this.mapping = true;
        return this;
    }

    @Deprecated
    public EsQuery setColumn(String name,String type){
        if(!this.mapping){
            throw new ElasticsearchException("setColumn 必须与 setMappings 或 mapping为true时使用");
        }

        putByPath(this.json, StrFormatter.format("mappings.properties.{}.type",name),type);
        return this;
    }

    @Deprecated
    public EsQuery setColumn(String name, EsTypeEnum type, EsAnalyzerEnum analyzer){
        if(!this.mapping){
            throw new ElasticsearchException("setColumn 必须与 setMappings 或 mapping为true时使用");
        }

        putByPath(this.json, StrFormatter.format("mappings.properties.{}.type",name),type.getValue());
        putByPath(this.json, StrFormatter.format("mappings.properties.{}.analyzer",name),analyzer.getValue());
        putByPath(this.json, StrFormatter.format("mappings.properties.{}.search_analyzer",name),analyzer.getValue());
        return this;
    }

    public SearchRequest search(QueryBuilder query){
        this.searchSourceBuilder.query(query);
        this.searchRequest.source(this.searchSourceBuilder);
        return this.searchRequest;
    }

    @Deprecated
    private void putByPath(JSONObject target,String ex,Object value){
        target.putByPath(ex,value);
    }

    @Deprecated
    public String getString(){
        return this.json.toString();
    }
}
