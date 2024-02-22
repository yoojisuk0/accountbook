package accountbook.domain;

import accountbook.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DecreaseCashCompleted extends AbstractEvent {

    private Long id;
    private String accountId;
    private Integer currentCash;

    public DecreaseCashCompleted(Cash aggregate) {
        super(aggregate);
    }

    public DecreaseCashCompleted() {
        super();
    }
}
