package asw.goodmusic.recensioniseguite.kafka;
import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioniEventChannel;
import asw.goodmusic.recensioni.api.kafka.RecensioneEvent;
import asw.goodmusic.recensioni.api.kafka.RecensioniEventChannel;
import asw.goodmusic.recensioniseguite.domain.RecensioniSeguiteConnessioniEventConsumer;
import asw.goodmusic.recensioniseguite.domain.RecensioniSeguiteRecensioniEventConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RecensioniSeguiteKafkaListener {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Autowired
    private RecensioniSeguiteConnessioniEventConsumer connessioniEventConsumer;

    @Autowired
    private RecensioniSeguiteRecensioniEventConsumer recensioniEventConsumer;

    @Value("${asw.goodmusic.recensioni-seguite.group-id.connessioni}")
    private String groupIdConnessioni;

    @Value("${asw.goodmusic.recensioni-seguite.group-id.recensioni}")
    private String groupIdRecensioni;

    private final static String connessioniEventChannel = ConnessioniEventChannel.connessioniTopic;

    private final static String recensioniEventChannel = RecensioniEventChannel.recensioniTopic;

    @KafkaListener(topics = connessioniEventChannel,
            containerFactory = "kafkaListenerConnessioneEventContainerFactory")
    public void listenConnessioneEvent(ConsumerRecord<String, ConnessioneEvent> consumerRecord) {
        logger.info("CONNESSIONE EVENT LISTENED. READY TO BE CONSUMED BY " + this.groupIdConnessioni +
                " ON " + connessioniEventChannel);
        this.connessioniEventConsumer.onConnessioneEvent(consumerRecord.value());
    }

    @KafkaListener(topics = recensioniEventChannel,
            containerFactory = "kafkaListenerRecensioneEventContainerFactory")
    public void listenRecensioneEvent(ConsumerRecord<String, RecensioneEvent> consumerRecord) {
        logger.info("RECENSIONE EVENT LISTENED. READY TO BE CONSUMED BY " + this.groupIdRecensioni +
                " ON " + connessioniEventChannel);
        this.recensioniEventConsumer.onRecensioneEvent(consumerRecord.value());
    }
}
