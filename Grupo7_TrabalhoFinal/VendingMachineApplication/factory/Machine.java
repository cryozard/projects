package factory;

import java.util.LinkedList;

public class Machine {

    private LinkedList<Produto> productList = new LinkedList<>();
    private LinkedList<Moeda> currencyList = new LinkedList<>();
    private double saldo = 0;
    private String prodEscolhido = "";

    public Machine(){
        productList.add(new Produto("Pepsi", 150));
        productList.add(new Produto("Bolachas", 130));
        productList.add(new Produto("Chocolate", 125));
        productList.add(new Produto("Agua", 80));
        productList.add(new Produto("Sumo Laranja", 120));
        productList.add(new Produto("Batatas", 140));
        productList.add(new Produto("Pastilhas", 100));
        productList.add(new Produto("Bolo", 200));
        productList.add(new Produto("Sandes Mista", 215));
        productList.add(new Produto("Amendoins", 110));

        currencyList.add(new Moeda("2 Euros", 200));
        currencyList.add(new Moeda("1 Euro", 100));
        currencyList.add(new Moeda("50 Centimos", 50));
        currencyList.add(new Moeda("20 Centimos", 20));
        currencyList.add(new Moeda("10 Centimos", 10));
        currencyList.add(new Moeda("5 Centimos", 5));

        currencyList.sort((m1, m2) -> m2.valor - m1.valor);
    }

    protected Produto[] getProdutos(){
        Produto[] productArray = new Produto[productList.size()];
        int i = 0;
        for (Produto p : productList){
            productArray[i] = p;
            i++;
        }
        return productArray;
    }

    protected String[] getNomeProdutos() {
        String[] productArray = new String[productList.size()];
        int i = 0;
        for (Produto p : productList){
            productArray[i] = p.nome;
            i++;
        }
        return productArray;
    }

    protected String[] getDinheiro() {
        String[] currencyArray = new String[currencyList.size()];
        int i = 0;
        for (Moeda m : currencyList) {
            currencyArray[i] = m.nome;
            i++;
        }
        return currencyArray;
    }

    protected String insertMoeda(String description) {
        for(Moeda m : currencyList) if (description.equals(m.nome)) saldo += m.valor;
        return "Saldo: " + saldo/100 + "€";
    }

    protected String chooseProduto(String description) {
        for(Produto p : productList){
            if (description.equals(p.nome)){
                prodEscolhido = p.nome;
                if (saldo < p.valor) return p + "\nSaldo: " + saldo/100 + "€";
                    saldo -= p.valor;
                    p.numProdSold ++;
                    p.valProdSold += p.valor/100;
                    return imprimeMoedas(saldo);
            }
        }
        return "ERROR";
    }

    private String imprimeMoedas(double dinheiro) {
        LinkedList<String> listaMoedas = new LinkedList<>();
        int[] moedas = new int[currencyList.size()];

        for (Moeda m : currencyList){
            while (dinheiro - m.valor >= 0){
                dinheiro -= m.valor;
                moedas[currencyList.indexOf(m)]++;
            }
        }
        for(int i = 0; i<moedas.length; i++) {
            if(moedas[i]>0) {
                String moeda = "moeda";
                if(moedas[i] != 1) moeda+="s";
                String t = (moedas[i]+ " " + moeda + " de "+ currencyList.get(i));
                listaMoedas.add(t);
            }
        }
        String troco = listaMoedas.toString().replace("[", "").replace("]", "");
        saldo = 0;
        if (troco.isEmpty()) troco = "0€";
        return "A dispensar " + prodEscolhido + "\nTroco: " + troco;
    }
}
