package voice;

import java.util.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;
/**
* @author harjas.singh
 */
public class voiceBot {
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
        
        
        
        public static void main(String[] args) throws IOException {
            
        String response = "";
        String prevResponse="";    
        //Configuration Object
        Configuration configuration = new Configuration();
 
        // Set path to the acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to the dictionary.
        configuration.setDictionaryPath("4953.dic");
        // Set path to the language model.
        configuration.setLanguageModelPath("4953.lm");
 
        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = new LiveSpeechRecognizer(configuration);
 
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognize.startRecognition(true);
 
        //Creating SpeechResult object
        SpeechResult result;
 
        //Check if recognizer recognized the speech
        while ((result = recognize.getResult()) != null) {
 
            //Get the recognized speech
            String command = result.getHypothesis();
            System.out.print("#");
            
            //store the output
            ArrayList<String> responses = new ArrayList<String>();
            responses = findMatch(command);
            
            Process p;
            
            
            if(command.equalsIgnoreCase("BYE")) {
		System.out.println("IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!");
		break;
            } else if(responses.isEmpty()) {
                if(command.equalsIgnoreCase("bot open google")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.google.com");
                }   
                else if(command.equalsIgnoreCase("what is java")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.google.co.in/search?q=what+is+java&oq=wha&aqs=chrome.0.69i59j69i57j0l4.1143j0j7&sourceid=chrome&ie=UTF-8");
                }
                else if(command.equalsIgnoreCase("check facebook")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.fb.com");
                }
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
