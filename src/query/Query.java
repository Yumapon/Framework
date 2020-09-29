package query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db_access.ConnectionPool;
import exception.NoColumnValueException;
import exception.NoDBAccessException;
import exception.NotBeginTransactionException;

public class Query {

	//ConnectionPoolを取得する
	ConnectionPool cp = ConnectionPool.getInstance();

	//TransactionID
	String transactionID;

	//Connection
	Connection conn;

	//Statement
	Statement stmt;

	//private Class<T> entityType;

	public Query() {
		//TransactionIDを取得する
		Thread currentThread = Thread.currentThread(); // 自分自身のスレッドを取得
		long threadID = currentThread.getId();
		this.transactionID = String.valueOf(threadID);
	}

	/**
	 * INSERT文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createInsertSql(QueryInfo qi) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("INSERT文の生成を開始します");

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Entityの情報を取得します");

		//SQL生成
		String column = "";
		String columnValue = "";
		for (String str : qi.getColumnNames()) {
			column += (str + " ,");
			columnValue += ("\"" + qi.getColumnValues().get(str) + "\",");
		}
		// 末尾から1文字分を削除
		column = column.substring(0, column.length() - 1);
		columnValue = columnValue.substring(0, columnValue.length() - 1);

		String sql = "INSERT INTO " + qi.getTableName() + " (" + column + ") values (" + columnValue + ");";

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("INSERT文の生成が完了しました:" + sql);
		return sql;
	}

	/**
	 * UPDATE文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createUpdateSql(QueryInfo qi) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("UPDATE文の生成を開始します");

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Entityの情報を取得します");

		//SQL生成
		String setValue = "";
		for (String column : qi.getColumnNames()) {
			if (column.equals(qi.getIdName()))
				continue;
			setValue += (column + " = \"" + qi.getColumnValues().get(column) + "\",");
		}
		// 末尾から1文字分を削除
		setValue = setValue.substring(0, setValue.length() - 1);

		String sql = "UPDATE " + qi.getTableName() + " SET " + setValue + " WHERE " + qi.getIdName() + " = \""
				+ qi.getIdValue() + "\";";

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("UPDATE文の生成が完了しました:" + sql);

		return sql;
	}

	/**
	 * SELECT文生成メソッド
	 * @param qi
	 * @return
	 */
	public String createSelectSql(QueryInfo qi) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("SELECT文の生成を開始します");

		//SQL生成
		String idValue = qi.getIdValue();
		String sql = "SELECT * FROM " + qi.getTableName();
		//QueryInfoに何もセットされていない場合、全件検索
		if (idValue == null && qi.getColumnValues().size() == 0)
			sql += " LIMIT 1000;";
		//QueryInfoにidValue以外のカラム値が設定されている場合、条件検索
		else {
			sql += " WHERE ";
			if (idValue != null) {
				sql += qi.getIdName() + " = \"" + idValue + "\";";
			} else {
				for (String columnName : qi.getColumnNames()) {
					if (qi.getColumnValues().get(columnName) == null || columnName.equals(qi.getIdName())) {
						continue;
					}
					sql += columnName + " = \"" + qi.getColumnValues().get(columnName) + "\" AND ";
				}
				sql = sql.substring(0, sql.length() - 5) + ";";
			}
		}
		return sql;
	}

	/**
	 * RECORD確認用メソッド
	 * @param qi
	 * @return
	 */
	public String createCheckRecordSql(QueryInfo qi) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("COUNTCHECK用SQLの生成を開始します");

		//SQL生成
		String sql = "SELECT COUNT(*) FROM " + qi.getTableName() + " WHERE " + qi.getIdName() + " = \""
				+ qi.getIdValue() + "\";";

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("COUNTCHECK用SQLの生成が完了しました:" + sql);

		return sql;
	}

	/**
	 * DELETE文生成メソッド
	 * @param qi
	 * @return
	 * @throws noColumnValueException
	 */
	public String createDeleteSql(QueryInfo qi) {
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("DELETE文の生成を開始します");

		String sql = null;

		//SQL生成
		String idValue = qi.getIdValue();
		boolean checkColumnValue = false;
		sql = "DELETE FROM " + qi.getTableName() + " WHERE ";
		if (idValue != null)
			sql += qi.getIdName() + " = \"" + idValue + "\";";
		else {
			for (String columnName : qi.getColumnNames()) {
				if (qi.getColumnValues().get(columnName) == null || columnName.equals(qi.getIdName())) {
					continue;
				}
				sql += columnName + " = \"" + qi.getColumnValues().get(columnName) + "\";";
				checkColumnValue = true;
			}
			if (!checkColumnValue) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//処理内容
				System.out.println("カラムに何も値が入っていません");
				throw new NoColumnValueException();
			}
		}

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("DELETE文の生成が完了しました:" + sql);

		return sql;
	}

	/**
	 * レコード数をチェックするメソッド
	 * @return
	 */
	public String createCheckCountSql(QueryInfo qi) {
		return "SELECT COUNT(*) FROM " + qi.getTableName() + ";";
	}

	/*SQL実行メソッド*/

	/**
	 * 更新メソッド
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql) {
		//更新レコード数
		int i = 0;

		//Connectionの取得
		try {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("コネクションを取得し、ステートメントを生成します");
			stmt = cp.getDBAccess(transactionID).getConnection().createStatement();
			i = stmt.executeUpdate(sql);
		} catch (NotBeginTransactionException e) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("トランザクションが開始されていません");
			e.printStackTrace();
		} catch (SQLException e) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("SQLエラーが発生しました");
			e.printStackTrace();
		}

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("SQLを実行しました");

		return i;
	}

	/**
	 * 参照メソッド
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		//Connectionの取得
		ResultSet rs = null;
		try {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("コネクションを取得し、ステートメントを生成します");
			stmt = cp.getDBAccess().getConnection().createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("SQLエラーが発生しました");
			e.printStackTrace();
		} catch (NoDBAccessException e) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("Connectionが見つかりません");
			e.printStackTrace();
		}

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("SQLを実行しました");

		return rs;
	}

}
