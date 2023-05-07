package com.xter.demo;

public class ClassDemo {

	static class Human{

	}

	static class BatMan extends Human{

	}

	static class SpideMan extends Human{

	}

	public void fight(Human human){
		System.out.println("Human,Fight");
	}

	public void fight(BatMan batMan){
		System.out.println("BatMan,Fight");
	}

	public void fight(SpideMan man){
		System.out.println("SpideMan,Fight");
	}

	public static void main(String[] args) {
		Human batMan = new BatMan();
		Human spideMan = new SpideMan();
		BatMan batMan1 = new BatMan();

		ClassDemo demo = new ClassDemo();
		demo.fight(batMan);
		demo.fight(spideMan);
		demo.fight(batMan1);
	}
}
