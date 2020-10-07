package usercreatesample.actions;

import java.util.ArrayList;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.LoginCheck;
import annotation.Service;
import entityCreater.entity.Task_list;
import servlet.Model;
import servlet.Value;
import usercreatesample.beans.CreateTaskEntity;
import usercreatesample.beans.DeleteTaskEntity;
import usercreatesample.businessLogic.BusinessLogic;

/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */
@LoginCheck
public class Action {

	@Service
	BusinessLogic bl1;

	@FormInjection
	CreateTaskEntity createTask;

	@FormInjection
	DeleteTaskEntity deleteTask;

	/*
	@FormInjection
	TestB testb;
	*/

	@ActionMethod("create")
	public Model actionMethod1() {
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");
		Model model = new Model();
		model.setNextPage("createcheck.jsp");

		return model;
	}

	@ActionMethod("createCheck")
	public Model actionMethod2() {
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");

		//Entityの生成
		Task_list task = new Task_list();
		//TaskNumの生成
		task.setNum(bl1.taskNum());
		task.setDeadline(createTask.getDeadline());
		task.setName(createTask.getTaskName());
		task.setContent(createTask.getContent());
		task.setClient(createTask.getClient());

		bl1.taskstorage(task);

		//task一覧を取得
		ArrayList<Task_list> taskList = bl1.getList();

		//taskListをセッションにセット
		Model model = new Model();
		Value value = new Value();
		value.setName("tasklist");
		value.setObj(taskList);
		model.getSessionObj().add(value);
		model.setNextPage("list.jsp");

		return model;
	}

	@ActionMethod("delete")
	public Model actionMethod3() {
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");

		//taskの削除
		bl1.deleteTask(deleteTask.getTaskNumList());

		//task一覧を取得
		ArrayList<Task_list> taskList = bl1.getList();

		//taskListをセッションにセット
		Model model = new Model();
		Value value = new Value();
		value.setName("tasklist");
		value.setObj(taskList);
		model.getSessionObj().add(value);
		model.setNextPage("list.jsp");

		return model;
	}

	@ActionMethod("jsonTest")
	public Model actionMethod4() {
		//task一覧を取得
		ArrayList<Task_list> taskList = bl1.getList();

		//taskListをJSONセット
		Model model = new Model();
		model.setJSON();
		model.setJsonObj(taskList);

		return model;

	}

}
