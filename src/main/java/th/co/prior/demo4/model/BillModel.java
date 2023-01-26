package th.co.prior.demo4.model;

import lombok.Data;

@Data
public class BillModel {
    private Integer status;

    private String description;

    private Integer billId;
    private Integer billTable;
    private Integer billWaiter;
    private Integer billCustomer;
    private Integer billPriceAll;
    private String billStatus;
}
