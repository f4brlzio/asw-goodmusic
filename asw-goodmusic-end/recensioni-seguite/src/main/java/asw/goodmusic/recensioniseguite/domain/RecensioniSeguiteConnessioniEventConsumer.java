package asw.goodmusic.recensioniseguite.domain;


import asw.goodmusic.connessioni.api.kafka.ConnessioneCreatedEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioneDeletedEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RecensioniSeguiteConnessioniEventConsumer {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Autowired
    private ConnessioniService connessioniService;

    public void onConnessioneEvent(ConnessioneEvent connessioneEvent) {
        if(connessioneEvent instanceof ConnessioneCreatedEvent connessioneCreatedEvent) {
            logger.info("CONNESSIONE CREATED-EVENT LISTENED: " + connessioneCreatedEvent.getUtente()
                    + connessioneCreatedEvent.getSeguito() + connessioneCreatedEvent.getRuolo());
            Connessione newConnessione = new Connessione(connessioneCreatedEvent.getId(), connessioneCreatedEvent.getUtente(),
                    connessioneCreatedEvent.getSeguito(), connessioneCreatedEvent.getRuolo());
            Connessione connessione = this.connessioniService.saveNewConnessione(newConnessione);
            logger.info("NEW CONNESSIONE: " + connessione.getUtente()
                    + connessione.getSeguito() + connessione.getRuolo());
        }
        else if(connessioneEvent instanceof ConnessioneDeletedEvent connessioneDeletedEvent) {
            logger.info("CONNESSIONE CREATED-EVENT LISTENED: " + connessioneDeletedEvent.getUtente()
                    + connessioneDeletedEvent.getSeguito() + connessioneDeletedEvent.getRuolo() );
            boolean deleted = this.connessioniService.deleteConnessione(connessioneDeletedEvent.getId());
            logger.info(deleted ? "CONNESSIONE DELETED" : "CONNESSIONE NOT DELETED --> why?");
        }
        else {
            logger.info("UNKNOWN CONNESSIONE CREATED-EVENT");
        }
    }
}
