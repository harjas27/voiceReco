package voice;
//Imports
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;
public class Voice {
    public static void main(String[] args) throws IOException {
        //Configuration Object
        Configuration configuration = new Configuration();
 
        // Set path to the acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to the dictionary.
        configuration.setDictionaryPath("0945.dic");
        // Set path to the language model.
        configuration.setLanguageModelPath("0945.lm");
 
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
            String work = null;
            Process p;
 
            if(command.equalsIgnoreCase("open file manager")) {
                work = "nautilus";
            } else if (command.equalsIgnoreCase("close file manager")) {
                work = "pkill nautilus";
            } else if (command.equalsIgnoreCase("open browser")) {
                work = "notepad";
            } else if (command.equalsIgnoreCase("close browser")) {
                work = "pkill google-chrome";
            }
            //In case command recognized is none of the above hence work might be null
            if(work != null) {
                //Execute the command
                p = Runtime.getRuntime().exec(work);
            }
        }
    }
}
