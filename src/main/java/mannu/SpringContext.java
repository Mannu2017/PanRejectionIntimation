package mannu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class SpringContext {
	
	@Bean(name = "clintMail")
    public ClintMail createMainFrame() {
        return new ClintMail();
    }
 
 @Bean
    public static PropertySourcesPlaceholderConfigurer setUp() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
}
