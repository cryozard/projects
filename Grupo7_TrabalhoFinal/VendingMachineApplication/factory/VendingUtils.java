package factory;

public class VendingUtils {

	private static Machine machine = new Machine();

	public static void initMachine() {
		IFileLoader[] loaders = {new CSVLoader(machine.getProdutos()), new TXTLoader(machine.getProdutos())};
		for (IFileLoader f : loaders) if (f.importReport()) break;
	}
	public static String[] getNameProducts() {return machine.getNomeProdutos();}
	public static String[] getMoney() {return machine.getDinheiro();}
	public static String insertCoin(String description) {return machine.insertMoeda(description);}
	public static String chooseProduct(String description) {return machine.chooseProduto(description);}
	public static void issueReport() {
		IReport[] reports = {new CSVReport(machine.getProdutos()), new StdReport(machine.getProdutos()), new TXTReport(machine.getProdutos())};
		for (IReport rep : reports) rep.exportReport();
	}
}


