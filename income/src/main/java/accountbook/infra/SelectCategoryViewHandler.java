package accountbook.infra;

import accountbook.config.kafka.KafkaProcessor;
import accountbook.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SelectCategoryViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SelectCategoryRepository selectCategoryRepository;
    //>>> DDD / CQRS
}
