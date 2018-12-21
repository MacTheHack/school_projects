package p5;

public class TestDice { 
	
	public static void test(Dice dice, int nbrOfThrows) {
		int[] res = new int[dice.getSides()]; 
		for (int i=0;i<nbrOfThrows;i++) res[dice.throwDice()-1]++;
		for(int i=0;i<dice.getSides();i++) TextWindow.append(i+1+"\t"+res[i]+"\n");	 
	}
	public static void test(Player player, int nbrOfThrows) { 
		String name = null;
		int[] res = null;
		Dice dice = null;
		Cheater c = null;
		OrdinaryPlayer p = null;
		if(player instanceof OrdinaryPlayer) {
			p = (OrdinaryPlayer)player;
			dice = p.getDice();
			name = p.getName();
			res = new int[ dice.getSides() ];
			for (int i=0;i<nbrOfThrows;i++) res[p.throwDice()-1]++;
		}
		if(player instanceof Cheater) {
			c = (Cheater)player;
			dice = c.getDice();
			name = c.getName();
			res = new int[dice.getSides()];
			for (int i=0;i<nbrOfThrows;i++) res[c.throwDice()-1]++;
		}
		TextWindow.append("Name:\t"+name+"\n");
		for(int i=0;i<dice.getSides();i++) TextWindow.append(i+1+"\t"+res[i]+"\n");
	}
	
	public static void main(String[] args) {
		//Uppgift 5c
		TextWindow.append("Uppgift 5c\n");
		TestDice.test( new SimpleDice( 6 ), 1000000 );
		TextWindow.println();
		TestDice.test( new SimpleDice( 4 ), 1000000 );
		TextWindow.println();
		
		//Uppgift 5e
		TextWindow.append("Uppgift 5e\n");
		TestDice.test(new OrdinaryPlayer("Rut",new SimpleDice(6)),1000000);
		TextWindow.println();
		TestDice.test(new Cheater("Fuffe",new SimpleDice(6)),1000000);
	}
}
