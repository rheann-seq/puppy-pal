package ds.cmu.puppypal;

import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * @author: rsequeir, Rheann Sequeira
 * */
public class GetPuppyDetails {

	private ApiService service;
	private final OkHttpClient client = new OkHttpClient();

	private static final String BASE_URL = "https://glowing-journey-699qrx9r94jwf4qgp-8080.app.github.dev//";

	/*static class CheckServerReachabilityTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... voids) {
			HttpUrl url = HttpUrl.parse(BASE_URL + "ping");
			if (url == null) {
				Log.e("GetPuppyDetails", "Invalid URL: " + BASE_URL);
				return false;
			}

			Request request = new Request.Builder().url(url).head().build();

			try {
				okhttp3.Response response = new OkHttpClient().newCall(request).execute();
				return response.isSuccessful();
			} catch (IOException e) {
				Log.e("GetPuppyDetails", "Error checking server reachability", e);
				return false;
			}

		}
		@Override
		protected void onPostExecute(Boolean isSuccessful) {
			if (isSuccessful) {
				Log.d("GetPuppyDetails", "Server is reachable");
			} else {
				Log.d("GetPuppyDetails", "Server is not reachable");
			}
		}
	}*/

	/*public void checkServerReachability() {
		AsyncTask<Void, Void, Boolean> execute = new AsyncTask<Void, Void, Boolean>() {
			@SuppressLint("StaticFieldLeak")
			@Override
			protected Boolean doInBackground(Void... voids) {
				HttpUrl url = HttpUrl.parse(BASE_URL+"ping");
				if (url == null) {
					Log.e("GetPuppyDetails", "Invalid URL: " + BASE_URL);
					return false;
				}

				Request request = new Request.Builder().url(url).head().build();

				try {
					okhttp3.Response response = client.newCall(request).execute();
					return response.isSuccessful();
				} catch (IOException e) {
					Log.e("GetPuppyDetails", "Error checking server reachability", e);
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean isSuccessful) {
				if (isSuccessful) {
					Log.d("GetPuppyDetails", "Server is reachable");
				} else {
					Log.d("GetPuppyDetails", "Server is not reachable");
				}
			}
		}.execute();
	}*/
	public GetPuppyDetails() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL) // Update with your API URL
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		service = retrofit.create(ApiService.class);
	}

	public void fetchData(final DataCallback<List<PuppyBreed>> callback) {
		Call <List<PuppyBreed>> call = service.getDogBreeds();
		Log.d("GetPuppyDetails", "In fetchData()");
		Log.d("GetPuppyDetails", call.toString());

		call.enqueue(new Callback<List<PuppyBreed>>() {
			@Override
			public void onResponse(Call<List<PuppyBreed>>call, Response<List<PuppyBreed>> response) {
				if (response.isSuccessful()) {
					List<PuppyBreed> puppyBreeds = response.body();
					Log.d("GetPuppyDetails", "Puppy breeds response from api:" + puppyBreeds);
					callback.onSuccess(puppyBreeds);
				} else {
					// Handle unsuccessful response
					System.out.println("GetPuppyDetails API request /getbreeds unsuccessful");
					Log.d("GetPuppyDetails", "response NOT received");
				}
			}

			@Override
			public void onFailure(Call<List<PuppyBreed>> call, Throwable t) {
				Log.d("GetPuppyDetails", "call to fetch puppy breeds failed");
				System.out.printf("GetPuppyDetails API request /getbreeds failed");
				if (call.isCanceled()) {
					Log.d("GetPuppyDetails", "Call was cancelled");
				} else {
					Log.d("GetPuppyDetails", "Call failed: " + t.getMessage());
				}
			}

			/*@Override
			public void onError(Call<ResponseBody> call, Throwable t) {
				// Handle failure
				Log.d("GetPuppyDetails", "call to fetch puppy breeds failed");
				System.out.printf("GetPuppyDetails API request /getbreeds failed");
			}*/
		});
	}

	public interface DataCallback<T> {
		void onSuccess(List<PuppyBreed> puppyBreeds);
		void onError(String errorMessage);
	}


}

