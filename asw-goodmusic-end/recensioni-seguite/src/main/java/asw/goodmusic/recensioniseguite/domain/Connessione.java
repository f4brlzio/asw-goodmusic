package asw.goodmusic.recensioniseguite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/* Connessione tra un utente e un seguito (con un ruolo). */  
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Connessione {

	/* id della connessione */
	@Id
	private Long id; 
	/* utente che segue */ 
	private String utente; 
	/* chi o cosa è seguito (un artista o uno che scrive recensioni oppure un genere musicale) */ 
	private String seguito; 
	/* ruolo del seguito: può essere ARTISTA oppure RECENSORE oppure GENERE */ 
	private String ruolo; 
	
}
