package cn.newgxu.jpamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.querytool.DynamicQResult;
import net.sf.querytool.FilterTag;
import net.sf.querytool.provider.QueryTool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Pagination;

/**
 * 
 * @author wyx
 * @date 2007-3-23--02:58:16
 */
@SuppressWarnings("serial")
public abstract class JPAEntity implements Serializable {

	protected Log log = LogFactory.getLog(this.getClass());

	public static final QueryTool QT = new QueryTool(new FilterTag("ignore",
			"<", ">", "</", ">"));

	public static final Query query(final DynamicQResult dqr) {
		EntityManager em = em();

		Query q = em.createQuery(dqr.getQueryStr());
		for (Object k : dqr.getParams().keySet()) {
			q.setParameter((String) k, dqr.getParams().get(k));
		}
		return q;

	}

	public static List<?> getResultList(DynamicQResult dqr) {
		return query(dqr).getResultList();
	}

	public static JPAEntity getById(Class<?> entityType, Object id)
			throws ObjectNotFoundException {
		JPAEntity entity = (JPAEntity) em().find(entityType, id);
		if (entity == null) {
			throw new ObjectNotFoundException();
		}
		return entity;
	}

	// 添加了事务管理，否则在发消息时插入不了
	public void save() {
//		em().getTransaction().begin();
		em().persist(this);
//		em().getTransaction().commit();
	}

	public void delete() {
		em().remove(this);
	}

	public void update() {
		em().merge(this);
	}

	public static Pair P(Object id, Object value) {
		return new Pair(id, value);
	}

	public final static Object SQ(Object... args)
			throws ObjectNotFoundException {
		try {
			return Q(args).getSingleResult();
		} catch (Exception e) {
			throw new ObjectNotFoundException();
		}
	}

	public final static Query Q(Object... args) {
		String qstr = null;
		List<Pair> params = new ArrayList<Pair>();
		int first = 0;
		int size = Integer.MAX_VALUE;
		for (Object o : args) 
		{
			if (o instanceof String)
				qstr = (String) o;
			if (o instanceof Pair)
				params.add((Pair) o);
			
			if (o instanceof Pair[]) 
			{
				for (Pair p : (Pair[]) o) 
				{
					params.add(p);
				}
			}
			if (o instanceof List) 
			{
				for (Object p : (List<?>) o) 
				{
					params.add((Pair) p);
				}
			}
			if (o instanceof Pagination) 
			{
				Pagination p = (Pagination) o;
				first = p.getFirst();
				size = p.getPageSize();
			}
		}
		Query q = _q(qstr, params);
		q.setFirstResult(first);
		q.setMaxResults(size);
		System.out.println(qstr+"  "+first+"  "+size);
		return q;
	}

	final static EntityManager em() {
		return ModelContext.getEntityManager();
	}

	static Query _q(String qstr, List<Pair> params) 
	{
		//System.out.println(em());
		Query q = em().createQuery(qstr);
		for (Pair p : params) 
		{
			if (p.id instanceof String) 
			{
				q.setParameter((String) p.id, p.value);
			} else
				q.setParameter(Integer.parseInt(p.id.toString()), p.value);
		}
		return q;
	}

	public static class Pair {
		Object id;

		Object value;

		public Pair(Object id, Object value) {
			super();
			this.id = id;
			this.value = value;
		}

		public String toString() {
			return "" + id + "=>" + value;
		}
	}

	/**
	 * 使用MySQL方言去获取数据，有时是很方便的。
	 * @param sql
	 * @since 2012-05-10
	 * @author ivy
	 * @return
	 */
//	public static Object createNativeSQL(String sql) {
//		return em().createNativeQuery(sql).getSingleResult();
//	}
	
	/**
	 * 获取entityManager，自己去控制实体的数据层。
	 * @return EntityManager
	 * @author longkai
	 * @since 2012-09-20
	 */
	protected static EntityManager getEntityManager() {
		EntityManager entityManager = em();
		if (entityManager == null)
			return ModelContext.getEntityManager();
		return entityManager;
	}
	
	/**
	 * 通过传入的实体名获取实体的总记录数。
	 * @param entityName
	 * @return 记录数
	 * @author longkai
	 * @since 2012-09-20
	 */
	/*protected static int size(String entityName) {
		StringBuilder sb = new StringBuilder();
		String alias = entityName.substring(0, 1).toLowerCase();
		sb.append("select count(").append(alias)
		.append(".id) ").append("from ").append(entityName)
		.append(" ").append(alias);
		Long size = em().createQuery(sb.toString(), Long.class)
				.setFirstResult(0).setMaxResults(1).getSingleResult();
		return size.intValue();
	}*/
}
