package dbMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Optional;

import annotation.id;
import query.Query;
import query.SqlCreater;

public class RepositoryImpl<T, ID> implements Repository<T, ID> {

	//SQL生成クラス
	SqlCreater<T> sc = new SqlCreater<>();
	//SQL実行クラス
	Query<T> query = new Query<>();

	//Entity情報
	private String idName;
	private ArrayList<String> columnNames;

	/**
	 * コンストラクタ
	 * @param t
	 */
	public RepositoryImpl(@SuppressWarnings("unchecked") T... t) {
		@SuppressWarnings("unchecked")
		Class<T> entity = (Class<T>) t.getClass().getComponentType();

		//EntityからPrimaryKeyのカラム名を取り出す
		for (Field f : entity.getClass().getDeclaredFields()) {
			//カラム名の取得
			columnNames.add(f.getName());
			//@idが付与されているメンバを探索
			if (f.isAnnotationPresent(id.class)) {
				try {
					//Field名と値を取得
					f.setAccessible(true);
					idName = f.getName();

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(T entity) {

		//PrimaryKeyの値
		ID idValue = null;

		try {
			//EntityからPrimaryKeyの値を取り出す
			Field primaryIdField = entity.getClass().getDeclaredField(idName);
			idValue = (ID) primaryIdField.get(entity);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		//すでにDBに格納されているEntityかどうかを確認する。
		if (existsById(idValue)) {
			//SQL文を発行
			String sql = sc.createUpdateSql(entity);

			//上書き処理を行う
			query.updateExecute(sql);
		}else {
			//SQL文を発行
			String sql = sc.createInsertSql(entity);

			//登録処理を行う
			query.insertExecute(sql);
		}
	}

	@Override
	public Optional<T> findById(ID primaryKey) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Iterable<T> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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

}
