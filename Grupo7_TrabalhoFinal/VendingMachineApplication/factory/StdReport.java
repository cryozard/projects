package factory;

public class StdReport implements IReport{

    private Produto[] produtos;
    public StdReport(Produto[] produtos){this.produtos = produtos;}

    @Override
    public void exportReport() {
            System.out.println("----------------------------------------------------");
            System.out.printf("%-15s %15s  %15s %n", "Produto", "Numero de Vendas", "Valor Total");
            System.out.println("----------------------------------------------------");
            for (Produto p : produtos) System.out.printf("%-15s %10s %20s %n", p.nome, p.numProdSold, p.valProdSold + "€");
    }
}
