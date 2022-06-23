package multicast.senseup.awareness.situation;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.kie.api.definition.rule.Rule;

import multicast.senseup.awareness.situation.domain.Fact;
import multicast.senseup.awareness.situation.domain.FactForm;
import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.factServices.FactDeleter;
import multicast.senseup.awareness.situation.services.factServices.FactFinder;
import multicast.senseup.awareness.situation.services.factServices.FactInserter;
import multicast.senseup.awareness.situation.services.factServices.FactsLister;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactDeleter;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactFinder;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactInserter;
import multicast.senseup.awareness.situation.services.factServices.dummies.DummyFactsLister;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactDeleterImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactFinderImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactInserterImpl;
import multicast.senseup.awareness.situation.services.factServices.implementation.FactsListerImpl;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;
import multicast.senseup.awareness.situation.services.packageServices.RuleDeleter;
import multicast.senseup.awareness.situation.services.packageServices.RulesLister;
import multicast.senseup.awareness.situation.services.packageServices.dummies.DummyPackageBuilder;
import multicast.senseup.awareness.situation.services.packageServices.dummies.DummyRuleDeleter;
import multicast.senseup.awareness.situation.services.packageServices.dummies.DummyRulesLister;
import multicast.senseup.awareness.situation.services.packageServices.implementation.PackageBuilderImpl;
import multicast.senseup.awareness.situation.services.packageServices.implementation.RuleDeleterImpl;
import multicast.senseup.awareness.situation.services.packageServices.implementation.RulesListerImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryLoader;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemorySaver;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemoryLoader;
import multicast.senseup.awareness.situation.services.workingMemoryServices.dummies.DummyWorkingMemorySaver;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemoryLoaderImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.WorkingMemorySaverImpl;
import multicast.senseup.awareness.situation.services.workingMemoryServices.implementation.DefaultWorkingMemoryBuilder.WorkingMemoryConfigurationsImpl;

public class App 
{
    static final String pkgName = "multicast.senseup.awareness.situation";
    static final String baseName = "rules";
    static final String sessionName = ".session";
    
    static String fileName = "workingMemory";
    static String fileExtension = ".save";

    static WorkingMemory workingMemory = null;

    static FactFinder factFinder = new DummyFactFinder();
    static FactInserter factInserter = new DummyFactInserter();
    static FactDeleter factDeleter = new DummyFactDeleter();
    static FactsLister factsLister = new DummyFactsLister();
    static RuleDeleter ruleDeleter = new DummyRuleDeleter();
    static RulesLister rulesLister = new DummyRulesLister();
    static WorkingMemorySaver workingMemorySaver = new DummyWorkingMemorySaver();
    static WorkingMemoryLoader workingMemoryLoader = new DummyWorkingMemoryLoader();
    static WorkingMemoryBuilder workingMemoryBuilder = new DummyWorkingMemoryBuilder();
    static PackageBuilder packageBuilder = new DummyPackageBuilder();
    

    public static void setWorkingMemory(WorkingMemory newWorkingMemory){
        workingMemory = newWorkingMemory;
        factFinder = new FactFinderImpl(workingMemory);
        factInserter = new FactInserterImpl(workingMemory);
        factsLister = new FactsListerImpl(workingMemory);
        rulesLister = new RulesListerImpl(workingMemory);
        factDeleter = new FactDeleterImpl(workingMemory);
        ruleDeleter = new RuleDeleterImpl(workingMemory);
    }

    public static void getFileName(Scanner user_input){

        fileName = user_input.nextLine();
        fileExtension = fileName.substring(fileName.indexOf("."));
        fileName = fileName.substring(0, fileName.indexOf("."));

    }

