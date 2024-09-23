package tennisje.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import tennisje.MachineApplication;
import tennisje.domain.MachineCleaned;

@Entity
@Table(name = "Machine_table")
@Data
//<<< DDD / Aggregate Root
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long courtId;

    private String status;

    private Long reserveId;

    @PostUpdate
    public void onPostUpdate() {
        MachineCleaned machineCleaned = new MachineCleaned(this);
        machineCleaned.publishAfterCommit();
    }

    public static MachineRepository repository() {
        MachineRepository machineRepository = MachineApplication.applicationContext.getBean(
            MachineRepository.class
        );
        return machineRepository;
    }

    //<<< Clean Arch / Port Method
    public static void cleanMachine(CourtReserved courtReserved) {
        //implement business logic here:
        Machine machine = new Machine();
        machine.setCourtId(courtReserved.getCourtId());
        machine.setReserveId(courtReserved.getId());
        machine.setStatus("Clean Complete");
        repository().save(machine);

        MachineCleaned machineCleaned = new MachineCleaned(machine);
        machineCleaned.publishAfterCommit();
        /** Example 1:  new item 
        Machine machine = new Machine();
        repository().save(machine);

        MachineCleaned machineCleaned = new MachineCleaned(machine);
        machineCleaned.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(courtReserved.get???()).ifPresent(machine->{
            
            machine // do something
            repository().save(machine);

            MachineCleaned machineCleaned = new MachineCleaned(machine);
            machineCleaned.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
