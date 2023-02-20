import java.util.HashMap;
import java.util.Map;

public class Example1 {

	private Map<Integer, String> dataMap;
	private boolean needToInitialize;
	
	public String get(int id) {
		if (needToInitialize) {
			initialize();
			needToInitialize = false;
		}
		
		return dataMap.get(id);
	}

	public void put(int id, String value) {
		if (needToInitialize) {
			initialize();
			needToInitialize = false;
		}
		
		dataMap.put(id, value);
	}
	
	private void initialize() {
		int expectedCapacity = doExpensiveSizeCalculation();
		dataMap = new HashMap<>(expectedCapacity);
	}

	private int doExpensiveSizeCalculation() {
		return 10; // TODO: replace with a database call
	}
}
