package p4;

import javax.swing.JOptionPane;

import arrays.Country;
import arrays.CountryArrays;

public class Exercise4c {
	public void testArray(Country[] array) {
		String message = "";
		Country country = new Country("Costa Rica",4133884);
//    	message += "toString: " + CountryArrays.toString( array ) + "\n";
//		message += "indexOf " + array[4] +": " + CountryArrays.indexOf( array, array[4] ) + "\n";
//		message += "indexOf " + country +": " + CountryArrays.indexOf( array, country ) + "\n";
//		message += "member " + array[4] +": " + CountryArrays.member( array, array[4] ) + "\n";
//		message += "member " + country +": " + CountryArrays.member( array, new Country("A",10) ) + "\n";
//		message += "max: " + CountryArrays.max( array ) + "\n";
//		message += "min: " + CountryArrays.min( array ) + "\n";
		JOptionPane.showMessageDialog( null, message );
	}
	
    public static void main(String[] args) {
    	Exercise4c e4c = new Exercise4c();
    	Country[] countries = {new Country("Guyana",769095),new Country("Sri Lanka",20926315),
    			new Country("Japan",126804433),new Country("Ryssland",143400000),
    			new Country("Monaco",32671),new Country("Moldavien",4320490)};
        e4c.testArray(countries);
    }

}
