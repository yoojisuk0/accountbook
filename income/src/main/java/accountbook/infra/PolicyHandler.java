package accountbook.infra;

import accountbook.config.kafka.KafkaProcessor;
import accountbook.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ModifyCategoryCompleted'"
    )
    public void wheneverModifyCategoryCompleted_ModifyCategory(
        @Payload ModifyCategoryCompleted modifyCategoryCompleted
    ) {
        ModifyCategoryCompleted event = modifyCategoryCompleted;
        System.out.println(
            "\n\n##### listener ModifyCategory : " +
            modifyCategoryCompleted +
            "\n\n"
        );

        // Sample Logic //
        Income.modifyCategory(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
