![image](https://play-lh.googleusercontent.com/XskKKrerCZPR5YH9iqSNNI9RmBedf_UN5KDr6ffeypSG-yn1dBi2yRoahttm9ENqvi-d=w240-h480-rw) ![뱅크샐러드](https://github.com/yoojisuk0/account-book/assets/61814964/c0901320-abe9-44ab-bc8a-1863d890568c)


# 가계부

# 클라우드 네이티브 아키텍처(IaaS)
### 클라우드 아키텍처 구성, MSA 아키텍처 구성도
- 서비스 시나리오
기능적 요구사항
1. 고객이 가계부를 등록한다
1. 가계부가 등록되면 잔고가 생성된다
1. 고객이 수입을 등록한다
1. 수입이 등록되면 잔고가 증액된다
1. 고객이 지출을 등록한다
1. 지출이 등록되면 잔고가 감액된다

# 클라우드 네이티브 모델링(Biz.)
### 도메인분석 - 이벤트스토밍
- 도메인 구성 (1단계)

![image](https://github.com/yoojisuk0/accountbook/assets/61814964/176a26b8-0537-4c02-9943-5736e6e6f45c)

- 바운디드 컨텍스트 구성 (2단계)
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/a3b286e7-d1b9-4787-9f13-902b668c5c0f)

- 이벤트스토밍 MSA 모델링 (3단계)
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/ba8aa70f-35fc-4891-9793-e660eda9d19e)

# 클라우드 네이티브 개발(Dev.)
### 분산트랜잭션 - Saga
- 구현:

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
- DDD 의 적용

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



### 보상처리 - Compensation
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
- kafka 기동 확인
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/a6857296-c0af-42f9-8149-6767e095090e)


### 단일 진입점 - Gateway
- gateway application.yml
```
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: account
          uri: http://localhost:8082
          predicates:
            - Path=/accounts/**, 
        - id: income
          uri: http://localhost:8083
          predicates:
            - Path=/incomes/**, 
        - id: expense
          uri: http://localhost:8084
          predicates:
            - Path=/expenses/**, 
        - id: category
          uri: http://localhost:8085
          predicates:
            - Path=/categorys/**, 
        - id: cash
          uri: http://localhost:8086
          predicates:
            - Path=/cash/**
#>>> API Gateway / Routes
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true


---

spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: account
          uri: http://account:8080
          predicates:
            - Path=/accounts/**, 
        - id: income
          uri: http://income:8080
          predicates:
            - Path=/incomes/**, 
        - id: expense
          uri: http://expense:8080
          predicates:
            - Path=/expenses/**, 
        - id: category
          uri: http://category:8080
          predicates:
            - Path=/categories/**, 
        - id: cash
          uri: http://cash:8080
          predicates:
            - Path=/cash/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080

```

- TEST
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/418f51cc-9e42-4789-9842-ba5a7cf7fa9b)


### 분산 데이터 프로젝션 - CQRS

# 클라우드 네이티브 운영(Paas)
### 클라우드 배포 - Container 운영
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/758a51a8-fd7c-4c0d-afec-34030eeb9d66)


### 컨테이너 자동확장 - HPA
- HPA 생성

![image](https://github.com/yoojisuk0/accountbook/assets/61814964/8ac5ca71-2b52-466c-b331-8997d0fec3b9)

- TEST

![image](https://github.com/yoojisuk0/accountbook/assets/61814964/b43a13ef-a5c9-4926-9d14-f372c8546f41)


### 컨테이너로부터 환경 분리 - ConfigMap/Secret 
- ConfigMap 생성
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/ae355af2-36a8-424e-9413-aa0dfdfc3ff7)

### 클라우드스토리지 활용 - PVC
- helm을 통해 설치한 kafka는 pvc를 사용하는것 같다.
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/37e903f6-3798-49be-b981-7c2ebf34d1c2)

- efs-sc 스토리지 클래스 생성 확인
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/7676a0f9-11e4-455d-b925-573caa5433c2)
- PVC 생성

![image](https://github.com/yoojisuk0/accountbook/assets/61814964/55d64f76-f9e6-489b-9617-4e95668304cc)



### 셀프 힐링/무정지배포 - Liveness/Rediness Probe
### 서비스 메쉬 응용 - Mesh
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/166b46ee-22ec-497c-b3e8-81ed17786b77)
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/4960902a-80a5-472d-aa2e-8342dff8cc7e)


### 통합 모니터링 - Loggregation/Monitoring
- Kiali
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/da13fe7e-62d1-4074-a718-a96f7fc08fb7)

- Jaeger
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/e39a9113-7641-4360-adb2-a03401f70753)

- Prometheus
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/3bbeb11c-b370-40b6-af32-713cf6916fb2)

- Grafana
![image](https://github.com/yoojisuk0/accountbook/assets/61814964/ef3e6139-7618-45ff-9f13-e741b865a19a)

