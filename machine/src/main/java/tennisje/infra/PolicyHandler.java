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
    MachineRepository machineRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CourtReserved'"
    )
    public void wheneverCourtReserved_StartMachine(
        @Payload CourtReserved courtReserved
    ) {
        CourtReserved event = courtReserved;
        System.out.println(
            "\n\n##### listener StartMachine : " + courtReserved + "\n\n"
        );

        // Sample Logic //
        Machine.startMachine(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CourtCanceled'"
    )
    public void wheneverCourtCanceled_StopMachine(
        @Payload CourtCanceled courtCanceled
    ) {
        CourtCanceled event = courtCanceled;
        System.out.println(
            "\n\n##### listener StopMachine : " + courtCanceled + "\n\n"
        );

        // Sample Logic //
        Machine.stopMachine(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
