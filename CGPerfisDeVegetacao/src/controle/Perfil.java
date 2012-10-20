package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import org.xml.sax.SAXException;

import vegetacao.*;

public class Perfil {
	
	
	protected File inFile;
	protected File outFile;
	static protected Models modelsInstance = Models.getInstance();
	public  SVGDocument respSVGDocument;
	protected  Properties properties ;
	protected static int cellWidht = 300;
	protected static int cellHeight = 200;
	protected  ConfigProperties configProperties ;
	protected  List<Double> maxBiomassProportional = new ArrayList<Double>();
	
	
	public Perfil(File inFile, File outFile, ConfigProperties configuration) throws FileNotFoundException,NumberFormatException {
		super();
		
		this.inFile = inFile;
		this.outFile = outFile;
		this.properties = new Properties(inFile);
		this.configProperties = configuration;
		this.respSVGDocument = null;
	}
	
	public void generatedResult() throws IOException, SAXException {
		
		System.out.println();
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		respSVGDocument = f.createSVGDocument(modelsInstance.getListSVGModels().get(0).getDocumentURI());//deve ser definido que o modelo indice 0 sera o chao
		
		//criando o chao
		ManipulateSVG.generatedGround(respSVGDocument, cellWidht, cellHeight, properties.cellNumber);
		//ManipulateSVG.insertSVGIn(respSVGDocument, modelsInstance.getListSVGModels().get(0), "celula1");
		
		//prenchendo celula por celula
		for(int i = 1; i <= properties.cellNumber;i++ ){
			
			int  numberOfPfs [] = new int[Models.getInstance().numberOfModels];
			List<SVGDocumentLocal> allPfList = new LinkedList<SVGDocumentLocal>();
			
			numberOfPfs = createPfsList(i,numberOfPfs, allPfList,maxBiomassProportional);
			for(int j= 0 ;j< numberOfPfs.length;j++){
				System.out.println("do modelo "+j+" teremos "+numberOfPfs[j]);
				
			}
			fillCell(i,numberOfPfs, allPfList);
		}
		
	
		
		
		
	} 
	
	
	
	private   int [] createPfsList(int cell, int numberOfPfs [],	List<SVGDocumentLocal> allPfList,List<Double> maxBiomassProportional){
		numberOfPfs = defineNumberOfModels(cell,maxBiomassProportional);
		
		
		//sera criada os modelos em uma estrura de dados depois eles serao adicionados a resposta
		
		for(int t = 0; t < numberOfPfs[1]; t++){
			
			allPfList.add(new SVGDocumentLocal(1));
		}
		
		
		for(int t = 0; t < numberOfPfs[2]; t++){
			
			allPfList.add(new SVGDocumentLocal(2));
		}
		
		for(int t = 0; t < numberOfPfs[3]; t++){
			
			allPfList.add(new SVGDocumentLocal(3));
		}
		
		
		for(int t = 0; t < numberOfPfs[4]; t++){
		
			allPfList.add(new SVGDocumentLocal(4));
		}
		
		for(int t = 0; t < numberOfPfs[5]; t++){
			
			allPfList.add(new SVGDocumentLocal(5));
		}
		
		for(int t = 0; t < numberOfPfs[6]; t++){
	
			allPfList.add(new SVGDocumentLocal(6));
		}
		for(int t = 0; t < numberOfPfs[7]; t++){
	
			allPfList.add(new SVGDocumentLocal(7));
		}
	
		for(int t = 0; t < numberOfPfs[8]; t++){
	
			allPfList.add(new SVGDocumentLocal(8));
		}
		
		for(int t = 0; t < numberOfPfs[9]; t++){

			allPfList.add(new SVGDocumentLocal(9));
		}
		
		for(int t = 0; t < numberOfPfs[10]; t++){

			allPfList.add(new SVGDocumentLocal(10));
		}
		for(int t = 0; t < numberOfPfs[11]; t++){

			allPfList.add(new SVGDocumentLocal(11));
		}
		
		for(int t = 0; t < numberOfPfs[12]; t++){

			allPfList.add(new SVGDocumentLocal(12));
		}
		
		
		return numberOfPfs;
		
	}
	
