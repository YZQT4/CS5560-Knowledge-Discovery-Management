import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Mayanka on 13-Jun-16.
 */
public class Main {
    public static void main(String args[]) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

// read some text in the text variable

        String text = "BACKGROUND: There is strong evidence for the effectiveness of addressing tobacco use in health care settings. However, few smokers receive cessation advice when visiting a hospital. Implementing smoking cessation technology in outpatient waiting rooms could be an effective strategy for change, with the potential to expose almost all patients visiting a health care provider without preluding physician action needed. OBJECTIVE: The objective of this study was to develop an intervention for smoking cessation that would make use of the time patients spend in a waiting room by passively exposing them to a face-aging, public morphing, tablet-based app, to pilot the intervention in a waiting room of an HIV outpatient clinic, and to measure the perceptions of this intervention among smoking and nonsmoking HIV patients. METHODS: We developed a kiosk version of our 3-dimensional face-aging app Smokerface, which shows the user how their face would look with or without cigarette smoking 1 to 15 years in the future. We placed a tablet with the app running on a table in the middle of the waiting room of our HIV outpatient clinic, connected to a large monitor attached to the opposite wall. A researcher noted all the patients who were using the waiting room. If a patient did not initiate app use within 30 seconds of waiting time, the researcher encouraged him or her to do so. Those using the app were asked to complete a questionnaire. RESULTS: During a 19-day period, 464 patients visited the waiting room, of whom 187 (40.3%) tried the app and 179 (38.6%) completed the questionnaire. Of those who completed the questionnaire, 139 of 176 (79.0%) were men and 84 of 179 (46.9%) were smokers. Of the smokers, 55 of 81 (68%) said the intervention motivated them to quit (men: 45, 68%; women: 10, 67%); 41 (51%) said that it motivated them to discuss quitting with their doctor (men: 32, 49%; women: 9, 60%); and 72 (91%) perceived the intervention as fun (men: 57, 90%; women: 15, 94%). Of the nonsmokers, 92 (98%) said that it motivated them never to take up smoking (men: 72, 99%; women: 20, 95%). Among all patients, 102 (22.0%) watched another patient try the app without trying it themselves; thus, a total of 289 (62.3%) of the 464 patients were exposed to the intervention (average waiting time 21 minutes). CONCLUSIONS: A face-aging app implemented in a waiting room provides a novel opportunity to motivate patients visiting a health care provider to quit smoking, to address quitting at their subsequent appointment and thereby encourage physician-delivered smoking cessation, or not to take up smoking."; // Add your text here!

// create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

// run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                System.out.println("\n" + token);

                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println("Text Annotation");
                System.out.println(token + ":" + word);
                // this is the POS tag of the token

                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                System.out.println("Lemma Annotation");
                System.out.println(token + ":" + lemma);
                // this is the Lemmatized tag of the token


                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("POS");
                System.out.println(token + ":" + pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("NER");
                System.out.println(token + ":" + ne);

                System.out.println("\n\n");
            }

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);
            // this is the Stanford dependency graph of the current sentence


            Map<Integer, CorefChain> graph =
                    document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(graph.values().toString());
        }

    }
}
