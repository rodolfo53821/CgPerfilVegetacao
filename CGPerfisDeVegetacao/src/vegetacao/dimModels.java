package vegetacao;

import java.util.ArrayList;
import java.util.List;



public class dimModels {
	
	
	public static dimModels instance = null;
	public static synchronized dimModels getInstance() {
		if(instance == null) instance = new dimModels();
		return instance;
	}
	private dimModels(){
		
		pointControlList = new ArrayList();
		
		buildPointControl();
		
	}
	private void buildPointControl() {
		
		
		//ponto de controle para a juncao de tronco com copa dos modelos
		
		//pf1
		pointControlList.add(new PointControl(20.26,71.25));
		//pf2
		pointControlList.add(new PointControl(20.26,71.25));
		//pf3
		pointControlList.add(new PointControl(40.49,23.15,45.08,2.01));
		//pf4
		pointControlList.add(new PointControl(18,150));
		//pf5
		pointControlList.add(new PointControl(18.41,55.80));
		//pf6
		pointControlList.add(new PointControl(11.30,128));
		//pf7
		pointControlList.add(new PointControl(11.30,128));
		//pf8
		pointControlList.add(new PointControl(45.52,6.7,47.56,1));
		//pf9
		pointControlList.add(new PointControl(22,47.3));
		//pf10
		pointControlList.add(new PointControl(9.56,26.94));
		//pf11
		pointControlList.add(new PointControl(0,0));
		//pf12
		pointControlList.add(new PointControl(0,0));
		
		
		
		
	}
	/**
	 * valores para ponto de controle para conexa entre
	 */
	
	
	public static List<PointControl> pointControlList;
	public class PointControl{
		public double pointControlOneX;
		public double pointControlOneY;
		public double pointControlTwoX;
		public double pointControlTwoY;
		
		public PointControl(double pointControlOneX, double pointControlOneY,
				double pointControlTwoX, double pointControlTwoY) {
			super();
			this.pointControlOneX = pointControlOneX;
			this.pointControlOneY = pointControlOneY;
			this.pointControlTwoX = pointControlTwoX;
			this.pointControlTwoY = pointControlTwoY;
		}

		public PointControl(double pointControlOneX, double pointControlOneY) {
			super();
			this.pointControlOneX = pointControlOneX;
			this.pointControlOneY = pointControlOneY;
			this.pointControlTwoX = 0;
			this.pointControlTwoY = 0;
		}
		
		
		
		
		
		
	}
	/*dimensoes de cada modelo para poder manipular a escala e translação capturados apartir da edição do modelo no inkscape
	 * 		largura ,	altura
	 * pf1 52.41 , 200
	 * pf2
	 * pf3
	 * pf4
	 * pf5
	 * pf6
	 * pf7
	 * pf8
	 * pf9
	 * pf10
	 * pf11
	 * pf12
	 */
	
	public static double dimensionsModel[] ={ 60.24,200,//pf1
		60.24,200 , //pf2
			91.94,179.96, //pf3
			49.06,182, //pf4
			54.22,157, //pf5
			38.39,140.29, //pf6
			38.39,140.29 , //pf7
			97.31,174.76, //pf8
			44.75,99.43, //pf9
			30,33.2, //pf10
			19,19, //pf11
			25.83,26.08 // pf12
									
			
			
	};
	public static double dimensionsStock[] ={ 20.30,130.80,//pf1
		20.30,130.80, //pf2
		5.69,156.81, //pf3
		12.44,31.31, //pf4
		18.27,103.74, //pf5
		15.54,11.62, //pf6
		15.54,11.62 , //pf7
		6.63,168.06, //pf8
		17.09,51.65, //pf9
		8.99,7.12, //pf10
		0,0, //pf11
		0,0 // pf12
		
		
	};
	
	public static double dimensionsPartCrown[] = {46.86,77.69,//um lado da copa do modelo pf3
		45.82,85.20
	};
	public static double dimensionsCrown[] ={ 60.24,71.53,//pf1
		60.24,71.53, //pf2
		91.88,79.27, //pf3
		49.06,150.92, //pf4
		54.22,56.67, //pf5
		38.39,130.87, //pf6
		38.39,130.87 , //pf7
		97.31,87.20, //pf8
		44.75,57.22, //pf9
		30,26.72, //pf10
		19,19, //pf11
		25.83,26.08 // pf12
		
	};
	


}
