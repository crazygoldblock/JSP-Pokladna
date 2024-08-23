package Core;

public enum Stav {
    Priprava,
    Dokonceno;

    @Override
    public String toString() {
        switch (this) {
            case Priprava:
                return "Příprava";
            case Dokonceno:
                return "Dokončeno";
            default:
                throw new NullPointerException("Neznámí stav");
        }
    }
    public int GetCode() {
        return this.ordinal() + 1;
    }
    public static Stav GetFromCode(int code) {
        switch (code) {
            case 1:
                return Priprava;
            case 2:
                return Dokonceno;
            default:
                throw new NullPointerException("Neznámí stav");
        }
    } 
}
