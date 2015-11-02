package database.frame.views.dialogs;

/*
* класс диалога для читателя (создание нового, редактирование существующего)
*/
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.AbstractButton;
//import javax.swing.ButtonGroup;
import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;


import database.frame.models.Readers;
import database.frame.controllers.ReadersController;
import database.frame.views.frames.ReadersFrame;

@SuppressWarnings("serial")
public class ReadersDialog extends JDialog implements ActionListener {

    private static final int D_HEIGHT = 150;  
    private final static int D_WIDTH = 510;  
    private final static int L_X = 10;      
    private final static int L_W = 100;     
    private final static int C_W = 150;     
   
    private ReadersFrame owner;                    //окно из которого, было вызвано диалоговое окно
    
    private boolean result = false;
   
    private int idReader = 0;
    private JTextField fullName = new JTextField();
    private JTextField job = new JTextField();
    private JTextField phoneNumber = new JTextField();
    private JTextField address = new JTextField();
    private JTextField expirationDate = new JTextField();

    
    public ReadersDialog(boolean newReader, ReadersFrame owner) {
        
        this.owner = owner;
       
        setTitle("Редактирование данных читателя");
        getContentPane().setLayout(new FlowLayout());

        
        getContentPane().setLayout(null);

        JLabel l = new JLabel("Имя:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        fullName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(fullName);

        l = new JLabel("Место работы:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        job.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(job);

        l = new JLabel("Номер телефона:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        phoneNumber.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(phoneNumber);
        
        l = new JLabel("Адрес:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        address.setBounds(L_X + L_W + 10, 70, C_W, 20);
        getContentPane().add(address);
        
        
        l = new JLabel("Дата окончания абонемента:", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        expirationDate.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(expirationDate);




        JButton btnCancel = new JButton("Отмена");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 150, 25);
        getContentPane().add(btnCancel);

        
        if (newReader) {
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
       
        setBounds(((int) d.getWidth() - ReadersDialog.D_WIDTH) / 2, ((int) d.getHeight() - ReadersDialog.D_HEIGHT) / 2,
        		ReadersDialog.D_WIDTH, ReadersDialog.D_HEIGHT);
    }

    //установлениe полей ввода диалогового окна 
    
    public void setReader(Readers sc) {
       idReader = sc.getReaderId();
        fullName.setText(sc.getFullName());
        job.setText(sc.getJob());
        phoneNumber.setText(sc.getPhoneNumber());
        address.setText(sc.getAddress());
        expirationDate.setText(String.valueOf(sc.getExpirationDate()));
       
    }

  //получение информации из диаологового окна
    
    public Readers getReader() {
        Readers sc = new Readers();
        sc.setIdReader(idReader);
        sc.setFullName(fullName.getText());
        sc.setJob(job.getText());
        sc.setPhoneNumber(phoneNumber.getText());
        sc.setAddress(address.getText());
        
        return sc;
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
                ReadersController.getInstance().insertReader(getReader());
                owner.reloadReaders();
                fullName.setText("");
                job.setText("");
                phoneNumber.setText("");
                address.setText("");
                expirationDate.setText("");
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