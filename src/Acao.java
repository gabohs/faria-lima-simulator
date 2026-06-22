import java.util.concurrent.ThreadLocalRandom;

public class Acao 
{
    private String codigo;
    private String nome;
    private double preco_atual;
    
    public Acao(String codigo, String nome, double preco_inicial)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.preco_atual = preco_inicial;
    }
    
    public void atualizarPreco()
    {
        double min = ThreadLocalRandom.current().nextDouble(-0.1f, -6.7f);
        double max = ThreadLocalRandom.current().nextDouble(0.0f, 6.7f);
        
        double mudanca = ThreadLocalRandom.current().nextDouble(min, max + 1);
        
        double fator_mudanca = 1 + mudanca / 100;
        this.preco_atual *= fator_mudanca;  
    }
    
    public String getCodigo() { return this.codigo; }
    public String getNome() { return this.nome; }
    public double getPreco() { return this.preco_atual; }
}
