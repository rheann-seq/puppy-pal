package ds.cmu.puppypal;

public class PuppyInfoImage {

	String temperament;
	String origin;
	String name;
	String bred_for;
	Integer id;
	String url;
	String life_span;

	public PuppyInfoImage(String temperament, String origin, String name, String bred_for,
						  Integer id, String url, String life_span) {
		this.temperament = temperament;
		this.origin = origin;
		this.name = name;
		this.bred_for = bred_for;
		this.id = id;
		this.url = url;
		this.life_span = life_span;
	}

	public String getLife_span() {
		return life_span;
	}

	public void setLife_span(String life_span) {
		this.life_span = life_span;
	}

	public String getTemperament() {
		return temperament;
	}

	public void setTemperament(String temperament) {
		this.temperament = temperament;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBred_for() {
		return bred_for;
	}

	public void setBred_for(String bred_for) {
		this.bred_for = bred_for;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PuppyInfoImage{" + "temperament='" + temperament + '\'' + ", origin='" + origin + '\'' + ", name='" + name + '\'' + ", bred_for='" + bred_for + '\'' + ", id=" + id + ", url='" + url + '\'' + '}';
	}
}
