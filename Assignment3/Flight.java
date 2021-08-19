import java.util.Date;

public class Flight {
	
	private String code;
	
	private String direction;
	
	private Date start;
	
	private Date finish;
	
	private int price;
	
	int index;

	public Flight(String code, String direction, Date start, Date finish, int price,int index) {
		this.code = code;
		this.direction = direction;
		this.start = start;
		this.finish = finish;
		this.price = price;
		this.index=index;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
