package DEV120_4_1_Tekiev;

import java.io.Serializable;
import java.util.Objects;
public class LibraryCode implements Serializable {
    private final String code;
    private String strVal;
    public LibraryCode(String code) {
        checkArg(code);
        this.code = code;
    }
    private void checkArg(String value) {
        if(value == null || value.isEmpty())
            throw new IllegalArgumentException("Внутрибиблиотечный код " + "равен null или задано пустое значение");
        for(int i = 0; i < value.length(); i++) {
            if(!Character.isDigit(value.charAt(i)))
                throw new IllegalArgumentException("Внутрибиблиотечный код " + "должен состоять только из цифр и не может быть отрицательным");
        }
    }
    public String getCode() {
        return code;
    }
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof LibraryCode other))
            return false;
        return this.code.equals(other.code);
    }
    @Override
    public int hashCode() {
        return Objects.hash(code);}
    @Override
    public String toString() {
        if(strVal == null) {
            strVal = code ;
        }
        return strVal;
    }
}
