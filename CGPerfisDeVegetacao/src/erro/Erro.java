package erro;

import java.io.*;
import java.util.Date;
import java.util.HashMap;


public class Erro extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrintWriter logStream;
	private final String path = "c:\\logErrors.txt";
	private File logFile;
	private boolean firstTime = false;

	public static Erro Instance = null;

	public static synchronized Erro getInstance() {
		if (Instance == null)
			Instance = new Erro();

		return Instance;

	}

	private Erro() {
   
		try {
			createTypesOfErro();
			logFile = new File(path);
			if (!logFile.exists()) {
				firstTime = true;
			}
			logStream = new PrintWriter(new FileWriter(path, true));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createTypesOfErro() {
		typeOFErro = new HashMap<String,Integer>();
		typeOFErro.put("IO", 1);
		
		
	}

	@SuppressWarnings("deprecation")
	public void createError(int type, String local, String line) {
		if (firstTime) {
			logStream
					 .println("****************************************************************************");
			logStream.println("*****************************Log de Erros***********************************");
			logStream
					 .println("****Arquivo contem os erros ou excessoes do programa CGPerfisDeVegeteção****");
			logStream
					 .println("******Este servira para futuras atualizações ou manunteção do mesmo*********");
			Date date = new Date();
			logStream.println("**********************Criado em " + date.getMonth() + "/"+date.getDate() + "/" + date.getYear() + "************************");
			logStream
					 .println("****************************************************************************");
			System.out.println("Arquivo nao existe ainda");
		
			firstTime = false;
		}

		String typeMessage = "Error " + type;
		Date date = new Date();
		String timeMessage = "Ocurred in " + date.getMonth() + "/"
				+ date.getDate() + "/" + date.getYear() + " at "
				+ date.getHours() + ":" + date.getMinutes();
		String localMessage = "Local: " + local + " line: " + line;

		logStream.println(typeMessage);
		logStream.println(timeMessage);
		logStream.println(localMessage);
		logStream.println();
		logStream.println();

	}

	public void finalize() {
		// out.close();
		logStream.close();

	}
	
	private HashMap<String,Integer> typeOFErro;

}
