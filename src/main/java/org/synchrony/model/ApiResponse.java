package org.synchrony.model;

import lombok.Data;

@Data
public class ApiResponse<T> {
    T data;
    String message;
    boolean success;
}
