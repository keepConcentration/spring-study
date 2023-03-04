package tobyspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> autoConfigs = new ArrayList<>();

        // META-INF/spring/패키지명 포함한 파일명.imports 파일을 읽음
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);
        return autoConfigs.stream().toArray(String[]::new);


        // Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
        // return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);


        /*return new String[] {
                "tobyspring.config.autoconfig.DispatcherServletConfig",
                "tobyspring.config.autoconfig.TomcatWebServerConfig"
        };*/
    }

}
