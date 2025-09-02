package vn.vdbc.core.configuration;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(PagedModel.class, new CustomPagedModelSerializer());
        customModule.addSerializer(CollectionModel.class, new CustomCollectionModelSerializer());
        mapper.registerModule(customModule);
//        mapper.addMixIn(RepresentationModel.class, RepresentationModelMixin.class);
//        mapper.addMixIn(EntityModel.class, RepresentationModelMixin.class);

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @JsonIgnoreProperties({"_links"})
    public static class RepresentationModelMixin {
    }

    public static class CustomPagedModelSerializer extends JsonSerializer<PagedModel> {
        @Override
        public void serialize(PagedModel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            Collection<?> content = value.getContent();
            gen.writeObjectField("value", content);
            value.getMetadata();
            gen.writeObjectField("@odata.count", value.getMetadata().getTotalElements());
            gen.writeEndObject();
        }


    }

    public static class CustomCollectionModelSerializer extends JsonSerializer<CollectionModel> {
        @Override
        public void serialize(CollectionModel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();

            Collection<?> content = value.getContent();
            gen.writeObjectField("data", content);

            value.getLinks();
            if (!value.getLinks().isEmpty()) {
                gen.writeObjectField("_links", convertLinksToMap(value.getLinks()));
            }

            gen.writeEndObject();
        }

        private Map<String, Object> convertLinksToMap(Links links) {
            Map<String, Object> linkMap = new HashMap<>();
            links.forEach(link -> {
                Map<String, String> linkObj = new HashMap<>();
                linkObj.put("href", link.getHref());
                linkMap.put(link.getRel().value(), linkObj);
            });
            return linkMap;
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

