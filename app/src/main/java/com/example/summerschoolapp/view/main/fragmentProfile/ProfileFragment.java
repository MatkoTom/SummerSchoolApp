package com.example.summerschoolapp.view.main.fragmentProfile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.common.BaseError;
import com.example.summerschoolapp.common.BaseFragment;
import com.example.summerschoolapp.dialog.ErrorDialog;
import com.example.summerschoolapp.dialog.SuccessDialog;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.utils.helpers.EventObserver;
import com.example.summerschoolapp.view.onboarding.OnboardingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    private ProfileFragmentViewModel viewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);

        viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);

        viewModel.getBaseErrors().observe(this, new EventObserver<BaseError>() {
            @Override
            public void onEventUnhandledContent(BaseError value) {
                super.onEventUnhandledContent(value);
                String message = getString(R.string.text_try_again);
                ErrorDialog.CreateInstance(getActivity(), getString(R.string.error), message, getString(R.string.ok), null, null);
            }
        });

        viewModel.getProgressStatus().observe(getActivity(), progressStatus -> {
            switch (progressStatus) {
                case START_PROGRESS:
                    showProgress();
                    break;
                case STOP_PROGRESS:
                    hideProgress();
                    break;
            }
        });

        viewModel.getNavigation().observeEvent(this, navigation -> {
            switch (navigation) {
                case LOGIN:
                    SuccessDialog.CreateInstance(getActivity(), getResources().getString(R.string.success), getString(R.string.logout_success), getResources().getString(R.string.ok), null, new SuccessDialog.OnSuccessDialogInteraction() {
                        @Override
                        public void onPositiveInteraction() {
                            Tools.getSharedPreferences(getActivity()).saveUserToPreferences(new User());
                            OnboardingActivity.StartActivity(getActivity());
                        }

                        @Override
                        public void onNegativeInteraction() {
                            // ignored
                        }
                    });
            }
        });

        return rootView;
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        // TODO @Matko
        // nothing should be fetched in view, call method and do this in viewModel
        viewModel.logout(Tools.getSharedPreferences(getActivity()).getSavedUserData().getJwt());
    }

}
