import java.util.ArrayList;
import java.lang.String;

public class Room implements Comparable<Room> {
        private ArrayList<Participant> participants = new ArrayList<>(  );

    public String getGender() {
        return gender;
    }

    private String gender;
        private String roomNo ;
        private static int No = 0;

    public Room(Participant p) {
        this.roomNo = String.valueOf(++No);
        participants.add(p);
        this.gender = p.getGender();
    }

    public boolean isFull(){
        return (participants.size() == 4? true: false);
    }

    public void addParticipant (Participant p) {
        participants.add(p);
    }

    @Override
    public int compareTo(Room r) {
       return this.roomNo.compareTo(r.roomNo);
    }

    @Override
    public String toString() {
        return String.format("%-21s %-21s %-21s", roomNo, gender, participantsList());
    }

    private String participantsList(){
        String names = new String(  );
        for(Participant p : participants){
            names += " "+ p.getName() + " " + p.getSurname() +", ";
        }
        return names;
    }
}
