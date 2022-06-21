package multicast.senseup.awareness.situation.services.packageServices.implementation;

import org.kie.api.KieServices;

import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.packageServices.PackageBuilder;

public class PackageBuilderImpl implements PackageBuilder{

    @Override
    public WorkingMemory build(WorkingMemory workingMemory, RulePackage rulePackage) {
        
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder
            .append("src/main/resources/")
            .append(rulePackage.getPkgName().replace(".", "/"))
            .append("/")
            .append(rulePackage.getFileName())
            .append(".drl");
        String fileName = fileNameBuilder.toString();
        System.out.println(fileName);
        
        workingMemory.updateVersion();

        KieServices kieServices = KieServices.Factory.get();

        //if(workingMemory.getKieFileSystem() == null){
            workingMemory.setKieFileSystem(kieServices.newKieFileSystem());

            workingMemory.addFile(fileName, rulePackage.getSourceCode());

            workingMemory.getKieFileSystem().writePomXML(workingMemory.getPom());
            workingMemory.getKieFileSystem().writeKModuleXML(workingMemory.getKmodule());
            for(var file : workingMemory.getFiles().entrySet()){
                workingMemory.getKieFileSystem().write(file.getKey(), file.getValue());
            }

            workingMemory.setKieBuilder(kieServices.newKieBuilder(workingMemory.getKieFileSystem()));
            //workingMemory.setKieContainer(kieServices.newKieContainer(workingMemory.releaseId));
        //}

        workingMemory.getKieBuilder().buildAll();

        System.out.println(workingMemory.getKieBuilder().getKieModule());

        /* workingMemory.setKieContainer(kieServices.newKieContainer());

        workingMemory.setKieBase( workingMemory.getKieContainer().getKieBase());
        workingMemory.setKieSession( workingMemory.getKieBase().newKieSession()) */;
        
        workingMemory.setReleaseId(workingMemory.getKieBuilder().getKieModule().getReleaseId());
        workingMemory.getKieContainer().updateToVersion(workingMemory.getKieBuilder().getKieModule().getReleaseId());
        
        return workingMemory;
    }
    
}
