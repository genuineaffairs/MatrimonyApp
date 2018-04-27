package co.wisne.matrimonyapp.ui.main;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;

import co.wisne.matrimonyapp.R;
import co.wisne.matrimonyapp.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    MainActivityViewModel viewModel;

    FragmentHomeBinding binding;

    ProfilePagerAdapter profilePagerAdapter;

    Uri profilePictureUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.setLifecycleOwner(this);

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        loadProfilePicture();

        profilePagerAdapter = new ProfilePagerAdapter(getChildFragmentManager());

        binding.viewPager.setAdapter(profilePagerAdapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);



    }



    public void loadProfilePicture(){

        if(profilePictureUri == null){

            viewModel.profilePictureRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Load the image using Glide

                    profilePictureUri = uri;

                    if(getActivity()!=null){

                        Glide.with(HomeFragment.this /* context */)
                                .load(uri)
                                .into(binding.imageViewProfilePicture)
                        ;
                    }

                }
            });

        }else {
            if(getActivity()!=null)
            Glide.with(HomeFragment.this /* context */)
                    .load(profilePictureUri)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(binding.imageViewProfilePicture)
            ;
        }


    }
}
