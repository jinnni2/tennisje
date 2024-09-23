package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CourtReserved extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long courtId;
    private String courtName;
    private String status;

    public CourtReserved(Reserve aggregate) {
        super(aggregate);
    }

    public CourtReserved() {
        super();
    }
}
//>>> DDD / Domain Event
