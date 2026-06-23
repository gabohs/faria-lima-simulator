import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class Simulador 
{
    private double saldo;
    private int dia_atual;
    private Map<String, Acao> mercado;
    private Map<String, Integer> portfolio;
    
    public Simulador(double saldo)
    {
        this.saldo     = saldo;
        this.dia_atual = 0;
        this.mercado   = new HashMap<>();
        this.portfolio = new TreeMap<>();
    }
    
    public void inicializarMercado()
    {
        mercado.put("IFRS", new Acao("IFRS", "Instituto Federal do RS", 50.0f));
        mercado.put("UFFS", new Acao("UFFS", "Universidade Fed. Front. Sul.", 25.0f));
        mercado.put("AUTE", new Acao("AUTE", "Autentique Assinaturas", 7.0f));
        mercado.put("CHIM", new Acao("CHIM", "Chimarrão Holdings S.A.", 12.5f));
        mercado.put("GALP", new Acao("GALP", "Galpão CTG Tradição Viva", 33.8f));
        mercado.put("ERVA", new Acao("ERVA", "Ervateira Erechim & Cia", 18.9f));
        mercado.put("BAGE", new Acao("BAGE", "Bagual Motors Pickup Ltda", 89.4f));
        mercado.put("PAMP", new Acao("PAMP", "Pampa Agro Soluções", 64.2f));
        mercado.put("CUCA", new Acao("CUCA", "Cuca Caseira Delivery", 9.3f));
        mercado.put("TCHE", new Acao("TCHE", "Tchê Tech Startups RS", 142.7f));
        mercado.put("RODE", new Acao("RODE", "Rodeio Digital Games", 27.6f));
        mercado.put("XIMI", new Acao("XIMI", "Ximia Lab Biotecnologia", 55.1f));
        mercado.put("GRES", new Acao("GRES", "Grêmio do Sul Esportes", 73.0f)); 
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
            JOptionPane.showMessageDialog(null, "Acao nula");
            return;
        }
        
        double valor_total = a.getPreco() * quantidade;
        if (this.saldo < valor_total)
        {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return;
        }
        
        this.saldo -= valor_total;
        
        this.portfolio.put(
            a.getCodigo(), portfolio.getOrDefault(codigo, 0) + quantidade
        );
        
        JOptionPane.showMessageDialog(null, "Compra Realizada");
    }
    
    public void vender(String codigo, int quantidade)
    {
        codigo = codigo.toUpperCase();
        
        if (!this.portfolio.containsKey(codigo))
        {
            JOptionPane.showMessageDialog(null, "Acao nao encontrada no seu portfolio");
            return;
        }
        
        int quant_atual = this.portfolio.get(codigo);
        if (quantidade > quant_atual)
        {
            JOptionPane.showMessageDialog(null, "Voce nao pode vender mais acoes do que possui");
            return;
        }
        
        Acao a = mercado.get(codigo);
        double valor_total = a.getPreco() * quantidade;
        
        saldo += valor_total;
        
        if (quant_atual == quantidade)
            portfolio.remove(codigo);
        else
            portfolio.put(codigo, quant_atual - quantidade);
        
        JOptionPane.showMessageDialog(null, "Venda Realizada");
    }
    
    public double getSaldo() { return this.saldo; }
    public int getDiaAtual() { return this.dia_atual; }
    public Map<String, Acao> getMercado() { return this.mercado; }
    public Map<String, Integer> getPortfolio() { return this.portfolio; }
}
