package database.frame.views.dialogs;

import database.frame.controllers.OrdersTypesController;
//import database.frame.controllers.OrdersController;
import database.frame.models.OrdersType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class OrdersTypeDialog extends JDialog implements ActionListener {

	
	private static final int D_HEIGHT = 200;
    private final static int D_WIDTH = 650;
    private final static int L_X = 10;
    private final static int L_W = 200;
    private final static int C_W = 200;
    private boolean result = false;

    private JTextField name = new JTextField();
    public OrdersTypeDialog() {

        setTitle("Добавления типа заказа");
        getContentPane().setLayout(new FlowLayout());

        getContentPane().setLayout(null);
        JLabel l = new JLabel("Название:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        name.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(name);
        
        
        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);
        JButton btnNew = new JButton("Сохранить");
        btnNew.setName("New");
        btnNew.addActionListener(this);
        btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 10, 150, 25);
        getContentPane().add(btnNew);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - OrdersTypeDialog.D_WIDTH) / 2, ((int) d.getHeight() - OrdersTypeDialog.D_HEIGHT) / 2,
        		OrdersTypeDialog.D_WIDTH, OrdersTypeDialog.D_HEIGHT);
    }

    //получение информации из диаологового окна
    
    public OrdersType getOrdersType() {
    	OrdersType pt = new OrdersType();
        pt.setName(name.getText());
        return pt;
    }

    public boolean getResult() {
        return result;
    }

    // обработчик событий

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("New")) {
            result = true;
            try {
            	OrdersTypesController.getInstance().insertOrdersType(getOrdersType());
                name.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}