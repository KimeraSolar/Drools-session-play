package multicast.senseup.awareness.situation;

import java.util.List;
import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.scene.util.RuleEngineController;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;
import multicast.senseup.awareness.situation.services.factServices.FactsLister;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactFinder;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactInserter;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactsLister;

public class App 
{
    static final String pkgName = "multicast.senseup.awareness.situation";
    static final String baseName = "rules";
    static final String sessionName = pkgName + ".session";
    static final String sceneAppName = "Fever";

    static FactFinder factFinder = new DummyFactFinder();
    static FactInserter factInserter = new DummyFactInserter();
    static FactsLister factsLister = new DummyFactsLister();

    public static void main( String[] args ){

        // Configura e inicializa sessão do Scene
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession(sessionName);

        final RuleEngineController eng = new RuleEngineController(kSession);
        eng.start();

        Scanner user_input = new Scanner(System.in);
        String sentence;
        String[] words;

        do {
            System.out.println("Sua ação: ");
            sentence = user_input.nextLine();
            words = sentence.split(" ");
            switch(words[0].toLowerCase()){
                case "insert":
                    // TODO: insere fato na working memory
                    System.out.println("Escreva o fato a ser inserido em formato Json:");
                    // Teste: { typeName : "Message", properties : "{ status : 1, message : \"Hello World\" }" }
                    String formString = user_input.nextLine();
                    FactForm factForm = FactForm.parseJson(formString);
                    factInserter.insert(factForm);
                    break;
                case "list":
                    // TODO: lista coisas
                    System.out.println("Escreva o que você deseja listar:");
                    String toList = user_input.nextLine();
                    switch (toList.toLowerCase()){
                        case "facts":
                        case "fact":
                            List<Fact> factsList = factsLister.list();
                            for (Fact fact : factsList){
                                System.out.println(fact);
                            }
                        break;
                    }
                    break;
                case "save":
                    System.out.println("Isso ainda não faz D:");
                    // TODO: salva working memory
                    break;
            }
        } while (!words[0].toLowerCase().contains("end"));

        // Finaliza sessão do Scene
        eng.end();

        // Fecha user_input
        user_input.close();
    }
}
