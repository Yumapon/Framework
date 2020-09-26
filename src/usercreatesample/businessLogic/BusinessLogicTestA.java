package usercreatesample.businessLogic;

import annotation.Transactional;
import dbMapper.Repository;
import dbMapper.RepositoryImpl;
import entityCreater.entity.User_id;
import usercreatesample.beans.TestA;

/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */

@Transactional
public class BusinessLogicTestA implements BusinessLogic{

	Repository<User_id, Integer> repos = new RepositoryImpl<>();

	@Override
	public void testMethod() {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
	}

	@Override
	public void testMethod(TestA testA) {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
		System.out.println("testA.str1 =" + testA.getStr1() + "：testA.str2 =" + testA.getStr2());
	}

	@Override
	//@Transactional
	public boolean login(String pass) {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
		return false;
	}

	@Override
	//@Transactional
	public void login(String a, String b) {
		User_id entity = new User_id();
		entity.setId(1010110);
		entity.setPassword("passwordddd");
		repos.save(entity);
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
	}

}
