package org.projekt;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Objects;

public class Bild implements Comparable<Bild>{

    @Getter
    private final String kuenstler;
    @Setter
    @Getter
    private String titel;
    @Setter
    @Getter
    private int hoeheInCm;
    @Setter
    @Getter
    private int breiteInCm;
    @Setter
    @Getter
    private boolean verkauf;
    @Setter
    @Getter
    private BigDecimal preis;





    public Bild(String kuenstler, String titel, int hoeheInCm, int breiteInCm, BigDecimal preis) throws GalerieException {
        if (kuenstler == null || kuenstler.isBlank()){
            throw new GalerieException("Künstler name darf nicht Null sein oder Leer");
        }
        this.kuenstler = kuenstler;

        setTitel(titel);
        setHoeheInCm(hoeheInCm);
        setBreiteInCm(breiteInCm);
        setPreis(preis);
    }





    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" \" ").append(titel);
        sb.append(" von ").append(kuenstler).append(" (");
        sb.append(hoeheInCm).append("x").append(breiteInCm);
        sb.append(") Preis: € ").append(preis).append(",-");
        sb.append(" ").append(verkauf);
        return sb.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bild bild)) return false;

        if (!kuenstler.equals(bild.kuenstler)) return false;
        return titel.equals(bild.titel);
    }

    @Override
    public int hashCode() {
        int result = kuenstler.hashCode();
        result = 31 * result + titel.hashCode();
        return result;
    }

    @Override
    public int compareTo(Bild o) {
       if ( o == null){
           return 0;
       }
       if (this == o){
           return 0;
       }
       int kuenstvergleich = this.kuenstler.compareToIgnoreCase(o.kuenstler);
       if (kuenstvergleich != 0){
           return kuenstvergleich;
       }

       return this.titel.compareToIgnoreCase(o.titel);
    }


}
