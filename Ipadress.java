package ipadressen;

public class Ipadress {
    String netId;
    String firstId;
    String lastId;
    String broadcastId;
    int numberClients;
    
    public Ipadress(String netId, String firstId, String lastId, String broadcastId, int numberClients) {
        this.netId = netId;
        this.firstId = firstId;
        this.lastId = lastId;
        this.broadcastId = broadcastId;
        this.numberClients = numberClients;
    }

    @Override
    public String toString() {
        return "Ipadress: [\n\t netId       = " + Calculator.convertBinarToIp(netId) + ",\n\t firstId     = " + Calculator.convertBinarToIp(firstId) + ",\n\t lastId      = " + Calculator.convertBinarToIp(lastId) + ",\n\t broadcastId = "
                + Calculator.convertBinarToIp(broadcastId) + ",\n\t numberOfClients = " + numberClients + "\n]";
    }
}
