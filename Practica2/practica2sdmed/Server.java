import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;
import MedicalStore.*;
class Server extends _medintfImplBase{

	static Map<String,Integer> medicine_list;
    //cesta
    static Map<String,Integer> cesta=new HashMap<String,Integer>();
	static int[] prices;
    //forma farmaceutica de los medicamentos
    static String[] form=new String[10];
	static int total;
	static int store;
	public Server(){
		total=0;
		medicine_list=new HashMap<String,Integer>();
		prices=new int[10];
		medicine_list.put("Paracetamol",15);
		medicine_list.put("Lyrica",20);
		medicine_list.put("Ibuprofen",20);
		medicine_list.put("Codiene",20);
		medicine_list.put("Cymbalta",20);
		medicine_list.put("Ativan",20);
		medicine_list.put("Losartan",20);
		medicine_list.put("Actidone",20);
		medicine_list.put("Lexapro",20);
		medicine_list.put("Lyrica",20);
		prices[0]=90; 	prices[1]=150; 	prices[2]=70;
		prices[3]=120; 	prices[4]=24; 	prices[5]=98;
		prices[6]=140; 	prices[7]=274; 	prices[8]=210;
		prices[9]=50;
        form[0]="Capsules"; 	form[1]="Syrup"; 	form[2]="Tablet";
        form[3]="Capsules"; 	form[4]="Tablet"; 	form[5]="Tablet";
        form[6]="Tablet";    	form[7]="Syrup"; 	form[8]="Capsules";
        form[9]="Tablet";


	}

  // checking for availabilty for medicines
	public String check_medicine(String med_id){
		store=-1;
		int i=0;
		if(!medicine_list.containsKey(med_id)   )
			{
						return "Medicine not present";
			}
	  String s="";
		for(String ik: medicine_list.keySet() ){

			if( ik.equals(med_id) )
			{
				s="Medicine present with total available quantity: "+ medicine_list.get(med_id) +"\nPrice:"+prices[i];
				store=i;
				break;
			}
			i++;
		}
		return s;
	}

  // add to cart
	public String process_medicine(String med_id,int quantity){
		int q=  medicine_list.get(med_id);
		String s="";
		if(q>=quantity){
			total+= prices[store] * quantity;
			medicine_list.put( med_id, medicine_list.get(med_id) - quantity );
            cesta.put(med_id,quantity);
			System.out.println("Medicine "+med_id+" ordered with quantities "+quantity);
			s= "Medicine "+med_id+" with " + quantity+ " added to cart";
		}
	  return s;
	}
  //return total prices
  public int total_price(){
		return total;
	}

	public String pay_bill(){
		System.out.println("Payment successful with amount "+total);
		total=0;
		return "Payment successful!!";
	}
    //return form of medicine
    public String form_medicine(String med_id){
        String s="";
        int i=0;
        for(String ik: medicine_list.keySet() ){
            if( ik.equals(med_id) )
            {
                s="Form of medicine: "+ form[i];
                break;
            }
            i++;
        }
        return s;
    }

    //return medicines in cart
    public String ver_cesta(){
        String s="";
        for(String ik: cesta.keySet() ){
            s+="Medicine: "+ ik + " Quantity: "+cesta.get(ik)+"\n";
        }
        return s;
    
    }


	public static void main(String[] args){
		try{

		ORB orb = ORB.init(args, null);
		Server lbRef=new Server();
		orb.connect(lbRef);
		org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		NamingContext ncRef = NamingContextHelper.narrow(objRef);
		NameComponent nc=new NameComponent( "Medicines" , "" );
		NameComponent path[] = {nc} ;
		ncRef.rebind(path,lbRef);
		System.out.println("Server started!!");
		Thread.currentThread().join();
		}catch(Exception e){
			System.err.println(e);
		}
	}


}