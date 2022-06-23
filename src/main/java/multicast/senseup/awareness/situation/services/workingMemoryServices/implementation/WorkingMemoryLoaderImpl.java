package multicast.senseup.awareness.situation.services.workingMemoryServices.implementation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.marshalling.MarshallerFactory;

import multicast.senseup.awareness.situation.domain.KmoduleForm;
import multicast.senseup.awareness.situation.domain.PomForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryLoader;

public class WorkingMemoryLoaderImpl implements WorkingMemoryLoader{

    private String fileName;
    private String fileExtension;


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public WorkingMemoryLoaderImpl() {
        fileName = "workingMemory";
        fileExtension = ".save";
    }

    public WorkingMemoryLoaderImpl(String fileName, String fileExtension) {
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    @Override
    public WorkingMemory load(WorkingMemory workingMemory) {
        try{

            if(workingMemory != null){
                workingMemory.disposeSession();
                workingMemory = null;
            }

            FileInputStream fileOutputStream = new FileInputStream(fileName + fileExtension);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileOutputStream);
            System.out.println((Date) objectInputStream.readObject());
            
            String pkgName = (String) objectInputStream.readObject();
            String baseName = (String) objectInputStream.readObject();
            String sessionName = (String) objectInputStream.readObject();
            ReleaseId releaseId = (ReleaseId) objectInputStream.readObject();
            PomForm pom = (PomForm) objectInputStream.readObject();
            KmoduleForm kmodule = (KmoduleForm) objectInputStream.readObject();
            int size = objectInputStream.readInt();

            workingMemory = new WorkingMemory(pkgName, baseName, sessionName);
            for(int i = 0; i < size; i++){
                String fileName = (String) objectInputStream.readObject();
                RulePackage fileContent = (RulePackage) objectInputStream.readObject();
                workingMemory.addFile(fileName, fileContent);
            }

            workingMemory.setReleaseId(releaseId);
            workingMemory.setPom(pom);
            workingMemory.setKmodule(kmodule);
            KieBase kieBase = (KieBase) objectInputStream.readObject();
            
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            kieFileSystem.writePomXML(workingMemory.getPom());
            kieFileSystem.writeKModuleXML(workingMemory.getKmodule());
            
            for(var file : workingMemory.getFiles().entrySet()){
                kieFileSystem.write(file.getKey(), file.getValue().getSourceCode());
            }

            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
    
            KieModule kieModule = kieBuilder.getKieModule();
            KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
    
            workingMemory.setKieContainer(kieContainer);

            workingMemory.setKieBase(kieContainer.getKieBase());

            // Configura a nova KieBase para ser o mais fiel possivel à carregada do save
            Collection<KiePackage> oldPackages = kieBase.getKiePackages();
            Collection<String> oldPackagesNames = oldPackages.stream().map(p -> p.getName()).collect(Collectors.toCollection(TreeSet::new)); // Queremos apenas esses pacotes dentro da nova base

            Collection<KiePackage> newPackages = workingMemory.getKieBase().getKiePackages();
            Collection<String> newPackagesNames = newPackages.stream().map(p -> p.getName()).collect(Collectors.toCollection(TreeSet::new));

            for(var pkg : newPackagesNames){

                // Se o pacote não existir na KieBase referencia, remove da nova, se não testa as regras do pacote
                if (!oldPackagesNames.contains(pkg)){
                    workingMemory.getKieBase().removeKiePackage(pkg);
                } else {
                    KiePackage originalPkg = kieBase.getKiePackage(pkg);
                    Collection<String> oldRules = originalPkg.getRules().stream().map(r -> r.getName()).collect(Collectors.toCollection(TreeSet::new)); // Queremos apenas essas regras dentro do novo pacote

                    KiePackage newPackage = workingMemory.getKieBase().getKiePackage(pkg);
                    Collection<String> newRules = newPackage.getRules().stream().map(r -> r.getName()).collect(Collectors.toCollection(TreeSet::new));

                    // Se a regra não existir no KiePackage referencia, remove do novo
                    for(var newRule : newRules){
    
                        if(!oldRules.contains(newRule)){
                            workingMemory.getKieBase().removeRule(pkg, newRule);
                        }
                    }

                    Collection<String> oldQueries = originalPkg.getQueries().stream().map(r -> r.getName()).collect(Collectors.toCollection(TreeSet::new));

                    Collection<String> newQueries = newPackage.getQueries().stream().map(r -> r.getName()).collect(Collectors.toCollection(TreeSet::new));

                    // Se a query não existir no KiePackage referencia, remove do novo
                    for(var newQuery : newQueries){
    
                        if(!oldQueries.contains(newQuery)){
                            workingMemory.getKieBase().removeQuery(pkg, newQuery);
                        }
                    }

                    Collection<String> oldFunctions = originalPkg.getFunctionNames();

                    Collection<String> newFunctions = newPackage.getFunctionNames();

                    // Se a function não existir no KiePackage referencia, remove do novo
                    for(var newFunction : newFunctions){
    
                        if(!oldFunctions.contains(newFunction)){
                            workingMemory.getKieBase().removeFunction(pkg, newFunction);
                        }
                    }
                }
            }

            KieSession kieSession = workingMemory.getKieBase().newKieSession();
            
            @SuppressWarnings("unused")
            KieSessionConfiguration kieSessionConfiguration = (KieSessionConfiguration) objectInputStream.readObject();
            
            MarshallerFactory.newMarshaller(workingMemory.getKieBase()).unmarshall(objectInputStream, kieSession);
            workingMemory.setKieSession(kieSession);
            
            objectInputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return workingMemory;
    }
    
}
