package vn.vdbc.wad.configuration

import jakarta.persistence.EntityManager
import jakarta.persistence.metamodel.EntityType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import vn.vdbc.wad.model.Post

@Configuration
class WadRestRepositoryConfig implements RepositoryRestConfigurer {
    @Autowired
    @Qualifier("wadEntityManagerFactory")
    private EntityManager entityManager

    @Override
    void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        for (EntityType<?> entity : entityManager.getMetamodel().getEntities()) {
            Class<?> clazz = entity.getJavaType()
            config.exposeIdsFor(clazz)
        }

        config.getExposureConfiguration()
                .forDomainType(Post.class)
                .withItemExposure({ metadata, httpMethods -> httpMethods.disable(HttpMethod.GET) })
                .withCollectionExposure({ metadata, httpMethods -> httpMethods.disable(HttpMethod.GET) })
    }
}

