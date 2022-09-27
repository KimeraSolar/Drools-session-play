package kimeraSolar.ruleEngineManagement.utils;

public class FactHashGenerator {
    public static String generateFactHash(String pkgName, String typeName, Object obj){
        StringBuilder sbHashString = new StringBuilder();
        sbHashString
                .append(pkgName)
                .append(".")
                .append(typeName)
                .append("@")
                .append(obj.hashCode());
        return sbHashString.toString();
    }

    public static String getPkgName (String hashString){
        return hashString.substring(0, hashString.lastIndexOf("."));
    }

    public static String getTypeName(String hashString){
        return hashString.substring(hashString.lastIndexOf(".") + 1, hashString.lastIndexOf("@"));
    }

    public static String getObjectHash(String hashString){
        return hashString.substring(hashString.lastIndexOf("@") + 1);
    }
}
