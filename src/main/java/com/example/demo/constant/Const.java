package com.example.demo.constant;

/**
 * @author vincent
 * @description 静态常量
 * @Date 2020/4/22 6:21 下午
 */
public class Const {

    public static final String ES_DOC = "_doc";

    public static final String ES_SEARCH = "_search";

    public static final String ES_MAPPING = "_mapping";

    public static final long ES_TIMEOUT = 60;

    public static class EsAnalyzer{
        public static final EsAnalyzerEnum IK_MAX_WORD = EsAnalyzerEnum.IK_MAX_WORD;
    }
}
