package servlet;

import java.util.ArrayList;

public class Model {

	//Type(Web,API,Windows)
	String type = "WEB";

	//遷移先
	String nextPage;

	//リダイレクトかフォワードか
	String method = "forword";

	//sessionに格納するオブジェクト
	ArrayList<Value> sessionObj = new ArrayList<>();

	//Requestに格納するオブジェクト
	ArrayList<Value> requestObj = new ArrayList<>();

	//JSONで返すオブジェクト
	Object jsonObj;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWeb() {
		this.type = "WEB";
	}

	public void setJSON() {
		this.type = "JSON";
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

	public ArrayList<Value> getSessionObj() {
		return sessionObj;
	}

	public void setSessionObj(ArrayList<Value> sessionObj) {
		this.sessionObj = sessionObj;
	}

	public ArrayList<Value> getRequestObj() {
		return requestObj;
	}

	public void setRequestObj(ArrayList<Value> requestObj) {
		this.requestObj = requestObj;
	}

	public Object getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(Object jsonObj) {
		this.jsonObj = jsonObj;
	}

}
