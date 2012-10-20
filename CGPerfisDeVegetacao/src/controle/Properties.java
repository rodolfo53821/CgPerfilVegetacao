package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.batik.util.gui.JErrorPane;

public class Properties {

	public File inFile;
	public int cellNumber;
	public int pfNumber = 12;
	public Double[] fu;
	public Double[] fl;
	public Double[] laiLowerConopy;
	public Double[] laiUpperConopy;
	public Double [][] agb;

	public Properties(File inFile) throws FileNotFoundException,NumberFormatException {
		super(); 
		this.inFile = inFile;
		
			Scanner sc = new Scanner(inFile);

			
			// lendo a quantidade de numeros de celulas
			cellNumber = sc.nextBigInteger().intValue();
		
			fu = new Double[cellNumber];
			fl = new Double[cellNumber];
			laiLowerConopy = new Double[cellNumber];
			laiUpperConopy = new Double[cellNumber];
			agb =  new Double[pfNumber][cellNumber];
			
						
			//comentario entao pula linha
			if(sc.next().equals("!")){
				sc.nextLine();
			}
			
			// lendo o fu
			int cont = 0;
			while (cont < cellNumber) {
				fu[cont] = pointToDouble(sc.next());
				//fu[cont] = sc.nextDouble();
				cont++;

			}
			
			cont = 0;


			//comentario entao pula linha
			if(sc.next().equals("!")){
				sc.nextLine();
			}
			// lendo fl
			while (cont < cellNumber) {
				fl[cont] = pointToDouble(sc.next());
				//fl[cont] = sc.nextDouble();
				cont++;

			}
			cont = 0;


			//comentario entao pula linha
			if(sc.next().equals("!")){
				sc.nextLine();
			}
			
			// lendo lai upper
			while (cont < cellNumber) {
				laiUpperConopy[cont] = pointToDouble(sc.next());
				//laiUpperConopy[cont] = sc.nextDouble();
				cont++;

			}

			cont = 0;
			
			//comentario entao pula linha
			if(sc.next().equals("!")){
				sc.nextLine();
			}
			// lendo lai lower
			while (cont < cellNumber) {
				laiLowerConopy[cont] = pointToDouble(sc.next());
				//laiLowerConopy[cont] = sc.nextDouble();
				cont++;

			}
			
			//comentario entao pula linha
			if(sc.next().equals("!")){
				sc.nextLine();
			}
			int contPf = 0;
			// lendo o above ground mass
			while (sc.hasNextLine() && contPf < 12) {
				cont = 0;
				while (cont < cellNumber) {
					agb[contPf][cont] = pointToDouble(sc.next());
					//agb[contPf][cont] = sc.nextDouble();
					cont++;
				}
				if(sc.next().equals("!")){
					sc.nextLine();
				}
				contPf++;
			}

		

	}
	
	public double pointToDouble(String value){
		String auxResp = value;
		
		if(auxResp.contains(",")){
			
			auxResp = auxResp.replace(",", ".");
			System.out.println(auxResp);
		}
		Double resp = Double.valueOf(auxResp);
		return resp;
		
	}

	public String valuesToString() {

		String result = "";
		result += cellNumber + "\n";

		int i = 0;
		while (i < fu.length) {
			result += fu[i] + " ";
			i++;
		}
		result += "\n";

		i = 0;
		while (i < fl.length) {
			result += fl[i] + " ";
			i++;
		}
		result += "\n";

		i = 0;
		while (i < laiUpperConopy.length) {
			result += laiUpperConopy[i] + " ";
			i++;
		}
		result += "\n";

		i = 0;
		while (i < laiLowerConopy.length) {
			result += laiLowerConopy[i] + " ";
			i++;
		}
		result += "\n";

		i = 0;
		while (i < pfNumber) {
			int j = 0;
			while (j < cellNumber) {
				result += agb[i][j] + " ";
				j++;
			}
			result += "\n";
			i++;
		}
		
		return result;
	}

}
