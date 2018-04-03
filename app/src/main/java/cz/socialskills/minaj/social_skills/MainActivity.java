package cz.socialskills.minaj.social_skills;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    int devWidth;
    int devHeight;

    ImageView iv;
    TextView tv;
    ImageButton ib1;
    ImageButton ib2;
    ImageButton ib3;
    ImageButton ib4;
    ListView lv;
    ListView lv2;

    int chosen_menu;
    String menuclick;
    Slideshow slides;
    File file;
    File dir;
    List<String> exScenes;
    String saveText;
    ListAdapter fmenu_a;
    //String[] fmenu;
    ListAdapter smenu_a;
    String[] smenu;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SocialSkills";

    //Thread t1 = new MyThread();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //RelativeLayout layout =(RelativeLayout)findViewById(R.id.imageView2);
        //getWindow().setBackgroundDrawableResource(R.drawable.one);
        setContentView(cz.socialskills.minaj.social_skills.R.layout.activity_main);
        //Display display = getWindowManager().getDefaultDisplay().getWidth();

        devWidth = getWindowManager().getDefaultDisplay().getWidth();            // Šířka displeje
        devHeight = getWindowManager().getDefaultDisplay().getHeight();          // Výška displeje

        iv = (ImageView) findViewById(cz.socialskills.minaj.social_skills.R.id.imageView2);         // Obrázek pozadí
        tv = (TextView) findViewById(cz.socialskills.minaj.social_skills.R.id.textView);            // Text pro scénu
        ib1 = (ImageButton) findViewById(cz.socialskills.minaj.social_skills.R.id.imageButton);     // Tlačítko další obrázek
        ib2 = (ImageButton) findViewById(cz.socialskills.minaj.social_skills.R.id.imageButton2);    // Tlačítko přechozí obrázek
        ib3 = (ImageButton) findViewById(cz.socialskills.minaj.social_skills.R.id.imageButton3);    // Tlačítko ukončit scénu
        ib4 = (ImageButton) findViewById(cz.socialskills.minaj.social_skills.R.id.imageButton4);    // Tlačítko zpět
        lv = (ListView) findViewById(cz.socialskills.minaj.social_skills.R.id.menuListView);        // Menu
        //lv2 = (ListView) findViewById(R.id.menuListView2);      // Menu 2

        dir = new File(path);                                   // Složka pro aplikaci
        exScenes = new ArrayList<String>();
        dir.mkdirs();
        chosen_menu = -1;

        //createBasicScenes();

        // VYTVORENI SCENY

//        Slide prvni = new Slide(R.drawable.prvni, "Nákup v obchodě");
//        Slide druha = new Slide(R.drawable.druha, "Jdu si nakoupit do obchodu");
//        Slide treti = new Slide(R.drawable.treti, "Osoba za pultem je prodavačka, slušně ji pozdravím");
//        Slide ctvrta = new Slide(R.drawable.ctvrta, "Vyberu si zboží, které si chci koupit");
//        Slide pata = new Slide(R.drawable.pata, "Vezmu si zboží s sebou a přinesu ke kase");
//        Slide sesta = new Slide(R.drawable.sesta, "Počkám, než mi zboží prodavačka namarkuje");
//        Slide sedma = new Slide(R.drawable.sedma, "Zaplatím, poděkuji a rozloučím se");
//        Slide osma = new Slide(R.drawable.osma, "Odcházím pryč z obchodu");
//        Slide[] asd = {prvni, druha, treti, ctvrta, pata, sesta, sedma, osma};
//        slides = new Slideshow(asd);

        getExternalScenes();

        // MENU
        setMenu(-1);
        //fmenu = new String[]{"Hlavní scény", "Stažené scény", "Analýza", "O aplikaci"};
        //String[] smenu = {"","","",""};
        //menu_a = new ArrayAdapter<String>(this, R.layout.centerviewlist, fmenu);


        //ListAdapter smenu_a = new ArrayAdapter<String>(this, R.layout.centerviewlist, smenu);
//        ListView menuListView = (ListView) findViewById(R.id.menuListView);
        lv.setAdapter(fmenu_a);
        //iv.setImageResource(R.drawable.prvni);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        menuclick = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(MainActivity.this, Integer.toString(chosen_menu), Toast.LENGTH_SHORT).show();
                        //chosen_menu = position;
                        if (chosen_menu >= 0){
                            startScene(position);
                            //Toast.makeText(MainActivity.this, Integer.toString(chosen_menu), Toast.LENGTH_SHORT).show();
                        } else {
                            ib4.setVisibility(View.VISIBLE);
                            chosen_menu = position;
                            setMenu(position);
                        }