    public static void main( String[] args ){

        // Configura e inicializa sessão do Drools
        workingMemoryBuilder = new DefaultWorkingMemoryBuilder();
        workingMemoryLoader = new WorkingMemoryLoaderImpl(fileName, fileExtension);
        workingMemorySaver = new WorkingMemorySaverImpl(fileName, fileExtension);
        packageBuilder = new PackageBuilderImpl();

        File f = new File(fileName + fileExtension);
        if(f.exists() && !f.isDirectory()) {
            // Carrega sessão previamente salva no arquivo workingMemory.save
            setWorkingMemory(workingMemoryLoader.load(workingMemory)); 
        } else {
            // Cria sessão do zero caso não exista o arquivo workingMemory.save
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
                    System.out.println("Escreva o que deseja inserir:");
                    String toInsert = user_input.nextLine();
                    switch(toInsert){
                        case "fact":
                        case "facts":
                            System.out.println("Escreva o fato a ser inserido em formato Json:");
                            /* Testes: 
                            *  { typeName : "Message", properties : "{ status : 1, message : \"Hello World\" }" }
                            *  { typeName : "Message", properties : "{ status : 1, message : \"Good morning World\" }" }
                            *  { typeName : "Message", properties : "{ status : 3, message : \"Não vai mudar\" }" }
                            *  { typeName : "Message", properties : "{ status : 4, message : \"Hello World\" }" } 
                            */
                            String formString = user_input.nextLine();
                            if(formString.toLowerCase().contains("abort")) break;
                            try{
                                FactForm factForm = FactForm.parseJson(formString);
                                System.out.println(factInserter.insert(factForm));
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            break;
                        case "rule":
                        case "rules":
                            System.out.println("Escreva o nome do arquivo .drl:");
                            String DrlFileName = user_input.nextLine();
                            RulePackage rulePackage = new RulePackage();
                            rulePackage.setFileName(DrlFileName);
                            /* Testes:
                            * animesongs
                            */

                            System.out.println("Escreva o nome completo do pacote");

                            /* Testes:
                             * anime.songs
                             */
                            String pkgName = user_input.nextLine();
                            rulePackage.setPkgName(pkgName);

                            /* Testes:
                             * multicast.senseup.awareness.situation.Message
                             */
                            System.out.println("Escreva os includes do pacote (end-include para finalizar):");
                            String include = user_input.nextLine();
                            while(!include.toLowerCase().contains("end-include")){
                                rulePackage.addInclude(include);
                                include = user_input.nextLine();
                            }

                            System.out.println("Escreva as regras a serem inseridas em formato Json: (end-rules para finalizar)");
                            /* Testes: 
                            *  { ruleName : "Ohayo sekai", source : "rule \"Ohayo sekai\"\n dialect \"mvel\" \n when\n m : Message( status == 2, $message : message )\n then\n modify ( m ) { message = \"Ohayo sekai Good morning World\", \n status = 3 }; \n end\n" }
                            *  { ruleName : "GURU", source: "rule \"GURU\"\n dialect \"mvel\" \n when\n m : Message( status == 4, $message : message )\n then\n modify ( m ) { message = \"Ah Wanderer Never Ending\", \n status = 3 }; \n end\n"}
                            *  { ruleName : "Naruto OP 2", source: "rule \"Haruka Kanata\"\n dialect \"mvel\" \n when\n m : Message( status == 1, $message : message )\n then\n modify ( m ) { message = \"fumikomu ze akuseru / kakehiki wa nai sa, sōda yo / yoru o nukeru\", \n status = 3 }; \n end\n"}
                            *  { ruleName : "Hello World", source: "rule \"Hello World\"\n dialect \"mvel\" \n when\n m : Message( status == 1, $message : message )\n then\n modify ( m ) { message = \"Hello World\", \n status = 3 }; \n end\n"}
                            */
                            String jsonString = user_input.nextLine();
                            while(!jsonString.toLowerCase().contains("end-rules")){
                                try{
                                    RuleForm ruleForm = RuleForm.parseJson(jsonString);
                                    rulePackage.addRule(ruleForm);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                jsonString = user_input.nextLine();
                            }
                            
                            packageBuilder.build(workingMemory, rulePackage);
                        break;
                    }
                break;
                
                case "find":
                    System.out.println("Escreva o hashcode do fato a ser procurado:");
                    String hashCodeToFind = user_input.nextLine();
                    if(hashCodeToFind.toLowerCase().contains("abort")) break;
                    System.out.println(factFinder.find(hashCodeToFind));
                    break;
                
                case "delete":
                    System.out.println("Escreva o que deseja deletar:");
                    String toDelete = user_input.nextLine();
                    switch(toDelete){
                        case "fact":
                        case "facts":
                            System.out.println("Escreva o hashcode do fato a ser deletado:");
                            String hashCodeToDelete = user_input.nextLine();
                            if(hashCodeToDelete.toLowerCase().contains("abort")) break;
                            factDeleter.delete(hashCodeToDelete);
                            break;
                        case "rule":
                        case "rules":
                            System.out.println("Escreva o pacote da regra a ser removida");
                            String rulePkg = user_input.nextLine();
                            if(rulePkg.toLowerCase().contains("abort")) break;
                            System.out.println("Escreva a regra a ser removida");
                            String ruleToDelete = user_input.nextLine();
                            if(ruleToDelete.toLowerCase().contains("abort")) break;
                            ruleDeleter.delete(rulePkg, ruleToDelete);
                            break;
                    }
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
                        case "rules":
                        case "rule":
                            List<Rule> rulesList = rulesLister.list();
                            for(Rule rule : rulesList){
                                System.out.println(rule);
                            }
                        break;
                    }
                    break;

                case "save":
                    System.out.println("Escreva o nome do arquivo:");
                    getFileName(user_input);
                    workingMemorySaver = new WorkingMemorySaverImpl(fileName, fileExtension);
                    workingMemorySaver.save(workingMemory);
                    break;

                case "load":
                    System.out.println("Escreva o nome do arquivo:");
                    getFileName(user_input);
                    workingMemoryLoader = new WorkingMemoryLoaderImpl(fileName, fileExtension);
                    setWorkingMemory(workingMemoryLoader.load(workingMemory));
                    break;

                case "reset":
                    if(workingMemory != null) workingMemory.disposeSession();
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
