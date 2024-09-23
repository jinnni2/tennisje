package tennisje.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tennisje.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "machines", path = "machines")
public interface MachineRepository
    extends PagingAndSortingRepository<Machine, Long> {}
