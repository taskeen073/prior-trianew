package th.co.prior.demo4.service;


import org.springframework.stereotype.Service;
import th.co.prior.demo4.component.OrderUtilComponent;
import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderInquiryResponseModel;
import th.co.prior.demo4.model.OrderQueryResultModel;
import th.co.prior.demo4.model.ResponseModel;
import th.co.prior.demo4.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderUtilComponent orderUtilComponent;

    public OrderService(OrderRepository orderRepository, OrderUtilComponent orderUtilComponent) {
        this.orderRepository = orderRepository;
        this.orderUtilComponent = orderUtilComponent;
    }

    public ResponseModel<List<OrderInquiryResponseModel>> getOrderByCriteria(OrderInquiryRequestModel orderInquiryRequestModel) {
    ResponseModel<List<OrderInquiryResponseModel>> result =new ResponseModel<>();
        result.setStatusCode(200);
        result.setDescription("Success");

    try {
//Validate
//        Query
        List<OrderQueryResultModel> queryResult= this.orderRepository.fetchOrderByCriteris(orderInquiryRequestModel);

//        transform

        List<OrderInquiryResponseModel> dataList = this.orderUtilComponent.transformQueryResultToResponseModel(queryResult);
//        Result
        result.setData(dataList);
    } catch (Exception e){
        result.setStatusCode(500);
        result.setDescription(e.getMessage());
    }

    return result;
    }



}
