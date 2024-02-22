![image](https://play-lh.googleusercontent.com/XskKKrerCZPR5YH9iqSNNI9RmBedf_UN5KDr6ffeypSG-yn1dBi2yRoahttm9ENqvi-d=w240-h480-rw) ![뱅크샐러드](https://github.com/yoojisuk0/account-book/assets/61814964/c0901320-abe9-44ab-bc8a-1863d890568c)


# 가계부


# Table of contents

- [예제 - 음식배달](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오


기능적 요구사항
1. 고객이 가계부를 등록한다
1. 가계부가 등록되면 잔고가 생성된다
1. 고객이 수입을 등록한다
1. 수입이 등록되면 잔고가 증액된다
1. 고객이 지출을 등록한다
1. 지출이 등록되면 잔고가 감액된다


# 클라우드 아키텍처 설계



# 구현:

```
cd account
mvn spring-boot:run

cd cash
mvn spring-boot:run 

cd income
mvn spring-boot:run 

cd expense
mvn spring-boot:run

cd category
mvn spring-boot:run

cd gateway
mvn spring-boot:run
```

## DDD 의 적용

```
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



```
- Repository
```
package accountbook.domain;

import accountbook.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="cash", path="cash")
public interface CashRepository extends PagingAndSortingRepository<Cash, Long>{
}
```
- REST API 의 테스트
```
# account 서비스의 가계부 생성
http localhost:8088/accounts accountId=1

# account 서비스의 가계부 생성 확인
http localhost:8088/accounts/1

# cash 서비스의 잔고 생성 및 잔액 확인
http localhost:8088/cash/1

# incomes 서비스의 수입 등록
http localhost:8088/incomes accountId=1 incomeCash=1000

# incomes 서비스의 수입 등록 확인
http localhost:8088/incomes/1

# cash 서비스의 잔액 확인
http localhost:8088/cash/1

# expense 서비스의 지출 등록
http localhost:8088/expenses accountId=1 expenseCash=500

# cash 서비스의 잔액 확인
http localhost:8088/cash/1

```


- Kafka StreamListener


```
package accountbook.infra;

import accountbook.config.kafka.KafkaProcessor;
import accountbook.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 가계부 등록 완료
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateAccountCompleted'"
    )
    public void wheneverCreateAccountCompleted_CreateCash(
        @Payload CreateAccountCompleted createAccountCompleted
    ) {
        CreateAccountCompleted event = createAccountCompleted;
        System.out.println(
            "\n\n##### listener CreateCash : " + createAccountCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.createCash(event);
    }

    // 가계부 삭제 완료
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteAccountCompleted'"
    )
    public void wheneverDeleteAccountCompleted_DeleteCash(
        @Payload DeleteAccountCompleted deleteAccountCompleted
    ) {
        DeleteAccountCompleted event = deleteAccountCompleted;
        System.out.println(
            "\n\n##### listener DeleteCash : " + deleteAccountCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.deleteCash(event);
    }

    // 수입 등록 완료 -> 증액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateIncomeCompleted'"
    )
    public void wheneverCreateIncomeCompleted_IncreaseCash(
        @Payload CreateIncomeCompleted createIncomeCompleted
    ) {
        CreateIncomeCompleted event = createIncomeCompleted;
        System.out.println(
            "\n\n##### listener IncreaseCash : " + createIncomeCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.increaseCash(event);
    }

    // 지출 삭제 완료 -> 증액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteExpenseCompleted'"
    )
    public void wheneverDeleteExpenseCompleted_IncreaseCash(
        @Payload DeleteExpenseCompleted deleteExpenseCompleted
    ) {
        DeleteExpenseCompleted event = deleteExpenseCompleted;
        System.out.println(
            "\n\n##### listener IncreaseCash : " + deleteExpenseCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.increaseCash(event);
    }

    // 지출 등록 완료 -> 감액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateExpenseCompleted'"
    )
    public void wheneverCreateExpenseCompleted_DecreaseCash(
        @Payload CreateExpenseCompleted createExpenseCompleted
    ) {
        CreateExpenseCompleted event = createExpenseCompleted;
        System.out.println(
            "\n\n##### listener DecreaseCash : " + createExpenseCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.decreaseCash(event);
    }

    // 수입 삭제 완료 -> 감액
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='DeleteIncomeCompleted'"
    )
    public void wheneverDeleteIncomeCompleted_DecreaseCash(
        @Payload DeleteIncomeCompleted deleteIncomeCompleted
    ) {
        DeleteIncomeCompleted event = deleteIncomeCompleted;
        System.out.println(
            "\n\n##### listener DecreaseCash : " + deleteIncomeCompleted + "\n\n"
        );

        // Sample Logic //
        Cash.decreaseCash(event);
    }
    
}
//>>> Clean Arch / Inbound Adaptor

```
