import java.util.ArrayList;

public class Player extends Person{
	private int bet;
	private String name;
	private int chips;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Player(String name, int chips) {
		this.name = name;
		this.chips = chips;
	}

	public String getName() {

		return name;
	}

	public int makeBet() {
		bet = 20;
		if (chips <= 0) {
			return 0;
		}
		return bet;
	}

		public boolean hit_me(Table table) { 
			int sum = 0;
			for (int i = 0; i < getOneRoundCard().size(); i++) {
				sum += ((Card) getOneRoundCard().get(i)).getRank();
			}
			if (sum<21)
					return true;
				
			return false;
		}
	

	public void increaseChips (int diff){
		chips +=diff;
	}

	public int getCurrentChips() {

		return chips;
	}

	public void sayHello() {
		System.out.println("Hello, I am " + name + ".");
		System.out.println("I have " + chips + " chips.");
	}
}
