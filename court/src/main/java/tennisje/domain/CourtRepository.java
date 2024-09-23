package tennisje.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tennisje.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "courts", path = "courts")
public interface CourtRepository
    extends PagingAndSortingRepository<Court, Long> {}
