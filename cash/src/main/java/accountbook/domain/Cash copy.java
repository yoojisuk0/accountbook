// package accountbook.domain;

// import accountbook.CashApplication;
// import java.time.LocalDate;
// import java.util.Date;
// import java.util.List;
// import javax.persistence.*;
// import lombok.Data;

// @Entity
// @Table(name = "Cash_table")
// @Data
// //<<< DDD / Aggregate Root
// public class Cash {

//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     private Long id;

//     private Long accountId;

//     private Integer currentCash;

//     public static CashRepository repository() {
//         CashRepository cashRepository = CashApplication.applicationContext.getBean(
//             CashRepository.class
//         );
//         return cashRepository;
//     }

//     //<<< Clean Arch / Port Method
//     public static void increaseCash(
//         CreateIncomeCompleted createIncomeCompleted
//     ) {
//         //implement business logic here:

//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
//         increaseCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(createIncomeCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
//             increaseCashCompleted.publishAfterCommit();

//          });
//         */

//     }

//     //>>> Clean Arch / Port Method
//     //<<< Clean Arch / Port Method
//     public static void increaseCash(
//         DeleteExpenseCompleted deleteExpenseCompleted
//     ) {
//         //implement business logic here:

//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
//         increaseCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(deleteExpenseCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             IncreaseCashCompleted increaseCashCompleted = new IncreaseCashCompleted(cash);
//             increaseCashCompleted.publishAfterCommit();

//          });
//         */

//     }

//     //>>> Clean Arch / Port Method
//     //<<< Clean Arch / Port Method
//     public static void decreaseCash(
//         CreateExpenseCompleted createExpenseCompleted
//     ) {
//         //implement business logic here:

//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
//         decreaseCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(createExpenseCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
//             decreaseCashCompleted.publishAfterCommit();

//          });
//         */

//     }

//     //>>> Clean Arch / Port Method
//     //<<< Clean Arch / Port Method
//     public static void decreaseCash(
//         DeleteIncomeCompleted deleteIncomeCompleted
//     ) {
//         //implement business logic here:

//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
//         decreaseCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(deleteIncomeCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             DecreaseCashCompleted decreaseCashCompleted = new DecreaseCashCompleted(cash);
//             decreaseCashCompleted.publishAfterCommit();

//          });
//         */

//     }

//     //>>> Clean Arch / Port Method
//     //<<< Clean Arch / Port Method
//     public static void createCash(
//         CreateAccountCompleted createAccountCompleted
//     ) {
//         //implement business logic here:

//         Cash cash = new Cash();
//         repository().save(cash);

//         CreateCashCompleted createCashCompleted = new CreateCashCompleted(cash);
//         createCashCompleted.publishAfterCommit();




//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         CreateCashCompleted createCashCompleted = new CreateCashCompleted(cash);
//         createCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(createAccountCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             CreateCashCompleted createCashCompleted = new CreateCashCompleted(cash);
//             createCashCompleted.publishAfterCommit();

//          });
//         */

//     }

//     //>>> Clean Arch / Port Method
//     //<<< Clean Arch / Port Method
//     public static void deleteCash(
//         DeleteAccountCompleted deleteAccountCompleted
//     ) {
//         //implement business logic here:

//         Cash cash = new Cash();
//         repository().save(cash);

//         DeleteCashCompleted deleteCashCompleted = new DeleteCashCompleted(cash);
//         deleteCashCompleted.publishAfterCommit();

//         /** Example 1:  new item 
//         Cash cash = new Cash();
//         repository().save(cash);

//         DeleteCashCompleted deleteCashCompleted = new DeleteCashCompleted(cash);
//         deleteCashCompleted.publishAfterCommit();
//         */

//         /** Example 2:  finding and process
        
//         repository().findById(deleteAccountCompleted.get???()).ifPresent(cash->{
            
//             cash // do something
//             repository().save(cash);

//             DeleteCashCompleted deleteCashCompleted = new DeleteCashCompleted(cash);
//             deleteCashCompleted.publishAfterCommit();

//          });
//         */

//     }
//     //>>> Clean Arch / Port Method

// }
// // >>> DDD / Aggregate Root
