package com.example.springcachehierarchy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringCacheHierarchyApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringCacheHierarchyApplication.class, args);
	}

}
