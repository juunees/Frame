package database.frame.views.dialogs;

/*
* класс диалога для поиска по читателям
* 
*каждый из них заказал на абонемент все книги заданного автора
*
*/
import database.frame.controllers.ReadersController;
import database.frame.models.Readers;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Collection;

@SuppressWarnings("serial")
public  class SearchReadersDialog extends JDialog implements ActionListener {

	private final static int D_HEIGHT = 200;
    private final static int D_WIDTH = 800;
    private final static int L_X = 10;
    private final static int L_W = 150;
    private final static int C_W = 650;
    @SuppressWarnings("unused")
	private boolean result = false;
    private Collection<Readers>  readers = new ArrayList<Readers>(); //список подписчиков, которые будут найдены после запроса

    // поле ввода автора
   // private JSpinner author = new JSpinner(new SpinnerListModel(new String(null)));
    
    String[] authors = {"A","B","C"};
    SpinnerModel snl = new SpinnerListModel(authors);
  private  JSpinner author = new JSpinner(snl);

    public SearchReadersDialog() {

        setTitle("Поиск по подписчикам");
        getContentPane().setLayout(null);

        JLabel l = new JLabel("Автор:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        author.setBounds(L_X + L_W + 10, 10, C_W - 200, 20);
        getContentPane().add(author);

        // возвращение на предыдущее окно
        
        JButton btnBack = new JButton("Назад");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        btnBack.setBounds(L_X + C_W + 10, 10, 100, 20);
        getContentPane().add(btnBack);

        l = new JLabel("Найти читателей,заказавших на абонемент все книги заданного автора", JLabel.RIGHT);
        l.setBounds(L_X, 40, C_W, 20);
        getContentPane().add(l);

        JButton btnSearch1 = new JButton("Найти");
        btnSearch1.addActionListener(this);
        btnSearch1.setName("SEARCH1");
        btnSearch1.setBounds(L_X + C_W + 10, 40, 100, 20);
        getContentPane().add(btnSearch1);

        l = new JLabel("Сколько книг каждый читатель заказал в зал", JLabel.RIGHT);
        l.setBounds(L_X, 70, C_W, 20);
        getContentPane().add(l);

        JButton btnSearch2 = new JButton("Найти");
        btnSearch2.addActionListener(this);
        btnSearch2.setName("SEARCH2");
        btnSearch2.setBounds(L_X + C_W + 10, 70, 100, 20);
        getContentPane().add(btnSearch2);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(((int) d.getWidth() - SearchReadersDialog.D_WIDTH) / 2, ((int) d.getHeight() - SearchReadersDialog.D_HEIGHT) / 2,
                SearchReadersDialog.D_WIDTH, SearchReadersDialog.D_HEIGHT);
    }

    public Collection<Readers> getResult() {
        return readers;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        /*
        * 	Найти и вывести список ФИО читателей, удовлетворяющих условию: 
          каждый из них заказал на абонемент все книги заданного автора.

        */
        if (src.getName().equals("SEARCH1")) {
            try {
                String auth = (String) ((SpinnerListModel) author.getModel()).getValue();
               final String search_author = auth;
                ReadersController sc = new ReadersController();
              readers = sc.getReadersByQuery(
            		  "select * from ("+
"select distinct r.full_name, b.author, "+
  "(select  distinct count(author)   from  books b1, orders o1 "+
    "where o1.book_id = b1.id_book  and  o1.reader_id = r.id_reader"+
    "group by b1.author,o1.reader_id ) cnt1,"+
    
   "(select distinct count(author)"+
     "from books b1"+
     "where b1.author = b.author"+
      "group by b1.author) cnt2"+
      
"from orders o,"+
     "readers r," +
    " books b"+
"where r.id_reader = o.reader_id"  +
     " and b.id_book = o.book_id"+
      "and o.place = 'абонемент'"+
      "and b.author = " + search_author+ "a"+
      
    "where a.cnt1=a.cnt2" );
      
   } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
     

        /*
        * для каждого читателя :
        * •	ФИО
          •	место работы
          •	сколько книг всего им заказано в читальный зал

        */
    if (src.getName().equals("SEARCH2")) {
            try {
                /*Date date = ((SpinnerDateModel) date_of_subscription.getModel()).getDate();
                String year = date.toString();
                year = year.substring(year.length() - 4, year.length());
                final String search_year = year;*/
                ReadersController sc = new ReadersController();
              readers = sc.getReadersByQuery("select  full_name,job, count(o.reader_id)"+
"FROM readers r, orders o"+
"WHERE" +
"r.id_reader=o.reader_id AND"+
"o.place='зал'"+
"GROUP BY r.full_name,job");
         } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        setVisible(false);
    }
}

	

