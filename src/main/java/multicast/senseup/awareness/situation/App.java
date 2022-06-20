package multicast.senseup.awareness.situation;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;
import multicast.senseup.awareness.situation.services.factServices.FactsLister;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactFinder;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactInserter;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactsLister;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactFinderImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactInserterImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactsListerImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryLoader;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemorySaver;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemoryLoader;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemorySaver;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemoryBuilderImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemoryLoaderImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemorySaverImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemoryBuilderImpl.WorkingMemoryConfigurationsImpl;

public class App 
{
    static final String pkgName = "multicast.senseup.awareness.situation";
    static final String baseName = "rules";
    static final String sessionName = ".session";
    static final String fileName = "workingMemory";
    static final String fileExtension = ".save";

    static WorkingMemory workingMemory = null;

    static FactFinder factFinder = new DummyFactFinder();
    static FactInserter factInserter = new DummyFactInserter();
    static FactsLister factsLister = new DummyFactsLister();
    static WorkingMemorySaver workingMemorySaver = new DummyWorkingMemorySaver();
    static WorkingMemoryLoader workingMemoryLoader = new DummyWorkingMemoryLoader();
    static WorkingMemoryBuilder workingMemoryBuilder = new DummyWorkingMemoryBuilder();

    

    public static void setWorkingMemory(WorkingMemory newWorkingMemory){
        workingMemory = newWorkingMemory;
        factFinder = new FactFinderImpl(workingMemory);
        factInserter = new FactInserterImpl(workingMemory);
        factsLister = new FactsListerImpl(workingMemory);
    }

    public static void main( String[] args ){

        // Configura e inicializa sessão do Drools
        workingMemoryBuilder = new WorkingMemoryBuilderImpl();
        workingMemoryLoader = new WorkingMemoryLoaderImpl(fileName, fileExtension);
        workingMemorySaver = new WorkingMemorySaverImpl(fileName, fileExtension);

        File f = new File(fileName + fileExtension);
        if(f.exists() && !f.isDirectory()) {
            // Carrega sessão previamente salva
            setWorkingMemory(workingMemoryLoader.load(workingMemory)); 
        } else {
            // Cria sessão do zero
            setWorkingMemory(workingMemoryBuilder.build(
                new WorkingMemoryConfigurationsImpl(pkgName, baseName)
            ));
        }

        Scanner user_input = new Scanner(System.in);
        String sentence;
        String[] words;
        do {
            System.out.println("\nSua ação: ");
            sentence = user_input.nextLine();
            words = sentence.split(" ");
            switch(words[0].toLowerCase()){
                case "insert":
                    System.out.println("Escreva o fato a ser inserido em formato Json:");
                    /* Testes: 
                     *  { typeName : "Message", properties : "{ status : 1, message : \"Hello World\" }" }
                     *  { typeName : "Message", properties : "{ status : 3, message : \"Não vai mudar\" }" } 
                     */
                    String formString = user_input.nextLine();
                    if(formString.toLowerCase().contains("abort")) break;
                    FactForm factForm = FactForm.parseJson(formString);
                    System.out.println(factInserter.insert(factForm));
                    break;
                case "find":
                    System.out.println("Escreva o hashcode do fato a ser procurado:");
                    String hashCode = user_input.nextLine();
                    if(hashCode.toLowerCase().contains("abort")) break;
                    System.out.println(factFinder.find(hashCode));
                    break;
                case "list":
                    System.out.println("Escreva o que você deseja listar:");
                    String toList = user_input.nextLine();
                    if(toList.toLowerCase().contains("abort")) break;
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
                    workingMemorySaver.save(workingMemory);
                    break;
                case "load":
                    setWorkingMemory(workingMemoryLoader.load(workingMemory));
                    break;
                case "reset":
                    workingMemory.disposeSession();
                    setWorkingMemory(workingMemoryBuilder.build(
                        new WorkingMemoryConfigurationsImpl(pkgName, baseName)
                    ));
                    break;
            }
            workingMemory.getKieSession().fireAllRules();
        } while (!words[0].toLowerCase().contains("end"));

        // Finaliza sessão do Scene
        workingMemory.disposeSession();
        setWorkingMemory(null);

        // Fecha user_input
        user_input.close();
    }
}
