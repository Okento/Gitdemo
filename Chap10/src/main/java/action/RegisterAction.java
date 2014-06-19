package action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import test.BookDB;
import test.UserTable;

@Results({ @Result(name="register", location="complete.jsp")})
public class RegisterAction {
		
	// リクエストパラメータを受け取り、更新utに格納する準備をする
	private String uid;
	private String passwd;
	private String uname;
	
	@Action("/Register")
	public String Updata(){
			
		// utへ格納する。
		UserTable ut = new UserTable();
		ut.setUid(uid);
		ut.setPasswd(md5(passwd));
		ut.setUname(uname);
		
		// BookDBの取得
		BookDB db = new BookDB();
		
		try{
			// データベースに接続
			db.getConnection();
			
			// 登録処理を行う。
			db.registerInsert(ut);
			db.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ("register");	
	}
	
	public String md5(String passwd) {
		String rtn = "";
		try {
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(passwd.getBytes());
		    byte[] digest = md.digest();

		    //ダイジェストを文字列に変換します。
		    for (int i = 0; i < digest.length; i++) {
		        rtn += String.format("%02x", digest[i]);
		    }
		} catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		return rtn;
	}
		
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
}
