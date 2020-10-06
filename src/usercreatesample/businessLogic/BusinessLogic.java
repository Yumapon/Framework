package usercreatesample.businessLogic;

import java.util.ArrayList;

import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import usercreatesample.exception.falseLogionException;
import usercreatesample.exception.notExistException;

public interface BusinessLogic {

	//ログインロジック
	void login(User_id user_id) throws falseLogionException, notExistException;

	//ログアウトロジック
	//void logout();

	//Task保存ロジック
	void taskstorage(Task_list task_list);

	//一覧取得ロジック
	ArrayList<Task_list> getList();

	//Task削除ロジック
	void deleteTask(String[] taskNumList);

	//Task番号採番
	String taskNum();

	//user登録ロジック
	//void register(User_id user_id) throws DuplicationError;

}

