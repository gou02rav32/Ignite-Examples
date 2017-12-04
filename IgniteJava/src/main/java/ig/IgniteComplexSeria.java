package ig;

import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

public class IgniteComplexSeria {
	public static void serializ() {
		IgniteConfiguration cfg = new IgniteConfiguration() {
			BinaryConfiguration bcfg = new BinaryConfiguration(Collection<String> Person);
		};
		Ignite ignite = Ignition.start();
		
		Person p = new Person();
		cache[1] = p.name = "Tiger";
		}
		
	}
	public static void main(String[] args) {
		
	}
}
class Person {
	public String name;
	public String age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " " + age;
	}
}