package asw.goodmusic.connessioni.domain;

import asw.goodmusic.connessioni.api.kafka.ConnessioneCreatedEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioneDeletedEvent;
import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*; 

@Service
public class ConnessioniServiceImpl implements ConnessioniService {

	@Autowired
	private ConnessioniRepository connessioniRepository;

	@Autowired
	private ConnessioniProducerPort connessioniKafkaPublisher;

	/* Crea una nuova connessione, dati utente, seguito e ruolo. */ 
 	public Connessione createConnessione(String utente, String seguito, String ruolo) {
		Connessione connessione = new Connessione(utente, seguito, ruolo); 
		try {
			connessione = connessioniRepository.save(connessione);
			ConnessioneEvent connessioneCreatedEvent = new ConnessioneCreatedEvent(connessione.getId(), utente, seguito, ruolo);
			this.connessioniKafkaPublisher.publish(connessioneCreatedEvent);
			return connessione;
		} catch(Exception e) {
			/* si potrebbe verificare un'eccezione se è violato il vincolo di unicità della connessione */ 
			return null; 
		}
	}

	/* Trova una connessione, dato l'id. */ 
 	public Connessione getConnessione(Long id) {
		Connessione connessione = connessioniRepository.findById(id).orElse(null);
		return connessione;
	}

	/* Trova una connessione, dati utente, seguito e ruolo. */ 
	public Connessione getConnessione(String utente, String seguito, String ruolo) {
		Connessione connessione = connessioniRepository.findByUtenteAndSeguitoAndRuolo(utente, seguito, ruolo);
		return connessione;
	}

	/* Trova tutte le connessioni. */ 
 	public Collection<Connessione> getConnessioni() {
		Collection<Connessione> connessioni = connessioniRepository.findAll();
		return connessioni;
	}

	/* Trova tutte le connessioni di un utente. */ 
	public Collection<Connessione> getConnessioniByUtente(String utente) {
		Collection<Connessione> connessioni = connessioniRepository.findByUtente(utente);
		return connessioni;
	}

	/* Trova tutte le connessioni con un certo ruolo. */ 
	public Collection<Connessione> getConnessioniByRuolo(String ruolo) {
		Collection<Connessione> connessioni = connessioniRepository.findByRuolo(ruolo);
		return connessioni;
	}

	/* Trova tutte le connessioni di un utente con un certo ruolo. */ 
	public Collection<Connessione> getConnessioniByUtenteAndRuolo(String utente, String ruolo) {
		Collection<Connessione> connessioni = connessioniRepository.findByUtenteAndRuolo(utente, ruolo);
		return connessioni;
	}

	/* Cancella una connessione, dati utente, seguito e ruolo. */ 
 	public Connessione deleteConnessione(String utente, String seguito, String ruolo) {
		Connessione connessione = getConnessione(utente, seguito, ruolo); 
		if (connessione!=null) {
			connessioniRepository.delete(connessione);
			ConnessioneEvent connessioneDeletedEvent = new ConnessioneDeletedEvent(connessione.getId(), utente, seguito, ruolo);
			this.connessioniKafkaPublisher.publish(connessioneDeletedEvent);
		}
		return connessione; 
	}

}
