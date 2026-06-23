import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Map;
import javax.swing.JOptionPane;

public class Tela extends javax.swing.JFrame 
{ 
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Tela.class.getName());
    
    private static Simulador simulador = new Simulador(5000.0f);
    
    private void inicializar_UI()
    {
        label_saldo.setText("Saldo: R$" + String.format("%.2f", simulador.getSaldo()));
        label_dia.setText("Dia atual: " + simulador.getDiaAtual());
    }
    
    private void mostrar_mercado()
    {
        txa_mercado.setText("");
        
        label_dia.setText("Dia atual: " + simulador.getDiaAtual());
                
        // listar acoes do mercado
        for (Acao a : simulador.getMercado().values())
        {
            String texto = a.getCodigo() + " - " + a.getNome() + "\t" + String.format("R$%.2f", a.getPreco());
            txa_mercado.append(texto + "\n");
        }
    }
    
    private void mostrar_portfolio()
    {
        txa_portfolio.setText("");
        
        for (Map.Entry<String, Integer> e : simulador.getPortfolio().entrySet())
        {
            String texto = e.getKey() + ": " + e.getValue();
            txa_portfolio.append(texto + "\n");
        }
    }

    public Tela() 
    {   
        initComponents();
        
        simulador.inicializarMercado();
        mostrar_mercado();
        
        inicializar_UI();
        
        // COMPRA
        btn_comprar.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String cod = tfd_codigo.getText();

                    if (cod.isEmpty())
                        throw new Exception("O campo codigo nao pode ser nulo");
                    
                    if (tfd_quantidade.getText().isEmpty())
                        throw new Exception("O campo quantidade nao pode ser nulo");
                    
                    Integer quant = Integer.parseInt(tfd_quantidade.getText());
                        
                    if (quant < 1)
                        throw new Exception("A quantidade deve ser maior que 1");
                    
                    simulador.comprar(cod, quant);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                finally
                {
                    mostrar_portfolio();
                    label_saldo.setText("Saldo: R$" + String.format("%.2f", simulador.getSaldo()));
                }
            }
        });
        
        // VENDER
        btn_vender.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String cod = tfd_codigo.getText();
 
                    if (cod.isEmpty())
                        throw new Exception("O campo codigo nao pode ser nulo");
                    
                    if (tfd_quantidade.getText().isEmpty())
                        throw new Exception("O campo quantidade nao pode ser nulo");
                    
                    Integer quant = Integer.parseInt(tfd_quantidade.getText());
                        
                    if (quant < 1)
                        throw new Exception("A quantidade deve ser maior que 1");
                    
                    simulador.vender(cod, quant);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                finally
                {
                    mostrar_portfolio();
                    label_saldo.setText("Saldo: R$" + String.format("%.2f", simulador.getSaldo()));
                }
            }
        });
        
        // atualizar mercado dinamicamente
        javax.swing.Timer timer_dias = new javax.swing.Timer(100, new ActionListener() {
            private long acumulador_dia     = 0; // para atualizar o dia a cada 24 segundos e mudar o mercado
            private long acumulador_segundo = 0; // para atualizar o relogio de 1 em 1 segundo
            
            private long ultimo_tick = System.currentTimeMillis();
            
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                long agora = System.currentTimeMillis();
                long dt = agora - ultimo_tick;
                ultimo_tick = agora;
                
                acumulador_dia     += dt;
                acumulador_segundo += dt;
                
                if (acumulador_segundo > 1 * 1000)
                {
                    label_relogio.setText("Relogio: " + Long.toString(acumulador_dia / 1000) + "/24h");
                    acumulador_segundo = 0;
                }
                        
                if (acumulador_dia > 24 * 1000)
                {
                    simulador.atualizarMercado();
                    
                    mostrar_mercado();
                    
                    acumulador_dia = 0;
                }
            }
        });
        timer_dias.start();        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txa_mercado = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfd_codigo = new javax.swing.JTextField();
        btn_comprar = new javax.swing.JButton();
        btn_vender = new javax.swing.JButton();
        label_saldo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txa_portfolio = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        tfd_quantidade = new javax.swing.JTextField();
        label_dia = new javax.swing.JLabel();
        label_relogio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 1000));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        jLabel1.setText("Faria Lima Simulator");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel2.setText("Mercado");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel3.setText("Portoflio");

        txa_mercado.setEditable(false);
        txa_mercado.setColumns(20);
        txa_mercado.setRows(5);
        jScrollPane1.setViewportView(txa_mercado);

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel4.setText("Comprar/Vender");

        jLabel5.setText("Codigo:");

        tfd_codigo.addActionListener(this::tfd_codigoActionPerformed);

        btn_comprar.setText("Comprar");
        btn_comprar.addActionListener(this::btn_comprarActionPerformed);

        btn_vender.setText("Vender");
        btn_vender.addActionListener(this::btn_venderActionPerformed);

        label_saldo.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        label_saldo.setText("Saldo: R$XXXX.X");

        txa_portfolio.setEditable(false);
        txa_portfolio.setColumns(20);
        txa_portfolio.setRows(5);
        jScrollPane2.setViewportView(txa_portfolio);

        jLabel6.setText("Quant.:");

        tfd_quantidade.addActionListener(this::tfd_quantidadeActionPerformed);

        label_dia.setText("Dia atual: 0");

        label_relogio.setText("Relogio: 0/24h");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_relogio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label_dia)
                                .addGap(19, 19, 19))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(label_saldo)
                        .addGap(165, 165, 165))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(95, 95, 95))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfd_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_vender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfd_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_comprar)))
                        .addGap(30, 30, 30)))
                .addGap(57, 57, 57))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(274, 274, 274))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(22, 22, 22)))
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tfd_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_comprar))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfd_quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_vender))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_saldo))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_dia)
                            .addComponent(label_relogio))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfd_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfd_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfd_codigoActionPerformed

    private void btn_comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_comprarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_comprarActionPerformed

    private void btn_venderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_venderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_venderActionPerformed

    private void tfd_quantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfd_quantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfd_quantidadeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Tela().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_comprar;
    private javax.swing.JButton btn_vender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_dia;
    private javax.swing.JLabel label_relogio;
    private javax.swing.JLabel label_saldo;
    private javax.swing.JTextField tfd_codigo;
    private javax.swing.JTextField tfd_quantidade;
    private javax.swing.JTextArea txa_mercado;
    private javax.swing.JTextArea txa_portfolio;
    // End of variables declaration//GEN-END:variables
}
