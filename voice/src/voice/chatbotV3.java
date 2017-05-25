/*
 * chatBotV3.java 
 * 24th may 17
 * 
 *  whats new : ?  : punctuations and spaces removed + more than one response for same question 
 *  
 *  idea from https://www.codeproject.com. 
 */

//******************************************************************************************************************
import java.io.*;
import java.util.*;
//******************************************************************************************************************
public class chatbotV3 {

	static String dataBase[][] = {
			{"WHAT IS YOUR NAME", "MY NAME IS CHATTERBOT2.",
			 "YOU CAN CALL ME CHATBOT3.","WHY DO YOU WANT TO KNOW MY NAME?"},
			{"HI", "HI THERE!","HOW ARE YOU?","HI!"},
			{"HOW ARE YOU","I'M DOING FINE!","I'M DOING WELL AND YOU?",
			 "WHY DO YOU WANT TO KNOW HOW AM I DOING?"},
			{"WHO ARE YOU","I'M AN A.I PROGRAM.",
			 "I THINK THAT YOU KNOW WHO I'M.","WHY ARE YOU ASKING?"},
			{"ARE YOU INTELLIGENT","YUP,IM BUT LESS THAN YOU.",
				 "WHAT DO YOU THINK?", "ACTUALY,I'M VERY INTELLIGENT!"},
			{"ARE YOU REAL","DOES THAT QUESTION REALLY MATER TO YOU?",
			 "WHAT DO YOU MEAN BY THAT?","I'M AS REAL AS I CAN BE."}
		};
	//******************************************************************************************************************
	 
	static String punctuations  = "?!.;,";
	 
	//******************************************************************************************************************
	/**
	 * @param  : ch
	 * @return : true if character & false if it ain't 
	 */
	static boolean isPunc(char ch) {
		return punctuations.indexOf(ch) != -1;
	}
	//******************************************************************************************************************
	/**
	 * @param  : str
	 * @return : resultBase
	 * match according to my search and return a set of possible results 
	 */
	static ArrayList<String> findMatch(String str) {
		
		ArrayList<String> resultBase = new ArrayList<String>();
		for(int i = 0; i < dataBase.length; i++) {
			if(dataBase[i][0].equalsIgnoreCase(str)) {
				for(int j = 1 ; j < dataBase[i].length; j++) {
					resultBase.add(dataBase[i][j]);
				}
				break;
			}
		}
		return resultBase;//this is my response list 
	}
	//******************************************************************************************************************
	/**
	 * @param str
	 * @return : cleaned string after removing punctuations and exclamations and spaces (extra ones ) 
	 */
	static String cleanString(String str) {
		StringBuffer temp = new StringBuffer(str.length());
		char prevChar = ' ';
		for(int i = 0; i < str.length(); ++i) {
			//don't do anything if extra space 
			if(str.charAt(i) == ' ' && prevChar == ' ' ) {
				continue;
			} else if(!isPunc(str.charAt(i))) { // else if there is a char then ignore 
				temp.append(str.charAt(i));
			}
			prevChar = str.charAt(i);
		}
		return temp.toString();
	}
	/**
	 * 
	 * @param responses
	 * @return selector
	 * returns a integer to select one of the responses of chatBotV3
	 */
	static int generateRandom(ArrayList<String> responses){
		Random generator = new Random();
		int itemCount = responses.size(); //store the returned list size
		int selector = generator.nextInt(itemCount);//generate a number to select the response
		return selector;
	}
	//******************************************************************************************************************
	//main starts here 
	//******************************************************************************************************************
	public static void main(String[] args) throws IOException {
		String response = "";
		String prevResponse=""; 
		while(true) {
			
			System.out.print("#");
			
			//read data
			BufferedReader keybd = new BufferedReader(new InputStreamReader(System.in));
			String input = keybd.readLine();
			
			//clean data
			input = cleanString(input);
			
			//store the output
			ArrayList<String> responses = new ArrayList<String>();
			responses = findMatch(input);
			
			//process data
			if(input.equalsIgnoreCase("BYE")) {
				System.out.println("IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!");
				break;
			} else if(responses.size() == 0) {
				System.out.println("I DON'T UNDERSTAND  WHAT YOU ARE TALKING ABOUT.");
			} else {
				//get a random number
				
				int selector = generateRandom(responses);
				response = responses.get(selector); // get a response
				/*
				 * this block of code makes sure that previous response ! = current response
				 */
				while(response.equals(prevResponse)) {
					//	generate a new response 
					selector = generateRandom(responses);
					response = responses.get(selector); // get a response again
				}
				prevResponse = response;//store the current response to avoid redundency
				System.out.println(response);
			}
		}
	}
}