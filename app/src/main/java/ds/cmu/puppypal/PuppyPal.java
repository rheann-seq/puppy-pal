package ds.cmu.puppypal;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import ds.cmu.puppypal.databinding.ActivityMainBinding;

/*
 * @author: rsequeir, Rheann Sequeira
 * */
public class PuppyPal extends AppCompatActivity {

    PuppyPal me = this;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String[] items = new String[]{"Labrador Retriever", "German Shepherd", "Golden Retriever"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        final PuppyPal ma = this;



        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.submit);

        /*
         * Find the spinner object
         * */
//        Spinner spinner = findViewById(R.id.spinner);

        //add a listener to submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viewParam) {
                String searchTerm = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Selected: " + searchTerm, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
