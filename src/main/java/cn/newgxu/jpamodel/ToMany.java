package cn.newgxu.jpamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.newgxu.jpamodel.JPAEntity.Pair;

/**
 * 
 * @author wyx
 * @date 2007-3-23--04:32:15
 */
public class ToMany {
	protected Object owner;

	protected String prop;

	protected Class<?> entityType;

	protected String eql;

	protected String alias = "o";

	public ToMany(Class<?> clazz, String prop,Object owner) {
		super();
		this.owner = owner;
		this.prop = prop;
		this.entityType = clazz;
		initEql();
	}

	private void initEql() {
		this.eql = "from " + entityType.getName() + " " + alias + " where "
				+ alias + "." + prop + " = :" + prop;
	}

	public ToMany alias(String a) {
		this.alias = a;
		this.initEql();
		return this;
	}

	Q getQByProps(Pair... props) {
		StringBuffer qstr = new StringBuffer(eql);
		List<JPAEntity.Pair> params = new ArrayList<JPAEntity.Pair>();
		params.add(new Pair(prop, this.owner));
		for (Pair p : props) {
			qstr.append(" and " + alias + "." + p.id + " = :" + p.id);
			params.add(p);
		}
		return new Q(qstr.toString(), params);
	}

	Q getDelQByProps(Pair... props) {
		Q q = getQByProps(props);
		return new Q("delete  " + q.qstr, q.params);
	}

	public List<?> getAll() {
		return query(getQByProps()).getResultList();
	}

	private Query query(Q q) {
		return JPAEntity.Q(q.qstr, q.params);
	}

	public List<?> findByProps(Pair... props) {
		return query(getQByProps(props)).getResultList();
	}

	public Object findSingleByProps(Pair... conditions) {
		return query(getQByProps(conditions)).getSingleResult();
	}

	public List<?> findByConditions(Object... args) {
		return query(getQByConditions(args)).getResultList();
	}

	public Object findSingleByConditions(Object... args) {
		return query(getQByConditions(args)).getSingleResult();
	}

	public Query qfind(Pair... props) {
		return query(getQByProps(props));
	}

	public int deleteByProps(Pair... conditions) {
		return query(getDelQByProps(conditions)).executeUpdate();
	}

	public static final class Q {
		String qstr;

		List<JPAEntity.Pair> params;

		public Q(String qstr, List<Pair> params) {
			super();
			this.qstr = qstr;
			this.params = params;
		}

		public String toString() {
			return "qstr:" + qstr + " Params:" + params;
		}
	}

	public static final Pair P(Object id, Object value) {
		return JPAEntity.P(id, value);
	}

	Q getQByConditions(Object... args) {
		if (args == null || args.length == 0)
			throw new IllegalArgumentException("incorrect args");
		String conds = (String) args[0];
		String feql = this.eql + " and " + conds;
		List<Pair> params = new ArrayList<Pair>();
		params.add(new Pair(this.prop, this.owner));
		for (int i = 1; i < args.length; i++) {
			Object o = args[i];
			if (o instanceof Pair) {
				params.add((Pair) o);
			} else
				throw new RuntimeException("the cdr of args must be Pair");
		}
		return new Q(feql, params);
	}

}
