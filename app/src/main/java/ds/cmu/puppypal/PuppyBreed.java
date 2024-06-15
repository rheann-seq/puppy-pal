package ds.cmu.puppypal;

/*
 * @author: rsequeir, Rheann Sequeira
 * */
public class PuppyBreed {

	String name;
	Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PuppyBreed(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return "PuppyBreed{" + "name:" + name + ", id:'" + id + '\'' + '}';
	}
}
