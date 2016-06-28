package com.shane.binaryfiles;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by SHANE on 2016/06/28.
 */
public class Main {
    final static File DIR=new File("C:/Users/SHANE/Desktop/Business/files/");
    private static BufferedWriter writer;

    public static void main(String[] args) {

        try
        {
            //Checking if the DIR exist
            if(DIR.exists())
            {
                ArrayList<File> binaryFiles=scanDirectory(DIR);
                String msg="";
                byte [] temp;
                byte [] current;
                for(int x=0;x<binaryFiles.size();x++)
                {

                    current=readBinaryFile(binaryFiles.get(x));
                    for(int y=x+1;y<=binaryFiles.size();y++)
                    {
                        temp=readBinaryFile(binaryFiles.get(y));
                        if(current.equals(temp))
                        {
                            msg="file_"+binaryFiles.get(x).getName()+" is a duplicate of file_"
                                    +binaryFiles.get(y).getName()+"\n";
                        }
                    }
                }

                if(!msg.equals(""))
                {
                    writeLogFile(msg,"2016/06/28 10:46");
                }
            }

            else
            {
                System.out.println("Directory does not exist!");
            }

        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void writeLogFile(String message,String date) throws IOException
    {
        try
        {
            writer=new BufferedWriter(new FileWriter("log.txt"));
            writer.write(date+"\n"+message);
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        finally {
            writer.close();
        }
    }

    public static byte[] readBinaryFile(File file) throws IOException {
        InputStream inputStream=null;
        System.out.println("Reading file: "+file.getAbsolutePath());
        byte[] result=new byte[(int)file.length()];

        try
        {
            inputStream=new BufferedInputStream(new FileInputStream(file));
            int totalBytesRead=0;

            while(totalBytesRead<result.length)
            {
                int bytesRemaining=result.length-totalBytesRead;
                int bytesRead= inputStream.read(result,totalBytesRead,bytesRemaining);
                if(bytesRead>0)
                {
                    totalBytesRead=totalBytesRead+bytesRead;
                }
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally
        {
            inputStream.close();
        }
        return result;
    }

    public static ArrayList<File> scanDirectory(File root)
    {
        ArrayList<File> files=new ArrayList<File>();
        File[] current=root.listFiles();

        for(File f:current)
        {
            if(!f.isDirectory()&&f.getName().endsWith(".bat"))
            {
                files.add(f);
            }
        }
        return files;
    }
}
