
public class Item implements Comparable <Item> {
	
	private int number;
	private int name;
	
	public Item(int number, int name) {
		this.number = number;
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	@Override
	public int compareTo(Item item) {
		
		if (this.number < item.number) {
			
			return -1;
		}
		
		else if (this.number == item.number) {
			
			return 0;
		}
		
		else {
			
			return 1;
		}
	}

}
