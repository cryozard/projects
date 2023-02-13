package factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TXTLoader implements  IFileLoader{

    private Produto[] produtos;
    public TXTLoader(Produto[] produtos){this.produtos = produtos;}

    @Override
    public boolean importReport() {
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader("products.txt"));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] products = line.split(";");
                if (products.length != 3) throw new ArrayIndexOutOfBoundsException();
                for (Produto p : produtos)
                    if (products[0].equals(p.nome)) {
                        p.numProdSold += Integer.parseInt(products[1]);
                        p.valProdSold += Double.parseDouble(products[2]);
                    }
            }
            System.out.println("Ficheiro 'products.txt' carregado.");
            return true;
        }
        catch(FileNotFoundException e) {System.out.println("Ficheiro 'products.txt' não encontrado.");}
        catch (IOException e) {e.printStackTrace();}
        catch (Exception e) {System.out.println("Ficheiro 'products.txt' possivelmente corrompido."); e.printStackTrace();}
        return false;
    }
}
