package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AvatarListFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_avatar_list, container, false);

        //create list of images
        List<Avatar> avatarList = new ArrayList<>();
        avatarList.add(new Avatar(R.drawable.avatar_default));
        avatarList.add(new Avatar(R.drawable.avatar_bear));
        avatarList.add(new Avatar(R.drawable.avatar_dog));
        avatarList.add(new Avatar(R.drawable.avatar_koala));
        avatarList.add(new Avatar(R.drawable.avatar_man1));
        avatarList.add(new Avatar(R.drawable.avaatar_woman3));
        avatarList.add(new Avatar(R.drawable.avatar_man2));
        avatarList.add(new Avatar(R.drawable.avatar_man3));
        avatarList.add(new Avatar(R.drawable.avatar_man4));
        avatarList.add(new Avatar(R.drawable.avatar_meerkat));
        avatarList.add(new Avatar(R.drawable.avatar_woman1));
        avatarList.add(new Avatar(R.drawable.avatar_woman2));
        avatarList.add(new Avatar(R.drawable.avatar_woman4));

        // Initialize the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new AvatarAdapter(avatarList));

        return rootView;
    }

}
