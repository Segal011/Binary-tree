
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    private static BST<Participant> participants;

    private static ParticipantsAccounting test;

    private BST<Participant> generateZeroParticipants(){
        return new BST<Participant>(  );
    }

    private BST<Participant> generateParticipants(int n){
        for(int i = 0; i < n; i++){
            participants.add(new Participant.Builder().buildRandom());
        }
        return participants;
    }




    @BeforeAll
    private static void beforeEach(){
        participants = new BST<>(  );
        test = new ParticipantsAccounting();
    }

    @AfterAll
    private static void afterEach(){
        test.clearEverything();
    }

    @Test
    public  void generationZeroParticipantsTest(){
        assertEquals(0, generateZeroParticipants().size);
    }

    @Test
    public  void generate10ParticipantsTest(){
        assertEquals(100, generateParticipants(100).size);
    }

    @Test
    public  void readingFromFile() throws IOException {
        assertEquals(20, generate().size);
    }

    @Test
    public  void removeParticipantTestFromZero()  {
        participants = generateZeroParticipants();
        participants.remove( new Participant.Builder().buildRandom());
        assertEquals(0, participants.size);
    }

    @Test
    public  void addAndRemoveParticipantTest()  {
        participants = generateZeroParticipants();
        Participant p = new Participant.Builder().buildRandom();
        participants.add( p);
        int size1 = participants.size;
        participants.remove( p);
        int size2 = participants.size;
        assertEquals(size1, size2+1);
    }

    @Test
    public  void showWhoCameTest() throws IOException {
        ParticipantsAccounting.findCameParticipants(generate().root);
        participants = ParticipantsAccounting.getParticipantsFromList();
        assertEquals(10, participants.size);
    }

    @Test
    public  void showCountiresTest() throws IOException {
        ParticipantsAccounting.participantsCountries(generate().root);
        ArrayList<String> countries = ParticipantsAccounting.getString();
        assertEquals(5, countries.size());
    }

    @Test
    public  void setRoomsTest() throws IOException {
        ParticipantsAccounting.setRooms(generate().root, 10);
        BST<Room> rooms = ParticipantsAccounting.getRooms();
        assertEquals(5, rooms.size());
    }

    private BST<Participant> generate(){
        ArrayList<String> lines = new ArrayList<String>();
        File file = new File("D:/Java/Lab2/files/fileForTests.txt");
        try (BufferedReader br = new BufferedReader( new FileReader( file) )) {
            String st;
            while ((st = br.readLine()) != null) {
                lines.add( st );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle( lines );
        return setParticipants(lines);
    }

    private BST<Participant> setParticipants(ArrayList<String> lines) {
        BST<Participant> participant = new BST<>(  );
        for(String l : lines){
            if(l != null && l.compareTo( "" ) != 0) {
                participant.add( new Participant( l ) );
            }
        }
        return participant;
    }


}



