package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import tennisje.infra.AbstractEvent;

@Data
public class FullyBooked extends AbstractEvent {

    private Long id;
    private String courtName;
    private Long reserveId;
}
