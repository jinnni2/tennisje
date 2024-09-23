package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CourtDecreased extends AbstractEvent {

    private Long id;
    private String courtName;
    private Integer qty;

    public CourtDecreased(Court aggregate) {
        super(aggregate);
    }

    public CourtDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
