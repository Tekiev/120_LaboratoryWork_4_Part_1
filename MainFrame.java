package DEV120_4_1_Tekiev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

public class MainFrame extends JFrame {
    private BookListTableModel clientsTableModel = new BookListTableModel();
    private final JTable clientsTable = new JTable();
    private BookDialog clientDialog = new BookDialog(this);
    BookList bookListOut = new BookList();
    BookList bookListIn = new BookList();
    public MainFrame() {
        super("Книги библиотеки");
        super.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Да", "Нет"};
                int n = JOptionPane
                        .showOptionDialog(e.getWindow(),"Вы уверены что хотите закрыть приложение?",
                                "Подтверждение закрытия", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                     saveFile();
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        initMenu();
        initLayout();
        setBounds(300, 200, 1300, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        openFile();
    }
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Действия");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Добавить книгу", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addClient());

        addMenuItemTo(operations, "Изменить книгу", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeClient());

        addMenuItemTo(operations, "Удалить книгу", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delClient());

        setJMenuBar(menuBar);
    }
    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
                               KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }
    private void initLayout() {
        clientsTable.setModel(clientsTableModel);
        clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(clientsTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(clientsTable), BorderLayout.CENTER);
    }
    private void addClient() {
        clientDialog.prepareForAdd();
        while(clientDialog.showModal()) {
            try {
                LibraryCode pn = new LibraryCode(clientDialog.getCode());
                clientsTableModel.addClient(pn, clientDialog.getIsbn(), clientDialog.getNameBook(), clientDialog.getAuthorsField(), clientDialog.getYearField());
                bookListOut.addClient(pn, clientDialog.getIsbn(), clientDialog.getNameBook(), clientDialog.getAuthorsField(), clientDialog.getYearField());
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка добавления книги",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void changeClient() {
        int seldRow = clientsTable.getSelectedRow();
        if(seldRow == -1)
            return;
        try {
            BookInfo ci = clientsTableModel.getClient(seldRow);
            BookInfo cp = bookListOut.getClientInfo(seldRow);
            clientDialog.prepareForChange(ci);
            if (clientDialog.showModal()) {
                ci.setIsbn(clientDialog.getIsbn());
                cp.setIsbn(clientDialog.getIsbn());
                ci.setNameOfBook(clientDialog.getNameBook());
                cp.setNameOfBook(clientDialog.getNameBook());
                ci.setAuthors(clientDialog.getAuthorsField());
                cp.setAuthors(clientDialog.getAuthorsField());
                ci.setYear(clientDialog.getYearField());
                cp.setYear(clientDialog.getYearField());
                clientsTableModel.clientChanged(seldRow);
            }
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка добавления книги",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void delClient() {
        int seldRow = clientsTable.getSelectedRow();
        if(seldRow == -1)
            return;
        BookInfo ci = clientsTableModel.getClient(seldRow);
        if(JOptionPane.showConfirmDialog(this,
                "Вы действительно хотите удалить книгу\n"
                        + "под номером " + ci.getPhoneNumber() + "?",
                "Удаление книги",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            clientsTableModel.dropClient(seldRow);
            bookListOut.remove(seldRow);
        }
    }
    private void saveFile()  {
        try {
            FileOutputStream outputStream = new FileOutputStream("user.dir");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(bookListOut);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    private void openFile()  {
        try {
        FileInputStream fileInputStream = new FileInputStream("user.dir");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        bookListIn = (BookList) objectInputStream.readObject();
            for (BookInfo book: bookListIn.getBooks()){
                clientsTableModel.addClient(new LibraryCode(book.getPhoneNumber().getCode()), book.getIsbn(), book.getNameOfBook(), book.getAuthors(), book.getYear());
                bookListOut.addClient(new LibraryCode(book.getPhoneNumber().getCode()), book.getIsbn(), book.getNameOfBook(), book.getAuthors(), book.getYear());
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
