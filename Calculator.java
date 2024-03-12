package ipadressen;

import java.util.Vector;

public class Calculator {
	
	public static void main(String[] args) {
		// Das Netz 192.168.33.0/24 soll in vier gleiche große Subnetze aufgeteilt werden. 
		
		// OUTPUT: Dokumentieren Sie den Rechenweg, um die Subnetze zu bilden#
		
		Vector mySubNets = createIpSpectrum("192.168.33.0", 24, 4);
        System.out.println(mySubNets);
	}
	
	public static Vector<Ipadress> createIpSpectrum(String ip, int cidr, int numberRequestedSubnet) {
		int bitsForSubnets = getSubnet(numberRequestedSubnet);
		int clientsCount = 32 - cidr - bitsForSubnets;
		String basisIp = getBinarIp(ip).substring(0, cidr);
		
		Vector<Ipadress> subnetIps = new Vector<Ipadress>();
		// build different subnets IDs
		for(int i=0; i<numberRequestedSubnet; i++) {
			String binaryString = leftPad(Integer.toBinaryString(i), bitsForSubnets);
			String netId = basisIp + binaryString + fillPad(clientsCount, '0');
			String firstId = basisIp + binaryString + fillPad(clientsCount -1, '0') + "1";
			String lastId = basisIp + binaryString + fillPad(clientsCount -1, '1') + "0";
			String broadcastId = basisIp + binaryString + fillPad(clientsCount, '1');
            int numberClients = (int)Math.round(Math.pow(2, clientsCount))-2;
			
            Ipadress temp = new Ipadress(netId, firstId, lastId, broadcastId, numberClients);
			subnetIps.add(temp);
            
			System.out.println(i+1+". Subnetz: ");
			System.out.println("================");
			System.out.println("Netzadresse:");
			System.out.println(convertBinarToIp(netId));
			System.out.println("Erste IP-Adresse:");
			System.out.println(convertBinarToIp(firstId));
			System.out.println("Letze IP-Adresse:");
			System.out.println(convertBinarToIp(lastId));
			System.out.println("Broadcastadresse: ");
			System.out.println(convertBinarToIp(broadcastId));
            System.out.println("numberOfClients: "+numberClients);
			System.out.println(" ");
            
		}
        return subnetIps;
		//System.out.println(subnetIps);
	}
	
	public static String convertBinarToIp(String binarIp){
		int[] oktets = new int[4];
		oktets[0] = Integer.parseInt(binarIp.substring(0, 8), 2);
		oktets[1] = Integer.parseInt(binarIp.substring(8, 16), 2);
		oktets[2] = Integer.parseInt(binarIp.substring(16, 24), 2);
		oktets[3] = Integer.parseInt(binarIp.substring(24, 32), 2);
		return oktets[0] + "." + oktets[1] + "." + oktets[2] + "." + oktets[3];
	}
	
	public static String getBinarIp(String ip) {
		String bitString = "";
		String[] oktets = new String[4];
		oktets = ip.split("\\.");
		for(String oktet: oktets) {
			bitString += leftPad(Integer.toBinaryString(Integer.parseInt(oktet)), 8);
		}
		return bitString;
	}
	
	public static String leftPad(String input, int count) {
		String padding =  "";
		for(int i=0; i<count-input.length(); i++) {
			padding += "0";
		}
		return padding + input; 
	}
	
	public static String fillPad(int count, char filler) {
		String result = "";
		for(int i=0; i<count; i++) {
			result += filler;
		}
		return result;  
	}
	
	public static int getSubnet(int numberRequestedSubnet) {
		return log2(numberRequestedSubnet);
	}
	
	public static int log2(int N)
    {
		return (int)Math.ceil(Math.log(N) / Math.log(2));
    }

}
