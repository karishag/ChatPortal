package Package.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import io.socket.emitter.Emitter;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;



public class ChatScreen extends Screen implements ComponentListener {

    public static JButton send;
    public static JTextArea text;
    public static JTextPane chatRoom;
    public static JLabel name;
    public static int length;
    public static JLabel line;
    public static JPanel p1;
    public static JPanel p2;
    public static JSplitPane s1;
    public static float screenWidth;
    public static float screenHeight;
    public static JScrollPane scrollableTextArea;

    public void responsiveChatLayout(JPanel p)
    {
        p.removeAll();
        p.revalidate();
        p.repaint();
        p.setLayout(null);
        screenWidth = p.getWidth();
        screenHeight = p.getHeight();
        System.out.println(screenHeight);
        System.out.println(screenWidth);
        /*name.setLocation((int) (0.1 *screenWidth), (int) (0.05 * screenHeight));
        name.setSize(name.getPreferredSize());
        name.validate();
        */name.setBounds((int) (0.05*screenWidth), (int) (0.03*screenHeight),length,50);

        p.add(name);
        /*JSeparator sep = new JSeparator();
        sep.setBounds(0,(int)(0.1*screenHeight),(int)screenWidth,(int)screenHeight);
        sep.setOrientation(1);
        p.add(sep);*/

        p1.setBounds(0, (int)(0.175*screenHeight), (int) screenWidth, (int) (0.625*screenHeight));
        p1.setBackground(Color.white);
        p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /*p1.setBounds(0, (int)(0.175*screenHeight), (int)( 0.30*screenWidth), (int) (0.625*screenHeight));
        p1.setBackground(Color.white);
        p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        p2.setBounds((int)( 0.30*screenWidth), (int)(0.175*screenHeight), (int)( 0.70*screenWidth),(int) (0.625*screenHeight));
        p2.setBackground(Color.white);
        p2.setBorder(BorderFactory.createLineBorder(Color.BLACK));*/

        text.requestFocus();
        text.setBounds((int) (0.22*screenWidth),(int)(screenHeight-0.2*screenHeight), (int) (0.5*screenWidth), (int)(0.2*screenHeight));
        p.add(text);

        send.setBounds((int)(0.74*screenWidth), (int)(0.8625*screenHeight),90,30);
        p.add(scrollableTextArea);
        p.add(send);
        p.add(p1);
        //p.add(p2);
        //p.add(s1);

    }
    public ChatScreen(JPanel p) throws MalformedURLException, URISyntaxException {

        super(p);
        p.addComponentListener(this);
        p.setLayout(new BorderLayout());
        //p.add(p1);
        length=(LoginScreen.name).length() * 40;
        name = new JLabel(LoginScreen.name);
        line = new JLabel("<html><hr></html>");
        name.setFont(new Font("Serif", Font.BOLD, 20));
        //chatRoom = new JTextPane("Welcome to the Chat room\n", 80, 80);

        chatRoom = new JTextPane();


        p1 = new JPanel(new GridLayout(1,1));
        p1.add(new JScrollPane(chatRoom));
        chatRoom.setEditable(false);
        //p1.setLayout(new BorderLayout ());

        //JScrollPane scrollPane = new JScrollPane();
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //p1.add(scrollPane);

        text = new JTextArea(2,25);
        text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(new Font("Helvetica",Font.PLAIN,15));
        scrollableTextArea = new JScrollPane(text);

        //scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //2scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        send = new JButton("Send");
        send.setSize(8,3);

        //p1 = new JPanel();
        p2 = new JPanel();
        p.add(p1, BorderLayout.WEST);
        p.add(p2, BorderLayout.EAST);
/*
        // create text areas
        JTextArea t1 = new JTextArea(10, 10);
        JTextArea t2 = new JTextArea(10, 10);

        // set texts
        t1.setText("this is first text area");
        t2.setText("this is second text area");

        // add text area to panel
        p1.add(t1);
        p2.add(t2);

        // create a splitpane
        s1 = new JSplitPane(SwingConstants.VERTICAL, p1, p2);

        // set Orientation for slider
        s1.setOrientation(SwingConstants.VERTICAL);
        //p.add(s1);*/

        responsiveChatLayout(p);


        System.out.println("Chat");
        IO.Options options = new IO.Options();
        options.forceNew = true;
        final OkHttpClient client = new OkHttpClient();
        options.webSocketFactory = client;
        options.callFactory = client;
        final Socket socket = IO.socket("http://localhost:3000", options);


        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(text.getText().equals("")){
                    send.setEnabled(false);
                    JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue >Empty message cannot be sent</FONT></HTML>");
                    JOptionPane.showMessageDialog(null,errorFields);
                    send.setEnabled(true);
                }
                else {
                    ChatViaSocket.btnClick(socket,text.getText(),p1,p, chatRoom);
                }
            }
        });

        ChatViaSocket.chat(socket,p1, chatRoom);
        socket.connect();

    }

    @Override
    public void componentResized(ComponentEvent e) {
        responsiveChatLayout((JPanel) e.getComponent());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}

