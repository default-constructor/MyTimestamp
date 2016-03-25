package de.defaultconstructor.mytimestamp.app.enumeration;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * Created by Thomas Reno on 20.03.2016.
 */
public enum Praefix {

    ;

    public enum Einheit {

        ;

        public enum Masz {

            ATTO("Atto", "a", BigDecimal.valueOf(Math.pow(10.0, -18.0)), "Trillionstel"),
            DEKA("Deka", "da", BigDecimal.valueOf(Math.pow(10.0, 1.0)), "Zehn"),
            DEZI("Dezi", "d", BigDecimal.valueOf(Math.pow(10.0, -1.0)), "Zehntel"),
            DIMI("Dimi", "dm", BigDecimal.valueOf(Math.pow(10.0, -4.0)), "Zehntausendstel"),
            EXA("Exa", "E", BigDecimal.valueOf(Math.pow(10.0, 18.0)), "Trillion"),
            FEMTO("Femto", "f", BigDecimal.valueOf(Math.pow(10.0, -15.0)), "Billiardstel"),
            GIGA("Giga", "G", BigDecimal.valueOf(Math.pow(10.0, 9.0)), "Milliarde"),
            HEKTO("Hekto", "h", BigDecimal.valueOf(Math.pow(10.0, 2.0)), "Hundert"),
            KILO("Kilo", "k", BigDecimal.valueOf(Math.pow(10.0, 3.0)), "Tausend"),
            MEGA("Mega", "M", BigDecimal.valueOf(Math.pow(10.0, 6.0)), "Million"),
            MIKRO("Mikro", "µ", BigDecimal.valueOf(Math.pow(10.0, -6.0)), "Millionstel"),
            MILLI("Milli", "m", BigDecimal.valueOf(Math.pow(10.0, -3.0)), "Tausendstel"),
            MYRIA("Myria", "ma", BigDecimal.valueOf(Math.pow(10.0, 4.0)), "Zehntausend"),
            NANO("Nano", "n", BigDecimal.valueOf(Math.pow(10.0, -9.0)), "Milliardstel"),
            PETA("Peta", "P", BigDecimal.valueOf(Math.pow(10.0, 15.0)), "Billiarde"),
            PIKO("Piko", "p", BigDecimal.valueOf(Math.pow(10.0, -12.0)), "Billionstel"),
            TERA("Tera", "T", BigDecimal.valueOf(Math.pow(10.0, 12.0)), "Billion"),
            YOKTO("Yokto", "y", BigDecimal.valueOf(Math.pow(10.0, -24.0)), "Quadrillionstel"),
            YOTTA("Yotta", "Y", BigDecimal.valueOf(Math.pow(10.0, 24.0)), "Quadrillion"),
            ZENTI("Zenti", "c", BigDecimal.valueOf(Math.pow(10.0, -2.0)), "Hundertstel"),
            ZEPTO("Zepto", "z", BigDecimal.valueOf(Math.pow(10.0, -21.0)), "Trilliardstel"),
            ZETTA("Zetta", "Z", BigDecimal.valueOf(Math.pow(10.0, 21.0)), "Trilliarde");

            public static Masz getPraefixEinheitMaszByPraefixname(String praefixname) {
                return getInstanceByProperty("praefixname", praefixname);
            }

            private static Masz getInstanceByProperty(String fieldName, String value) {
                try {
                    for (Masz masz : values()) {
                        Field field = Masz.class.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        Object object = field.get(masz);
                        if (object.equals(value)) {
                            return masz;
                        }
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }

            public String praefixname;
            public String symbol;
            public BigDecimal zahl;
            public String zahlwort;

            Masz(String praefixname, String symbol, BigDecimal zahl, String zahlwort) {
                this.praefixname = praefixname;
                this.symbol = symbol;
                this.zahl = zahl;
                this.zahlwort = zahlwort;
            }
        }
    }
}
