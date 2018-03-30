package cn.nriet.manage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.nriet.entity.User;
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	/**
	 * 根据名称和密码查询用户
	 * @return
	 */
	List<User> findByUsernameAndPassword(String name, String password);
	
	
	@Query("select u from User u where u.username = :username")
	User getUserByUsername(@Param("username") String name);
}
