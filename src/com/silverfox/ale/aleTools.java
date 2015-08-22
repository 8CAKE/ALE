package com.silverfox.ale;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class aleTools {

    public String stripHTMLTags(String htmlText) {

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while(matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return(sb.toString().trim());

    }

    public aleTools() {
    }
}
