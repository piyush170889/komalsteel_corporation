package co.in.replete.komalindustries.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableTransactionManagement
@EnableCaching
@ComponentScan(basePackages = "co.in.replete")
@PropertySources({
	@PropertySource("classpath:config.properties"),
	@PropertySource("classpath:db.properties") 
})
public class BaseModuleConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
	private Environment environment;
	
	@Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(false);
    }
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {

	registry.addMapping("/**").allowCredentials(false).allowedOrigins("*").allowedMethods("PUT", "POST", "GET", "OPTIONS", "DELETE").exposedHeaders("Authorization", "Content-Type");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:bootstrap/");
		registry.addResourceHandler("/dist/**").addResourceLocations("classpath:dist/");
		registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:plugins/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:js/");
	}
	
	@Bean(name="jdbcTemplate")
    public JdbcTemplate getJdbcTemplate() {
		
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource(), true);
        return jdbcTemplate;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
        factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factory.setShared(true);
        return factory;
    }

    @Bean
    public CacheManager cacheManager() {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehCacheCacheManager().getObject());
        return cacheManager;
    }
    
	@Bean(name="sqlProperties")
	public Properties getSqlPropertiesFile() throws IOException {
		Resource resource = new ClassPathResource("sqlQueries.properties");
		return PropertiesLoaderUtils.loadProperties(resource);
	}
	
	@Bean(name="responseMessageProperties")
	public Properties getResponseMessagePropertiesFile() throws IOException {
		Resource resource = new ClassPathResource("responseMessages.properties");
		return PropertiesLoaderUtils.loadProperties(resource);
	}
	
	@Bean(name="configProperties")
	public Properties getConfigPropertiesFile() throws IOException {
		Resource resource = new ClassPathResource("config.properties");
		return PropertiesLoaderUtils.loadProperties(resource);
	}
	
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        
        return viewResolver;
    }

	 @Bean
	 public DriverManagerDataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    
	    dataSource.setDriverClassName(environment.getProperty("db.driver"));
		dataSource.setUrl(environment.getProperty("db.url"));
		dataSource.setUsername(environment.getProperty("db.userName"));
		dataSource.setPassword(environment.getProperty("db.password"));
	    
	    return dataSource;
	 }
	 
	@Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "co.in.replete.beans.dto" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
     
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;        
    }
     
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
    
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
    	
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    	multipartResolver.setMaxUploadSize(3768666);
    	return multipartResolver;
    }
    
    @Bean(name="paramsList") 
    public HashMap<String, Object> getParamsMap() {
    	return new HashMap<String, Object>();
    }
    
}