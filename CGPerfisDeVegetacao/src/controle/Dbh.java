package controle;

import java.util.HashMap;

public class Dbh {

	private HashMap<Double, Double> aglbDbhTable;

	public static Dbh instance = null;

	private Dbh() {
		aglbDbhTable = new HashMap<Double, Double>();

		fillTable();

	}

	public synchronized static Dbh getInstance() {
		if (instance == null)
			instance = new Dbh();

		return instance;

	}

	public double getDbhApproximate(double aglb) {
		double resp = 0.0;

		if (aglb >= 0.01) {//o valor de aglb deve estar entre [0.01,100]

			if (aglb <= 0.1) {

				// para poder usar a tecnica de arredondamento o valor deve
				// estar entre 1 e 0
				double decaPart;

				decaPart = aglb * 100.0;

				double roundValue = Math.floor(decaPart);

				// em caso do tenPart ser um inteiro ou .5 nao fazemos a
				// regressao linear
				if (decaPart != roundValue) {
					double baseLimit = roundValue / 100.0;
					
					double aboveLimit = (roundValue + 1) / 100.0;
					

					// funcao do primeiro grau y(x) = y0 +
					// (y1-y0)*(x-x0)/(x1-x0)
					resp = aglbDbhTable.get(baseLimit)
							+ (aglbDbhTable.get(aboveLimit) - aglbDbhTable
									.get(baseLimit)) * (aglb - baseLimit)
							/ (aboveLimit - baseLimit);

				} else {
					if (!aglbDbhTable.containsKey(aglb))
						// TODO excessao erro no if acima
						System.out
								.println("Erro nao devido pois o if acima garante a veracidade");
					resp = aglbDbhTable.get(aglb);
				}

			} else if (aglb <= 20.0) {
				double roundValue = Math.floor(aglb + 0.5);
				// em caso do aglb ser um inteiro ou .5 nao fazemos a regressao
				// linear
				if (aglb != roundValue && aglb != (roundValue - 0.5)) {
					double baseLimit;
					double aboveLimit;
					if (aglb < roundValue) {// aglb esta entre .5 e o proximo
											// inteiro a ele
						baseLimit = roundValue - 0.5;
						aboveLimit = roundValue;

					} else {
						baseLimit = roundValue;
						if (roundValue == 0)
							baseLimit = 0.1;
						aboveLimit = roundValue + 0.5;

					}

					// funcao do primeiro grau y(x) = y0 +
					// (y1-y0)*(x-x0)/(x1-x0)
					resp = aglbDbhTable.get(baseLimit)
							+ (aglbDbhTable.get(aboveLimit) - aglbDbhTable
									.get(baseLimit)) * (aglb - baseLimit)
							/ (aboveLimit - baseLimit);

				} else {
					if (!aglbDbhTable.containsKey(aglb))
						// TODO excessao erro no if acima
						System.out
								.println("Erro nao devido pois o if acima garante a veracidade");
					resp = aglbDbhTable.get(aglb);
				}
			} else if (aglb <= 100.0) {
				// para poder usar a tecnica de arredondamento o valor deve
				// estar entre 1 e 0

				double tenPart = aglb / 10.0;

				double roundValue = Math.floor(tenPart + 0.5);
				// em caso do tenPart ser um inteiro ou .5 nao fazemos a
				// regressao linear
				if (tenPart != roundValue && tenPart != (roundValue - 0.5)) {
					double baseLimit;
					double aboveLimit;
					if (tenPart < roundValue) {// tenPart esta entre .5 e o
												// proximo inteiro a ele

						baseLimit = (roundValue - 0.5) * 10.0;
						aboveLimit = (roundValue) * 10.0;

					} else {
						baseLimit = roundValue * 10.0;
						aboveLimit = (roundValue + 0.5) * 10.0;

					}

					// funcao do primeiro grau y(x) = y0 +
					// (y1-y0)*(x-x0)/(x1-x0)
					resp = aglbDbhTable.get(baseLimit)
							+ (aglbDbhTable.get(aboveLimit) - aglbDbhTable
									.get(baseLimit)) * (aglb - baseLimit)
							/ (aboveLimit - baseLimit);

				} else {
					if (!aglbDbhTable.containsKey(aglb))
						// TODO excessao erro no if acima
						System.out
								.println("Erro nao devido pois o if acima garante a veracidade");
					resp = aglbDbhTable.get(aglb);
				}

			} else {
				// TODO deve lancar uma excessao

				System.out.println("Valore fora do valor suoerior");
			}
		} else {
			// TODO deve lancar uma excessao

			System.out.println("Valore fora do valor inferior");
		}

		return resp;

	}

