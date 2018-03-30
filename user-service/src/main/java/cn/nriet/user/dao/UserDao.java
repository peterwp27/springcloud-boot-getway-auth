package cn.nriet.user.dao;

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
	List<User> findByNameAndPassword(String name, String password);
	
	@Query("select u from User u where u.name = :name")
	User getUserByName(@Param("name") String name);
}
