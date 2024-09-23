package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MachineCleaned extends AbstractEvent {

    private Long id;
    private Long reserveId;
    private Long courtId;
    private String status;

    public MachineCleaned(Machine aggregate) {
        super(aggregate);
    }

    public MachineCleaned() {
        super();
    }
}
//>>> DDD / Domain Event
