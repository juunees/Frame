package database.frame.views.dialogs;

/*
* класс диалога для книги (создание нового, редактирование существующего)
*/

import database.frame.controllers.BooksController;
//import database.frame.controllers.BooksTypesController;
//import database.frame.controllers.ReadersController;
import database.frame.models.Books;
//import database.frame.models.BooksType;
//import database.frame.models.Readers;
import database.frame.views.frames.BooksFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Vector;

import javax.swing.*;


@SuppressWarnings("serial")


public class BooksDialog extends JDialog implements ActionListener {

   
	  private static final int D_HEIGHT = 150;  
	    private final static int D_WIDTH = 510;  
	    private final static int L_X = 10;      
	    private final static int L_W = 100;     
	    private final static int C_W = 150;      
   
    private BooksFrame owner;
  
    private boolean result = false;                      // возвращаемые результат
    private int idBook = 0;                               // идентефикатор книги
    private JTextField title = new JTextField();
    private JTextField author = new JTextField();
    private JTextField yearOfPublish = new JTextField();
    private JTextField price = new JTextField();
    private JTextField countOfOrders = new JTextField();
    private JTextField publishHouse = new JTextField();
   // private JComboBox<BooksType> books_typeList;                    // поле ввода типа


   
    public BooksDialog(boolean newBook, BooksFrame owner) {
        this.owner = owner;
        setTitle("Редактирование данных книги");
        getContentPane().setLayout(new FlowLayout());

        
        getContentPane().setLayout(null);

        JLabel l = new JLabel("Название:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        title.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(title);
        
        
     /*   l = new JLabel("Тип книги:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
     */
        
     // получение списка читателей
        
       /* BooksTypesController ptc = null;
        Collection<BooksType>  books_types = new ArrayList<BooksType>();
        try {
            ptc = new BooksTypesController();
            books_types = ptc.getAllBooksTypes();
            System.out.println(books_types);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        books_typeList = new JComboBox<BooksType>(new Vector<BooksType>(books_types));
        books_typeList.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(books_typeList);

*/
        
   
        l = new JLabel("Автор:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        author.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(author);

        l = new JLabel("Год публикации:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        yearOfPublish.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(yearOfPublish);

        l = new JLabel("Цена:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        price.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(price);

        l = new JLabel("Количество заказов:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        countOfOrders.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(countOfOrders);

        l = new JLabel("Издательство:", JLabel.RIGHT);
        l.setBounds(L_X, 110, L_W, 20);
        getContentPane().add(l);
        publishHouse.setBounds(L_X + L_W + 10, 110, C_W, 20);
        getContentPane().add(publishHouse);

        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        if (newBook) {
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
       
        setBounds(((int) d.getWidth() - BooksDialog.D_WIDTH) / 2, ((int) d.getHeight() - BooksDialog.D_HEIGHT) / 2,
                BooksDialog.D_WIDTH, BooksDialog.D_HEIGHT);
    }

   
  //установление полей ввода диалогового окна
    
    public void setBook(Books p) {
        idBook = p.getIdBook();
        title.setText(p.getTitle());
        author.setText(p.getAuthor());
        yearOfPublish.setText(String.valueOf(p.getYearOfPublish()));
        price.setText(String.valueOf(p.getPrice()));
        countOfOrders.setText(String.valueOf(p.getCountOfOrders()));
        publishHouse.setText(p.getPublishHouse());
        
     /*  for (int i = 0; i < books_typeList.getModel().getSize(); i++) {
        	BooksType pt = books_typeList.getModel().getElementAt(i);
            if (pt.getBooksType_id() == p.getBooksType_id()) {
                books_typeList.setSelectedIndex(i);
                break;
            }
        }*/

    }
    
    
    //получение информации из диаологового окна

    public Books getBook() {
        Books p = new Books();
        p.setIdBook(idBook);
        p.setTitle(title.getText());
        p.setAuthor(author.getText());
        p.setYearOfPublish(Integer.parseInt(yearOfPublish.getText()));
        p.setPrice(Integer.parseInt(price.getText()));
        p.setCountOfOrders(Integer.parseInt(countOfOrders.getText()));
        p.setPublishHouse(publishHouse.getText());
        //p.setBooksType_id(((BooksType) books_typeList.getSelectedItem()).getBooksType_id());
       // System.out.println(((BooksType) books_typeList.getSelectedItem()).getBooksType_id());

        return p;
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
                BooksController.getInstance().insertBook(getBook());
                owner.reloadBooks();
                title.setText("");
                author.setText("");
                yearOfPublish.setText("");
                price.setText("");
                countOfOrders.setText("");
                publishHouse.setText("");
                
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
