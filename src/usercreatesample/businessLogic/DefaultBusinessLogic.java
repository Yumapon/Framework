package usercreatesample.businessLogic;

import java.util.ArrayList;
import java.util.Calendar;

import annotation.Transactional;
import dbMapper.Repository;
import dbMapper.RepositoryImpl;
import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import usercreatesample.exception.falseLogionException;
import usercreatesample.exception.notExistException;

public class DefaultBusinessLogic implements BusinessLogic {

	Repository<Task_list, String> Task_listRepos = new RepositoryImpl<>();

	@Override
	//ログイン処理の実装(ログイン失敗、該当のユーザIDが存在しない場合は例外処理発生)
	public void login(User_id user_id) throws falseLogionException, notExistException {

		//確認用
		System.out.println("ログイン機能スタート");

		//空実装

		System.out.println("ログインに成功しました。");
	}


	//タスクの格納機能
	@Override
	@Transactional
	public void taskstorage(Task_list task) {

		System.out.println(task.getName());
		System.out.println(task.getDeadline());
		System.out.println(task.getClient());
		System.out.println(task.getContent());
		System.out.println(task.getNum());

		//確認用
		System.out.println("タスク格納機能スタート");
		Task_listRepos.save(task);

	}

	//task一覧取得
	@Override
	public ArrayList<Task_list> getList() {
		ArrayList<Task_list> list  = Task_listRepos.findAll();
		return list;
	}

	//Task削除機能
	@Override
	@Transactional
	public void deleteTask(String[] taskNumList) {

		System.out.println("タスクを削除します。");
		Task_list task = new Task_list();
		for(String num : taskNumList) {
			task.setNum(num);
			Task_listRepos.delete(task);
		}
		System.out.println("タスクの削除が完了しました。");

	}

	/*
	@Override
	//user登録機能
	public void register(UserContext userContext) throws DuplicationError {

		//DAO呼び出し
		System.out.println("ユーザを登録します。");
		dao.registerDAO(userContext);
		System.out.println("ユーザの登録が完了しました。");

	}

	@Override
	public void logout() {
		//特にビジネスロジック側でやることはないので空実装

	}
	*/


	//タスク番号採番の実装
	@Override
	public String taskNum() {

		//確認用
		System.out.println("タスク番号採番機能スタート");

		//一意な番号は、ユーザID+時間分秒ミリ秒の文字列とする
		Calendar cTime = Calendar.getInstance();
		//各要素の取得
		int hour = cTime.get(Calendar.HOUR_OF_DAY);
		int minute =  cTime.get(Calendar.MINUTE);
		int second =  cTime.get(Calendar.SECOND);
		int millisecond =  cTime.get(Calendar.MILLISECOND);

		String taskNum = "" + hour + minute + second + millisecond;

		//テスト用
		System.out.println("タスク番号は：" + taskNum);

		return taskNum;
	}
}