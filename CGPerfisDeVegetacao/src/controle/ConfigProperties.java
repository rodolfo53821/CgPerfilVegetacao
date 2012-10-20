package controle;

import java.math.BigDecimal;

public class ConfigProperties {

	
	
	///variaveis padrao para serem utilizados nos modelos
	// elas definem o intervalo da biomassa para identificar a quantidade de modelos tera na celula
	public double tropicalBiomass = 1;
	public double temperateBiomass = 0.666;
	public double borealBiomass = 0.5;
	public double shrubsBiomass = 0.1;
	public double gramineaBiomass = 0.05;
	
	//variaveis para definir o lai MAX
	public double tropicalLaiMax = 6;
	public double temperateLaiMax = 5;
	public double borealLaiMax = 4;
	public double shrubsLaiMax = 3;
	public double gramineaLaiMax = 1.5;
	
	
	public double aglbLimit = 10.0;
	
	public ConfigProperties(double tropicalBiomass, double temperateBiomass,
			double borealBiomass, double shrubsBiomass, double gramineaBiomass,
			double tropicalLaiMax, double temperateLaiMax, double borealLaiMax,
			double shrubsLaiMax, double gramineaLaiMax, double aglbLimit) {
		super();
		this.tropicalBiomass = tropicalBiomass;
		this.temperateBiomass = temperateBiomass;
		this.borealBiomass = borealBiomass;
		this.shrubsBiomass = shrubsBiomass;
		this.gramineaBiomass = gramineaBiomass;
		this.tropicalLaiMax = tropicalLaiMax;
		this.temperateLaiMax = temperateLaiMax;
		this.borealLaiMax = borealLaiMax;
		this.shrubsLaiMax = shrubsLaiMax;
		this.gramineaLaiMax = gramineaLaiMax;
		this.aglbLimit = aglbLimit;
	}

	public ConfigProperties() {
		super();
	}
	
	
	
	public double getLaiMax(int type){
		double resp = 0;
		switch (type){
			case 1:
				resp = tropicalLaiMax;
				break;
			case 2:
				resp = tropicalLaiMax;
				break;
			case 3:
				resp = temperateLaiMax;
				break;
			case 4:
				resp = temperateLaiMax;
				break;
			case 5:
				resp = temperateLaiMax;
				break;
			case 6:
				resp = borealLaiMax;
				break;
			case 7:
				resp = borealLaiMax;
				break;
			case 8:
				resp = borealLaiMax;
				break;
			case 9:
				resp = shrubsLaiMax;
				break;
			case 10:
				resp = shrubsLaiMax;
				break;
			case 11:
				resp = gramineaLaiMax;
				break;
			case 12:
				resp = gramineaLaiMax;
				break;

			
		
		}
		return resp;
		
	}
	
	public int getNumberOfModels(int type, double aglb){
		int resp = 0;
		double interval = 1;
		switch (type){
		case 1:
			interval = tropicalBiomass;
			break;
		case 2:
			interval = tropicalBiomass;
			break;
		case 3:
			interval = temperateBiomass;
			break;
		case 4:
			interval = temperateBiomass;
			break;
		case 5:
			interval = temperateBiomass;
			break;
		case 6:
			interval = borealBiomass;
			break;
		case 7:
			interval = borealBiomass;
			break;
		case 8:
			interval = borealBiomass;
			break;
		case 9:
			interval = shrubsBiomass;
			break;
		case 10:
			interval = shrubsBiomass;
			break;
		case 11:
			interval = gramineaBiomass;
			break;
		case 12:
			interval = gramineaBiomass;
			break;

		
	
	}
		 if(aglb > 0.0){
			
			if(((BigDecimal.valueOf(aglb)).remainder(BigDecimal.valueOf(interval))).compareTo(BigDecimal.valueOf(0.0)) == 0){//eh limite do intervalo logo a conta associada sera outra
				resp = (int)(aglb / interval);
			}else{
				resp = (int)(aglb / interval) + 1 ;
				
			}
		} 
		
	return resp;
	}
	
	
	
	
	
	
}
