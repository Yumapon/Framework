package container;

//サーブレットで使用するオブジェクト
public class InstanceAndClassObjectforServlet {

	//インスタンス
	Object obj;

	//クラス
	Class<?> clazz;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
