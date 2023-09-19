package com.example.madassignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import java.util.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AvatarListFragment extends Fragment implements AvatarSelectListener {

    public AvatarListFragment(AvatarSelectListener listener) {
    }

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

        // Create the adapter and pass the AvatarSelectListener instance
        AvatarAdapter adapter = new AvatarAdapter(avatarList, this); // 'this' refers to the AvatarSelectListener

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onAvatarSelected(int drawableResourceId) {
// Before creating a new LoginFragment, check if one already exists.
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        LoginFragment existingLoginFragment = (LoginFragment) fragmentManager.findFragmentByTag("login_fragment");
        Log.d("AvatarListFragment", "Received drawableResourceId: " + drawableResourceId);
        if (existingLoginFragment != null) {
            // Use the existing LoginFragment instance
            Log.d("AvatarListFragment", "Existing login fragment found");
            existingLoginFragment.onAvatarSelected(drawableResourceId);
            fragmentManager.popBackStackImmediate("login_fragment", 0);
            Log.d("AvatarListFragment", "Sent drawableResourceId to existing fragment");
        } else {
            Log.d("AvatarListFragment", "No login fragment found, created new one");
            // Create a new LoginFragment instance
            LoginFragment newLoginFragment = new LoginFragment();
            newLoginFragment.onAvatarSelected(drawableResourceId);
            fragmentManager.beginTransaction().replace(R.id.MainActivityFrameLayout, newLoginFragment, "login_fragment").commit();
            Log.d("AvatarListFragment", "Sent drawableResourceId to new fragment");
        }
    }
}
