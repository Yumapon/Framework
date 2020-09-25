package entityCreater.entity;

import annotation.Entity;
import annotation.Table;
import annotation.column;
import annotation.id;

@Entity
@Table("USER_ID")
public class User_id {

	@id
	@column
	private int id;

	@column
	private String password;

	public void setId(int id) {
		this.id = id;
 	}

	public void setPassword(String password) {
		this.password = password;
 	}

}