//                                if(position == 1){
//                            //Toast.makeText(MainActivity.this, path, Toast.LENGTH_LONG).show();
//
//                            saveFile();
//                            //saveImage();
//                            loadImage();
//                            Toast.makeText(MainActivity.this, "Asi saved?", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            startScene();
//                        }
                    }


                }
        );

//        File dir = new File(path);
//        dir.mkdirs();



//        ImageView iv = (ImageView) findViewById(R.id.imageView2);
//        TextView tv = (TextView) findViewById(R.id.textView);
//        iv.setImageResource(prvni.getImg());
//        tv.setText(prvni.getText());


    }

    private void getExternalScenes() {
        File[] files = dir.listFiles();
        exScenes.clear();
        for (File file : files){
            exScenes.add(file.getName());
        }
    }

    private void setMenu(int i) {
        switch(i) {
            case -1: //Hlavní menu
                fmenu_a = new ArrayAdapter<String>(MainActivity.this, cz.socialskills.minaj.social_skills.R.layout.centerviewlist, new String[]{"Hlavní scény", "Stažené scény", "Analýza", "O aplikaci"});
                lv.setAdapter(fmenu_a);
                //Toast.makeText(MainActivity.this , exScenes.get(0).toString() , Toast.LENGTH_SHORT).show();
                break;
            case 0: // Hlavní scény
                fmenu_a = new ArrayAdapter<String>(MainActivity.this, cz.socialskills.minaj.social_skills.R.layout.centerviewlist, assetlist(""));
                lv.setAdapter(fmenu_a);
                break;
            case 1: // Stažené scény
                fmenu_a = new ArrayAdapter<String>(MainActivity.this, cz.socialskills.minaj.social_skills.R.layout.centerviewlist, exScenes);
                lv.setAdapter(fmenu_a);
                break;
            case 2: // Analýza
                fmenu_a = new ArrayAdapter<String>(MainActivity.this, cz.socialskills.minaj.social_skills.R.layout.centerviewlist, new String[]{"3 První", "3 Druhá", "3 Třetí", "3 Čtvrtá"});
                lv.setAdapter(fmenu_a);
                //Toast.makeText(MainActivity.this, Integer.toString(devHeight), Toast.LENGTH_SHORT).show();
                break;
            case 3: // O aplikaci
                fmenu_a = new ArrayAdapter<String>(MainActivity.this, cz.socialskills.minaj.social_skills.R.layout.centerviewlist, new String[]{"4 První", "4 Druhá", "4 Třetí", "4 Čtvrtá"});
                lv.setAdapter(fmenu_a);
                break;
        }
    }

    private String[] assetlist(String folder) {
        String[] result = null;
        try {
            result = this.getResources().getAssets().list(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void loadImage() {
    }

    private void saveImage() {
        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        String fileName = String.valueOf(iv.getTag())+".jpg";
        file = new File(path, fileName);
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveFile() {
        file = new File(path+"/"+menuclick+".txt");
        saveText = menuclick+" asd a s d";
        //Toast.makeText(MainActivity.this, file.toString(), Toast.LENGTH_LONG).show();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(saveText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void startScene(int pos) {
        lv.setVisibility(View.INVISIBLE);
        ib2.setVisibility(View.INVISIBLE);
        ib1.setVisibility(View.VISIBLE);
        ib3.setVisibility(View.VISIBLE);
        ib4.setVisibility(View.INVISIBLE);
        ib1.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.rightarrow);
        //Toast.makeText(MainActivity.this, menuclick, Toast.LENGTH_SHORT).show();
        slides = null;
        if (chosen_menu == 0){
            slides = createScene(pos);
        } else if (chosen_menu == 1){
            slides = createExtScene(pos);
        }

        if (slides == null){
            Toast.makeText(MainActivity.this, "Chyba", Toast.LENGTH_SHORT).show();
            exitImg(null);
        } else {
            showImg(0);
        }
    }


    private Slideshow createExtScene(int pos) {
        String folder_path = path + "/" + exScenes.get(pos) + "/";
        File jpg_file = new File(folder_path + "pics");
        File txt_file= new File(folder_path + "text.txt");

        //List<String> bmp_name = new ArrayList<String>();
        //List<Bitmap> bmp = new ArrayList<Bitmap>();
        List<Slide> scene = new ArrayList<Slide>();

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScreenDensity = 1;
        //options.inSampleSize = 2;

        if(jpg_file.exists() && txt_file.exists()){
            File[] images = jpg_file.listFiles();
            String[] text = loadText(txt_file);
            if (images.length == text.length){
                int i = 0;
                for (File image : images){
                    //Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()), devWidth, devHeight, false);

                    //Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
                    //Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                    //Bitmap bmp = createBitmap(image.getAbsolutePath());

                    scene.add(new Slide(image.getAbsolutePath(), text[i++]));
                }
            } else { return null; }
        } else { return null; }

//        if (jpg_file.exists()){
//            File[] files = jpg_file.listFiles();
//
//            for (File file : files){
//                bmp_name.add(file.getName());
//            }
//            for (int i = 0; i< bmp_name.size(); i++){
//                File temp = new File(folder_path + "pics/" + bmp_name.get(i));
//                bmp.add(BitmapFactory.decodeFile(temp.getAbsolutePath()));
//            }
//        } else { return null; }
//
//        //File txt_file= new File(folder_path + "text.txt");
//        if (txt_file.exists()){
//            String[] text = loadText(txt_file);
//
//            for (int i = 0; i < text.length; i++){
//                Slide temp = new Slide(bmp.get(i), text[i]);
//                scene.add(temp);
//            }
//        } else { return null; }

        return new Slideshow(scene.toArray(new Slide[scene.size()-1]));

    }

    private Bitmap createBitmap(String path) {
        //Bitmap asd = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //asd = BitmapFactory.decodeFile(path, options);
        //options.inSampleSize = 3;
        Log.i("MyActivity", String.valueOf(options.inBitmap));
        int h = options.outHeight;
        int w = options.outWidth;
        int x = 1;
        int fDiff = (h - devHeight) + (w - devWidth);
        int nDiff = fDiff;

        if (nDiff > 0){
            while (nDiff > 0){
                fDiff = nDiff;
                x = x*2;
                nDiff = (h/x - devHeight) + (w/x - devWidth);
            }
            if (fDiff > nDiff*(-1)){
                options.inSampleSize = x;
                //Toast.makeText(MainActivity.this, Integer.toString(fDiff)+"<"+Integer.toString(nDiff*(-1))+"="+Integer.toString(x), Toast.LENGTH_SHORT).show();
            } else {
                options.inSampleSize = (x-1);
                //Toast.makeText(MainActivity.this, Integer.toString(fDiff)+"<"+Integer.toString(nDiff*(-1))+"="+Integer.toString(x), Toast.LENGTH_SHORT).show();
            }
        }

        options.inJustDecodeBounds = false;
        //options.inSampleSize = 3;
        Log.i("MyActivity", "asd: "+"byteCount="+Integer.toString(options.inBitmap.getByteCount()));
        Bitmap temp = BitmapFactory.decodeFile(path, options);
        Log.i("MyActivity", "temp: "+Integer.toString(temp.getWidth())+"x"+Integer.toString(temp.getHeight())+", byteCount="+Integer.toString(temp.getByteCount()));
        Bitmap bmp = Bitmap.createScaledBitmap(temp, devWidth, devHeight, false);
        Log.i("MyActivity", "bmp: "+Integer.toString(bmp.getWidth())+"x"+Integer.toString(bmp.getHeight())+", byteCount="+Integer.toString(bmp.getByteCount()));
        //Toast.makeText(MainActivity.this, Integer.toString(bmp.getWidth())+"x"+Integer.toString(bmp.getHeight())+", byteCount="+Integer.toString(bmp.getByteCount()), Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, Integer.toString(x), Toast.LENGTH_SHORT).show();

        return bmp;
    }

    public static String[] loadText(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

    private Slideshow createScene(int pos) {

        List<Slide> scene = new ArrayList<Slide>();
        String folder_name = null;
        int pic_count = 0;
        switch(pos){
            case 0:
                folder_name = "Nákup v obchodě";
                pic_count = 8;
                break;
            case 1:
                folder_name = "Nákup v obchodě";
                pic_count = 6;
                break;
            case 2:
                folder_name = "Nákup v obchodě";
                pic_count = 4;
                break;
            case 3:
                folder_name = "Nákup v obchodě";
                pic_count = 3;
                break;
        }
        List<Bitmap> bmp_list = new ArrayList<Bitmap>();


        InputStream is = null;
        for (int i = 0; i < pic_count; i++){
            try {
                //is = this.getResources().getAssets().open(folder_name+"/" + Integer.toString(i) + ".jpg"); //ahoj/0.jpg
                is = this.getResources().getAssets().open(folder_name+"/pics/" + Integer.toString(i+1) + ".jpg");
                //Toast.makeText(MainActivity.this, folder_name+"/" + Integer.toString(i) + ".jpg", Toast.LENGTH_SHORT).show();
            } catch (IOException e) { ; }

            Bitmap bmp = BitmapFactory.decodeStream(is);
            bmp_list.add(bmp);
        }

        //String[] text = {"Nákup v obchodě", "Jdu si nakoupit do obchodu", "Osoba za pultem je prodavačka, slušně ji pozdravím", "Vyberu si zboží, které si chci koupit", "Vezmu si zboží s sebou a přinesu ke kase", "Počkám, než mi zboží prodavačka namarkuje", "Zaplatím, poděkuji a rozloučím se" "Odcházím pryč z obchodu"};
        String[] text = {"Lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit"};
        for (int i =0; i < pic_count; i++){
            Slide temp = new Slide(bmp_list.get(i), text[i]);
            scene.add(temp);
        }



        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.prvni);
//        Slide prvni = new Slide(R.drawable.prvni, "Nákup v obchodě");
//        Slide druha = new Slide(R.drawable.druha, "Jdu si nakoupit do obchodu");
//        Slide treti = new Slide(R.drawable.treti, "Osoba za pultem je prodavačka, slušně ji pozdravím");
//        Slide ctvrta = new Slide(R.drawable.ctvrta, "Vyberu si zboží, které si chci koupit");
//        Slide pata = new Slide(R.drawable.pata, "Vezmu si zboží s sebou a přinesu ke kase");
//        Slide sesta = new Slide(R.drawable.sesta, "Počkám, než mi zboží prodavačka namarkuje");
//        Slide sedma = new Slide(R.drawable.sedma, "Zaplatím, poděkuji a rozloučím se");
//        Slide osma = new Slide(R.drawable.osma, "Odcházím pryč z obchodu");

//        Slide[] asd = {prvni, druha, treti, ctvrta, pata, sesta, sedma, osma};

//        return new Slideshow(asd);
        return new Slideshow(scene.toArray(new Slide[scene.size()-1]));
    }

    private void showMenu() {
        lv.setVisibility(View.VISIBLE);
        ib3.setVisibility(View.INVISIBLE);
        ib4.setVisibility(View.INVISIBLE);
        iv.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.socialskills);
        chosen_menu = -1;
    }
//
//    private void hideMenu() {
//        lv.setVisibility(View.INVISIBLE);
//        //ib1.setVisibility(View.VISIBLE);
//        //ib2.setVisibility(View.VISIBLE);
//    }

    private void createBasicScenes() {

    }


    public void nextImg(View view) {
        int next = slides.getPlace() + 1;
        //ImageButton ib;

        if (next == slides.getSlides().length - 1) {
            //ib = (ImageButton) findViewById(R.id.imageButton);
            //ib.setVisibility(View.INVISIBLE);
            ib1.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.repeat);

        } else if (next == slides.getSlides().length) {
            next = 0;

            //ib = (ImageButton) findViewById(R.id.imageButton2);
            ib2.setVisibility(View.INVISIBLE);
            //ib = (ImageButton) findViewById(R.id.imageButton);
            ib1.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.rightarrow);
            //ib1.setVisibility(View.VISIBLE);

        } else {
            //ib = (ImageButton) findViewById(R.id.imageButton2);
            ib2.setVisibility(View.VISIBLE);
        }
        showImg(next);
    }

    public void prevImg(View view) {
        int prev = slides.getPlace()- 1;
        ib1.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.rightarrow);

        if (prev == 0){
            ib2.setVisibility(View.INVISIBLE);
        } else {
            ib1.setImageResource(cz.socialskills.minaj.social_skills.R.drawable.rightarrow);
        }
        showImg(prev);
    }
    public void exitImg(View view) {
        setMenu(-1);
        showMenu();
    }



    public void showImg(int place){
        //Bitmap bmp = slides.getSlides()[place].getBmp();
        //Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(slides.getSlide(place).getPath()), devWidth, devHeight, false);

        tv.setText(slides.getSlides()[place].getText());
        if (slides.getSlides()[place].getImg() != 0) {
            iv.setImageResource(slides.getSlides()[place].getImg());
        } else {
            iv.setImageBitmap(createBitmap(slides.getSlide(place).getPath()));
        }


        //Toast.makeText(MainActivity.this, Integer.toString(bmp.getWidth())+"x"+Integer.toString(bmp.getHeight()), Toast.LENGTH_SHORT).show();

        slides.setPlace(place);

    }



}
