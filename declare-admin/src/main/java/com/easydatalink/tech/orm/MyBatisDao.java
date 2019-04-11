package com.easydatalink.tech.orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.easydatalink.tech.entity.IdEntity;
import com.easydatalink.tech.page.Page;
import com.easydatalink.tech.utils.ReflectionUtils;

/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * 
 */ 
@SuppressWarnings("unchecked")
public class MyBatisDao<T extends IdEntity> extends SqlSessionDaoSupport implements IMybatisDao<T> {
	protected Class<T> entityClass;
	@SuppressWarnings("rawtypes")
	protected Class sequenceClass;
	private static boolean allStatementsBuilt = false;
	
	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User>
	 */
	public MyBatisDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenericType(getClass());
		this.sequenceClass = this.entityClass;
	}
	
	
	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用MyBatisDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User> userDao = new MyBatisDao<User>(sessionFactory, User.class);
	 */
	public MyBatisDao(final Class<T> entityClass) {
		this.entityClass = entityClass;
		this.sequenceClass = this.entityClass;
	}
	
	@Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    	super.setSqlSessionFactory(sqlSessionFactory);
    	// a workaround for Mybatis concurrent bug
    	synchronized (getSqlSession().getConfiguration()) {
	    	if (!allStatementsBuilt) {
	    		getSqlSession().getConfiguration().getMappedStatements();
	    		allStatementsBuilt = true;
	    	}
    	}
    }

	public T get(final Long id) {
		Assert.notNull(id, "id不能为空");
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		T obj = (T)mapper.selectByPrimaryKey(id);
		return obj;
	}
	
	public T getByCode(final String code) {
		Assert.notNull(code, "code不能为空");
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		T obj = (T)mapper.getByCode(code);
		return obj;
	}
	
	@Override
	public T getByCode(T entity) {
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		T obj = (T)mapper.getByCode(entity);
		return obj;
	}
	
	@Override
	public T getCityCode(T entity) {
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		T obj = (T)mapper.getCityCode(entity);
		return obj;
	}

	/**
	 * 逻辑删除
	 */
	public void delete(final Long id) {
		Assert.notNull(id, "id不能为空");
		T t =get(id);
		t.setIsRemoved("1");
		update(t);
	}
	
	/**
	 * 逻辑删除
	 */
	public void delete(T entity) {
		if (entity == null) {
			return;
		}
		entity.setIsRemoved("1");
		update(entity);
	}
	
	/**
	 * 物理删除
	 * @param id
	 */
	public void deleteComplete(final Long id) {
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 保存新增的对象.
	 */
	public Long insert(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		mapper.insert(entity);
		return entity.getId();
	}
	
	/**
	 * 保存修改的对象. 返回0表示更新失败。如果这个值已经被人修改过，会抛出ConcurrentModificationException
	 */
	public boolean update(final T entity) throws ConcurrentModificationException {
		//TODO 这里的事务处理有问题，请大家注意
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());		
		int count = mapper.updateByPrimaryKeySelective(entity);
		if (count == 0) {
			IdEntity o = mapper.selectByPrimaryKey(entity.getId());
			Long version = o == null ? 0 : o.getVersion();
			if (version != entity.getVersion()) {
				throw new ConcurrentModificationException(mapper.getClass().getName() + ", Stale data: version="+entity.getVersion()+", version in db="+version);
			}
			throw new RuntimeException("mapper.update return 0 for:"+ mapper.getClass().getName());
		}
		return count > 0;
	}
	
	// TODO need a better method
	public void batchInsert(List<T> entities) {
		if (entities != null) {
			for (T entity: entities) {
				insert(entity);
			}
		}
	}
	
	// TODO need a better method
	public void batchUpdate(List<T> entities) {
		if (entities != null) {
			for (T entity: entities) {
				update(entity);
			}
		}
	}
	
	@Override
	public void batchDelete(List<Long> ids) {
		if (ids != null) {
			for (Long id : ids) {
				delete(id);
			}
		}
	}

	private Class<?> getMapperClass() {
		Class<?> mapperClass = null;
		try {
			String mapperCls = entityClass.getName().replace("entity", "mapper")+"Mapper";
			mapperClass = Class.forName(mapperCls);
			return mapperClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}
	
	public <X> Page<X> findPage(final Page<X> page, String statementName, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");
		List<X> result;
		if(page.getNumPerPage() <= 0){
			result = getSqlSession().selectList(getFullSatementName(statementName), toParameterMap(values, page));
			page.setResult(result);
			page.setTotalCount(result == null ? 0 : result.size());
		} else {
			result = getSqlSession().selectList(getFullSatementName(statementName), toParameterMap(values, page), getRowBounds(page));
			page.setResult(result);

			if (page.isAutoCount()) {
					long totalCount = getTotalCount(getFullSatementName(statementName), values);
					page.setTotalCount(totalCount);
			}
		}

		return page;
	}
	@SuppressWarnings("rawtypes")
	private long getTotalCount(String statementName,Object values ) {  
        Map parameterMap=toParameterMap(values);  
        long count=0l;  
        try {  
            MappedStatement mst = getSqlSession().getConfiguration().getMappedStatement(statementName);  
            BoundSql boundSql = mst.getBoundSql(parameterMap);  
            String sql = " select count(*) total_count from (" + boundSql.getSql()+ ") a ";  
            PreparedStatement pstmt = getSqlSession().getConnection().prepareStatement(sql);  
            setParameters(pstmt,mst,boundSql,parameterMap);  
            ResultSet rs=pstmt.executeQuery();   
            if (rs.next()){   
                count = rs.getLong("total_count");  
            }  
            rs.close();  
            pstmt.close();  
        } catch (Exception e) {  
            count=0l;  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
        return count;  
    }  
	@SuppressWarnings("rawtypes")  
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {    
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());    
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();    
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();    
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();    
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);    
            for (int i = 0; i < parameterMappings.size(); i++) {    
                ParameterMapping parameterMapping = parameterMappings.get(i);    
                if (parameterMapping.getMode() != ParameterMode.OUT) {    
                    Object value;  
                    String propertyName = parameterMapping.getProperty();    
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {    
                        value = null;    
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {    
                        value = parameterObject;    
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {    
                        value = boundSql.getAdditionalParameter(propertyName);    
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {    
                        value = boundSql.getAdditionalParameter(prop.getName());    
                        if (value != null) {    
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));    
                        }    
                    } else {    
                        value = metaObject == null ? null : metaObject.getValue(propertyName);    
                    }    
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();    
                    if (typeHandler == null) {    
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());    
                    }  
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());    
                }    
            }    
        }    
    }    
	
	private String getFullSatementName(String statementName) {
		return getMapperClass().getName()+"." + statementName;
	}
	
	@SuppressWarnings("rawtypes") 
	protected RowBounds getRowBounds(Page p) {
		return new RowBounds(p.getFirst() - 1, p.getNumPerPage());
	}
	
	/*
	 * startRow,endRow : 用于oracle分页使用,从1开始
	 * offset,limit : 用于mysql 分页使用,从0开始
	 */
	@SuppressWarnings("rawtypes")
	protected Map toParameterMap(Object parameter, Page p) {
		Map map = toParameterMap(parameter);
		map.put("startRow", p.getFirst());
		// get PAGE_FETCH_NUMBER pages
		map.put("endRow", p.getFirst() + p.getNumPerPage());
		map.put("offset", p.getFirst() - 1);
		map.put("limit", p.getNumPerPage());
		return map;
	}

	@SuppressWarnings("rawtypes")
	protected Map toParameterMap(Object parameter) {
		if (parameter == null) {
			return new HashMap();
		}
		if (parameter instanceof Map) {
			return (Map<?,?>) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	protected <X> X getMapper(Class<X> type) {
		return getSqlSession().getMapper(type);
	}
	@SuppressWarnings("rawtypes")
	protected Mapper getMapper() {
		return (Mapper)getSqlSession().getMapper(getMapperClass());
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List getAll() {
		return getMapper().getAll();
	}
	@Override
	public void deleteAll(){
		Mapper<T> mapper = (Mapper<T>)getMapper(getMapperClass());
		mapper.deleteAll();
	}

	@Override
	public List<T> findBy(String propName, Object propValue) {
		throw new RuntimeException("Unsupported");
	}

	@Override
	public T findUniqueBy(String propName, Object propValue) {
		throw new RuntimeException("Unsupported");
	}

	@Override
	public List<T> findByIds(List<Long> ids) {
		if (ids == null) {
			return null;
		}
		
		List<T> list = new ArrayList<T>();
		for (Long id : ids) {
			list.add(get(id));
		}
		return list;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<T> find(String statementName, Map values) {
		return getSqlSession().selectList(getFullSatementName(statementName), values);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public Object findUnique(String statementName, Map values) {
		List list = getSqlSession().selectList(getFullSatementName(statementName), values);
		return list == null || list.size() == 0 ? null : list.get(0);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void delete(String statementName, Map values) {
		getSqlSession().delete(getFullSatementName(statementName), values);
	}

	@Override
	public void deleteBy(String propName, Object propValue) {
		throw new RuntimeException("Unsupported");
	}

	@Override
	public boolean isPropertyUnique(String propertyName, Object newValue, Object oldValue) {
		throw new RuntimeException("Unsupported");
	}
	@SuppressWarnings("rawtypes")
	@Override
	public long count(String statementName, Map values) {
		Object o = findUnique(statementName, values);
		return ((Number)o).longValue();
	}
	@SuppressWarnings("rawtypes")
	public Class getSequenceClass() {
		return sequenceClass;
	}

	@SuppressWarnings("rawtypes")
	public void setSequenceClass(Class sequenceClass) {
		this.sequenceClass = sequenceClass;
	}
}
