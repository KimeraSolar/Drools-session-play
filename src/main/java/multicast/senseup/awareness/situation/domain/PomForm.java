package multicast.senseup.awareness.situation.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PomForm implements Serializable {

    private String PkgName;
    private String artifactiId;
    private String name;
    private String version;

    private Map<String, String> properties = new HashMap<>();
    private Map<String, String> dependencies = new HashMap<>();
    private Map<String, String> plugins = new HashMap<>();


    public String getPkgName() {
        return this.PkgName;
    }

    public void setPkgName(String PkgName) {
        this.PkgName = PkgName;
    }

    public String getArtifactiId() {
        return this.artifactiId;
    }

    public void setArtifactiId(String artifactiId) {
        this.artifactiId = artifactiId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String,String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String,String> properties) {
        this.properties = properties;
    }

    public void addProperty(String name, String code){
        this.properties.put(name, code);
    }

    public void removeProperty(String name){
        this.properties.remove(name);
    }

    public String getProperty(String name){
        return this.properties.get(name);
    }

    public Map<String,String> getDependencies() {
        return this.dependencies;
    }

    public void setDependencies(Map<String,String> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(String name, String code){
        this.dependencies.put(name, code);
    }

    public void removeDependency(String name){
        this.dependencies.remove(name);
    }

    public String getDependency(String name){
        return this.dependencies.get(name);
    }

    public Map<String,String> getPlugins() {
        return this.plugins;
    }

    public void setPlugins(Map<String,String> plugins) {
        this.plugins = plugins;
    }

    public void addPlugin(String name, String code){
        this.plugins.put(name, code);
    }

    public void removePlugin(String name){
        this.plugins.remove(name);
    }

    public String getPlugin(String name){
        return this.plugins.get(name);
    }

    public String getPom(){

        StringBuilder propertiesBuilder = new StringBuilder();

        if(properties.size() > 0){
            propertiesBuilder.append("\t<properties>\n");
            for(var prop : properties.values()){
                propertiesBuilder.append(prop).append("\n");
            }
            propertiesBuilder.append("\t</properties>\n");
        }

        StringBuilder pluginBuilder = new StringBuilder();

        if(plugins.size() > 0){
            pluginBuilder.append("\t<build>\n");
            pluginBuilder.append("\t\t<pluginManagement>\n");
            pluginBuilder.append("\t\t\t<plugins>\n");

            for(var plugin : plugins.values()){
                pluginBuilder.append(plugin).append("\n");
            }
            
            pluginBuilder.append("\t\t\t</plugins>\n");
            pluginBuilder.append("\t\t</pluginManagement>\n");
            pluginBuilder.append("\t</build>\n");
        }

        StringBuilder dependenciesBuilder = new StringBuilder();

        if(dependencies.size() > 0){
            dependenciesBuilder.append("\t<dependencies>\n");
            
            for(var dependency : dependencies.values()){
                dependenciesBuilder.append(dependency).append("\n");
            }

            dependenciesBuilder.append("\t</dependencies>\n");
        }

        StringBuilder pomBuilder = new StringBuilder();

        pomBuilder
            .append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n")
            .append("   <modelVersion>4.0.0</modelVersion>\n")
            .append("   <groupId>").append( getPkgName() ).append("</groupId>\n")
            .append("   <artifactId>").append( getArtifactiId() ).append("</artifactId>\n")
            .append("   <packaging>jar</packaging>\n")
            .append("   <version>").append( getVersion() ).append("</version>\n")
            .append("   <name>").append( getName() ).append("</name>\n")
            .append(propertiesBuilder)
            .append(pluginBuilder)
            .append(dependenciesBuilder)
            .append("</project>");

        return pomBuilder.toString();
    }


    @Override
    public String toString() {
        return getPom();
    }

    
}
