package accountbook.domain;

import accountbook.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="cash", path="cash")
public interface CashRepository extends PagingAndSortingRepository<Cash, Long>{
}