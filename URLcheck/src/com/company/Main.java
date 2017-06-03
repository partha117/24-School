package com.company;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) {


        /*try {
            URL url=new URL("https://www.facebook.com/");
            System.out.println(url.getProtocol());
            System.out.println(url.toExternalForm());
            URLConnection urlConnection=url.openConnection();
            InputStream inputStream=urlConnection.getInputStream();
            System.out.println(convertStreamToString(inputStream));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        int array[]={1,3,5,7,9,11,13,15};
        for(int i=0;i<array.length;i++)
        {
            for(int j=0;j<array.length;j++)
            {
                for(int k=0;k<array.length;k++)
                {
                    if(array[i]+array[j]+array[k]==30)
                    {
                        System.out.println(array[i]+"  +  "+array[j]+"  +  "+array[k]+"  =  "+"30\n");
                    }
                }
            }
        }

    }
    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
