package com.example.graphqldemo.utils;

import java.lang.reflect.Field;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.relational.core.mapping.Column;

public final class Utils {
	private Utils() {}
	
	public static String getSqlColumnName(Class<?> entityClass, String fieldName) {
        try {
            Field field = entityClass.getDeclaredField(fieldName);            
            Column columnAnnotation = AnnotationUtils.findAnnotation(field, Column.class);
            
            if (columnAnnotation != null) {
                return columnAnnotation.value();
            } else {
            	return field.getName();
            }
            
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
