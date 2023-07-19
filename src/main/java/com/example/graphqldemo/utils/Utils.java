package com.example.graphqldemo.utils;

import java.lang.reflect.Field;

import org.springframework.core.annotation.AnnotationUtils;

import jakarta.persistence.Column;

public final class Utils {
	private Utils() {}
	
	public static String getSqlColumnName(Class<?> entityClass, String fieldName) {
		if (fieldName == null) {
			return null;
		}
		
        try {
            Field field = entityClass.getDeclaredField(fieldName);            
//            Column columnAnnotation = AnnotationUtils.findAnnotation(field, Column.class);
//            
//            if (columnAnnotation != null) {
//                return columnAnnotation.name();
//            } else {
//            	return field.getName();
//            }
            
            
            
            return field.getName();
            
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
