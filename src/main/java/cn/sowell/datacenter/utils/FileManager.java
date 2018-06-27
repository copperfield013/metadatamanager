package cn.sowell.datacenter.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;

public class FileManager {
    
	/**
	 * 
	 * @param path 文件夹路径
	 * @param fileName   文件名称
	 * @return
	 * @throws IOException
	 */
    public static File createFile(String path, String fileName) throws IOException {
		File file = new File(path);
		if(!file.exists()){
        	file.mkdirs();      
        }
        file = new File(path, fileName);
        if(!file.exists()){
        	file.createNewFile();      
        }
        
        return file;
    }
    
    /**
     * 向文件中写入内容
     * @param file 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(File file,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    
}
