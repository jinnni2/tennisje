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
        condition = "headers['type']=='MachineCleaned'"
    )
    public void wheneverMachineCleaned_DecreaseCourt(
        @Payload MachineCleaned machineCleaned
    ) {
        MachineCleaned event = machineCleaned;
        System.out.println(
            "\n\n##### listener DecreaseCourt : " + machineCleaned + "\n\n"
        );

        // Sample Logic //
        Court.decreaseCourt(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CourtCanceled'"
    )
    public void wheneverCourtCanceled_IncreaseCourt(
        @Payload CourtCanceled courtCanceled
    ) {
        CourtCanceled event = courtCanceled;
        System.out.println(
            "\n\n##### listener IncreaseCourt : " + courtCanceled + "\n\n"
        );

        // Sample Logic //
        Court.increaseCourt(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
