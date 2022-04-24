package comperators;


//OOP Final Project - Dorel Saig 308065176	& Osnat Levy 311253306 


import java.util.Comparator;

public class DescOrder  implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return o2.compareTo(o1);
	}

}
