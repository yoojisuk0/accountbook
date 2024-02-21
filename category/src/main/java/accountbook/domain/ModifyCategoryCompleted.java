package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ModifyCategoryCompleted extends AbstractEvent {

    private Long id;
    private String userid;
    private String categoryType;

    public ModifyCategoryCompleted(Category aggregate) {
        super(aggregate);
    }

    public ModifyCategoryCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
