package asw.goodmusic.connessioni.domain;

import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;

public interface ConnessioniProducerPort {

    public void publish(ConnessioneEvent connessioneEvent);
}
