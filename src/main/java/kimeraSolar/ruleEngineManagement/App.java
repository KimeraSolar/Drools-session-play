package kimeraSolar.ruleEngineManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.kie.api.definition.rule.Rule;

import kimeraSolar.ruleEngineManagement.configurations.RuleEngineConfiguration;
import kimeraSolar.ruleEngineManagement.configurations.implementation.DroolsRuleEngineConfiguration;
import kimeraSolar.ruleEngineManagement.domain.Fact;
import kimeraSolar.ruleEngineManagement.domain.FactForm;
import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleFileReader;
import kimeraSolar.ruleEngineManagement.services.packageServices.implementation.RuleFileReaderImpl;
import kimeraSolar.ruleEngineManagement.services.ruleEngineServices.RuleEngineManagement;
import kimeraSolar.ruleEngineManagement.services.ruleEngineServices.implementation.DroolsRuleEngineManagement;

public class App 
{

    static final String pkgName = "multicast.senseup.awareness.situation";
    static final String baseName = "rules";
    static final String sessionName = ".session";
    
    static String fileName = "workingMemory";
    static String fileExtension = ".save";

    static RuleEngineManagement ruleEngineManagement = new DroolsRuleEngineManagement();

    public static void getFileName(Scanner user_input){

        fileName = user_input.nextLine();
        fileExtension = fileName.substring(fileName.indexOf("."));
        fileName = fileName.substring(0, fileName.indexOf("."));

    }

    public static void main( String[] args ){
        app_main(args);
        //file_reader_test(args);
    }

    public static void file_reader_test( String[] args ){
        RuleFileReader ruleFileReader = new RuleFileReaderImpl();
        try {
            RulePackage rulePackage = ruleFileReader.readRuleFile("monitoramento-vacinas-backend/src/main/resources/rules/Sample.drl");
            System.out.println(rulePackage.getFileName());
            System.out.println(rulePackage.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void app_main( String[] args ){

        RuleEngineConfiguration ruleEngineConfiguration = new DroolsRuleEngineConfiguration();

        ruleEngineConfiguration.setPkgName(pkgName);
        ruleEngineConfiguration.setRulesBaseName(baseName);
        ruleEngineConfiguration.setSessionName(sessionName);

        ruleEngineManagement.setConfigurations(ruleEngineConfiguration);

        File f = new File(fileName + fileExtension);
        if(f.exists() && !f.isDirectory()) {
            // Carrega sessão previamente salva no arquivo workingMemory.save
            ruleEngineManagement.loadWorkingMemory(f.getName().replaceFirst(".save", ""));
        } else {
            // Cria sessão do zero caso não exista o arquivo workingMemory.save
            ruleEngineManagement.resetRuleEngine();
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
                                System.out.println(ruleEngineManagement.insertFact(factForm));
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
                            
                            ruleEngineManagement.insertPackage(rulePackage);;
                        break;
                    }
                break;
                
                case "find":
                    System.out.println("Escreva o hashcode do fato a ser procurado:");
                    String hashCodeToFind = user_input.nextLine();
                    if(hashCodeToFind.toLowerCase().contains("abort")) break;
                    System.out.println(ruleEngineManagement.findFact(hashCodeToFind));
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
                            ruleEngineManagement.deleteFact(hashCodeToDelete);
                            break;
                        case "rule":
                        case "rules":
                            System.out.println("Escreva o pacote da regra a ser removida");
                            String rulePkg = user_input.nextLine();
                            if(rulePkg.toLowerCase().contains("abort")) break;
                            System.out.println("Escreva a regra a ser removida");
                            String ruleToDelete = user_input.nextLine();
                            if(ruleToDelete.toLowerCase().contains("abort")) break;
                            ruleEngineManagement.deleteRule(rulePkg, ruleToDelete);
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
                            List<Fact> factsList = ruleEngineManagement.listFacts();
                            for (Fact fact : factsList){
                                System.out.println(fact);
                            }
                        break;
                        case "rules":
                        case "rule":
                            List<Rule> rulesList = ruleEngineManagement.listRules();
                            for(Rule rule : rulesList){
                                System.out.println(rule);
                            }
                        break;
                    }
                    break;

                case "save":
                    System.out.println("Escreva o nome do arquivo:");
                    getFileName(user_input);
                    ruleEngineManagement.saveWorkingMemory(fileName);
                    break;

                case "load":
                    System.out.println("Escreva o nome do arquivo:");
                    getFileName(user_input);
                    ruleEngineManagement.loadWorkingMemory(fileName);
                    break;

                case "reset":
                    ruleEngineManagement.resetRuleEngine();
                    break;
            }
            ruleEngineManagement.fireAllRules();
        } while (!words[0].toLowerCase().contains("end"));

        // Finaliza sessão do Scene
         ruleEngineManagement.clear();

        // Fecha user_input
        user_input.close();
    }
}
