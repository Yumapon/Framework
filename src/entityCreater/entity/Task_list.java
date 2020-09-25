package entityCreater.entity;

import annotation.Entity;
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

	public void setNum(String num) {
		this.num = num;
 	}

	public void setDeadline(java.sql.Date deadline) {
		this.deadline = deadline;
 	}

	public void setName(String name) {
		this.name = name;
 	}

	public void setContent(String content) {
		this.content = content;
 	}

	public void setClient(String client) {
		this.client = client;
 	}

}
