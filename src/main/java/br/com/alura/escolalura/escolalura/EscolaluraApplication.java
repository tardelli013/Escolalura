package br.com.alura.escolalura.escolalura;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // Define a classe como classe de configuração
@EnableAutoConfiguration // Habilita a autoconfiguração
@EnableSwagger //Habilita o Swagger
@ComponentScan(basePackages = {"br.com.alura"}) //Escaneia todos os pacotes com o padrão br.com.erudio
@SpringBootApplication
public class EscolaluraApplication {

	//Injeta uma instancia de SpringSwaggerConfig
	@Autowired
	private SpringSwaggerConfig swaggerConfig;

	public static void main(String[] args) {
		SpringApplication.run(EscolaluraApplication.class, args);
	}


	@Bean
	public SwaggerSpringMvcPlugin groupOnePlugin() {
		return new SwaggerSpringMvcPlugin(swaggerConfig)
				//Adiciona as configurações do Swagger ao SwaggerSpringMvcPlugin
				.apiInfo(apiInfo()) //Adiciona as propriedades de configuração
				.includePatterns("/person.*?", "/greeting.*?") //Habilita o Swagger para os nossos 2 endpoints
				.swaggerGroup("admin");
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo( //Configurações de contato, licença etc não nescessáriamente precisa ser definida
				"Swagger With Spring Boot",
				"This is a simple application to demonstrate how to work with Swagger in Spring Boot project!",
				"Free to use and mess around",
				"erudio@gmail.com",
				"Open Licence",
				"myemail@gmail.com"
		);
		return apiInfo;
	}
}
	