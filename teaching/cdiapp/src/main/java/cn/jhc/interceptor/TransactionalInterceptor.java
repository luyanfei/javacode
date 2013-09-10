package cn.jhc.interceptor;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import cn.jhc.annotations.PersistenceLog;
import cn.jhc.annotations.Transactional;
import cn.jhc.manager.Manager;

@Transactional
@Interceptor
public class TransactionalInterceptor implements Serializable {

	private static final long serialVersionUID = -4005485633217198958L;

	@Inject @PersistenceLog
	private Logger logger;

	@AroundInvoke
	public Object manageTransaction(InvocationContext ctx) throws Exception {
		logger.info("Begining transactional interceptor.");
		Object target = ctx.getTarget();
		if(!(target instanceof Manager))
			throw new RuntimeException("Transactional annotation can only apply to Manager.");
		Manager manager = (Manager) target;
		EntityManager em = manager.getEntityManager();
		Object obj = null;
		try {
			em.getTransaction().begin();
			obj = ctx.proceed();
			em.getTransaction().commit();
		} catch (RuntimeException ex) {
			try {
				em.getTransaction().rollback();
			} catch (RuntimeException rex) {
				logger.error("Could not rollback transaction. " + rex);
			}
			throw ex;
		}
		logger.info("Ending transaction interceptor.");
		return obj;
	}
}
