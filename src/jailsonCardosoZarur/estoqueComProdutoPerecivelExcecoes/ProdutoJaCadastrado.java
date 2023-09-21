package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

public class ProdutoJaCadastrado extends Exception{
    public ProdutoJaCadastrado(){
        super("Esse produto já foi cadastrado!");
    }
}
