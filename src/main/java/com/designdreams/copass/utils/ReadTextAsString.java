package com.designdreams.copass.utils;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadTextAsString
{
  public static String readFileAsString(String fileName)throws Exception
  {
    String data = "";
    StringBuilder sb = new StringBuilder();
    List<String> readLines = Files.readAllLines(
            Paths.get(ReadTextAsString.class.getResource(fileName).toURI()), Charset.defaultCharset());

    readLines.forEach(s->sb.append(s));
    data = sb.toString();
    return data; 
  } 
  
  public static void main(String[] args) throws Exception 
  { 
    //String data = readFileAsString("C:\\Users\\pankaj\\Desktop\\test.java");
    String data = readFileAsString("/static/alert_emaI.html");
    System.out.println(data);
  } 
} 