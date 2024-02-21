package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CreateIncomeCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String categoryId;
    private Integer incomeCash;

    public CreateIncomeCompleted(Income aggregate) {
        super(aggregate);
    }

    public CreateIncomeCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
