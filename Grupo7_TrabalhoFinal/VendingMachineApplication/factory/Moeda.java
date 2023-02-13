package factory;

public class Moeda {

    protected String nome;
    protected int valor;

    public Moeda(String nome, int valor){
        this.nome = nome;
        this.valor = valor;
    }
    @Override
    public String toString(){
        return this.nome;
    }
}
