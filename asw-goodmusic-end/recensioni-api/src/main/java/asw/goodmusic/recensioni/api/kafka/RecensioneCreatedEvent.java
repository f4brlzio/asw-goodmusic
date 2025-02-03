package asw.goodmusic.recensioni.api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RecensioneCreatedEvent implements RecensioneEvent{

    private Long id;

    private String recensore;

    private String album;

    private String artista;

    private String genere;

    private String sunto;


}
