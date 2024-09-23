package tennisje.domain;

import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

@Data
@ToString
public class FullyBooked extends AbstractEvent {

    private Long id;
    private String courtName;
    private Long reserveId;
}
