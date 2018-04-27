package co.wisne.matrimonyapp.ui.profile.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import co.wisne.matrimonyapp.R;

public class BookmarkPromptDialogFragment extends DialogFragment {

    public interface BookmarkUserPrompt{
        public void bookmarkCurrentUser();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.bookmark_user_dialog_prompt_message)
                .setPositiveButton(R.string.prompt_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((BookmarkUserPrompt)getActivity()).bookmarkCurrentUser();
                    }
                }).setNegativeButton(R.string.prompt_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
