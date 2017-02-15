package com.ymt;

import com.ymt.util.Common;
import com.ymt.util.Umeng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClass {
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        System.out.print(Common.buildCommand());
        String cmd = readUserInput("please input your command: ");

        boolean result = false;
        if (cmd.equals("1")) {
            result = Umeng.formatData();
        } else if (cmd.equals("2")) {
            System.exit(0);
        }
        if (result) {
            System.out.print("success!");
        }
    }

    private static String readUserInput(String prompt) {
        try {
            String result;
            do {
                System.out.print(prompt);
                InputStreamReader is_reader = new InputStreamReader(System.in);
                result = new BufferedReader(is_reader).readLine();
            } while (result.equals(""));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
