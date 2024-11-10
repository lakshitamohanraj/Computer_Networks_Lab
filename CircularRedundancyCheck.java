import java.util.Scanner;
public class CircularRedundancyCheck{


    public static String xor(String a,String b) // both a,b of same length
    {
        String result = "";
        for(int i=0;i<a.length();i++){
           if(a.charAt(i)==b.charAt(i)) result += "0";
           else result+="1";
        }
        return result;
    }
    public static String div(String divident,String divisor){
        int divisorLength = divisor.length();
        String remainder = divident;
        
        while(remainder.length() >= divisorLength){

            if(remainder.charAt(0)=='1'){
            String part = remainder.substring(0,divisorLength);
            String xorRes = xor(part,divisor);
            remainder = xorRes + remainder.substring(divisorLength);
             
            }
            else{ 
                   remainder = remainder.substring(1); // move the bit to the right by 1
            }

        }

        return remainder;
    }
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the Data to be forwarded");
        String data = sc.next(); // divident
        System.out.println("Enter the polynomial Bits - Divisor - CRC Code generator");
        String gen = sc.next(); //divisor

        StringBuilder code = new StringBuilder(data);
        
        int temp = gen.length() -1;
        while(temp>0){
            code.append("0"); // adding len(gen) - 1 0's to the code
            temp--;
        }

        String transmittedData =  data + div(code.toString(),gen);
        System.out.println("The actual transmitted data is : "+transmittedData);
        System.out.println("Enter the recieved Data");
        String recievedData = sc.next();
        String crcCheckRemainder = div(recievedData,gen);
        if(Integer.parseInt(crcCheckRemainder)==0){
            System.out.print("There is no error in the recieved code");
        }
        else{
             System.out.print("There is an error in the recieved code");
        }
    }

}