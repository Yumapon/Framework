package usercreatesample.actions;

import java.util.ArrayList;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.Login;
import annotation.Service;
import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import servlet.Model;
import servlet.Value;
import usercreatesample.beans.UserInfoEntity;
import usercreatesample.businessLogic.BusinessLogic;

public class LoginAction {

	@Service
	BusinessLogic bl1;

	@FormInjection
	UserInfoEntity userInfo;

	@ActionMethod("login")
	@Login
	public Model actionMethod3() {
		//Login処理
		User_id user_id = new User_id();
		user_id.setId(userInfo.getUser_id());
		user_id.setPassword(userInfo.getPassword());
		if(!bl1.login(user_id)) {
			/*
			 * ログイン失敗
			 * ログイン画面を再度表示
			 */
			Model model  = new Model();
			model.setNextPage("login.jsp");
			model.setLoginCheckerFlag(false);
			return model;
		}

		//task一覧を取得
		ArrayList<Task_list> taskList = bl1.getList();

		//taskListをセッションにセット
		Model model  = new Model();
		Value value = new Value();
		value.setName("tasklist");
		value.setObj(taskList);
		model.getSessionObj().add(value);
		model.setLoginCheckerFlag(true);

		//次画面をセット
		model.setNextPage("list.jsp");

		return model;
	}

}
