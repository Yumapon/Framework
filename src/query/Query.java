package query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db_access.ConnectionPool;
import exception.notBeginTransactionException;

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

	public Query(/*@SuppressWarnings("unchecked") T... t*/) {
		//TransactionIDを取得する
		Thread currentThread = Thread.currentThread(); // 自分自身のスレッドを取得
		long threadID = currentThread.getId();
		this.transactionID = String.valueOf(threadID);

		/*
		@SuppressWarnings("unchecked")
		Class<T> entityType = (Class<T>) t.getClass().getComponentType();
		this.entityType = entityType;

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		System.out.println(entityType);

		//Entity情報を取得
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Entityクラス情報の取得を開始します");
		try {
			T entity = (T) this.entityType.getDeclaredConstructor().newInstance();

			//Entityのテーブル名を取得
			if (entity == null)
				System.out.println("nullです");
			else
				if(entity.getClass().isAnnotationPresent(Table.class)) {
					System.out.println("@Tableを発見 テーブル名を取得します");
					this.tableName = entity.getClass().getAnnotation(Table.class).value();
				}else {
					System.out.println(entity.getClass().getName());
					System.out.println("@Tableが付与されていません");
				}

			//EntityからPrimaryKeyのカラム名を取り出す
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("Entityクラスのカラム名を取得します");
			for (Field f : entity.getClass().getDeclaredFields()) {
				//カラム名の取得
				columnNames.add(f.getName());
				//@idが付与されているメンバを探索
				if (f.isAnnotationPresent(id.class)) {
					try {
						//ログ発生箇所
						System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
						//処理内容
						System.out.println("EntityクラスのPrimaryKey名を取得します");
						//Field名を取得する
						this.idName = f.getName();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Entityクラス情報の取得が完了しました");
		*/
	}

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

		String sql = "UPDATE " + qi.getTableName() + " SET " + setValue + " WHERE " + qi.getIdName() + " = \"" + qi.getColumnValues().get(qi.getIdName()) + "\";";

		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("UPDATE文の生成が完了しました:" + sql);

		return sql;
	}

	/*
	public String createSelectSql() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String createSelectSql() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	*/

	/*SQL実行メソッド*/

	//更新メソッド
	public int executeUpdate(String sql) {
		//更新レコード数
		int i = 0;

		//Connectionの取得
		try {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//例外内容
			System.out.println("コネクションを取得し、ステートメントを生成する");
			stmt = cp.getDBAccess(transactionID).getConnection().createStatement();
			i = stmt.executeUpdate(sql);
		} catch (notBeginTransactionException e) {
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

	/*
	public ArrayList<T> selectExecute(String sql) {
		return null;
	}
	*/

}
