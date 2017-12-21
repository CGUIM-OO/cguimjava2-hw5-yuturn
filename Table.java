import java.util.ArrayList;

public class Table {
	final int MAXPLAYER = 4;
	private Deck deck;
	private Player[] players;
	private Dealer dealer;
	private int[] pos_betArray;

	
	public Table(int nDeck) {
		players = new Player[MAXPLAYER];
		deck = new Deck(nDeck);
	}

	public void set_player(int pos, Player p) {
		players[pos] = p;
	}

	public Player[] get_player() {
		return players;
	}

	public void set_dealer(Dealer d) {
		dealer = d;
	}

	public Card get_face_up_card_of_dealer() {
		return dealer.getOneRoundCard().get(1);
	}

	private void ask_each_player_about_bets() {
		pos_betArray = new int[MAXPLAYER];
		for (int i = 0; i < MAXPLAYER; i++) {
			players[i].sayHello();
			pos_betArray[i] = players[i].makeBet();
		}
	}

	private void distribute_cards_to_dealer_and_players() {
		for (int i = 0; i < MAXPLAYER; i++) {
			ArrayList<Card> temp = new ArrayList<Card>();
			temp.add(deck.getOneCard(true));
			temp.add(deck.getOneCard(true));
			players[i].setOneRoundCard(temp);
		}
		ArrayList<Card> temp1 = new ArrayList<Card>();
		temp1.add(deck.getOneCard(false));
		temp1.add(deck.getOneCard(true));
		dealer.setOneRoundCard(temp1);
		System.out.println("Dealer's face up card is ");
		Card z = get_face_up_card_of_dealer();
		z.printCard();
	}

	private void ask_each_player_about_hits() {
		{

			for (Player p : get_player()) {
				ArrayList<Card> temp2 = p.getOneRoundCard();
				boolean hit = false;
				do {
					if (p.getTotalValue() > 21)
						break;
					hit = p.hit_me(this); // this
					if (hit) {

						System.out.println("Hit! " + p.getName() + "¡¦s cards now: ");
						temp2.add(deck.getOneCard(true));
						p.setOneRoundCard(temp2);
						for (Card c : p.getOneRoundCard()) {
							c.printCard();
						}
					}

				} while (hit);
				System.out.println(p.getName() + ", Pass hit!");
			}
		}
	}

	private void ask_dealer_about_hits() {
		dealer.hit_me(this);
		do {
			if (dealer.hit_me(this)) {
				ArrayList<Card> temp1 = new ArrayList<Card>();
				temp1 = dealer.getOneRoundCard();
				temp1.add(deck.getOneCard(true));
				dealer.hit_me(this);
			}
		} while (dealer.hit_me(this) == true);
		System.out.println("Dealer's hit is over!");

	}

	private void calculate_chips() {
		System.out.println("Dealer's card value is " + dealer.getTotalValue() + " ,Cards:");
		dealer.printAllCard();
		for (int i = 0; i < MAXPLAYER; i++) {
			System.out.println(players[i].getName() + " card value is " + players[i].getTotalValue());
			if (dealer.getTotalValue() > players[i].getTotalValue() && dealer.getTotalValue() <= 21
					&& players[i].getTotalValue() <= 21) {
				players[i].increaseChips(-players[i].makeBet());
				System.out.println(
						", Loss " + players[i].makeBet() + " Chips, the Chips now is: " + players[i].getCurrentChips());
			} else if (dealer.getTotalValue() < players[i].getTotalValue() && dealer.getTotalValue() <= 21
					&& players[i].getTotalValue() <= 21) {
				players[i].increaseChips(players[i].makeBet());
				System.out.println(
						", Get " + players[i].makeBet() + " Chips, the Chips now is: " + players[i].getCurrentChips());

			} else if (players[i].getTotalValue() > 21) {
				players[i].increaseChips(-players[i].makeBet());
				System.out.println(
						", Loss " + players[i].makeBet() + " Chips, the Chips now is: " + players[i].getCurrentChips());
			} else if (dealer.getTotalValue() > 21) {
				players[i].increaseChips(players[i].makeBet());
				System.out.println(
						", Get " + players[i].makeBet() + " Chips, the Chips now is: " + players[i].getCurrentChips());
			} else if (dealer.getTotalValue() == players[i].getTotalValue() && dealer.getTotalValue() <= 21
					&& players[i].getTotalValue() <= 21) {
				players[i].increaseChips(0);
				System.out.println(",chips have no change! The Chips now is: " + players[i].getCurrentChips());
			} 
		}
	}

	public int[] get_palyers_bet() {
		int[] get_palyers_bet = new int[4];
		for (int i = 0; i < MAXPLAYER; i++) {
			get_palyers_bet[i] = players[i].makeBet();

		}
		return get_palyers_bet;
	}

	public void play() {
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
}
