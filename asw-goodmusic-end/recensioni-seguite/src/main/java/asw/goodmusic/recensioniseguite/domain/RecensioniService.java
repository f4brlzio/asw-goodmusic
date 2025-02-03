package asw.goodmusic.recensioniseguite.domain;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class RecensioniService {

    @Autowired
    private RecensioniBreviRepository recensioniBreviRepository;

    public RecensioneBreve saveNewRecensioneBreve(RecensioneBreve recensioneBreve) {
        RecensioneBreve newRecensioneBreve = this.recensioniBreviRepository.save(recensioneBreve);
        return newRecensioneBreve;
    }

    public Collection<RecensioneBreve> getAllRecensioniBreviByRecensore(String recensore) {
        return this.recensioniBreviRepository.findByRecensore(recensore);
    }

    public Collection<RecensioneBreve> getAllRecensioniBreviByArtista(String artista) {
        return this.recensioniBreviRepository.findByArtista(artista);
    }

    public Collection<RecensioneBreve> getAllRecensioniBreviByGenere(String genere) {
        return this.recensioniBreviRepository.findByGenere(genere);
    }
}
