package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ProdutoPerecivel extends Produto{

    ArrayList<Lote> lotes = new ArrayList<>();
    int qtdLotes = 0;


    public ProdutoPerecivel(int cod, String desc, int min, double lucro, Fornecedor forn){
        super(cod, desc, min, lucro, forn);

    }
    public void compra(int quant, double val, Date d){
        precompra = (quantidade*precompra + quant*val)/(quantidade+quant);
        prevenda = precompra*lucro + precompra;
        quantidade += quant;
        Lote aux = new Lote(quant, d);
        lotes.add(qtdLotes, aux);
        qtdLotes++;
    }

    public double venda(int quant) throws ProdutoVencido {
        Date data = Date.from(Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo"))));
        data.setTime(data.getTime());
        Collections.sort(lotes);
        int quantemp = quant;
        for(Lote l : lotes){
            if(data.before(l.validade)){
                if(l.quant <= quantemp){
                    quantemp = quantemp - l.quant;
                    l.quant -= l.quant;
                }else{
                    quantemp = quantemp - l.quant;
                    l.quant -= quantemp;

                }
            }
            if(quantemp == 0) break;
        }
        if(quant > quantidade) return -1;
        if(quantemp > 0) throw new ProdutoVencido();
        quantidade -= quant;
        return (quant*prevenda);
    }
}

