package accountbook.domain;

import accountbook.CashApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cash_table")
@Data
//<<< DDD / Aggregate Root
public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountId;

    private Integer currentCash;

    public static CashRepository repository() {
        CashRepository cashRepository = CashApplication.applicationContext.getBean(
            CashRepository.class
        );
        return cashRepository;
    }

    //<<< Clean Arch / Port Method
    public static void increaseCash(CreateIncomeCompleted createIncomeCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
        increaseCashCompleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseCash(DeleteExpenseCompleted deleteExpenseCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
        increaseCashCompleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void decreaseCash(CreateExpenseCompleted createExpenseCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
        decreaseCashCompleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void decreaseCash(DeleteIncomeCompleted deleteIncomeCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
        decreaseCashCompleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void createCash(CreateAccountCompleted createAccountCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        CreateCashCompleted createCashCompleted = new CreateCashCompleted(cash);
        createCashCompleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void deleteCash(DeleteAccountCompleted deleteAccountCompleted) {
        Cash cash = new Cash();
        repository().save(cash);

        DeleteCashCompleted deleteCashCompleted = new DeleteCashCompleted(cash);
        deleteCashCompleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
// >>> DDD / Aggregate Root
