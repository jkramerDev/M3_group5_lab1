package tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class OrgChartImpl implements OrgChart{

		//Employee is your generic 'E'..
		//nodes is your tree
		private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();

		/**
		 * it doesn't exist, add it.
		 * create a new 'node', which is:
		 * new element that goes into nodes ..
		 * 
		 * Once created, add to the list
		 * 
		 */
		public void addRoot(Employee e) {		
			GenericTreeNode<Employee> rootEmployee = new GenericTreeNode<Employee>(e);
			nodes.add(rootEmployee);
	 	}

		
		//removes all elements in the org chart..  can do in a 1 liner
		public void clear() {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Assuming the manager for the newPerson is in the list...
		 * 
		 * loop thru each of the nodes 
		 * - get the GenericTreeNode<Employee> currentEmployee -> the nodes.get(i)
		 * - conditionally check (if) the currentEmployee.data.equals the manager
		 * --> note how equals(Object) is overridden in the Employee class!
		 * - if true, create a new node out of this employee
		 * -> (1) add the new node as a child of the manager
		 * -> (2) add the new node to the list of nodes
		 * 
		 * --> note required GenericTreeNode initialization - ie create the array
		 * 
		 */
		public void addDirectReport(Employee manager, Employee newPerson) {
			for (int i = 0; i < nodes.size(); i++) {
				GenericTreeNode<Employee> currentEmployee = nodes.get(i);
				if (currentEmployee.data.equals(manager)) {
					GenericTreeNode<Employee> newE = new GenericTreeNode<Employee>(newPerson);
					
					//add child to the current employee's list of children
					//look closely & see the ref to currentEmployee is the same as in the list
					//so modifying it 'works'
					currentEmployee.addChild(newE);
					
					//add the new node to the list of nodes
					nodes.add(newE);
					break;
				}
			}
			
		}

		/**
		 * 
			You have a:  removeChild(E toRemove) in the GenericGraphNode
	 
			in your org chart you want to remove an employee.  Couple steps.  
			From your client you'll invoke a version of OrgChartImpl.removeEmployee
	 
	 		From there:

	 		before you remove the node that contains this employee you may want to:

	 			handle the employee's reports ( children )
	   			assign them to employee's supervisor

	   		So now you have a handle to the Employee's supervisor - 
	   		consider that when calling your GenericTreeNode removeChild
	    	i.e. the supervisor has potentially a bunch of children (at least one - the employee getting removed ).
	    	in other words, consider what object removeChild is invoked on.  
	    	If the employee's supervisor that supports removal of employee from the supervisor's list of children
		 */
		
		@Override
		public void removeEmployee(Employee firedPerson) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * recursion is key here
		 */
		public void showOrgChartDepthFirst() {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * show root.  level 0
		 * add all children of current level to queue.  
		 * 
		 * print each child - level next
		 * get children of each child
		 * 
		 */

		@Override
		public void showOrgChartBreadthFirst() {
			
			
			Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();		//linked list can implement queue
	        queue.add(nodes.get(0));

	        GenericTreeNode<Employee> currentNode;
	        Set<GenericTreeNode<Employee>> alreadyVisited = new HashSet<>();
	        System.out.print("Visited nodes: ");
	   
	        while (!queue.isEmpty()) {
	            currentNode = queue.remove();
	            System.out.print(currentNode.data + " | ");

	            alreadyVisited.add(currentNode);
	            queue.addAll(currentNode.children);
	            queue.removeAll(alreadyVisited);
	        }
	        
			
		}
		
	
	
}

