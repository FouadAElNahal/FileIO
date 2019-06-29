package sakr.mahmoodsakr.fileio;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoreData extends AppCompatActivity {

    EditText nameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    public void open_ActivityB(View view) {
        Intent intent = new Intent(StoreData.this, LoadData.class);
        startActivity(intent);
        finish();
    }

    public void saving_file_in_InternalStorage(View view) {
        storeData(null, "saving_file_in_default_internalStorage");
    }

    public void saving_file_in_Directory_in_InternalStorage(View view) {
        String Folder_Name = "SakrDirectory";
        Context context = getApplicationContext();
        String Folder_Path = context.getFilesDir().getAbsolutePath() + File.separator + Folder_Name;
        File folderDir = new File(Folder_Path);
        if (!folderDir.exists()) {
            folderDir.mkdirs();
            Toast.makeText(this, "Directory is created for the first time ", Toast.LENGTH_SHORT).show();
        }
        File file = new File(folderDir, "my_internal_file.txt");
        storeData(file, "saving_file_in_Directory_in_InternalStorage");
    }

    public void saving_file_in_Private_ExternalStorage(View view) {
        //don't forget give permission on mainfest file
        File folderDir = getExternalFilesDir("Private_External_Sakr_Directory");
        File file = new File(folderDir, "my_private_external_file.txt");
        storeData(file, "saving_file_in_Private_ExternalStorage");
    }

    public void saving_file_in_Public_ExternalStorage(View view) {
//don't forget give permission on mainfest file
        File folderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!folderDir.exists()) {
            folderDir.mkdirs();
            Toast.makeText(this, "Directory is created for the first time ", Toast.LENGTH_SHORT).show();
        }
        File file = new File(folderDir, "my_public_external_file.txt");
        storeData(file, "saving_file_in_Public_ExternalStorage");
    }

    public void storeData(File file, String status) {
        String Name = nameEditText.getText().toString();
        String Password = passwordEditText.getText().toString();
        if (Name.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "EditText Fields are Empty !", Toast.LENGTH_SHORT).show();
        } else {
            FileOutputStream outputStream = null; // create and write to a file
            try {
                if (status.equals("saving_file_in_default_internalStorage")) {
//to use overreide mode and also private use next line
//outputStream = openFileOutput("default_internal.txt", Context.MODE_PRIVATE);
//to use append mode and also private use next line
                    outputStream = openFileOutput("default_internal.txt", Context.MODE_APPEND);
                    Toast.makeText(this, "File Path : " + getFilesDir().getAbsolutePath() + File.separator + "default_internal.txt", Toast.LENGTH_LONG).show();
                    Log.d("sakr", "File Path : " + getFilesDir().getAbsolutePath() + File.separator + "default_internal.txt");
                } else {
                    outputStream = new FileOutputStream(file);
                    Toast.makeText(this, "File Path : " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    Log.d("sakr", "File Path : " + file.getAbsolutePath());
                }
                Toast.makeText(this, "File Status is : " + status, Toast.LENGTH_LONG).show();
                byte[] nameInByte = Name.concat("-").getBytes(); //space after token
                byte[] passwordInByte = Password.concat("-").getBytes();
                outputStream.write(nameInByte);
                outputStream.write(passwordInByte);
                for (int i = 0; i < nameInByte.length; i++) {
                    Log.d("sakr", "name in byte : " + i + " - " + nameInByte[i]);
                }
                Toast.makeText(this, "Writing to the file is done .", Toast.LENGTH_LONG).show();
                outputStream.close();
                Toast.makeText(this, "Closing the outputStream session is done Successfully .", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "File is not found !!! \nPlease create it firstly !", Toast.LENGTH_LONG).show();
                Log.d("MahmoodSakr-FileEx", e.toString());
            } catch (IOException e) {
                Toast.makeText(this, "IO Exception \n" + e.toString(), Toast.LENGTH_LONG).show();
                Log.d("MahmoodSakr-IOEx", e.toString());
            }

        }

    }
}

