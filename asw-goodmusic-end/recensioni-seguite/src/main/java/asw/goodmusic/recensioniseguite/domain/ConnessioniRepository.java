package asw.goodmusic.recensioniseguite.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ConnessioniRepository extends CrudRepository<Connessione, Long> {

    @Override
    public Collection<Connessione> findAll();

    public Collection<Connessione> findByUtente(String utente);
}
