package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.UK);
		
		String path = "/Users/luizclaudiomoraes/temp/employee.csv";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the full file path: ");
		if(path.isEmpty()) {
			path = sc.nextLine();
		} else {
			System.out.println(path);
		}
		
		System.out.println("Enter salary: ");
		double salary = sc.nextDouble();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Employee> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				
				String[] fields = line.split(",");
				list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
				
				line = br.readLine();
			}
			
			List<String> greaterSalary = list.stream()
					.filter(p -> p.getSalary() > salary)
					.map(p -> p.getEmail())
					.sorted( (e1, e2) -> e1.toUpperCase().compareTo(e2.toUpperCase()) )
					.collect(Collectors.toList());
			
			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) );
			greaterSalary.forEach(System.out::println);
			
			final char constLetter = 'M';
			double sum = list.stream().filter(p -> p.getName().charAt(0) == constLetter).map(p -> p.getSalary()).reduce(0.0, (x,y) -> x + y);
			System.out.println("Sum of salary of people whose name starts with " + constLetter + ": " + String.format("%.2f",sum));
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			sc.close();
		}
		
	}
	
}
