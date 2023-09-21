package jailsonCardosoZarur.estoqueComProdutoPerecivelExcecoes;

import java.util.Date;

public class Lote implements Comparable<Lote>{

    @Override public int compareTo(Lote outroLote) { //implementação }
        if(this.validade.before(outroLote.validade)){
            return -1;
        }
        if(this.validade.after(outroLote.validade)){
            return 1;
        }
        return 0;
    }
    int quant;
    Date validade;
    public Lote(int quant, Date validade){
        this.quant = quant;
        this.validade = validade;
    }
}