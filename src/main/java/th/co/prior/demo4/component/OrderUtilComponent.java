package th.co.prior.demo4.component;


import org.springframework.stereotype.Component;
import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderInquiryResponseModel;
import th.co.prior.demo4.model.OrderQueryResultModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderUtilComponent {
    public List<OrderInquiryResponseModel> transformQueryResultToResponseModel(List<OrderQueryResultModel> queryResult) {
        List<OrderInquiryResponseModel> orderInquiryResponseModel=new ArrayList<>();
        for (OrderQueryResultModel x:queryResult){
            OrderInquiryResponseModel y=new OrderInquiryResponseModel();
            y.setOrderStatus(x.getOrderStatus());
            y.setOrderBill(x.getOrderBill());
            y.setOrderMenu(x.getOrderMenu());
            y.setOrderValue(x.getOrderValue());

            orderInquiryResponseModel.add(y);

        }
        return orderInquiryResponseModel;
    }
}