	//funcao que define a proporcao da biomassa, esta varia de acordo com um valor de entrada e com o valor de correcao 
	//definido em maxBiomassProportional diferente de zero quando se tem mais de dez arvores por modelo
	private  double [] defineBiomassScale(int numberOfModels[], int cell,List<SVGDocumentLocal> allPfList){
		double resp[] = new double[allPfList.size()];
		
		// define a proporcao media da biomassa para cada modelo
		double proportionBiomass[] = new double[numberOfModels.length-1];
			for(int i = 0;i< proportionBiomass.length;i++)
				proportionBiomass[i] = properties.agb[i][cell-1]/numberOfModels[i+1] + maxBiomassProportional.get(i);
		
		
		
		//definindo a porcentagem para a biomassa de cada modelo
		int modelIterator = 0;
		
		while(modelIterator < allPfList.size()){
			int beg = modelIterator;
			int end = numberOfModels[allPfList.get(beg).type ] + beg;
			int i = beg;
			while(i < end){
			    if(end - i  < 2){
					resp[i] = proportionBiomass[allPfList.get(i).type - 1];
					
				}else{//tem dois numero e podemos aplicar o passo de randomico para ambos
					//sera criado um randomico entre 20% a mais ou amenos para um modelo e o seguinte ira complementar
					double percNumber = (1 -(Math.random()/5));
					double firstBiomassValue = proportionBiomass[allPfList.get(i).type - 1]* percNumber;
					double secondBiomassValue = proportionBiomass[allPfList.get(i).type - 1] *(2 - percNumber);
					resp[i] = firstBiomassValue;
					resp[i+1] = secondBiomassValue;
					i++;
				}
				i++;
			}
			modelIterator = end;
			
		}
		
		
		return resp;
	}
	
	private   int[] defineNumberOfModels(int cell,List<Double> maxBiomassProportional) {
		//seria criado um vetor com o numero de modelos menos um pois nos modelos tem o chao, ou seja apartir de 1
		int []numberOfModels = new int[Models.getInstance().numberOfModels] ;
		
		for(int i = 1; i < numberOfModels.length; i++){
			int numbers = configProperties.getNumberOfModels(i , properties.agb[i-1][cell -1]);
			if(numbers >= configProperties.aglbLimit){
				numberOfModels [i] = (int) configProperties.aglbLimit;
				maxBiomassProportional.add((numbers - configProperties.aglbLimit)/configProperties.aglbLimit);
			}else{
				numberOfModels [i] = numbers;
				maxBiomassProportional.add(0.0);
			}
			
		
		}
		return numberOfModels;
		
	}
	
	private static double double2Decimal(double value){
	     //formula para obter um double com duas casas decimais
		// com arredondamento pra cima
		return(BigDecimal.valueOf(value).setScale(2,BigDecimal.ROUND_UP).doubleValue() );
		
	}

