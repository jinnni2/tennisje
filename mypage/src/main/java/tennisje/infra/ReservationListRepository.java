package tennisje.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tennisje.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "reservationLists",
    path = "reservationLists"
)
public interface ReservationListRepository
    extends PagingAndSortingRepository<ReservationList, Long> {
    List<ReservationList> findByReserveId(Long reserveId);
}
