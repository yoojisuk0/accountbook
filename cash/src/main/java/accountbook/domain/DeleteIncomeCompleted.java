package accountbook.domain;

import accountbook.infra.AbstractEvent;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeleteIncomeCompleted extends AbstractEvent {

    private Long id;
    private Long accountId;
    private String categoryId;
    private Integer incomeCash;

}
