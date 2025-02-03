package asw.goodmusic.recensioniseguite.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service(value = "dbQueryService")
@Transactional
public class RecensioniSeguiteServiceBasedImpl implements RecensioniSeguiteService {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

    @Autowired
    private RecensioniService recensioniService;

    @Autowired
    private ConnessioniService connessioniService;

    @Override
    public Collection<RecensioneBreve> getRecensioniSeguite(String utente) {
        logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: init");
        Collection<RecensioneBreve> recensioniSeguite = new TreeSet<>();

        Collection<Connessione> connessioniByUtente = this.connessioniService.getAllConnessioniByUtente(utente);

        logger.info("# connessione di " + utente + ": " + connessioniByUtente.size());

        logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: query the Db generiSeguiti");
        Collection<Connessione> connessioniGeneriByUtente = connessioniByUtente
                .stream()
                .filter(connessione -> connessione.getRuolo().equals("GENERE"))
                .collect(Collectors.toSet());

        if(!connessioniGeneriByUtente.isEmpty()) {
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + + connessioniGeneriByUtente.size() +
                    " connessioni for GENERE");
            Collection<RecensioneBreve> recensioniBreviGeneriSeguiti = connessioniGeneriByUtente
                    .stream()
                    //.filter(connessione -> connessione.getRuolo().equals("GENERE")) con connessioniByUtente
                    .map(connessione -> this.recensioniService.getAllRecensioniBreviByGenere(connessione.getSeguito()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + recensioniBreviGeneriSeguiti.size() +
                    " for " + utente + " with GENERE as role");
            recensioniSeguite.addAll(recensioniBreviGeneriSeguiti);
        }


        logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: query the Db artistiSeguiti");
        Collection<Connessione> connessioniArtistiByUtente = connessioniByUtente
                .stream()
                .filter(connessione -> connessione.getRuolo().equals("ARTISTA"))
                .collect(Collectors.toSet());

        if(!connessioniArtistiByUtente.isEmpty()) {
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + + connessioniArtistiByUtente.size() +
                    " connessioni for ARTISTA");
            Collection<RecensioneBreve> recensioniBreviArtistiSeguiti = connessioniArtistiByUtente
                    .stream()
                    .map(connessione -> this.recensioniService.getAllRecensioniBreviByArtista(connessione.getSeguito()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + recensioniBreviArtistiSeguiti.size() +
                    " for " + utente + " with ARTISTA as role");
            recensioniSeguite.addAll(recensioniBreviArtistiSeguiti);
        }

        logger.info("*getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: query the Db recensoriSeguiti");
        Collection<Connessione> connessioniRecensoriByUtente = connessioniByUtente
                .stream()
                .filter(connessione -> connessione.getRuolo().equals("RECENSORE"))
                .collect(Collectors.toSet());

        if(!connessioniRecensoriByUtente.isEmpty()) {
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + + connessioniRecensoriByUtente.size() +
                    " connessioni for RECENSORE");
            Collection<RecensioneBreve> recensioniBreviRecensoriSeguiti = connessioniRecensoriByUtente
                    .stream()
                    .map(connessione -> this.recensioniService.getAllRecensioniBreviByRecensore(connessione.getSeguito()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: we have " + recensioniBreviRecensoriSeguiti.size() +
                    " for " + utente + " with RECENSORE as role");
            recensioniSeguite.addAll(recensioniBreviRecensoriSeguiti);
        }

        logger.info("getRecensioniSeguite in RecensioniSeguiteServiceBasedImpl: for " + utente +
                " we have " + recensioniSeguite.size() + " recensioni");
        return recensioniSeguite;
    }
}
