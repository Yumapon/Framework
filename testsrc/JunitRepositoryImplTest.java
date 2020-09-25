import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import dbMapper.RepositoryImpl;
import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import transactionManager.DefaultTransactionManager;
import transactionManager.TransactionManager;

public class JunitRepositoryImplTest {

	RepositoryImpl<User_id, Integer> repos;
	RepositoryImpl<Task_list, String> repos2;
	TransactionManager tm = new DefaultTransactionManager();

	@Before
	public void setUp() {
		System.out.println("-------テスト結果-------");
		repos = new RepositoryImpl<>();
		repos2 = new RepositoryImpl<>();
	}

	@Test
	public void test() {
		User_id entity = new User_id();
		entity.setId(1010110);
		entity.setPassword("passwordddd");

		/*
		tm.beginTransaction();
		repos.save(entity);
		tm.endTransaction();
		*/

		Task_list entity2 = new Task_list();
		entity2.setClient("hogehoge");
		entity2.setNum("swswswwswswwsws");
		entity2.setContent("asdfjakfjaksfjaksdjfaksdfjalkfj");
		entity2.setName("shifghsoifg");
		entity2.setDeadline(Date.valueOf("2017-03-02"));

		tm.beginTransaction();
		repos2.save(entity2);
		tm.endTransaction();

	}

}
