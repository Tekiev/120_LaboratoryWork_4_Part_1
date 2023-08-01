package DEV120_4_1_Tekiev;

import java.io.Serializable;

public class BookInfo implements Serializable {
    private final LibraryCode code;
    private String isbn;
    private String nameOfBook;
    private String authors;
    private String year;

    public BookInfo(LibraryCode code, String isbn, String nameOfBook, String authors, String year) {
        if(code == null)
            throw new IllegalArgumentException("Внутрибиблиотичный код не может быть null.");
        this.code = code;
        setIsbn(isbn);
        setNameOfBook(nameOfBook);
        setAuthors(authors);
        setYear(year);
    }
    private void checkArg(String value) {
        if(value == null || value.isEmpty())
            throw new IllegalArgumentException("Поля кроме \"Авторы\" не могут быть пустыми или null");
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        checkArg(isbn);
        this.isbn = isbn;
    }
    public String getNameOfBook() {
        return nameOfBook;
    }
    public void setNameOfBook(String nameOfBook) {
        checkArg(nameOfBook);
        this.nameOfBook = nameOfBook;
    }
    public void setAuthors(String authors) {
        if(authors == null)
            throw new IllegalArgumentException("Поле авторы не может быть null.");
        this.authors = authors;
    }
    public void setYear(String year) {
        checkArg(year);
        this.year = year;
    }
    public LibraryCode getPhoneNumber() {
        return code;
    }
    public String getAuthors() {return authors;}
    public String getYear() {return year;}
    @Override
    public String toString() {
        return "BookInfo{" +
                "code=" + code +
                ", isbn='" + isbn + '\'' +
                ", nameOfBook='" + nameOfBook + '\'' +
                ", authors='" + authors + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
