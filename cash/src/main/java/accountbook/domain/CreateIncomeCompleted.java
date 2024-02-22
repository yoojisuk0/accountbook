package accountbook.domain;

import accountbook.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateIncomeCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String categoryId;
    private Integer incomeCash;

}

