package dbMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import annotation.Table;
import annotation.id;
import query.Query;
import query.QueryInfo;

public class RepositoryImpl<T, ID> implements Repository<T, ID> {

	//Queryクラス
	Query query = new Query();
	//Query用オブジェクト
	QueryInfo qi = new QueryInfo();

	//Entity情報
	private String tableName;
	private String idName;
	private ArrayList<String> columnNames = new ArrayList<>();
	private Class<T> entityType;

	/**
	 * コンストラクタ
	 * QueryInfoにEntityの情報を格納する
	 * @param t
	 */
	@SuppressWarnings("unchecked")
	public RepositoryImpl(T... t) {
		//EntityのTypeを取得する
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
			else if (entity.getClass().isAnnotationPresent(Table.class)) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//処理内容
				System.out.println("@Tableを発見 テーブル名を取得します");
				this.tableName = entity.getClass().getAnnotation(Table.class).value();
			} else {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//処理内容
				System.out.print(entity.getClass().getName());
				System.out.println("@Tableが付与されていません、付与してください");
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
						idName = f.getName();
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

		//Query用オブジェクトにEntity情報を格納
		qi.setTableName(tableName);
		qi.setIdName(idName);
		qi.setColumnNames(columnNames);
	}

	/**
	 * Entity格納メソッド
	 * @param T entity
	 * @return 更新レコード数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void save(T entity) {
		//QueryInfoの初期化
		qi.clearQueryInfo();

		//PrimaryKeyの値
		ID idValue = null;

		try {
			//entityのカラム値を取得
			for (Field f : entity.getClass().getDeclaredFields()) {
				try {
					f.setAccessible(true);
					qi.getColumnValues().put(f.getName(), f.get(entity).toString());
					f.setAccessible(false);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			//EntityからPrimaryKeyの値を取り出す
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("EntityクラスのPrimaryKeyの値を取得する");

			Field primaryIdField = entity.getClass().getDeclaredField(idName);
			primaryIdField.setAccessible(true);
			idValue = (ID) primaryIdField.get(entity);
			primaryIdField.setAccessible(false);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		//すでにDBに格納されているEntityかどうかを確認する。
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("Entityがすでに格納されているか確認します");
		if (existsById(idValue)) {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("Entityがすでに格納されているため、上書きを行います");

			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("SQLを発行します");
			//SQL文を発行
			String sql = query.createUpdateSql(qi);

			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("SQLを実行します");
			//上書き処理を行う
			query.executeUpdate(sql);
		} else {
			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("Entityは格納されていないため、DBへ新規登録を行います");

			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("SQLを発行します");
			//SQL文を発行
			String sql = query.createInsertSql(qi);

			//ログ発生箇所
			System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
			//処理内容
			System.out.println("SQLを実行します");
			//登録処理を行う
			query.executeUpdate(sql);
		}
	}

	/**
	 * 主キーでEntityを探索するメソッド
	 */
	//TODO
	@Override
	public Optional<T> findById(ID primaryKey) {
		//QueryInfoの初期化
		qi.clearQueryInfo();

		//return値
		Optional<T> entityOpt = null;
		T entity = null;

		//SQL文を発行
		qi.getColumnValues().put(idName, primaryKey.toString());
		String sql = query.createSelectSql(qi);

		System.out.println(sql);

		//SQLを実行
		ResultSet result = query.executeQuery(sql);

		//リストにデータを追加する
		List<Object> list = new ArrayList<>();
		try {
			while (result.next()) {
			    list.add(result.getObject(idName));
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		//リストがからの場合、nullを返却する
		if(list.isEmpty()){
			entityOpt = Optional.empty();
		    return entityOpt;
		}

		//resultから値を取得
		try {
			entity = (T)this.entityType.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		Field f;
		try {
			//ResultSetのカーソルを先頭に持ってくる
			result.beforeFirst();
			result.next();
			for (String column : columnNames) {
				Object columnValue = result.getObject(column);
				f = entity.getClass().getDeclaredField(column);
				f.setAccessible(true);
				f.set(entity, columnValue);
				f.setAccessible(false);
			}

			//Entityをオプショナル型に変換
			entityOpt = Optional.of(entity);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return entityOpt;
	}

	@Override
	public ArrayList<T> findAll() {
		//QueryInfoの初期化
		qi.clearQueryInfo();

		//SQL文を発行
		//String sql = query.createSelectSql(entityType);

		//SQLを実行
		//ArrayList<T> result = query.selectExecute(sql);

		//return result;
		return null;
	}

	@Override
	public int count() {
		//QueryInfoの初期化
		qi.clearQueryInfo();

		//SQL文の生成
		String sql = query.createCheckCountSql(qi);

		//SQL文の実行
		ResultSet rs = query.executeQuery(sql);

		//ResultSetからレコード数を受け取る
		int i = 0;
		try {
			rs.next();
			i = rs.getInt("COUNT(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
	}

	/**
	 * Entity削除用メソッド
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(T entity) {
		//QueryInfoの初期化
		qi.clearQueryInfo();

		//PrimaryKeyの値
		ID idValue;

		//EntityからPrimaryKeyの値を取り出す
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("EntityクラスのPrimaryKeyの値を取得します");
		Field primaryIdField;
		try {
			primaryIdField = entity.getClass().getDeclaredField(idName);
			primaryIdField.setAccessible(true);
			idValue = (ID) primaryIdField.get(entity);
			primaryIdField.setAccessible(false);

			//主キーが格納されていない場合
			if (idValue == null) {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//処理内容
				System.out.println("主キーに何もパラメータがセットされていません、カラムの値を取得します");
				//entityのカラム値を取得
				for (Field f : entity.getClass().getDeclaredFields()) {
					try {
						f.setAccessible(true);
						if (f.get(entity) == null || f.getName().equals(idName))
							continue;
						qi.getColumnValues().put(f.getName(), f.get(entity).toString());
						f.setAccessible(false);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			} else {
				//ログ発生箇所
				System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
				//処理内容
				System.out.println("主キーにパラメータがセットされています");
				qi.getColumnValues().put(idName, idValue.toString());
			}
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		//SQLの生成
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println("SQLを生成します");
		String sql = query.createDeleteSql(qi);

		//SQLの実行
		int i = query.executeUpdate(sql);
		//ログ発生箇所
		System.out.print(Thread.currentThread().getStackTrace()[1].getClassName() + ":");
		//処理内容
		System.out.println(i + "件更新しました");
	}

	/**
	 *主キーでEntityが格納されているか確認するメソッド
	 *@param ID
	 *@return boolean
	 */
	@Override
	public boolean existsById(ID primaryKey) {
		//SQLを発行する
		qi.getColumnValues().put(idName, primaryKey.toString());
		String sql = query.createCheckRecordSql(qi);

		//SQLの実行
		ResultSet rs = query.executeQuery(sql);

		//ResultSetからレコード数を受け取る
		int i = 0;
		try {
			rs.next();
			i = rs.getInt("COUNT(*)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (i <= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Optional<T> multiFindById(ID primaryKey) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Iterable<T> multifindAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
