package arrays;

/**
 * This class contains the solutions for exercise 4c
 * 
 * @author Mattias Jönsson
 * Date 14/11-2018
 *
 */
public class CountryArrays {

	
	/**
	 * Prints out the countries
	 * 
	 * @param array
	 * @return A string of the countries
	 */
	public static String toString(Country[] array) {
		String str = "{";
		int l = array.length;
		for(int i=0;i<l-1;i++)str+=array[i]+", ";
		str+=array[l-1]+"}";
		return str;
	}

	/**
	 * Checks the index of a given county
	 * 
	 * @param array		the array of countires
	 * @param country	the countries being checked
	 * @return the index of the county, if the country doesn't exist it returns -1
	 */
	public static int indexOf(Country[] array, Country country) {
		int indexOf=-1;
		for(int i=0;i<array.length;i++)
			if(country.equals(array[i]))
				indexOf=i;
		return indexOf;
	}

	/**
	 * Checks if a given countruy is a member
	 * 
	 * @param array		the array of countires
	 * @param country	the countries being checked
	 */
	public static boolean member(Country[] array, Country country) {
		boolean isMember=false;
		for(int i=0;i<array.length;i++)
			if(country.equals(array[i]))
				isMember=true;
		return isMember;
	}
	
	/**
	 * Checks which country has the biggest population
	 * 
	 * @param array		the array of countires
	 * @return	a country
	 */
	public static Country max(Country[] array) {
		Country max = array[array.length-1];
		for(int i=array.length-1;i>=0;i--)
			if(max.compareTo(array[i])<0)
				max=array[i];									
		return max;
	}
	
	/**
	 * Checks which country has the smallest population
	 * 
	 * @param array		the array of countires
	 * @return	a country
	 */
	public static Country min(Country[] array) {
		Country min = array[0];
		for(int i=0;i<array.length;i++)
			if(min.compareTo(array[i])>0)
				min=array[i];						
		return min;
	}		
}