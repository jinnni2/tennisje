package tennisje.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import tennisje.ReserveApplication;
import tennisje.domain.CourtCanceled;
import tennisje.domain.CourtReserved;

@Entity
@Table(name = "Reserve_table")
@Data
//<<< DDD / Aggregate Root
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long courtId;

    private String courtName;

    private String status;

    @PostPersist
    public void onPostPersist() {
        CourtReserved courtReserved = new CourtReserved(this);
        courtReserved.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        CourtCanceled courtCanceled = new CourtCanceled(this);
        courtCanceled.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {}

    public static ReserveRepository repository() {
        ReserveRepository reserveRepository = ReserveApplication.applicationContext.getBean(
            ReserveRepository.class
        );
        return reserveRepository;
    }

    public void reserveCourt() {
        //implement business logic here:

        CourtReserved courtReserved = new CourtReserved(this);
        courtReserved.publishAfterCommit();
    }

    public void cancelCourt() {
        //implement business logic here:

        CourtCanceled courtCanceled = new CourtCanceled(this);
        courtCanceled.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public static void updateStatus(FullyBooked fullyBooked) {
        //implement business logic here:

        /** Example 1:  new item 
        Reserve reserve = new Reserve();
        repository().save(reserve);

        */

        /** Example 2:  finding and process
        
        repository().findById(fullyBooked.get???()).ifPresent(reserve->{
            
            reserve // do something
            repository().save(reserve);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
