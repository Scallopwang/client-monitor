/*
@File  : ExecuteCommand.java
@Author: WZC
@Date  : 2021-08-12 14:47
*/
package com.guotai.clientspringboot.utils;

import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

@Component
public class ExecuteCommand {
    public String getCommandRes(String para) {
        if (para == null) {
            return null;
        }
        try{
            String commandBaseStr = "cmd /C ";
            Process process=Runtime.getRuntime().exec(commandBaseStr+para);
            InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            LineNumberReader line = new LineNumberReader(reader);
            StringBuilder resStr = new StringBuilder();
            String str;
            while((str=line.readLine())!=null){
                resStr.append(str).append("\n");
            }
            return resStr.toString();

        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
