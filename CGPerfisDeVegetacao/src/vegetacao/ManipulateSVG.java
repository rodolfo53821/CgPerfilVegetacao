package vegetacao;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

public class ManipulateSVG {
	
	
	///**Metodos de manipulação dos svgs**//////
	
	
	
	/**
	 * Cria o chao, com a quantidade de celulas necessarias
	 * o importante notar nessa funcao que em cada celula tem-se uma transformacao para o local da celula
	 * fazeno com que ao se inserir um modelo nao precisa fazer o translado ate a celula
	 * @param chaoDocument document chao base
	 * @param largura largura da celula
	 * @param altura altura da celula
	 * @param celulas quantidade de celulas
	 */
	public static void generatedGround(SVGDocument chaoDocument, int largura,
			int altura, int celulas) {
		// aumentando o tamanho do svg

		Attr alturaAtributo = chaoDocument.getRootElement().getAttributeNode(
				"width");
		alturaAtributo.setNodeValue(String.valueOf(largura * celulas));
		NamedNodeMap atributos = chaoDocument.getRootElement().getAttributes();

		// aumentando a linha do chao

		Element chaoElement = chaoDocument.getRootElement().getElementById(
				"chao");
		chaoElement.getAttributeNode("x2").setNodeValue(
				String.valueOf(largura * celulas));
		// adicionando separadores

		int iterador = 1;

		while (iterador <= celulas) {
			Element separadorElement = chaoDocument.getRootElement()
					.getElementById("c0");
			Node separadorClone = separadorElement.cloneNode(true);
			String nomeSeparadorClone = "c" + String.valueOf(iterador);

			// renomeando clone
			separadorClone.getAttributes().getNamedItem("id").setNodeValue(
					nomeSeparadorClone);

			// posicionando o separador
			separadorClone.getAttributes().getNamedItem("x1").setNodeValue(
					String.valueOf(largura * iterador));
			separadorClone.getAttributes().getNamedItem("x2").setNodeValue(
					String.valueOf(largura * iterador));

			// adicionando o no novo separador
			Node eixoNo = chaoDocument.getRootElement().getElementById("eixo");
			eixoNo.appendChild(separadorClone);

			NodeList nosChao = chaoDocument.getRootElement()
					.getElementsByTagName("line");

			
			//criando nova celula para inserir os modelos
			if (iterador > 1) {

				// criando nova celula
				Element celulaElement = chaoDocument.getRootElement()
						.getElementById("celula1");
				Node celulaClone = celulaElement.cloneNode(true);

				// nomeando nova celula
				String nomeCelulaClone = "celula"
						+ String.valueOf(iterador );

				// renomeando clone
				celulaClone.getAttributes().getNamedItem("id").setNodeValue(
						nomeCelulaClone);

				// posicionando a vizualicao da celula
				Attr newNodoTransform = chaoDocument
						.createAttribute("transform");// cria um atributo com
				// o nome transform
				String translate = "translate(" + largura * (iterador - 1)
						+ ",0)";
				newNodoTransform.setNodeValue(translate);// set o valor para tal
				// transformação
				celulaClone.getAttributes().setNamedItem(newNodoTransform); // seta
																			// o
																			// atributo
																			// no
																			// nó
																			// celulas
				// adicionando o no novo celula
				Node celulasNo = chaoDocument.getRootElement().getElementById(
						"celulas");
				celulasNo.appendChild(celulaClone);

			}
			iterador++;
		}
		
		//aumentando o c0 e o cUltimo pra melhor visualizar 
		//String transformationLine = "scale(1,"+4.65+")";
		//createTransform(chaoDocument,"c0",transformationLine);

	}

	
	/**
	 * Faz uma transformacao em um dado no de um certo documento
	 * @param doc documento que contem o no a sofrer transformacao
	 * @param nodeName nome do no que sofrera a transformacao
	 * @param transform transformacao a ser realizada
	 */
	public static void createTransform(SVGDocument doc, String nodeName,
			String transform) {
		// identificando o no a sofrer transformação
		Element nodeElement = doc.getRootElement().getElementById(nodeName);
		// identificando o atrr transform
		Attr transformAttr = nodeElement.getAttributeNode("transform");

		// talvez ainda nao tenha o atributo transform

		if (transformAttr == null) {// nao contem o atributo transform

			Attr newNodoTransform = doc.createAttribute("transform");// cria um atributo transform
			newNodoTransform.setNodeValue(transform);// set o valor para tal transformação
			nodeElement.getAttributes().setNamedItem(newNodoTransform);
		}
		else{//ja tem o atributo transform
			String transformValue = transformAttr.getNodeValue();
			transformValue += transform;
			transformAttr.setNodeValue(transformValue);
		}
System.out.println();
	}
	static String getRGBAString(int r,int g, int b, int a){
		String result = null;
		
		result = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b) + Integer.toHexString(a);
		
