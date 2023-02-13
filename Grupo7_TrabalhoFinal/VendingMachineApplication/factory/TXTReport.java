package factory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TXTReport implements IReport{

    private Produto[] produtos;
    public TXTReport(Produto[] produtos){this.produtos = produtos;}

    @Override
    public void exportReport() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("products.txt"));
            bw.write("Produto;Numero de Vendas;Valor Total");
            for (Produto p : produtos) bw.write("\n" + p.nome + ";" + p.numProdSold + ";" + p.valProdSold);
            bw.close();
        }
        catch(IOException e){e.printStackTrace();}
    }
}
