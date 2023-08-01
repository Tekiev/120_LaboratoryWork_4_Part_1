package DEV120_4_1_Tekiev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BookDialog extends JDialog {
    private final JTextField codeField;
    private final JTextField isbnField;
    private final JTextField nameField;
    private final JTextField authorsField;
    private final JTextField yearField;
    private boolean okPressed;
    public BookDialog(JFrame owner) {
        super(owner, true);
        codeField = new JTextField(3);
        isbnField = new JTextField(30);
        nameField = new JTextField(30);
        authorsField = new JTextField(30);
        yearField = new JTextField(30);
        initLayout();
        setResizable(false);
    }
    private void initLayout() {
        initControls();
        initOkCancelButtons();
    }
    private void initControls() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel("Внутрибиблиотечный код");
        lbl.setLabelFor(codeField);
        p.add(lbl);
        p.add(codeField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("ISBN");
        lbl.setLabelFor(isbnField);
        p.add(lbl);
        p.add(isbnField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Название книги");
        lbl.setLabelFor(nameField);
        p.add(lbl);
        p.add(nameField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Авторы");
        lbl.setLabelFor(authorsField);
        p.add(lbl);
        p.add(authorsField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Год издания");
        lbl.setLabelFor(yearField);
        p.add(lbl);
        p.add(yearField);
        controlsPane.add(p);
        add(controlsPane, BorderLayout.CENTER);
    }
    private void initOkCancelButtons() {
        JPanel btnsPane = new JPanel();
        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okBtn.setDefaultCapable(true);
        btnsPane.add(okBtn);
        Action cancelDialogAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        JButton cancelBtn = new JButton(cancelDialogAction);
        btnsPane.add(cancelBtn);
        add(btnsPane, BorderLayout.SOUTH);
    }
    public boolean showModal() {
        pack();
        setLocationRelativeTo(getOwner());
        if(codeField.isEnabled())
            codeField.requestFocusInWindow();
        else
            isbnField.requestFocusInWindow();
        okPressed = false;
        setVisible(true);
        return okPressed;
    }
    public void prepareForAdd() {
        setTitle("Регистрация новой книги");
        codeField.setText("");
        isbnField.setText("");
        nameField.setText("");
        authorsField.setText("");
        yearField.setText("");
        codeField.setEnabled(true);
    }
    public void prepareForChange(BookInfo ci) {
        setTitle("Редактирование данных о книге");
        codeField.setText(ci.getPhoneNumber().getCode());
        isbnField.setText(ci.getIsbn());
        nameField.setText(ci.getNameOfBook());
        authorsField.setText(ci.getAuthors());
        yearField.setText(ci.getYear());
        codeField.setEnabled(false);
    }
    public String getCode() {
        return codeField.getText();
    }
    public String getIsbn() {
        return isbnField.getText();
    }
    public String getNameBook() {
        return nameField.getText();
    }
    public String getAuthorsField() {return authorsField.getText();}
    public String getYearField() {return yearField.getText();}


}
