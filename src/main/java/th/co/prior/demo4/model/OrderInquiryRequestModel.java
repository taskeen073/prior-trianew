package th.co.prior.demo4.model;

import lombok.Data;

@Data
public class OrderInquiryRequestModel {
    private Integer status;
    private String description;
    private Integer orderMenu;
    private Integer orderValue;
    private Integer orderBill;
    private String orderStatus;

}
