package com.emedinaa.module.core.helpers;

/**
 * Created by eduardo on 29/11/16.
 */
public class StringHelper {

    public String checkNotNull(String mStr){
        if(mStr==null)return "";
        if(mStr.isEmpty())return "";
        return mStr;
    }

    public  String capitalize(String mStr)
    {
        if(mStr==null)return "";
        if(mStr.isEmpty())return "";

        String source = mStr;
        StringBuffer res = new StringBuffer();

        String[] strArr = source.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            if(stringArray.length>0){
                stringArray[0] = Character.toUpperCase(stringArray[0]);
            }
            str = new String(stringArray);
            res.append(str).append(" ");
        }

        // System.out.print("Result: " + res.toString().trim());
        return res.toString().trim();
    }
}
