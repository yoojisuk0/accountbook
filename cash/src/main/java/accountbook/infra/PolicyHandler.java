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
        condition = "headers['type']=='CreateAccountCompleted'"
    )
    public void wheneverCreateAccountCompleted_CreateCash(
        @Payload CreateAccountCompleted createAccountCompleted
    ) {
        CreateAccountCompleted event = createAccountCompleted;
        System.out.println(
            "\n\n##### listener CreateCash : " + createAccountCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.createCash(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteAccountCompleted'"
    )
    public void wheneverDeleteAccountCompleted_DeleteCash(
        @Payload DeleteAccountCompleted deleteAccountCompleted
    ) {
        DeleteAccountCompleted event = deleteAccountCompleted;
        System.out.println(
            "\n\n##### listener DeleteCash : " + deleteAccountCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.deleteCash(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
