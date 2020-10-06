package usercreatesample.actions;

import java.util.ArrayList;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.Service;
import entityCreater.entity.Task_list;
import entityCreater.entity.User_id;
import servlet.Model;
import servlet.Value;
import usercreatesample.beans.UserInfoEntity;
import usercreatesample.businessLogic.BusinessLogic;
import usercreatesample.exception.falseLogionException;
import usercreatesample.exception.notExistException;

public class LoginAction {

	@Service
	BusinessLogic bl1;

	@FormInjection
	UserInfoEntity userInfo;

	@ActionMethod("login")
	public Model actionMethod3() {
		//Login処理
		User_id user_id = new User_id();
		user_id.setId(userInfo.getUser_id());
		user_id.setPassword(userInfo.getPassword());
		try {
			bl1.login(user_id);
		} catch (falseLogionException | notExistException e) {
			e.printStackTrace();
		}

		//task一覧を取得
		ArrayList<Task_list> taskList = bl1.getList();

		//taskListをセッションにセット
		Model model  = new Model();
		Value value = new Value();
		value.setName("tasklist");
		value.setObj(taskList);
		model.getSessionObj().add(value);

		//次画面をセット
		model.setNextPage("list.jsp");

		return model;
	}

}
