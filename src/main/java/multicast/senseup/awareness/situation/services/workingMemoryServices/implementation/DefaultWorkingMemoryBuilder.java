package multicast.senseup.awareness.situation.services.workingMemoryServices.implementation;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;

import multicast.senseup.awareness.situation.domain.WorkingMemory;
import multicast.senseup.awareness.situation.services.workingMemoryServices.WorkingMemoryBuilder;

public class DefaultWorkingMemoryBuilder implements WorkingMemoryBuilder {

    public static class WorkingMemoryConfigurationsImpl implements WorkingMemoryConfigurations{

        private String pkgName;
        private String defaultBaseName;

        public WorkingMemoryConfigurationsImpl() {
            this.pkgName = "com.sample";
            this.defaultBaseName = "drools-session";
        }

        public WorkingMemoryConfigurationsImpl(String pkgName, String defaultBaseName ) {
            this.pkgName = pkgName;
            this.defaultBaseName = defaultBaseName;
        }

        public String getPkgName(){
            return this.pkgName;
        }

        public void setPkgName(String pkgName){
            this.pkgName = pkgName;
        }

        public String getDefaultBaseName(){
            return this.defaultBaseName;
        }

        public void setDefaultBaseName(String defaultBaseName){
            this.defaultBaseName = defaultBaseName;
        }

        public String getDefaultSessionName() {
            return pkgName + ".session";
        }

        @Override
        public String getPom() {
            StringBuilder pomBuilder = new StringBuilder();
            pomBuilder
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n")
                .append("         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n")
                .append("         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n")
                .append("    <modelVersion>4.0.0</modelVersion>\n")
                .append("\n")
                .append("    <groupId>" + getPkgName() + "</groupId>\n")
                .append("    <artifactId>" + getDefaultBaseName() + "</artifactId>\n")
                .append("    <version>1.0-SNAPSHOT</version>\n")
                .append("    <name>" + getDefaultBaseName() + "</name>\n")
                .append("\n")
                .append("    <!-- Configure Java 11-->\n")
                .append("    <properties>\n")
                .append("        <maven.compiler.release>11</maven.compiler.release>\n")
                .append("    </properties>\n")
                .append("\n")
                .append("    <!-- By default your version of Maven might use an old version of the maven-compiler-plugin that is not compatible with Java 9 or later versions. To target Java 9 or later, you should at least use version 3.6.0 of the maven-compiler-plugin -->\n")
                .append("    <build>\n")
                .append("        <pluginManagement>\n")
                .append("            <plugins>\n")
                .append("                <plugin>\n")
                .append("                    <groupId>org.apache.maven.plugins</groupId>\n")
                .append("                    <artifactId>maven-compiler-plugin</artifactId>\n")
                .append("                    <version>3.8.1</version>\n")
                .append("                </plugin>\n")
                .append("            </plugins>\n")
                .append("        </pluginManagement>\n")
                .append("    </build>\n")
                .append("\n")
                .append("    <dependencies>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.kie</groupId>\n")
                .append("            <artifactId>kie-api</artifactId>\n")
                .append("            <version>7.63.0.Final</version>\n")
                .append("        </dependency>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.drools</groupId>\n")
                .append("            <artifactId>drools-core</artifactId>\n")
                .append("            <version>7.63.0.Final</version>\n")
                .append("        </dependency>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.drools</groupId>\n")
                .append("            <artifactId>drools-compiler</artifactId>\n")
                .append("            <version>7.63.0.Final</version>\n")
                .append("        </dependency>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.drools</groupId>\n")
                .append("            <artifactId>drools-mvel</artifactId>\n")
                .append("            <version>7.63.0.Final</version>\n")
                .append("        </dependency>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.kie</groupId>\n")
                .append("            <artifactId>kie-ci</artifactId>\n")
                .append("            <version>7.63.0.Final</version>\n")
                .append("        </dependency>\n")
                .append("\n")
                .append("        <dependency>\n")
                .append("            <groupId>org.slf4j</groupId>\n")
                .append("            <artifactId>slf4j-simple</artifactId>\n")
                .append("            <version>1.7.21</version>\n")
                .append("          </dependency>\n")
                .append("        \n")
                .append("    </dependencies>\n")
                .append("\n")
                .append("</project>");
            return pomBuilder.toString();
        }

