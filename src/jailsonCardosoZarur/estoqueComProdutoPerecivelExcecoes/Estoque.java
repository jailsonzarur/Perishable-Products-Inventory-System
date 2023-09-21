package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Estoque {
    ArrayList<Produto> produtos = new ArrayList<>();
    int qtdprod = 0;
    public void incluir(Produto p) throws DadosInvalidos, ProdutoJaCadastrado {
        int c = 0;
        if(p == null){
            throw new DadosInvalidos();
        }
        if(p.getCodigo() < 0 || p.getDescricao() == null || p.getDescricao().isEmpty() || p.getFornecedor().getCnpj() <= 0 || p.getFornecedor().getNome().isEmpty()){
            throw new DadosInvalidos();
        }
        for( int i = 0 ; i < p.getDescricao().length() ; i++ ){
            if(p.getDescricao().charAt(i) == ' '){
                c++;
            }
        }
        if(c == p.getDescricao().length()){
            throw new DadosInvalidos();
        }
        if(p.getCodigo() <= 0) throw new DadosInvalidos();
        for( int i = 0 ; i < qtdprod ; i++ ){
            if(p.getCodigo() == produtos.get(i).getCodigo()) throw new ProdutoJaCadastrado();
        }
        produtos.add(qtdprod, p);
        qtdprod++;
    }
    public void comprar(int cod, int quant, double preco, Date val) throws ProdutoInexistente, ProdutoNaoPerecivel, DadosInvalidos{
        if(quant <= 0 || preco < 0) throw new DadosInvalidos();
        if(pesquisar(cod) == null) throw new ProdutoInexistente();
        Produto produto = pesquisar(cod);
        boolean aux = produto instanceof ProdutoPerecivel;
        if(aux){
            Date data = Date.from(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))));
            data.setTime(data.getTime());
            if(val == null){
                produto.compra(quant, preco);
                return;
            }
            if(data.after(val)){
                return;
            }else{
                ProdutoPerecivel perecivel = (ProdutoPerecivel) produto;
                perecivel.compra(quant, preco, val);
                return;
            }
        }else{
            if(val != null){
                throw new ProdutoNaoPerecivel();
            }else{
                produto.compra(quant, preco);
                return;
            }
        }

    }
    public double vender(int cod, int quant) throws DadosInvalidos, ProdutoInexistente, ProdutoVencido {
        if(quant <= 0 || cod <= 0){
            throw new DadosInvalidos();
        }else{
            for( int i = 0 ; i < qtdprod ; i++ ){
                if(produtos.get(i).getCodigo() == cod){
                    if(produtos.get(i) instanceof ProdutoPerecivel){
                        ProdutoPerecivel pp = (ProdutoPerecivel) produtos.get(i);
                        return pp.venda(quant);
                    }else{
                        return produtos.get(i).venda(quant);
                    }

                }
            }
        }
        throw new ProdutoInexistente();
    }
    public int quantidade(int cod){
        for( int i = 0 ; i < qtdprod ; i++ ){
            if(produtos.get(i).getCodigo() == cod){
                return produtos.get(i).getQuantidade();
            }
        }
        return 0;
    }
    public Fornecedor fornecedor(int cod){
        for( int i = 0 ; i < qtdprod ; i++ ){
            if(produtos.get(i).getCodigo() == cod){
                return produtos.get(i).getFornecedor();
            }
        }
        return null;
    }
    public ArrayList<Produto> estoqueAbaixoDoMinimo(){
        ArrayList<Produto> aux = new ArrayList<>();
        int posaux = 0;
        for( int i = 0 ; i < qtdprod ; i++ ){
            if(produtos.get(i).getQuantidade() < produtos.get(i).getEstminimo()){
                aux.add(posaux, produtos.get(i));
                posaux++;
            }
        }
        return aux;
    }
    public Produto pesquisar(int cod) throws ProdutoInexistente {
        for(int i = 0; i < qtdprod; i++){
            if(produtos.get(i).getCodigo() == cod){
                return produtos.get(i);
            }
        }
        throw new ProdutoInexistente();
    }
    public ArrayList<Produto> estoqueVencido(){
        Date data = Date.from(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))));
        data.setTime(data.getTime());
        ArrayList<Produto> vencidos = new ArrayList<>();
        int qtdvencidos = 0;
        for( int i = 0 ; i < qtdprod ; i++ ){
            if(produtos.get(i) instanceof ProdutoPerecivel){
                ProdutoPerecivel aux = (ProdutoPerecivel) produtos.get(i);
                for( int j = 0 ; j < aux.qtdLotes ; j++ ){
                    if(aux.lotes.get(j).validade.before(data)){
                        vencidos.add(qtdvencidos, aux);
                        qtdvencidos++;
                        break;
                    }
                }
            }
        }
        return vencidos;
    }

    public int quantidadeVencidos(int cod) throws ProdutoInexistente {
        int qtdvencidos = 0;
        for(Produto p : produtos){
            if(p.getCodigo() == cod){
                if(p instanceof ProdutoPerecivel){
                    ProdutoPerecivel pp = (ProdutoPerecivel) p;
                    for(Lote l : pp.lotes){
                        qtdvencidos += l.quant;
                    }
                }
            }
            return qtdvencidos;
        }
        throw new ProdutoInexistente();
    }
}
