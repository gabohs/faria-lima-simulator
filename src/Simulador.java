import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Simulador 
{
    private double saldo;
    private int dia_atual;
    private Map<String, Acao> mercado;
    private Map<String, Integer> portfolio;
    
    public Simulador(double saldo)
    {
        this.saldo     = saldo;
        this.dia_atual = 1;
        this.mercado   = new HashMap<>();
        this.portfolio = new TreeMap<>();
    }
    
    public void inicializarMercado()
    {
        mercado.put("IFRS", new Acao("IFRS", "Instituto Federal do RS", 50.0f));
        mercado.put("UFFS", new Acao("UFFS", "Universidade Fed. Front. Sul.", 25.0f));
        mercado.put("AUTE", new Acao("AUTE", "Autentique Assinaturas", 7.0f));
    }
    
    public void atualizarMercado()
    {
        for (Acao a : mercado.values())
        {
            a.atualizarPreco();
        }
        dia_atual++;
    }
    
    public void comprar(String codigo, int quantidade)
    {
        codigo = codigo.toUpperCase();
        Acao a = mercado.get(codigo);
        
        if (a == null) 
        {
            System.out.println("Acao nao encontrada");
            return;
        }
        
        double valor_total = a.getPreco() * quantidade;
        if (this.saldo < valor_total)
        {
            System.out.println("Saldo insuficiente");
            return;
        }
        
        this.saldo -= valor_total;
        
        this.portfolio.put(
            a.getCodigo(), portfolio.getOrDefault(codigo, 0) + quantidade
        );
    }
    
    public void vender(String codigo, int quantidade)
    {
        codigo = codigo.toUpperCase();
        
        if (!this.portfolio.containsKey(codigo))
        {
            System.out.println("Acao nao encontrada no seu portfolio");
            return;
        }
        
        int quant_atual = this.portfolio.get(codigo);
        if (quantidade > quant_atual)
        {
            System.out.println("Voce nao pode vender mais acoes do que possui");
            return;
        }
        
        Acao a = mercado.get(codigo);
        double valor_total = a.getPreco() * quantidade;
        
        saldo += valor_total;
        
        if (quant_atual == quantidade)
            portfolio.remove(codigo);
        else
            portfolio.put(codigo, quant_atual - quantidade);
        
        System.out.println("Venda realizada");
    }
}
