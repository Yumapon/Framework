package servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import annotation.ActionMethod;
import annotation.SessionScoped;
import container.ApplicationContainer;
import container.InstanceAndClassObjectforServlet;
import exception.IlligalMethodNameException;

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

		//Beanのライフタイムがセッションスコープであれば、セッションに格納する
		if(cams.getClazz().isAnnotationPresent(SessionScoped.class)) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("セッションにBeanを格納します。" + "Bean名：" + formName );
			HttpSession session = request.getSession(true);
			session.setAttribute(formName, form);
		}

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
				System.out.println(request.getParameter(paraName));

				//Fieldの方を取得
				Class<?> type = f.getType();
				String typeName = type.toString();

				if(typeName.contains("String")) {
					f.set(form, request.getParameter(paraName));
				}else if(typeName.contains("int")) {
					f.set(form, Integer.parseInt(request.getParameter(paraName)));
				}else if(typeName.contains("boolean")) {
					f.set(form, Boolean.parseBoolean(request.getParameter(paraName)));
				}else if(typeName.contains("byte")) {
					f.set(form, Byte.parseByte(request.getParameter(paraName)));
				}else if(typeName.contains("short")) {
					f.set(form, Short.parseShort(request.getParameter(paraName)));
				}else if(typeName.contains("long")) {
					f.set(form, Long.parseLong(request.getParameter(paraName)));
				}else if(typeName.contains("float")){
					f.set(form, Float.parseFloat(request.getParameter(paraName)));
				}else if(typeName.contains("double")) {
					f.set(form, Double.parseDouble(request.getParameter(paraName)));
				}else if(typeName.contains("java.util.Date")) {
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					java.util.Date date = sdFormat.parse(request.getParameter(paraName));
					f.set(form, date);
				}else if(typeName.contains("java.sql.Date")) {
					f.set(form, java.sql.Date.valueOf(request.getParameter(paraName)));
				}else if(typeName.contains("java.sql.Time")) {
					f.set(form, java.sql.Time.valueOf(request.getParameter(paraName)));
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
		//実行できたか確認するフラグ
		boolean invokeMethodCheck = false;

		for(Method m2 : methods) {
			if(m2.isAnnotationPresent(ActionMethod.class)) {
				ActionMethod aMethod = m2.getAnnotation(ActionMethod.class);
				if(!(aMethod.value().equals(actionMethodName))) {
					continue;
				}else {
					try {
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//処理内容
						System.out.print("実行するメソッドが見つかりました。実行します 実行するメソッド：");
						System.out.println(m2.getName());

						String urlAndMethodStr = (String) m2.invoke(cams2.getObj());

						//遷移先URLとメソッド（forword or redirect）を取得
						String[] urlAndMethod = urlAndMethodStr.split(" : ", 0);

						invokeMethodCheck = true;

						if(urlAndMethod[1].equals("forword")) {
							request.getRequestDispatcher(urlAndMethod[0]).forward(request, response);
						}else if(urlAndMethod[1].equals("redirect")) {
							response.sendRedirect(urlAndMethod[0]);
						}else {
							throw new IlligalMethodNameException();
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
		if(!invokeMethodCheck) {
			System.out.println("指定されたメソッドは見つかりませんでした");
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
