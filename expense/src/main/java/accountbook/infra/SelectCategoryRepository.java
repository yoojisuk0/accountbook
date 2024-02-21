package accountbook.infra;

import accountbook.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "selectCategories",
    path = "selectCategories"
)
public interface SelectCategoryRepository
    extends PagingAndSortingRepository<SelectCategory, Long> {}
