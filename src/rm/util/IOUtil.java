package rm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

public class IOUtil 
{
	public static void print_str(String file, String str)
	{
		Writer writer = null;
		
		try 
		{
			writer = new BufferedWriter(new OutputStreamWriter(
					  new FileOutputStream(file, false), "utf-8"));
			writer.write(str);
		}
		catch(IOException e)
		{
			System.err.println("Cannot print the string into '" + file + "' : " + e.getMessage());
		}
		finally
		{
			try {
				if(writer != null) writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String[] read_file_lines(String file)
	{
		BufferedReader reader = null;
		
		Vector<String> vec = new Vector<String>();
		
		try 
		{
			reader = new BufferedReader(new FileReader(file));
			for(String line; (line = reader.readLine()) != null; )
		        vec.add(line);	
		}
		catch(IOException e)
		{
			System.err.println("Cannot print the string into '" + file + "' : " + e.getMessage());
		}
		finally
		{
			try {
				if(reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String[] array = new String[vec.size()]; 
		vec.copyInto(array);
		return array;
	}
}
