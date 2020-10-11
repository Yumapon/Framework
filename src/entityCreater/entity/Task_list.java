package entityCreater.entity;

import annotation.Entity;
import annotation.OneToOne;
import annotation.Table;
import annotation.column;
import annotation.id;

@Entity
@Table("TASK_LIST")
public class Task_list {

	@id
	@column
	private String num;

	@column
	private java.sql.Date deadline;

	@column
	private String name;

	@column
	private String content;

	@column
	private String client;

	@OneToOne
	private entityCreater.entity.Test test;

	public entityCreater.entity.Test getTest() {
		return test;
	}

	public void setTest(entityCreater.entity.Test test) {
		this.test = test;
	}

	public void setNum(String num) {
		this.num = num;
 	}

	public String getNum() {
		return this.num;
 	}

	public void setDeadline(java.sql.Date deadline) {
		this.deadline = deadline;
 	}

	public java.sql.Date getDeadline() {
		return this.deadline;
 	}

	public void setName(String name) {
		this.name = name;
 	}

	public String getName() {
		return this.name;
 	}

	public void setContent(String content) {
		this.content = content;
 	}

	public String getContent() {
		return this.content;
 	}

	public void setClient(String client) {
		this.client = client;
 	}

	public String getClient() {
		return this.client;
 	}

}
