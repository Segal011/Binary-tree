import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import java.awt.*;

public class SysOut {

    private static int lineNr;
    private static boolean formatStartOfLine = true;

    public static void setFormatStartOfLine(boolean formatStartOfLine) {

        SysOut.formatStartOfLine = formatStartOfLine;
    }

    private static String getStartOfLine() {

        return (formatStartOfLine) ? ++lineNr + "| ".intern() : "".intern();
    }

    public  static void oun(TextArea text, Object o){
        StringBuilder sb = new StringBuilder();
        if(o instanceof Iterable){
            for (Object ob : (Iterable) o){
                sb.append( ob.toString()).append( System.lineSeparator() );
            }
            sb.append(System.lineSeparator());
        }
        else{
            sb.append(o.toString()).append(System.lineSeparator());
        }
        Platform.runLater( () -> text.appendText(sb.toString()) );
    }

    public  static void oun(TextArea text, String o){
        Platform.runLater( () -> text.appendText(o + System.lineSeparator()) );
    }

    public static void oun(TextArea ta, Object o, String msg) {
        String startOfLine = getStartOfLine();
        Platform.runLater(() -> ta.appendText(startOfLine + msg + ": ".intern() + System.lineSeparator()));
        oun(ta, o);
    }




}
