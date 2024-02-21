package accountbook.domain;

import accountbook.AccountApplication;
import accountbook.domain.CreateAccountCompleted;
import accountbook.domain.DeleteAccountCompleted;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Account_table")
@Data
//<<< DDD / Aggregate Root
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String accountStatus;

    @PostPersist
    public void onPostPersist() {
        CreateAccountCompleted createAccountCompleted = new CreateAccountCompleted(
            this
        );
        createAccountCompleted.publishAfterCommit();

        DeleteAccountCompleted deleteAccountCompleted = new DeleteAccountCompleted(
            this
        );
        deleteAccountCompleted.publishAfterCommit();
    }

    public static AccountRepository repository() {
        AccountRepository accountRepository = AccountApplication.applicationContext.getBean(
            AccountRepository.class
        );
        return accountRepository;
    }
}
//>>> DDD / Aggregate Root
