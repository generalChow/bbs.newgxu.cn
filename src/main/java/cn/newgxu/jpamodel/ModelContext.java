package cn.newgxu.jpamodel;

import javax.persistence.EntityManager;

/**
 * 
 * @author wyx
 * @date 2007-3-23--03:00:32
 */
public class ModelContext {

	private static EntityManagerLookup EM_LOOKUP = null;

	public final static void registerEntityManagerLookup(
			EntityManagerLookup lookup) {
		EM_LOOKUP = lookup;
	}

	public final static EntityManager getEntityManager() {
		return EM_LOOKUP.lookupEntityManager();
	}

	public interface EntityManagerLookup {
		EntityManager lookupEntityManager();
	}

}
