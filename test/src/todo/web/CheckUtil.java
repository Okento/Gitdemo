package todo.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CheckUtil {
	private ArrayList<String> _errs = null;
	
	// コンストラクタ
	public CheckUtil() {
		// プライベート変数 _errsを初期化
		this._errs = new ArrayList<String>();
	}
	
	// 必須検証
	public void requiredCheck(String value, String name){
		if(value == null || value.trim().isEmpty()){
			this._errs.add(name + "は必須です。");
		}
	}
	
	// 文字列長検証(max文字以内であるか)
	public void lengthCheck(String value, int max, String name){
		if(value != null && !value.trim().isEmpty()){
			if(value.length() > max){
				this._errs.add(name + "は" + max + "文字以下で入力してください");
			}
		}
	}
	
	// 数値型検証
	public void numberTypeCheck(String value, String name){
		if(value != null && !value.trim().isEmpty()){
			try{
				Integer.parseInt(value);
			}catch(NumberFormatException e){
				this._errs.add(name + "は数値で入力してください");
			}
		}
	}
	
	// 数値範囲検証(min ～ maxの範囲にあるか)
	public void rangeCheck(String value, int max, int min, String name){
		if(value != null && !value.trim().isEmpty()){
			int val = 0;
			try{
				val = Integer.parseInt(value);
			}catch(NumberFormatException e){
				this._errs.add(name + "は数値で入力してください");
			}
			if(val < min || val > max){
				this._errs.add(name + "は" + min + "以上、かつ" + max + "以下で入力してください。");
			}
		}
	}
	
	// 日付型検証
	public void dateTypeCheck(String value, String name){
		if(value != null && !value.trim().isEmpty()){
			
			// [YYYY-MM-DD]の形式であるかをチェック
			if(value.matches("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$")){
				
				// 年月日を分解してCalendarオブジェクトを生成
				StringTokenizer token = new StringTokenizer(value, "-");
				int year = Integer.parseInt((String)token.nextToken());
				int month = Integer.parseInt((String)token.nextToken());
				int day = Integer.parseInt((String)token.nextToken());
				Calendar cal = Calendar.getInstance();
				cal.set(year, month - 1, day);
				
				//
				if(cal.get(Calendar.YEAR) != year ||
						cal.get(Calendar.MONTH) != month-1 ||
						cal.get(Calendar.DATE) != day){
					this._errs.add(name + "は正しい日付で入力してください。");
				}else{
					this._errs.add(value + "は日付形式で入力してください");
				}
			}
		}
	}
	
	// 正規表現パターン検証(パターンpatternに合致するか)
	public void regExCheck(String value, String pattern, String name){
		if(value != null && !value.trim().isEmpty()){
			if(value.matches(pattern)){
				this._errs.add(name + "を正しい形式で入力してください");
			}
		}
	}
	
	// 重複検証(データベースの内容と重複してないか)
	public void duplicateCheck(String value, String sql, String name){
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/dbtest");
			db = ds.getConnection();
			ps = db.prepareStatement(sql);
			ps.setString(1,  value);
			rs = ps.executeQuery();
			if(rs.next()){this._errs.add(name + "が重複しています。");}
		}catch(NamingException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(db != null){db.close();}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	// エラーの有無を確認
	public boolean hasErrors(){
		return !this._errs.isEmpty();
	}
	
	// プライベート変数_errsの値を<ul>リストに整形
	public String getErrorList(){
		StringBuffer buf = new StringBuffer();
		buf.append("<ul style='color:Red;'>");
		for(String err : this._errs){
			buf.append("<li>" + err + "</li>");
		}
		buf.append("</ul>");
		return buf.toString();
	}
}
