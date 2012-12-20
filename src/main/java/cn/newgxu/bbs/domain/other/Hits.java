/*
 *
 * Copyright (C) 2012 longkai im.longkai@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.newgxu.bbs.domain.other;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.newgxu.jpamodel.JPAEntity;

/**
 * 这个是临时为了记录论坛手机版的访问量（用于和移动谈赞助）
 * 
 * @author longkai
 * @version 1.0
 * @since 2012-10-21
 */
@Entity
@Table(name = "hits")
public class Hits extends JPAEntity {

	private static final long	serialVersionUID	= 1L;
	
	private static final Logger l = LoggerFactory.getLogger(Hits.class);
	
	private static Hits lastHits;
	
	@Id
	@GeneratedValue
	private int		id;
	
	private int		hits;
	
	@Column(name = "unique_hits")
	private int		uniqueHits;
	
	private Date	date;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getUniqueHits() {
		return uniqueHits;
	}

	public void setUniqueHits(int uniqueHits) {
		this.uniqueHits = uniqueHits;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
	public static void addHits(boolean isLogin) {
		if (lastHits == null)
			lastHits = getLastHits();
		Calendar now = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(lastHits.getDate());
		
		l.info("last time: {}", last.getTime());
		l.info("this time: {}", now.getTime()); 
		
		if (now.get(Calendar.DATE) != last.get(Calendar.DATE)) {
			lastHits = new Hits();
			lastHits.setDate(now.getTime());
			lastHits.setHits(1);
			setUniqueHits(lastHits, isLogin);
			lastHits.setUniqueHits(1);
			getEntityManager().persist(lastHits);
		} else {
			lastHits.setHits(lastHits.getHits() + 1);
			setUniqueHits(lastHits, isLogin);
			getEntityManager().merge(lastHits);
		}
	}
	
	public static void setUniqueHits(Hits hits, boolean isLogin) {
		if (!isLogin)
			hits.setUniqueHits(hits.getUniqueHits() + 1);
	}
	
	public static Hits getLastHits() {
//		EntityManager entityManager = getEntityManager();
		Hits last = null;
		try {
//			last = entityManager.createQuery("from Hits h order by id desc", Hits.class)
//			.setFirstResult(0).setMaxResults(1).getSingleResult();
			last = (Hits) Q("from Hits h order by id desc").setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			last = null;
		}
		
		if (last != null) {
			return last;
		} else {
			last = new Hits();
			last.setDate(new Date());
//			entityManager.persist(last);
			getEntityManager().persist(last);
			return last;
		}
	}
	
}
