package cn.nriet.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.nriet.entity.User;
import cn.nriet.user.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User findByNameAndPassowrd(String name, String password) {
		// 查询符合条件的用户
		System.out.println("#######################"+name+";"+password);
//		List<User> users = null;
//		User u = userDao.getUserByName(name);
		List<User> users = userDao.findByNameAndPassword(name, password);
		System.out.println("#######################"+users.size());
		return users.size() == 1 ? users.get(0) : new User();
//		return u;
	}
}
