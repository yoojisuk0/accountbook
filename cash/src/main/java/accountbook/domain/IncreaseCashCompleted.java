package accountbook.domain;

import accountbook.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IncreaseCashCompleted extends AbstractEvent {

    private Long id;
    private String accountId;
    private Integer currentCash;

    public IncreaseCashCompleted(Cash aggregate) {
        super(aggregate);
    }

    public IncreaseCashCompleted() {
        super();
    }
}