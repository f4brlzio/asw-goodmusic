package asw.goodmusic.recensioniseguite.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RecensioniBreviRepository extends CrudRepository<RecensioneBreve, Long> {

    @Override
    public Collection<RecensioneBreve> findAll();

    public Collection<RecensioneBreve> findByRecensore(String recensore);

    public Collection<RecensioneBreve> findByArtista(String artista);

    public Collection<RecensioneBreve> findByGenere(String genere);
}