	private  void fillCell(int cell,int []numberOfPfs, List<SVGDocumentLocal> allPfList) {
		//o vetor podera ter tres valores 
		// 0 para vazio e pode ocupar
		// 1 para cheio
		// -1 para vazio e nao pode ocupar limite do fu e fl
		int positionFreeTree[] = new int[cellWidht];
		int positionFreeShrub[] = new int[cellWidht];
		for(int i = 0;i <cellWidht;i++) {
			positionFreeTree[i] = 0; 
			positionFreeShrub[i] = 0; 
		}
		
				
		
		//ajustando fu o espacao em branco sera nos extremos da celula por enquanto
		int sizePixelNotFreeTree = (int)(cellWidht - cellWidht*properties.fu[cell-1])/2;
		
		for(int i = 0;i< (sizePixelNotFreeTree);i++){
			positionFreeTree[cellWidht - i -1] = -1;
			positionFreeTree[i] = -1;
		}
		//ajustando o fl - espaco em branco sera no meio da celula 
		int sizePixelNotFreeShrub = (int)(cellWidht - cellWidht*properties.fl[cell-1])/2;
		for(int i = 0;i< (sizePixelNotFreeShrub);i++){
			positionFreeShrub[cellWidht/2 - i ] = -1;
			positionFreeShrub[cellWidht/2 + i] = -1;
		}
		
		//vetor contendo valores que identifica o quanto deve ser alterado na largura de cada modelo pra que fu e fl sejam preenchidos
		double[] proportionalWidth =   correctingWidth(allPfList,sizePixelNotFreeTree,sizePixelNotFreeShrub);
		
		//vetor contendo os valores pra cada arvore e que podem variar randomicamente ate mesmo arvores de um mesmo modelo
		double biomassValue[] = defineBiomassScale(numberOfPfs, cell,allPfList);
		
		
		double numberOfModels = getNumberOfModels(numberOfPfs);
		//percorrendo a lista de modelos que contera na celula e aplicando lai , fu ,fl e o biomass
		int iterator = 0;
		for(SVGDocumentLocal element : allPfList){
			Properties p = properties;
			double maxLai = configProperties.getLaiMax(element.type);
			double laiLocal ;
			double fuProportional = element.getWidthTree()/(cellWidht*properties.fu[cell-1]);
			double flProportional = element.getWidthTree()/(cellWidht*properties.fl[cell-1]);
			double pointControlOneX ;
			double pointControlOneY;
			double pointControlTwoX ;
			double pointControlTwoY ;
			double biomassLocalWidth = biomassValue[iterator];
			double biomassLocalHeight = biomassValue[iterator];
			
			
			
			
			//quando o modelo for um arbustro ou graminea o lai deve ser lailower canopy
			if(element.group == 2 ){
				laiLocal = properties.laiLowerConopy[cell-1] /(numberOfModels*flProportional);
				System.out.println();
				
			}else{
				laiLocal = properties.laiUpperConopy[cell-1] / (numberOfModels*fuProportional);
				System.out.println();
			}
			
			
			//o lai tem um limite 
			if(laiLocal> maxLai)
				laiLocal = maxLai;
			
			//verificando se pode aplicar o lai
			System.out.println();
			double oldHeightTree = element.getHeightTree();
			double oldHeightCrown = element.getHeightCrown();
			double oldHeightStock = element.getHeightStock();
			double oldWidthStock = element.getWidthStock();
			
			if(cell == 3){
				System.out.println();
			}
			//o tronco nao pode ser maior que a copa
			if((element.getWidthCrown()*laiLocal/maxLai )< (element.getWidthStock()*biomassLocalWidth)){
				System.out.println(element.getWidthCrown()*laiLocal/maxLai + " ---- "+ (element.getWidthStock()*biomassLocalWidth));
				biomassLocalWidth = (element.getWidthCrown()*laiLocal/maxLai)/element.getWidthStock();
				
			}
			
		    
		    if(element.type != 8 && element.type != 3){
				//ponto de controle 1 em x sera a media entre o tronco e a largura da copa
		    	double teste = (element.getWidthCrown()*laiLocal/maxLai );
		    	double teste2 = (element.getWidthStock()*biomassLocalWidth);
		    	System.out.println();
		    	pointControlOneX = double2Decimal(Math.abs(((element.getWidthCrown()*proportionalWidth[iterator] ) - (element.getWidthStock()*biomassLocalWidth))/2));
		    	pointControlOneY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneY * laiLocal/maxLai);
				pointControlTwoX = dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoX ;
				pointControlTwoY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoY * laiLocal/maxLai);
				/*
		    	pointControlOneX =  double2Decimal((dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneX  + element.getWidthStock()/2.0)*proportionalWidth[iterator] -  element.getWidthStock()/2.0) ;
				pointControlOneY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneY * laiLocal/maxLai);
				pointControlTwoX = dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoX ;
				pointControlTwoY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoY * laiLocal/maxLai);
		    	*/
		    }else{
		    	int numberPartCrown = element.type == 3 ? 0:1;
		    	pointControlOneX =  double2Decimal((proportionalWidth[iterator] )*dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneX  ) ;
				pointControlOneY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneY * laiLocal/maxLai);
				pointControlTwoX = dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoX + ((biomassLocalWidth-1))*element.getWidthStock() + (proportionalWidth[iterator] - 1)*dimModels.getInstance().dimensionsPartCrown[numberPartCrown] ;
				pointControlTwoY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoY * laiLocal/maxLai);
		    	System.out.println();
		    	
		    }
		    
		    element.setPointControlOneX(pointControlOneX);
		    element.setPointControlOneY(pointControlOneY);
		    element.setPointControlTwoX(pointControlTwoX);
		    element.setPointControlTwoY(pointControlTwoY);
		    
		    
		    
		  //configurando novas dimensoes do modelo
			element.setHeightCrown(double2Decimal(element.getHeightCrown()*laiLocal/maxLai));
			element.setWidthCrown(double2Decimal(element.getWidthCrown()*proportionalWidth[iterator]));
			
			element.setHeightStock(double2Decimal(element.getHeightStock()*biomassLocalHeight));
		    element.setWidthStock(double2Decimal(element.getWidthStock()*biomassLocalWidth));
		    
		    
		    //verificando os limites caso seja necessario ha uma reavaliacao do lai e do biomass
		    if(element.getHeightTree() >= cellHeight){
		    	System.out.println();
		    	
		    	double exceed  = element.getHeightTree() - cellHeight;
				
		    	double proportionalExc = element.getHeightTree()/cellHeight - 1;
				
		    		if(biomassLocalHeight > 1 ){
		    		double maxStock = (int)(element.getHeightStock() - exceed);
		    		double proportionalExcStock = maxStock/oldHeightStock;
		    		biomassLocalHeight = proportionalExcStock;
					double newBiomassWidth = biomassLocalWidth;
					biomassLocalWidth = newBiomassWidth*newBiomassWidth/biomassLocalHeight;
					
					double limitBiomass = 2.0;
					if(biomassLocalWidth > limitBiomass)biomassLocalWidth = limitBiomass;
			    	
		    	}
		    	
				
				 if(element.type != 8 && element.type != 3){
					 	pointControlOneX = double2Decimal(Math.abs((oldHeightCrown*laiLocal/maxLai ) - (oldWidthStock*biomassLocalWidth)/2));
						//pointControlOneX =  double2Decimal((dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneX  + element.getWidthStock()/2.0)*proportionalWidth[iterator] -  element.getWidthStock()/2.0) ;
						pointControlOneY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneY * laiLocal/maxLai);
						pointControlTwoX = dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoX ;
						pointControlTwoY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoY * laiLocal/maxLai);
				    }else{
				    	int numberPartCrown = element.type == 3 ? 0:1;
				    	pointControlOneX =  double2Decimal((proportionalWidth[iterator] )* dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneX ) ;
						pointControlOneY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlOneY * laiLocal/maxLai);
						pointControlTwoX = dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoX + ((biomassLocalWidth-1))*element.getWidthStock() + (proportionalWidth[iterator] - 1)*dimModels.getInstance().dimensionsPartCrown[numberPartCrown] ;
						pointControlTwoY = double2Decimal(dimModels.getInstance().pointControlList.get(element.type - 1).pointControlTwoY * laiLocal/maxLai);
				    	System.out.println();
				    	
				    }
				 
				 
				 
				 element.setPointControlOneX(pointControlOneX);
				    element.setPointControlOneY(pointControlOneY);
				    element.setPointControlTwoX(pointControlTwoX);
				    element.setPointControlTwoY(pointControlTwoY);
				    
				    
				 element.setHeightCrown(double2Decimal(oldHeightCrown*laiLocal/maxLai));
					
				element.setHeightStock(double2Decimal(oldHeightStock*biomassLocalHeight));
				element.setWidthStock(double2Decimal(oldWidthStock*biomassLocalWidth));
		    System.out.println();
		    }
			
		    
			
			
			//aplicando lai somente a copa
			//para pfs diferente de pf3 e pf8
			if(element.type != 8 && element.type != 3){
				String transformLai = "scale("+ double2Decimal(proportionalWidth[iterator]) +","+ double2Decimal(laiLocal/maxLai) +")";
				ManipulateSVG.createTransform(element.document, "agrupamentoCopaPF"+element.type,transformLai);
			}else{
				String transformLai = "scale("+ double2Decimal(proportionalWidth[iterator]) +","+ double2Decimal(laiLocal/maxLai) +")";
				//aplicar as duas copas separadamente
				ManipulateSVG.createTransform(element.document, "agrupamentoCopaDireitaPF"+element.type,transformLai);
				ManipulateSVG.createTransform(element.document, "agrupamentoCopaEsquerdaPF"+element.type,transformLai);
				
				
			}
			
			//aplicando o aglb apenas naqueles que tem tronco em pf1 ao 10
			if(element.type <= 10){
				String transformAglb = "scale("+ double2Decimal(biomassLocalWidth) +","+ double2Decimal(biomassLocalHeight) +")";
				ManipulateSVG.createTransform(element.document, "agrupamentoTroncoPF"+element.type,transformAglb);
			}
			
			//se modelo nao for graminea precisa acoplar copa com tronco
			if( element.type <= 10)
			//acoplando tronco a copa
			ManipulateSVG.bindCrownAndStock(element.document, "PF"+element.type,element.getPointControlOneX(),element.getPointControlOneY(),element.getPointControlTwoX(),element.getPointControlTwoY());
		
			
			
		    
			iterator ++;
		}
		
		
		
		
		//posicionando modelos
		int nextTree  = sizePixelNotFreeTree;
		int nextShrub  = 0;
		
		
		int randomVector [] = randomizeModels(allPfList);
		
		//posicionando arvores e arbustos
		for(int t = 0; t < randomVector.length; t++){
			System.out.println();
			if(allPfList.get(randomVector[t]).group == 2 ){
				if(nextShrub!=-1)
					nextShrub = setLocation(allPfList.get(randomVector[t]),positionFreeTree,cell,nextShrub,sizePixelNotFreeShrub);
				else
					setLocationRandom(allPfList.get(randomVector[t]),sizePixelNotFreeShrub);
				
			}else{
				if(nextTree!=-1)
					nextTree = setLocation(allPfList.get(randomVector[t]),positionFreeTree,cell,nextTree,sizePixelNotFreeTree);
				else
					setLocationRandom(allPfList.get(randomVector[t]),sizePixelNotFreeTree);
			}
		}
		
		
		//inserindo os modelos em resp
		for(SVGDocumentLocal element : allPfList){
			System.out.println();
			ManipulateSVG.insertSVGIn(respSVGDocument, element.document,"celula"+cell);			
		}
		
		
		
		
	}
	
	
	private double getNumberOfModels(int[] numberOfPfs) {
		int sum = 0;
		for(int i = 0; i < numberOfPfs.length;i++ )
			sum += numberOfPfs[i];
		return sum;
	}

	//verifica se o modelo com a transformacao cabera na celula
	static boolean isWidthPermited(SVGDocumentLocal documentLocal,double newProportion){
		boolean resp =true;
		
		if(documentLocal.widthTree*newProportion >= cellWidht) resp = false;
		return resp;
	}
	static boolean isHeightPermited(SVGDocumentLocal documentLocal,double newProportion){
		boolean resp =true;
		
		if(documentLocal.heightTree*newProportion >= cellHeight) resp = false;
		return resp;
	}
	//destribui proporcionalmente o espaco restante entre os modelos
	//retorna um vetor contendo a porcentagem para cada modelo armazenado
	static double [] correctingWidth(List<SVGDocumentLocal> allPfList,int sizePixelNotFreeTree,int sizePixelNotFreeShrub){
		double [] resp = new double[allPfList.size()];
		int restGroup1 = verifyFill(1,sizePixelNotFreeTree, allPfList);
		int restGroup2 = verifyFill(2,sizePixelNotFreeShrub, allPfList);
		
		int sumGroup1 = 0;
		int sumGroup2 = 0;
		for(SVGDocumentLocal element : allPfList){
			if(element.group == 1)
				sumGroup1 += element.getWidthTree();
			else
				sumGroup2 += element.getWidthTree();
		}
		
		//definindo a proporcao em que a copa deve assumir
		for(int i = 0;i < resp.length;i++){
			if(allPfList.get(i).group == 1 ){
				if(restGroup1 > 0){
					resp [i] = BigDecimal.valueOf((cellWidht - 2*sizePixelNotFreeTree)).divide(BigDecimal.valueOf(sumGroup1), 2).doubleValue() ;
				}else
					resp [i] = 1;
				
			}else if(allPfList.get(i).group == 2 ){
				if(restGroup2 > 0){
					resp [i] = BigDecimal.valueOf((cellWidht - 2*sizePixelNotFreeShrub)).divide(BigDecimal.valueOf(sumGroup2), 2).doubleValue() ;
				}else
					resp [i] = 1;
				
			}
			
		}
		
		
		
		return resp;
		
		
	}
	
	//funcao que verifica se a parte que deve ser coberta da celula esta coberta
	// =< 0 celula totalmente ocupada
	// > 0 ainda tem espaco
	
	static int verifyFill(int group,int sizPixelNotFree,List<SVGDocumentLocal> allPfList){
		int resp = cellWidht;
		resp -= sizPixelNotFree;
		for(SVGDocumentLocal element :allPfList){
			if(element.getGroup() == group)
				resp -= element.getWidthTree();
			
		}
		
		return resp;
		
	}
	
	private static int[] randomizeModels(List<SVGDocumentLocal> allPfList2) {
		Boolean[] choosedElement = new Boolean[allPfList2.size()];
		int [] randomVector = new int [allPfList2.size()];
		for(int i = 0; i < choosedElement.length;i++) choosedElement[i] = false;
		//escolhendo randomicamente a sequencia dos elementos a serem colocados
		for(int i = 0; i < randomVector.length;i++){
			int element = (int)(Math.random()*(randomVector.length ));
			while(choosedElement[element] == true) {
				element = (int)(Math.random()*(randomVector.length ));
				
			}
			
			randomVector[i] = element;
			choosedElement[element] = true;
		}
		
		return randomVector;
	}

	private static void setLocationRandom(SVGDocumentLocal documentLocal,int sizePixelNotFree) {
		int posX;
		int posY;
		if(documentLocal.group == 1){
		//posicao em x sera um numero randomico entre 0 e o limite da celula para este modelo
			posX = (int)(Math.random()*(cellWidht - 2*sizePixelNotFree - (int)documentLocal.getWidthTree())) + sizePixelNotFree;
			
		}else{
			posX = (int)(Math.random()*(cellWidht )) ;
			while(posX >(cellWidht/2 - sizePixelNotFree) && posX <(cellWidht/2 + sizePixelNotFree) )
				posX = (int)(Math.random()*(cellWidht -documentLocal.getWidthTree()) );
		}
		posY = cellHeight - (int)documentLocal.getHeightTree();
		//cria a transformacao e o nome do no a ser inserido tal transformacao
		String transformation = "translate(" + posX+ "," +posY+ ")";
		String nodeName = "agrupamentoPF"+documentLocal.type;
		ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
	}

	private static int setLocation(SVGDocumentLocal documentLocal,
			int[] positionFreeTree,int cell,int next,int sizePixelNotFree) {
		//se for do grupo 1 sera prenchenchido apenas o centro
		//caso contrario sera preenchido os cantos
		if(documentLocal.group == 1){
			//caso esteja no fim da celula
			if(next + documentLocal.getWidthTree() > (cellWidht-sizePixelNotFree)){
				
				int posX = cellWidht - (int)documentLocal.getWidthTree() - sizePixelNotFree ;
				int posY = cellHeight - (int)documentLocal.getHeightTree();
				// cria a transformacao e o nome do no a ser inserido tal transformacao
				String transformation = "translate(" + posX+ "," +posY+ ")";
				String nodeName = "agrupamentoPF"+documentLocal.type;
				ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
				//marca como ocupado os pixel do chao onde o modelo foi posicionado
				for(int i = cellWidht-1; i >= documentLocal.getWidthTree();i--)
					positionFreeTree[i] = 1;
				
				return -1;
			}else{
				int posX = next;
				int posY = cellHeight - (int)documentLocal.getHeightTree();
				// cria a transformacao e o nome do no a ser inserido tal transformacao
				String transformation = "translate(" + posX+ "," +posY+ ")";
				String nodeName = "agrupamentoPF"+documentLocal.type;
				ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
				
				//marca como ocupado os pixel do chao onde o modelo foi posicionado
				for(int i = next; i < documentLocal.getWidthTree();i++)
					positionFreeTree[i] = 1;
				
				return next + (int)documentLocal.getWidthTree();
			
			}
		
		}
		else{
			//caso for do grupo 2
			// se ultrapassar o limite no meio
			if(next + documentLocal.getWidthTree() > (cellWidht/2-sizePixelNotFree) && next <= (cellWidht/2 - sizePixelNotFree)){
				
				int posX = cellWidht/2 - (int)documentLocal.getWidthTree() - sizePixelNotFree ;
				int posY = cellHeight - (int)documentLocal.getHeightTree();
				// cria a transformacao e o nome do no a ser inserido tal transformacao
				String transformation = "translate(" + posX+ "," +posY+ ")";
				String nodeName = "agrupamentoPF"+documentLocal.type;
				ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
				//marca como ocupado os pixel do chao onde o modelo foi posicionado
				for(int i = cellWidht/2-1; i >= documentLocal.getWidthTree();i--)
					positionFreeTree[i] = 1;
				
				return cellWidht/2 + sizePixelNotFree  ;
			}else if(next + documentLocal.getWidthTree() > (cellWidht)){
				
				int posX = cellWidht - (int)documentLocal.getWidthTree()  ;
				int posY = cellHeight - (int)documentLocal.getHeightTree();
				// cria a transformacao e o nome do no a ser inserido tal transformacao
				String transformation = "translate(" + posX+ "," +posY+ ")";
				String nodeName = "agrupamentoPF"+documentLocal.type;
				ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
				//marca como ocupado os pixel do chao onde o modelo foi posicionado
				for(int i = cellWidht-1; i >= documentLocal.getWidthTree();i--)
					positionFreeTree[i] = 1;
				
				return -1;
			}else{
				int posX = next;
				int posY = cellHeight - (int)documentLocal.getHeightTree();
				// cria a transformacao e o nome do no a ser inserido tal transformacao
				String transformation = "translate(" + posX+ "," +posY+ ")";
				String nodeName = "agrupamentoPF"+documentLocal.type;
				ManipulateSVG.createTransform(documentLocal.document, nodeName, transformation);
				
				//marca como ocupado os pixel do chao onde o modelo foi posicionado
				for(int i = next; i < documentLocal.getWidthTree();i++)
					positionFreeTree[i] = 1;
				
				return next + (int)documentLocal.getWidthTree();
			
			}
		}
	}
	public static class SVGDocumentLocal{
		private int type;
		private int group;
		private double widthTree;
		private double heightTree;
		private double widthCrown;
		private double heightCrown;
		private double widthStock;
		private double heightStock;
		private double pointControlOneX;
		private double pointControlOneY;
		private double pointControlTwoX;
		private double pointControlTwoY;
		
		protected SVGDocument document;
		
		public SVGDocumentLocal(int type){
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			this.type  = type;
			File fileModel = new File(Models.modelArchive[type]);
			try {
				this.document = f.createSVGDocument(fileModel.toURI()
						.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.widthTree = dimModels.getInstance().dimensionsModel [2*type-2];
			this.heightTree = dimModels.getInstance().dimensionsModel [2*type-1];
			this.widthCrown = dimModels.getInstance().dimensionsCrown [2*type-2];
			this.heightCrown = dimModels.getInstance().dimensionsCrown [2*type-1];
			this.widthStock = dimModels.getInstance().dimensionsStock [2*type-2];
			this.heightStock = dimModels.getInstance().dimensionsStock [2*type-1];
			this.pointControlOneX = dimModels.getInstance().pointControlList.get(type-1).pointControlOneX;
			this.pointControlOneY = dimModels.getInstance().pointControlList.get(type-1).pointControlOneY;
			this.pointControlTwoX = dimModels.getInstance().pointControlList.get(type-1).pointControlTwoX;
			this.pointControlTwoY = dimModels.getInstance().pointControlList.get(type-1).pointControlTwoY;
			
			if(type <= 8) group = 1;
			else group = 2;
			
			if(this.type == 3)
				System.out.println();
			
			
			
			
		}

		public double getHeightTree() {
			return heightTree;
		}

		public void setHeightTree(double heightTree) {
			this.heightTree = heightTree;
		}

		public double getWidthTree() {
			return widthTree;
		}

		public void setWidthTree(double widthTree) {
			this.widthTree = widthTree;
		}

		public double getWidthCrown() {
			return widthCrown;
		}

		public void setWidthCrown(double widthCrown) {
			this.setWidthTree(this.getWidthTree() - this.getWidthCrown());
			this.widthCrown = widthCrown;
			this.setWidthTree(this.getWidthTree() + this.getWidthCrown());
			
			
		}

		public double getHeightCrown() {
			return heightCrown;
		}

		public void setHeightCrown(double heightCrown) {
			if(this.type <= 10){
				this.heightCrown = heightCrown;
				
				this.heightTree = this.pointControlOneY + this.heightStock;
			}else{
				this.heightCrown = heightCrown;
				this.heightTree = heightCrown;
				
			}
			
		}
		
		/*public void setHeightCrown(double heightCrown) {
			if(this.type != 9  && this.type != 3 && this.type != 8){
				this.setHeightTree(this.getHeightTree() - this.getHeightCrown());
				this.heightCrown = heightCrown;
				this.setHeightTree(this.getHeightTree() + this.getHeightCrown());
			}
			else{
				if(this.type == 9){
					this.setHeightTree(dimModels.getInstance().pointControlList.get(this.type - 1).pointControlOneY * (heightCrown/this.getHeightCrown()) + this.getHeightStock() );
					this.heightCrown = heightCrown;
				}else{
					this.setHeightTree(dimModels.getInstance().pointControlList.get(this.type - 1).pointControlOneY * (heightCrown/this.getHeightCrown()) + this.getHeightStock() );
					this.heightCrown = heightCrown;
					
				}
				
				
			}
		}*/

		public double getWidthStock() {
			return widthStock;
		}

		public void setWidthStock(double widthStock) {
			
			this.widthStock = widthStock;
			
			
		}

		public double getHeightStock() {
			return heightStock;
		}
		
		public void setHeightStock(double heightStock) {
			if(this.type <= 10){
				this.heightStock = heightStock;
				this.heightTree = this.heightStock + this.pointControlOneY;
			}else{
				this.heightStock = heightStock;
				
			}
			
		}
		

		/*public void setHeightStock(double heightStock) {
			
			if(this.type != 8 && this.type != 3){
				this.setHeightTree(this.getHeightTree() - this.getHeightStock());
				this.heightStock = heightStock;
				this.setHeightTree(this.getHeightTree() + this.getHeightStock());
				
			}else{
				this.setHeightTree(dimModels.getInstance().pointControlList.get(this.type - 1).pointControlOneY * (heightCrown/this.getHeightCrown()) + this.getHeightStock() );
				this.heightStock = heightStock;
				this.setHeightTree(dimModels.getInstance().pointControlList.get(this.type - 1).pointControlOneY * (heightCrown/this.getHeightCrown()) + this.getHeightStock() );
				
			}
		}*/

		public int getGroup() {
			return group;
		}

		public void setGroup(int group) {
			this.group = group;
		}

		public double getPointControlOneX() {
			return pointControlOneX;
		}

		public void setPointControlOneX(double pointControlOneX) {
			this.pointControlOneX = pointControlOneX;
		}

		public double getPointControlOneY() {
			return pointControlOneY;
		}

		public void setPointControlOneY(double pointControlOneY) {
			this.pointControlOneY = pointControlOneY;
		}

		public double getPointControlTwoX() {
			return pointControlTwoX;
		}

		public void setPointControlTwoX(double pointControlTwoX) {
			this.pointControlTwoX = pointControlTwoX;
		}

		public double getPointControlTwoY() {
			return pointControlTwoY;
		}

		public void setPointControlTwoY(double pointControlTwoY) {
			this.pointControlTwoY = pointControlTwoY;
		}
		
		
		
	}
	public File getInFile() {
		return inFile;
	}
	public void setInFile(File inFile) {
		this.inFile = inFile;
	}
	public File getOutFile() {
		return outFile;
	}
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}

	
}
