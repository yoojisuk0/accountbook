package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CreateCashCompleted extends AbstractEvent {

    private Long id;
    private String accountId;
    private Integer currentCash;

    public CreateCashCompleted(Cash aggregate) {
        super(aggregate);
    }

    public CreateCashCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
