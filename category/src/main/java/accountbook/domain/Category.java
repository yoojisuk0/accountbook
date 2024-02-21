package accountbook.domain;

import accountbook.CategoryApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Category_table")
@Data
//<<< DDD / Aggregate Root
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userid;

    private String categoryType;

    public static CategoryRepository repository() {
        CategoryRepository categoryRepository = CategoryApplication.applicationContext.getBean(
            CategoryRepository.class
        );
        return categoryRepository;
    }
}
//>>> DDD / Aggregate Root
