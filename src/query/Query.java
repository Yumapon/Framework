package query;

import java.sql.ResultSet;

public interface Query {

	/**
	 * INSERT文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createInsertSql(QueryInfo qi);

	/**
	 * UPDATE文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createUpdateSql(QueryInfo qi);

	/**
	 * SELECT文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createSelectSql(QueryInfo qi);

	/**
	 * RECORD確認用メソッド
	 * @param qi
	 * @return
	 */
	public String createCheckRecordSql(QueryInfo qi);

	/**
	 * DELETE文生成メソッド
	 * @param qi
	 * @return
	 * @throws noColumnValueException
	 */
	public String createDeleteSql(QueryInfo qi);

	/**
	 * レコード数をチェックするメソッド
	 * @return
	 */
	public String createCheckCountSql(QueryInfo qi);

	/**
	 * 更新メソッド
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql);

	/**
	 * 参照メソッド
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql);

}
