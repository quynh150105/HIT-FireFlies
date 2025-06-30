package com.example.hit_networking_base.config;

import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Iterator;

@Component
public class MultipartFileArrayConverter implements ModelConverter {

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        Type javaType = type.getType();

        if (javaType instanceof Class) {
            Class<?> clazz = (Class<?>) javaType;
            if (clazz.isArray() && clazz.getComponentType().equals(MultipartFile.class)) {
                return new ArraySchema()
                        .items(new Schema<>().type("string").format("binary"))
                        .description("Danh sách ảnh (có thể chọn nhiều)");
            }
        }

        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }
}

