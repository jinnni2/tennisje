package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FullyBooked extends AbstractEvent {

    private Long id;
    private String courtName;
    private Long reserveId;

    public FullyBooked(Court aggregate) {
        super(aggregate);
    }

    public FullyBooked() {
        super();
    }
}
//>>> DDD / Domain Event
