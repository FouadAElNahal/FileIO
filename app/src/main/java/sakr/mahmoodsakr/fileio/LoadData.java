package sakr.mahmoodsakr.fileio;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadData extends AppCompatActivity {
    TextView TextViewname, TextViewpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        TextViewname = (TextView) findViewById(R.id.nameTextView);
        TextViewpassword = (TextView) findViewById(R.id.passwordTextView);
    }

    public void open_ActivityA(View view) {
        Intent intent = new Intent(LoadData.this, StoreData.class);
        startActivity(intent);
        finish();
    }

    public void loading_file_from_InternalStorage(View view) {
        loadData(null, "loading_file_from_default_InternalStorage");
    }

    public void loading_file_from_Directory_in_InternalStorage(View view) {
        String Folder_Name = "SakrDirectory";
        Context context = getApplicationContext();
        String Folder_Path = context.getFilesDir().getAbsolutePath() + File.separator + Folder_Name;
        File folderDir = new File(Folder_Path);
        if (!folderDir.exists()) {
            folderDir.mkdirs();
        }
        File file = new File(folderDir, "my_internal_file.txt");
        loadData(file, "loading_file_from_Directory_in_InternalStorage");
    }

    public void loading_file_from_Private_ExternalStorage(View view) {
        //don't forget give permission on mainfest file
        File folderDir = getExternalFilesDir("Private_External_Sakr_Directory");
        File file = new File(folderDir, "my_private_external_file.txt");
        loadData(file, "loading_file_from_Private_ExternalStorage");
    }

    public void loading_file_from_Public_ExternalStorage(View view) {
//don't forget give permission on mainfest file
        File folderDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = new File(folderDir, "my_public_external_file.txt");
        loadData(file, "loading_file_from_Public_ExternalStorage");
    }


    public void loadData(File file, String status) {
        int read = -1;
        try {
            FileInputStream inputStream = null;
            if (status.equals("loading_file_from_default_InternalStorage")) {
                inputStream = openFileInput("default_internal.txt");
            } else {
                inputStream = new FileInputStream(file);
            }
            Toast.makeText(this, "File Status is : " + status, Toast.LENGTH_LONG).show();
            StringBuffer CharactersBuffer = new StringBuffer();
            StringBuffer BinaryBuffer = new StringBuffer();
            read = inputStream.read();  // read single byte = single character and cast it to an integer byte
            if (read == -1) {  //file is empty
                Toast.makeText(this, "File is Empty", Toast.LENGTH_SHORT).show();
            } else {
                while (read != -1) {
                    CharactersBuffer.append((char) read);
                    BinaryBuffer.append(read).append("-");
                    read = inputStream.read();
                }
                String allStringBufferContent = CharactersBuffer.toString();
                String allBinaryBufferContent = BinaryBuffer.toString();
                Log.d("sakr", "allStringBufferContent " + allStringBufferContent);
                Log.d("sakr", "allBinaryBufferContent " + allBinaryBufferContent);
                Toast.makeText(this, "Reading from the file is done successfully .", Toast.LENGTH_LONG).show();
                int lastDelimeterIndex;
                String[] tokens = allStringBufferContent.split("-");
                TextViewname.setText(tokens[0]);
                TextViewpassword.setText(tokens[1]);
                inputStream.close();
                Toast.makeText(this, "Closing inputStream session is done successfully .", Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File is not found !!! \nPlease create it firstly before reading from it .", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "IO Exception :\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}

