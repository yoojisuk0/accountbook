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

public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private Integer currentCash;

    public static CashRepository repository() {
        CashRepository cashRepository = CashApplication.applicationContext.getBean(
            CashRepository.class
        );
        return cashRepository;
    }

    // 잔고 생성
    public static void createCash(CreateAccountCompleted createAccountCompleted) {
        Cash cash = new Cash();
        cash.setAccountId(createAccountCompleted.getAccountId());
        cash.setCurrentCash(0);
        repository().save(cash);

        CreateCashCompleted createCashCompleted = new CreateCashCompleted(cash);
        createCashCompleted.publishAfterCommit();
    }

    // 잔고 삭제
    public static void deleteCash(DeleteAccountCompleted deleteAccountCompleted) {
        repository().findById(deleteAccountCompleted.getAccountId()).ifPresent(cash->{
 
            repository().delete(cash);

            DeleteCashCompleted deleteCashCompleted = new DeleteCashCompleted(cash);
            deleteCashCompleted.publishAfterCommit();

         });
    }
    

    // 수입등록 -> 증액
    public static void increaseCash(CreateIncomeCompleted createIncomeCompleted) {
       
        repository().findById(createIncomeCompleted.getAccountId()).ifPresent(cash->{
            
            // do something
            cash.setCurrentCash(cash.getCurrentCash() + createIncomeCompleted.getIncomeCash());
            repository().save(cash);

            IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
            increaseCashCompleted.publishAfterCommit();

         }); 
    }


    // 지출등록 -> 증액
    public static void increaseCash(DeleteExpenseCompleted deleteExpenseCompleted) {

        repository().findById(deleteExpenseCompleted.getAccountId()).ifPresent(cash->{
            
            // do something
            cash.setCurrentCash(cash.getCurrentCash() + deleteExpenseCompleted.getExpenseCash());
            repository().save(cash);

            IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
            increaseCashCompleted.publishAfterCommit();

         }); 
    }

    
    // 지출등록 -> 감액
    public static void decreaseCash(CreateExpenseCompleted createExpenseCompleted) {

        repository().findById(createExpenseCompleted.getAccountId()).ifPresent(cash->{
            
            // do something
            cash.setCurrentCash(cash.getCurrentCash() - createExpenseCompleted.getExpenseCash());
            repository().save(cash);

            DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
            decreaseCashCompleted.publishAfterCommit();

         });
    }

    
    // 입금삭제 -> 감액
    public static void decreaseCash(DeleteIncomeCompleted deleteIncomeCompleted) {

        repository().findById(deleteIncomeCompleted.getAccountId()).ifPresent(cash->{
            
            // do something
            cash.setCurrentCash(cash.getCurrentCash() - deleteIncomeCompleted.getIncomeCash());
            repository().save(cash);

            DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
            decreaseCashCompleted.publishAfterCommit();

         });
    }



}

