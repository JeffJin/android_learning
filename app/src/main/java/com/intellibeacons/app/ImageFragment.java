package com.intellibeacons.app;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ImageFragment extends Fragment {
    private static final String URL_LIST = "url_list";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";

    // TODO: Rename and change types of parameters
    private String[] mUrls;
    private String[] mTitles;
    private int mWidth;
    private int mHeight;
    private int mImageIndex = 0;


    ImageView mImageView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param urls Parameter 1.
     * @param width Parameter 2.
     * @param height Parameter 3.
     * @return A new instance of fragment ImageFragment.
     */
    public static ImageFragment newInstance(String[] urls, int width, int height) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putStringArray(URL_LIST, urls);
        args.putInt(WIDTH, width);
        args.putInt(HEIGHT, height);
        fragment.setArguments(args);
        return fragment;
    }
    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mUrls = getArguments().getStringArray(URL_LIST);
//            mWidth = getArguments().getInt(WIDTH);
//            mHeight = getArguments().getInt(HEIGHT);
//        }

        mUrls = getResources().getStringArray(R.array.images_list);
        mTitles = getResources().getStringArray(R.array.image_titles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
//        Bundle args = getArguments();
//        if(args != null){
//            String[] images = args.getStringArray(URL_LIST);
//            if(mImageIndex >= 0 && mImageIndex < images.length){
//                displayImage(view, images[mImageIndex]);
//            }
//        }
        mImageView = (ImageView) view.findViewById(R.id.imageViewFragment);
        return view;
    }

    public void setImageSource(int index) {
        String imageName = mUrls[index];
        Drawable drawable = getResources().getDrawable(getResources()
                .getIdentifier(imageName, "drawable", ((Activity)mListener).getPackageName()));

        mImageView.setImageDrawable(drawable);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
