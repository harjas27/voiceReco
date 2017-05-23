import java.io.*;
public class open
{
	public static void main(String args[])
	{
		 try {
                //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.google.com");
                Runtime.getRuntime().exec("notepad");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		
	}
}