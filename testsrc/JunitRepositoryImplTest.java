import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dbMapper.RepositoryImpl;
import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import exception.NoColumnValueException;
import transactionManager.DefaultTransactionManager;
import transactionManager.TransactionManager;

public class JunitRepositoryImplTest {

	RepositoryImpl<User_id, Integer> repos;
	RepositoryImpl<Task_list, String> repos2;
	RepositoryImpl<entityCreater.entity.Test, String> repos3;
	TransactionManager tm = new DefaultTransactionManager();

	@Before
	public void setUp() {
		System.out.println("-------テスト結果-------");
		repos = new RepositoryImpl<>();
		repos2 = new RepositoryImpl<>();
		repos3 = new RepositoryImpl<>();
	}

	@Ignore
	@Test
	public void test() {
		User_id entity = new User_id();
		entity.setId(1010110);
		entity.setPassword("passwordddd");

		tm.beginTransaction();
		repos.save(entity);
		tm.endTransaction();

		entity.setId(100);
		entity.setPassword("passwsgfdsordddd");

		tm.beginTransaction();
		repos.save(entity);
		tm.endTransaction();

		entity.setId(800);
		entity.setPassword("hogehoge");

		tm.beginTransaction();
		repos.save(entity);
		tm.endTransaction();

		/*
		Task_list entity2 = new Task_list();
		entity2.setClient("hogehoge");
		entity2.setNum("swswswwswswwsws");
		entity2.setContent("asdfjakfjaksfjaksdjfaksdfjalkfj");
		entity2.setName("shifghsoifg");
		entity2.setDeadline(Date.valueOf("2017-03-02"));

		tm.beginTransaction();
		repos2.save(entity2);
		tm.endTransaction();
		*/

	}

	@Ignore
	@Test
	public void test2() {
		if (repos.existsById(888888))
			System.out.println("あります");
		else
			System.out.println("ないです");
	}

	@Ignore
	@Test(expected = NoColumnValueException.class)
	public void test3() {

		//主キー削除
		User_id entity = new User_id();
		entity.setId(100);
		entity.setPassword("hogehoge");

		tm.beginTransaction();
		repos.delete(entity);
		tm.endTransaction();

		//条件削除
		System.out.println("-------テスト結果-------");
		User_id entity2 = new User_id();
		entity2.setPassword("password");

		tm.beginTransaction();
		repos.delete(entity2);
		tm.endTransaction();

		//条件なし削除（例外発生）
		System.out.println("-------テスト結果-------");
		User_id entity3 = new User_id();

		tm.beginTransaction();
		repos.delete(entity3);
		tm.endTransaction();
	}

	@Ignore
	@Test
	public void test4() {
		int i = repos.count();
		System.out.println(i);
	}

	@Ignore
	@Test
	public void test5() {
		Optional<User_id> entity = repos.findById(121212);
		if (entity.isPresent()) {
			User_id entityty = entity.get();
			System.out.println(entityty.getId());
			System.out.println(entityty.getPassword());
		} else
			System.out.println("ないです。。");

		entity = repos.findById(34343434);
		if (entity.isPresent()) {
			User_id entityty = entity.get();
			System.out.println(entityty.getId());
			System.out.println(entityty.getPassword());
		} else
			System.out.println("ないです。。");
	}

	@Ignore
	@Test
	public void test6() {
		ArrayList<User_id> list = repos.findAll();
		for (User_id e : list) {
			System.out.println(e.getId());
			System.out.println(e.getPassword());
		}

		ArrayList<Task_list> list2 = repos2.findAll();
		for (Task_list e : list2) {
			System.out.println(e.getNum());
			System.out.println(e.getContent());
			System.out.println(e.getName());
			System.out.println(e.getClient());
			System.out.println(e.getDeadline());
		}
	}

	@Ignore
	@Test
	public void test7() {
		User_id entity = new User_id();
		entity.setPassword("hogehoge");

		ArrayList<User_id> list = repos.findAll(entity);
		for (User_id e : list) {
			System.out.println(e.getId());
			System.out.println(e.getPassword());
		}
	}

	@Ignore
	@Test
	public void test8() {
		Optional<Task_list> entityOpt;
		entityOpt = repos2.multiFindById("swswswwswswwsws");

		if (entityOpt.isPresent()) {
			System.out.println("なんかとれた");
			Task_list e = entityOpt.get();
			System.out.println("--------Task_listEntityの内容--------");
			System.out.println(e.getNum());
			System.out.println(e.getContent());
			System.out.println(e.getName());
			System.out.println(e.getClient());
			System.out.println(e.getDeadline());
			System.out.println("--------TestEntityの内容--------");
			System.out.println(e.getTest().getNum());
			System.out.println(e.getTest().getNamename());
		} else {
			System.out.println("何も取れず");
		}
	}

	@Ignore
	@Test
	public void test9() {
		entityCreater.entity.Test test = new entityCreater.entity.Test();
		test.setNamename("popopo");
		test.setNum("swswswwswswwsws");

		tm.beginTransaction();
		repos3.save(test);
		tm.endTransaction();
	}

	@Test
	public void test10() {
		Optional<entityCreater.entity.Test> entityOpt;
		entityOpt = repos3.multiFindById("swswswwswswwsws");

		if (entityOpt.isPresent()) {
			System.out.println("なんかとれた");
			entityCreater.entity.Test e = entityOpt.get();
			System.out.println("--------TestEntityの内容--------");
			System.out.println(e.getNum());
			System.out.println(e.getNamename());
			System.out.println("--------Task_listEntityの内容--------");
			System.out.println(e.getTask_list().getNum());
			System.out.println(e.getTask_list().getContent());
			System.out.println(e.getTask_list().getName());
			System.out.println(e.getTask_list().getClient());
			System.out.println(e.getTask_list().getDeadline());
			System.out.println("--------Task_list2Entityの内容--------");

		} else {
			System.out.println("何も取れず");
		}
	}

}
