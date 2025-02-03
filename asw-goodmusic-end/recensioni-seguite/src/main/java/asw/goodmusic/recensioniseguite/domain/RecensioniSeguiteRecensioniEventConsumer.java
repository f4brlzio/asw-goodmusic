package asw.goodmusic.recensioniseguite.domain;

import asw.goodmusic.recensioni.api.kafka.RecensioneCreatedEvent;
import asw.goodmusic.recensioni.api.kafka.RecensioneEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RecensioniSeguiteRecensioniEventConsumer {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Autowired
    private RecensioniService recensioniService;

    public void onRecensioneEvent(RecensioneEvent recensioneEvent) {
        if(recensioneEvent instanceof RecensioneCreatedEvent recensioneCreatedEvent) {
            logger.info("RECENSIONE CREATED-EVENT LISTENED: " + recensioneCreatedEvent.getId()
                    + recensioneCreatedEvent.getRecensore() + recensioneCreatedEvent.getAlbum()
                    + recensioneCreatedEvent.getArtista() + recensioneCreatedEvent.getGenere()
                    + recensioneCreatedEvent.getSunto());

            this.recensioniService.saveNewRecensioneBreve(new RecensioneBreve(recensioneCreatedEvent.getId(),
                    recensioneCreatedEvent.getRecensore(), recensioneCreatedEvent.getAlbum(),
                    recensioneCreatedEvent.getArtista(), recensioneCreatedEvent.getGenere(),
                    recensioneCreatedEvent.getSunto()));
        }
        else {
            logger.info("UNKNOWN RECENSIONE CREATED-EVENT");
        }
    }
}
