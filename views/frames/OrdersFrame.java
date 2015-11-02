package database.frame.views.frames;
/*
* класс окна для вывода информации об заказах
* таблица подписок
* меню перехода к другим окнам
* кнопки добавления, удаления и редактирования подписки
*/

import java.sql.SQLException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.frame.controllers.OrdersController;
import database.frame.models.Orders;
import database.frame.views.dialogs.OrdersDialog;
import database.frame.views.tables.OrdersTableModel;
import database.frame.views.dialogs.OrdersTypeDialog;
//import database.frame.DbHelper;

@SuppressWarnings("serial")
public class OrdersFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

	
	private OrdersController sc = new OrdersController();
    private JTable ordersList;

    private static final String INSERT_SC = "insertOrder";
    private static final String DELETE_SC = "deleteOrder";
    private static final String UPDATE_SC = "updateOrder";


    public OrdersFrame() throws Exception {

        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");

        // переход к окну читателей
        
        JMenuItem menuItem1 = new JMenuItem("Читатели");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ReadersFrame s = new ReadersFrame();
                    s.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    s.setVisible(true);
                    s.reloadReaders();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem1);
        
     // переход к окну изданий
        JMenuItem menuItem2 = new JMenuItem("Книги");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    BooksFrame p = new BooksFrame();
                    p.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    p.setVisible(true);
                    p.reloadBooks();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem2);
        
     // переход к окну подписчиков
        
        JMenuItem menuItem3 = new JMenuItem("Типы заказов");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t = new Thread() {
                    public void run() {
                        OrdersTypeDialog ptd = new OrdersTypeDialog();
                        ptd.setModal(true);
                        ptd.setVisible(true);
                        ptd.getResult();
                    }
                };
                t.start();
            }
        });
        
        menu.add(menuItem3);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.add(new JLabel("Заказы:"), BorderLayout.NORTH);
        ordersList = new JTable(1, 5);
        right.add(new JScrollPane(ordersList), BorderLayout.CENTER);
        JButton btnAddSt = new JButton("Добавить");
        btnAddSt.setName(INSERT_SC);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Обновить");
        btnUpdSt.setName(UPDATE_SC);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Удалить");
        btnDelSt.setName(DELETE_SC);
        btnDelSt.addActionListener(this);
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

       
        bot.add(right, BorderLayout.CENTER);

      
        getContentPane().add(bot, BorderLayout.CENTER);

        
        setBounds(100, 100, 700, 500);
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(INSERT_SC)) {
                insertOrder();
            }
            if (c.getName().equals(UPDATE_SC)) {
                updateOrder();
            }
            if (c.getName().equals(DELETE_SC)) {
                deleteOrder();
            }
        }
    }

    
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadOrders();
        }
    }

   
    public void stateChanged(ChangeEvent e) {
        reloadOrders();
    }
    

    public void reloadOrders() {
        
        Thread t = new Thread() {
            
            public void run() {
                if (ordersList != null) {
                    Collection<Orders> s = new ArrayList<>();
                    try {
                        s = sc.getAllOrders();
                        ordersList.setModel(new OrdersTableModel(new Vector<Orders>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(OrdersFrame.this, e.getMessage());
                    }
                }
            }
        };
        t.start();
    }

    private void insertOrder() {
        Thread t = new Thread() {
            public void run() {
                try {
                	OrdersDialog sd = new OrdersDialog(true, OrdersFrame.this);
                    sd.setModal(true);
                    sd.setVisible(true);
                    if (sd.getResult()) {
                    	Orders s = sd.getOrders();
                        sc.insertOrder(s);
                        reloadOrders();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(OrdersFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    private void updateOrder() {
        Thread t = new Thread() {
            public void run() {
                if (ordersList != null) {
                	OrdersTableModel scm = (OrdersTableModel) ordersList.getModel();
                    if (ordersList.getSelectedRow() >= 0) {
                    	Orders s = scm.getOrder(ordersList.getSelectedRow());
                        try {
                        	OrdersDialog sd = new OrdersDialog(false, OrdersFrame.this);
                            sd.setOrder(s);
                            sd.setModal(true);
                            sd.setVisible(true);
                            if (sd.getResult()) {
                            	Orders us = sd.getOrders();
                                sc.updateOrder(us);
                                reloadOrders();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(OrdersFrame.this, e.getMessage());
                        }
                    } 
                    else {
                        JOptionPane.showMessageDialog(OrdersFrame.this,
                                "Необходимо выделить заказ в списке");
                    }
                }
            }
        };
        t.start();
    }


    private void deleteOrder() {
        Thread t = new Thread() {
            public void run() {
                if (ordersList != null) {
                	OrdersTableModel scb = (OrdersTableModel) ordersList.getModel();
                   
                    if (ordersList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(OrdersFrame.this,
                                "Вы хотите удалить заказ?", "Удаление заказа",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            
                        	Orders s = scb.getOrder(ordersList.getSelectedRow());
                            try {
                                sc.deleteOrder(s);
                                reloadOrders();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(OrdersFrame.this, e.getMessage());
                            }
                        }
                    } 
                    else {
                        JOptionPane.showMessageDialog(OrdersFrame.this, "Необходимо выделить заказ в списке");
                    }
                }
            }
        };
        t.start();
    }


    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    
                	OrdersFrame sf = new OrdersFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);

                    sf.setVisible(true);
                    sf.reloadOrders();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}

