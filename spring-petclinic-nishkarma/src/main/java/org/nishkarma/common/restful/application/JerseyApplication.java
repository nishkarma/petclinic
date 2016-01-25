package org.nishkarma.common.restful.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.ContextResolver;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;


@ApplicationPath("/")
public class JerseyApplication extends ResourceConfig {

	private final String RESTFUL_PACKAGE_FILE = "restful/restfulpackage.properties";
	Logger logger = LoggerFactory.getLogger(JerseyApplication.class);

	public JerseyApplication() {
		// Now you can expect validation errors to be sent to the client.
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		// @ValidateOnExecution annotations on subclasses won't cause errors.
		property(
				ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK,
				true);
		// Further configuration of ResourceConfig.

		// Providers - JSON.
		register(JsonConfiguration.class);
		register(MoxyJsonFeature.class);
		register(MultiPartFeature.class);
		
		InputStream is = null;
		
		try {
			Properties prop = new Properties();
			is = getClassLoader().getResourceAsStream(RESTFUL_PACKAGE_FILE);
			prop.load(is);
			String basePackage = prop.getProperty("basePackage");
			
			Set<Class<?>> controllers = findMyTypes(basePackage);
			
			if (!controllers.isEmpty()) {
				registerClasses(controllers);
			}
			
		} catch (ClassNotFoundException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		} finally {
			try {
				if (is != null)	is.close(); 
			} catch (Exception e){}
		}
		
	}

	private  Set<Class<?>> findMyTypes(String basePackage) throws IOException, ClassNotFoundException
	{
	    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

	    Set<Class<?>> candidates = new HashSet<Class<?>>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
	                               resolveBasePackage(basePackage) + "/" + "**/*.class";
	    Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    for (Resource resource : resources) {
	        if (resource.isReadable()) {
	            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	            if (isCandidate(metadataReader)) {
	                candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
		            //logger.debug("-----------"+metadataReader.getClassMetadata().getClassName());
	            }
	        }
	    }
	    return candidates;
	}

	private String resolveBasePackage(String basePackage) {
	    return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}	
	
	private boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException
	{
	    try {
	        Class<?> c = Class.forName(metadataReader.getClassMetadata().getClassName());
	        if (c.getAnnotation(Controller.class) != null) {
	            return true;
	        }
	    }
	    catch(Throwable e){
	    }
	    return false;
	}		
	

	/**
	 * Configuration for
	 * {@link org.eclipse.persistence.jaxb.rs.MOXyJsonProvider} - outputs
	 * formatted JSON.
	 */
	public static class JsonConfiguration implements
			ContextResolver<MoxyJsonConfig> {

		@Override
		public MoxyJsonConfig getContext(final Class<?> type) {
			final MoxyJsonConfig config = new MoxyJsonConfig();
			config.setFormattedOutput(true);
			return config;
		}
	}
}
