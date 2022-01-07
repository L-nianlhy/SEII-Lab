import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import java.io.BufferedReader;
import java.util.StringTokenizer;
public class KWIC {
//    public static void main(String[] args){
//        KWIC kwic = new KWIC();
//        kwic.input("D:\\JavaScriptCore.resources\\java_massion\\Test\\src\\1.txt");
//        kwic.circularShift();
//        kwic.alphabetizing();
//        kwic.output();
//    }


  private ArrayList<String> kwicList = new ArrayList<String>();
  private ArrayList<String> lineTxt = new ArrayList<String>();
  private BufferedReader inputFile;


  public void input(String fileName) {
    try {
      inputFile = new BufferedReader(new FileReader(fileName));
    } catch (Exception e) {
      System.err.print("wrong");
      System.exit(1);
    }
    String line;
    try {
      while ((line = inputFile.readLine()) != null) {
        lineTxt.add(line);
      }
    } catch (IOException e) {
// TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void output() {
    Iterator<String> it = kwicList.iterator();
    System.out.println("the output is ");
    while (it.hasNext()) {
      System.out.println(it.next());
    }

  }

  public void circularShift() {
//获取每个单词，存入tokens
    Iterator<String> it = lineTxt.iterator();
    while (it.hasNext()) {
      StringTokenizer token = new StringTokenizer(it.next());
      ArrayList<String> tokens = new ArrayList<String>();
      int i = 0;
//循环添加单词

      int count = token.countTokens();
      while (i < count) {
//System.out .println(token.nextToken().toString());
        tokens.add(token.nextToken());
        i++;
      }

//display(tokens);
//切割各个单词，不断改变起始值和利用loop实现位移。
      for (i = 0; i < count; i++) {
        StringBuffer lineBuffer = new StringBuffer();
        int index = i;
        for (int f = 0; f < count; f++) {

//从头继续位移
          if (index >= count)
            index = 0;
//存入StringBuffer
          lineBuffer.append(tokens.get(index));
          lineBuffer.append(" ");
          index++;
        }
        String tmp = lineBuffer.toString();
//System.out .println("the buffer is "+tmp);
        kwicList.add(tmp);

      }

    }

  }

}