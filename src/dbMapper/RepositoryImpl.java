package dbMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
	 * コンストラクタ基本引数なしで使う
	 * @param t
	 */
	@SuppressWarnings("unchecked")
	public RepositoryImpl(T... t) {
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
				System.out.println("@Tableを発見 テーブル名を取得します");
				this.tableName = entity.getClass().getAnnotation(Table.class).value();
			} else {
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
						setIdName(f.getName());
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
			Field primaryIdField = entity.getClass().getDeclaredField(getIdName());
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
		System.out.println("Entityがすでに格納されているか確認する");
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
			System.out.println("Entityは格納されていないため、新規登録を行います");

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

	@Override
	public T findById(ID primaryKey) {
		return null;
		//SQL文を発行
		//String sql = query.createSelectSql(entityType, primaryKey);

		//SQLを実行
		//ArrayList<T> result = query.selectExecute(sql);

		//resultから値を取得
		//T entity = result.get(0);

		//return entity;
	}

	@Override
	public ArrayList<T> findAll() {
		return null;
		//SQL文を発行
		//String sql = query.createSelectSql(entityType);

		//SQLを実行
		//ArrayList<T> result = query.selectExecute(sql);

		//return result;
	}

	@Override
	public long count() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void delete(T entity) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean existsById(ID primaryKey) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
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

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public ArrayList<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(ArrayList<String> columnNames) {
		this.columnNames = columnNames;
	}

	public Class<T> getEntityType() {
		return entityType;
	}

	public void setEntityType(Class<T> entityType) {
		this.entityType = entityType;
	}

}
