package cl.transbank.pos.exceptions;

public class TransbankParseException extends TransbankException {
    public TransbankParseException(String line) {
        super(line);
    }

    public TransbankParseException(String line, Exception e) {
        super(line, e);
    }
}
