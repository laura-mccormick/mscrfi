package Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("resources/application.properties")
public class PropertiesConfig {

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.sid}")
    private String jdbcSid;

    @Value("${jdbc.username}")
    private String  jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${mail.username}")
    private String javaMailUsername;

    @Value("${mail.password}")
    private String javaMailPassword;

    @Value("${mail.auth}")
    private String isMailAuth;

    @Value("${mail.starttls.enable}")
    private String isMailStarttlsEnable;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.access.port}")
    private int mailPort;

    @Value("{mail.store.protocol}")
    private String storeProtocol;

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
