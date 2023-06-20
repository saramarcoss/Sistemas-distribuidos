import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

class HelloImpl extends HelloPOA {
  private ORB orb;

  public void setORB(ORB orb_val) {
    orb = orb_val; 
  }
    
  // implementa el metodo sayHello() definido en la interfaz para que el cliente pueda invocarlo
  public String sayHello() {
    return "\nHello world !!\n";
  }
    
  // implementa el metodo shutdown() definido en la interfaz para que el cliente pueda terminar la ejecucion del servidor
  public void shutdown() {
    orb.shutdown(false);
  }
}

public class HelloServer {
  public static void main(String args[]) {
    try{
      // crea e inicializa el ORB
      ORB orb = ORB.init(args, null);

      // obtiene la referencia del rootpoa & activa el POAManager
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // crea el servant
      HelloImpl helloImpl = new HelloImpl();
      helloImpl.setORB(orb); 

      // obtiene la referencia del objeto desde el servant
      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
      Hello href = HelloHelper.narrow(ref);
          
      // obtiene la referencia del servicio de nombres
      org.omg.CORBA.Object objRef =
          orb.resolve_initial_references("NameService");
      // Usa NamingContextExt, que es parte de la especificacion Interoperable
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // vincula la referencia del objeto en el servicio de nombres
      String name = "Hello";
      NameComponent path[] = ncRef.to_name( name );
      ncRef.rebind(path, href);

      System.out.println("HelloServer ready and waiting ...");

      // espera por las invocaciones de los clientes
      orb.run();
    } 
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
      System.out.println("HelloServer Exiting ...");
        
  }
}