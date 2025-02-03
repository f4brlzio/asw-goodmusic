package asw.goodmusic.recensioni.kafka;

import asw.goodmusic.recensioni.api.kafka.RecensioneEvent;
import asw.goodmusic.recensioni.api.kafka.RecensioniEventChannel;
import asw.goodmusic.recensioni.domain.RecensioniPublisherPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RecensioniKafkaPublisher implements RecensioniPublisherPort {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    private final String channel = RecensioniEventChannel.recensioniTopic;

    @Autowired
    private KafkaTemplate<String, RecensioneEvent> kafkaTemplate;

    @Override
    public void publish(RecensioneEvent recensioneEvent) {
        logger.info("PUBLISHING A NEW RECENSIONE EVENT ON " + this.channel);
        this.kafkaTemplate.send(channel, recensioneEvent);
    }
}
