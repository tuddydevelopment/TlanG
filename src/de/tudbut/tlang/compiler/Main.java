package de.tudbut.tlang.compiler;

import de.tudbut.tools.Tools;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String theString;
        while ((theString = Tools.getStdInput().readLine()) != null) {
             theString = theString
                    .replaceAll("%", "%P")
                    .replaceAll("\\\\\\*", "%T")
                    .replaceAll("\\\\-", "%S")
                    .replaceAll("\\\\#", "%B")
                    .replaceAll("\\\\<", "%L")
                    .replaceAll("\\\\>", "%R")
                    .replaceAll("\\\\'", "%U")
                    .replaceAll("\\\\,", "%D")
                    .replaceAll("\\\\\\(", "%1")
                    .replaceAll("\\\\\\)", "%2")
                    .replaceAll("\\\\n", "\n");

            boolean isInString = false;
            boolean wasInString = false;
            StringBuilder thatString = new StringBuilder();
            for (int i = 0; i < theString.length(); i++) {
                if (theString.substring(i).startsWith("(")) {
                    isInString = true;
                    i++;
                }
                if (theString.substring(i).startsWith(")")) {
                    isInString = false;
                    wasInString = true;
                }
                if (isInString)
                    thatString.append(theString, i, i + 1);
                if (wasInString) {
                    wasInString = false;

                    String theStrings = "";
                    int jlen = 0;
                    StringBuilder strings = new StringBuilder();
                    for (int j = 0; j < 512; j++) {
                        if ((theString.substring(i + 1)).startsWith("*" + j)) {
                            theStrings = strings.toString();
                            jlen = String.valueOf(j).length();
                        }
                        strings.append(thatString);
                    }
                    theString = theString.substring(0, i - 1 - thatString.length()) + theStrings + theString.substring(i + 2 + jlen);

                    thatString = new StringBuilder();
                }
                if (!isInString && !theString.substring(i).startsWith("(") && !theString.substring(i).startsWith(")")) {
                    i++;
                    String theChars = theString;
                    StringBuilder chars = new StringBuilder();
                    for (int j = 0; j < 512; j++) {
                        if (theString.substring(i).startsWith("*" + j)) {
                            theChars = theString.replaceAll(theString.substring(i - 1, i + 1 + String.valueOf(j).length()).replaceAll("\\*", "\\\\*"), chars.toString());
                        }
                        chars.append(theString.toCharArray()[i - 1]);
                    }
                    theString = theChars;
                    i--;
                }
            }
            System.out.println(("'#-" + theString)
                    .replaceAll("<", "\u001b[D")
                    .replaceAll(">", "\u001b[C")
                    .replaceAll("'", "\u001b[A")
                    .replaceAll(",", "\u001b[B")
                    .replaceAll("-", "\u001b[K")
                    .replaceAll("#", "\r")
                    .replaceAll("%T", "*")
                    .replaceAll("%S", "-")
                    .replaceAll("%B", "#")
                    .replaceAll("%L", "<")
                    .replaceAll("%R", ">")
                    .replaceAll("%U", "'")
                    .replaceAll("%D", ",")
                    .replaceAll("%P", "%")
                    .replaceAll("%1", "(")
                    .replaceAll("%2", ")"));
        }
    }
}
