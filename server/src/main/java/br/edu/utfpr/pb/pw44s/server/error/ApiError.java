package br.edu.utfpr.pb.pw44s.server.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError {
    private long timestamp =new Date().getTime();
    private String message;
    private int staus;
    private String url;
    private Map<String, String> validationErrors;

    public ApiError(String message, int staus, String url) {
        this.message = message;
        this.staus = staus;
        this.url = url;
    }

    public ApiError(String message, int staus, String url, Map<String, String> validationErrors) {
        this.message = message;
        this.staus = staus;
        this.url = url;
        this.validationErrors = validationErrors;
    }
}
