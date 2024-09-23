package tennisje.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import tennisje.CourtApplication;
import tennisje.domain.CourtDecreased;
import tennisje.domain.CourtIncreased;
import tennisje.domain.FullyBooked;

@Entity
@Table(name = "Court_table")
@Data
//<<< DDD / Aggregate Root
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String courtName;

    private Integer qty;

    private Long reserveId;

    @PostUpdate
    public void onPostUpdate() {
        CourtDecreased courtDecreased = new CourtDecreased(this);
        courtDecreased.publishAfterCommit();

        FullyBooked fullyBooked = new FullyBooked(this);
        fullyBooked.publishAfterCommit();

        CourtIncreased courtIncreased = new CourtIncreased(this);
        courtIncreased.publishAfterCommit();
    }

    public static CourtRepository repository() {
        CourtRepository courtRepository = CourtApplication.applicationContext.getBean(
            CourtRepository.class
        );
        return courtRepository;
    }

    //<<< Clean Arch / Port Method
    public static void decreaseCourt(MachineCleaned machineCleaned) {
        //implement business logic here:

        /** Example 1:  new item 
        Court court = new Court();
        repository().save(court);

        CourtDecreased courtDecreased = new CourtDecreased(court);
        courtDecreased.publishAfterCommit();
        FullyBooked fullyBooked = new FullyBooked(court);
        fullyBooked.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(machineCleaned.get???()).ifPresent(court->{
            
            court // do something
            repository().save(court);

            CourtDecreased courtDecreased = new CourtDecreased(court);
            courtDecreased.publishAfterCommit();
            FullyBooked fullyBooked = new FullyBooked(court);
            fullyBooked.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void increaseCourt(CourtCanceled courtCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Court court = new Court();
        repository().save(court);

        CourtIncreased courtIncreased = new CourtIncreased(court);
        courtIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(courtCanceled.get???()).ifPresent(court->{
            
            court // do something
            repository().save(court);

            CourtIncreased courtIncreased = new CourtIncreased(court);
            courtIncreased.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
