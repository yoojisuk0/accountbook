package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeleteAccountCompleted extends AbstractEvent {

    private Long id;
    private Long accountId;
    private String accountStatus;

    public DeleteAccountCompleted(Account aggregate) {
        super(aggregate);
    }

    public DeleteAccountCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