        @Override
        public String getSourceCode() {
            StringBuilder sourceBuilder = new StringBuilder();
            sourceBuilder
                .append("package ").append( getPkgName() ).append(";\n")
                .append("\n")
                .append("declare Message\n")
                .append("    message : String\n")
                .append("    status : Integer\n")
                .append("end\n")
                .append("\n")
                .append("rule \"Hello World\"\n")
                .append("    dialect \"mvel\"\n")
                .append("    when\n")
                .append("        m : Message( status == 1, $message : message )\n")
                .append("    then\n")
                .append("        modify ( m ) { message = \"Goodbye cruel world\",\n")
                .append("                    status = 2 };\n")
                .append("end\n");
            return sourceBuilder.toString();
        }

        @Override
        public String getConfigurations() {
            KieServices kieServices = KieServices.Factory.get();
            KieModuleModel kieModule = kieServices.newKieModuleModel();
            
            KieBaseModel defaultBase = kieModule.newKieBaseModel(getDefaultBaseName());
            defaultBase = defaultBase.setDefault(true);
            defaultBase = defaultBase.addPackage(getPkgName());
            defaultBase = defaultBase.setEventProcessingMode(EventProcessingOption.STREAM);
            
            KieSessionModel sessionModel = defaultBase.newKieSessionModel(getDefaultSessionName());
            sessionModel = sessionModel.setDefault(true);

            return kieModule.toXML();
        }

        public String getLog4jDotProperties(){
            StringBuilder log4jBuilder = new StringBuilder();
            log4jBuilder
                .append("log4j.appender.console=org.apache.log4j.ConsoleAppender\n")
                .append("log4j.appender.console.layout=org.apache.log4j.PatternLayout\n")
                .append("log4j.appender.console.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n\n")
                .append("\n")
                .append("log4j.appender.file=org.apache.log4j.RollingFileAppender\n")
                .append("log4j.appender.file.maxFileSize=100KB\n")
                .append("log4j.appender.file.maxBackupIndex=5\n")
                .append("log4j.appender.file.File=session.log\n")
                .append("log4j.appender.file.threshold=DEBUG\n")
                .append("log4j.appender.file.layout=org.apache.log4j.PatternLayout\n")
                .append("log4j.appender.file.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n\n")
                .append("\n")
                .append("log4j.rootLogger=DEBUG,console,file");
            
            return log4jBuilder.toString();
        }
        
    }

    @Override
    public WorkingMemory build(WorkingMemoryConfigurations configurations) {
        WorkingMemory workingMemory = new WorkingMemory(configurations.getPkgName(), configurations.getDefaultBaseName(), configurations.getDefaultSessionName());

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder
            .append("src/main/resources/")
            .append(configurations.getPkgName().replace(".", "/"))
            .append("/rules.drl");
        String fileName = fileNameBuilder.toString();
        
        kieFileSystem = kieFileSystem.write(fileName, configurations.getSourceCode());
        kieFileSystem = kieFileSystem.writeKModuleXML(configurations.getConfigurations());
        kieFileSystem = kieFileSystem.writePomXML(configurations.getPom());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        workingMemory.setKieSession(kieContainer.newKieSession());
        workingMemory.setKieBase(workingMemory.getKieSession().getKieBase());

        workingMemory.setKieBuilder(kieBuilder);
        workingMemory.setKieFileSystem(kieFileSystem);
        workingMemory.setKieContainer(kieContainer);
        workingMemory.setReleaseId(kieModule.getReleaseId());
        workingMemory.setPom(configurations.getPom());
        workingMemory.setKmodule(configurations.getConfigurations());
        workingMemory.addFile(fileName, configurations.getSourceCode());

        return workingMemory;
    }
    
}
