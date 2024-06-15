package ds.cmu.puppypal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/*
 * @author: rsequeir, Rheann Sequeira
 * */
public interface ApiService {

	@GET("dogbreeds")
	Call<List<PuppyBreed>> getDogBreeds();

	@GET("dogimage")
	Call<List<PuppyInfoImage>> getDogInfoImage(@Query("breedId") Integer breedId);

}
