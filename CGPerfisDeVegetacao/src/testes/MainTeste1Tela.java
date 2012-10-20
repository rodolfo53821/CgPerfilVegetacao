package testes;

import javax.swing.JFrame;


import telas.MainWindow;

public class MainTeste1Tela {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		new JFrame("Batik");
        new MainWindow();
        String teste[] = { "t","e","s","t","e"};
        MainWindow.main(teste);

        
	}

}
