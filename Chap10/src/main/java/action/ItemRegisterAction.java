package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import test.BookDB;
import test.TBook;

@Results({ @Result(name="Iregister", location="bookstore.jsp")})
public class ItemRegisterAction {
	
	private List<TBook> lblist;
	
	// リクエストパラメータを受け取り、更新tbに格納する準備をする
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private int price;
	
	@Action("/IRegister")
	public String Updata(){
			
		// tbへ格納する。
		TBook tb = new TBook();
		tb.setIsbn(isbn);
		tb.setTitle(title);
		tb.setAuthor(author);
		tb.setPublisher(publisher);
		tb.setPrice(price);
		
		// BookDBの取得
		BookDB db = new BookDB();
		
		try{
			// データベースに接続
			db.getConnection();
			
			// 登録処理を行う。
			db.ItemregisterInsert(tb);			
			
			// リストを取得
			lblist = db.getListbook();
			
			// データベース接続解除
			db.closeConnection();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ("Iregister");	
	}
	
	public List<TBook> getLblist(){
		return lblist;
	}
	public void setLblist(List<TBook> lblist){
		this.lblist = lblist;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}