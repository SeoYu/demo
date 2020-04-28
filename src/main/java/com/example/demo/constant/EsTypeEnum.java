package com.example.demo.constant;

public enum EsTypeEnum {
    TEXT("text"),
    KEYWORD("keyword"),
    BYTE("byte"), // 127
    SHORT("short"), // 32767
    INTEGER("integer"), // 2^31 -1
    LONG("long"), // 2^63 -1
    DOUBLE("double"), // 64位
    FLOAT("float"), // 32位
    HALF_FLOAT("half_float"),// 16位
    SCALED_FLOAT("scaled_float"), // 缩放因子 默认100 scaling_factor
    DATE("date"), // format yyyy-MM-dd HH:mm:ss
    BOOLEAN("boolean"),
    BINARY("binary"), // 存储base64 图像类数据
    ;

    private String value;

    EsTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
