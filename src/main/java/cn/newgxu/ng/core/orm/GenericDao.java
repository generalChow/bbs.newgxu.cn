/*
 * Copyright im.longkai@gmail.com
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
 */
package cn.newgxu.ng.core.orm;

import java.io.Serializable;

import cn.newgxu.ng.util.Pagination;


/**
 * 通用的数据访问接口。
 * 
 * @author longkai
 * @param <T> 泛型
 * @since 2012-12-29
 */
public interface GenericDao<T> {

	/**
	 * 持久化一个实体。
	 * @param entity 实体
	 * @return 持久化后的实体
	 */
	T persist(T entity);
	
	/**
	 * 更新一个实体。
	 * @param entity 实体
	 * @return 更新后的实体
	 */
	T merge(T entity);

	/**
	 * 将一个持久化的实体游离化。
	 * @param entity 实体
	 * @return 游离化后的实体
	 */
	T remove(T entity);
	
	/**
	 * 通过序列化的标识来查找实体。
	 * @param pk 序列化的键
	 * @return 查找到的实体
	 */
	T find(Serializable pk, Class<T> type); 
	
	/**
	 * 查找指定类型的记录数量。
	 * @param type 要查找的类型
	 * @return 记录数目
	 */
	int size(Class<T> type);
	
	/**
	 * 获取实体列表。
	 * @param NO 第几页（用于分页）
	 * @param howMany 每页的记录数
	 * @return 分页模型
	 */
	Pagination<T> list(int NO, int howMany, Class<T> type);
	
}
