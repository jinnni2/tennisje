package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CourtIncreased extends AbstractEvent {

    private Long id;
    private String courtName;
    private Integer qty;

    public CourtIncreased(Court aggregate) {
        super(aggregate);
    }

    public CourtIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
