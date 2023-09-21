package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

public class ProdutoInexistente extends Exception{
    public ProdutoInexistente(){
        super("Esse produto não existe");
    }
}
