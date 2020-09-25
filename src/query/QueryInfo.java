package query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryInfo {

	//TableName
	String tableName;

	//PrimaryKey
	String idName;

	//ColumnNames
	ArrayList<String> columnNames;

	//ColumnValues
	Map<String, String> columnValues = new HashMap<>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	public Map<String, String> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(Map<String, String> columnValues) {
		this.columnValues = columnValues;
	}

}
