package cn.sowell.datacenter.model.comm.dao;


public interface CommDao {

	/**
	 * 对象插入到数据表中
	 * @param demo
	 */
	void insert(Object demo);

	/**
	 * 从数据库中查找对应的pajo对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> clazz, String id);

	/**
	 * 更新一个pojo对象
	 * @param demo
	 */
	void update(Object demo);

	/**
	 * 从数据库中删除一个对象
	 * @param pojo
	 */
	void delete(Object pojo);


}
