package database.frame.views.frames;


/*
* класс вывода информации об изданиях
* меню для перехода к другим окнам
* таблица изданий
* кнопки добавления, удаления и редактирования издания
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.SwingUtilities;
//import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import database.frame.DbHelper;
import database.frame.controllers.BooksController;
import database.frame.models.Books;
//import database.frame.models.Readers;
import database.frame.views.dialogs.BooksDialog;
//import database.frame.views.dialogs.BooksTypeDialog;
//import database.frame.views.dialogs.SearchReadersDialog;
import database.frame.views.tables.BooksTableModel;
//import database.frame.views.tables.ReadersTableModel;

@SuppressWarnings("serial")
public class BooksFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {

    private BooksController pc = new BooksController();
    private JTable booksList;
    

    private static final String INSERT_PC = "insertBook";
    private static final String DELETE_PC = "deleteBook";
    private static final String UPDATE_PC = "updateBook";

   
	public BooksFrame() throws Exception {

    	
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
        
        
     // переход к окну читателей
        JMenuItem menuItem2 = new JMenuItem("Читатели");
        menuItem2.addActionListener(new ActionListener() {
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
        menu.add(menuItem2);
        
        
        // переход к окну типов
     /*   JMenuItem menuItem3 = new JMenuItem("Типы книг");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t = new Thread() {
                    public void run() {
                        BooksTypeDialog ptd = new BooksTypeDialog();
                        ptd.setModal(true);
                        ptd.setVisible(true);
                        ptd.getResult();
                    }
                };
                t.start();
            }
        });
        menu.add(menuItem3); */
       
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        
        
        
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        JPanel bot = new JPanel();
        right.add(new JLabel("Книги:"), BorderLayout.NORTH);
        booksList = new JTable(1,7);
        right.add(new JScrollPane(booksList), BorderLayout.CENTER);
        JButton btnAddPb = new JButton("Добавить");
        btnAddPb.setName(INSERT_PC);
        btnAddPb.addActionListener(this);
        JButton btnUpdPb = new JButton("Испраить");
        btnUpdPb.setName(UPDATE_PC);
        btnUpdPb.addActionListener(this);
        JButton btnDelPb = new JButton("Удалить");
        btnDelPb.setName(DELETE_PC);
        btnDelPb.addActionListener(this);
        
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddPb);
        pnlBtnSt.add(btnUpdPb);
        pnlBtnSt.add(btnDelPb);
        right.add(pnlBtnSt, BorderLayout.SOUTH);

       
        bot.add(right, BorderLayout.CENTER);

       
        getContentPane().add(bot, BorderLayout.CENTER);

        
        setBounds(100, 100, 700, 500);
    }

    //обработчик событий
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(INSERT_PC)) {
                insertBook();
            }
            if (c.getName().equals(UPDATE_PC)) {
                updateBook();
            }
            if (c.getName().equals(DELETE_PC)) {
                deleteBook();
            }
        }
    }

  
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadBooks();
        }
    }

   
    public void stateChanged(ChangeEvent e) {
        reloadBooks();
    }

    //перерузка списка книг
    
    public void reloadBooks() {
        
        Thread t = new Thread() {
            
            public void run() {
                if (booksList != null) {
                    Collection<Books> p = new ArrayList<>();
                    try {
                        p = pc.getAllBooks();
                        booksList.setModel(new BooksTableModel(new Vector<Books>(p)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(BooksFrame.this, e.getMessage());
                    }
                }
            }
            
        };
       
        t.start();
    }

    //добавление книги
    
    private void insertBook() {
        Thread t = new Thread() {
            public void run() {
                try {
                	
                	 //запуск диалогового окна для добавления книги
                	BooksDialog pd = new BooksDialog(true, BooksFrame.this);
                    pd.setModal(true);
                    pd.setVisible(true);
                    if (pd.getResult()) {
                    	Books p = pd.getBook();
                        pc.insertBook(p);
                        reloadBooks();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(BooksFrame.this, e.getMessage());
                }
            }
        };
        t.start();
    }

    //редактироваие инф книги
    private void updateBook() {
        Thread t = new Thread() {
            public void run() {
                if (booksList != null) {
                    BooksTableModel ptm = (BooksTableModel) booksList.getModel();
                    if (booksList.getSelectedRow() >= 0) {
                        Books p = ptm.getBook(booksList.getSelectedRow());
                        try {
                        	//запуск диалогового окна для добавления книги
                        	BooksDialog pd = new BooksDialog(false, BooksFrame.this);
                            pd.setBook(p);
                            pd.setModal(true);
                            pd.setVisible(true);
                            if (pd.getResult()) {
                                Books up = pd.getBook();
                                pc.updateBook(up);
                                reloadBooks();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(BooksFrame.this, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(BooksFrame.this,
                                "Необходимо выделить книгу в списке");
                    }
                }
            }
        };
        t.start();
    }

//удаление книги
    private void deleteBook() {
        Thread t = new Thread() {
            public void run() {
                if (booksList != null) {
                	BooksTableModel ptm = (BooksTableModel) booksList.getModel();
                    if (booksList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(BooksFrame.this,
                                "Вы хотите удалить книгу?", "Удаление книги",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        	Books p = ptm.getBook(booksList.getSelectedRow());
                            try {
                                pc.deleteBook(p);
                                reloadBooks();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(BooksFrame.this, e.getMessage());
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(BooksFrame.this, "Необходимо выделить книгу в списке");
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

     