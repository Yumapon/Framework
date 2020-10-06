package servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import annotation.ActionMethod;
import annotation.RequestScoped;
import annotation.SessionScoped;
import container.ApplicationContainer;
import container.ApplicationContainerImplemention;
import exception.IlligalMethodNameException;

/**
 * Servlet implementation class HogeHogeServlet
 */
@WebServlet("/HogeHogeServlet")
public class HogeHogeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//コンテナ生成
	ApplicationContainer ac = new ApplicationContainerImplemention();

	//Action格納用Object
	Object actionObj;

	//RequestのParameterName格納用配列
	ArrayList<String> paraNameList = new ArrayList<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HogeHogeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		//バインディングするBean名(Form名)を取得 formはからでもOK
		Optional<String> formNameOpt = Optional.ofNullable(request.getParameter("formName"));
		//Actionクラス名を取得
		String actionName = request.getParameter("actionName");
		//実行するメソッド名を取得
		String actionMethodName = request.getParameter("actionMethodName");

		//Requestから送られてきたParameterNameを全て取得する
		paraNameList.clear();
		Enumeration<?> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			paraNameList.add(name);
		}

		//formName.actionName.actionMethodは必要ないので削除
		//formNameは格納されていない可能性があるので、格納されている場合のみ削除
		int index = paraNameList.indexOf("formName");
		if (index != (-1)) {
			paraNameList.remove(index);
		}
		paraNameList.remove(paraNameList.indexOf("actionName"));
		paraNameList.remove(paraNameList.indexOf("actionMethodName"));
		paraNameList.remove(paraNameList.indexOf("button"));

		//Actionクラスを取得
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Actionクラスを取得します。");
		actionObj = ac.getAction(actionName);

		//Actionクラス内のFormBeanクラスにリクエストの値をセット(formNameが指定されている場合のみ)
		formNameOpt.ifPresent(formName -> setFormBean(formName, request));

		//ActionClass内の指定されたMethodを取得し、実行
		Class<?> clazz = actionObj.getClass();
		Method[] methods = clazz.getMethods();

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("実行するメソッドを探しています。");

		for (Method m2 : methods) {
			if (m2.isAnnotationPresent(ActionMethod.class)) {
				ActionMethod aMethod = m2.getAnnotation(ActionMethod.class);
				if (!(aMethod.value().equals(actionMethodName))) {
					continue;
				} else {
					try {
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//処理内容
						System.out.print("実行するメソッドが見つかりました。実行します 実行するメソッド：");
						System.out.println(m2.getName());

						//遷移先URLとメソッド（forword or redirect）などを取得
						Model model = (Model) m2.invoke(actionObj);

						//Sessionスコープが指定されているものを格納する
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//処理内容
						System.out.println("ModelにSessionとして格納されているものをsessionに格納します");
						ArrayList<Value> list = model.getSessionObj();
						int size = list.size();
						HttpSession session = request.getSession(true);
						Value value;
						for (int i = 0; i < size; i++) {
							value = list.get(i);
							session.setAttribute(value.getName(), value.getObj());
						}

						//Requestスコープが指定されているものを格納する
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//処理内容
						System.out.println("ModelにRequestとして格納されているものをRequestに格納します");
						ArrayList<Value> list2 = model.getRequestObj();
						int size2 = list2.size();
						Value value2;
						for (int i = 0; i < size2; i++) {
							value2 = list2.get(i);
							request.setAttribute(value2.getName(), value2.getObj());
						}

						//WEBの場合
						if (model.getType().equalsIgnoreCase("WEB")) {

							//遷移先・遷移方法を判断し、遷移する
							if (model.getMethod().equals("forword")) {
								request.getRequestDispatcher(model.getNextPage()).forward(request, response);
							} else if (model.getMethod().equals("redirect")) {
								response.sendRedirect(model.getNextPage());
							} else {
								throw new IlligalMethodNameException();
							}
						}

						//APIの場合
						if (model.getType().equalsIgnoreCase("JSON")) {
							//JSONにするデータを取得
							Object jsonObj = model.getJsonObj();

							//JavaObjectからJSONに変換
							ObjectMapper mapper = new ObjectMapper();
							String json = mapper.writeValueAsString(jsonObj);
							//JSONの出力
							response.getWriter().write(json);
						}

						//GUIの場合
						if (model.getType().equalsIgnoreCase("GUI")) {

						} else {
							//ログ発生箇所
							System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
							//処理内容
							System.out.print("WEB,API,GUIのいずれかをModelクラスのTypeFieldにセットしてください");
						}

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {

						e1.printStackTrace();
					} catch (IlligalMethodNameException e1) {
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//例外内容
						System.out.print("メソッド名が間違っています メソッド名はforwordもしくはredirectで指定してください");
						e1.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Beanにリクエストパラメータをセットするメソッド
	 */
	private void setFormBean(String formName, HttpServletRequest request) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println(formName + "に値をセットします。");

		//リクエストの値をセットするBeanのインスタンスを取得
		//Actionクラス
		Class<?> clazz = actionObj.getClass();

		System.out.println("------ActionClassのField------");
		for (Field f1 : clazz.getDeclaredFields()) {
			System.out.println(f1.getName());
		}
		System.out.println("------------------------------");

		//Actionクラス内のFormクラス
		Field formField = null;
		try {
			formField = clazz.getDeclaredField(formName);
		} catch (NoSuchFieldException | SecurityException e2) {
			e2.printStackTrace();
		}

		Object form = null;
		try {
			formField.setAccessible(true);
			form = formField.get(actionObj);
			formField.setAccessible(false);

			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("InstanceScopeを確認しています");

			//Sessionへの格納
			if (form.getClass().isAnnotationPresent(SessionScoped.class)) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Entityに@SessionScopedが付与されているのを確認しました");
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Sessionにインスタンスがすでに格納されているか確認します。" + "インスタンス名：" + form.getClass().getName());

				//すでにSessionに入っている場合
				HttpSession session = request.getSession(true);
				Object obj = session.getAttribute(form.getClass().getName());
				if (obj != null) {
					//ログ発生箇所
					System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
					//例外内容
					System.out.println("Sessionからインスタンスを取得します。" + "インスタンス名：" + form.getClass().getName());
					formField.setAccessible(true);
					formField.set(actionObj, obj);
					formField.setAccessible(false);
					return;
				}
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Sessionにインスタンスを格納します。" + "インスタンス名：" + form.getClass().getName());
				obj = form;
				session.setAttribute(form.getClass().getName(), obj);
			}

			//Requestへの格納
			if (form.getClass().isAnnotationPresent(RequestScoped.class)) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Entityに@RequestScopedが付与されているのを確認しました");
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Requestにインスタンスを格納します。" + "インスタンス名：" + form.getClass().getName());
				Object obj = form;
				request.setAttribute(form.getClass().getName(), obj);
			}

			/*
			//Cookieの格納
			for(Field f1 : field.getClass().getDeclaredFields()) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//例外内容
				System.out.println("Cookieにインスタンスを格納します。" + "フィールド名：" + f1.getName());
				if(f1.isAnnotationPresent(CookieScoped.class)) {

					f.setAccessible(true);
					Object obj = f1.get(field);
					f.setAccessible(false);

					Cookie cookie = new Cookie(f1.getName(), obj.toString());
					response.addCookie(cookie);
				}
			}
			*/
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		//確認用
		System.out.println("-------FormClassのField-------");
		for (Field f : form.getClass().getDeclaredFields()) {
			System.out.println(f.getName());
		}
		System.out.println("------------------------------");

		//Fieldにリクエストの値をセット
		System.out.println("-------リクエストパラメータ-------");
		String paraName;
		for (int i = 0; i < paraNameList.size(); i++) {
			//リクエストパラメータの属性値を取得
			paraName = paraNameList.get(i);
			System.out.print(paraName + " : ");

			try {
				//リクエストパラメータと合致するFieldを取得
				Field f = form.getClass().getDeclaredField(paraName);

				//Fieldに値をセット
				f.setAccessible(true);//無理やり書き込む。
				System.out.println(request.getParameter(paraName));

				//Fieldの方を取得
				Class<?> type = f.getType();
				String typeName = type.toString();
				System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				System.out.println(typeName);
				System.out.println(paraName);
				System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★");

				if (typeName.contains("[Ljava.lang.String")) {
					f.set(form, request.getParameterValues(paraName));
				} else if (typeName.contains("String")) {
					f.set(form, request.getParameter(paraName));
					break;
				} else if (typeName.contains("int")) {
					f.set(form, Integer.parseInt(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("boolean")) {
					f.set(form, Boolean.parseBoolean(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("byte")) {
					f.set(form, Byte.parseByte(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("short")) {
					f.set(form, Short.parseShort(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("long")) {
					f.set(form, Long.parseLong(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("float")) {
					f.set(form, Float.parseFloat(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("double")) {
					f.set(form, Double.parseDouble(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("java.util.Date")) {
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					java.util.Date date = sdFormat.parse(request.getParameter(paraName));
					f.set(form, date);
					break;
				} else if (typeName.contains("java.sql.Date")) {
					f.set(form, java.sql.Date.valueOf(request.getParameter(paraName)));
					break;
				} else if (typeName.contains("java.sql.Time")) {
					f.set(form, java.sql.Time.valueOf(request.getParameter(paraName)));
					break;
				}

				f.setAccessible(false);

			} catch (NoSuchFieldException | SecurityException e1) {

				e1.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException e1) {

				e1.printStackTrace();
			} catch (ParseException e1) {
				System.out.println("システムエラー：Date型への変換に失敗しました");
				e1.printStackTrace();
			}
		}
		System.out.println("------------------------------");
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println(formName + "への値のセットが完了しました。");
	}

}

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
		e1.printStackTrace();
	}
}
*/

//Beanのライフタイムがセッションスコープであれば、セッションに格納する
/*
if (iaco.getClazz().isAnnotationPresent(SessionScoped.class)) {
	//ログ発生箇所
	System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
	//例外内容
	System.out.println("セッションにBeanを格納します。" + "Bean名：" + formName);
	HttpSession session = request.getSession(true);
	session.setAttribute(formName, form);
}
*/
