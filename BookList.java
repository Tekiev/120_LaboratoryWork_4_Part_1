package DEV120_4_1_Tekiev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookList implements Serializable {
    private static final BookList instance = new BookList();
    private List<BookInfo> books = new ArrayList<>();
    private Set<LibraryCode> codes = new HashSet<>();
    BookList() {}
    public void addClient(LibraryCode code, String isbn, String nameOfBook, String authors, String year) {
        if(codes.contains(code))
            throw new IllegalArgumentException("Книга с таким библиотечным номером уже существует.");
        books.add(new BookInfo(code, isbn, nameOfBook, authors, year));
        codes.add(code);
    }
    public void remove(int index) {
        BookInfo clientInfo = books.get(index);
        codes.remove(clientInfo.getPhoneNumber());
        books.remove(index);
    }
    public List<BookInfo> getBooks() {
        return books;
    }
    public int getClientsCount() {
        return books.size();
    }
    public BookInfo getClientInfo(int index) {return books.get(index);}
    public static BookList getInstance() {
        return instance;
    }
    @Override
    public String toString() {
        return "BookList{" +
                "clients=" + books +
                ", numbers=" + codes +
                '}';
    }

}
