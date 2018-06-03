package com.app.lenovo.summerproject;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
public class StatesData extends AppCompatActivity{
    int h=getScreenHeight();
    int w=getScreenWidth();
    int p=1;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    String name[]={"Oregon","Idaho","Wyoming","South Dakota","California","Nevada","Utah","Colorado","Arizona","New Mexico",
            "Oklahama","Arkansas","Texas","Louisiana","Mississippi","Albama"};
    int names[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    LinkedHashSet<Integer> lhs=new LinkedHashSet<>();
    LinkedHashSet<Integer> lhs2=new LinkedHashSet<>();
    //private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.states);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_view, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
            String s1 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Bp", "");
            String s2 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Temp", "");
            String s3 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Other", "");
            String s4 = HelperClass.getSharedPreferencesString(getApplicationContext(), "Date", "");
            boolean c1=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c1",false);
            boolean c2=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c2",false);
            boolean c3=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c3",false);
            boolean c4=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c4",false);
            boolean c5=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c5",false);
            boolean c6=HelperClass.getSharedPreferencesBoolean(getApplicationContext(),"c6",false);
            //Toast.makeText(getApplicationContext(), "Please!" + s1, Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                try {
                    Intent intent=new Intent(StatesData.this,ProfileHandling.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Log.e("Activity",e.getMessage());
                }
                break;
            case R.id.add_mine:
                Toast.makeText(getApplicationContext(),"My suggestion",Toast.LENGTH_LONG).show();
                dummy3();
                break;
            case R.id.alert:
                Toast.makeText(getApplicationContext(),"Alert",Toast.LENGTH_SHORT).show();
                /*mDatabase=FirebaseDatabase.getInstance().getReference();
                SuggestionModel sm = new SuggestionModel("012", 1);
                mDatabase.child(name[0]).push().setValue(sm).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                            }
                        });
                SuggestionModel sm2 = new SuggestionModel("0459", 1);
                mDatabase.child(name[0]).push().setValue(sm2);*/
                break;
            case R.id.show_me:
                Toast.makeText(getApplicationContext(),"Show Path",Toast.LENGTH_SHORT).show();
                dummy();
                break;
            case R.id.suggest:
                Toast.makeText(getApplicationContext(),"Suggesting...",Toast.LENGTH_SHORT).show();
                dummy2(c1,c2,c3,c4,c5,c6);
                break;
            case R.id.Temp:
                Toast.makeText(getApplicationContext(),"Temperatures...",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Uh-oh",Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    private void dummy3()
    {
        /*StringBuilder sb=new StringBuilder();
        Iterator itr=lhs2.iterator();
        int i=(int)itr.next();
        final String xr=name[i];
        sb.append(i);
        while(itr.hasNext())
            sb.append(itr.next());
        final String str=sb.toString();
        lhs2.clear();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query=mDatabase.child(xr).orderByChild("path").equalTo(str);
        Log.e("0",query+"");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(),"Exists",Toast.LENGTH_SHORT).show();
                    for (DataSnapshot children : dataSnapshot.getChildren())
                    {
                        Toast.makeText(getApplicationContext(),""+children.child("count").getValue(),Toast.LENGTH_SHORT).show();
                        try {
                            int x = Integer.parseInt(children.child("count").getValue().toString());
                            children.getRef().removeValue();
                            final SuggestionModel sm = new SuggestionModel(str, x + 1);
                            mDatabase.child(xr).push().setValue(sm);
                        }catch (ClassCastException e)
                        {
                            Log.e("What?",e.getMessage());
                        }
                    }
                    /*Log.e("1","If top");
                    for (DataSnapshot children : dataSnapshot.getChildren()) {
                        if(children.child("path").getValue(String.class)!=null)
                        {
                            Log.e("2","Existing node,"+children.child("path").getValue(String.class));
                            try {
                                final SuggestionModel sm = new SuggestionModel(name[2], children.child("count").getValue(Integer.class));
                                mDatabase.child(name[0]).setValue(sm);
                                children.getRef().removeValue();
                                break;
                            }catch (NullPointerException e)
                            {
                                Log.e("Null",e.getMessage());
                            }
                        }
                        else
                        {
                            Log.e("3","Should push value");
                            final SuggestionModel sm = new SuggestionModel(name[2], 1);
                            mDatabase.child(name[0]).setValue(sm);
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Does not Exist",Toast.LENGTH_SHORT).show();
                    final SuggestionModel sm = new SuggestionModel(str, 1);
                    mDatabase.child(xr).push().setValue(sm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private void dummy2(boolean c1,boolean c2,boolean c3,boolean c4,boolean c5,boolean c6)
    {
        boolean summer=true;//Will parse from the date later
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suggestions...");
        TextView tv=new TextView(this);
        StringBuilder sb=new StringBuilder();
        if(summer)
            sb.append("You should wear cotton clothes\n");
        else
            sb.append("You shouuld wear cotton clothes\n");
        if(c1)
            sb=sb.append("Don't forget to take water bottles with you\n");
        if(c3)
            sb.append("Have you taken your migraine medicines?\n");
        if(c2)
            sb.append("Please wear a cap or hat if you haven't, also remember to avoid congested places. We don't want another" +
                    " fainting incident, do we?\n");
        if(c4)
            sb.append("Don't forget to take lotions. Also wear loose fitting clothes\n");
        if(c5)
            sb.append("Try to stay in shades\n");
        if(c6)
            sb.append("Remember to sit down at once if you're feeling tired. Also there's no shame in asking for help\n");
        tv.setText(sb.toString());
        builder.setView(tv);
        builder.show();
    }

    final void dummy()
    {
        setContentView(R.layout.states);
        Algorithms obj=new Algorithms(16);
        double dist[]=new double[16];
        int parent[]=new int[16];
        int mapping[]={R.id.textView11,R.id.textView4,R.id.textView5,R.id.textView6,
                R.id.textView7,R.id.textView12,R.id.textView17,R.id.textView18
                ,R.id.textView3,R.id.textView16,R.id.textView13,R.id.textView14
                ,R.id.textView15,R.id.textView8,R.id.textView9,R.id.textview20};

        //obj=obj.go2();
        /*lhs.clear();
        lhs.add(0);
        lhs.add(7);*/
        if(lhs.size()==2)
        {
            //Toast.makeText(getApplicationContext(),"Reached here",Toast.LENGTH_SHORT).show();
            Iterator iterator=lhs.iterator();
            int x=(int)iterator.next(),y=(int)iterator.next();
            parent[x]=x;
            //Toast.makeText(getApplicationContext(),""+x+","+y,Toast.LENGTH_SHORT).show();
            try {
                obj.shortestPath(x, y, dist, parent);
                String s=dist[y] + "," + parent[y];
                Log.e("Umm",s+","+ Arrays.toString(parent));
                colourIt(parent,y,mapping);
                TextView t=findViewById(mapping[x]);
                t.setBackgroundColor(Color.GREEN);
               // Log.e("Umm",s);
            }catch (Exception e)
            {
                Log.e("Testing...", e.getMessage());
            }
        }
    }
    private void colourIt(int par[],int x,int mapping[]) {
        if(par[x]!=x) {
            TextView t=findViewById(mapping[x]);
            t.setBackgroundColor(Color.GREEN);
            colourIt(par, par[x], mapping);
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Toast.makeText(this, "left2right swipe", Toast.LENGTH_SHORT).show ();
                    return true;
                }
                break;
        }
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.e("Hmm",x+","+y);
        if(x<=w/4&&y<=h/4) {
            Log.e(p + "", name[0]);
            lhs.add(names[0]);
            lhs2.add(names[0]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y<=h/4) {
            Log.e(p + "", name[1]);
            lhs.add(names[1]);
            lhs2.add(names[1]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y<=h/4) {
            Log.e(p + "", name[2]);
            lhs.add(names[2]);
            lhs2.add(names[2]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y<=h/4) {
            Log.e(p + "", name[3]);
            lhs.add(names[3]);
            lhs2.add(names[3]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[4]);
            lhs.add(names[4]);
            lhs2.add(names[4]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[5]);
            lhs.add(names[5]);
            lhs2.add(names[5]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[6]);
            lhs.add(names[6]);
            lhs2.add(names[6]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=h/4&&y<=h/2) {
            Log.e(p + "", name[7]);
            lhs.add(names[7]);
            lhs2.add(names[7]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[8]);
            lhs.add(names[8]);
            lhs2.add(names[8]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[9]);
            lhs.add(names[9]);
            lhs2.add(names[9]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[10]);
            lhs.add(names[10]);
            lhs2.add(names[10]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=h/2&&y<=3*h/4) {
            Log.e(p + "", name[11]);
            lhs.add(names[11]);
            lhs2.add(names[11]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }else if(x<=w/4&&y>=3*h/4) {
            Log.e(p + "", name[12]);
            lhs.add(names[12]);
            lhs2.add(names[12]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>w/4&&x<=2*w/4&&y>=3*h/4) {
            Log.e(p + "", name[13]);
            lhs.add(names[13]);
            lhs2.add(names[13]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        if(x>2*w/4&&x<=3*w/4&&y>=3*h/4) {
            Log.e(p + "", name[14]);
            lhs.add(names[14]);
            lhs2.add(names[14]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
        }
        else if(x>3*w/4&&y>=3*h/4) {
            Log.e(p + "", name[15]);
            lhs.add(names[15]);
            lhs2.add(names[15]);
            if(lhs.size()>2)
                lhs.remove(lhs.iterator().next());
            //NavigationView navigationView = findViewById(R.id.nav_view);
            //dummy();
        }

        p++;
        return false;
    }
    public static int getScreenWidth() {

        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
