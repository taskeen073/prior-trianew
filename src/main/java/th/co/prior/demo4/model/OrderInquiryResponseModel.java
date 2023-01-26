package th.co.prior.demo4.model;


import lombok.Data;

@Data
public class OrderInquiryResponseModel{

    private Integer status;
    private String description;
    private String data;
    private String orderStatus;
    private Integer orderBill;
    private Integer orderMenu;
    private Integer orderValue;




}
