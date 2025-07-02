package com.example.hit_networking_base.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@Documented
@RequestMapping("/api/v1")
public @interface RestApiV1 {
}
