import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Comparator;
import java.util.Random;

public class Participant  implements Comparable<Participant> {
    private String name;
    private String surname;
    private String partNo ;
    private String projCode = "ES";
    private static int No = 0;
    private String country;
    private int age;
    private String gender;
    private boolean came;

    public Participant(){}

    public Participant(String name, String surname, String country, int age, String gender, boolean came){
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.age = age;
        this.gender = gender;
        this.partNo = projCode + (No++);
        this.came = came;
    }



    public Participant(String line){
        String[] parts = line.split(" ");
        this.name = parts[0];
        this.surname = parts[1];
        this.country = parts[2];
        this.age = Integer.parseInt( parts[3]);
        this.gender = parts[4];
        this.came = Boolean.parseBoolean( parts[5] );
        this.partNo = parts[6];
    }

    private Participant(Builder aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Participant(String name, String surname, String country, String age, String gender, String came){
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.age =Integer.parseInt( age);
        this.gender = gender;
        this.partNo = projCode + (No++);
        this.came = Boolean.parseBoolean(came);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public boolean isCame() {
        return came;
    }

    public void setCame() {

        if(this.came){
            this.came = false;
        }
        else{
            this.came = true;
        }
    }

    public String getPartNo() {
        return partNo;
    }

    @Override
    public String toString() {
        return String.format("%-21s%-21s %-8s %-3s %-3s %-6s %-8s", name, surname, country, age, gender, came, partNo);
    }
    public final static Comparator byCountry = new Comparator() {
        // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
        @Override
        public int compare(Object o1, Object o2) {
            String k1 = ((Participant) o1).getCountry();
            String k2 = ((Participant) o2).getCountry();
            // didėjanti tvarka, pradedant nuo mažiausios
            if(k1.compareTo(k2) < 0) return -1;
            if(k1.compareTo(k2) > 0) return 1;
            return 0;
        }
    };

    public int compareTo(Participant e) {
        return this.partNo.compareTo(e.partNo);
    }

    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[] NAMES =
                {"Kazys", "Sara", "Joana", "Doantas", "Paulius","Neapolas"};
        private final static String[] SURNAMES =
                {"Pavardenis", "Surneimis", "Pavardziukas", "GalPravardziukas"};
        private final static String[] COUNTRIES =
                {"Lietuva", "Lenkija", "Ispanijam", "Ukraina", "Kipras"};
        private final static String[] GENDER =
                {"m", "v"};

        private String name = "";
        private String surname = "";
        private String country = "";
        private String gender = "";
        private int age = -1;
        private boolean came = false;


        public Participant build() {
            return new Participant(this);
        }

        public Participant buildRandom() {
            int ge = RANDOM.nextInt(GENDER.length);// modelio indeksas 1..
            return new Participant(
                    NAMES[RANDOM.nextInt(NAMES.length)],
                    SURNAMES[RANDOM.nextInt(SURNAMES.length)],
                    COUNTRIES[RANDOM.nextInt(COUNTRIES.length)],
                    1980 + RANDOM.nextInt(20),
                    GENDER[ge],
                    RANDOM.nextBoolean());
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder rida(int age) {
            this.age = age;
            return this;
        }

        public Builder kaina(boolean came) {
            this.came = came;
            return this;
        }
    }
}
