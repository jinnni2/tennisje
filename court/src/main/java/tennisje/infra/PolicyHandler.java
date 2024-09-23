package tennisje.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tennisje.config.kafka.KafkaProcessor;
import tennisje.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CourtRepository courtRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MachineStarted'"
    )
    public void wheneverMachineStarted_DecreaseCourt(
        @Payload MachineStarted machineStarted
    ) {
        MachineStarted event = machineStarted;
        System.out.println(
            "\n\n##### listener DecreaseCourt : " + machineStarted + "\n\n"
        );

        // Sample Logic //
        Court.decreaseCourt(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='MachineStopped'"
    )
    public void wheneverMachineStopped_IncreaseCourt(
        @Payload MachineStopped machineStopped
    ) {
        MachineStopped event = machineStopped;
        System.out.println(
            "\n\n##### listener IncreaseCourt : " + machineStopped + "\n\n"
        );

        // Sample Logic //
        Court.increaseCourt(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
