package accountbook.domain;

import accountbook.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="categories", path="categories")
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
}