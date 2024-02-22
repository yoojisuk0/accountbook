package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CreateCategoryCompleted extends AbstractEvent {

    private Long id;
    private Long accountId;
    private String categoryType;

    public CreateCategoryCompleted(Category aggregate) {
        super(aggregate);
    }

    public CreateCategoryCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
