package cn.nriet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
	
	public static final String TYPE_CUSTOMER = "CUSTOMER";
	
	public static final String TYPE_MANAGER = "MANAGER";

    @Id
    @GeneratedValue
	private Integer id;

    private String username;

    private String password;

    private Integer sex;
    
    
//    @Column
//    private Integer is_delete;
//
//	public Integer getIs_delete() {
//		return is_delete;
//	}
//
//	public void setIs_delete(Integer is_delete) {
//		this.is_delete = is_delete;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    
}
