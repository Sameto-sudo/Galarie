package org.projekt;




import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Galerie {



    private String name;
    private List<Bild> bilder;
    private BigDecimal umsatz;




    public Galerie(String name, BigDecimal umsatz){
        if (name != null && !name.isEmpty()){
            this.name = name;
        }
        this.bilder = new ArrayList<>();
        this.umsatz = umsatz;

    }



    public boolean addBild(Bild bild) throws GalerieException {
        if (bild == null){
            throw new GalerieException("addBild ist null oder gleich");
        }

        int nichtverkauftebilder = (int) bilder.stream().filter(bild1 -> !bild1.isVerkauf()).count();


        if (nichtverkauftebilder >= 100){
            throw new GalerieException("Es dürfen nur 100 nicht verkaufte vorhanden sein");
        }

        for (Bild bild1 : bilder){
            if (bild.getTitel().equalsIgnoreCase(bild1.getTitel()) &&
                bild.getKuenstler().equalsIgnoreCase(bild1.getKuenstler())){
                return false;
            }
        }
        bilder.add(bild);
        return true;
    }


    public boolean verkaufeBilder(Bild bild) throws GalerieException {
        if (bild == null){
            return false;
        }
        if (!bilder.contains(bild)){
            throw new GalerieException("Bild ist nicht in der Galerie vorhanden");
        }

        BigDecimal verkaufspreis = bild.getPreis();
        if (verkaufspreis == null || verkaufspreis.compareTo(BigDecimal.ZERO) <= 0){
            throw new GalerieException("Bild hat kein Preis");
        }

        this.umsatz = umsatz.add(verkaufspreis);
        bild.setVerkauf(true);
        return true;

    }

    public int removeBilder(String kuenstler) throws GalerieException {

        if (kuenstler == null){
            throw new GalerieException("Küsntlern darf nnicht Null sein");
        }

        if (kuenstler.equalsIgnoreCase("alle")){
            int size = bilder.size();
            for (int i = size - 1; i >= 0; i--){
                Bild bild = bilder.get(i);
                if (!bild.isVerkauf()){
                    bilder.remove(i);
                }
            }
        }

        int entfernteBilder = 0;
        for (int i = 0; i < bilder.size(); i++){
            Bild bild = bilder.get(i);
            if (bild.getKuenstler().equals(kuenstler)){
                bilder.remove(i);
                entfernteBilder++;
                i--;
            }
        }
        return entfernteBilder;
    }

    public int removeBilder(BigDecimal preis) throws GalerieException {
        if (preis == null){
            throw new GalerieException("");
        }

        int entfernteBilder = 0;
        for (int i = 0; i < bilder.size(); i++){
            Bild bild = bilder.get(i);
            if (bild.getPreis().equals(preis)){
                bilder.remove(i);
                entfernteBilder++;
                i--;
            }
        }
        return entfernteBilder;

    }

    public int berechneAnzVerkauf(){
        int verkaufteBilder = 0;
        int size = bilder.size();
        for (int i = 0; i < size; i++){
            Bild bild = bilder.get(i);
            if (bild.isVerkauf()){
                verkaufteBilder++;
            }
        }
        return verkaufteBilder;
    }

    public BigDecimal berechneGesamtwert(String kuenstler){
        BigDecimal gesamt = BigDecimal.ZERO;

        for (Bild bild : bilder){
            if (bild.getKuenstler().equalsIgnoreCase(kuenstler)){
                gesamt.add(bild.getPreis());
            }
        }
        return gesamt;
    }


    public BigDecimal berechneDurchschnittswert(String kuenstler){
        BigDecimal gesamt = BigDecimal.ZERO;
        BigDecimal count = BigDecimal.ZERO;

        for (Bild bild : bilder){
            if (bild.getKuenstler().equalsIgnoreCase(kuenstler)){
                count.add(BigDecimal.valueOf(1));
                gesamt.add(bild.getPreis());
            }
        }
        return gesamt.divide(count);
    }

    public List<Bild> sortierteBilder(){
        List<Bild> kopie = new ArrayList<>(bilder);
        Collections.sort(kopie);
        return kopie;
    }


//    public List<Bild> sortByGroesse(){
//        List<Bild> kopie = new ArrayList<>(bilder);
//        Collections.sort(kopie,Comparator.comparing(Bild::g));
//        return kopie;
//    }


    public List<Bild> sortByPreis(){
        List<Bild> kopie = new ArrayList<>(bilder);
        Collections.sort(kopie,Comparator.comparing(Bild::getPreis));
        return kopie;
    }

    public void printBilder(String kuenstler){
        int anzahlUnverkaufte = 0;
        int gesamtWert = 0;
        List<String> unverkaufteBilder = new ArrayList<>();


//        for (Bild bild : bilder) {
//            if (bild.getKuenstler().equalsIgnoreCase(kuenstler)) {
//                unverkaufteBilder.add(getName());
//                anzahlUnverkaufte++;
//
//            }
//        }


    }

    public boolean speichern(String filename) throws GalerieException, IOException {
        if (filename == null){
            throw new GalerieException("Filename darf nicht null seinn");
        }

        if (!(filename.endsWith(".sav") || filename.endsWith(".ser"))) {
            throw new GalerieException("muss .ser oder .sav");
        }

        if (filename.length() < 7){
            throw new GalerieException("Fehler speichern 7 zeichen");
        }

        try (var oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(bilder);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return true;
    }


    public List<Bild> laden(String filename) {

        List<Bild> geladeneBilder = new ArrayList<>();

        try (var ois = new ObjectInputStream(new FileInputStream(filename))){

            geladeneBilder = (List<Bild>) ois.readObject();

        }catch (FileNotFoundException e){
            throw new GalerieException("Fehler: Datei wurde nicht gefunden. ",e);
        }catch (IOException e){
            throw new GalerieException("Fehler: Zugriff Filesystem. ",e);
        } catch (ClassNotFoundException e) {
            throw new GalerieException("Fehler: Datei kann nicht gelesen werden.",e);
        }
        return geladeneBilder;
    }




//    public void export(String filename){
//
//        if(filename == null){
//            throw new GalerieException("Fehler");
//        }
//
//        try (var fw = new FileWriter(filename)) {
//
//            fw.write(toString());
//            fw.flush();
//
//        }
//
//
//
//    }


}
