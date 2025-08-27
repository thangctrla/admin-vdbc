//package vn.vdbc.wad.configuration;
//
//import jakarta.persistence.Entity;
//import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//
//import java.util.Objects;
//
//@Configuration
//public class WadRestRepositoryConfig implements RepositoryRestConfigurer {
//
//    @Override
//    public void configureRepositoryRestConfiguration(
//            RepositoryRestConfiguration config,
//            CorsRegistry cors) {
//        config.setBasePath("/api/wad");
//        config.exposeIdsFor(getAllEntityClasses());
//    }
//    private Class<?>[] getAllEntityClasses() {
//        ClassPathScanningCandidateComponentProvider scanner =
//                new ClassPathScanningCandidateComponentProvider(false);
//
//        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
//
//        String basePackage = "vn.vdbc.wad.model";
//
//        return scanner.findCandidateComponents(basePackage)
//                .stream()
//                .map(beanDefinition -> {
//                    try {
//                        return Class.forName(beanDefinition.getBeanClassName());
//                    } catch (ClassNotFoundException e) {
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull)
//                .toArray(Class<?>[]::new);
//    }
//}
