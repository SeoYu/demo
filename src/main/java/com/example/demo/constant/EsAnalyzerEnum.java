package com.example.demo.constant;

public enum EsAnalyzerEnum{
    IK_MAX_WORD(0,"ik_max_word");

    private int key;
    private String value;

    EsAnalyzerEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
