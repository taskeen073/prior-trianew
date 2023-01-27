package th.co.prior.demo4.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.prior.demo4.component.OrderUtilComponent;
import th.co.prior.demo4.model.*;
import th.co.prior.demo4.repository.OrderRepository;

import java.io.IOException;
import java.sql.SQLException;
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

    public ResponseModel<Void>  insertNewBill(BillModel billModel){
        ResponseModel<Void> result = new ResponseModel<>();

        result.setStatusCode(201);
        result.setDescription("ok");
        try {
            this.insertTableBill(billModel);

        } catch (Exception e){
            e.printStackTrace();
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }

    @Transactional(rollbackFor = SQLException.class, propagation = Propagation.REQUIRES_NEW)
    public BillModel insertTableBill(BillModel billModel) throws IOException {

        int tableBillId = this.orderRepository.insertTableBill(billModel);

        BillModel x = new BillModel();
        x.setBillTable(tableBillId);
        x.setBillStatus("NEW");
        x.setBillWaiter(tableBillId);

    return x;
    }


    public ResponseModel<Integer> updateOrderStatus(OrderInquiryRequestModel orderInquiryRequestModel) {
        ResponseModel<Integer> result = new ResponseModel();

        result.setStatusCode(201);
        result.setDescription("ok");

        try {
            Integer dataResponse = this.orderRepository.updateOrderStatus(orderInquiryRequestModel);
            result.setData(dataResponse);
            result.setDescription("SUCCESS");
            result.setStatusCode(200);
        } catch (Exception e){
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return  result;
    }

    public ResponseModel<Void> insertNewOrder(OrderInquiryRequestModel orderInquiryRequestModel) {
        ResponseModel<Void> result = new ResponseModel<>();

        result.setStatusCode(201);
        result.setDescription("ok");
        try {
            // do some business
            this.insertTableOrder(orderInquiryRequestModel);

//            insert billOrder
//
        } catch (Exception e){
            e.printStackTrace();
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;

    }

    @Transactional(rollbackFor = SQLException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertTableOrder(OrderInquiryRequestModel orderInquiryRequestModel) {

        int orderTableId = this.orderRepository.insertTableOrder(orderInquiryRequestModel);

        OrderInsertRequestModel x = new OrderInsertRequestModel();
        x.setOrderMenu(orderTableId);
        x.setOrderStatus("NEW");

    }


}
