package accountbook.domain;

import accountbook.ExpenseApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Expense_table")
@Data
//<<< DDD / Aggregate Root
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String categoryId;

    private Integer expenseCash;

    public static ExpenseRepository repository() {
        ExpenseRepository expenseRepository = ExpenseApplication.applicationContext.getBean(
            ExpenseRepository.class
        );
        return expenseRepository;
    }

    //<<< Clean Arch / Port Method
    public static void modifyCategory(
        ModifyCategoryCompleted modifyCategoryCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Expense expense = new Expense();
        repository().save(expense);

        ModifyCategoryCompleted modifyCategoryCompleted = new ModifyCategoryCompleted(expense);
        modifyCategoryCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(modifyCategoryCompleted.get???()).ifPresent(expense->{
            
            expense // do something
            repository().save(expense);

            ModifyCategoryCompleted modifyCategoryCompleted = new ModifyCategoryCompleted(expense);
            modifyCategoryCompleted.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
