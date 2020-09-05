///**
// * Copyright(c) 2011-2015 by HeXin Inc.
// * All Rights Reserved
// */
//package com.hexin.common;
//
//import com.hexin.common.BusinessException;
//import com.hexin.common.hexincore.service.exception.ErrorCode;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Collection;
//import java.util.Map;
//
///**
// * 所有的错误异常统一封装为 BusinessException
// */
//public class ServiceValidate {
//
//    private static final ErrorCode DEFAULT_IS_TRUE_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//    private static final ErrorCode DEFAULT_IS_NULL_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//    private static final ErrorCode DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//    private static final ErrorCode DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//    private static final ErrorCode DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//    private static final ErrorCode DEFAULT_NOT_BLANK_EX_MESSAGE = ErrorCode.GLOBAL_EXCEPTION;
//
//    /**
//     * Constructor. This class should not normally be instantiated.
//     */
//    public ServiceValidate() {
//        super();
//    }
//
//    public static void isTrue(boolean expression, ErrorCode code) {
//        if (expression == false) {
//            throw new BusinessException(code);
//        }
//    }
//
//    public static void isTrue(boolean expression, String  message) {
//        if (expression == false) {
//            throw new BusinessMessageException(message);
//        }
//    }
//
//    public static void isTrue(boolean expression, ErrorCode code, Object... args) {
//        if (expression == false) {
//            throw new BusinessException(code, args);
//        }
//    }
//
//    public static void isTrue(boolean expression) {
//        if (expression == false) {
//            throw new BusinessException(DEFAULT_IS_TRUE_EX_MESSAGE);
//        }
//    }
//
//    public static <T> T notNull(T object) {
//        return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
//    }
//
//    public static <T> T notNull(T object, ErrorCode code) {
//        if (object == null) {
//            throw new BusinessException(code);
//        }
//        return object;
//    }
//
//    public static <T> T notNull(T object, String message) {
//        if (object == null) {
//            throw new BusinessMessageException(message);
//        }
//        return object;
//    }
//
//    public static <T> T[] notEmpty(T[] array, ErrorCode code) {
//        if (array == null) {
//            throw new BusinessException(code);
//        }
//        if (array.length == 0) {
//            throw new BusinessException(code);
//        }
//        return array;
//    }
//
//    public static <T> T[] notEmpty(T[] array) {
//        return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
//    }
//
//    public static <T extends Collection<?>> T notEmpty(T collection, ErrorCode code) {
//        if (collection == null) {
//            throw new BusinessException(code);
//        }
//        if (collection.isEmpty()) {
//            throw new BusinessException(code);
//        }
//        return collection;
//    }
//
//    public static <T extends Collection<?>> T notEmpty(T collection) {
//        return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
//    }
//
//    public static <T extends Map<?, ?>> T notEmpty(T map, ErrorCode code) {
//        if (map == null) {
//            throw new BusinessException(code);
//        }
//        if (map.isEmpty()) {
//            throw new BusinessException(code);
//        }
//        return map;
//    }
//
//    public static <T extends Map<?, ?>> T notEmpty(T map) {
//        return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
//    }
//
//    public static <T extends CharSequence> T notEmpty(T chars, ErrorCode code) {
//        if (chars == null) {
//            throw new BusinessException(code);
//        }
//        if (chars.length() == 0) {
//            throw new BusinessException(code);
//        }
//        return chars;
//    }
//
//    public static <T extends CharSequence> T notBlank(T chars, ErrorCode code) {
//        if (chars == null) {
//            throw new BusinessException(code);
//        }
//        if (StringUtils.isBlank(chars)) {
//            throw new BusinessException(code);
//        }
//        return chars;
//    }
//
//    public static <T extends CharSequence> T notBlank(T chars, ErrorCode code, Object... args) {
//        if (chars == null) {
//            throw new BusinessException(code, args);
//        }
//        if (StringUtils.isBlank(chars)) {
//            throw new BusinessException(code, args);
//        }
//        return chars;
//    }
//
//    public static <T extends CharSequence> T notBlank(T chars) {
//        return notBlank(chars, DEFAULT_NOT_BLANK_EX_MESSAGE);
//    }
//}
