package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeleteIncomeCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String categoryId;
    private Integer incomeCash;

    public DeleteIncomeCompleted(Income aggregate) {
        super(aggregate);
    }

    public DeleteIncomeCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
