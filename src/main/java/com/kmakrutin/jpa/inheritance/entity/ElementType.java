package com.kmakrutin.jpa.inheritance.entity;

public enum ElementType {
    LOGICAL_GROUP,
    HOST_CODE_ATTRIBUTE,
    BOOLEAN_ATTRIBUTE;

    public static class Constants {
        public static final String LOGICAL_GROUP = "LOGICAL_GROUP";
        public static final String HOST_CODE_ATTRIBUTE = "HOST_CODE_ATTRIBUTE";
        public static final String BOOLEAN_ATTRIBUTE = "BOOLEAN_ATTRIBUTE";
    }
}
