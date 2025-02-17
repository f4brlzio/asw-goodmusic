package asw.goodmusic.recensioniseguite.rest;

import asw.goodmusic.recensioniseguite.domain.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant; 
import java.time.Duration; 

import java.util.*; 

import java.util.logging.Logger; 

@RestController
public class RecensioniSeguiteController {

    private final Logger logger = Logger.getLogger(this.getClass().toString());

	@Autowired
	@Qualifier("dbQueryService")
	private RecensioniSeguiteService recensioniSeguiteService;

	/* Trova le recensioni degli utenti seguiti da utente effettuando delle chiamate REST a recensioni e connessioni */
	@GetMapping("/recensioni_seguite/{utente}")
	public Collection<RecensioneBreve> getRecensioniSeguiteRestCall(@PathVariable String utente) {
		Instant start = Instant.now();
		logger.info("REST CALL: getRecensioniSeguite " + utente); 
		Collection<RecensioneBreve> recensioni = recensioniSeguiteService.getRecensioniSeguite(utente); 
		Duration duration = Duration.between(start, Instant.now()); 
		logger.info("getRecensioniSeguite " + utente 
		            + " --> trovate " + recensioni.size() + " recensioni in " + duration.toMillis() + " ms" 
					+ " --> " + recensioni);
		return recensioni; 
	}

	/* Trova le recensioni degli utenti seguiti da utente interrogando la propria base di dati */
	@GetMapping("/recensioniseguite/{utente}")
	public Collection<RecensioneBreve> getRecensioniSeguite(@PathVariable String utente) {
		Instant start = Instant.now();
		logger.info("getRecensioniSeguite " + utente);
		Collection<RecensioneBreve> recensioni = recensioniSeguiteService.getRecensioniSeguite(utente);
		Duration duration = Duration.between(start, Instant.now());
		logger.info("getRecensioniSeguite " + utente
				+ " --> trovate " + recensioni.size() + " recensioni in " + duration.toMillis() + " ms"
				+ " --> " + recensioni);
		return recensioni;
	}
	
}
