package tennisje.domain;

import java.util.*;
import lombok.*;
import tennisje.domain.*;
import tennisje.infra.AbstractEvent;

@Data
@ToString
public class CourtReserved extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long courtId;
    private String courtName;
    private String status;
}
