package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

public class ProdutoVencido extends Exception{
    public ProdutoVencido(){
        super("Esse produto está vencido");
    }
}
