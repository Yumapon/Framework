package container;

//データベースの設定ファイル
public class DBConfig {

	//ドライバ名
	String driver;

	//DBのURL
	String url;

	//DBのユーザ
	String user;

	//パスワード
	String password;

	//スキーマ名
	String schema;

	//コネクションの確立数
	int numberOfAccess;

	//DBName
	String dbName;

	//DBType
	String dbType;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public int getNumberOfAccess() {
		return numberOfAccess;
	}

	public void setNumberOfAccess(int numberOfAccess) {
		this.numberOfAccess = numberOfAccess;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
