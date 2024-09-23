package tennisje.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import tennisje.infra.AbstractEvent;

@Data
public class CourtCanceled extends AbstractEvent {

    private Long id;
    private Long userId;
    private Long courtId;
    private String courtName;
    private String status;
}
