package th.co.prior.demo4.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import th.co.prior.demo4.model.OrderInquiryRequestModel;
import th.co.prior.demo4.model.OrderInquiryResponseModel;
import th.co.prior.demo4.model.ResponseModel;
import th.co.prior.demo4.service.OrderService;

import java.util.List;

@RestController
public class OrderRestController {

    private OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/inquiry/order")
    public ResponseModel<List<OrderInquiryResponseModel>> inquiryOrder(
            @RequestBody OrderInquiryRequestModel orderInquiryRequestModel,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return this.orderService.getOrderByCriteria(orderInquiryRequestModel);
    }
}
