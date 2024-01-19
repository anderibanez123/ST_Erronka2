package com.example.st_jokoa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GaldetegiaST.db";
    private static final int DATABASE_VERSION = 1;


    //taula "erabiltzaileak"
    private static final String TABLE_USERS = "erabiltzaileak";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "izena";
    private static final String COLUMN_NAME2 = "abizena";
    private static final String COLUMN_PASSWORD = "pasahitza";
    private static final String COLUMN_NAN = "nan";


    //taula "galderak"
    private static final String COLUMN_ID2 = "id";
    private static final String TABLE_GALDERAK = "galderak";
    private static final String COLUMN_GALDERA = "galdera";
    private static final String COLUMN_ERANTZUN_ZUZENA = "erantzunZuzena";
    private static final String COLUMN_ERANTZUN_OKERRA_1 = "erantzunOkerra1";
    private static final String COLUMN_ERANTZUN_OKERRA_2 = "erantzunOkerra2";
    private static final String COLUMN_ERANTZUN_OKERRA_3 = "erantzunOkerra3";


    //taula "txapelketa"
    public static final String TABLE_TXAPELKETA = "txapelketa";
    public static final String COLUMN_ID3 = "id";
    public static final String COLUMN_IZENA = "izena";
    public static final String COLUMN_ABIZENA = "abizena";
    public static final String COLUMN_DENBORA = "denbora";
    public static final String COLUMN_NAN2 = "nan";
    public static final String COLUMN_PUNTUAKETA = "puntuaketa";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NAME2 + " TEXT, " +
                    COLUMN_NAN + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_GALDERAK =
            "CREATE TABLE " + TABLE_GALDERAK + " (" +
                    COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GALDERA + " TEXT, " +
                    COLUMN_ERANTZUN_ZUZENA + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_1 + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_2 + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_3 + " TEXT);";

    private static final String CREATE_TABLE_TXAPELKETA =
            "CREATE TABLE " + TABLE_TXAPELKETA + " (" +
                    COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IZENA + " TEXT, " +
                    COLUMN_ABIZENA + " TEXT, " +
                    COLUMN_NAN2 + " TEXT, " +
                    COLUMN_PUNTUAKETA + " INTEGER, " +
                    COLUMN_DENBORA + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_GALDERAK);
        db.execSQL(CREATE_TABLE_TXAPELKETA);

        galderakSortu(db);
    }

    private void galderakSortu(SQLiteDatabase db) {
        // Insertar preguntas de ejemplo
        insertQuestion(db, "Ordenagailuko teklatuari begiratzen badiozu, ze letra aurkitzen da E eta T letren artean?", "R", "D", "F", "U");
        insertQuestion(db, "Zein da munduko kirolik ezagunena?", "Futbola", "Saskibaloia", "Boleibola", "Surfa");
        insertQuestion(db, "Kolore horia eta kolore urdina nahasten badituzu, zein kolore lortuko duzu?", "Berdea", "Gorria", "Laranja", "Morea");
        insertQuestion(db, "Munduko zein hiritan aurkitzen da Eiffel dorrea?", "Paris", "Erroma", "Iruñea", "Pisa");
        insertQuestion(db, "Haragia jaten duten animaliak dira...", "Haragijaleak", "Orojaleak", "Belarjaleak", "Landarejaleak");
        insertQuestion(db, "Zein hilabetek izan ditzake 28 edo 29 egun urtearen arabera?", "Otsaila", "Martxoa", "Urtarrila", "Apirila");
        insertQuestion(db, "Zenbat minutu ditu ordu batek?", "60", "100", "50", "600");
        insertQuestion(db, "Zenbat silaba ditu \"ordenagailua\" hitzak?", "6", "4", "5", "7");
        insertQuestion(db, "Lasterketa batean bigarren doanari aurreratzen badiot, ze posizioan geratuko naiz?", "Bigarrena", "Lehenengoa", "Hirugarrena", "Laugarrena");
        insertQuestion(db, "Nola esaten da \"osteguna\" ingelesez?", "Thursday", "Wednesday", "Tuesday", "Monday");
        insertQuestion(db, "Zenbat alde ditu hexagono batek?", "6", "4", "7", "5");
        insertQuestion(db, "Zein da giza gorputzeko hezurrik luzeena?", "Femurra (hanka)", "Humeroa (besoa)", "Buruhezurra (burua)", "Saihetsak (enborra)");
        insertQuestion(db, "Zein da Iruñeatik pasatzen den ibaia?", "Arga", "Urumea", "Irati", "Aragoi");
        insertQuestion(db, "Nola deitzen da pertsona itsuek erabiltzen duten idazteko eta irakurtzeko metodoa?", "Braille", "Marrazkiak", "Zeinuak", "Trise");
        insertQuestion(db, "Gorputzeko zein atal du neurri berdina jaiotzerakoan eta helduak garenean?", "Begiak", "Hortzak", "Mingaina", "Hankako bietz txikia");
        insertQuestion(db, "Mozart izan zen...", "Musikaria", "Margolaria", "Kirolaria", "Idazlea");
        insertQuestion(db, "Hurrengo hirietatik, zein du itsasoa?", "Donostia", "Iruñea", "Gasteiz", "Maule");
        insertQuestion(db, "Zein da munduko mendirik altuena?", "Everest", "Annapurna", "Hiru Errege Mahaia", "Jaizkibel");
        insertQuestion(db, "Zer da niretzako nire aitaren anaiaren semea?", "Lehengusua", "Osaba", "Anaia", "Iloba");
        insertQuestion(db, "Zein urtaroetan dira egunak gauak baino luzeagoak?", "Uda", "Negua", "Udazkena", "Udaberria");
        insertQuestion(db, "Nondik ateratzen da eguzkia?", "Ekialdea", "Iparraldea", "Hegoaldea", "Mendebaldea");
        insertQuestion(db, "Zein da ozeanorik handiena?", "Barea", "Artikoa", "Atlantikoa", "Indikoa");
        insertQuestion(db, "Zein da munduko animaliarik azkarrena?", "Gepardoa", "Barraskiloa", "Tigrea", "Azeria");
        insertQuestion(db, "Pablo Picasso izan zen...", "Margolaria", "Musikaria", "Arkitektoa", "Idazlea");
        insertQuestion(db, "Giza gorputzeko zein aparatuk laguntzen digu oxigenoa hartzen?", "Arnas-aparatua", "Digestio-aparatua", "Iraitz-aparatua", "Lokomozio-aparatua");
        insertQuestion(db, "Zergatik urte batean ditugu lau urtaro desberdin?", "Lurraren traslazio mugimenduagatik", "Ilargiaren errotazio mugimenduagatik", "Oporrengatik", "Egutegiagatik");
        insertQuestion(db, "Zein da Bizkaiko hiriburua?", "Bilbo", "Gasteiz", "Maule", "Donostia");
        insertQuestion(db, "Zein da Nafarroako mendirik altuena?", "Hiru Errege Mahaia", "Annapurna", "Ezkaba", "Izarraitz");
        insertQuestion(db, "Ze eraikuntza mota aurkituko ditugu Egiptona bidaiatzen badugu?", "Piramideak", "Gazteluak", "Tipiak", "Etxe-orratzak");
        insertQuestion(db, "HAINBAT (...?) AGERTZEN DA", "LIBURUTAN", "LIBURUETAN", "LIBURUAN", "DENAK DIRA ZUZENAK");
        insertQuestion(db, "ZEIN DA ZUZENA?", "Medikuarenetik nator", "Medikuarengatik nator", "Medikuatik nator", "Medikutik nator");
        insertQuestion(db, "(...?) EROSI DUT ETXEA", "DIRUARI ESKER", "DIRUARENGATIK ESKER", "DIRUARI ESKERRAK", "DIRUAREN ESKER");
        insertQuestion(db, "AMAIA (...?) ETORRI ZEN", "Urtarrilaren 8an", "Urtarrilak 8an", "Urtarrila 8an", "Urtarrila 8");
        insertQuestion(db, "POSPOSIZIOAK", "GALTZEKO BELDUR DAUDE", "GALTZEAREN BELDUR DIRA", "GALTZEKO BELDUR DIRA", "GALTZEAREN BELDUR DAUDE");
        insertQuestion(db, "ZEIN DA ZUZENA?", "8 EUROTAN SALDU DUT", "8 EURORENGATIK SALDU DUT", "BIAK ZUZENAK DIRA", "EZ DIRA ZUZENAK");
        insertQuestion(db, "TELEBISTA (...?) OHARTU ZEN", "IKUSTEAN", "IKUSIAN", "IKUSTERAKOAN", "DENAK DIRA ZUZENAK");
        insertQuestion(db, "MAIALENEK BERE URTEBETETZE FESTARA", "JOATEKO ESAN ZIGUN", "JOATEA ESAN ZIGUN", "BIAK ZUZENAK DIRA", "EZ DIRA ZUZENAK");
        insertQuestion(db, "ZEIN DA ZUZENA?", "ZUREGATIK", "ZUREGAITIK", "ZURE GATIK", "ZURE GAITIK");
        insertQuestion(db, "ALDE BATERA (...?) ZUEN LANAK", "UTZI", "HUTSI", "HUTZI", "UTSI");
        insertQuestion(db, "AMAK (...?) EMAT DIZKIGU", "AHOLKUAK", "AOLKUAL", "HAOLKUAK", "DENAK GAIZKI");
        insertQuestion(db, "ZABORRONTZIA...", "HUTSIK", "HUTZIK", "UTZIK", "UTSIK");
        insertQuestion(db, "HIZTEGIAN (...?) EMATEN DA ONTZAT", "AURRERAPAUSO", "AURRERAPAUSU", "AURRERA PAUSO", "AURRERA PAUSU");
        insertQuestion(db, "Zeinen kontra jolastu/ko du errealak 2020 erregearen kopa?", "Athletic", "Barça", "Getafe", "Real Madrid");
        insertQuestion(db, "Zein da Lopez Ufarteren ezizena?", "Pequeño diablo", "Pequeño topo", "Gran hormiga", "El txopo Ufarte");
        insertQuestion(db, "Zeinen kontra irabazi behar izan zuten realeko neskek erregearen kopa lortzeko?", "Atletico Madrid", "Athletic", "Getafe", "Real Madrid");
        insertQuestion(db, "Zertarako erabiltzen dira barra diagramak?", "Datuak konparatzeko", "Joerak aztertzeko eta aurreikuspenak egiteko", "Maiztasun erlatiboak adierazteko, ehuneko batekiko", "Biztanleria aztertzeko");
        insertQuestion(db, "Zein da biztanleriaren banaketa adin tarteka eta sexuaren arabera adierazten duen diagrama?", "Adin piramidea", "Barra diagrama", "Ziklograma", "Infograma");
        insertQuestion(db, "Mundu mailan, zein dira dentsitate handieneko guneak?", "Asia eta Europa", "Europa eta Amerika", "Amerika eta Asia", "Amerika eta Afrika");
        insertQuestion(db, "Nola deitzen da herriei buruz informazioa biltzen duen tresna?", "Errolda", "Adin piramidea", "Ziklograma", "Errentaren aitorpena");
        insertQuestion(db, "Zein dira biztanleria adin talde nagusiak?", "Gazteak, helduak eta adinekoak", "Haurrak, gazteak eta helduak", "Gazteak eta Helduak", "Helduak eta adinekoak");
        insertQuestion(db, "Zer da protokolo bat?", "komunikaziorako arau multzo bat", "komunikaziorako harau multzo bat", "arau bat", "albokoak ez daki");
        insertQuestion(db, "ZEIN DA OSI ARKITEKTURAREN 6. MAILA", "aurkezpen-maila", "maila-fisikoa", "aplikazio maila", "garraio maila");
        insertQuestion(db, "Zer era ez da informazioa transmititzeko", "GUZTIAK TXARTO DAUDE", "WIFI", "IZPI INFRAGORRIAK", "BLUETOOTH");
        insertQuestion(db, "Zer motatako helbidea da 255.250.250.265", "Okerra da", "B", "C", "E");
        insertQuestion(db, "informazio bat sareko ordenagailu guzti-guztiei bidaltzeko erabiltzen da", "difusio edo broadcast", "broadcast", "trama", "difusio");
        insertQuestion(db, "Zer da ISP", "Internet Service Provider", "Instituto de Salud Pública de Chile.", "Instituto Sistemas Perú.", "In-System Programming");
        insertQuestion(db, "(orain) Zu ere ikasturte amaierako bidaiara joan ............. gurasoek utziz gero.", "zaitezke", "diezaiezuke", "dakizuke", "dezakezu");
        insertQuestion(db, "(alegiazkoa) Nik zuek busti ..........", "zintzaketet", "nenkizuekeen", "zintezketet", "zenkizkidaketen");
        insertQuestion(db, "(alegiazkoa) Oinatz eta Iraitz aitonari gertura .........diru apur bat eskatzeko asmoz.", "lekizkioke", "litzaiokete", "lekiekete", "litzakete");
        insertQuestion(db, "(lehen) Ni tabernako mutilari hurrera .......... bakarrik geratzean.", "nenkiokeen", "nekiokeen", "nezakeen", "zezakedan");
        insertQuestion(db, "Non sortzen da islama?", "Arabiako basamortua", "Indonesia", "Jerusalem", "Zarauzko desertu txikian");
        insertQuestion(db, "Zein da Islamaren liburu Sakratua", "Korana", "Biblia", "Buda", "Meka");
        insertQuestion(db, "Zein da judaismoaren liburu sakratua", "Tora", "Koran", "Biblia", "Buda");
        insertQuestion(db, "Zein erlijioetan izan zen profeta Mahoma?", "Islamean", "Budismoan", "Kristautasunean", "Hinduismoan");
        insertQuestion(db, "Nola deitzen dira Islam erlijioaren eraikin sakratuak?", "Mezkitak", "Elizak", "Gazteluak", "Anfiteatroak");
        insertQuestion(db, "Nola deitzen Hinduismoaren tenpluak?", "Mandir", "Meka", "Eliza", "Gaztelua");
        insertQuestion(db, "Ze taldetako zalea zein Bin Laden?", "Arsenal", "Psg", "Madrid", "Barcelona");
        insertQuestion(db, "Nork irabazi zuen 2002ko munduko txapelketa?", "Brasil", "Alemania", "Italia", "Frantzia");
        insertQuestion(db, "Soldataren 2/3 gastatu dut eta oraindik 210€ geratzen zaizkit. Zenbateko soldata daukat?", "630", "420", "620", "600");
        insertQuestion(db, "Gela baten 2/3 kirola egiten dute eta horietatik 3/4 futbola. Zer zatiki egiten du futbola?", "1/2", "1/4", "6/12", "3/12");
        insertQuestion(db, "Zein herrialdek ditu biztanle gutxien?", "Araba", "Gipuzkoa", "Nafarroa", "Bizkaia");
        insertQuestion(db, "4 kg malluki eta 2 kg angurri erosi baditut (Mallukiak 2.5 euro/kg Angurria 6 euro/kg), zenbat ordaindu dut?", "22 €", "26 €", "20 €", "25 €");
        insertQuestion(db, "Bukatu esaera: Kalean uso, etxean…", "otso", "multzo", "atso", "mantso");
        insertQuestion(db, "Zelan deitzen da Ibarrolak margoztutako basoa?", "Omako basoa", "Ibarrola basoa", "Baso magikoa", "Koloredun basoa");
        insertQuestion(db, "Euskal kondairetako Mari pertsonaia , zein mendirekin lotzen dugu?", "Anboto", "Sollube", "Gorbeia", "Oiz");
        insertQuestion(db, "Euskararen eguna", "Abenduak 3", "Abenduak 17", "Urtarrilak 4", "Irailak 16");
        insertQuestion(db, "Zer esan nahi du IHINTZA hitzak?", "Rocío", "Espuma", "Cincha", "Brisa");
        insertQuestion(db, "Zer da morokila?", "Janaria", "Herri baten izena", "Argeliatik ekarritako tresna", "Jolasa");
        insertQuestion(db, "Non dago Euskal Herriko txapela fabrikarik garrantzitsuena?", "Balmaseda", "Castro", "Eibar", "Mungia");
        insertQuestion(db, "Zein da Bertsolaritza txapelketa irabazi duen emakume bakarra?", "Maialen Lujanbio", "Miren Amuriza", "Inaxi Etxaberri", "Oihane Muñiz");
        insertQuestion(db, "Urteko zein hilabetetan ospatzen da Durangoko Azoka?", "Abendua", "Otsaila", "Azaroa", "Iraila");
        insertQuestion(db, "Zein da Mario Nistalen ezizena?", "Ez dauka", "Mariolo", "Nistales", "Luigi");
        insertQuestion(db, "Zenbat jokalari jolasten dute futbol zelai batean?", "22", "11", "10", "20");
        insertQuestion(db, "Joko hau ze enpresak sortu du?", "ST enpresa", "TSB", "M&M", "Supercell");
        insertQuestion(db, "Hiru hauetako bat ez da besteen esanahikidea. Zein?", "Zertarako", "Zelako", "Nolako", "Zertako");
        insertQuestion(db, "Euskal Herriko zein hiriburutakoei egozten zaie harroputzak izatea?", "Bilbokoei", "Donostiakoei", "Gasteizekoei", "Azpeitikoei");
        insertQuestion(db, "Nori deitu zioten 'Lepantoko besamotza'?", "Miguel de Cervantes", "Miguel Delibes", "Quevedo", "Francisco Javier Arrutia");
        insertQuestion(db, "Zer ospatzen da Eguberri egunean?", "Jesusen jaiotza", "Jesusen piztuera", "Jesusen heriotza", "Jesusen hezkontza");
        insertQuestion(db, "Zenbat sektore ditu ekonomiak?", "3", "2", "4", "5");
        insertQuestion(db, "Zenbat denbora behar izan zuen Phileas Fogg-ek munduari bira emateko?", "80 egun", "Hilabete", "Urte erdia", "2 urte");
        insertQuestion(db, "Noiz eta non ospatu ziren lehen Joku Olinpiko modernoak?", "1896 Atenasen", "1902an Atlantan", "1892an Erroman", "1906an Munichen");
        insertQuestion(db, "Non sortu ziren lehenengo Jaurerriak?", "Nafarroan", "Bizkaian", "Araban", "Gipuzkoan");
        insertQuestion(db, "Nor izan zen lehena Hego Polora iristen?", "Roal Amundsen", "Robert Falcon Scott", "Haakon Norvegiakoa", "Mr Tartaria");
        insertQuestion(db, "Non jaio zen Marco Polo?", "Venezioan", "Sizilian", "Milanen", "Florenzian");
        insertQuestion(db, "Zein da lurreko lekurik hotzena?", "Antartida", "Alaska", "Rusia", "Everest");
        insertQuestion(db, "Zein da munduko ibairik luzeena?", "Nilo", "Amazonas", "Mississippi", "Yangtsé");
        insertQuestion(db, "Non sortu ziren Olinpiar Jokoak?", "Grecia", "Londres", "Roma", "AEB");
        insertQuestion(db, "Zenbat hezur daude giza gorputzean?", "206", "200", "203", "205");
        insertQuestion(db, "Nork margotu zuen “Azken Afaria”?", "Leonardo da Vinci", "Miguel Ángel", "Picasso", "Rafael Sanzio");
        insertQuestion(db, "50 % 100 bada, zer da % 90?", "45", "60", "40", "42");
        insertQuestion(db, "Zein herrialdetan dago Taj Mahal monumentu ospetsua?", "India", "China", "Mongolia", "Arabia Saudita");
        insertQuestion(db, "Zenbat urte ditu lustro batek?", "5", "10", "15", "7");
        insertQuestion(db, "Android sistema eragilea konpainiarena da...", "Google", "Microsoft", "Samsung", "Apple");
        insertQuestion(db, "Whatsapp konpainiarena da...", "Facebook", "Google", "Apple", "Sony");
        insertQuestion(db, "MEGABYTE BAT BALIOKIDEA DA...", "1024 kiloBytes", "1024 bits", "1024 bytes", "1000 kiloBytes");
        insertQuestion(db, "Proiektorea periferiko bat da...", "Irtera", "Sarrera", "Komunikazioa", "Biltegiratzea");
        insertQuestion(db, ".odt luzapena softwareari dagokio...", "LibreOffice Writer", "LibreOffice Impress", "Microsoft PowerPoint", "Microsoft Word");
        insertQuestion(db, "Scratch bat da...", "Programazio lengoaia", "Bideo editorea", "Web nabegatzailea", "Testu prozesadorea");
        insertQuestion(db, "Gure gailuekin ordainketak egiteko aukera ematen digun haririk gabeko teknologiari deitzen zaio...", "NFC", "infragorriak", "Bluetooth", "Wifi");
        insertQuestion(db, "Programa hauetatik zein da web-arakatzailea?", "Mozilla Firefox", "FaceBook", "Google", "Instagram");
        insertQuestion(db, "Zer da hardwarea?", "Ordenagailuaren zati fisikoa da.", "Programak dira.", "Birus mota bat da.", "Noveldako otordu tipikoa");
        insertQuestion(db, "Zertarako erabiltzen dira lasterbide-teklak ordena berean: Moztu, Kopiatu eta Itsatsi?", "Ctrl + X, Ctrl + C y Ctrl + V", "Ctrl + X, Ctrl + O y Ctrl + V", "Ctrl + X, Ctrl + C y Ctrl + P", "Ctrl + Z, Ctrl + C y Ctrl + V");
        insertQuestion(db, "Hauetatik zeintzuk dira birus motak?", "Spywareak, troiarrak eta malwareak.", "Firewalls, P2P eta Blocks.", "AVG, Norton eta Aviras", "Flotters, Spirros eta Gryps.");
        insertQuestion(db, "1 byte da...", "8 bits", "1024 bits", "6 bits", "10 bits");
        insertQuestion(db, "Zein urtetan hasi zen Bigarren Mundu Gerra?", "1939", "1945", "1927", "1935");
        insertQuestion(db, "Zenbat planeta daude gure Eguzki Sisteman?", "8", "9", "10", "7");
        insertQuestion(db, "Nola du izena Frantziako ereserki nazionalak?", "La Marsellesa", "La Brabançonne", "La Congolaise", "Gens du pays");
        insertQuestion(db, "Zein da Europan bizirik dagoen hizkuntzarik zaharrena?", "Euskara", "Gaztelania", "Rumaniarra", "Frantzesa");
        insertQuestion(db, "Zein da Austriako hiriburua?", "Viena", "Berlín", "Praga", "Zagreb");
        insertQuestion(db, "Zein urtetan gertatu ziren New Yorkeko Dorre Bikien aurkako erasoak?", "2001", "2011", "2111", "2002");
        insertQuestion(db, "Zenbat balio du Pi zenbakiak?", "3.1416", "3.1422", "2.1415", "3.1614");
        insertQuestion(db, "Nork abestu zuen \"Hey Jude\" abestia?", "The Beatles", "Queen", "Rolling stones", "Oasis");
        insertQuestion(db, "Zein urtetan erori zen Berlingo harresia?", "1989", "1987", "1978", "1998");
        insertQuestion(db, "Zein margolari susmatzen zen 1911n Mona Lisa lapurtu zuela?", "Pablo Picasso", "Henri Matisse", "Paul Cézanne", "Claude Monet");
        insertQuestion(db, "NASAren arabera, zenbat denbora behar izan zuen Apollo 11 misioak ilargira iristeko?", "Hiru egun", "Egun bat", "Aste bat", "Hiru aste");
        insertQuestion(db, "Zein da giza urdailaren lana?", "Janaria elikagaietan zatitu", "Eman intsulina gorputzari", "Iragazi hondakinak gorputzetik", "Ponpa odola gorputz osoan");
        insertQuestion(db, "Neguan Mexikora migratzen den tximeleta monarka zein prozesu mota bezala ezagutzen da?", "Egokitzapena", "Hibernazioa", "Kamuflajea", "Harrapaketa");
        insertQuestion(db, "Zein zenbaki erromatar da hau XIX", "19", "59", "21", "10110");
        insertQuestion(db, "Zein zenbaki erromatar da hau XIX", "19", "59", "21", "10110");
        insertQuestion(db, "Zein da Australiako hiriburua?", "Canberra", "Melbourne", "Sídney", "Brisbane");
        insertQuestion(db, "Neil Armstrong izan zen ilargiko lehen pertsona. NASAren arabera, nor izan zen bigarrena?", "Buzz Aldrin", "Michael Collins", "Yuri Gagarin", "Alan Shephard");

    }

    private void insertQuestion(SQLiteDatabase db, String pregunta, String respuestaCorrecta, String respuestaIncorrecta1, String respuestaIncorrecta2, String respuestaIncorrecta3) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_GALDERA, pregunta);
        values.put(COLUMN_ERANTZUN_ZUZENA, respuestaCorrecta);
        values.put(COLUMN_ERANTZUN_OKERRA_1, respuestaIncorrecta1);
        values.put(COLUMN_ERANTZUN_OKERRA_2, respuestaIncorrecta2);
        values.put(COLUMN_ERANTZUN_OKERRA_3, respuestaIncorrecta3);

        db.insert(TABLE_GALDERAK, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALDERAK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TXAPELKETA);
        onCreate(db);
    }


    @SuppressLint("Range")
    public List<Galdera> getRandomGalderak(int count) {
        List<Galdera> galderaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Random random = new Random();

        // Obtener todas las preguntas
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GALDERAK, null);

        if (cursor.moveToFirst()) {
            do {
                Galdera galdera = new Galdera();
                galdera.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                galdera.setGaldera(cursor.getString(cursor.getColumnIndex(COLUMN_GALDERA)));
                galdera.setErantzunZuzena(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_ZUZENA)));
                galdera.setErantzunOkerra1(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_1)));
                galdera.setErantzunOkerra2(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_2)));
                galdera.setErantzunOkerra3(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_3)));

                // Establecer aleatoriamente el índice de la respuesta correcta
                int correctAnswerIndex = random.nextInt(4); // 4 opciones posibles
                galdera.setCorrectAnswerIndex(correctAnswerIndex);

                galderaList.add(galdera);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Barajar aleatoriamente la lista y devolver el número especificado de preguntas
        Collections.shuffle(galderaList, random);
        return galderaList.subList(0, count);
    }


    public void updateTxapelketaTable(int puntuaketa, long denbora, String nan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PUNTUAKETA, puntuaketa);
        values.put(COLUMN_DENBORA, denbora);

        // Puedes personalizar la condición WHERE según tus necesidades
        db.update(TABLE_TXAPELKETA, values, COLUMN_NAN2 + "=?", new String[]{nan});



        db.close();
    }






    public String getUsernameColumnName() {
        return COLUMN_NAME;
    }
    public String getUsernameColumnName2() {
        return COLUMN_NAME2;
    }
    public String getColumnNan() {
        return COLUMN_NAN;
    }

    public String getPasswordColumnName() {
        return COLUMN_PASSWORD;
    }
    public String getColumnPuntuaketa(){
        return COLUMN_PUNTUAKETA;
    }
    public String getTableName() {
        return TABLE_USERS;
    }
    public String getTableName2() {
        return TABLE_TXAPELKETA;
    }
    public String getColumnGaldera(int i){
        return COLUMN_GALDERA;
    }
    public String getColumnErantzunZuzena(int i){
        return COLUMN_ERANTZUN_ZUZENA;
    }
    public String getColumnErantzunOkerra1(int i){
        return COLUMN_ERANTZUN_OKERRA_1;
    }
    public String getColumnErantzunOkerra2(int i){
        return COLUMN_ERANTZUN_OKERRA_2;
    }
    public String getColumnErantzunOkerra3(int i){
        return COLUMN_ERANTZUN_OKERRA_3;
    }

    public int getQuestionCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_GALDERAK, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }
}



