import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import entityCreater.entity.User_id;
import query.Query;
import query.QueryInfo;

public class JunitQueryTest {

	//User_id entity = new User_id();
	Query q;

	String sql;

	User_id entity;

	QueryInfo qi;

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

	@Test
	public void test5() {
		sql = q.createDeleteSql(qi);
	}

	@After
	public void tearDown() {
		System.out.println(sql);
	}

}
