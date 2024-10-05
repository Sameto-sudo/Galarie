package org.projekt;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class GalerieTest extends TestCase {

    public void testAdden() {

        Galerie gal = new Galerie("Galarie", BigDecimal.ZERO);
        Bild bil = new Bild("Samet", "Vangoh", 50,60, BigDecimal.valueOf(7000));
        Bild ball = new Bild("Temas", "DODODO", 70,50, BigDecimal.valueOf(9000));
        gal.addBild(ball);
        gal.addBild(bil);

        System.out.println(gal.sortierteBilder());
    }


    public void testladen(){
        Galerie gal = new Galerie("Galarie", BigDecimal.ZERO);
        Bild bil = new Bild("Samet", "Vangoh", 50,60, BigDecimal.valueOf(7000));
        Bild ball = new Bild("Temas", "DODODO", 70,50, BigDecimal.valueOf(9000));

        gal.speichern("test.sav");
        gal.laden("test.sav");


    }




}