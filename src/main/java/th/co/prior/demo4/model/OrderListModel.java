package th.co.prior.demo4.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderListModel extends BillModel {

    private List<String> orderList = new ArrayList<>();

}
