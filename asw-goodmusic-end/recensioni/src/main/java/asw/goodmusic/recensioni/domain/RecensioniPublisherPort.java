package asw.goodmusic.recensioni.domain;

import asw.goodmusic.recensioni.api.kafka.RecensioneEvent;

public interface RecensioniPublisherPort {

    public void publish(RecensioneEvent recensioneEvent);
}