	private void fillTable() {

		// valores com passo de 0.01 apartir de 0.01
		aglbDbhTable.put(0.01, 39.33048823);
		aglbDbhTable.put(0.02, 10.54491569);
		aglbDbhTable.put(0.03, 5.570789733);
		aglbDbhTable.put(0.04, 3.747469578);
		aglbDbhTable.put(0.05, 2.842540411);
		aglbDbhTable.put(0.06, 2.313023558);
		aglbDbhTable.put(0.07, 1.969503107);
		aglbDbhTable.put(0.08, 1.730383007);
		aglbDbhTable.put(0.09, 1.555232328);
		aglbDbhTable.put(0.1, 1.421908524);

		// valores com passo de 0.5 apartit de 0.5
		aglbDbhTable.put(0.5, 0.682244376);
		aglbDbhTable.put(1.0, 0.690734331);
		aglbDbhTable.put(1.5, 0.753446567);
		aglbDbhTable.put(2.0, 0.828169467);
		aglbDbhTable.put(2.5, 0.907168034);
		aglbDbhTable.put(3.0, 0.988094392);
		aglbDbhTable.put(3.5, 1.070048497);
		aglbDbhTable.put(4.0, 1.152636521);
		aglbDbhTable.put(4.5, 1.235671908);
		aglbDbhTable.put(5.0, 1.319062252);
		aglbDbhTable.put(5.5, 1.402761032);
		aglbDbhTable.put(6.0, 1.48674518);
		aglbDbhTable.put(6.5, 1.571003953);
		aglbDbhTable.put(7.0, 1.655533158);
		aglbDbhTable.put(7.5, 1.740332035);
		aglbDbhTable.put(8.0, 1.825401553);
		aglbDbhTable.put(8.5, 1.910743445);
		aglbDbhTable.put(9.0, 1.996359682);
		aglbDbhTable.put(9.5, 2.082252174);
		aglbDbhTable.put(10.0, 2.16842262);
		aglbDbhTable.put(10.5, 2.254872431);
		aglbDbhTable.put(11.0, 2.341602705);
		aglbDbhTable.put(11.5, 2.428614224);
		aglbDbhTable.put(12.0, 2.515907469);
		aglbDbhTable.put(12.5, 2.603482642);
		aglbDbhTable.put(13.0, 2.691339692);
		aglbDbhTable.put(13.5, 2.779478336);
		aglbDbhTable.put(14.0, 2.867898089);
		aglbDbhTable.put(14.5, 2.956598287);
		aglbDbhTable.put(15.0, 3.045578104);
		aglbDbhTable.put(15.5, 3.134836578);
		aglbDbhTable.put(16.0, 3.224372627);
		aglbDbhTable.put(16.5, 3.314185063);
		aglbDbhTable.put(17.0, 3.404272608);
		aglbDbhTable.put(17.5, 3.494633908);
		aglbDbhTable.put(18.0, 3.585267541);
		aglbDbhTable.put(18.5, 3.676172032);
		aglbDbhTable.put(19.0, 3.767345856);
		aglbDbhTable.put(19.5, 3.85878745);

		// valores com passo de 5 apartir de 20
		aglbDbhTable.put(20.0, 3.950495221);
		aglbDbhTable.put(25.0, 4.881849006);
		aglbDbhTable.put(30.0, 5.837991855);
		aglbDbhTable.put(35.0, 6.817319537);
		aglbDbhTable.put(40.0, 7.818366388);
		aglbDbhTable.put(45.0, 8.839818608);
		aglbDbhTable.put(50.0, 9.880503349);
		aglbDbhTable.put(55.0, 10.93937193);
		aglbDbhTable.put(60.0, 12.01548307);
		aglbDbhTable.put(65.0, 13.10798802);
		aglbDbhTable.put(70.0, 14.21611772);
		aglbDbhTable.put(75.0, 15.3391721);
		aglbDbhTable.put(80.0, 16.47651106);
		aglbDbhTable.put(85.0, 17.62754688);
		aglbDbhTable.put(90.0, 18.79173788);
		aglbDbhTable.put(95.0, 19.96858303);
		aglbDbhTable.put(100.0, 21.15761737);

	}

}
