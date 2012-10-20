package testes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Scanner;

import org.apache.xml.serialize.OutputFormat;

public class reformulaEntrada {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String entradaPath = "C:/Users/aguirre/workspace/CGPerfisDeVegetacao/arquivos de entrada/";
		
		String arquivo = "Arquivo de dados de exemplo8.txt";
		try {
			FileInputStream in = new FileInputStream(entradaPath+arquivo);
			OutputStream out = new FileOutputStream(entradaPath+arquivo);
			Reader rd = new InputStreamReader(in);
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
