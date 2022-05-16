package id.ac.ui.cs.advprog.minjemin.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer{
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("auth/login");
    }
}
