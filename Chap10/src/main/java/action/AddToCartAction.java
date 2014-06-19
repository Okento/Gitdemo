package action;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import test.BookDB;
import test.TBook;

@Results({
	@Result(name="addtocart", location="bookstore.jsp"),
	@Result(name="checkout", location="checkout.jsp")
})

public class AddToCartAction implements ServletRequestAware {
	
	private List<TBook> lblist;
	private HttpServletRequest request;
	
	private String[] selecteditems = null;
	
	@Action("/AddToCart")
	public String addToCart(){
		HttpSession session = request.getSession(); // セッションの開始。セッションの受け取り。
		session.removeAttribute("CART"); 			// カートの中のセッション情報を初期化する。(一回アクセスするごとに初期化されている)
		
		BookDB bd = new BookDB();
		
		// アイテムの情報が存在しなかったり、選択されていない場合の検出
		// 情報をセットしている
		if(selecteditems != null && selecteditems.length != 0){
			List<String> cart = Arrays.asList(selecteditems);
			session.setAttribute("CART", cart);		// カート("CART")情報の中にセットする
		}
		
		try{
			bd.getConnection();
			lblist = bd.getListbook();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// BDの処理が完了したら接続を閉じる
			bd.closeConnection();
		}
		return ("addtocart"); //リザルトに戻す。(リザルトがaddtocartの場合bookstore.jspを見ろと指示しているのでそうなる(18行目)
	}
	
	public String checkout(){
		HttpSession session = request.getSession(false);
		List<String> cart = (List<String>)session.getAttribute("CART");
		
		BookDB bd = new BookDB();

		try{
			bd.getConnection();
			int total = bd.total(cart);
			request.setAttribute("TOTAL", total);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// BDの処理が完了したら接続を閉じる
			bd.closeConnection();
		}
		return ("checkout");
	}
	
	
	public List<TBook> getLblist(){
		return lblist;
	}
	public void setLblist(List<TBook> lblist){
		this.lblist = lblist;
	}
	
	public String[] getSelecteditems(){
		return selecteditems;
	}
	public void setSelecteditems(String[] selecteditems){
		this.selecteditems = selecteditems;
	}
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
}
