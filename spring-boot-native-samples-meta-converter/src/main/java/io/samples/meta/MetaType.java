package io.samples.meta;

public enum MetaType {
    META_TYPE_VO("VO", "VO"),
    META_TYPE_ENTITY("ENTITY", "ENTITY"),
    META_TYPE_DTO("DTO", "DTO"),
    ;

    private String code;
    private String desc;

    MetaType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MetaType of(String code) {
        for (MetaType metaType: values()) {
            if (metaType.code.equals(code)) {
                return metaType;
            }
        }
        throw new IllegalArgumentException("无效状态码: " + code);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
