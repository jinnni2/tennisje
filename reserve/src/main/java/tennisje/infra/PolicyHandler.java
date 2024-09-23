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
    ReserveRepository reserveRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='FullyBooked'"
    )
    public void wheneverFullyBooked_UpdateStatus(
        @Payload FullyBooked fullyBooked
    ) {
        FullyBooked event = fullyBooked;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + fullyBooked + "\n\n"
        );

        // Sample Logic //
        Reserve.updateStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
