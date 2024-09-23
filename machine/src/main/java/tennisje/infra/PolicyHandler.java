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
    public void wheneverCourtReserved_CleanMachine(
        @Payload CourtReserved courtReserved
    ) {
        CourtReserved event = courtReserved;
        System.out.println(
            "\n\n##### listener CleanMachine : " + courtReserved + "\n\n"
        );

        // Sample Logic //
        Machine.cleanMachine(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
