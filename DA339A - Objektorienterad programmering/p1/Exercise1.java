package p1;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
/*
 * Klassen innehåller lösningar på uppgifterna 1a – 1f
 * @author Mattias Jönsson den 24/9-2018
 **/
public class Exercise1 {

	/*
	 * Metoden beräknar totala biljettpriset och det genomsnittliga
	 * biljettpriset för ett antal vuxna, barn och pensionärer.
	 **/	
	public void exercise1a() {
		// Deklaration av variabler som används i programmet
		int childs, adults, pensioners, totalPrice, avgPrice;
		String res;
		// Användaren matar in antalet vuxna. Lagras i ’adults’
		adults = Integer.parseInt( JOptionPane.showInputDialog( "Ange antal vuxna" ) );
		// Användaren matar in antalet barn. Lagras i ’child’
		childs = Integer.parseInt( JOptionPane.showInputDialog( "Ange antal barn" ) );
		// Användaren matar in antalet pensionärer. Lagras i ’pensioners’
		pensioners = Integer.parseInt( JOptionPane.showInputDialog( "Ange antal pensionärer" ) );
		//Beräknar det total priset
		totalPrice = (adults*110)+(childs*60)+(pensioners*80);
		//Beräknar det genomsnittiga priset
		avgPrice = totalPrice/(adults+childs+pensioners);
		res = "Total priset är: " + totalPrice + "\nGenomsnittiga priset är: " + avgPrice;
		// Meddelande till användaren där det står det total och genomsnittliga priset
		JOptionPane.showMessageDialog( null, res );
	}

	/*
	 * Metoden skriver ut alla udda tal mellan 1 och 100
	 **/	
	public void exercise1b() {
		// Loopar igenom for-loopen med 0 som början och 100 som slut och ökar värdet med 2
		for(int i=1;i<100;i+=2) {
			System.out.print(i+" "); // Skriver ut vad i är och ett mellanslag
		}
		System.out.println();
	}

	/*
	 * Metoden skriver ut om talet användaren har angivit är 
	 * positivt, negativt eller noll.
	 * Metoden körs 4 gånger
	 **/	
	public void exercise1c() {
		// Loopar for-loopen med 0 som början och 4 som slut och ökar värdet med 1
		for(int i=0;i<4;i++) {
			int num = Integer.parseInt( JOptionPane.showInputDialog( "Ange ett nummer" ) );
			// Om num är större än 0 skriv ut att det är positivt
			if (num>0) {
				System.out.println(num + " är positivt");
			}
			// Om num är mindre än 0 skriv ut att det är negativt
			else if (num>0) {
				System.out.println(num + " är negativt");
			}
			// Om num är lika med 0 skriv ut att det är noll
			else{
				System.out.println(num + " är noll");
			}
		}
	}

	/*
	 * Metoden skriver ut tal mellan det minsta och största 
	 * angivna värdet med steg på 7
	 **/	
	public void exercise1d() {
		// Deklaration av variabler som används i programmet 
		int min, max;
		// Användaren matar in minsta värdet. Lagras i ’min’
		min = Integer.parseInt( JOptionPane.showInputDialog( "Ange minsta värdet" ) );
		// Användaren matar in största värdet. Lagras i ’max’
		max = Integer.parseInt( JOptionPane.showInputDialog( "Ange största värdet" ) );
		// Loopar for-loopen med min som början och max som slut och ökar värdet med 7
		for(int i=min; i<max; i+=7) {
			System.out.print(i+", ");// Skriver ut vad i är och ett komma och ett mellanslag
		}
		System.out.println(); 
	}

	/*
	 * Metoden gör så bilden rör sig horisontellt mellan kanterna på 
	 * fönstret
	 **/	
	public void exercise1e() {
		PaintWindow pw = new PaintWindow();
		Random rand = new Random();
		ImageIcon image = new ImageIcon("images/gubbe.jpg");
		int width = pw.getBackgroundWidth(); // Sätter width som bredden på pw
		int height = pw.getBackgroundHeight(); // Sätter height som höjden på pw
		int dx = -3; 
		int dy = 0;
		int x = 250;
		int y = rand.nextInt(height-100);  // Bildens höjd är 100
		int imageWidth = image.getIconWidth(); //Bildens bredd

		// oändlig loop, raderna 36-42 upprepas tills PaintWindow-fönstret stängs
		while(true) {
			pw.showImage(image, x, y);
			PaintWindow.pause(20); // pausa exekveringen i 20 ms innan nästa förflyttning
			x += dx;
			y += dy;
			//Sätter en gräns för den vänstra kanten
			if(x<0) {
				dx = -dx;
			}
			//Sätter en gräns för den högra kanten
			if(x>(width-imageWidth)) {
				dx = -dx;
			} 
		}
	}

	/*
	 * Metoden gör så bilden rör sig vertikalt mellan kanterna på 
	 * fönstret
	 **/
	public void exercise1f() {
		PaintWindow pw = new PaintWindow();
		Random rand = new Random();
		ImageIcon image = new ImageIcon("images/gubbe.jpg");
		int width = pw.getBackgroundWidth(); // Hämtar bredden på pw
		int height = pw.getBackgroundHeight(); // Hämtar höjden på pw
		int dx = rand.nextInt(7)-3; //Genererar ett värde för dx mellan -3 och 3
		int dy = rand.nextInt(7)-3; //Genererar ett värde för dy mellan -3 och 3
		int x = rand.nextInt(501); //Genererar ett värde för x mellan 0 och 500
		int y = rand.nextInt(height-100);  // Bildens höjd är 100		
		int imageHeight = image.getIconHeight(); //Bildens höjd
		int imageWidth = image.getIconWidth(); //Bildens bredd

		// oändlig loop, raderna 36-42 upprepas tills PaintWindow-fönstret stängs
		while(true) {
			pw.showImage(image, x, y);
			PaintWindow.pause(20); // pausa exekveringen i 20 ms innan nästa förflyttning
			x += dx;
			y += dy;			
			//Sätter en gräns för den övre kanten
			if(y<0) { 
				dy = -dy;
			}
			//Sätter en gräns för den undre kanten
			if(y>(height-imageHeight)) {
				dy = -dy;
			} 
			//Sätter en gräns för den vänstra kanten
			if(x<0) {
				dx = -dx;
			} 
			//Sätter en gräns för den högra kanten
			if(x>(width-imageWidth)) {
				dx = -dx;
			} 
		}
	}
}
