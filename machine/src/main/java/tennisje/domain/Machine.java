package tennisje.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import tennisje.MachineApplication;
import tennisje.domain.MachineStarted;
import tennisje.domain.MachineStopped;

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

    @PostUpdate
    public void onPostUpdate() {
        MachineStarted machineStarted = new MachineStarted(this);
        machineStarted.publishAfterCommit();

        MachineStopped machineStopped = new MachineStopped(this);
        machineStopped.publishAfterCommit();
    }

    public static MachineRepository repository() {
        MachineRepository machineRepository = MachineApplication.applicationContext.getBean(
            MachineRepository.class
        );
        return machineRepository;
    }

    //<<< Clean Arch / Port Method
    public static void startMachine(CourtReserved courtReserved) {
        //implement business logic here:

        /** Example 1:  new item 
        Machine machine = new Machine();
        repository().save(machine);

        MachineStarted machineStarted = new MachineStarted(machine);
        machineStarted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(courtReserved.get???()).ifPresent(machine->{
            
            machine // do something
            repository().save(machine);

            MachineStarted machineStarted = new MachineStarted(machine);
            machineStarted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void stopMachine(CourtCanceled courtCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Machine machine = new Machine();
        repository().save(machine);

        MachineStopped machineStopped = new MachineStopped(machine);
        machineStopped.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(courtCanceled.get???()).ifPresent(machine->{
            
            machine // do something
            repository().save(machine);

            MachineStopped machineStopped = new MachineStopped(machine);
            machineStopped.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
