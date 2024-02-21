package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeleteExpenseCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String categoryId;
    private Integer expenseCash;

    public DeleteExpenseCompleted(Expense aggregate) {
        super(aggregate);
    }

    public DeleteExpenseCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
