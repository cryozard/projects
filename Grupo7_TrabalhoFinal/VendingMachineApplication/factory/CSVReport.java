package factory;

import java.io.*;

public class CSVReport implements IReport {

    private Produto[] produtos;

    public CSVReport(Produto[] produtos){this.produtos = produtos;}

    @Override
    public void exportReport() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("products.csv"));
            bw.write("Produto;Numero de Vendas;Valor Total");
            for (Produto p : produtos) bw.write("\n" + p.nome + ";" + p.numProdSold + ";" + p.valProdSold);
            bw.close();
        }
        catch(IOException e){e.printStackTrace();}
    }
}
