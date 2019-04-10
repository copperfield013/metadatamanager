package cn.sowell.datacenter.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileManager {
	
	
	public static boolean deleteFile(String path) {// 删除文件|目录（连联删除）
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在！");
            return false;
        }        
        delDir(file);
        return true;
    }

    private static void delDir(File file) {
        if(file.isFile()) {//如果此file对象是文件的话，直接删除
            file.delete();
            return;
        }
        //当 file是文件夹的话，先得到文件夹下对应文件的string数组 ，递归调用本身，实现深度优先删除
    	File[] listFiles = file.listFiles();
    
    	for (File file2 : listFiles) {
    		delDir(file2);
		}
    	
        //当把文件夹内所有文件删完后，此文件夹已然是一个空文件夹，可以使用delete()直接删除
        file.delete();
        return;
    }
	
	
    
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
         File file2 = new File(path, fileName);
        if(!file2.exists()){
        	file2.createNewFile();      
        }
        
        FileWriter fileWriter =new FileWriter(file2);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
        
        return file2;
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
