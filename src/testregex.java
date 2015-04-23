import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testregex {
	static public void main(String[] args)
	{
		String s = "Used these for years... Now I h[]a$ve two ..in operation - keeping myself and cats happy,";
		Pattern p = Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@^_`{|}~\\[\\]\\\\]+");
		Matcher matcher = p.matcher(s);
		System.out.println(matcher.replaceAll(" "));
	}
}
