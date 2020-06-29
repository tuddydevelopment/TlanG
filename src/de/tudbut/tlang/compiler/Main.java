package de.tudbut.tlang.compiler;

import de.tudbut.tools.FileRW;
import de.tudbut.tools.Tools;
import de.tudbut.tools.bintools.BinFileRW;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.print("\r\u001b[K");
        StringBuilder s = new StringBuilder();
        if(args[0].endsWith(".tlg.b64")) {
            BinFileRW binFile = new BinFileRW(args[0]);
            byte[] toDecode = new byte[binFile.getBinContent().length];
            for (int i = 0; i < toDecode.length; i++) {
                toDecode[i] = (byte) binFile.getBinContent()[i];
            }
            for (byte b : Base64.getDecoder().decode(toDecode)) {
                s.append((char) b);
            }
        }
        else if(args[0].endsWith(".tlg")) {
            s = new StringBuilder(new FileRW(args[0]).getContent().join(""));

            byte[] toEncode = new byte[s.length()];
            for (int i = 0; i < toEncode.length; i++) {
                toEncode[i] = (byte) s.toString().toCharArray()[i];
            }

            toEncode = Base64.getEncoder().encode(toEncode);

            int[] toWrite = new int[toEncode.length];
            for (int i = 0; i < toWrite.length; i++) {
                toWrite[i] = toEncode[i];
            }
            new BinFileRW(args[0] + ".b64").setBinContent(toWrite);

            new FileRW("short_" + args[0]).setContent(s.toString().replaceAll("\n", ""));
        }
        String theString = s.toString();
        theString = theString
                .replaceAll("\\\\\\*", "\u0000")
                .replaceAll("\\\\-", "\u0001")
                .replaceAll("\\\\#", "\u0002")
                .replaceAll("\\\\<", "\u0003")
                .replaceAll("\\\\>", "\u0004")
                .replaceAll("\\\\'", "\u0005")
                .replaceAll("\\\\,", "\u0006")
                .replaceAll("\\\\\\(", "\u0007")
                .replaceAll("\\\\\\)", "\u0008")
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
            if (!theString.substring(i).startsWith("(") && !theString.substring(i).startsWith(")")) {
                i++;
                String theChars = theString;
                StringBuilder chars = new StringBuilder();
                for (int j = 0; j < 128; j++) {
                    if (theString.substring(i).startsWith("*" + j)) {
                        theChars = theString.substring(0, i - 1) + chars.toString() + theString.substring(i + 1 + String.valueOf(j).length());
                    }
                    chars.append(theString.toCharArray()[i - 1]);
                }
                theString = theChars;
                i--;
            }
        }
        System.out.print(theString
                .replaceAll("<", "\u001b[D")
                .replaceAll(">", "\u001b[C")
                .replaceAll("'", "\u001b[A")
                .replaceAll(",", "\u001b[B")
                .replaceAll("-", "\u001b[K")
                .replaceAll("#", "\r")
                .replaceAll("\\x{0000}", "*")
                .replaceAll("\\x{0001}", "-")
                .replaceAll("\\x{0002}", "#")
                .replaceAll("\\x{0003}", "<")
                .replaceAll("\\x{0004}", ">")
                .replaceAll("\\x{0005}", "'")
                .replaceAll("\\x{0006}", ",")
                .replaceAll("\\x{0007}", "(")
                .replaceAll("\\x{0008}", ")"));
        System.out.println();
    }
}
