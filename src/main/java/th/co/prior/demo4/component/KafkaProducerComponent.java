package th.co.prior.demo4.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class KafkaProducerComponent {

    private KafkaTemplate<String,String> kafkaTemplate;
    public KafkaProducerComponent(@Qualifier("registerKafkaTemplate") KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void senData(String message,String topics) {
        this.kafkaTemplate.send(topics,message)
                .whenComplete((result, throwable) -> {
                    if (null != throwable) {
                        log.info("kafka send to done {}"
                                , result.getRecordMetadata().topic()
                                , result.getProducerRecord().value());
                    }
                    else {
                        log.info("kafka send exception {}" ,throwable.getMessage());
                    }

                });
    }
}
