package cn.coding.com.netsolutionapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomErrorResponse {

    private int status;
    private String message;

    private long timestamp;

    public CustomErrorResponse() {
        super();
    }

    public CustomErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
