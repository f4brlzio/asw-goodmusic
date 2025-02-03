package asw.goodmusic.recensioniseguite.kafka;

import asw.goodmusic.connessioni.api.kafka.ConnessioneEvent;
import asw.goodmusic.recensioni.api.kafka.RecensioneEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class RecensioniSeguiteKafkaConsumerConfig {

    @Value("${asw.goodmusic.recensioni-seguite.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${asw.goodmusic.recensioni-seguite.group-id.connessioni}")
    private String groupIdConnessioni;

    @Value("${asw.goodmusic.recensioni-seguite.trusted-packages.connessioni}")
    private String trustedPackagesConnessioni;

    @Value("${asw.goodmusic.recensioni-seguite.group-id.recensioni}")
    private String groupIdRecensioni;

    @Value("${asw.goodmusic.recensioni-seguite.trusted-packages.recensioni}")
    private String trustedPackagesRecensioni;

    @Bean
    public ConsumerFactory<String, ConnessioneEvent> connessioneEventConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupIdConnessioni);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, this.trustedPackagesConnessioni);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean(name = "kafkaListenerConnessioneEventContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ConnessioneEvent>
    kafkaListenerConnessioneEventContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ConnessioneEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.connessioneEventConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, RecensioneEvent> recensioneEventConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupIdRecensioni);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, this.trustedPackagesRecensioni);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean(name = "kafkaListenerRecensioneEventContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, RecensioneEvent>
    kafkaListenerRecensioneEventContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RecensioneEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.recensioneEventConsumerFactory());
        return factory;
    }




}
