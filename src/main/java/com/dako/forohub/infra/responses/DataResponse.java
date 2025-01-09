package com.dako.forohub.infra.responses;

public class DataResponse<T> extends ApiResponse {
    private T data;

    public DataResponse(String message, int status, T data) {
        super(message, status);
        this.data = data;
    }

    // Constructor para solo mensaje y estado
    public DataResponse(String message, int status) {
        super(message, status);
    }

    public T getData() {
        return data;
    }
}
