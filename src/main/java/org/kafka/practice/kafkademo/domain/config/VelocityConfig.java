package org.kafka.practice.kafkademo.domain.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class VelocityConfig {

    public static final String CLASSPATH_RESOURCE_LOADER_CLASS = "classpath.resource.loader.class";
    public static final String VELOCITY_PAGES_PATH = "/templates/";
    public static final String OUTPUT_ENCODING = "output.encoding";
    public static final String RESOURCE_LOADER = "resource.loader";
    public static final String INPUT_ENCODING = "input.encoding";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String CLASSPATH = "classpath";
    public static final String PAGES_TYPE = ".html";

    @Bean
    public VelocityEngine velocityEngine() {
        final var props = new Properties();
        props.setProperty(CLASSPATH_RESOURCE_LOADER_CLASS, ClasspathResourceLoader.class.getName());
        props.setProperty(OUTPUT_ENCODING, ENCODING_UTF8);
        props.setProperty(INPUT_ENCODING, ENCODING_UTF8);
        props.setProperty(RESOURCE_LOADER, CLASSPATH);

        final var engine = new VelocityEngine();
        engine.init(props);
        return engine;
    }

    @Bean
    public ViewResolver viewResolver(final VelocityEngine velocityEngine) {
        return (viewName, locale) -> new View() {

            @NonNull
            @Override
            public String getContentType() {
                return "text/html";
            }

            @Override
            public void render(@NonNull final Map<String, ?> model,
                               @NonNull final HttpServletRequest request,
                               @NonNull final HttpServletResponse response) throws Exception {
                final var template = velocityEngine.getTemplate(
                        VELOCITY_PAGES_PATH + viewName + PAGES_TYPE, ENCODING_UTF8);
                final var writer = new StringWriter();
                template.merge(new VelocityContext(new HashMap<>(model)), writer);
                response.getWriter().write(writer.toString());
            }

        };
    }

}
