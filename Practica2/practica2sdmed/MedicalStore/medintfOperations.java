package MedicalStore;


/**
* MedicalStore/medintfOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from store.idl
* martes 28 de febrero de 2023 23H43' CET
*/

public interface medintfOperations 
{
  String check_medicine (String med_id);
  String process_medicine (String med_id, int quantity);
  String form_medicine (String med_id);
  String ver_cesta ();
  int total_price ();
  String pay_bill ();
} // interface medintfOperations