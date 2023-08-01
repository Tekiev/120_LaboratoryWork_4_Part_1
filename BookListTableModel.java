package DEV120_4_1_Tekiev;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;

public class BookListTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "Внутрибиблиотечный номер",
            "ISBN",
            "Название книги",
            "Авторы",
            "Год издания"
    };
    private final Set<TableModelListener> modelListeners = new HashSet<>();
    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return LibraryCode.class;
            case 1:
            case 2:
            case 3:
            case 4:
                return String.class;
        }
        throw new IllegalArgumentException("неизвестный индекс");
    }
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }
    @Override
    public int getRowCount() {
        return BookList.getInstance().getClientsCount();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookInfo ci = BookList.getInstance().getClientInfo(rowIndex);
        switch(columnIndex) {
            case 0: return ci.getPhoneNumber();
            case 1: return ci.getIsbn();
            case 2: return ci.getNameOfBook();
            case 3: return ci.getAuthors();
            case 4: return ci.getYear();
        }
        throw new IllegalArgumentException("неизвестный индекс");
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }
    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }
    public BookInfo getClient(int rowNdx) {
        return BookList.getInstance().getClientInfo(rowNdx);
    }
    public void addClient(LibraryCode code, String isbn, String nameOfBook, String authors, String year) {
        BookList.getInstance().addClient(code, isbn, nameOfBook, authors, year);
        int rowNdx = BookList.getInstance().getClientsCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }
    public void clientChanged(int index) {
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }
    public void dropClient(int index) {
        BookList.getInstance().remove(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }
    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }





}
