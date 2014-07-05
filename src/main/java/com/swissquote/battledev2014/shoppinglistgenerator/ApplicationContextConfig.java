package com.swissquote.battledev2014.shoppinglistgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Configuration
@ComponentScan(basePackages = "com.swissquote.battledev2014.shoppinglistgenerator")
@PropertySource({"classpath:/qtysyn.properties", "classpath:/qtyunit.properties"})
@EnableElasticsearchRepositories(basePackages = "com.swissquote.battledev2014.shoppinglistgenerator.repository")
public class ApplicationContextConfig {

    @Autowired
    Environment env;

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}

}
