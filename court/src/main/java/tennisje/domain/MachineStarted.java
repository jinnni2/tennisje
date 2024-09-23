package tennisje.domain;

import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

@Data
@ToString
public class MachineStarted extends AbstractEvent {

    private Long id;
    private Long reserveId;
    private Long courtId;
    private String status;
}
