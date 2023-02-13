package factory;

public class Produto {

    protected String nome;
    protected double valor;
    protected int numProdSold;
    protected double valProdSold;

    public Produto(String nome, double valor){
        this.nome = nome;
        this.valor = valor;
    }

    @Override
    public String toString(){return this.nome + ": " + valor/100 + "€";}
}
