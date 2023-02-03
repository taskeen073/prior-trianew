package th.co.prior.demo4.repository;

import th.co.prior.demo4.model.*;

import java.util.List;


public interface OrderRepository {
     List<OrderQueryResultModel> fetchOrderByCriteris(OrderInquiryRequestModel orderInquiryRequestModel);
     int insertTableBill(BillModel billModel);


     int updateOrderStatus(OrderInquiryRequestModel orderInquiryRequestModel);

     int  insertTableOrder(OrderInquiryRequestModel orderInquiryRequestModel);
}
