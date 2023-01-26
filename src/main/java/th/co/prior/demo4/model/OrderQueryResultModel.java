package th.co.prior.demo4.model;

import lombok.Data;

@Data
public class OrderQueryResultModel {
    private Integer orderId;
    private String orderStatus;
    private Integer orderBill;
    private Integer orderMenu;
    private Integer orderValue;
}
