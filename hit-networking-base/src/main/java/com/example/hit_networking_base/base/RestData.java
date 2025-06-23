package com.example.hit_networking_base.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestData<T> {

    private RestStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public RestData(T data){
        this.status = RestStatus.SUCCESS;
        this.data = data;
    }

    public static <T>RestData<T> error(String message){
        return new RestData<>(RestStatus.ERROR, message, null);
    }

    public static <T> RestData<T> success(T data) {
        return new RestData<>(data);
    }
}
