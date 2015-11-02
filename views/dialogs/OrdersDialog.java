package database.frame.views.dialogs;


/*
* класс диалога для заказа (создание новой, редактирование существующей)
*/ 
import database.frame.controllers.BooksController;
import database.frame.controllers.ReadersController;
import database.frame.controllers.OrdersController;
import database.frame.models.Books;
import database.frame.models.Readers;
import database.frame.models.Orders;
import database.frame.views.frames.OrdersFrame;

import database.frame.controllers.OrdersTypesController;
import database.frame.controllers.OrdersController;
import database.frame.models.OrdersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Date;

@SuppressWarnings("serial")
public class OrdersDialog extends JDialog implements ActionListener {

  
	
	private static final int D_HEIGHT = 200;   
    private final static int D_WIDTH = 600;   
    private final static int L_X = 10;      
    private final static int L_W = 150;     
    private final static int C_W = 150; 
    
    private OrdersFrame owner;
    private boolean result = false;

    private int idOrder = 0;           //идентификатор подписки
    @SuppressWarnings("rawtypes")
	private JComboBox readersList ;      // список читателей
    @SuppressWarnings("rawtypes")
	private JComboBox booksList;         // список книг
    
    
    @SuppressWarnings("rawtypes")
	private JComboBox orders_typeList;
    
    private JSpinner dataOfOrder = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
  
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public OrdersDialog(boolean newOrder, OrdersFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных заказа");
        getContentPane().setLayout(new FlowLayout());

        
        getContentPane().setLayout(null);

        JLabel l = new JLabel("Читатель:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        
           // получение списка читателей
        
        ReadersController sc = null;
        Collection<Readers> readers = new ArrayList<Readers>();
        try {
            sc = new ReadersController();
            readers = sc.getAllReaders();
            System.out.println("lol");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        readersList = new JComboBox(new Vector<Readers>(readers));
        readersList.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(readersList);
        
        

        l = new JLabel("Книга:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        
         // получение списка
        
        BooksController pc = null;
        Collection<Books> books = new ArrayList<Books>();
        try {
            pc = new BooksController();
            books = pc.getAllBooks();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        booksList = new JComboBox(new Vector<Books>(books));
        booksList.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(booksList);

        
        //тип
        
         l = new JLabel("Тип заказа:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
     
        
        OrdersTypesController ptc = null;
        Collection<OrdersType> orders_types = new ArrayList<OrdersType>();
        try {
            ptc = new OrdersTypesController();
            orders_types = ptc.getAllOrdersTypes();
            System.out.println(orders_types);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        orders_typeList = new JComboBox(new Vector<OrdersType>(orders_types));
        orders_typeList.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(orders_typeList);

       

        l = new JLabel("Дата заказа:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        dataOfOrder.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(dataOfOrder);

       

        
        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        if (newOrder) {
            JButton btnNew = new JButton("Сохранить");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 10, 150, 25);
            getContentPane().add(btnNew);
        } else {
            JButton btnOk = new JButton("Сохранить");
            btnOk.setName("OK");
            btnOk.addActionListener(this);
            btnOk.setBounds(L_X + L_W + C_W + 10 + 50, 10, 150, 25);
            getContentPane().add(btnOk);
        }

        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

       
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        
        setBounds(((int) d.getWidth() - OrdersDialog.D_WIDTH) / 2, ((int) d.getHeight() - OrdersDialog.D_HEIGHT) / 2,
        		OrdersDialog.D_WIDTH, OrdersDialog.D_HEIGHT);
    }

    
  //установлениe полей ввода диалогового окна
    
    public void setOrder(Orders s) {
    	idOrder = s.getIdOrder();
       
    	for (int i = 0; i < orders_typeList.getModel().getSize(); i++) {
            OrdersType pt = (OrdersType) orders_typeList.getModel().getElementAt(i);
            if (pt.getOrdersType_id() == s.getOrdersType_id()) {
                orders_typeList.setSelectedIndex(i);
                break;
            }
        }
        
    	dataOfOrder.getModel().setValue(s.getDataOfOrder());
        for (int i = 0; i < readersList.getModel().getSize(); i++) {
            Readers sb = (Readers) readersList.getModel().getElementAt(i);
            if (sb.getReaderId() == s.getIdReader()) {
            	readersList.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < booksList.getModel().getSize(); i++) {
            Books pb = (Books) booksList.getModel().getElementAt(i);
            if (pb.getIdBook() == s.getIdBook()) {
                booksList.setSelectedIndex(i);
                break;
            }
        }
    }
    
    

  // получение информации из диалогового окна
    public Orders getOrders() {
       Orders s = new Orders();
        s.setIdOrder(idOrder);
        s.setOrdersType_id(((OrdersType) orders_typeList.getSelectedItem()).getOrdersType_id());
        System.out.println(((OrdersType) orders_typeList.getSelectedItem()).getOrdersType_id());
        Date d = ((SpinnerDateModel) dataOfOrder.getModel()).getDate();
        s.setDataOfOrder(new java.sql.Date(d.getTime()));
        s.setIdReader(((Readers) readersList.getSelectedItem()).getReaderId());
        s.setIdBook(((Books) booksList.getSelectedItem()).getIdBook());
        return s;
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
                OrdersController.getInstance().insertOrder(getOrders());
                owner.reloadOrders();
                
               // where.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}

