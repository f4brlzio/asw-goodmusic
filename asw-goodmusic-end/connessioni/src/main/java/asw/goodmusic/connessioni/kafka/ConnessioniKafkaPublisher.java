package asw.goodmusic.connessioni.kafka;

import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioniEventChannel;
import asw.goodmusic.connessioni.domain.ConnessioniProducerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ConnessioniKafkaPublisher implements ConnessioniProducerPort {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    private String channel = ConnessioniEventChannel.connessioniTopic;

    @Autowired
    private KafkaTemplate<String, ConnessioneEvent> kafkaTemplate;

    @Override
    public void publish(ConnessioneEvent connessioneEvent) {
        logger.info("PUBLISHING A NEW CONNESSIONE EVENT ON " + this.channel);
        this.kafkaTemplate.send(channel, connessioneEvent);
    }
}
