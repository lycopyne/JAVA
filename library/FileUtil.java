package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileUtil {


	public static void fileCopy(String readfile, String writefile, String code){
		String writetext = readf(readfile, code);
		writef(writefile, writetext, code);
	}
	private static String readf(String readfile, String code){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readfile), code));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		}
		catch(IOException e){
			System.err.println(readfile + " not found");
			return null;
		}
	}

	public static void writef(String writefile, String writetext, String code){
		try{
			BufferedWriter bw = FileUtil.setWriteDir(writefile, code);
			bw.write(writetext);
			bw.close();
		}
		catch(IOException e){
			System.err.println(writefile + " not found");
		}
	}

	public static BufferedReader setReadDir(String path, String code) {
		new File(new File(path).getParent()).mkdirs();
		try {
			return new BufferedReader(new InputStreamReader(new FileInputStream(path), code));
		} catch(FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedWriter setWriteDir(String path, String code) {
		new File(new File(path).getParent()).mkdirs();
		try {
			return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), code));
		} catch(FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void mkdir(String filepath){
		File f = new File(filepath);
		f.mkdir();
	}
	public static void mkdirs(String filepath){
		File f = new File(filepath);
		f.mkdirs();
	}

	public static boolean fileExist(String filepath){
		File f = new File(filepath);
		if(f.exists())
			return true;
		else
			return false;
	}
}
