package oop.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Tools {
	public static String input() throws NumberFormatException{
		String str = " ";
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			str = buf.readLine();
		} catch(Exception e) {
			System.out.println(e.toString());
			System.out.println("Tools¿‡µƒ ‰»Î");
		}
		return str;
	}

}
