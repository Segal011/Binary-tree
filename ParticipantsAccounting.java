import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ParticipantsAccounting extends BST<Participant>{

    private static BST<Participant> tempPart = new BST<Participant>(  );
    private static ArrayList<Participant> tempPartList = new ArrayList<Participant>(  );

    public static BST<Room> getRooms() {
        return tempRooms;
    }

    private static BST<Room> tempRooms = new BST<Room>(  );
    private static ArrayList<Room> tempRoomsList = new ArrayList<Room>(  );
    private static ArrayList<String> tempString = new ArrayList<String>(  );
    private static BST<Participant> partByCountry = new BST<>(Participant.byCountry);
    private static int femalesCount;
    private static int malesCount;
    private static Participant part;



    public static void newList(){
        tempPartList.clear();
        partByCountry.clear();
        tempRoomsList.clear();
        femalesCount = 0;
        malesCount = 0;
        part = null;
    }

    public static void clearEverything(){
        tempPart .clear();
        tempRooms.clear();
        tempString.clear();
        tempPartList.clear();
        partByCountry.clear();
        tempRoomsList.clear();
        femalesCount = 0;
        malesCount = 0;
        part = null;
    }

    public static BST<Participant> getParticipantsFromList()
    {
        tempPart.clear();
        Collections.shuffle( tempPartList );
        for(Participant p : tempPartList){
            tempPart.add( p );
        }
        return tempPart;
    }

    public static BST<Participant> getParticipants()
    {
        return tempPart;
    }

    public static ArrayList<String> getString()
    {
        return tempString;
    }

    public static BST.BstNode participantsCountries(BstNode<Participant> root) {

        if(root == null){
            return root;
        }

        participantsCountries( root.left );
        if( !tempString.contains( root.element.getCountry() )){
            tempString.add(root.element.getCountry());
        }
        participantsCountries( root.right );
        return root;

    }

    public static BST.BstNode findCameParticipants(BstNode<Participant> root){
        if(root == null){
            return root;
        }

       findCameParticipants( root.left );
        if(root.element.isCame()) {
            tempPartList.add( root.element );
        }
       findCameParticipants( root.right );

       return root;
   }

    public static BST.BstNode setRooms(BstNode<Participant> root, int setRooms) {
        if (root == null) {
            return root;
        }

        try{
            findGenderCount(root);
            int rooms = 0;
            rooms += (femalesCount / 4 == 0 ? femalesCount / 4: femalesCount/ 4 + 1);
            rooms += (malesCount / 4 == 0 ? malesCount/ 4 : malesCount/ 4 + 1);
            if(setRooms < rooms){
                throw new Exception("There are more participants than can fit in rooms");
            }
            setRooms(root);
            Collections.shuffle( tempRoomsList );
            for(Room r : tempRoomsList){
                tempRooms.add( r );
            }
        }
        catch (Exception m){
            WindowFX window = new WindowFX();
            window.writeMessage( "Exception occured: " + m);
        }
        return root;
    }

    private static  BST.BstNode findGenderCount(BstNode<Participant> root){
        if(root == null){
            return root;
        }
        findGenderCount( root.left);
        if(root.element.getGender().compareTo( "v") == 0){
            malesCount++;
        }
        else if(root.element.getGender().compareTo( "m") == 0) {
            femalesCount++;
        }
        findGenderCount( root.right);
        return root;
    }

    private static  BST.BstNode setRooms(BstNode<Participant> root){
        if(root == null){
            return root;
        }
        setRooms( root.left);
        if( tempRoomsList.size() == 0){
            tempRoomsList.add( new Room( root.element ) );
        }
        else
        {
            boolean used = false;
            for(Room r: tempRoomsList){
                if(!r.isFull() && r.getGender().compareTo( root.element.getGender()) == 0){
                    r.addParticipant( root.element );
                    used = true;
                }
            }
            if(!used) {
                tempRoomsList.add( new Room( root.element ) );
            }
        }
        setRooms( root.right);
        return root;
    }

    public static Participant getParticipantByCode(BstNode<Participant> root, String text) {
        if (root == null) {
            return null;
        }
        getPart( root, text );
        return part;
    }

    private static BST.BstNode getPart(BstNode<Participant> root, String text) {
        if (root == null) {
            return root;
        }
        getPart(root.left, text);
        if (root.element.getPartNo().compareTo( text ) == 0) {
            part = root.element;
            return root;
        }
        getPart( root.right, text);
        return root;
    }

    public static void readingFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<String>(  );
        String st;
        while ((st = br.readLine()) != null) {
            lines.add(st);
        }
        Collections.shuffle( lines );
        setParticipants(lines);
    }

    private static void setParticipants(ArrayList<String> lines) {
        tempPart = new BST<>(  );
        for(String l : lines){
            if(l != null && l.compareTo( "" ) != 0) {
                tempPart.add( new Participant( l ) );
            }
        }
    }
}
