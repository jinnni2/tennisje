package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CourtCanceled extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long courtId;
    private String courtName;
    private String status;

    public CourtCanceled(Reserve aggregate) {
        super(aggregate);
    }

    public CourtCanceled() {
        super();
    }
}
//>>> DDD / Domain Event
