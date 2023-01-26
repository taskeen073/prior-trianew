package th.co.prior.demo4.repository;

import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderQueryResultModel;

import java.util.List;


public interface OrderRepository {
     List<OrderQueryResultModel> fetchOrderByCriteris(OrderInquiryRequestModel orderInquiryRequestModel);
}
