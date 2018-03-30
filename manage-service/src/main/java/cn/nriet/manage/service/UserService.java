package cn.nriet.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import cn.liandi.framework.springjpa.BasicJpaService;
import cn.nriet.entity.User;
import cn.nriet.manage.dao.UserDao;

@Service
public class UserService extends BasicJpaService<User,Integer,UserDao> {
	

	
	public User findByNameAndPassowrd(String name, String password) {
		// 查询符合条件的用户
		System.out.println("#######################"+name+";"+password);
		List<User> users = baseDao.findByUsernameAndPassword(name, password);
		System.out.println("#######################"+users.size());
		return users.size() == 1 ? users.get(0) : new User();
	}
	
//	public User save(User u) {
//		return userDao.save(u);
//	}
//	
//	public List<User> findAll(){
//		return userDao.findAll();
//	}


}
