package servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actions.ActionMethod;
import container.ApplicationContainer;
import container.InstanceAndClassObjectforServlet;

/**
 * Servlet implementation class HogeHogeServlet
 */
@WebServlet("/HogeHogeServlet")
public class HogeHogeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//コンテナ生成
	ApplicationContainer ac = new ApplicationContainer();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HogeHogeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//バインディングするBean名(Form名)を取得
		String formName = request.getParameter("formName");
		//Actionクラスを取得
		String actionName= request.getParameter("actionName");
		//実行するメソッド名を取得
		String actionMethodName = request.getParameter("actionMethodName");

		//Servlet用Beanクラスを生成
		InstanceAndClassObjectforServlet cams = ac.getCAMS(formName);

		//Beanのインスタンス取得
		Object form = cams.getObj();

		//RequestのParameterName格納用配列
		ArrayList<String> paraNameList = new ArrayList<>();

		//Requestから送られてきたParameterNameを全て取得する
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			paraNameList.add(name);
		}

		//formName.actionName.actionMethodは必要ないので削除
		paraNameList.remove(paraNameList.indexOf("formName"));
		paraNameList.remove(paraNameList.indexOf("actionName"));
		paraNameList.remove(paraNameList.indexOf("actionMethodName"));
		paraNameList.remove(paraNameList.indexOf("button"));

		//setメソッドを実行してインスタンスに値を格納
		/*
		String paraName;
		Method m = null;
		for (int i = 0; i < (paraNameList.size() - 1); i++) {
			paraName = paraNameList.get(i);
			String setMethodName = "set" + paraName.substring(0, 1).toUpperCase() + paraName.substring(1).toLowerCase();

			try {
				//setMethodを取得
				loop: for (Method method : cams.getClazz().getMethods()) {
				      for (Class<?> p : method.getParameterTypes()) {
				    	  if(setMethodName.equals(method.getName())) {
				    		  if(p.getName().contains("String")) {
				    			  m = cams.getClazz().getMethod(setMethodName, String.class);
				    			  //Setメソッドは引数が絶対に１つなハズなので、これでOK
				    			  m.invoke(form, request.getParameter(paraName));
				    			  break loop;
				    		  }else if(p.getName().contains("int")) {
				    			  m = cams.getClazz().getMethod(setMethodName, int.class);
				    			  m.invoke(form, Integer.parseInt(request.getParameter(paraName)));
				    			  break loop;
				    		  }
				    	  }
				      }
				    }


			} catch (NoSuchMethodException | SecurityException e1) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("システムエラー：セットメソッドが見つかりません。");
				e1.printStackTrace();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
		*/

		//Fieldにリクエストの値をセット
		String paraName;
		for(int i = 0; i < paraNameList.size(); i++) {
			//リクエストパラメータの属性値を取得
			paraName = paraNameList.get(i);

			try {
				//リクエストパラメータと合致するFieldを取得
				Field f = cams.getClazz().getDeclaredField(paraName);

				//Fieldに値をセット
				f.setAccessible(true);//無理やり書き込む。
				f.set(form, request.getParameter(paraName));
				f.setAccessible(false);

			} catch (NoSuchFieldException | SecurityException e1) {

				e1.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException e1) {

				e1.printStackTrace();
			}
		}


		//上記まででインスタンスの生成は完了しているので、ここからActionを作成していく！

		//Servlet用ActionClassを取得
		InstanceAndClassObjectforServlet cams2 = ac.getAction(actionName);

		//ActionClassの実行したいMethodを取得し、実行
		Class<?> clazz = cams2.getClazz();
		Method[] methods = clazz.getMethods();

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//例外内容
		System.out.println("実行するメソッドを探しています。");
		for(Method m2 : methods) {
			if(m2.isAnnotationPresent(ActionMethod.class)) {
				ActionMethod aMethod = m2.getAnnotation(ActionMethod.class);
				if(!(aMethod.value().equals(actionMethodName))) {
					continue;
				}else {
					try {
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//例外内容
						System.out.println("実行するメソッドが見つかりました。実行します");
						System.out.println(m2.getName());
						m2.invoke(cams2.getObj());

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
