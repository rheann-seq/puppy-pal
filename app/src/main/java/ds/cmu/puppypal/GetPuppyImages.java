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
public class GetPuppyImages {

	private static final String BASE_URL = "https://glowing-journey-699qrx9r94jwf4qgp-8080.app.github.dev/";
	private final OkHttpClient client = new OkHttpClient();
	private ApiService service;
	private Integer breedId;

	public GetPuppyImages(Integer breedId) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		service = retrofit.create(ApiService.class);
	}

	public void fetchPuppyImages(Integer breedId,
								 final DataCallback<List<PuppyInfoImage>> callback) {
		Call<List<PuppyInfoImage>> call = service.getDogInfoImage(breedId);
		Log.d("GetPuppyImage", "In fetchPuppyImages()");
		Log.d("GetPuppyImage", call.toString());

		call.enqueue(new Callback<List<PuppyInfoImage>>() {
			@Override
			public void onResponse(Call<List<PuppyInfoImage>> call,
								   Response<List<PuppyInfoImage>> response) {
				if (response.isSuccessful()) {
					List<PuppyInfoImage> puppyInfoAndImage = response.body();
					Log.d("GetPuppyImage", "GetPuppyImage response from api:" + puppyInfoAndImage);
					callback.onResponse(puppyInfoAndImage);
				} else {
					// Handle unsuccessful response
					System.out.println("GetPuppyImage API request /dogimage unsuccessful");
					Log.d("GetPuppyImage", "response NOT received");
				}
			}

			@Override
			public void onFailure(Call<List<PuppyInfoImage>> call, Throwable t) {
				Log.d("GetPuppyImage", "call to fetch puppy images failed");
				System.out.printf("GetPuppyImage API request /getbreeds failed");
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
		void onResponse(List<PuppyInfoImage> data);
		void onError(String errorMessage);
	}


}
