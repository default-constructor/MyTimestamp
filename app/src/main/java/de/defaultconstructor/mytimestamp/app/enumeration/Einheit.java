package de.defaultconstructor.mytimestamp.app.enumeration;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public enum Einheit {

    DATUM, MASZ, WAEHRUNG;

    public enum Masz {

        ;

        public enum Gruppe {

            LAENGE("Meter", "Länge", "L", "m", "l"),
            LICHTSTAERKE("Candela", "Lichtstärke", "J", "cd", "Iv"),
            MASSE("Kilogramm", "Masse", "M", "kg", "m"),
            STOFFMENGE("Mol", "Stoffmenge", "N", "mol", "n"),
            STROMSTAERKE("Ampere", "Stromstärke", "I", "A", "i"),
            THERMODYNAMISCHE_TEMPERATUR("Kelvin", "Thermodynamische Temperatur", "Θ", "K", "T"),
            ZEIT("Sekunde", "Zeit", "T", "s", "t");

            public static Gruppe getByBasiseinheit(String basiseinheit) {
                return getInstanceByProperty("basiseinheit", basiseinheit);
            }

            public static Gruppe getByDimensionsname(String dimensionsname) {
                return getInstanceByProperty("dimensionsname", dimensionsname);
            }

            public static Gruppe getByDimensionssymbol(String dimensionssymbol) {
                return getInstanceByProperty("dimensionssymbol", dimensionssymbol);
            }

            public static Gruppe getByEinheitenzeichen(String einheitenzeichen) {
                return getInstanceByProperty("einheitenzeichen", einheitenzeichen);
            }

            public static Gruppe getByFormelzeichen(String formelzeichen) {
                return getInstanceByProperty("formelzeichen", formelzeichen);
            }

            private static Gruppe getInstanceByProperty(String fieldName, String value) {
                try {
                    for (Gruppe gruppe : values()) {
                        Field field = Gruppe.class.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object object = field.get(gruppe);
                        if (object.equals(value)) {
                            return gruppe;
                        }
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public String basiseinheit;
            public String dimensionsname;
            public String dimensionssymbol;
            public String einheitenzeichen;
            public String formelzeichen;

            Gruppe(String basiseinheit, String dimensionsname, String dimensionssymbol,
                   String einheitenzeichen, String formelzeichen) {
                this.basiseinheit = basiseinheit;
                this.dimensionsname = dimensionsname;
                this.dimensionssymbol = dimensionssymbol;
                this.einheitenzeichen = einheitenzeichen;
                this.formelzeichen = formelzeichen;
            }

            public enum Laenge {

                METRISCH;

                public enum Maszsystem {

                    METRISCH("Meter", "Metrisches Einheitensystem");

                    public String basiseinheit;
                    public String praefixname;

                    Maszsystem(String basiseinheit, String praefixname) {
                        this.basiseinheit = basiseinheit;
                        this.praefixname = praefixname;
                    }

                    public enum Metrisch {

                        ANGSTROEM("Ångström", "Å", BigDecimal.valueOf(0.0000000001)),
                        DEKAMETER("Dekameter", "dam", BigDecimal.valueOf(10.0)),
                        DEZIMETER("Dezimeter", "dm", BigDecimal.valueOf(0.1)),
                        FEMTOMETER("Femtometer", "fm", BigDecimal.valueOf(0.000000000000001)),
                        HEKTOMETER("Hektometer", "hm", BigDecimal.valueOf(100.0)),
                        KILOMETER("Kilometer", "km", BigDecimal.valueOf(1000.0)),
                        METER("Meter", "m", BigDecimal.valueOf(1.0)),
                        MIKROMETER("Mikrometer", "µm", BigDecimal.valueOf(0.000001)),
                        MILLIMETER("Millimeter", "mm", BigDecimal.valueOf(0.001)),
                        MYRIAMETER("Myriameter", "Mm", BigDecimal.valueOf(10000.0)),
                        NANOMETER("Nanometer", "nm", BigDecimal.valueOf(0.000000001)),
                        PIKOMETER("Pikometer", "pm", BigDecimal.valueOf(0.000000000001)),
                        ZENTIMETER("Zentimeter", "cm", BigDecimal.valueOf(0.01));

                        public String einheitenname;
                        public String einheitenzeichen;
                        public BigDecimal umrechnungsfaktor;

                        Metrisch(String einheitenname, String einheitenzeichen, BigDecimal umrechnungsfaktor) {
                            this.einheitenname = einheitenname;
                            this.einheitenzeichen = einheitenzeichen;
                            this.umrechnungsfaktor = umrechnungsfaktor;
                        }
                    }
                }
            }
        }
    }

    public enum Praefix {

        ATTO("Atto", "a", BigDecimal.valueOf(Math.pow(10.0, -18.0)), "Trillionstel"),
        DEKA("Deka", "da", BigDecimal.valueOf(Math.pow(10.0, 1.0)), "Zehn"),
        DEZI("Dezi", "d", BigDecimal.valueOf(Math.pow(10.0, -1.0)), "Zehntel"),
        EXA("Exa", "E", BigDecimal.valueOf(Math.pow(10.0, 18.0)), "Trillion"),
        FEMTO("Femto", "f", BigDecimal.valueOf(Math.pow(10.0, -15.0)), "Billiardstel"),
        GIGA("Giga", "G", BigDecimal.valueOf(Math.pow(10.0, 9.0)), "Milliarde"),
        HEKTO("Hekto", "h", BigDecimal.valueOf(Math.pow(10.0, 2.0)), "Hundert"),
        KILO("Kilo", "k", BigDecimal.valueOf(Math.pow(10.0, 3.0)), "Tausend"),
        MEGA("Mega", "M", BigDecimal.valueOf(Math.pow(10.0, 6.0)), "Million"),
        MIKRO("Mikro", "µ", BigDecimal.valueOf(Math.pow(10.0, -6.0)), "Millionstel"),
        MILLI("Milli", "m", BigDecimal.valueOf(Math.pow(10.0, -3.0)), "Tausendstel"),
        NANO("Nano", "n", BigDecimal.valueOf(Math.pow(10.0, -9.0)), "Milliardstel"),
        PETA("Peta", "P", BigDecimal.valueOf(Math.pow(10.0, 15.0)), "Billiarde"),
        PIKO("Piko", "p", BigDecimal.valueOf(Math.pow(10.0, -12.0)), "Billionstel"),
        TERA("Tera", "T", BigDecimal.valueOf(Math.pow(10.0, 12.0)), "Billion"),
        YOTTA("Yotta", "Y", BigDecimal.valueOf(Math.pow(10.0, 24.0)), "Quadrillion"),
        ZENTI("Zenti", "c", BigDecimal.valueOf(Math.pow(10.0, -2.0)), "Hundertstel"),
        ZEPTO("Zepto", "z", BigDecimal.valueOf(Math.pow(10.0, -21.0)), "Trilliardstel"),
        ZETTA("Zetta", "Z", BigDecimal.valueOf(Math.pow(10.0, 21.0)), "Trilliarde");

        public String praefixname;
        public String symbol;
        public BigDecimal wertZahl;
        public String wertZahlwort;

        Praefix(String praefixname, String symbol, BigDecimal wertZahl, String wertZahlwort) {
            this.praefixname = praefixname;
            this.symbol = symbol;
            this.wertZahl = wertZahl;
            this.wertZahlwort = wertZahlwort;
        }
    }

    public enum Energie {

        WATT
    }

    public enum Frequenz {

        HERTZ
    }

    public enum Gewicht {

        GRAMM("g"), KILOGRAMM("kg");

        public static Gewicht getByZeichen(String zeichen) {
            return null;
        }

        public String zeichen;

        Gewicht(String kuerzel) {
            this.zeichen = kuerzel;
        }
    }

    public enum Waehrungeinheit {

        BRITISCHE_PFUND("GBP", "£"),
        CHINESISCHE_YUAN("CNY", "¥"),
        DAENISCHE_KRONE("DKK", "kr"),
        EURO("EUR", "€"),
        JAPANISCHER_YEN("JPY", "¥"),
        RUSSISCHER_RUBEL("RUB", "руб"),
        SCHWEIZER_FRANKEN("CHF", "CHF"),
        US_DOLLAR("USD", "$");

        public static Waehrungeinheit getByWaehrungscode(String waehrungscode) {
            for (Waehrungeinheit waehrungeinheit : values()) {
                if (waehrungeinheit.equals(waehrungscode)) {
                    return waehrungeinheit;
                }
            }
            return null;
        }

        public static Waehrungeinheit getByWaehrungssymbol(String waehrungssymbol) {
            for (Waehrungeinheit waehrungeinheit : values()) {
                if (waehrungeinheit.equals(waehrungssymbol)) {
                    return waehrungeinheit;
                }
            }
            return null;
        }

        public String waehrungscode;
        public String waehrungssymbol;

        Waehrungeinheit(String waehrungscode, String waehrungssymbol) {
            this.waehrungscode = waehrungscode;
            this.waehrungssymbol = waehrungssymbol;
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
