package accountbook.domain;

import accountbook.domain.*;
import accountbook.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class DeleteAccountCompleted extends AbstractEvent {

    private Long id;
    private String userId;
    private String accountStatus;
}
