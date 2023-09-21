package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

public class Produto {
    protected int codigo;
    protected String descricao;
    protected double precompra;
    protected double prevenda;
    protected double lucro;
    protected int quantidade;
    protected int estminimo;
    protected Fornecedor fornecedor;
    public Produto(int cod, String desc, int min, double lucro, Fornecedor forn){
        codigo = cod;
        descricao = desc;
        estminimo = min;
        this.lucro = lucro;
        fornecedor = forn;
    }
    public void compra(int quant, double val){
        if(quant < 0 || val < 0) return;
        precompra = (quantidade*precompra + quant*val)/(quantidade+quant);
        prevenda = precompra*lucro + precompra;
        quantidade += quant;
    }
    public double venda(int quant) throws ProdutoVencido {
        if(quant > quantidade) return -1;
        quantidade -= quant;
        return (quant*prevenda);
    }
    public int getCodigo(){
        return codigo;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public Fornecedor getFornecedor(){
        return fornecedor;
    }
    public int getEstminimo(){
        return estminimo;
    }

    public String getDescricao() {
        return descricao;
    }
}
