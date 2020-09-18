package dbMapper;

import java.util.Optional;

//TにはEntityクラスの型を、IDにはPrimaryKeyを記載する
public interface Repository<T, ID>{

	/*
	 * 単一テーブルへの操作
	 */

	//指定されたエンティティを保存します。
	T save(T entity);

	//指定された ID で識別されるエンティティを返します。
	Optional<T> findById(ID primaryKey);

	//すべてのエンティティを返します。
	Iterable<T> findAll();

	//エンティティの数を返します。
	long count();

	//指定されたエンティティを削除します。
	void delete(T entity);

	//指定された ID のエンティティが存在するかどうかを示します。
	boolean existsById(ID primaryKey);

	/*
	 * 複数テーブルへの操作
	 */

	//指定された ID で識別されるエンティティを返します。
	Optional<T> multiFindById(ID primaryKey);

	//すべてのエンティティを返します。
	Iterable<T> multifindAll();

}
