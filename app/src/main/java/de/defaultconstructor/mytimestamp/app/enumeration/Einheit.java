package de.defaultconstructor.mytimestamp.app.enumeration;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public enum Einheit {

    ;

    public enum Waehrung {

        BRITISCHE_PFUND("GBP", "£"),
        CHINESISCHE_YUAN("CNY", "¥"),
        DAENISCHE_KRONE("DKK", "kr"),
        EURO("EUR", "€"),
        JAPANISCHER_YEN("JPY", "¥"),
        RUSSISCHER_RUBEL("RUB", "руб"),
        SCHWEIZER_FRANKEN("CHF", "CHF"),
        US_DOLLAR("USD", "$");

        public static Waehrung getByCode(String code) {
            for (Waehrung waehrung : values()) {
                if (waehrung.equals(code)) {
                    return waehrung;
                }
            }
            return null;
        }

        public static Waehrung getBySymbol(String symbol) {
            for (Waehrung waehrung : values()) {
                if (waehrung.equals(symbol)) {
                    return waehrung;
                }
            }
            return null;
        }

        private String code;
        private String symbol;

        public String getCode() {
            return code;
        }

        public String getSymbol() {
            return symbol;
        }

        Waehrung(String code, String symbol) {
            this.code = code;
            this.symbol = symbol;
        }
    }

    public enum Zeit {

        JAHR("J", "Jahr", "a"),
        MINUTE("m", "Minute", "min"),
        MONAT("M", "Monat", "M"),
        STUNDE("h", "Stunde", "h"),
        SEKUNDE("s", "Sekunde", "s"),
        TAG("T", "Tag", "d"),
        WOCHE("w", "Woche", "W");

        public static Zeit getByDarstellungszeichen(String darstellungszeichen) {
            for (Zeit zeit : values()) {
                if (zeit.darstellungszeichen.equals(darstellungszeichen)) {
                    return zeit;
                }
            }
            return null;
        }

        public static Zeit getByEinheitsname(String einheitsname) {
            for (Zeit zeit : values()) {
                if (zeit.einheitsname.equals(einheitsname)) {
                    return zeit;
                }
            }
            return null;
        }

        public static Zeit getByEinheitszeichen(String einheitszeichen) {
            for (Zeit zeit : values()) {
                if (zeit.einheitszeichen.equals(einheitszeichen)) {
                    return zeit;
                }
            }
            return null;
        }

        public String darstellungszeichen;
        public String einheitsname;
        public String einheitszeichen;

        Zeit(String darstellungszeichen, String einheitsname, String einheitszeichen) {
            this.darstellungszeichen = darstellungszeichen;
            this.einheitsname = einheitsname;
            this.einheitszeichen = einheitszeichen;
        }
    }
}
