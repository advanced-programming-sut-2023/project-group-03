package view;


import view.Enums.ConsoleColors;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Captcha {
    public static String[] array = new String[5];
    public static HashMap<Character,String> ASCII_Chars = new HashMap<>();
    public static char[] smalls = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r'
            , 't', 'u', 'v', 'w', 'x', 'y'};
    public static char[] numbers;
    public static char [] capitals;
    static {
        ASCII_Chars.put('A',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "███████║ \n" +
                        "██╔══██║ \n" +
                        "██║░░██║ \n" +
                        "╚═╝░░╚═╝ ");
        ASCII_Chars.put('B',
                "██████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██████╦╝ \n" +
                        "██╔══██╗ \n" +
                        "██████╦╝ \n" +
                        "╚═════╝░ ");
        ASCII_Chars.put('C',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██║░░╚═╝ \n" +
                        "██║░░██╗ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('D',
                "██████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██║░░██║ \n" +
                        "██║░░██║ \n" +
                        "██████╔╝ \n" +
                        "╚═════╝░ ");
        ASCII_Chars.put('E',
                "███████╗ \n" +
                        "██╔════╝ \n" +
                        "█████╗░░ \n" +
                        "██╔══╝░░ \n" +
                        "███████╗ \n" +
                        "╚══════╝ ");
        ASCII_Chars.put('F',
                "███████╗ \n" +
                        "██╔════╝ \n" +
                        "█████╗░░ \n" +
                        "██╔══╝░░ \n" +
                        "██║░░░░░ \n" +
                        "╚═╝░░░░░ ");
        ASCII_Chars.put('G',
                "░██████╗░ \n" +
                        "██╔════╝░ \n" +
                        "██║░░██╗░ \n" +
                        "██║░░╚██╗ \n" +
                        "╚██████╔╝ \n" +
                        "░╚═════╝░ ");
        ASCII_Chars.put('H',
                "██╗░░██╗ \n" +
                        "██║░░██║ \n" +
                        "███████║ \n" +
                        "██╔══██║ \n" +
                        "██║░░██║ \n" +
                        "╚═╝░░╚═╝ ");
        ASCII_Chars.put('I',
                "██╗ \n" +
                        "██║ \n" +
                        "██║ \n" +
                        "██║ \n" +
                        "██║ \n" +
                        "╚═╝ ");
        ASCII_Chars.put('J',
                "░░░░░██╗ \n" +
                        "░░░░░██║ \n" +
                        "░░░░░██║ \n" +
                        "██╗░░██║ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('K',
                "██╗░░██╗ \n" +
                        "██║░██╔╝ \n" +
                        "█████═╝░ \n" +
                        "██╔═██╗░ \n" +
                        "██║░╚██╗ \n" +
                        "╚═╝░░╚═╝ ");
        ASCII_Chars.put('L',
                "██╗░░░░░ \n" +
                        "██║░░░░░ \n" +
                        "██║░░░░░ \n" +
                        "██║░░░░░ \n" +
                        "███████╗ \n" +
                        "╚══════╝ ");
        ASCII_Chars.put('M',
                "███╗░░░███╗ \n" +
                        "████╗░████║ \n" +
                        "██╔████╔██║ \n" +
                        "██║╚██╔╝██║ \n" +
                        "██║░╚═╝░██║ \n" +
                        "╚═╝░░░░░╚═╝ ");
        ASCII_Chars.put('N',
                "███╗░░██╗ \n" +
                        "████╗░██║ \n" +
                        "██╔██╗██║ \n" +
                        "██║╚████║ \n" +
                        "██║░╚███║ \n" +
                        "╚═╝░░╚══╝ ");
        ASCII_Chars.put('O',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██║░░██║ \n" +
                        "██║░░██║ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('P',
                "██████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██████╔╝ \n" +
                        "██╔═══╝░ \n" +
                        "██║░░░░░ \n" +
                        "╚═╝░░░░░ ");
        ASCII_Chars.put('Q',
                "░██████╗░ \n" +
                        "██╔═══██╗ \n" +
                        "██║██╗██║ \n" +
                        "╚██████╔╝ \n" +
                        "░╚═██╔═╝░ \n" +
                        "░░░╚═╝░░░ ");
        ASCII_Chars.put('R',
                "██████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██████╔╝ \n" +
                        "██╔══██╗ \n" +
                        "██║░░██║ \n" +
                        "╚═╝░░╚═╝ ");
        ASCII_Chars.put('S',
                "░██████╗ \n" +
                        "██╔════╝ \n" +
                        "╚█████╗░ \n" +
                        "░╚═══██╗ \n" +
                        "██████╔╝ \n" +
                        "╚═════╝░ ");
        ASCII_Chars.put('T',
                "████████╗ \n" +
                        "╚══██╔══╝ \n" +
                        "░░░██║░░░ \n" +
                        "░░░██║░░░ \n" +
                        "░░░██║░░░ \n" +
                        "░░░╚═╝░░░ ");
        ASCII_Chars.put('U',
                "██╗░░░██╗ \n" +
                        "██║░░░██║ \n" +
                        "██║░░░██║ \n" +
                        "██║░░░██║ \n" +
                        "╚██████╔╝ \n" +
                        "░╚═════╝░ ");
        ASCII_Chars.put('V',
                "██╗░░░██╗ \n" +
                        "██║░░░██║ \n" +
                        "╚██╗░██╔╝ \n" +
                        "░╚████╔╝░ \n" +
                        "░░╚██╔╝░░ \n" +
                        "░░░╚═╝░░░ ");
        ASCII_Chars.put('W',
                "░██╗░░░░░░░██╗ \n" +
                        "░██║░░██╗░░██║ \n" +
                        "░╚██╗████╗██╔╝ \n" +
                        "░░████╔═████║░ \n" +
                        "░░╚██╔╝░╚██╔╝░ \n" +
                        "░░░╚═╝░░░╚═╝░░ ");
        ASCII_Chars.put('X',
                "██╗░░██╗ \n" +
                        "╚██╗██╔╝ \n" +
                        "░╚███╔╝░ \n" +
                        "░██╔██╗░ \n" +
                        "██╔╝╚██╗ \n" +
                        "╚═╝░░╚═╝ ");
        ASCII_Chars.put('Y',
                "██╗░░░██╗ \n" +
                        "╚██╗░██╔╝ \n" +
                        "░╚████╔╝░ \n" +
                        "░░╚██╔╝░░ \n" +
                        "░░░██║░░░ \n" +
                        "░░░╚═╝░░░ ");
        ASCII_Chars.put('Z',
                "███████╗ \n" +
                        "╚════██║ \n" +
                        "░░███╔═╝ \n" +
                        "██╔══╝░░ \n" +
                        "███████╗ \n" +
                        "╚══════╝ ");
        ASCII_Chars.put('a', "╋╋╋╋" + "\n"+
                "╔══╗\n" +
                "║╔╗║\n" +
                "║╔╗║\n" +
                "╚╝╚╝\n"+
                "░░░░");
        ASCII_Chars.put('b',
                "╔╗  \n" +
                        "║║  \n" +
                        "║╚═╗\n" +
                        "║╔╗║\n" +
                        "║╚╝║\n" +
                        "╚══╝");
        ASCII_Chars.put('c', "╋╋╋╋" + "\n" +
                "╔══╗\n" +
                "║╔═╝\n" +
                "║╚═╗\n" +
                "╚══╝\n"+"░░░░");
        ASCII_Chars.put('d',
                "──╔╗\n" +
                        "──║║\n" +
                        "╔═╝║\n" +
                        "║╔╗║\n" +
                        "║╚╝║\n" +
                        "╚══╝");
        ASCII_Chars.put('e', "╋╋╋╋" + "\n" +
                "╔══╗\n" +
                "║║═╣\n" +
                "║║═╣\n" +
                "╚══╝\n░░░░");
        ASCII_Chars.put('f',
                "─╔═╗\n" +
                        "─║╔╝\n" +
                        "╔╝╚╗\n" +
                        "╚╗╔╝\n" +
                        "─║║ \n" +
                        "─╚╝ ");
        ASCII_Chars.put('g',
                "╔══╗\n" +
                        "║╔╗║\n" +
                        "║╚╝║\n" +
                        "╚═╗║\n" +
                        "╔═╝║\n" +
                        "╚══╝");
        ASCII_Chars.put('h',
                "╔╗  \n" +
                        "║║  \n" +
                        "║╚═╗\n" +
                        "║╔╗║\n" +
                        "║║║║\n" +
                        "╚╝╚╝");
        ASCII_Chars.put('i', "╋╋" + "\n" +
                "╔╗\n" +
                "╠╣\n" +
                "║║\n" +
                "╚╝\n░░");
        ASCII_Chars.put('j',
                "─╔╗\n" +
                        "─╚╝\n" +
                        "─╔╗\n" +
                        "─║║\n" +
                        "╔╝║\n" +
                        "╚═╝");
        ASCII_Chars.put('k', "░░░\n" +
                "╔╗ \n" +
                "║╠╗\n" +
                "║═╣\n" +
                "╚╩╝" + "\n╋╋╋");
        ASCII_Chars.put('l',
                "╔╗ \n" +
                        "║║ \n" +
                        "║║ \n" +
                        "║║ \n" +
                        "║╚╗\n" +
                        "╚═╝");
        ASCII_Chars.put('m',"░░░░\n" +
                "╔╗╔╗\n" +
                "║╚╝║\n" +
                "║║║║\n" +
                "╚╩╩╝\n"+
                "╋╋╋╋");
        ASCII_Chars.put('n', "░░░░\n" +
                "╔═╗ \n" +
                "║╔╗╗\n" +
                "║║║║\n" +
                "╚╝╚╝" + "\n╋╋╋╋");
        ASCII_Chars.put('o', "░░░░\n" +
                "╔══╗\n" +
                "║╔╗║\n" +
                "║╚╝║\n" +
                "╚══╝" + "\n╋╋╋╋");
        ASCII_Chars.put('p',
                "╔══╗\n" +
                        "║╔╗║\n" +
                        "║╚╝║\n" +
                        "║╔═╝\n" +
                        "║║  \n" +
                        "╚╝  ");
        ASCII_Chars.put('q',
                "╔══╗\n" +
                        "║╔╗║\n" +
                        "║╚╝║\n" +
                        "╚═╗║\n" +
                        "──║║\n" +
                        "──╚╝");
        ASCII_Chars.put('r', "░░░\n" +
                "╔═╗\n" +
                "║╔╝\n" +
                "║║ \n" +
                "╚╝ " + "\n╋╋╋");
        ASCII_Chars.put('s', "░░░░░\n" +
                "╔═══╗\n" +
                "║ ══╣\n" +
                "╠══ ║\n" +
                "╚═══╝"+ "\n╋╋╋╋╋");
        ASCII_Chars.put('t',
                "─╔╗ \n" +
                        "╔╝╚╗\n" +
                        "╚╗╔╝\n" +
                        "─║║ \n" +
                        "─║╚╗\n" +
                        "─╚═╝");
        ASCII_Chars.put('u', "░░░░\n" +
                "╔╗╔╗\n" +
                "║║║║\n" +
                "║╚╝║\n" +
                "╚══╝" + "\n╋╋╋╋");
        ASCII_Chars.put('v', "░░ ░░\n" +
                "╔═╦═╗\n" +
                "╚╗║╔╝\n" +
                " ║║║ \n"+
                "─╚═╝ "+ "\n╋╋ ╋╋");
        ASCII_Chars.put('w', "╋░░╋╋╋\n" +
                "╔╗╔╗╔╗\n" +
                "║╚╝╚╝║\n" +
                "╚╗╔╗╔╝\n" +
                "─╚╝╚╝ " + "\n░╋╋░░╋");
        ASCII_Chars.put('x', "╋╋╋╋" + "\n" +
                "╔╗╔╗\n" +
                "╚╬╬╝\n" +
                "╔╬╬╗\n" +
                "╚╝╚╝" + "\n░░░░");
        ASCII_Chars.put('y',
                "╔╗─╔╗\n" +
                        "║║─║║\n" +
                        "║╚═╝║\n" +
                        "╚═╗╔╝\n" +
                        "╔═╝║ \n" +
                        "╚══╝ ");
        ASCII_Chars.put('z', "╋ ╋░╋╋" + "\n" +
                "╔════╗\n" +
                "╠═══ ║\n" +
                "║ ═══╣\n" +
                "╚════╝" + "\n ░░░░");
        ASCII_Chars.put('1',
                "░░███╗░░ \n" +
                        "░████║░░ \n" +
                        "██╔██║░░ \n" +
                        "╚═╝██║░░ \n" +
                        "███████╗ \n" +
                        "╚══════╝ ");
        ASCII_Chars.put('2',
                "██████╗░ \n" +
                        "╚════██╗ \n" +
                        "░░███╔═╝ \n" +
                        "██╔══╝░░ \n" +
                        "███████╗ \n" +
                        "╚══════╝ ");
        ASCII_Chars.put('3',
                "██████╗░ \n" +
                        "╚════██╗ \n" +
                        "░█████╔╝ \n" +
                        "░╚═══██╗ \n" +
                        "██████╔╝ \n" +
                        "╚═════╝░ ");
        ASCII_Chars.put('4',
                "░░██╗██╗ \n" +
                        "░██╔╝██║ \n" +
                        "██╔╝░██║ \n" +
                        "███████║ \n" +
                        "╚════██║ \n" +
                        "░░░░░╚═╝ ");
        ASCII_Chars.put('5',
                "███████╗ \n" +
                        "██╔════╝ \n" +
                        "██████╗░ \n" +
                        "╚════██╗ \n" +
                        "██████╔╝ \n" +
                        "╚═════╝░ ");
        ASCII_Chars.put('6',
                "░█████╗░ \n" +
                        "██╔═══╝░ \n" +
                        "██████╗░ \n" +
                        "██╔══██╗ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('7',
                "███████╗ \n" +
                        "╚════██║ \n" +
                        "░░░░██╔╝ \n" +
                        "░░░██╔╝░ \n" +
                        "░░██╔╝░░ \n" +
                        "░░╚═╝░░░ ");
        ASCII_Chars.put('8',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "╚█████╔╝ \n" +
                        "██╔══██╗ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('9',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "╚██████║ \n" +
                        "░╚═══██║ \n" +
                        "░█████╔╝ \n" +
                        "░╚════╝░ ");
        ASCII_Chars.put('0',
                "░█████╗░ \n" +
                        "██╔══██╗ \n" +
                        "██║░░██║ \n" +
                        "██║░░██║ \n" +
                        "╚█████╔╝ \n" +
                        "░╚════╝░ ");
        String charactars = "ABCDEFGHIJKLMNOPQRTUVWXY";
        capitals = new char[charactars.length()];
        String digits = "123467890";
        numbers = new char[digits.length()];
        for (int i = 0; i < charactars.length(); i++) {
            capitals[i] = charactars.charAt(i);
        }
        for (int i = 0; i < digits.length(); i++) {
            numbers[i] = digits.charAt(i);
        }
    }

    public static String makeCaptcha() {
        Random random = new Random();
        String buff = new String();
        buff += capitals[Math.abs(random.nextInt() % capitals.length)];
        buff += smalls[Math.abs(random.nextInt() % smalls.length)];
        buff += smalls[Math.abs(random.nextInt() % smalls.length)];
        buff += smalls[Math.abs(random.nextInt() % smalls.length)];
        buff += capitals[Math.abs(random.nextInt() % capitals.length)];
        buff += numbers[Math.abs(random.nextInt() % numbers.length)];
        buff += smalls[Math.abs(random.nextInt() % smalls.length)];
        buff += capitals[Math.abs(random.nextInt() % capitals.length)];
        System.out.println(ASCIIPrinter(buff));
        //return buff;
        return ASCIIPrinter(buff);
    }

    public static String stringMatcher(ArrayList<String> strings) {
        Random random=new Random();
        StringBuilder ans = new StringBuilder();
        ArrayList<String[]> linesOfStrings = new ArrayList<>();
        for (String current : strings) {
            String color = ConsoleColors.getColorByBynumber(random.nextInt());
            String[] array = current.split("\n");
            for (int i = 0; i < array.length; i++) {
                array[i] = color + array[i] + ConsoleColors.TEXT_RESET;
            }
            linesOfStrings.add(array);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < strings.size(); j++) {
                if (linesOfStrings.get(j).length > i) {
                    ans.append(linesOfStrings.get(j)[i]);
                }
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    public static String ASCIIPrinter(String input) {
        ArrayList<String> ascii = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            ascii.add(ASCII_Chars.get(input.charAt(i)));
        }
        return stringMatcher(ascii);
    }
    public static void main(String[] args) {
        System.out.println(ASCIIPrinter("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(ASCIIPrinter("ABCDEFGHIJKLMNLOPQRSTUVWXYZ"));
        System.out.println(ASCIIPrinter("123456789"));
        System.out.println("============================");
        System.out.println(makeCaptcha());
    }

}
