package Package.Screen;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import Package.Demo;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;


public class ChatViaSocket {

    public static String textReceived;
    public static StyledDocument doc;
    public static SimpleAttributeSet left;
    public static SimpleAttributeSet right;
    public static float yIndex= (float) (0.03*ChatScreen.screenHeight);
    public static float gap= (float)(0.05*ChatScreen.screenHeight);
    public static int y = -30;

    public static void chat(Socket socket,JPanel p1, JTextPane chatRoom){

        doc = chatRoom.getStyledDocument();

        left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontFamily(left, "SansSerif");
        StyleConstants.setFontSize(left, 14);
        StyleConstants.setForeground(left, Color.RED);

        right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontFamily(right, "SansSerif");
        StyleConstants.setFontSize(right, 14);
        StyleConstants.setForeground(right, Color.BLUE);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("connected");
            }
        }).on("messageReceive", new Emitter.Listener() {
            @Override
            public void call(Object... arg) {
                System.out.println("hello");
                JSONObject ob = (JSONObject) arg[0];
                try {
                    System.out.println(ob.getString("hi"));
                    textReceived = ob.getString("hi");
                    displayReceivedText(textReceived,p1,chatRoom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //socket.emit("afterNews", "message");
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("disconnected");
            }
        });
    }

    private static void displayReceivedText(String textReceived,JPanel p1,JTextPane chatRoom) {
        /*if(last==1)
        {
            JLabel l
        }*/
        //chatRoom.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //chatRoom.append(textReceived+"\n\r");
        //chatRoom.setCaretPosition(chatRoom.getText().length()-1);

        try {
            doc.insertString(doc.getLength(),"\n"+textReceived, left );
            doc.setParagraphAttributes(doc.getLength(), 1, left, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }


        /*JLabel mesg = new JLabel(textReceived);
        Dimension d=mesg.getPreferredSize();
        mesg.setBounds((int)(0.05*ChatScreen.screenWidth),(int)yIndex,d.width,d.height);
        p1.add(mesg);
*/

    }


    public static void btnClick(Socket socket, String message,JPanel p1, JPanel p, JTextPane chatRoom){

        socket.emit("messageSent",message);
        //chatRoom.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        //chatRoom.append(message+"\n\r");
        //chatRoom.setCaretPosition(chatRoom.getText().length());

        try {
            doc.insertString(doc.getLength(), "\n"+ message, right );
            doc.setParagraphAttributes(doc.getLength(), 1, right, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }





        /*JLabel mesg = new JLabel(message);
        Dimension d=mesg.getPreferredSize();
        System.out.println(d.width);
        System.out.println(d.height);
        System.out.println(ChatScreen.screenWidth);
        System.out.println(yIndex);
        int g = (int)(0.525*ChatScreen.screenWidth);
        System.out.println(g);
        System.out.println("gaping");
        System.out.println(y);
        mesg.setHorizontalAlignment(SwingConstants.RIGHT);
        mesg.setHorizontalTextPosition(SwingConstants.RIGHT);
        mesg.setBounds(250,250,d.width*10,d.height*10);
        y = y+(int)gap;
        System.out.println(mesg);
        p1.add(mesg);
        p1.revalidate();
        p1.repaint();
        p1.setVisible(true);
        p.revalidate();
        p.repaint();
        p.setVisible(true);*/
    }
}