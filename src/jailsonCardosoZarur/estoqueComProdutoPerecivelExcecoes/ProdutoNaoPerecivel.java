package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

public class ProdutoNaoPerecivel extends Exception{
    public ProdutoNaoPerecivel(){
        super("Esse produto não é perecível");
    }
}
