package cn.jhc.resource;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.util.AnnotationLiteral;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;

import cn.jhc.annotations.PersistenceLog;

public class MyExtension implements Extension {
	
	@SuppressWarnings("serial")
	public void closeFactory(@Observes BeforeShutdown event, BeanManager manager) {
		EntityManagerFactory factory = EntityManagerProvider.getFactory();
		if(factory.isOpen()) factory.close();
		Set<Bean<?>> beans = manager.getBeans(Logger.class, new AnnotationLiteral<PersistenceLog>() {});
		Bean<?> bean = beans.iterator().next();
		CreationalContext<?> cc = manager.createCreationalContext(bean);
		Logger logger = (Logger) manager.getReference(bean, Logger.class, cc);
		logger.info("BeforeShutdown event is fired. And EntityManagerFactory is closed.");
	}
}
