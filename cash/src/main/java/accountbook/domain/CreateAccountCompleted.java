package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CreateAccountCompleted extends AbstractEvent {

    private Long id;
    private Long accountId;
    private String accountStatus;
}
