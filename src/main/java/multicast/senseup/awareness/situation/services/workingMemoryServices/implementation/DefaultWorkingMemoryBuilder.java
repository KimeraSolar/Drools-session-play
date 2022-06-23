package multicast.senseup.awareness.situation.services.workingMemoryServices.implementation;

import org.json.JSONObject;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;

import multicast.senseup.awareness.situation.domain.BaseModelForm;
import multicast.senseup.awareness.situation.domain.KmoduleForm;
import multicast.senseup.awareness.situation.domain.PomForm;
import multicast.senseup.awareness.situation.domain.RuleForm;
import multicast.senseup.awareness.situation.domain.RulePackage;
import multicast.senseup.awareness.situation.domain.SessionModelForm;
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

        public PomForm setPlugins(PomForm pomForm){
            pomForm.addPlugin(
                "org.apache.maven.compiler.plugin", 
                "\t\t\t\t<plugin>\n" +
                "\t\t\t\t\t<groupId>org.apache.maven.plugins</groupId>\n" +
                "\t\t\t\t\t<artifactId>maven-compiler-plugin</artifactId>\n" +
                "\t\t\t\t\t<version>3.8.1</version>\n" +
                "\t\t\t\t</plugin>\n"
            );

            return pomForm;
        }

        public PomForm setProperties(PomForm pomForm){
            pomForm.addProperty("maven.compiler.release", 
            "\t\t<maven.compiler.release>11</maven.compiler.release>");

            return pomForm;
        }

        public PomForm setDependencies(PomForm pomForm){
            pomForm.addDependency(
                "org.kie.api",
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.kie</groupId>\n" + 
                "\t\t\t<artifactId>kie-api</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );

            pomForm.addDependency(
                "org.drools.core", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.drools</groupId>\n" + 
                "\t\t\t<artifactId>drools-core</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.drools.compiler", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.drools</groupId>\n" + 
                "\t\t\t<artifactId>drools-compiler</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.drools.mvel", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.drools</groupId>\n" + 
                "\t\t\t<artifactId>drools-mvel</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.drools.persistence", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.drools</groupId>\n" + 
                "\t\t\t<artifactId>drools-persistence-jpa</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.kie.ci", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.kie</groupId>\n" + 
                "\t\t\t<artifactId>kie-ci</artifactId>\n" +
                "\t\t\t<version>7.63.0.Final</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.slf4j", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.slf4j</groupId>\n" + 
                "\t\t\t<artifactId>slf4j-simple</artifactId>\n" +
                "\t\t\t<version>1.7.21</version>\n" +
                "\t\t</dependency>\n"
            );
            
            pomForm.addDependency(
                "org.json", 
                "\t\t<dependency>\n" + 
                "\t\t\t<groupId>org.json</groupId>\n" + 
                "\t\t\t<artifactId>json</artifactId>\n" +
                "\t\t\t<version>20220320</version>\n" +
                "\t\t</dependency>\n"
            );

            return pomForm;
        }

        @Override
        public PomForm getPom() {
            PomForm pomForm = new PomForm();
            pomForm.setArtifactiId( getDefaultBaseName() );
            pomForm.setPkgName( getPkgName() );
            pomForm.setName(  getDefaultBaseName() );
            pomForm.setVersion("1.0.0");

            pomForm = setDependencies(pomForm);
            pomForm = setPlugins(pomForm);
            pomForm = setProperties(pomForm);
            
            return pomForm;
        }

        @Override
        public RulePackage getSourceCode() {
            RulePackage rulePackage = new RulePackage();
            rulePackage.setPkgName( getPkgName() );
            rulePackage.setFileName("defaultRules");

            JSONObject jsonMessageDeclaration = new JSONObject();
            jsonMessageDeclaration.put("ruleName", "Message Declaration");

            StringBuilder messageDeclarationBuilder = new StringBuilder();
            messageDeclarationBuilder
                .append("declare Message\n")
                .append("    message : String\n")
                .append("    status : Integer\n")
                .append("end\n");
            jsonMessageDeclaration.put("source", messageDeclarationBuilder.toString());
            RuleForm messageDeclaration = RuleForm.parseJson( jsonMessageDeclaration.toString() );
            rulePackage.addRule(messageDeclaration);

            JSONObject jsonRuleDeclaration = new JSONObject();
            jsonRuleDeclaration.put("ruleName", "Rule Declaration");

            StringBuilder ruleDeclarationBuilder = new StringBuilder();
            ruleDeclarationBuilder
                .append("rule \"Hello World\"\n")
                .append("    dialect \"mvel\"\n")
                .append("    when\n")
                .append("        m : Message( status == 1, $message : message )\n")
                .append("    then\n")
                .append("        modify ( m ) { message = \"Goodbye cruel world\",\n")
                .append("                    status = 2 };\n")
                .append("end\n");
            jsonRuleDeclaration.put("source", ruleDeclarationBuilder.toString());
            RuleForm ruleDeclaration = RuleForm.parseJson(jsonRuleDeclaration.toString());
            rulePackage.addRule(ruleDeclaration);

            return rulePackage;
        }

        @Override
        public KmoduleForm getConfigurations() {
            KmoduleForm kieKmoduleForm = new KmoduleForm();

            BaseModelForm defaultBase = new BaseModelForm();
            defaultBase.setBaseName( getDefaultBaseName() );
            defaultBase.addPackage( getPkgName() );
            defaultBase.setDefaultBase(true);
            defaultBase.setEventMode(EventProcessingOption.STREAM);

            SessionModelForm defaultSession = new SessionModelForm();
            defaultSession.setDefaultSession(true);
            defaultSession.setSessionName( getDefaultSessionName() );
            
            defaultBase.addSession(defaultSession);
            kieKmoduleForm.addBaseModel(defaultBase);
            return kieKmoduleForm;
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
            .append("/defaultRules.drl");
        String fileName = fileNameBuilder.toString();

        kieFileSystem = kieFileSystem.write(fileName, configurations.getSourceCode().getSourceCode());
        kieFileSystem = kieFileSystem.writeKModuleXML(configurations.getConfigurations().toString());
        kieFileSystem = kieFileSystem.writePomXML(configurations.getPom().toString());

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        workingMemory.setKieSession(kieContainer.newKieSession());
        workingMemory.setKieBase(workingMemory.getKieSession().getKieBase());

        workingMemory.setKieContainer(kieContainer);
        workingMemory.setReleaseId(kieModule.getReleaseId());
        workingMemory.setPom(configurations.getPom());
        workingMemory.setKmodule(configurations.getConfigurations());
        workingMemory.addFile(fileName, configurations.getSourceCode());

        return workingMemory;
    }
    
}
