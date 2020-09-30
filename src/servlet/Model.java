package servlet;

import java.util.ArrayList;

public class Model {

	//Type(Web,API,Windows)
	String type = "web";

	//遷移先
	String nextPage;

	//リダイレクトかフォワードか
	String method = "forword";

	//sessionに格納するオブジェクト
	ArrayList<Object> sessionObj = new ArrayList<>();

	//Requestに格納するオブジェクト
	ArrayList<Object> requestObj = new ArrayList<>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ArrayList<Object> getSessionObj() {
		return sessionObj;
	}

	public void setSessionObj(ArrayList<Object> sessionObj) {
		this.sessionObj = sessionObj;
	}

	public ArrayList<Object> getRequestObj() {
		return requestObj;
	}

	public void setRequestObj(ArrayList<Object> requestObj) {
		this.requestObj = requestObj;
	}


}
