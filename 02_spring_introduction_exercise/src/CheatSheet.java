import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CheatSheet {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int n = Integer.parseInt(scanner.nextLine());
		
		Integer[] minionsId = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt)
				.toArray(Integer[]::new);

		List<Integer> numbers = Arrays.stream(scanner.nextLine().split("\\s+"))

				.map(Integer::parseInt).collect(Collectors.toList());

		List<Double> numbers1 = Arrays.stream(scanner.nextLine().split("\\s+"))

				.map(Double::parseDouble).collect(Collectors.toList());

		List<String> numbers2 = Arrays.stream(scanner.nextLine().split("\\s+")).collect(Collectors.toList());

		System.out.println(numbers.stream().map(Object::toString).collect(Collectors.joining(" ")));
		
		System.out.println(String.join("\n", numbers2));
	}
}
