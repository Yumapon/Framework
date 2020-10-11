package query;

public class QueryFactory {

	Query query;

	public Query getQueryClass(String dbType){

		if(dbType.equalsIgnoreCase("mysql") || dbType == null)
			query = new MysqlQuery();

		else if(dbType.equalsIgnoreCase("oracledb"))
			query = new OracledbQuery();

		return query;
	}

}
