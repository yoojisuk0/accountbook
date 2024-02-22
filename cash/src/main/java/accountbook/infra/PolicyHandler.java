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

    // 가계부 등록 완료
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

    // 가계부 삭제 완료
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

    // 수입 등록 완료 -> 증액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateIncomeCompleted'"
    )
    public void wheneverCreateIncomeCompleted_IncreaseCash(
        @Payload CreateIncomeCompleted createIncomeCompleted
    ) {
        CreateIncomeCompleted event = createIncomeCompleted;
        System.out.println(
            "\n\n##### listener IncreaseCash : " + createIncomeCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.increaseCash(event);
    }

    // 지출 삭제 완료 -> 증액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteExpenseCompleted'"
    )
    public void wheneverDeleteExpenseCompleted_IncreaseCash(
        @Payload DeleteExpenseCompleted deleteExpenseCompleted
    ) {
        DeleteExpenseCompleted event = deleteExpenseCompleted;
        System.out.println(
            "\n\n##### listener IncreaseCash : " + deleteExpenseCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.increaseCash(event);
    }

    // 지출 등록 완료 -> 감액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateExpenseCompleted'"
    )
    public void wheneverCreateExpenseCompleted_DecreaseCash(
        @Payload CreateExpenseCompleted createExpenseCompleted
    ) {
        CreateExpenseCompleted event = createExpenseCompleted;
        System.out.println(
            "\n\n##### listener DecreaseCash : " + createExpenseCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.decreaseCash(event);
    }

    // 수입 삭제 완료 -> 감액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteIncomeCompleted'"
    )
    public void wheneverDeleteIncomeCompleted_DecreaseCash(
        @Payload DeleteIncomeCompleted deleteIncomeCompleted
    ) {
        DeleteIncomeCompleted event = deleteIncomeCompleted;
        System.out.println(
            "\n\n##### listener DecreaseCash : " + deleteIncomeCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.decreaseCash(event);
    }
    
}
//>>> Clean Arch / Inbound Adaptor
