package accountbook.domain;

import accountbook.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository
    extends PagingAndSortingRepository<Account, Long> {}
