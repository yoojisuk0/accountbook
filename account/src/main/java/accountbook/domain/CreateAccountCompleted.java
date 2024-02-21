package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CreateAccountCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String accountStatus;

    public CreateAccountCompleted(Account aggregate) {
        super(aggregate);
    }

    public CreateAccountCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
