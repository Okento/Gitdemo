package todo.web;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import todo.web.TodoValueObject;

public class TodoDAO2 extends CommonMySQLDAO {
	public List<TodoValueObject> todoList() throws Exception {
		List<TodoValueObject> returnList = new ArrayList<TodoValueObject>();
		String sql = "SELECT id, title, task, limitdate, lastupdate, userid, label, td.status, filename " + 
		" FROM todo_list td LEFT JOIN status_list stts ON stts.status = td.status";
		
		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// SQLを実行してその結果を取得する。
		ResultSet rs = statement.executeQuery();
		
		// 検索結果の行数文フェッチを行い、取得結果をValueObjectへ格納する
		while (rs.next()) {
			TodoValueObject vo = new TodoValueObject();
			
			// クエリー結果をVOへ格納(あらかじめクエリー結果とVOの変数名は一致させている)
			vo.setId(rs.getInt("id"));
			vo.setTitle(rs.getString("title"));
			vo.setTask(rs.getString("task"));
			vo.setLimitdate(rs.getTimestamp("limitdate"));
			vo.setLastupdate(rs.getTimestamp("lastupdate"));
			vo.setUserid(rs.getString("userid"));
			vo.setLabel(rs.getString("label"));
			vo.setFilename(rs.getString("filename"));
			
			returnList.add(vo);
		}
		
		return returnList;
	}
	public TodoValueObject detail(int id) throws Exception {
		TodoValueObject vo = new TodoValueObject();
		/**
		 * 表示するタスクの番号を指定して、タスク詳細を返す。11111
		 * @param id 表示対象のタスクID
		 * @return
		 * @throws Exception
		 */
		String sql = "SELECT id, title, task, limitdate, lastupdate, userid, label, td.status, filename " + 
		" FROM todo_list td LEFT JOIN status_list stts ON stts.status = td.status where id = ? ";
		
		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		
		// SQLを実行してその結果を取得する。
		ResultSet rs = statement.executeQuery();
		
		// 検索結果の行数文フェッチを行い、取得結果をValueObjectへ格納する
		while (rs.next()) {
			
			// クエリー結果をVOへ格納(あらかじめクエリー結果とVOの変数名は一致させている)
			vo.setId(rs.getInt("id"));
			vo.setTitle(rs.getString("title"));
			vo.setTask(rs.getString("task"));
			vo.setLimitdate(rs.getTimestamp("limitdate"));
			vo.setLastupdate(rs.getTimestamp("lastupdate"));
			vo.setUserid(rs.getString("userid"));
			vo.setLabel(rs.getString("label"));
			vo.setStatus(rs.getInt("status"));
			vo.setFilename(rs.getString("filename"));
		}
		return vo;
	}
	
	/**
	 * 新規登録処理を行う。
	 * タスクIDはAutoIncrementのキー項目なので、INSERT文のSQLに含めなくても自動的に最新のIDが登録される。
	 * @param vo 入力されたタスク内容。
	 * @return 追加された件数
	 * @throws Exception
	 */
	public int registerInsert(TodoValueObject vo) throws Exception {
		String sql = "INSERT INTO todo_list (title,task,limitdate,lastupdate,userid,status) " 
				+ "VALUES (?,?,?,now(),?,0) ";
		int result = 0;
		
		// プリペアステートメント取得し、実行SQLを渡す
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, vo.getTitle());
			statement.setString(2, vo.getTask());
			statement.setString(3, vo.getInputLimit());
			statement.setString(4, vo.getUserid());
			
			result = statement.executeUpdate();
			
			// コミットを行う
			connection.commit();
		}catch (Exception e){
			// ロールバックを行い、スローした例外はDAOから脱出する
			connection.rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * 更新処理を行う
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	
	public int registerUpdate(TodoValueObject vo) throws Exception {
		String sql = "UPDATE todo_list SET title = ?, task = ?, limitdate = ?, lastupdate = now(), userid = ?, status = ? where id = ?";
		
		// プリペアステートメントを取得し、実行SQLを渡す
		int result = 0;
		
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, vo.getTitle());
			statement.setString(2, vo.getTask());
			statement.setString(3, vo.getInputLimit());
			statement.setString(4, vo.getUserid());
			statement.setInt(5, vo.getStatus());
			statement.setInt(6, vo.getId());
			
			result = statement.executeUpdate();
			
			// コミットを行う
			connection.commit();
		}catch(Exception e){
			connection.rollback();
			throw e;
		}
		
		return result;
	}
	
	/**
	 * 削除処理を行う。指定されたidのタスクを削除する。
	 * @param id
	 * @return 削除件数
	 * @throws Exception
	 */
	public int delete(int id) throws Exception {
		String sql = "DELETE FROM todo_list where id = ?";
		
		// SQLを実行してその結果を取得する。
		int result = 0;
		try {
			// プリペアステートメントを取得し、実行SQLを渡す
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1,  id);
			result = statement.executeUpdate();
			
			// コミットを行う
			connection.commit();
		}catch(Exception e){
			connection.rollback();
			throw e;
		}
		
		return result;
	}
	
	public int updateUploadInfo(TodoValueObject vo) throws Exception {
		String sql = "UPDATE todo_list SET filename = ? WHERE id = ?";
		int result = 0;
		
		// プリペアステートメントを取得し、実行SQLを渡す
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, vo.getFilename());
			statement.setInt(2, vo.getId());
			
			// 登録を行う
			result = statement.executeUpdate();
			
			// コミットを行う
			connection.commit();
		}catch(Exception e){
			connection.rollback();
			throw e;
		}
		
		return result;
	}
}