package asw.goodmusic.recensioniseguite.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.*;

@Service(value = "restCallService")
public class RecensioniSeguiteServiceRestBasedImpl implements RecensioniSeguiteService {

	private final Logger logger = Logger.getLogger(this.getClass().toString());

	@Autowired 
	private ConnessioniClientPort connessioniClient;

	@Autowired 
	private RecensioniClientPort recensioniClient;


	/* Trova le recensioni seguite da un utente, 
	 * ovvero le recensioni degli album degli artisti, dei recensori e dei generi musicali seguiti da quell'utente. */ 
	@Override
	public Collection<RecensioneBreve> getRecensioniSeguite(String utente) {
		logger.info("getRecensioniSeguite in RecensioniSeguiteServiceRestBasedImpl: init");
		Collection<RecensioneBreve> recensioniSeguite = new TreeSet<>(); 
		
		Collection<Connessione> connessioni = connessioniClient.getConnessioniByUtente(utente);

		/* ok, ma purtroppo chiama il metodo getRecensioniByGeneri che non è definito né implementato */
		logger.info("getRecensioniSeguite in RecensioniSeguiteServiceRestBasedImpl: calls generiSeguiti");
		Collection<String> generiSeguiti =
				connessioni
						.stream()
						.filter(c -> c.getRuolo().equals("GENERE"))
						.map(c -> c.getSeguito())
						.collect(Collectors.toSet());
		if (generiSeguiti.size()>0) {
			Collection<RecensioneBreve> recensioniDiGeneri = recensioniClient.getRecensioniByGeneri(generiSeguiti);
			recensioniSeguite.addAll(recensioniDiGeneri);
		}

		Collection<String> artistiSeguiti = 
			connessioni
				.stream()
				.filter(c -> c.getRuolo().equals("ARTISTA"))
				.map(c -> c.getSeguito())
				.collect(Collectors.toSet()); 
		if (artistiSeguiti.size()>0) {
			logger.info("getRecensioniSeguite in RecensioniSeguiteServiceRestBasedImpl: artistiSeguiti");
			Collection<RecensioneBreve> recensioniDiArtisti = recensioniClient.getRecensioniByArtisti(artistiSeguiti);
			recensioniSeguite.addAll(recensioniDiArtisti); 
		}
		
		Collection<String> recensoriSeguiti = 
			connessioni
				.stream()
				.filter(c -> c.getRuolo().equals("RECENSORE"))
				.map(c -> c.getSeguito())
				.collect(Collectors.toSet()); 
		if (recensoriSeguiti.size()>0) {
			logger.info("getRecensioniSeguite in RecensioniSeguiteServiceRestBasedImpl: recensoriSeguiti");
			Collection<RecensioneBreve> recensioniDiRecensori = recensioniClient.getRecensioniByRecensori(recensoriSeguiti);
			recensioniSeguite.addAll(recensioniDiRecensori); 
		}

		return recensioniSeguite; 
	}

}
