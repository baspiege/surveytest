package surveytest.utils;

public class StringUtils
{
    public static boolean isEmpty(String aString)
    {
        return aString==null || aString.trim().length()==0;
    }
}