		return result;
		
	}

	
	/**
	 * Funcao que altera a cor dos modelos pra cor desejada, observando que ambos tronco e copa terao as mesmas cores
	 * @param document documento que sofrera a alteracao
	 * @param colorFill cor de preenchimento em forma de string
	 * @param colorStroke cor de linha em forma de string
	 */
	static void applyTheColor(SVGDocument document,String colorFill,String colorStroke){
		
		int modelsNumber = 12;
		//pode-se passar o modelo com todos os pft ou apenas algums
		//recolorindo os perfis
		for (int i= 1; i <= modelsNumber;i++ ){
			String crownColor = "preenchimentoCopaPF"+i;
			String stockColor = "preenchimentoTroncoPF"+i;
			//obtendo os nos preenchimento para o pf i
			Element crownElement = document.getElementById(crownColor);
			Element stockElement = document.getElementById(stockColor);
			
			if(crownElement != null){
				Attr styleAttr = crownElement.getAttributeNode("style");
				String styleValue = styleAttr.getNodeValue();
				
				styleValue = styleValue.replaceFirst("fill:#[0-9a-fA-F]*;","fill:#" + colorFill + ";");
				styleValue = styleValue.replaceFirst("stroke:#[0-9a-zA-Z]*;", "fill:#" + colorStroke + ";");
			}
			
			if(stockElement != null){
				Attr styleAttr = stockElement.getAttributeNode("style");
				String styleValue = styleAttr.getNodeValue();
				
				styleValue = styleValue.replaceFirst("fill:#[0-9a-fA-F]*;", "fill:#" + colorFill + ";");
				styleValue = styleValue.replaceFirst("stroke:#[0-9a-zA-Z]*;", "fill:#" + colorStroke + ";");
			}
			
		
		
	}
	}
	
	
	/**
	 * Funcao que elimina o preenchimento do modelo
	 * @param document documento que contera apenas o traco do modelo
	 */
	static void onlyStroke(SVGDocument document){
		int modelsNumber = 12;
		//pode-se passar o modelo com todos os pft ou apenas algums
		//eliminando todos os preenchimento pft
		for (int i= 1; i <= modelsNumber;i++ ){
			String crownColor = "preenchimentoCopaPF"+i;
			String stockColor = "preenchimentoTroncoPF"+i;
			//obtendo os nos preenchimento para o pf i
			Element crownElement = document.getElementById(crownColor);
			Element stockElement = document.getElementById(stockColor);
			
			if(crownElement != null){
				Node fatherNode = crownElement.getParentNode();
				fatherNode.removeChild(crownElement);				
			}
			
			if(stockElement != null){
				Node fatherNode = stockElement.getParentNode();
				fatherNode.removeChild(stockElement);	
				
			}
			
			
			
		}
		
		
		
		
	}

	
	
	/**
	 * Inseri um modelo num outro, no caso dependura o no grafico do modelo no documento destino
	 * @param documentDest documento destino
	 * @param documentOrig documento de origem
	 * @param in id do no que  recebera o novo no grafico
	 */
	public static void insertSVGIn(SVGDocument documentDest, SVGDocument documentOrig,String in) {
		Element celulaElement = documentDest.getElementById(in);
		NodeList graphicsNodes = documentOrig.getElementsByTagName("g");
		NodeList listSvgNodesOrig = documentOrig.getElementsByTagName("svg");
		NodeList listSvgNodesDest = documentDest.getElementsByTagName("svg");
		System.out.println("Inserindo nos");
		// deve-se importar o no a ser inserido pelo documento destino pois nao
		// foi o documento quem criou o no entao nao seria possivel a inserção
		// vamos adicionar filho a filho do no svg do documento origem
		int svgNode = 0;
		for (int i = 0; i < listSvgNodesOrig.item(svgNode).getChildNodes()
				.getLength(); i++) {
			System.out.println("No a ser inserido "+listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeName()+" --- "+listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeType()+"-----"+listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeValue());
			if(!listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeName().equals("metadata") && !listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeName().equals("sodipodi:namedview")){
			Node importatedNode = documentDest.importNode(listSvgNodesOrig
					.item(svgNode).getChildNodes().item(i), true);
			if(listSvgNodesOrig.item(svgNode).getChildNodes().item(i).getNodeName().equals("g")){//devemos inserilo na celula correspondende
				celulaElement.appendChild(importatedNode);
				
				
			}
			else
			// considerando que o destino tenha apenas um nodo svg
			listSvgNodesDest.item(0).appendChild(importatedNode);
		}
		}

	}
	
	 static void printChild(Node no){
		String resp ="";
		
		for(int i = 0; i< no.getChildNodes().getLength();i++){
			System.out.println(no.getChildNodes().item(i).getNodeName() );
			if(no.getChildNodes().item(i).getNodeName().equals("g")){
				System.out.println();
				System.out.println("Filho de g "+no.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue());
				System.out.println();
				
				if(no.getChildNodes().item(i).getAttributes().getNamedItem("id").getNodeValue().equals("celula2")){
					printChild(no.getChildNodes().item(i));
				}
				printChild(no.getChildNodes().item(i));
			}
			
		}
		System.out.println(resp);
		
	}

	 
	 
	 /**
	  * Para facilitar o tronco e a copa dos modelos nao estao lincados,dai esta funcao serve para conecta-los
	  * A funcao trata os modelos de forma diferente, e para o caso de uma copa é o tronco que anda, em caso de duas copa
	  * é o tronco e a copa da direita
	  * 
	  * @param document documento que contem o modelo a sofrer a ligacao entre copa e tronco
	  * @param pf tipo funcional do modelo, valor importante pois atraves dele se constroi a string para determinar a copa e o tronco
	  * @param pointControlOneX  tanto em x que deve posicionar o tronco
	  * @param pointControlOneY tanto em y que deve posicionar o tronco
	  * @param pointControlTwoX tanto em x que deve posicionar a copa direita caso o modelo tenha
	  * @param pointControlTwoY tanto em y que deve posicionar a copa direita caso o modelo tenha
	  */
	 public static void bindCrownAndStock(SVGDocument document,String pf,double pointControlOneX,double pointControlOneY,double pointControlTwoX, double pointControlTwoY){
		 
		 String transformOne = "translate("+pointControlOneX+","+pointControlOneY+")";
		 String transformTwo = "translate("+pointControlTwoX+","+pointControlTwoY+")";
		 
		 
		 
		 //para os modelos que nao sao parecidos com auracaria ou que a copa esta em cima do tronco
		 if(pf.toLowerCase().equals("pf1")||pf.toLowerCase().equals("pf2")||pf.toLowerCase().equals("pf4")||pf.toLowerCase().equals("pf5")||pf.toLowerCase().equals("pf6")||pf.toLowerCase().equals("pf7") || pf.toLowerCase().equals("pf9") || pf.toLowerCase().equals("pf10")){
			 String stockGroup = "agrupamentoTronco"+pf;
			 Element groupStockElement = document.getElementById(stockGroup);
			 
			
			 Attr transformAttr = groupStockElement.getAttributeNode("transform");

				// talvez ainda nao tenha o atributo transform

				if (transformAttr == null) {// nao contem o atributo transform

					Attr newNodoTransform = document.createAttribute("transform");// cria um atributo transform
					newNodoTransform.setNodeValue(transformOne);// set o valor para tal transformação
					groupStockElement.getAttributes().setNamedItem(newNodoTransform);
				}
				else{//ja tem o atributo transform
					String transformValue = transformAttr.getNodeValue();
					if(transformValue.contains("scale"))//antes de aplicar a escala deve ser feita a translacao
						transformValue = transformOne + transformValue;
					else
					transformValue += transformOne;
					transformAttr.setNodeValue(transformValue);
				}
			
			 
		 }
		 //nesse caso teremos duas copas entao a copa nao fica acima do tronco fica ao lado
		 else if(pf.toLowerCase().equals("pf3")||pf.toLowerCase().equals("pf8")){
			//deve-se transladar o tronco para o ponto de controle um
			 String stockGroup = "agrupamentoTronco"+pf;
			 Element groupElementOne = document.getElementById(stockGroup);
			 
			 Attr transformAttrOne = groupElementOne.getAttributeNode("transform");
			
			 
			 if(transformAttrOne == null){//nao contem a transformacao ainda
				 Attr newNodeTransform = document.createAttribute("transform");// cria um atributo transform
				 newNodeTransform.setNodeValue(transformOne);// set o valor para tal transformação
				 groupElementOne.getAttributes().setNamedItem(newNodeTransform);
			 }else{
				 String transformValue = transformAttrOne.getNodeValue();
					if(transformValue.contains("scale"))//antes de aplicar a escala deve ser feita a translacao
						transformValue = transformOne + transformValue;
					else
					transformValue += transformOne;
					transformAttrOne.setNodeValue(transformValue);
			 }
			 
		     //deve transladar a copa direita para o ponto de controle dois
			 
			 String crownRightGroup = "agrupamentoCopaDireita"+pf;
			 Element groupElementTwo = document.getElementById(crownRightGroup);
			 Attr transformAttrTwo = groupElementTwo.getAttributeNode("transform");
			
			 
			 if(transformAttrTwo == null){//nao contem a transformacao ainda
				 Attr newNodeTransform = document.createAttribute("transform");// cria um atributo transform
				 newNodeTransform.setNodeValue(transformTwo);// set o valor para tal transformação
				 groupElementTwo.getAttributes().setNamedItem(newNodeTransform);
			 }else{
				 String transformValue = transformAttrTwo.getNodeValue();
					if(transformValue.contains("scale"))//antes de aplicar a escala deve ser feita a translacao
						transformValue = transformTwo + transformValue;
					else
						transformValue += transformTwo;
					transformAttrTwo.setNodeValue(transformValue);
			 }
			
			 
						 
		 }
		 
	 
		 
		 
	 }


}
