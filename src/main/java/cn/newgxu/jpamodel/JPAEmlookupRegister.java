package cn.newgxu.jpamodel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.newgxu.jpamodel.ModelContext.EntityManagerLookup;

/**
 * 
 * @author wyx
 * @date 2007-3-23--08:08:29
 */
public class JPAEmlookupRegister implements ServletContextListener {
	public static final String DEFAULT_PERSISTENCE_MANAGER_FACTORY_BEAN_NAME = "entityManagerFactory";

	private String entityManagerFactoryBeanName = DEFAULT_PERSISTENCE_MANAGER_FACTORY_BEAN_NAME;

	public String getEntityManagerFactoryBeanName() {
		return entityManagerFactoryBeanName;
	}

	public void setEntityManagerFactoryBeanName(
			String entityManagerFactoryBeanName) {
		this.entityManagerFactoryBeanName = entityManagerFactoryBeanName;
	}

	public void contextDestroyed(ServletContextEvent event) {

	}

	protected EntityManagerFactory lookupEntityManagerFactory(ServletContext sc) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		return (EntityManagerFactory) wac.getBean(
				getEntityManagerFactoryBeanName(), EntityManagerFactory.class);
	}

	public void contextInitialized(final ServletContextEvent event)
	{
		EntityManagerLookup eml = new EntityManagerLookup() 
		{
			public EntityManager lookupEntityManager() 
			{
				EntityManagerFactory emf = lookupEntityManagerFactory(event.getServletContext());
				if (TransactionSynchronizationManager.hasResource(emf)) 
				{
					EntityManagerHolder holder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(emf);
					EntityManager s = holder.getEntityManager();
					return s;
				} else {
//					return lookupEntityManagerFactory(event.getServletContext()).createEntityManager();
					return null;
				}
			}
		};
		ModelContext.registerEntityManagerLookup(eml);

	}

}
