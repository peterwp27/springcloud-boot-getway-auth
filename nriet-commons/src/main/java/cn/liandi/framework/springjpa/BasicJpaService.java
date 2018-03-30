package cn.liandi.framework.springjpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.liandi.framework.util.Testtrans;

/**
 * JpaServices
 * 
 * @author b_wangpei
 *
 * @param <T>
 *            服务对应的实体类型
 * @param <IdType>
 *            实体主键的数据类型
 * @param <TD>
 *            实体对应的Repository类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BasicJpaService<T, IdType extends Serializable, TD extends JpaRepository<T, IdType>> {

	@Autowired
	public TD baseDao;

	@Autowired
	private EntityManager entityManager;

	private static Session session;

	private Session getSession() {
		if(session == null || !session.isConnected()) {
			session = entityManager.unwrap(org.hibernate.Session.class);
		}
		return session;
	}
	/**
	 * 执行普通sql语句，返回Map:list
	 * @param sql
	 * @param objects
	 *            依次写入的sql参数
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findBySql(String sql, Object... objects) {
		SQLQuery query = getSQLQuery(sql,objects);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	/**
	 * 执行sql语句，返回Map：list
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findBySql(String sql, Map<String, Object> paramMap) {
		SQLQuery query = getSQLQuery(sql,paramMap);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	/**
	 * 执行普通sql语句，返回具体实体类
	 * @param sql
	 * @param rsClass
	 *            返回实体类型
	 * @param objects
	 *            依次写入的sql参数
	 * @return
	 */
	public List<Object> findBySql(String sql, Class<? extends Object> rsClass, Object... objects) {
		SQLQuery query = getSQLQuery(sql,objects);
		Testtrans Testtrans = new Testtrans(rsClass);
		query.setResultTransformer(Testtrans);
		return query.list();
	}
	/**
	 * 执行sql语句返回实体类型
	 * @param sql
	 * @param rsClass
	 * @param params
	 * @return
	 */
	public List<Object> findBySql(String sql, Class<? extends Object> rsClass, Map<String, Object> params) {
		SQLQuery query = getSQLQuery(sql,params);
		Testtrans Testtrans = new Testtrans(rsClass);
		query.setResultTransformer(Testtrans);
		return query.list();
	}

	public List<T> find(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}
	/**
	 * 执行sql更新语句
	 * @param hql
	 * @param params
	 * @return
	 */
	public int excuteSql(String sql, Map<String, Object> params) {
		int i = 0;
		SQLQuery query = getSQLQuery(sql,params);
		i = query.executeUpdate();
		return i;
	}
	public int excuteSql(String sql,Object...objects) {
		int i = 0;
		SQLQuery query = getSQLQuery(sql,objects);
		i = query.executeUpdate();
		return i;
	}
	/**
	 * 获取sqlquery 抽取参数注入功能，支持？方式和：方式的参数注入
	 * @param sql
	 * @param objects
	 * @return
	 */
	@SuppressWarnings("unused")
	private SQLQuery getSQLQuery(String sql,Object... objects) {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		return query;
	}
	@SuppressWarnings("unused")
	private SQLQuery getSQLQuery(String sql,Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return query;
	}
	// @Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return baseDao.findAll();
	}

	// @Override
	public List<T> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return baseDao.findAll(sort);
	}

	// @Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	// @Override
	public <S extends T> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return baseDao.saveAndFlush(entity);
	}

	// @Override
	public void deleteInBatch(Iterable<T> entities) {
		// TODO Auto-generated method stub

	}

	// @Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	// @Override
	public T getOne(IdType id) {
		// TODO Auto-generated method stub
		return baseDao.getOne(id);
	}

	// @Override
	public <S extends T> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return baseDao.findAll(example);
	}

	// @Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return baseDao.findAll(example, sort);
	}

	// @Override
	public Page<T> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return baseDao.findAll(arg0);
	}

	// @Override
	public long count() {
		// TODO Auto-generated method stub
		return baseDao.count();
	}

	// @Override
	public void delete(T arg0) {
		// TODO Auto-generated method stub

	}

	// @Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	// @Override
	public <S extends T> S save(S arg0) {
		// TODO Auto-generated method stub
		return baseDao.save(arg0);
	}

	// @Override
	public <S extends T> long count(Example<S> arg0) {
		// TODO Auto-generated method stub
		return baseDao.count(arg0);
	}

	// @Override
	public <S extends T> boolean exists(Example<S> arg0) {
		// TODO Auto-generated method stub
		return baseDao.exists(arg0);
	}

	// @Override
	public <S extends T> Page<S> findAll(Example<S> arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return baseDao.findAll(arg0, arg1);
	}

	// @Override
	public List<T> findAll(Iterable<IdType> ids) {
		// TODO Auto-generated method stub
		return baseDao.findAll(ids);
	}

	// @Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return baseDao.save(entities);
	}

	// @Override
	public T findOne(IdType id) {
		// TODO Auto-generated method stub
		return baseDao.findOne(id);
	}

	// @Override
	public boolean exists(IdType id) {
		// TODO Auto-generated method stub
		return false;
	}

	// @Override
	public void delete(IdType id) {
		// TODO Auto-generated method stub

	}

	// @Override
	public void delete(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub

	}

	// @Override
	public <S extends T> S findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return baseDao.findOne(example);
	}
}
