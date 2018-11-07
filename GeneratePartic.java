import java.io.*;
import java.util.*;

public class GeneratePartic {

    private static ArrayList<Participant> participants = new ArrayList<Participant>();

    public static List<Participant> generate(int size) {
        for (int i = 0; i < size; i++) {
            participants.add(new Participant.Builder().buildRandom());
        }
        Collections.shuffle( participants );
        return participants;
    }

    private static void writingToFile (File file) throws IOException {
        BufferedWriter writer = new BufferedWriter( new FileWriter( file, true ) );
        writer.newLine();

        for (Participant p : participants) {
            writer.write( p.getName() + " " + p.getSurname() + " " +p.getCountry()+ " " + p.getAge() + " " + p.getGender() + " " + p.isCame() + " " + p.getPartNo() );
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String [] args) throws IOException {
        generate( 20 );
        File file = new File("D:/Java/Lab2/files/fileForTests.txt");
        writingToFile(file);
    }
}
