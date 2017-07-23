// Mario Ruiz
// #46301389
public class Bathroom{
	
	private int state ;  // empty = 0, man = 1, woman = 2
	private int count; // number of people currently in bathroom
	private LinkedList <Person> menQ = new LinkedList <Person> (); // queue for men who are waiting
	private LinkedList <Person> womenQ = new LinkedList <Person> (); // queue for women who are waiting
	
	public Bathroom{
		this.menQ = new LinkedList <Person> (); //initialize an empty queue
		this.womenQ = new LinkedList <Person> () // initialize an empty queue
		this. state = 0;
		this.count = 0;
		
	}
	
	
	public void woman_wants_to_enter (Person woman){
		if (this.state == 0 || (this.state == 2 && this.count < maxCapacity)){ //if bathroom is empty or its currently used by women and there is space open, enter
			this.count++;
			this.state = 2;
			// woman.enter();  // does whatever needs to be done;
			System.out.println("woman entered bathroom.");
		}
		else { //else bathroom is full with women or is currently used by men, wait in line
			this.womenQ.add(woman);
		}
	} 

	public void man_wants_to_enter (Person man) {
		if ((this.state == 0 || this.state == 1) && this.count < maxCapacity){ //if bathroom is empty or its currently used by men and there is space open, enter
			this.count++;
			this.state = 1;
			// man.enter();  // does whatever needs to be done;
			System.out.println("man entered bathroom.");
		}
		else { //else bathroom is full with men or is currently used by women, wait in line
			this.menQ.add(man);
		}
	}

	public void woman_leaves () {
		assertTrue("There are currently no women in the bathroom", (this.state == 1 || this.state == 0));
		this.count--;
		System.out.println("woman left bathroom.");
		if (count == 0){ // if bathroom is empty
			this.state = 0;
			while(this.menQ.size() != 0 && this.count < maxCapacity){ // Let any men currently waiting enter the bathroom
				this.menQ.remove();
				this.count++;
				this.state = 1;
				System.out.println("man entered bathroom.");
			}
		}
		else if (this.womenQ.size() != 0){ // bathroom still occupied by women, if woman is waiting , she may now enter
			this.womenQ.remove();
			this.count++
			System.out.println("woman entered bathroom.");	
		}
		
	}

	public void man_leaves (){
		assertTrue("There are currently no men in the bathroom", (this.state == 2 || this.state == 0));
		this.count--;
		System.out.println("man left bathroom.");
		if (count == 0){ // if bathroom is empty
			this.state = 0;
			while(this.womenQ.size() != 0 && this.count < maxCapacity){ // Let any women currently waiting enter the bathroom
				this.menQ.remove();
				this.count++;
				this.state = 2;
				System.out.println("woman entered bathroom.");
			}
		}
		else if (this.menQ.size() != 0){ // bathroom still occupied by men, if man is waiting , he may now enter
			this.menQ.remove();
			this.count++
			System.out.println("man entered bathroom.");	
		}
		
	}
	
}

