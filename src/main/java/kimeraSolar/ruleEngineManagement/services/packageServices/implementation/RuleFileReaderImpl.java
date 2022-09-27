package kimeraSolar.ruleEngineManagement.services.packageServices.implementation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;

import kimeraSolar.ruleEngineManagement.domain.RuleForm;
import kimeraSolar.ruleEngineManagement.domain.RulePackage;
import kimeraSolar.ruleEngineManagement.services.packageServices.RuleFileReader;

public class RuleFileReaderImpl implements RuleFileReader{

    @Override
    public RulePackage readRuleFile(String ruleFileName) throws IOException {
        String pkgName = "";

        RulePackage rulePackage = new RulePackage();
        String fileName = ruleFileName.replace(".drl", "");
        int lastIndexOfDot = fileName.lastIndexOf("/");
        if( lastIndexOfDot != -1 ){
            fileName = fileName.substring(lastIndexOfDot + 1);
        }

        rulePackage.setFileName(fileName);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(ruleFileName));

        String line = bufferedReader.readLine();
        while(line != null){
            if(line.startsWith("package")){
                pkgName = 
                    line.replace("package", "")
                        .replace(";", "")
                        .replaceAll(" ", "");
                rulePackage.setPkgName(pkgName);
            } else if(line.startsWith("import")){
                String include =
                    line.replace("import", "")
                        .replace(";", "")
                        .replaceAll(" ", "");
                rulePackage.addInclude(include);
            } else if(line.startsWith("declare") || line.startsWith("rule")){
                String ruleBody = "";
                String ruleName = 
                    line.replace("\"", "")
                        .replace("declare", " ")
                        .replace("rule", "");
                
                while(!line.equals("end")){
                    ruleBody += line + "\n";
                    line = bufferedReader.readLine();
                }
                ruleBody += "end\n";
                String jsonString = "{ ruleName : \"" + ruleName + "\", source : \"" + StringEscapeUtils.escapeJava(ruleBody) + "\"}";
                RuleForm ruleForm = RuleForm.parseJson(jsonString);
                rulePackage.addRule(ruleForm);
            }
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
    
        return rulePackage;
    }
    
}
