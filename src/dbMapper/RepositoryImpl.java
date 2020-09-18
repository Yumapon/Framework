package dbMapper;

import java.util.Optional;

public class RepositoryImpl<T, ID> implements Repository<T, ID>{

	@Override
	public T save(T entity) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
