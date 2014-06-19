package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import test.BookDB;
import test.TBook;

@Results({ @Result(name="login", location="bookstore.jsp"),
		   @Result(name="false", location="login_error.jsp")})
public class LoginAction implements ServletRequestAware {
	private List<TBook> lblist;
	
	private HttpServletRequest request;
	
	private String uid;
	private String passwd;
	
	@Action("/Login")
	public String loginAndGetBookList(){
		BookDB bd = new BookDB();
		try{
			bd.getConnection();
			lblist = bd.getListbook();
			request.login(uid, passwd);
		}catch(Exception e){
			e.printStackTrace();
			return("false");
		}finally{
			// BD‚Ìˆ—‚ªŠ®—¹‚µ‚½‚çÚ‘±‚ğ•Â‚¶‚é
			bd.closeConnection();
		}
		return("login");
	}
	
	public List<TBook> getLblist(){
		return lblist;
	}
	
	public void setLblist(List<TBook> lblist){
		this.lblist = lblist;
	}
	
	public String getUid(){
		return uid;
	}
	
	public void setUid(String uid){
		this.uid = uid;
	}
	
	public String getPasswd(){
		return passwd;
	}
	
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
	
}