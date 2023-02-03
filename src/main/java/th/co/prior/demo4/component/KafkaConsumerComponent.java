package th.co.prior.demo4.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import th.co.prior.demo4.service.OrderService;

@Slf4j
@Component
public class KafkaConsumerComponent {


    private OrderService orderService;

    public KafkaConsumerComponent(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${kafka.topics.regist.update.order}", groupId = "${kafka.consumer.group-id}")
    public void updateResult(@Payload String message) throws Exception {
        log.info("Kitchener say message  {}", message);
        this.orderService.updateOrderInfo(message);
   }
    @KafkaListener(topics = "${kafka.topics.regist.get.order}", groupId = "$kafka.consumer.group-id}")
    public void getResult(@Payload String message) throws Exception {
        log.info("Waiter say message got {}", message);
        this.orderService.insertOrderInfo(message);
    }
}