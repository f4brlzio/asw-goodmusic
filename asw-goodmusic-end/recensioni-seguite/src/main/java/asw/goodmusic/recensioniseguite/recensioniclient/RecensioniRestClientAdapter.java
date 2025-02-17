package asw.goodmusic.recensioniseguite.recensioniclient;

import asw.goodmusic.recensioniseguite.domain.*; 

import asw.goodmusic.recensioni.api.rest.*; 

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.*;

@Service 
@Primary 
public class RecensioniRestClientAdapter implements RecensioniClientPort {

	private final Logger logger = Logger.getLogger(this.getClass().toString());

	@Autowired 
	@Qualifier("loadBalancedWebClient")
    private WebClient loadBalancedWebClient;

	@Override
	public Collection<RecensioneBreve> getRecensioniByArtisti(Collection<String> artisti) {
		logger.info("RecensioniRestClientAdapter getRecensioniByRecensori in adapter: calls artistiSeguiti");
		Collection<RecensioneBreve> recensioni = null; 
        Flux<RecensioneBreveResponse> response = loadBalancedWebClient
                .get()
				.uri("http://recensioni/cercarecensioni/artisti/{artisti}", toString(artisti))
                .retrieve()
                .bodyToFlux(RecensioneBreveResponse.class);
        try {
            recensioni = toRecensioni(response.collectList().block());
        } catch (WebClientException e) {
            e.printStackTrace();
        }
		return recensioni; 
	}	

	@Override
	public Collection<RecensioneBreve> getRecensioniByRecensori(Collection<String> recensori) {
		logger.info("RecensioniRestClientAdapter getRecensioniByRecensori in adapter: calls recensoriSeguiti");
		Collection<RecensioneBreve> recensioni = null; 
        Flux<RecensioneBreveResponse> response = loadBalancedWebClient
                .get()
				.uri("http://recensioni/cercarecensioni/recensori/{recensori}", toString(recensori))
                .retrieve()
                .bodyToFlux(RecensioneBreveResponse.class);
        try {
            recensioni = toRecensioni(response.collectList().block());
        } catch (WebClientException e) {
            e.printStackTrace();
        }
		return recensioni; 
	}

	@Override
	public Collection<RecensioneBreve> getRecensioniByGeneri(Collection<String> generiSeguiti) {
		logger.info("RecensioniRestClientAdapter getRecensioniByGeneri in adapter: calls generiSeguiti");
		Collection<RecensioneBreve> recensioni = null;
		Flux<RecensioneBreveResponse> response = loadBalancedWebClient
				.get()
				.uri("http://recensioni/cercarecensioni/generi/{generi}", toString(generiSeguiti))
				.retrieve()
				.bodyToFlux(RecensioneBreveResponse.class);
		try {
			recensioni = toRecensioni(response.collectList().block());
		} catch (WebClientException e) {
			e.printStackTrace();
		}
		return recensioni;
	}

	private RecensioneBreve toRecensione(RecensioneBreveResponse response) {
		return new RecensioneBreve(response.getId(), response.getRecensore(), response.getAlbum(), 
				response.getArtista(), response.getGenere(), response.getSunto());
	}

	private Collection<RecensioneBreve> toRecensioni(Collection<RecensioneBreveResponse> response) {
		Collection<RecensioneBreve> recensioni = 
			response
				.stream()
				.map(r -> toRecensione(r))
				.collect(Collectors.toList());
		return recensioni; 
	}

	private static String toString(Collection<String> c) {
		String result = 
			c.stream()
				.map(n -> String.valueOf(n))
				.collect(Collectors.joining(",", "", ""));
		return result; 
	}

}
