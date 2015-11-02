package database.frame.views.frames;

/*
* класс окна для вывода информации об читателях
* таблица подписчиков
* меню перехода к другим окнам и поиск по подписчикам
* кнопки добавления, удаления и редактирования подписчиков
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

import database.frame.controllers.ReadersController;
import database.frame.views.dialogs.ReadersDialog;
import database.frame.views.dialogs.SearchReadersDialog;
import database.frame.views.tables.ReadersTableModel;
import database.frame.models.Readers;

@SuppressWarnings("serial")
public class ReadersFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    private ReadersController sc = new ReadersController();
    private JTable readersList;

    private static final String INSERT_SC = "insertReader";
    private static final String DELETE_SC = "deleteReader";
    private static final String UPDATE_SC = "updateReader";
    private static final String ALL_SUBSCRIBERS = "allReaders";


    public ReadersFrame() throws Exception {

        getContentPane().setLayout(new BorderLayout());


        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");

// переход к окну заказов 
        
        JMenuItem menuItem1 = new JMenuItem("Заказы");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    OrdersFrame s = new OrdersFrame();
                    s.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    s.setVisible(true);
                    s.reloadOrders();
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        menu.add(menuItem1);
        
        
        // переход к окну книг
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
        
        
        //запуск диалогового окна для поиска
        JMenuItem menuItem3 = new JMenuItem("Поиск");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t = new Thread() {
                    public void run() {
                        SearchReadersDialog ssd = new SearchReadersDialog();
                        ssd.setModal(true);
                        ssd.setVisible(true);
                        Collection<Readers> s = ssd.getResult();
                        readersList.setModel(new ReadersTableModel(new Vector<Readers>(s)));
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
        
        right.add(new JLabel("Читатели:"), BorderLayout.NORTH);
     
        readersList = new JTable(1,5);
        right.add(new JScrollPane(readersList), BorderLayout.CENTER);
        
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
        pnlBtnSt.setLayout(new GridLayout(1,5));
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
            if (c.getName().equals(ALL_SUBSCRIBERS)) {
                showAllReaders();
            }
            if (c.getName().equals(INSERT_SC)) {
                insertReader();
            }
            if (c.getName().equals(UPDATE_SC)) {
                updateReader();
            }
            if (c.getName().equals(DELETE_SC)) {
                deleteReader();
        }
      }
    }

   
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadReaders();
        }
    }

    
    public void stateChanged(ChangeEvent e) {
        reloadReaders();
    }

    public void reloadReaders() {
       
        Thread t = new Thread() {
            
            public void run() {
                if (readersList != null) {
                    Collection<Readers> s = new ArrayList<>();
                    try {
                        s = sc.getAllReaders();
                        readersList.setModel(new ReadersTableModel(new Vector<Readers>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(ReadersFrame.this, e.getMessage());
                    }
                }
            }
           
        };
       
        t.start();
    }

    
    private void insertReader() {
        Thread t = new Thread() {
            public void run() {
                try {
                    ReadersDialog sd = new ReadersDialog(true, ReadersFrame.this);
                    sd.setModal(true);
                    sd.setVisible(true);
                    if (sd.getResult()) {
                        Readers s = sd.getReader();
                        sc.insertReader(s);
                        reloadReaders();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(ReadersFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

  
    private void updateReader() {
        Thread t = new Thread() {
            public void run() {
                if( readersList != null) {
                    ReadersTableModel scm = (ReadersTableModel) readersList.getModel();
                    if (readersList.getSelectedRow() >= 0) {
                        Readers s = scm.getReader(readersList.getSelectedRow());
                        try {
                           ReadersDialog sd = new ReadersDialog(false, ReadersFrame.this);
                            sd.setReader(s);
                            sd.setModal(true);
                            sd.setVisible(true);
                            if (sd.getResult()) {
                                Readers us = sd.getReader();
                                sc.updateReader(us);
                                reloadReaders();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(ReadersFrame.this, e.getMessage());
                        }
                    } 
                    else {
                        JOptionPane.showMessageDialog(ReadersFrame.this,
                                "Необходимо выделить читателя в списке");
                    }
                }
            }
        };
        t.start();
    }


    private void deleteReader() {
        Thread t = new Thread() {
            public void run() {
                if (readersList != null) {
                    ReadersTableModel scb = (ReadersTableModel) readersList.getModel();
                    
                    if (readersList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(ReadersFrame.this,
                                "Вы хотите удалить подписчика?", "Удаление подписчика",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            
                            Readers s = scb.getReader(readersList.getSelectedRow());
                            try {
                                sc.deleteReader(s);
                                reloadReaders();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(ReadersFrame.this, e.getMessage());
                            }
                        }
                    } 
                    else {
                        JOptionPane.showMessageDialog(ReadersFrame.this, "Необходимо выделить подписчика в списке");
                    }
                }
            }
        };
        t.start();
    }

    
    private void showAllReaders() {
        Thread t = new Thread() {
            public void run() {
                try {
                    Collection<Readers> s = sc.getAllReaders();
                    readersList.setModel(new ReadersTableModel(new Vector<Readers>(s)));
                } catch (SQLException e){
                    JOptionPane.showMessageDialog(ReadersFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }


    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    
                    ReadersFrame sf = new ReadersFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);

                    sf.setVisible(true);
                    sf.reloadReaders();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
}
