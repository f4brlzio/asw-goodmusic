package asw.goodmusic.recensioniseguite.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/* Recensione (in formato breve) di un album scritta da un recensore. */ 
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class RecensioneBreve implements Comparable<RecensioneBreve> {

	/* id della recensione */
	@EqualsAndHashCode.Include
	@Id
	private Long id; 
	/* chi ha scritto la recensione */ 
	private String recensore; 
	/* album oggetto della recensione */ 
	private String album; 
	/* artista autore dell'album */ 
	private String artista; 
	/* genere dell'album */ 
	private String genere; 
	/* sunto del testo della recensione */ 
	private String sunto; 

	@Override
	public int compareTo(RecensioneBreve other) {
		return this.id.compareTo(other.id); 
	}
	
}
