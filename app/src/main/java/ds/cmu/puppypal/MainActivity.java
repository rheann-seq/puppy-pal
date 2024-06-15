package ds.cmu.puppypal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ds.cmu.puppypal.databinding.ActivityMainBinding;


/*
 * @author: rsequeir, Rheann Sequeira
 * */

/*
 * This class is responsible for updating all the UI widgets
 * The spinner view is updated according to the breed details obtained from GetPuppyDetails
 * On submit, the GetPuppyImages is called to fetch details for the selected breed from the
 * webservcie
 * */
public class MainActivity extends AppCompatActivity implements GetPuppyDetails.DataCallback<List<PuppyBreed>>, GetPuppyImages.DataCallback<PuppyInfoImage> {

	private AppBarConfiguration appBarConfiguration;
	private ActivityMainBinding binding;
	private List<PuppyBreed> puppyBreedsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the list
		puppyBreedsList = new ArrayList<>();

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());


		Spinner spinner = findViewById(R.id.spinner);

		/*
		 * Find the "submit" button, and add a listener to it
		 */
		Button submitButton = (Button) findViewById(R.id.submit);
		// call fetchImageUrl on submit
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View viewParam) {
				String searchBreed = spinner.getSelectedItem().toString();
				Toast.makeText(getApplicationContext(), "Selected: " + searchBreed,
						Toast.LENGTH_SHORT).show();

				fetchImageUrl(searchBreed);
			}
		});
		GetPuppyDetails gpd = new GetPuppyDetails();


		if (isNetworkConnected()) {
			Log.d("GetPuppyDetails", "Network is connected, checking server availability");
			//			System.out.println("Trying to fetch GetPuppyDetails");
			gpd = new GetPuppyDetails();

			// Call fetchData with this as the callback
			gpd.fetchData(this);
		} else {
			Log.d("GetPuppyDetails", "Network is NOT connected, cannot fetch details");
			System.out.println("Cannot fetch GetPuppyDetails");
		}


	}

	// this calls the GetPuppyImages class to call the webservice
	private void fetchImageUrl(String searchBreed) {
		Integer breedId = null;
		for (PuppyBreed breed : puppyBreedsList) {
			if (breed.getName().equalsIgnoreCase(searchBreed)) {
				breedId = breed.getId();
				break; // Exit the loop once breed is found
			}
		}

		//update the Image view and text view with the details received
		if (breedId != null) {
			GetPuppyImages gpi = new GetPuppyImages(breedId);
			gpi.fetchPuppyImages(breedId, new GetPuppyImages.DataCallback<List<PuppyInfoImage>>() {
				@Override
				public void onResponse(List<PuppyInfoImage> data) {
					//					onResponse(data);
					Log.d("GetPuppyImage", "data received" + data.get(0).toString());
					if (data != null && data.size() != 0) {
						String url = data.get(0).getUrl();
						downloadAndDisplayImage(url);
						StringBuilder puppyInfo = new StringBuilder();
						if (data != null && data.get(0) != null) {
							if (data.get(0).getName() != null)
								puppyInfo.append("\nName: " + data.get(0).getName());
							if (data.get(0).getTemperament() != null)
								puppyInfo.append("\nLife Span: " + data.get(0).getLife_span());
							if (data.get(0).getTemperament() != null)
								puppyInfo.append("\nTemperament: " + data.get(0).getTemperament());
							if (data.get(0).getBred_for() != null)
								puppyInfo.append("\nBred for: " + data.get(0).getBred_for());
							if (data.get(0).getOrigin() != null && !data.get(0).getOrigin().equalsIgnoreCase("null") && !Objects.equals(data.get(0).getOrigin(), ""))
								puppyInfo.append("\nOrigin: " + data.get(0).getOrigin());
						}

						displayPuppyInfo(puppyInfo);
					}
				}

				@Override
				public void onError(String errorMessage) {
					MainActivity.this.onError(errorMessage);
				}
			});
		}
	}

	//method to check if android app is connected to the internet
	private boolean isNetworkConnected() {
		ConnectivityManager connectivityManager =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	// Use library Picasso to download and display the image
	private void downloadAndDisplayImage(String imageUrl) {
		ImageView imageView = findViewById(R.id.imageView);
		Picasso.get().load(imageUrl).into(imageView);
		imageView.setVisibility(View.VISIBLE);
	}

	//update the text view with the details of the puppy
	private void displayPuppyInfo(StringBuilder puppyInfo) {
		TextView textView = findViewById(R.id.textView2);
		textView.setText(puppyInfo);
		textView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSuccess(List<PuppyBreed> puppyBreeds) {
		puppyBreedsList.addAll(puppyBreeds);
		Spinner spinner = findViewById(R.id.spinner);

		// Extract breed names from the list of PuppyBreed objects
		List<String> breedNames = new ArrayList<>();
		for (PuppyBreed breed : puppyBreeds) {
			breedNames.add(breed.getName());
		}

		// Create a new adapter with the updated list of breed names
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_item, breedNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Set the new adapter to the spinner
		spinner.setAdapter(adapter);
	}

	@Override
	public void onError(String errorMessage) {
		Log.d("GetPuppyDetails", "Could not fetch puppy breeds from the API");
	}

	@Override
	public void onResponse(List<PuppyInfoImage> data) {

		String url = data.get(0).getUrl();
	}


}
