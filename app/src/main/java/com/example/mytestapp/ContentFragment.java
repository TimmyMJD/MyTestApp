package com.example.mytestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContentFragment extends Fragment {

    private final static String ID_KEY = "id_key";
    private final static String IS_SHOW_REMOVE_KEY = "is_show_remove_key";

    private int idArg;
    private boolean isShowRemoveArg;

    public Callback clickCallback;

    public static ContentFragment newInstance(boolean isShowRemove, int number) {
        ContentFragment myFragment = new ContentFragment();

        Bundle args = new Bundle();
        args.putBoolean(IS_SHOW_REMOVE_KEY, isShowRemove);
        args.putInt(ID_KEY, number);
        myFragment.setArguments(args);

        return myFragment;
    }

    public ContentFragment() {
        super(R.layout.fragment_content);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        isShowRemoveArg = args.getBoolean(IS_SHOW_REMOVE_KEY);
        idArg = args.getInt(ID_KEY);
    }


    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        TextView fragmentNumber = rootView.findViewById(R.id.fragment_number);
        fragmentNumber.setText(String.valueOf(idArg));

        int minusVisibility;
        if (isShowRemoveArg) {
            minusVisibility = View.VISIBLE;
        } else {
            minusVisibility = View.INVISIBLE;
        }
        rootView.findViewById(R.id.button_minus).setVisibility(minusVisibility);

        ImageButton buttonMinus = (ImageButton) rootView.findViewById(R.id.button_minus);
        ImageButton buttonPlus = (ImageButton) rootView.findViewById(R.id.button_plus);
        Button buttonCreateNewNotification = (Button) rootView.findViewById(R.id.buttonCreateNewNotification);

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallback.onMinusClick(idArg);
            }
        });
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallback.onPlusClick(idArg);
            }
        });
        buttonCreateNewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallback.onCreateNewNotificationClick(idArg);
            }
        });



    }

    public interface Callback {

        void onMinusClick(int id);

        void onPlusClick(int id);

        void onCreateNewNotificationClick(int id);
    }
}
