package com.common.utils;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author RJ
 * @date 2020/4/23 10:33
 */
public class ExceptionUtils {
    private String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    public static String getException(Exception e) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.getClass() + "\n");
        builder.append("\t" + e.getMessage() + "\r\n");
        builder.append("\tCaused by: " + e.getCause() + "\r\n");
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace) {
            builder.append("\tat " + s + "\r\n");
        }

        return builder.toString();
    }

    public static void main(String args[]){

    }
}
