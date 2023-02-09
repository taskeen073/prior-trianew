package th.co.prior.demo4.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import th.co.prior.demo4.component.KafkaProducerComponent;
import th.co.prior.demo4.component.OrderUtilComponent;
import th.co.prior.demo4.model.*;
import th.co.prior.demo4.repository.OrderRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderUtilComponent orderUtilComponent;
    private KafkaProducerComponent kafkaProducerComponent;

    @Value("${kafka.topics.regist.get.order}")
    private String kafkaGetOrderWaiterToKitchener ;
    @Value("${kafka.topics.regist.update.order}")
    private String kafkaUpdateKitchenerToWaiter ;

    public OrderService(OrderRepository orderRepository, OrderUtilComponent orderUtilComponent,KafkaProducerComponent kafkaProducerComponent) {
        this.orderRepository = orderRepository;
        this.orderUtilComponent = orderUtilComponent;
        this.kafkaProducerComponent =kafkaProducerComponent;
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
            //queForWaiterToKitchener

        } catch (Exception e){
            e.printStackTrace();
            result.setStatusCode(500);
            result.setDescription(e.getMessage());
        }
        return result;
    }
    private String objectToJsonString(Object model) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(model);
    }

    @Transactional(rollbackFor = SQLException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertTableBill(BillModel billModel) throws IOException {

         this.orderRepository.insertTableBill(billModel);

    }


    public ResponseModel<Integer> updateOrderStatus(OrderInquiryRequestModel orderInquiryRequestModel) {
        ResponseModel<Integer> result = new ResponseModel<>();

        result.setStatusCode(201);
        result.setDescription("ok");

        try {
            Integer dataResponse = this.orderRepository.updateOrderStatus(orderInquiryRequestModel);
            result.setData(dataResponse);
            result.setDescription("SUCCESS");
            result.setStatusCode(200);
            String message = this.objectToJsonString(orderInquiryRequestModel);
            this.kafkaProducerComponent.senData(message,this.kafkaUpdateKitchenerToWaiter);

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

            // insert billOrder

            String message = this.objectToJsonString(orderInquiryRequestModel);

            this.kafkaProducerComponent.senData(message,kafkaGetOrderWaiterToKitchener);


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


    public void insertOrderInfo(String message) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            OrderInquiryRequestModel x = mapper.readValue(message, OrderInquiryRequestModel.class);

        }catch (Exception e){
            e.printStackTrace();
            log.info("insertOrderInfo error {}",e.getMessage());
        }
    }
    public void updateOrderInfo(String message) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            OrderInquiryRequestModel x = mapper.readValue(message, OrderInquiryRequestModel.class);

        }catch (Exception e){
            e.printStackTrace();
            log.info("updateOrderInfo error {}",e.getMessage());
        }
    }
}
