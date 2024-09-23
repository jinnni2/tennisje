package tennisje.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tennisje.config.kafka.KafkaProcessor;
import tennisje.domain.*;

@Service
public class ReservationListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private ReservationListRepository reservationListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCourtReserved_then_CREATE_1(
        @Payload CourtReserved courtReserved
    ) {
        try {
            if (!courtReserved.validate()) return;

            // view 객체 생성
            ReservationList reservationList = new ReservationList();
            // view 객체에 이벤트의 Value 를 set 함
            reservationList.setUserId(courtReserved.getUserId());
            reservationList.setReserveId(courtReserved.getId());
            reservationList.setCountName(courtReserved.getCourtName());
            reservationList.setStatus("R");
            // view 레파지 토리에 save
            reservationListRepository.save(reservationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenFullyBooked_then_UPDATE_1(
        @Payload FullyBooked fullyBooked
    ) {
        try {
            if (!fullyBooked.validate()) return;
            // view 객체 조회

            List<ReservationList> reservationListList = reservationListRepository.findByReserveId(
                fullyBooked.getReserveId()
            );
            for (ReservationList reservationList : reservationListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                reservationList.setStatus("F");
                // view 레파지 토리에 save
                reservationListRepository.save(reservationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCourtCanceled_then_UPDATE_2(
        @Payload CourtCanceled courtCanceled
    ) {
        try {
            if (!courtCanceled.validate()) return;
            // view 객체 조회

            List<ReservationList> reservationListList = reservationListRepository.findByReserveId(
                courtCanceled.getId()
            );
            for (ReservationList reservationList : reservationListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                reservationList.setStatus("C");
                // view 레파지 토리에 save
                reservationListRepository.save(reservationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
