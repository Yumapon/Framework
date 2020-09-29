import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import query.Query;
import query.QueryInfo;

public class JunitQueryTest {

	//User_id entity = new User_id();
	Query q;
	Query q2;//Task_list用

	String sql;

	User_id entity;
	Task_list entity2;

	QueryInfo qi;
	QueryInfo qi2;

	@Before
	public void setUp() {
		System.out.println("-------テスト結果-------");
		//クエリクラスの生成
		q = new Query();
		entity = new User_id();
		entity.setId(121212);
		entity.setPassword("asasasasas");

		qi = new QueryInfo();
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("id");
		columnNames.add("password");

		Map<String, String> value = new HashMap<>();
		//value.put("id", "1234");
		value.put("password", "hogehoge");

		qi.setTableName("User_id");
		qi.setIdName("id");
		qi.setColumnNames(columnNames);
		qi.setColumnValues(value);

		//クエリクラスの生成
		q2 = new Query();
		entity2 = new Task_list();
		entity2.setNum("hogehoge");
		entity2.setClient("poyopoyo");

		qi2 = new QueryInfo();
		ArrayList<String> columnNames2 = new ArrayList<>();
		columnNames2.add("num");
		columnNames2.add("client");
		columnNames2.add("deadline");
		columnNames2.add("name");
		columnNames2.add("content");

		Map<String, String> value2 = new HashMap<>();
		//value.put("id", "1234");
		value2.put("name", "hogehoge");
		value2.put("client", "hogehoge");
		value2.put("content", "hogehoge");

		qi2.setTableName("Task_list");
		qi2.setIdName("num");
		qi2.setColumnNames(columnNames2);
		qi2.setColumnValues(value2);
	}

	@Ignore
	@Test
	public void test() {
		sql = "INSERT INTO User_id Values(333333, \"aaaaa\");";
		q.executeUpdate(sql);
	}

	@Ignore
	@Test
	public void test2() {
		sql = q.createInsertSql(qi);
	}

	@Ignore
	@Test
	public void test3() {
		sql = q.createUpdateSql(qi);
	}

	@Ignore
	@Test
	public void test4() {
		sql = q.createCheckRecordSql(qi);
	}

	@Ignore
	@Test
	public void test5() {
		sql = q.createDeleteSql(qi);
	}

	@Test
	public void test6() {
		sql = q.createSelectSql(qi);
	}

	@Test
	public void test7() {
		sql = q2.createSelectSql(qi2);
	}

	@After
	public void tearDown() {
		System.out.println(sql);
	}

}
