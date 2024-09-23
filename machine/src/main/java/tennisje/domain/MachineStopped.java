package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MachineStopped extends AbstractEvent {

    private Long id;
    private Long courtId;
    private String status;

    public MachineStopped(Machine aggregate) {
        super(aggregate);
    }

    public MachineStopped() {
        super();
    }
}
//>>> DDD / Domain Event
