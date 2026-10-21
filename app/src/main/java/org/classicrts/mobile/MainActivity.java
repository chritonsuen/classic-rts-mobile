package org.classicrts.mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int PICK_GAME_FOLDER = 42;
    private TextView status;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 48, 32, 32);
        TextView title = new TextView(this);
        title.setText("Classic RTS Mobile");
        title.setTextSize(26);
        layout.addView(title);
        TextView game = new TextView(this);
        game.setText("\nBattle for Middle-earth II\nInitial profile — runtime not yet integrated\n");
        game.setTextSize(17);
        layout.addView(game);
        Button importButton = new Button(this);
        importButton.setText("Select game folder");
        importButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivityForResult(intent, PICK_GAME_FOLDER);
        });
        layout.addView(importButton);
        status = new TextView(this);
        status.setText("\nNo game folder selected.\nThis prototype does not execute Windows games yet.");
        layout.addView(status);
        setContentView(layout);
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_GAME_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try { getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); }
                catch (SecurityException ignored) { }
                status.setText("Selected folder: " + uri + "\nImport/validation and Windows runtime are not yet implemented.");
            }
        }
    }
}
