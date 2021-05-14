package com.example.mytestapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private ViewPager2 viewPager;

    private final static String ID_KEY = "id_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), new ContentFragment.Callback() {
            @Override
            public void onMinusClick(int id) {
                List<Integer> adapterList = new ArrayList<>(adapter.getFragmentList());
                int removePosition = adapterList.indexOf(id);
                adapterList.remove(removePosition);
                adapter.setFragmentList(adapterList);
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(removePosition - 1, false);


                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                notificationManager.deleteNotificationChannel(String.valueOf(id));
            }

            @Override
            public void onPlusClick(int id) {
                List<Integer> adapterList = new ArrayList<>(adapter.getFragmentList());
                Integer lastNumber = adapterList.get(adapterList.size() - 1);
                int newNumber = lastNumber + 1;
                adapterList.add(newNumber);
                adapter.setFragmentList(adapterList);
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(newNumber);
            }

            @Override
            public void onCreateNewNotificationClick(int id) {

                String channelId = String.valueOf(id);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Name", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                notificationIntent.putExtra(ID_KEY, id);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, id, notificationIntent, 0);

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, channelId)
                                .setSmallIcon(R.drawable.icons_notification)
                                .setContentTitle("Chat heads active")
                                .setContentText("Notification " + id)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(contentIntent);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(id, builder.build());
            }
        });

        viewPager = findViewById(R.id.viewpager);

        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(adapter);


        List<Integer> fragmentList = new ArrayList();
        Integer id = getIntent().getIntExtra(ID_KEY, 1);
        fragmentList.add(id);

        adapter.setFragmentList(fragmentList);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Integer id = intent.getIntExtra(ID_KEY, -1);
        int newPosition = adapter.getFragmentList().indexOf(id);
        if(newPosition != -1) {
            viewPager.setCurrentItem(newPosition);
        } else {
            List<Integer> adapterList = new ArrayList<>(adapter.getFragmentList());
            adapterList.add(id);

            Collections.sort(adapterList);

            int position = adapterList.indexOf(id);
            adapter.setFragmentList(adapterList);
            adapter.notifyDataSetChanged();
            viewPager.setCurrentItem(position);
        }
    }
}
