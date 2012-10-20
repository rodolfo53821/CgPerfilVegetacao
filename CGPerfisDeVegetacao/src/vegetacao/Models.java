package vegetacao;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

public class Models {

	dimModels dim = dimModels.getInstance();
	
	private List<SVGDocument> listSVGModels;
	public static String modelArchive[] = { "modelos/chao.svg","modelos/pf1.svg","modelos/pf2.svg",
			"modelos/pf3.svg","modelos/pf4.svg","modelos/pf5.svg","modelos/pf6.svg","modelos/pf7.svg",
			"modelos/pf8.svg","modelos/pf9.svg","modelos/pf10.svg","modelos/pf11.svg","modelos/pf12.svg"
			
	};
	public int numberOfModels = 12;

	// ***Constructor***////
	public Models(List<SVGDocument> listSVGModels, String[] modelArchive,
			int numberOfModels) {
		super();
		
		this.listSVGModels = listSVGModels;
		this.modelArchive = modelArchive;
		this.numberOfModels = numberOfModels;
	}

	static Models Instance = null;

	public static synchronized Models getInstance() {
		if (Instance == null)
			Instance = new Models();
		return Instance;
	}

	private Models() {
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		listSVGModels = new LinkedList<SVGDocument>();
		for (String element : modelArchive) {

			try {
				File fileModel = new File(element);
				System.out.println("Caminho "+fileModel.toURI().toString());
				SVGDocument svgModel = f.createSVGDocument(fileModel.toURI()
						.toString());
				listSVGModels.add(svgModel);

			} catch (IOException e) {
				System.out.println("Erro abrindo arquivo");

				e.printStackTrace();

			}

		

		}
		setNumberOfModels(listSVGModels.size());
	}

	// ***Gets and Sets****////////

	public List<SVGDocument> getListSVGModels() {
		return listSVGModels;
	}

	public void setListSVGModels(List<SVGDocument> listSVGModels) {
		this.listSVGModels = listSVGModels;
	}

	protected int getNumberOfModels() {
		return numberOfModels;
	}

	public void setNumberOfModels(int numberOfModels) {
		this.numberOfModels = numberOfModels;
	}

	/** *****Fim Gets and Sets* */
	// ///

	public String toString() {
		String info = "Classe Models contem os seguintes modelos de arvores\n";
		info += "Numero de modelos : " + getNumberOfModels() + "\n";

		for (String element : modelArchive) {
			info += "Modelo " + element + "\n";
		}

		return info;
	}

}
