package asw.goodmusic.connessioni.api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ConnessioneCreatedEvent implements ConnessioneEvent{

    private Long id;

    private String utente;

    private String seguito;

    private String ruolo;
}
