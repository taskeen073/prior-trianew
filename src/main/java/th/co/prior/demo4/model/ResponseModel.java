package th.co.prior.demo4.model;


import lombok.Data;

import java.util.List;

@Data
public class ResponseModel<T> {
    private Integer statusCode;
    private String description;

    private T data;
}
