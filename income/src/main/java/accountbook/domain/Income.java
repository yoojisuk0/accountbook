package accountbook.domain;

import accountbook.IncomeApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Income_table")
@Data
//<<< DDD / Aggregate Root
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountId;

    private String categoryId;

    private Integer incomeCash;

    @PostPersist
    public void onPostPersist() {
        CreateIncomeCompleted createIncomeCompleted = new CreateIncomeCompleted(
            this
        );
        createIncomeCompleted.publishAfterCommit();

    }
    

    public static IncomeRepository repository() {
        IncomeRepository incomeRepository = IncomeApplication.applicationContext.getBean(
            IncomeRepository.class
        );
        return incomeRepository;
    }

    //<<< Clean Arch / Port Method
    public static void modifyCategory(
        ModifyCategoryCompleted modifyCategoryCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Income income = new Income();
        repository().save(income);

        ModifyCategoryCompleted modifyCategoryCompleted = new ModifyCategoryCompleted(income);
        modifyCategoryCompleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(modifyCategoryCompleted.get???()).ifPresent(income->{
            
            income // do something
            repository().save(income);

            ModifyCategoryCompleted modifyCategoryCompleted = new ModifyCategoryCompleted(income);
            modifyCategoryCompleted.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
