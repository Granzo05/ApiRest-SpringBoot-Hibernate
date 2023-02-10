package com.example.ApiREST.configurationSpring;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = { "com.example.ApiREST.repositories" },
        entityManagerFactoryRef = "entityManagerFactory")
class ApplicationConfig {
}
