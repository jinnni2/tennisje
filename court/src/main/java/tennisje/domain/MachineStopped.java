package tennisje.domain;

import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

@Data
@ToString
public class MachineStopped extends AbstractEvent {

    private Long id;
    private Long courtId;
    private String status;
}
