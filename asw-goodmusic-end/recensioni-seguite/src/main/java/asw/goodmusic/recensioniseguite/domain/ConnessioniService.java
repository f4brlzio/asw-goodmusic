package asw.goodmusic.recensioniseguite.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class ConnessioniService {

    @Autowired
    private ConnessioniRepository connessioniRepository;


    public Connessione saveNewConnessione(Connessione connessione) {
        Connessione newConnessione = this.connessioniRepository.save(connessione);
        return newConnessione;
    }


    public boolean deleteConnessione(Long id) {
        this.connessioniRepository.deleteById(id);
        return this.connessioniRepository.findAll().stream().allMatch(connessione -> !connessione.getId().equals(id));
    }

    public Collection<Connessione> getAllConnessioniByUtente(String utente) {
        return this.connessioniRepository.findByUtente(utente);
    }
}
