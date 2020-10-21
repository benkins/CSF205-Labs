import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

class Card {
  public String name, title, email, phone, logo;

  public Card(String name, String title, String email,
              String phone, String logo) {
    this.name = name;
    this.title = title;
    this.email = email;
    this.phone = phone;
    this.logo = logo;
  }
}

public class BCedit extends Frame implements ActionListener {
  // declare the user interface
  Button ok = new Button("ok");
  Button delete = new Button("delete");
  Button clear = new Button("new");
  Button save = new Button("save");
  Button quit = new Button("quit");
  TextField name = new TextField(20);
  TextField title = new TextField(20);
  TextField email = new TextField(20);
  TextField phone = new TextField(20);
  TextField logo = new TextField(20);
  Panel cardpanel = new Panel(new GridLayout(0, 1));

  String cardfile;
  Vector cardvector;
  int current = -1;


  public static void main(String[] args) { new BCedit(args[0]); }
  //TODO: fix the code with the missing JDOM API's
  //All XXX should be replaced with the appropriate API's
  Vector doc2vector(Document d) {
    Vector v = new Vector();
    Iterator i = d.getRootElement().XXX("card").iterator();
    while (i.hasNext()) {
      Element e = (Element)i.next();
      String phone = e.XXX("phone");
      if (phone==null)
        phone="";
      Element logo = e.XXX("logo");
      String uri;
      if (logo==null)
        uri = "";
      else
        uri = logo.XXX("uri");
      Card c = new Card(e.XXX("name"),
                        e.XXX("title"),
                        e.XXX("email"),
                        phone,
                        uri);
      v.add(c);
    }
    return v;
  }

  Document vector2doc() {
    //TODO: fix the code with the missing JDOM API's
    //All XXX should be replaced with the appropriate API's
    Element cardlist = new Element("cardlist");
    for (int i=0; i<cardvector.size(); i++) {
      Card c = (Card)cardvector.elementAt(i);
      if (c!=null) {
        Element card = new Element("card");
        Element name = new Element("name");
        name.XXX(c.name);
        card.addContent(name);
        Element title = new Element("title");
        title.XXX(c.title);
        card.addContent(title);
        Element email = new Element("email");
        email.XXX(c.email);
        card.addContent(email);
        if (!c.phone.equals("")) {
          Element phone = new Element("phone");
          phone.XXX(c.phone);
          card.addContent(phone);
        }
        if (!c.logo.equals("")) {
          Element logo = new Element("logo");
          logo.XXX("uri", c.logo);
          card.addContent(logo);
        }
        cardlist.addContent(card);
      }
    }
    return new Document(cardlist);
  }

  void addCards() {
    cardpanel.removeAll();
    for (int i=0; i<cardvector.size(); i++) {
      Card c = (Card)cardvector.elementAt(i);
      if (c!=null) {
        Button b = new Button(c.name);
        b.setActionCommand(String.valueOf(i));
        b.addActionListener(this);
        cardpanel.add(b);
      }
    }
    pack();
  }

  public BCedit(String cardfile) {
    super("BCedit");
    this.cardfile = cardfile;
    try {
      SAXBuilder b = new SAXBuilder();
      Document d = b.build(new File(cardfile));
      cardvector = doc2vector(d);
    } catch (Exception e) {
      System.err.println(e);
      System.exit(-1);
    }
    // initialize the user interface
    setLayout(new BorderLayout());
    ScrollPane s = new ScrollPane();
    s.setSize(200, 0);
    s.add(cardpanel);
    add(s,BorderLayout.WEST);
    Panel l = new Panel(new GridLayout(5, 1));
    l.add(new Label("Name"));
    l.add(new Label("Title"));
    l.add(new Label("Email"));
    l.add(new Label("Phone"));
    l.add(new Label("Logo"));
    add(l,BorderLayout.CENTER);
    Panel f = new Panel(new GridLayout(5, 1));
    f.add(name);
    f.add(title);
    f.add(email);
    f.add(phone);
    f.add(logo);
    add(f,BorderLayout.EAST);
    Panel p = new Panel();
    ok.addActionListener(this);
    p.add(ok);
    delete.addActionListener(this);
    p.add(delete);
    clear.addActionListener(this);
    p.add(clear);
    save.addActionListener(this);
    p.add(save);
    quit.addActionListener(this);
    p.add(quit);
    add(p,BorderLayout.SOUTH);
    addCards();
    show();
  }

  public void actionPerformed(ActionEvent event) {
     Card c;
     String command = event.getActionCommand();
     if (command.equals("ok")) {
       c = new Card(name.getText(),
                    title.getText(),
                    email.getText(),
                    phone.getText(),
                    logo.getText());
       if (current==-1)
         cardvector.add(c);
       else
         cardvector.setElementAt(c, current);
       addCards();
     } else if (command.equals("delete")) {
        if (current!=-1) {
          cardvector.setElementAt(null, current);
          current = -1;
          addCards();
        }
     } else if (command.equals("new")) {
        current = -1;
        name.setText("");
        title.setText("");
        email.setText("");
        phone.setText("");
        logo.setText("");
     } else if (command.equals("save")) {
       //TODO: update the code to save the new Document
       //from vector2doc to the cardlist.xml
     } else if (command.equals("quit")) {
        System.exit(0);
     } else {
        current = Integer.parseInt(command);
        c = (Card)cardvector.elementAt(current);
        name.setText(c.name);
        title.setText(c.title);
        email.setText(c.email);
        phone.setText(c.phone);
        logo.setText(c.logo);
     }
  }
}
