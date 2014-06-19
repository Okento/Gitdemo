package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.TBook;
import test.MySQLDAO;
import test.UserTable;

public class BookDB extends MySQLDAO{
	public List<TBook> getListbook()throws Exception{
		
		// returnListをArrayList型にする
		List<TBook> returnList = new ArrayList<TBook>();
		
		// 実行するSQL文を記述
		String sql = "SELECT id, isbn, title, author, publisher, price " + 
		" FROM t_book ";
		
		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// SQLを実行してその結果を取得する。
		ResultSet rs = statement.executeQuery();
		
		// 検索結果の行数文フェッチを行い、取得結果をTBookへ格納する
		while (rs.next()) {
			TBook tb = new TBook();
			
			// クエリー結果をtbへ格納(あらかじめクエリー結果とtbの変数名は一致させている)
			tb.setId(rs.getInt("id"));
			tb.setIsbn(rs.getString("isbn"));
			tb.setTitle(rs.getString("title"));
			tb.setAuthor(rs.getString("author"));
			tb.setPublisher(rs.getString("publisher"));
			tb.setPrice(rs.getInt("price"));
			
			returnList.add(tb);
		}
		
		return returnList;
	}
	
	
	public TBook findBookByISBN(String inISBN) throws Exception{
		
		// 実行するSQL文を記述
		String sql = "SELECT * FROM t_book WHERE isbn = ?";
			
		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, inISBN);		// 42行目としっかり統一させる。
		
		// SQLを実行してその結果を取得する。
		ResultSet rs = statement.executeQuery();
						
		// 検索結果の行数文フェッチを行い、取得結果をTBookへ格納する
		TBook tb = new TBook();
		while (rs.next()) {
			
			// クエリー結果をtbへ格納(あらかじめクエリー結果とtbの変数名は一致させている)
			tb.setId(rs.getInt("id"));
			tb.setIsbn(rs.getString("isbn"));
			tb.setTitle(rs.getString("title"));
			tb.setAuthor(rs.getString("author"));
			tb.setPublisher(rs.getString("publisher"));
			tb.setPrice(rs.getInt("price"));
			
		}
		return tb;
	}
	
	public int total(List<String> cart) throws Exception{
		
		int returnsum = 0;
		String sqlcart = "";
		
		// 個別の('isbn',)を作る
		for(String isbn :cart){
			sqlcart += "'" + isbn + "'" + ",";
		}
		
		// 実行するSQL文を記述
		// カンマで終わるとエラーが出るので、1字はさむ。
		String sql = "select sum(price) from t_book where isbn in (" + sqlcart + "1);";
		
		// プリペアステートメントを取得し、実行SQLを渡す
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// SQLを実行してその結果を取得する。
		ResultSet rs = statement.executeQuery();
		
		connection = getConnection();
		
		// 検索結果の行数文フェッチを行い、取得結果をTBookへ格納する
		while (rs.next()) {
			
			// ループの中で集めたpriceのsumをreturnsumに返す
			returnsum = rs.getInt("sum(price)");
			
		}
		return returnsum;
		
	}
	
	public int registerInsert(UserTable ut) throws Exception {
		String sql = "INSERT INTO usr (uid,passwd,uname) " 
				+ "VALUES (?,?,?) ";
		int result = 0;
		
		// プリペアステートメント取得し、実行SQLを渡す
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, ut.getUid());
			statement.setString(2, ut.getPasswd());
			statement.setString(3, ut.getUname());
			
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
	
	public int ItemregisterInsert(TBook tb) throws Exception {
		String sql = "INSERT INTO t_book (isbn,title,author,publisher,price) " 
				+ "VALUES (?,?,?,?,?) ";
		int result = 0;
		
		// プリペアステートメント取得し、実行SQLを渡す
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, tb.getIsbn());
			statement.setString(2, tb.getTitle());
			statement.setString(3, tb.getAuthor());
			statement.setString(4, tb.getPublisher());
			statement.setInt(5, tb.getPrice());
			
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
}